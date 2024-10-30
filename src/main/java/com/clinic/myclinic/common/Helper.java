package com.clinic.myclinic.common;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import com.google.cloud.Timestamp;

public class Helper {

	public static Date dateFormater(Object firebaseDate) {
		if (firebaseDate instanceof Timestamp) {
			Timestamp timestamp = (Timestamp) firebaseDate;
			return timestamp.toDate();
		} else {
			Calendar calendar = Calendar.getInstance();
			calendar.set(1800, Calendar.JANUARY, 1, 0, 0, 0);
			return calendar.getTime();
		}
	}
	
	public static Date defaultDate() {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
            Date defaultDate = sdf.parse("Wed Jan 01 00:00:00 IST 1800");
			return removeMilliseconds(defaultDate);
		} catch (Exception e) {
			return null;
		}
	}
	
	public static Date removeMilliseconds(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.MILLISECOND, 0); // Set milliseconds to 0
        return cal.getTime();
    }
	
	public static boolean isNullOrEmpty(Collection<?> collection) {
		return collection==null || collection.isEmpty();
	}
	
	public static boolean isNullOrEmpty(Map<?,?> map) {
		return map==null || map.isEmpty();
	}
	
	public static boolean isFutureDate(Date date) {
		Date currentDate = new Date();
		return date.after(currentDate);
	}
}
