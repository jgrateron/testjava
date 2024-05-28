package com.fresco;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class ContarCaracteres {
	public static void main(String[] args) throws IOException {
		var mapCaracteres = Files.lines(Path.of("src/main/java/com/fresco/CalculateAverage_jgrateron.java"))
				.map(s -> List.of(s.split(""))).flatMap(List::stream).filter(c -> !c.isBlank())
				.collect(Collectors.groupingBy(String::toString, Collectors.counting()));

		var i = new AtomicInteger(0);

		System.out.println("-".repeat(500));
		mapCaracteres.entrySet().stream()//
				.sorted(Comparator.comparing(Map.Entry<String, Long>::getValue).reversed()
						.thenComparing(Map.Entry.comparingByKey()))//
				.limit(20)//
				.forEach(e -> {
					System.out.println("%02d) %s: %d".formatted(i.incrementAndGet(), e.getKey(), e.getValue()));
				});
	}
}
