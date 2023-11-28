package com.fresco;

import java.util.List;

public class StringJoin {
	public static void main(String[] args) {
		var frutas = List.of("Pera", "Manzana", "Banana", "Mandarinas");
		var misFrutas = String.join(", ", frutas);
		System.out.println(misFrutas);
	}
}
