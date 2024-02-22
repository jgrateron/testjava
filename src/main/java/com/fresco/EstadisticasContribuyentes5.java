package com.fresco;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Map;
import java.util.TreeMap;

import com.fresco.parse.Entero;
import com.fresco.parse.Index;
import com.fresco.parse.SplitFile;

public class EstadisticasContribuyentes5 {
	public static String FILE = "contribuyentes.txt";

	public static void main(String[] args) throws IOException {
		var ini = System.nanoTime();
		procesar();
		var end = System.nanoTime();
		var time = (end - ini) / 1_000_000;
		System.out.println(time);
	}

	private static void procesar() throws IOException {
		var result = SplitFile.split(FILE, 3, '|').parallelStream()//
				.collect(ResumenContribuyente::new, ResumenContribuyente::acumular, ResumenContribuyente::combinar);
		result.show();
	}

	public static class ResumenContribuyente {
		private int count;
		private Map<Index, Entero> groupEstado;
		private Map<Index, Entero> groupCondicion;
		private Index index;

		public ResumenContribuyente() {
			count = 0;
			groupEstado = new TreeMap<Index, Entero>();
			groupCondicion = new TreeMap<Index, Entero>();
			index = new Index();
		}

		public void acumular(SplitFile sf) {
			for (;;) {
				var linea = sf.getLine();
				if (linea == null) {
					break;
				}
				acumular(linea);
			}
		}

		public void acumular(ByteBuffer[] records) {
			count++;
			var estado = records[1];
			index.setByteBuffer(estado);
			var cuantosXEstado = groupEstado.get(index);
			if (cuantosXEstado == null) {
				var newEstado = ByteBuffer.allocate(estado.limit());
				newEstado.put(records[1]);
				newEstado.flip();
				var newIndex = new Index();
				newIndex.setByteBuffer(newEstado);
				groupEstado.put(newIndex, new Entero(1));
			} else {
				cuantosXEstado.inc(1);
			}
			var condicion = records[2];
			index.setByteBuffer(condicion);
			var cuantosXCondicion = groupCondicion.get(index);
			if (cuantosXCondicion == null) {
				var newCondicion = ByteBuffer.allocate(condicion.limit());
				newCondicion.put(condicion);
				newCondicion.flip();
				var newIndex = new Index();
				newIndex.setByteBuffer(newCondicion);
				groupCondicion.put(newIndex, new Entero(1));
			} else {
				cuantosXCondicion.inc(1);
			}
		}

		public ResumenContribuyente combinar(ResumenContribuyente other) {
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

		public void show() {
			System.out.println(count);
			System.out.println("-".repeat(40));
			groupEstado.entrySet().stream()//
					.sorted(Map.Entry.comparingByValue())//
					.forEach(e -> {
						var estado = e.getKey().toString();
						System.out.println(estado + " " + e.getValue());
					});
			System.out.println("-".repeat(40));
			groupCondicion.entrySet().stream()//
					.sorted(Map.Entry.comparingByValue())//
					.forEach(e -> {
						var condicion = e.getKey().toString();
						System.out.println(condicion + " " + e.getValue());
					});
			System.out.println("-".repeat(40));
		}
	}
}
