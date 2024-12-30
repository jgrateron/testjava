package com.fresco;

public class RecordGeneric {

	public record Pair<T>(T first, T second) {
	}

	public record Tuple<T, U>(T first, U second) {
	}

	public record Triple<T, U, W>(T first, U second, W third) {
	}

	public static void main(String[] args) {

		var intPair = new Pair<Integer>(10, 20);
		System.out.println(intPair);

		var strPair = new Pair<String>("Hello", "World");
		System.out.println(strPair);

		var tuple = new Tuple<String, Integer>("Hello World", 30);
		System.out.println(tuple);

		var triple = new Triple<String, Integer, Double>("Hello World", 21, Math.PI);
		System.out.println(triple);
	}
}
