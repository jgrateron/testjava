package com.fresco;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;

public class RemoveDuplicates2 {
	public static void main(String[] args) {

		record Color(int i, String name) {
			public static Color of(int i, String name) {
				return new Color(i, name);
			}

			@Override
			public final int hashCode() {
				return name.hashCode();
			}

			@Override
			public final boolean equals(Object o) {
				if (o instanceof Color otr) {
					return this.name.equals(otr.name);
				}
				return false;
			}

			@Override
			public final String toString() {
				return name;
			}
		}

		var list = List.of(Color.of(1, "red"), Color.of(2, "blue"), Color.of(3, "green"), Color.of(4, "yellow"),
				Color.of(5, "black"), Color.of(6, "green"), Color.of(7, "yellow"), Color.of(8, "yellow"));
		System.out.println(list);
		System.out.println();

		// Method 1: Using groupingBy and then extracting keys (Does NOT preserve order)
		var newList1 = list.stream()
				.collect(Collectors.groupingBy(Color::name))
				.keySet().stream().toList();
		System.out.println("M1: " + newList1);

		// Method 2: Using toMap and keeping the first occurrence (Does NOT preserve
		// order)
		var newList2 = list.stream()
				.collect(Collectors.toMap(Color::name, n -> n, (ol, ne) -> ol))
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
				.collect(Collectors.groupingBy(Color::name, LinkedHashMap::new, Collectors.toList()))
				.keySet().stream().toList();
		System.out.println("M5: " + newList5);

		// Method 6: Using toMap and LinkedHashMap (preserve order)
		var newList6 = list.stream()
				.collect(Collectors.toMap(Color::name, n -> n, (ol, ne) -> ol, LinkedHashMap::new))
				.keySet().stream().toList();
		System.out.println("M6: " + newList6);

		// Method 7
		var newList7 = new ArrayList<>(new HashSet<Color>(list));
		System.out.println("M7: " + newList7);

		// Method 8
		var newList8 = new ArrayList<>(new LinkedHashSet<Color>(list));
		System.out.println("M8: " + newList8);
	}
}
