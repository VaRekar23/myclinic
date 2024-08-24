package com.clinic.myclinic.common;

import java.util.Calendar;
import java.util.Date;
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
}
