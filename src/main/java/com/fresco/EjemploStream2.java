package com.fresco;

import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class EjemploStream2 {
	record Pais(String nombre, String capital, Integer nroHabitantes) {
	}

	private static Predicate<Pais> filtro = p -> {
		return p.nroHabitantes > 10_000_000 && p.nombre.length() > 5;
	};
	
	private static Function<Pais, String> convertir = p -> {
		return "Nombre: %s Capital: %s Nro Habitantes: %s".formatted(p.nombre, p.capital, p.nroHabitantes);
	};

	private static Consumer<String> mostrar = s -> {
		System.out.println(s);
		System.out.println("-".repeat(80));
	};
	
	private static Comparator<Pais> comparar = (p, q) -> {
		return p.nombre.compareTo(q.nombre);
	};
	
	public static void main(String[] args) {
		getPaises().stream()
		.filter(filtro)
		.sorted(comparar)
		.map(convertir)
		.forEach(mostrar);
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



