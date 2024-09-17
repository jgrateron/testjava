package com.fresco;

import java.util.function.Function;
import java.util.stream.Collectors;

public class FrecuenciaCaracteres {
	public static void main(String[] args) {
		var input = "apple";

		var freq = input.chars().boxed()//
				.collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

		var result = freq.entrySet().stream()//
				.map(e -> (char) e.getKey().intValue() + ":" + e.getValue())//
				.collect(Collectors.joining(", "));
		System.out.println(result);
	}
}


