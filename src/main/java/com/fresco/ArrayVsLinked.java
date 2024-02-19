package com.fresco;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;

public class ArrayVsLinked {

	public static void main(String[] args) {
		System.out.println("-".repeat(50));
		addTest(new LinkedList<Integer>());
		addTest(new ArrayList<Integer>());
		System.out.println("-".repeat(50));
	}

	public static void addTest(List<Integer> lista) {
		var summary = IntStream.rangeClosed(1, 21)//
				.mapToLong(n -> {
					var ini = System.nanoTime();
					for (int i = 0; i < 1_000_000; i++) {
						lista.add(i);
					}
					var end = (System.nanoTime() - ini) / 1_000_000;
					return end;
				})//
				.skip(1)//
				.summaryStatistics();
		System.out.println("%s avg: %.2f min: %d max: %d"
				.formatted(lista.getClass().getName(), summary.getAverage(),
				summary.getMin(), summary.getMax()));
	}
}

