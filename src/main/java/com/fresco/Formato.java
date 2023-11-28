package com.fresco;

import java.util.List;

public class Formato {

	public static void main(String[] args) {
		var edades = List.of(17, 19, 16, 15, 21, 24, 18, 17, 20, 22, 18, 19, 23, 31, 16, 28, 25);

		var mayorEdad = edades.stream().filter(e -> e >= 18).count();
		System.out.println(mayorEdad);

		var menorEdad = edades.stream()//
				.filter(e -> e < 18)//
				.count();
		System.out.println(menorEdad);

		var promedio = edades.stream().mapToInt(e -> e).average();
		promedio.ifPresent(System.out::println);

		var estadisticas = edades.stream()//
				.mapToInt(Integer::valueOf)//
				.summaryStatistics();
		System.out.println(estadisticas);
	}
}

