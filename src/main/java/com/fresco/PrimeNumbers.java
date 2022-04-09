package com.fresco;

import java.util.function.IntFunction;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

public class PrimeNumbers {

	public static void main(String[] args) {

		record Number(int value, boolean isPrime) {};

		IntFunction<Number> intToNumber = num -> {
			boolean isPrime = num > 1 && LongStream.rangeClosed(2, Math.round(Math.sqrt(num)))
					   .allMatch(n -> num % n != 0);
			return new Number(num, isPrime);
		};
		
		var listNumbers = IntStream.rangeClosed(1, 100)
				          .mapToObj(intToNumber)
				          .toList();

		var max = 1 + listNumbers.stream()
				      .map( n -> Integer.toString(n.value).length())
				      .reduce(0, (a , n ) -> n > a ? n : a);
		
		System.out.println(" " + "-".repeat((max + 1) * 10));
		listNumbers.forEach( n -> 
		{
			if (n.value % 10 == 1)
				System.out.print("|");

			var str = Integer.toString(n.value);
			str = " ".repeat(max - str.length()) + str + (n.isPrime ? "*" : " ");
        	System.out.print(str);
        	
        	if (n.value % 10 == 0)
        		System.out.println("|");
		});
		System.out.println(" " + "-".repeat((max + 1) * 10));
	}
}

