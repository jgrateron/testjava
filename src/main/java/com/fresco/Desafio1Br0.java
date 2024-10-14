package com.fresco;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

public class Desafio1Br0 {
	public static String FILE = "/opt/desafio/measurements.txt";

	public static void main(String[] args) throws IOException {
		Locale.setDefault(Locale.US);
		var ini = System.nanoTime();
		procesar();
		var time = (System.nanoTime() - ini) / 1_000_000;
		System.out.println(time);
	}

	public static void procesar() throws IOException {
		var resultMap = Files.lines(Path.of(FILE)).parallel()//
				.map(l -> {
					var ar = l.split(";");
					return Map.entry(ar[0], Double.valueOf(ar[1]));
				})//
				.collect(Collectors.groupingBy(Map.Entry::getKey, Collectors.summarizingDouble(Map.Entry::getValue)));
		var result = resultMap.entrySet().stream()//
				.sorted(Map.Entry.comparingByKey())//
				.map(e -> {
					var min = e.getValue().getMin();
					var avg = e.getValue().getAverage();
					var max = e.getValue().getMax();
					return "%s=%.1f/%.1f/%.1f".formatted(e.getKey(), min, avg, max);
				})//
				.collect(Collectors.joining(", ", "{", "}"));
		System.out.println(result);
	}
}
