package com.fresco;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class FrecuenciaCaracteres {

	public static void main(String[] args) {
		var INPUTS = """
				apple
				Añoranza
				Incomprehensibilities
				Electroencefalografista
				""";

		INPUTS.lines().forEach(input -> {
			var freq = input.toLowerCase().chars().boxed()//
					.collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

			var result = freq.entrySet().stream()//
					.sorted(Map.Entry.comparingByValue())//
					.map(e -> (char) e.getKey().intValue() + ":" + e.getValue())//
					.collect(Collectors.joining(", "));

			System.out.println("%-25s: [%s]".formatted(input, result));
		});
	}
}
