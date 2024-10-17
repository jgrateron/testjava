package com.fresco;

import java.util.Comparator;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Aleatorio {

	public static void main(String[] args) {
		var random = new Random();

		var sumasRestas = IntStream.rangeClosed(1, 4)
				.mapToObj(i -> IntStream.rangeClosed(1, 2)
						.map(n -> random.nextInt(1000, 10000)).boxed()
						.sorted(Comparator.comparing(Integer::intValue).reversed())
						.toList());

		var multiplicaciones = IntStream.rangeClosed(1, 2)
				.mapToObj(i -> IntStream.rangeClosed(1, 2)
						.map(n -> random.nextInt(10, 1000)).boxed()
						.toList());

		var listaNumeros = Stream.concat(sumasRestas, multiplicaciones)//
				.toList();

		System.out.println();
		for (int i = 0; i < listaNumeros.size(); i += 2) {
			for (var j = 0; j < 2; j++) {
				System.out.println("%5d %20d".formatted(listaNumeros.get(i).get(j), listaNumeros.get(i + 1).get(j)));
			}
			System.out.println();
		}
	}
}
