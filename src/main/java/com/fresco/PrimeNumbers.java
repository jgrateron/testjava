package com.fresco;

import java.util.function.IntFunction;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

public class PrimeNumbers {
	public record Number(int value, boolean isPrime) {
	}

	public static void main(String[] args) {

		IntFunction<Number> intToNumber = num -> {
			var isPrime = num > 1 && LongStream.rangeClosed(2, Math.round(Math.sqrt(num)))
					.allMatch(n -> num % n != 0);
			return new Number(num, isPrime);
		};

		var listNumbers = IntStream.rangeClosed(1, 100).mapToObj(intToNumber).toList();
		System.out.println(" " + "-".repeat(50));
		listNumbers.forEach(n -> {
			if (n.value % 10 == 1) {
				System.out.print("|");
			}
			var cad = "%4s%s".formatted(n.value, n.isPrime ? "*" : " ");
			System.out.print(cad);
			if (n.value % 10 == 0) {
				System.out.println("|");
			}
		});
		System.out.println(" " + "-".repeat(50));
	}
}
