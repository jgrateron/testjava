package com.fresco;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingDouble;

import java.util.Comparator;

import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.joining;

public class EstadisticasAgrupadas {

	record Gasto(String concepto, String fecha, double monto) {
	};

	private static List<Gasto> getGastos() {
		return List.of(new Gasto("Comida", "2022-06-01", 100.00), new Gasto("Transporte", "2022-06-01", 10.00),
				new Gasto("Otros", "2022-06-01", 30), new Gasto("Comida", "2022-06-02", 120.00),
				new Gasto("Transporte", "2022-06-02", 20.00), new Gasto("Otros", "2022-06-02", 10),
				new Gasto("Comida", "2022-06-03", 80.00), new Gasto("Transporte", "2022-06-03", 5.00),
				new Gasto("Comida", "2022-06-04", 70.00), new Gasto("Transporte", "2022-06-04", 10.00),
				new Gasto("Otros", "2022-06-04", 25), new Gasto("Cine", "2022-06-04", 200));
	}

	public static void main(String[] args) {

		System.out.println("-".repeat(70));
		var totalGastosxConcepto = getGastos().stream()
				.collect(groupingBy(Gasto::concepto, summingDouble(Gasto::monto)));

		totalGastosxConcepto.entrySet().stream()//
				.sorted(Comparator.comparing(Map.Entry<String, Double>::getValue).reversed())//
				.forEach(e -> {
					System.out.println("Concepto: %s%s Total: %9.2f".formatted(e.getKey(),
							" ".repeat(15 - e.getKey().length()), e.getValue()));
				});
		System.out.println("-".repeat(70));
		var conceptoGastosxFecha = getGastos().stream()
				.collect(groupingBy(Gasto::fecha, mapping(Gasto::concepto, joining(", "))));

		conceptoGastosxFecha.entrySet().stream()//
				.sorted(Map.Entry.comparingByKey())//
				.collect(Collectors.toList())//
				.forEach(e -> {
					System.out.println("Fecha: %s Conceptos: '%s'".formatted(e.getKey(), e.getValue()));
				});
	}
}
