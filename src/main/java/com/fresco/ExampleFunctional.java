package com.fresco;

import java.util.Arrays;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.IntBinaryOperator;
import java.util.function.IntPredicate;
import java.util.function.IntUnaryOperator;
import java.util.function.Predicate;

public class ExampleFunctional {

	public static void test1() {
		IntUnaryOperator toDouble = n -> n * 2;
		IntPredicate isEven = n -> n % 2 == 0;
		IntBinaryOperator acum = (l, r) -> l + r;
		
		int[] numbers = { 1, 2, 3, 4, 5, 6 };
		var result = Arrays.stream(numbers)//
				.map(toDouble)//
				.filter(isEven)//
				.reduce(0, acum);
		System.out.println(result);

	}

	public static void test2() {
		Function<Integer, Integer> toDouble = n -> n * 2;
		Predicate<Integer> isEven = n -> n % 2 == 0;
		BinaryOperator<Integer> acum = (l, r) -> l + r;

		var numbers = List.of(1, 2, 3, 4, 5, 6);
		var result = numbers.stream()//
				.map(toDouble)//
				.filter(isEven)//
				.reduce(0, acum);
		System.out.println(result);
	}

	public static void main(String[] args) {
		test1();
		test2();
	}

}
