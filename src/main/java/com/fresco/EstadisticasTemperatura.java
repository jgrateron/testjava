package com.fresco;

import java.util.ArrayList;
import java.util.function.BinaryOperator;

public class EstadisticasTemperatura {

	record Estadistica(int count, int sum, int min, int max) {

		public double average() {
			return count == 0 ? 0 : (double) sum / (double) count;
		}

		@Override
		public String toString() {
			return "Estadisticas[count=%d, sum=%d, min=%d, average=%.6f, max=%d]".formatted(count, sum, min, average(), max);
		}
	}

	/*
	 * 
	 */
	public static void main(String[] args) {

		BinaryOperator<Estadistica> operadorEstadistica = (sub, elem) -> {
			int count = sub.count + 1;
			int sum = sub.sum + elem.sum;
			int min = elem.sum < sub.min ? elem.sum : sub.min;
			int max = elem.sum > sub.max ? elem.sum : sub.max;
			return new Estadistica(count, sum, min, max);
		};

		var temperaturas = new ArrayList<Integer>();

		var estadistica = temperaturas.stream()
				.map(i -> new Estadistica(1, i, i, i))
				.reduce(new Estadistica(0,0,0,0), operadorEstadistica);

		System.out.println(estadistica);
		var esta = temperaturas.stream()
				.mapToInt(Integer::valueOf)
				.summaryStatistics();
		System.out.println(esta);
	}
}
