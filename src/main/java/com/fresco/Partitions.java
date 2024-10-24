package com.fresco;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Partitions {

	public static void partitionsByNroElements(int n, List<?> list) {
		System.out.println("\nI want partitions of at least %d elements.\n".formatted(n));
		var partNumbers = IntStream.range(0, list.size())//
				.mapToObj(i -> Map.entry(i + 1, list.get(i)))//
				.collect(Collectors.groupingBy(e -> (e.getKey() - 1) / n,
						Collectors.mapping(Map.Entry::getValue, Collectors.toList())));

		partNumbers.forEach((k, v) -> {
			System.out.println("%s: %s".formatted(k, v));
		});
	}

	public static void partitionsByNroPart(int n, List<?> list) {
		System.out.println("\nI want %d partitions.\n".formatted(n));
		var partMonths = IntStream.range(0, list.size())//
				.mapToObj(i -> Map.entry(i + 1, list.get(i)))//
				.collect(Collectors.groupingBy(e -> (e.getKey() - 1) % n,
						Collectors.mapping(Map.Entry::getValue, Collectors.toList())));

		partMonths.forEach((k, v) -> {
			System.out.println("%s: %s".formatted(k, v));
		});
	}

	public static void main(String[] args) {

		var months = List.of("January", "February", "March", "April", "May", "June", "July", "August", "September",
				"October", "November", "December").stream().map(s -> "%-10s".formatted(s)).toList();

		var numbers = List.of(10, 12, 14, 16, 18, 20, 22, 24, 26, 28, 30, 32, 34, 36, 38, 40, 42, 44, 46);

		System.out.println("-".repeat(40));
		partitionsByNroElements(4, numbers);
		System.out.println("-".repeat(40));
		partitionsByNroElements(2, months);
		System.out.println("-".repeat(40));
		partitionsByNroPart(3, numbers);
		System.out.println("-".repeat(40));
		partitionsByNroPart(5, months);
		System.out.println("-".repeat(40));
	}
}
