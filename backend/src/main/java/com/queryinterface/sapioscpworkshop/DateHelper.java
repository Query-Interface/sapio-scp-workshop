package com.queryinterface.sapioscpworkshop;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DateHelper {

	public static String serializeDate(final LocalDateTime date) {
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSS", Locale.ENGLISH);
    	//LocalDate date = LocalDate.parse("2018-04-10T04:00:00.000Z", inputFormatter);
    	return formatter.format(date);
    }
    
    public static LocalDateTime unserializeDate(final String serializedDate) {
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH);
    	return LocalDateTime.parse(serializedDate, formatter);
    }
}
