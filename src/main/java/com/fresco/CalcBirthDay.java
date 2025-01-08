package com.fresco;

import java.time.LocalDate;
import java.time.Period;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CalcBirthDay {

	public static void main(String[] args) {
		Stream.of(
				Map.entry("Linus Torvalds", LocalDate.of(1969, 12, 28)),
				Map.entry("James Gosling", LocalDate.of(1955, 5, 19)),
				Map.entry("Bjarne Stroustrup", LocalDate.of(1950, 12, 30)),
				Map.entry("Richard Stallman", LocalDate.of(1953, 3, 16)),
				Map.entry("John Berners-Lee", LocalDate.of(1955, 6, 8)))
				.forEach(CalcBirthDay::printPersonInfo);
	}

	private static void printPersonInfo(Map.Entry<String, LocalDate> e) {
		System.out.println(e.getKey());
		var dateOfBirth = e.getValue();
		var now = LocalDate.now();
		System.out.println("You are %s old.".formatted(calcPeriodToStr(dateOfBirth, now)));
		var birthDate = dateOfBirth.withYear(now.getYear());
		System.out.println("Your next birthday is in %s.".formatted(calcPeriodToStr(now, birthDate)));
		System.out.println();
	}

	/**
	 * Calculates the period between two dates and formats it as a human-readable string.
	 */
	public static String calcPeriodToStr(LocalDate start, LocalDate end) {
		var period = Period.between(start, end);
		var periodMap = new LinkedHashMap<String, Integer>();
		periodMap.put("year", period.getYears());
		periodMap.put("month", period.getMonths());
		periodMap.put("day", period.getDays());
		var parts = periodMap.entrySet().stream()
				.filter(e -> e.getValue() > 0)
				.map(e -> e.getValue() + " " + pluralize(e.getKey(), e.getValue()))
				.collect(Collectors.toList());
		if (parts.size() > 1) {
			var lastPart = parts.remove(parts.size() - 1);
			return String.join(", ", parts) + " and " + lastPart;
		}
		return parts.isEmpty() ? "" : parts.get(0);
	}

	private static String pluralize(String word, int value) {
		return value == 1 ? word : word + "s";
	}
}
