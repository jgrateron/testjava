package com.fresco;

import static java.util.Map.entry;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class IntToRoman {
	private static List<Map.Entry<Integer, String>> romanNumerals = List.of(entry(1000, "M"), entry(900, "CM"),
			entry(500, "D"), entry(400, "CD"), entry(100, "C"), entry(90, "XC"), entry(50, "L"), entry(40, "XL"),
			entry(10, "X"), entry(9, "IX"), entry(5, "V"), entry(4, "IV"), entry(1, "I"));

	public static String toRoman(int number) {
		var num = new AtomicInteger(number);
		return romanNumerals.stream()//
				.<String>mapMulti((e, consumer) -> {
					while (num.get() >= e.getKey()) {
						consumer.accept(e.getValue());
						num.addAndGet(-e.getKey());
					}
				})//
				.collect(Collectors.joining());
	}

	public static void main(String[] args) {
		Stream.of(168, 1994, 2024, 3888)//
				.forEach(n -> {
					System.out.println("%4s : %s".formatted(n, toRoman(n)));
				});
	}
}
