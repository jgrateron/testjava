package com.fresco;

public class MultipleTypeResult {

	record Pair<T, U>(T first, U second) {
	}

	public static Pair<Integer, Double> increment(int x, double y) {
		return new Pair<Integer, Double>(x + 1, y + 1);
	}

	public static void main(String[] args) {
		var result = increment(1, 1.1);
		System.out.println(result);
	}
}
