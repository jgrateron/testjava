package com.fresco;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class EstadisticasTemperatura3 {
	private static final String FILE = "./measurements.txt";

	public record Medicion(String estacion, double temperatura) {
	}

	public static void main(String[] args) throws IOException {
		var ini = System.nanoTime();
		procesar();
		var end = System.nanoTime();
		System.out.println((end - ini) / 1_000_000 + "ms");
	}

	public static void procesar() throws IOException {
		var summary = Files.lines(Path.of(FILE)).parallel()//
				.map(s -> {
					var arr = s.split(";");
					return new Medicion(arr[0], Double.parseDouble(arr[1]));
				})//
				.collect(Collectors.groupingBy(Medicion::estacion,
						Collector.of(SummaryMedicion::new, SummaryMedicion::accumulator, SummaryMedicion::combiner)));

		Comparator<Map.Entry<String, SummaryMedicion>> comparatorMin = (a, b) -> {
			return Double.compare(a.getValue().min.temperatura, b.getValue().min.temperatura);
		};
		Comparator<Map.Entry<String, SummaryMedicion>> comparatorMax = (a, b) -> {
			return Double.compare(a.getValue().max.temperatura, b.getValue().max.temperatura);
		};

		summary.entrySet().stream()//
				.sorted(comparatorMin)//
				.limit(10)//
				.forEach(e -> {
					System.out.println(e.getKey() + " " + e.getValue());
				});
		System.out.println("-".repeat(50));
		summary.entrySet().stream()//
				.sorted(comparatorMax.reversed())//
				.limit(10)//
				.forEach(e -> {
					System.out.println(e.getKey() + " " + e.getValue());
				});
	}

	public static class SummaryMedicion {
		private int count;
		private Medicion min, max;
		private double sum;

		public SummaryMedicion() {
			min = new Medicion("", Double.MAX_VALUE);
			max = new Medicion("", Double.MIN_VALUE);
			count = 0;
			sum = 0;
		}

		public void accumulator(Medicion med) {
			count++;
			sum += med.temperatura;
			min = min.temperatura <= med.temperatura ? min : med;
			max = max.temperatura >= med.temperatura ? max : med;
		}

		public SummaryMedicion combiner(SummaryMedicion other) {
			min = other.min.temperatura < min.temperatura ? other.min : min;
			max = other.max.temperatura > max.temperatura ? other.max : max;
			count += other.count;
			sum += other.sum;
			return this;
		}

		public double getAvg() {
			return count == 0 ? 0 : sum / count;
		}

		@Override
		public String toString() {
			return "[count=%d min=%.1f max=%.1f avg=%.1f]".formatted(count, min.temperatura, max.temperatura, getAvg());
		}
	}
}
