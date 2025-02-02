package com.fresco;

import java.util.stream.Stream;

public class PalindromeNumber {
	public static void main(String[] args) {
		Stream.of(121, -121, 123, 1221, 10)
				.forEach(n -> {
					verifyPalindrome(n);
				});
	}

	public static void verifyPalindrome(int n) {
		var ori = String.valueOf(n);
		var sb = new StringBuilder(ori);
		var rev = sb.reverse().toString();
		if (ori.equals(rev)) {
			System.out.println(n + " is a palindrome.");
		} else {
			System.out.println(n + " is not a palindrome.");
		}
	}
}

