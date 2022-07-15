package com.fresco;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Stream;

public class ClockTime {
	public static void main(String[] args) {
		Stream.of(args)
		.map(ZoneId.SHORT_IDS::get)
		.map(ZoneId::of)
		.map(ZonedDateTime::now)
		.map(time -> time.format(DateTimeFormatter.ofPattern("E, dd MMM u hh:mm:ss a z")))
		.forEach(System.out::println);
	}
}
