package com.fresco;

import java.util.List;
import java.util.stream.Collectors;

public class JoinListString {

	public static void main(String[] args) {
		
		var fruits = List.of("Pears", "Apples", "Tangerines", "Bananas");

		var concat = "";
		for (var f : fruits) {
			if (concat.isEmpty()) {
				concat = f;
			} else {
				concat = concat + ", " + f;
			}
		}
		System.out.println(concat);
		//Pears, Apples, Tangerines, Bananas
		
		System.out.println(String.join(", ", fruits));

		System.out.println(fruits.stream().collect(Collectors.joining(", ")));
		
		System.out.println(fruits.stream().collect(StringBuilder::new,
				(sb, s) -> sb.append(sb.isEmpty() ? s : ", " + s), StringBuilder::append));

	}
}
