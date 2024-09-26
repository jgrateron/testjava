package com.fresco;

import static java.util.Comparator.reverseOrder;
import static java.util.Map.Entry.comparingByKey;
import static java.util.Map.Entry.comparingByValue;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.joining;

public class GroupsData2 {
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
		var mundiales = MUNDIALES.lines()//
				.map(l -> {
					var ar = l.split(";");
					return new WorldCup(ar[0], ar[1]);
				}).toList();

		var mapMundialesCount = mundiales.stream()//
				.collect(groupingBy(WorldCup::winner, counting()));

		mapMundialesCount.entrySet().stream()//
				.sorted(comparingByValue(reverseOrder())).forEach(e -> {
					System.out.println("%-10s: %d".formatted(e.getKey(), e.getValue()));
				});
		System.out.println("-".repeat(50));

		var mapMundiales = mundiales.stream()//
				.collect(groupingBy(WorldCup::winner));

		mapMundiales.entrySet().stream().sorted(comparingByKey())//
				.forEach(e -> {
					var years = e.getValue().stream().map(WorldCup::year).collect(joining(", "));
					System.out.println("%-10s: %s".formatted(e.getKey(), years));
				});
		System.out.println("-".repeat(50));
	}
}


