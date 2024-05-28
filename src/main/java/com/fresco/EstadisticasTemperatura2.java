package com.fresco;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class EstadisticasTemperatura2 {
	private static final String FILE = "./measurements.txt";

	public record Medicion(String estacion, double temperatura) {
	}

	public static void main(String[] args) throws IOException {
		var ini = System.nanoTime();
		var summary = Files.lines(Path.of(FILE)).parallel()//
				.map(s -> {
					var arr = s.split(";");
					return new Medicion(arr[0], Double.parseDouble(arr[1]));
				}).collect(SummaryMedicion::new, SummaryMedicion::accumulator, SummaryMedicion::combiner);
		System.out.println("count=" + summary.count);
		System.out.println("min=" + summary.min.toString());
		System.out.println("max=" + summary.max.toString());
		System.out.println("avg=%.2f".formatted(summary.getAvg()));
		var end = System.nanoTime();
		System.out.println((end - ini) / 1_000_000 + "ms");
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
	}
}
