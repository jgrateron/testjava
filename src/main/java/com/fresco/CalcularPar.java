package com.fresco;

import java.util.List;

public class CalcularPar {

	public static void main(String[] args) {

		var numeros = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
		var pares = numeros.stream().filter(n -> n % 2 == 0).toList();
		System.out.println(pares);
		var factorial = numeros.stream().reduce(1, (a, r) -> a * r);
		System.out.println(factorial);
	}

}
