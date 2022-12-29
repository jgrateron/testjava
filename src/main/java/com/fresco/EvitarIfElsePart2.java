package com.fresco;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class EvitarIfElsePart2 {

	private record Operandos(int a, int b) {
	};
	private Map<String, Function<Operandos, Integer>> operaciones;

	public static void main(String[] args) {
		new EvitarIfElsePart2().run();
	}
	public EvitarIfElsePart2() {
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
			var result = operaciones.get(funcion).apply(new Operandos(a, b));
			System.out.println("Result: " + result);
		} else {
			System.err.println("Operación no soportada: " + funcion);
		}
	}
	public Function<Operandos, Integer> sum = oper -> {
		return oper.a + oper.b;
	};
	public Function<Operandos, Integer> rest = oper -> {
		return oper.a - oper.b;
	};
	public Function<Operandos, Integer> mult = oper -> {
		return oper.a * oper.b;
	};
	public Function<Operandos, Integer> divi = oper -> {
		return oper.a / oper.b;
	};
}


