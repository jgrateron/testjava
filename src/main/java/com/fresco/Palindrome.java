package com.fresco;

public class Palindrome {
	public static void main(String[] args) {
		System.out.println("Palindrome words");
		System.out.println("-".repeat(16));
		LIST_WORDS.lines()//
				.forEach(s -> {
					var reverse = new StringBuilder(s).reverse().toString();
					var result = s.equals(reverse);
					System.out.println("%-8s %s".formatted(s, result));
				});
	}

	public static String LIST_WORDS = """
			level
			radar
			rotator
			reviver
			racecar
			top spot
			redder
			madam
			noon
			civic
			kayak
			""";
}

