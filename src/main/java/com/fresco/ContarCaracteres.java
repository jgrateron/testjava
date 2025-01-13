package com.fresco;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ContarCaracteres {
	public static void main(String[] args) throws IOException {
		var mapCaracteres = Files.lines(Path.of("lorem.txt"))
				.flatMap(linea -> linea.chars().boxed())
				.filter(ContarCaracteres::esCaracterValido)
				.map(ch -> String.valueOf((char) ch.intValue()))
				.collect(Collectors.groupingBy(String::toString, Collectors.counting()));

		var listaOrdenada = mapCaracteres.entrySet().stream()//
				.sorted(Comparator.comparing(Map.Entry<String, Long>::getValue).reversed()
						.thenComparing(Map.Entry.comparingByKey()))//
				.limit(20)
				.toList();

		IntStream.range(0, listaOrdenada.size())
				.forEach(i -> {
					var e = listaOrdenada.get(i);
					System.out.println("%02d) %s: %d".formatted(i, e.getKey(), e.getValue()));
				});
	}

	private static boolean esCaracterValido(Integer character) {
		return !Character.isWhitespace(character) && Character.isDefined(character);
	}
}
