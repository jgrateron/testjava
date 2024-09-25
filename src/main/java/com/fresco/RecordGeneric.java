package com.fresco;

public class RecordGeneric {

	public record Pair<T>(T first, T second) {
	}

	public record Tuple<T, U>(T first, U second) {
	}

	public static void main(String[] args) {

		var pair = new Pair<String>("Jairo", "Grateron");
		System.out.println(pair);

		var tuple = new Tuple<Integer, String>(45, "Jairo Grateron");
		System.out.println(tuple);

	}

}
