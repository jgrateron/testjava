package com.fresco;

import static java.util.Comparator.comparing;

import java.util.List;

public class TestComparator {
	public record DiaSemana(String nombre, int pos) {
	}

	public static void main(String[] args) {

		var listaDias = List.of(new DiaSemana("Domingo", 7), new DiaSemana("Lunes", 1), new DiaSemana("Martes", 2),
				new DiaSemana("Miércoles", 3), new DiaSemana("Jueves", 4), new DiaSemana("Viernes", 5),
				new DiaSemana("Sábado", 6));

		listaDias.stream().sorted(comparing(DiaSemana::pos).reversed().thenComparing(comparing(DiaSemana::nombre)))
				.forEach(System.out::println);
		System.out.println("-".repeat(30));

		listaDias.stream().sorted(comparing(DiaSemana::pos)).forEach(System.out::println);
		System.out.println("-".repeat(30));
	}
}
