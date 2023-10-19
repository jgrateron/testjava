package com.fresco;

public class CalcularPi {

	public static double factorial(double n) {
		if (n <= 0) {
			return 1;
		}
		var fact = 1.0;
		while (n > 0) {
			fact = fact * n;
			n--;
		}
		return fact;
	}

	public static double elevado(double n, double i) {
		var z = 1.0;
		while (i > 0) {
			z = z * n;
			i--;
		}
		return z;
	}

	public static double calcularPi(double k) {
		var suma = 0.0;
		for (var n = 1.0; n <= k; n++) {
			var a1 = factorial(2 * n);
			var a2 = elevado(16, n);
			var a3 = elevado(factorial(n), 2);
			var b1 = a1 / (a2 * a3);
			var b2 = 2 * n + 1;
			var b3 = 1 / b2;
			var z = b1 * b3;
			suma += z;
		}
		var pi = 3 * (1 + suma);
		return pi;
	}

	public static void main(String[] args) {

		System.out.println(calcularPi(20));
		System.out.println(Math.PI);
	}
}
