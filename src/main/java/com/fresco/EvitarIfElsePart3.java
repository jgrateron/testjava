package com.fresco;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

public class EvitarIfElsePart3 {

	private Map<String, BiFunction<Integer, Integer, Integer>> operaciones;

	public static void main(String[] args) {
		new EvitarIfElsePart2().run();
	}

	public EvitarIfElsePart3() {
		operaciones = new HashMap<>();
		operaciones.put("+", sum);
		operaciones.put("-", rest);
		operaciones.put("*", mult);
		operaciones.put("/", divi);
	}

	public void run() {
		ejecutarOperacion("+", 20, 10);
		ejecutarOperacion("-", 20, 10);
		ejecutarOperacion("*", 20, 10);
		ejecutarOperacion("/", 20, 10);
		ejecutarOperacion("%", 20, 10);
	}

	public void ejecutarOperacion(String funcion, int a, int b) {
		if (operaciones.containsKey(funcion)) {
			var result = operaciones.get(funcion).apply(a, b);
			System.out.println("Result: " + result);
		} else {
			System.err.println("Operación no soportada: " + funcion);
		}
	}

	public BiFunction<Integer, Integer, Integer> sum = (a, b) -> {
		return a + b;
	};
	public BiFunction<Integer, Integer, Integer> rest = (a, b) -> {
		return a - b;
	};
	public BiFunction<Integer, Integer, Integer> mult = (a, b) -> {
		return a * b;
	};
	public BiFunction<Integer, Integer, Integer> divi = (a, b) -> {
		return a / b;
	};
}


