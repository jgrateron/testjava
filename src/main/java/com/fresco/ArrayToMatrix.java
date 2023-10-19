package com.fresco;

import java.util.ArrayList;
import java.util.List;

public class ArrayToMatrix {

	private record Lista(List<Integer> a, Lista next) {
	};

	public static void main(String[] args) {
		var array = List.of(1,2,3,4,5,6,7,8,9,10);
		List<Lista> matrix = new ArrayList<>();
		array.stream().forEach(System.out::println);
		
		//var x = array.stream().collect(() -> new Lista(new ArrayList<Integer>(), null), (a,b)-> a.add(b), (a,b) -> a.addAll(b));
	}

}
