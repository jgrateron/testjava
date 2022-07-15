package com.fresco;

import java.util.List;

public class EjemploStream1 {

	record Pais(String nombre, String capital, Integer nroHabitantes) {
	}

	public static void main(String[] args) {
		getPaises().stream()
		.filter(p -> p.nroHabitantes > 10_000_000 && p.nombre.length() > 5)
		.map(p -> "Nombre: %s Capital: %s Nro Habitantes: %s".formatted(p.nombre, p.capital, p.nroHabitantes))
		.forEach(s -> {
			System.out.println(s);
			System.out.println("-".repeat(80));
		});
	}

	private static List<Pais> getPaises() {
		return List.of(new Pais("Venezuela", "Caracas", 28_440_000), 
				new Pais("Colombia", "Bogota", 50_880_000),
				new Pais("Brasil", "Brasilia", 212_600_000), 
				new Pais("Ecuador", "Quito", 17_640_000),
				new Pais("Peru", "Lima", 33_000_000), 
				new Pais("Bolivia", "La Paz", 11_700_000),
				new Pais("Chile", "Santiago", 19_120_000), 
				new Pais("Argentina", "Buenos Aires", 45_380_000));
	}
}

