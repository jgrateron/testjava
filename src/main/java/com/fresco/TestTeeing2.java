package com.fresco;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class TestTeeing2 {
	public static void main(String[] args) {
		var numbers = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9);

		record Result(Set<Integer> evenSet, Set<Integer> oddSet) {
		}
		var result = numbers.stream().collect(Collectors.teeing(
				Collectors.filtering(n -> n % 2 == 0, Collectors.toSet()), // evens
				Collectors.filtering(n -> n % 2 != 0, Collectors.toSet()), // odds
				(evenSet, oddSet) -> new Result(evenSet, oddSet)));

		System.out.println("Evens: " + result.evenSet);
		System.out.println("Odds: " + result.oddSet);
	}
}
































