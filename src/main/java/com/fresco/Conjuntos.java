package com.fresco;

import java.util.Set;
import static com.fresco.Debug.println;

public class Conjuntos {
	public static void main(String[] args) {
		Set<String> numerosPares = Set.of("2", "4", "6", "8");
		System.out.println(numerosPares.contains("1"));
		System.out.println(numerosPares.contains("2"));
		System.out.println(numerosPares.contains("5"));
		System.out.println(numerosPares.contains("6"));
		
		

	}
}
