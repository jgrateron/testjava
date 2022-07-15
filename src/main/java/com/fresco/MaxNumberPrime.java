package com.fresco;

import java.util.function.Predicate;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class MaxNumberPrime {

	public static void main(String[] args) {
		
		System.out.println("run");
		Predicate <Long> isPrime = num -> {
			return num > 1 && LongStream.rangeClosed(2, 1000).allMatch(n -> num % n != 0) &&
					          LongStream.rangeClosed(1001, Math.round(Math.sqrt(num)))
					          .parallel()
					          .filter(n -> n % 2 != 0)
					          .allMatch(n -> num % n != 0);
		};
		var ini = System.nanoTime();
		var maxPrime = Stream.iterate(Long.MAX_VALUE, n -> n - 1)
				       .peek(n -> System.out.println(n))
			           .filter(isPrime)
		               .limit(1)
		               .toList();

		var end = System.nanoTime();
		var duration = (end - ini) / 1000000;
		System.out.println(maxPrime + " in " + duration + " ns");
	}
}
