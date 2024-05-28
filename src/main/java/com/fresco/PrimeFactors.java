package com.fresco;

import java.util.function.Predicate;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class PrimeFactors {
	public static void main(String[] args) {
		Predicate <Long> isPrime = num -> {
			return num > 1 && LongStream.rangeClosed(2, 1000).allMatch(n -> num % n != 0) &&
					          LongStream.rangeClosed(1001, Math.round(Math.sqrt(num)))
					          .parallel()
					          .filter(n -> n % 2 != 0)
					          .allMatch(n -> num % n != 0);
		};
		var maxPrime = Stream.iterate(9223372036854775807L, n -> n - 1)
		           .filter(isPrime)
	               .limit(1)
	               .toList();
		System.out.println(maxPrime);
	}
}
