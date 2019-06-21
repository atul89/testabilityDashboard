package com.au.utils;

import java.time.Duration;

public class TimeUtils {
	
	private TimeUtils() {
		// to be used for static methods
	}
	
	public static String durationToHhMm(long durationInMillis) {
		Duration duration = Duration.ofMillis(durationInMillis);
		long durationHrs = duration.toHours();
		long durationMins = duration.minusHours(durationHrs).toMinutes();
		return String.format("%s:%s", durationHrs, durationMins);
	}

}
