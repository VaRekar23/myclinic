package com.clinic.myclinic.controller;

import java.io.ByteArrayInputStream;
import java.util.UUID;
import org.bytedeco.javacv.*;
import org.bytedeco.opencv.opencv_core.*;
import org.bytedeco.opencv.opencv_objdetect.*;
import org.bytedeco.opencv.opencv_imgproc.*;
import static org.bytedeco.opencv.global.opencv_core.*;
import static org.bytedeco.opencv.global.opencv_imgcodecs.*;
import static org.bytedeco.opencv.global.opencv_imgproc.*;
import static org.bytedeco.opencv.global.opencv_objdetect.*;
import org.bytedeco.javacpp.BytePointer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.firebase.cloud.StorageClient;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@CrossOrigin
@RestController
public class ImageUploadController {
	
	@RequestMapping(value="api/image-controller/upload-image", method=RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> uploadImage(@RequestBody MultipartFile file) {
		try {
			Bucket bucket = StorageClient.getInstance().bucket();
			String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
			
			Blob blob = bucket.create(fileName, file.getInputStream(), file.getContentType());
			
			return new ResponseEntity<String>(fileName, HttpStatus.OK);
		} catch (Exception e) {
			System.out.println("Upload-Image Error: "+e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "api/image-controller/preview-blurred-eyes", method = RequestMethod.POST,
		consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String, String>> previewBlurredEyes(@RequestBody MultipartFile file) {
		BytePointer buffer = null;
		Mat image = null;
		RectVector eyes = null;
		CascadeClassifier eyeDetector = null;

		try {
			// Convert MultipartFile to Mat
			byte[] imageBytes = file.getBytes();
			image = imdecode(new Mat(imageBytes), IMREAD_COLOR);
			
			// Convert to grayscale for better detection
			Mat grayImage = new Mat();
			cvtColor(image, grayImage, COLOR_BGR2GRAY);
			
			// Enhance contrast
			equalizeHist(grayImage, grayImage);
			
			// Load eye cascade classifier
			eyeDetector = new CascadeClassifier();
			
			try (InputStream is = getClass().getResourceAsStream("/haarcascade_eye.xml")) {
				if (is == null) {
					throw new RuntimeException("Could not find haarcascade_eye.xml in resources");
				}
				
				File tempFile = File.createTempFile("haarcascade_eye", ".xml");
				tempFile.deleteOnExit();
				Files.copy(is, tempFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
				
				if (!eyeDetector.load(tempFile.getAbsolutePath())) {
					throw new RuntimeException("Could not load eye cascade classifier");
				}
			}
			
			// Detect eyes with adjusted parameters
			eyes = new RectVector();
			eyeDetector.detectMultiScale(
				grayImage,           // Use grayscale image
				eyes,
				1.05,               // Smaller scale factor for more detections
				3,                  // Reduced minimum neighbors
				0,                  // Flags
				new Size(20, 20),   // Minimum size
				new Size(150, 150)  // Maximum size
			);
			
			int totalEyes = (int) eyes.size();
			
			// Validate eye pairs
			List<Rect[]> validEyePairs = new ArrayList<>();
			// Only proceed if we detect at least 2 eyes
			if (totalEyes >= 2) {
				// Convert eyes to a list for sorting and validation
				List<Rect> eyeList = new ArrayList<>();
				for (int i = 0; i < totalEyes; i++) {
					eyeList.add(eyes.get(i));
				}
				
				// Sort eyes by x-coordinate (left to right)
				Collections.sort(eyeList, (e1, e2) -> Integer.compare(e1.x(), e2.x()));
				
				for (int i = 0; i < eyeList.size() - 1; i++) {
					for (int j = i + 1; j < eyeList.size(); j++) {
						Rect leftEye = eyeList.get(i);
						Rect rightEye = eyeList.get(j);
						
						if (isValidEyePair(leftEye, rightEye, image.rows(), image.cols())) {
							validEyePairs.add(new Rect[]{leftEye, rightEye});
						}
					}
				}
				
				// If we have valid eye pairs, mask them
				if (!validEyePairs.isEmpty()) {
					// Use the first valid pair
					Rect[] bestPair = validEyePairs.get(0);
					maskEyePair(image, bestPair[0], bestPair[1]);
				}
			}
			
			// Convert image to Base64
			buffer = new BytePointer();
			imencode(".jpg", image, buffer);
			
			long bufferSize = buffer.capacity();
			if (bufferSize > Integer.MAX_VALUE) {
				throw new RuntimeException("Image size too large to process");
			}
			byte[] processedImageBytes = new byte[(int) bufferSize];
			buffer.get(processedImageBytes);
			String base64Image = Base64.getEncoder().encodeToString(processedImageBytes);
			
			// Create response
			Map<String, String> response = new HashMap<>();
			response.put("processedImage", "data:image/jpeg;base64," + base64Image);
			response.put("originalFileName", file.getOriginalFilename());
			response.put("eyePairDetected", String.valueOf(!validEyePairs.isEmpty()));
			
			return new ResponseEntity<>(response, HttpStatus.OK);
			
		} catch (Exception e) {
			System.out.println("Preview-Blurred-Eyes Error: " + e.getMessage());
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		} finally {
			// Clean up native resources
			if (image != null) image.deallocate();
			if (eyes != null) eyes.deallocate();
			if (buffer != null) buffer.deallocate();
			if (eyeDetector != null) eyeDetector.close();
		}
	}

	// Helper method to validate eye pairs
	private boolean isValidEyePair(Rect leftEye, Rect rightEye, int imageHeight, int imageWidth) {
		// More lenient validation parameters
		double distanceX = rightEye.x() - leftEye.x();
		double avgEyeWidth = (leftEye.width() + rightEye.width()) / 2.0;
		
		// Vertical position difference
		double heightDiff = Math.abs(leftEye.y() - rightEye.y());
		
		// Size difference ratio
		double sizeDiff = Math.abs(leftEye.width() - rightEye.width()) / Math.max(leftEye.width(), rightEye.width());
		
		return distanceX > avgEyeWidth && // Eyes should have some minimum distance
			   distanceX < avgEyeWidth * 5 && // But not too far apart
			   heightDiff < avgEyeWidth * 1.0 && // More lenient height difference
			   sizeDiff < 0.7; // More lenient size difference
	}

	// Helper method to mask a pair of eyes
	private void maskEyePair(Mat image, Rect leftEye, Rect rightEye) {
		// Calculate the region to mask with more padding
		int startX = Math.max(0, leftEye.x() - (int)(leftEye.width() * 0.3));
		int endX = Math.min(image.cols(), rightEye.x() + rightEye.width() + (int)(rightEye.width() * 0.3));
		
		int startY = Math.max(0, Math.min(leftEye.y(), rightEye.y()) - (int)(leftEye.height() * 0.7));
		int endY = Math.min(image.rows(), 
						   Math.max(leftEye.y() + leftEye.height(), rightEye.y() + rightEye.height()) + 
						   (int)(leftEye.height() * 0.7));
		
		// Draw a single black rectangle covering both eyes
		rectangle(
			image,
			new Point(startX, startY),
			new Point(endX, endY),
			new Scalar(0, 0, 0, 255),
			-1,
			LINE_8,
			0
		);
	}

}
