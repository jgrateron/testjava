package com.fresco;

import java.util.List;
import java.util.stream.IntStream;

public class ArrayToMatrix {

	public static void main(String[] args) {
		var lista = List.of(10, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33);

		var partition = 3;
		var groups = lista.size() / partition;
		if (lista.size() % groups != 0) {
			groups++;
		}
		IntStream.range(0, groups)
				.mapToObj(i -> {
					var ini = i * partition;
					var end = ini + partition;
					if (end > lista.size()) {
						end = lista.size();
					}
					return lista.subList(ini, end);
				})
				.forEach(i -> {
					System.out.println(i);
				});
	}
}
