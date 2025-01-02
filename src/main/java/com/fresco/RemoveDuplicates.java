package com.fresco;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class RemoveDuplicates {
	public static void main(String[] args) {

		var list = List.of(7, 8, 4, 5, 2, 8, 4, 7, 3, 9, 4, 1, 2, 8, 9, 5, 7, 6, 3, 2, 4, 0, 5, 0, 8, 0);
		System.out.println(list);
		System.out.println();

		// Method 1: Using groupingBy and then extracting keys (Does NOT preserve order)
		var newList1 = list.stream()
				.collect(Collectors.groupingBy(Function.identity()))
				.keySet().stream().toList();
		System.out.println("M1: " + newList1);

		// Method 2: Using toMap and keeping the first occurrence (Does NOT preserve
		// order)
		var newList2 = list.stream()
				.collect(Collectors.toMap(Function.identity(), n -> n, (ol, ne) -> ol))
				.keySet().stream().toList();
		System.out.println("M2: " + newList2);

		// Method 3: Using toSet (Does NOT preserve order)
		var newList3 = list.stream()
				.collect(Collectors.toSet());
		System.out.println("M3: " + newList3);

		// Method 4: Using LinkedHashSet to maintain insertion order while removing
		// duplicates
		var newList4 = list.stream()
				.collect(Collectors.toCollection(LinkedHashSet::new))
				.stream().toList();
		System.out.println("M4: " + newList4);

		// Method 5: Using groupingBy and LinkedHashMap (preserve order)
		var newList5 = list.stream()
				.collect(Collectors.groupingBy(Function.identity(), LinkedHashMap::new, Collectors.toList()))
				.keySet().stream().toList();
		System.out.println("M5: " + newList5);

		// Method 6: Using toMap and LinkedHashMap (preserve order)
		var newList6 = list.stream()
				.collect(Collectors.toMap(Function.identity(), n -> n, (ol, ne) -> ol, LinkedHashMap::new))
				.keySet().stream().toList();
		System.out.println("M6: " + newList6);
		
		// Method 7  Removing duplicates using HashSet (does not preserve order)
		var newList7 = new ArrayList<>(new HashSet<Integer>(list));
		System.out.println("M7: " + newList7);

		// Method 8: Removing duplicates using LinkedHashSet (preserves insertion order)
		var newList8 = new ArrayList<>(new LinkedHashSet<Integer>(list));
		System.out.println("M8: " + newList8);
	}
}
