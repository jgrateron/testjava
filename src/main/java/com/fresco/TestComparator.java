package com.fresco;

import java.util.Comparator;
import java.util.List;

public class TestComparator {
	public record DiaSemana(String nombre, int pos) {
	}

	public static void main(String[] args) {

		var listaDias = List.of(new DiaSemana("Domingo", 7), new DiaSemana("Lunes", 1), new DiaSemana("Martes", 2),
				new DiaSemana("Miércoles", 3), new DiaSemana("Jueves", 4), new DiaSemana("Viernes", 5),
				new DiaSemana("Sábado", 6));

		Comparator<DiaSemana> comparador = (d1, d2) -> {
			return Integer.compare(d1.pos, d2.pos);
		};

		listaDias.stream().sorted(comparador).forEach(System.out::println);
		System.out.println("-".repeat(30));

		listaDias.stream().sorted(comparador.reversed()).forEach(System.out::println);
		System.out.println("-".repeat(30));
	}
}
