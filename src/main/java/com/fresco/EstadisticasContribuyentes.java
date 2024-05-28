package com.fresco;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class EstadisticasContribuyentes {
	public static String FILE = "contribuyentes.txt";

	public record Contribuyente(String numRuc, String indEstado, String indCondicion) {
	}

	public static void main(String[] args) throws IOException {
		var ini = System.nanoTime();
		procesar();
		var end = System.nanoTime();
		var time = (end - ini) / 1_000_000;
		System.out.println(time);
	}

	public static void procesar() throws IOException {
		var result = Files.lines(Path.of(FILE)).parallel()//
				.map(s -> {
					var arr = s.split("\\|", 3);
					return new Contribuyente(arr[0], arr[1], arr[2]);
				})
				.collect(ResumenContribuyente::new, ResumenContribuyente::accumulator, ResumenContribuyente::combiner);
		System.out.println(result.count);
		result.groupEstado.entrySet().stream()//
				.sorted(Map.Entry.comparingByValue())//
				.forEach(e -> {
					System.out.println(e.getKey() + " " + e.getValue());
				});
		result.groupCondicion.entrySet().stream()//
				.sorted(Map.Entry.comparingByValue())//
				.forEach(e -> {
					System.out.println(e.getKey() + " " + e.getValue());
				});
	}

	public static class ResumenContribuyente {
		private int count;
		private Map<String, Entero> groupEstado;
		private Map<String, Entero> groupCondicion;

		public ResumenContribuyente() {
			count = 0;
			groupEstado = new HashMap<String, Entero>();
			groupCondicion = new HashMap<String, Entero>();
		}

		public void accumulator(Contribuyente contribuyente) {
			count++;
			var cuantosXEstado = groupEstado.get(contribuyente.indEstado);
			if (cuantosXEstado == null) {
				groupEstado.put(contribuyente.indEstado, new Entero(1));
			} else {
				cuantosXEstado.inc(1);
			}
			var cuantosXCondicion = groupCondicion.get(contribuyente.indCondicion);
			if (cuantosXCondicion == null) {
				groupCondicion.put(contribuyente.indCondicion, new Entero(1));
			} else {
				cuantosXCondicion.inc(1);
			}
		}

		public ResumenContribuyente combiner(ResumenContribuyente other) {
			count += other.count;
			for (var e : other.groupEstado.entrySet()) {
				var cuantosXEstado = groupEstado.get(e.getKey());
				if (cuantosXEstado == null) {
					groupEstado.put(e.getKey(), e.getValue());
				} else {
					cuantosXEstado.inc(e.getValue());
				}
			}
			for (var e : other.groupCondicion.entrySet()) {
				var cuantosXCondicion = groupCondicion.get(e.getKey());
				if (cuantosXCondicion == null) {
					groupCondicion.put(e.getKey(), e.getValue());
				} else {
					cuantosXCondicion.inc(e.getValue());
				}
			}
			return this;
		}
	}

	public static class Entero implements Comparable<Entero> {
		private int value;

		public Entero(int value) {
			this.value = value;
		}

		public void inc(int value) {
			this.value += value;
		}

		public void inc(Entero entero) {
			this.value += entero.value;
		}

		public int intValue() {
			return value;
		}

		@Override
		public int compareTo(Entero o) {
			return Integer.compare(value, o.value);
		}

		@Override
		public String toString() {
			return Integer.toString(value);
		}
	}
}
