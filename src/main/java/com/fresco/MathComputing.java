package com.fresco;

import java.util.stream.IntStream;

public class MathComputing {

	public static void main(String[] args) {

		
		var summation = IntStream.rangeClosed(0, 4)
				.reduce(0, (sum, n) -> sum += 3 * n);
		System.out.println(summation);

		var sum = 0;
		for (var n = 0; n <= 4; n++) {
			sum += 3 * n;
		}
		System.out.println(sum);
		
		var product = IntStream.rangeClosed(1, 4)
				.reduce(1, (prod, n) -> prod *= 2 * n);
		System.out.println(product);
		var prod = 1;
		for (var n = 1; n <= 4; n++) {
			prod *= 2 * n;
		}
		System.out.println(prod);
	}
}
