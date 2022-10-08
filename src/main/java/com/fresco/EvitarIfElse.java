package com.fresco;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class EvitarIfElse {

	private record Operandos(int a, int b) {
	};
	private Map<String, Function<Operandos, Integer>> operaciones;

	public static void main(String[] args) {
		new EvitarIfElse().run();
	}
	public EvitarIfElse() {
		operaciones = new HashMap<>();
		operaciones.put("+", this::sum);
		operaciones.put("-", this::rest);
		operaciones.put("*", this::mult);
		operaciones.put("/", this::divi);
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
	public int sum(Operandos oper) {
		return oper.a + oper.b;
	}
	public int rest(Operandos oper) {
		return oper.a - oper.b;
	}
	public int mult(Operandos oper) {
		return oper.a * oper.b;
	}
	public int divi(Operandos oper) {
		return oper.a / oper.b;
	}
}

