package com.fresco;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FirstNonRepetead {

	public static void firstNonRepetead(String text, Supplier<Map<String, Long>> mapFactory, String result) {
		var first = text.chars()
				.mapToObj(Character::toString)
				.collect(Collectors.groupingBy(Function.identity(), mapFactory, Collectors.counting()))
				.entrySet().stream()
				.filter(e -> e.getValue() == 1)
				.map(Map.Entry::getKey)
				.findFirst().orElse("null");
		System.out.println("%-10s -> %s: %s [%s]".formatted(text,first,result.equals(first), result));
	}

	public static void main(String[] args) {

		Supplier<Map<String, Long>> newLinkedHashMap = () -> {
			return new LinkedHashMap<String, Long>();
		};
		Supplier<Map<String, Long>> newHashMap = () -> {
			return new HashMap<String, Long>();
		};

		var keyValues = Map.of("aabbcdeeff", "c", "aabbdceeff", "d", "xzyx", "z", "xyyzzx", "null");

		Stream.of(newLinkedHashMap, newHashMap)
				.forEach(mapFactory -> {
					System.out.println("-".repeat(20));
					keyValues.forEach((k, v) -> {
						firstNonRepetead(k, mapFactory, v);
					});
				});
	}
}










