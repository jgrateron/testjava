package com.fresco;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel.MapMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EstadisticasContribuyentes3 {
	public static String FILE = "contribuyentes.txt";

	public static void main(String[] args) throws IOException, InterruptedException {
		var ini = System.nanoTime();
		procesar();
		var end = System.nanoTime();
		var time = (end - ini) / 1_000_000;
		System.out.println(time);
	}

	public static void procesar() throws IOException, InterruptedException {
		var listWorkers = new ArrayList<Worker>();
		var fileContribuyente = new RandomAccessFile(new File(FILE), "r");
		var channel = fileContribuyente.getChannel();
		var cores = Runtime.getRuntime().availableProcessors();
		var length = channel.size();
		var sizeChunk = length / cores;
		var offset = 0;
		while (offset < length) {
			var remaining = length - offset;
			var chunk = remaining > sizeChunk ? sizeChunk : remaining;
			var byteBuffer = channel.map(MapMode.READ_ONLY, offset, chunk);
			do {
				chunk--;
			} while (byteBuffer.get((int) chunk) != '\n');
			chunk++;
			var worker = new Worker(byteBuffer, chunk);
			worker.start();
			listWorkers.add(worker);
			offset += chunk;
		}
		var result = new ResumenContribuyente();
		for (var worker : listWorkers) {
			worker.join();
			result.combiner(worker.getResumen());
		}
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

	public static class Worker extends Thread {
		private ByteBuffer byteBuffer;
		private ByteBuffer linea;
		private ResumenContribuyente resumen;
		private long size;

		public Worker(ByteBuffer byteBuffer, long size) {
			this.byteBuffer = byteBuffer;
			this.size = size;
			this.resumen = new ResumenContribuyente();
			this.linea = ByteBuffer.allocate(255);
		}

		@Override
		public void run() {
			int count = 0;
			while (count < size) {
				var b = byteBuffer.get();
				if (b == '\n') {
					linea.flip();
					resumen.accumulator(linea.array(), linea.limit());
					linea.clear();
				} else {
					linea.put(b);
				}
				count++;
			}
		}

		public ResumenContribuyente getResumen() {
			return resumen;
		}
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

		public void accumulator(byte[] array, int limit) {
			String[] arr = { "", "", "" };
			int idx = 0;
			for (int i = 0; i < limit && idx < 3; i++) {
				if (array[i] == '|') {
					idx++;
				} else {
					if (idx > 0) {
						arr[idx] += (char) array[i];
					}
				}
			}
			count++;
			var cuantosXEstado = groupEstado.get(arr[1]);
			if (cuantosXEstado == null) {
				groupEstado.put(arr[1], new Entero(1));
			} else {
				cuantosXEstado.inc(1);
			}
			var cuantosXCondicion = groupCondicion.get(arr[2]);
			if (cuantosXCondicion == null) {
				groupCondicion.put(arr[2], new Entero(1));
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
