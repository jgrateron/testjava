package com.fresco;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GroupsData3 {
	public static String MUNDIALES = """
			1930;Uruguay
			1934;Italia
			1938;Italia
			1950;Uruguay
			1954;Alemania
			1958;Brasil
			1962;Brasil
			1966;Inglaterra
			1970;Brasil
			1974;Alemania
			1978;Argentina
			1982;Italia
			1986;Argentina
			1990;Alemania
			1994;Brasil
			1998;Francia
			2002;Brasil
			2006;Italia
			2010;España
			2014;Alemania
			2018;Francia
			2022;Argentina
			""";

	public record WorldCup(String year, String winner) {
	}

	public static void main(String[] args) {
		System.out.println("-".repeat(50));

		var mapMundiales = MUNDIALES.lines()//
				.map(l -> {
					var ar = l.split(";");
					return new WorldCup(ar[0], ar[1]);
				}).collect(Collectors.groupingBy(WorldCup::winner,
						Collectors.mapping(WorldCup::year, Collectors.toList())));

		Comparator<Map.Entry<String, List<String>>> comparar = (e1, e2) -> {
			return Integer.compare(e1.getValue().size(), e2.getValue().size());
		};

		mapMundiales.entrySet().stream()//
				.sorted(comparar.reversed())//
				.forEach(e -> {
					System.out.println("%-10s: %d %s".formatted(e.getKey(), e.getValue().size(), e.getValue()));
				});
		System.out.println("-".repeat(50));
	}
}
