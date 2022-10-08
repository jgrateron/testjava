package com.fresco;

public class UsarIfElse {

	private record Operandos(int a, int b) {
	};

	public static void main(String[] args) {
		new UsarIfElse().run();
	}
	public void run() {
		ejecutarOperacion("+", 20, 10);
		ejecutarOperacion("-", 20, 10);
		ejecutarOperacion("*", 20, 10);
		ejecutarOperacion("/", 20, 10);
		ejecutarOperacion("%", 20, 10);
	}
	public void ejecutarOperacion(String funcion, int a, int b) {
		int result;
		if ("+".equals(funcion)) {
			result = sum(new Operandos(a, b));
		} else if ("-".equals(funcion)) {
			result = rest(new Operandos(a, b));
		} else if ("*".equals(funcion)) {
			result = mult(new Operandos(a, b));
		} else if ("/".equals(funcion)) {
			result = divi(new Operandos(a, b));
		} else {
			System.err.println("Operación no soportada: " + funcion);
			return;
		}
		System.out.println("Result: " + result);
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

