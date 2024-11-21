package com.clinic.myclinic.controller;

import java.util.UUID;
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
}
