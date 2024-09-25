package com.fresco;

import java.util.Comparator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class EjemploStream3 {

	public static String COUNTRIES = """
			Colombia,Bogota,50880000
			Brasil,Brasilia,212600000
			Ecuador,Quito,17640000
			Peru,Lima,33000000
			Bolivia,La Paz,11700000
			Chile,Santiago,19120000
			Argentina,Buenos Aires,45380000
			""";

	public record Country(String name, String capital, Integer population) {
	}

	public static Predicate<Country> filtro = p -> {
		return p.population > 10_000_000 && p.name.length() > 5;
	};

	public static Function<Country, String> convertir = p -> {
		return "Nombre: %-15s Capital: %-15s Nro Habitantes: %10s".formatted(p.name, p.capital, p.population);
	};

	public static Consumer<String> mostrar = s -> {
		System.out.println(s);
	};

	public static Comparator<Country> comparar = (p, q) -> {
		return p.name.compareTo(q.name);
	};

	public static void main(String[] args) {
		var listCountries = COUNTRIES.lines()//
				.map(c -> {
					var ar = c.split(",");
					return new Country(ar[0], ar[1], Integer.valueOf(ar[2]));
				}).toList();

		listCountries.stream()//
				.filter(filtro)//
				.sorted(comparar)//
				.map(convertir)//
				.forEach(mostrar);
	}
}
