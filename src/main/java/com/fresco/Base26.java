package com.fresco;

import java.util.Map;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Base26 {
	private static final int BASE = 26;
	private static final int CHAR_OFFSET = 'A' - 1;

	public static String valueOf(int number) {
		var result = new StringBuilder();
		while (number > 0) {
			var remainder = (number - 1) % BASE;
			result.insert(0, (char) (remainder + CHAR_OFFSET + 1));
			number = (number - 1) / BASE;
		}
		return result.toString();
	}

	public static int valueOf(String str) {
		var result = 0;
		for (var c : str.toCharArray()) {
			result = result * BASE + (c - CHAR_OFFSET);
		}
		return result;
	}

	public static String next(String str) {
		var num = valueOf(str);
		return valueOf(num + 1);
	}

	public static void main(String[] args) {
		System.out.println();
		Stream.of("A", "M", "AA", "BZ", "ZZ", "XXX", "ZZZ", "AAAZ", "ZZZZ")
				.map(s -> Map.entry(s, next(s)))
				.forEach(e -> {
					System.out.println("%-5s: %-5s".formatted(e.getKey(), e.getValue()));
				});
		System.out.println();
		IntStream.rangeClosed(26, 52)
				.mapToObj(i -> Map.entry(i, valueOf(i)))
				.forEach(e -> {
					System.out.println("%d: %-5s".formatted(e.getKey(), e.getValue()));
				});
	}
}
