package com.fresco;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel.MapMode;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.IntSummaryStatistics;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

public class Desafio1BR8 {
	public static String FILE = "/opt/desafio/measurements.txt";

	public static void main(String[] args) throws FileNotFoundException, IOException {
		Locale.setDefault(Locale.US);
		var ini = System.nanoTime();
		procesar();
		var time = (System.nanoTime() - ini) / 1_000_000;
		System.out.println(time);
	}

	public static void procesar() throws FileNotFoundException, IOException {
		var workers = new ArrayList<Worker>();
		try (var fileMeasurements = new RandomAccessFile(new File(FILE), "r")) {
			var channel = fileMeasurements.getChannel();
			var length = channel.size();
			var sizeChunk = length / Runtime.getRuntime().availableProcessors();
			var offset = 0l;
			while (offset < length) {
				var remaining = length - offset;
				var chunk = remaining > sizeChunk ? sizeChunk : remaining;
				var byteBuffer = channel.map(MapMode.READ_ONLY, offset, chunk);
				do {
					chunk--;
				} while (byteBuffer.get((int) chunk) != '\n');
				chunk++;
				var worker = new Worker(byteBuffer, chunk);
				workers.add(worker);
				offset += chunk;
			}
			var result = workers.parallelStream()//
					.peek(Worker::execute)//
					.reduce(Worker::combine).get();
			System.out.println(result.toString());
		}
	}

	static class Worker {
		private MappedByteBuffer byteBuffer;
		private long size;
		private Map<ByteBuffer, IntSummaryStatistics> mapMediciones = new HashMap<>();

		public Worker(MappedByteBuffer byteBuffer, long size) {
			this.byteBuffer = byteBuffer;
			this.size = size;
		}

		public void execute() {
			try {
				var count = 0;
				var estacion = ByteBuffer.allocate(50);
				while (count < size) {
					var esNegativo = false;
					var temperatura = 0;
					var b = byteBuffer.get(count++);
					do {
						estacion.put(b);
						b = byteBuffer.get(count++);
					} while (b != ';');
					b = byteBuffer.get(count++);
					if (b == '-') {
						esNegativo = true;
						b = byteBuffer.get(count++);
					}
					if (byteBuffer.get(count) == '.') {
						count++;// punto
						var c = byteBuffer.get(count++);
						temperatura = (b - '0') * 10 + (c - '0');
					} else {
						var c = byteBuffer.get(count++);
						count++;// punto
						var d = byteBuffer.get(count++);
						temperatura = (b - '0') * 100 + (c - '0') * 10 + (d - '0');
					}
					count++;// enter
					temperatura = esNegativo ? -temperatura : temperatura;
					estacion.flip();
					var medicion = mapMediciones.get(estacion);
					if (medicion == null) {
						var newEstacion = ByteBuffer.allocate(50);
						newEstacion.put(estacion);
						newEstacion.flip();
						medicion = new IntSummaryStatistics();
						mapMediciones.put(newEstacion, medicion);
					}
					medicion.accept(temperatura);
					estacion.clear();
				}
			} catch (Exception e) {
				System.out.println(size);
			}
		}

		public Worker combine(Worker o) {
			for (var entry : o.mapMediciones.entrySet()) {
				var medicion = mapMediciones.get(entry.getKey());
				if (medicion == null) {
					mapMediciones.put(entry.getKey(), entry.getValue());
				} else {
					medicion.combine(entry.getValue());
				}
			}
			return this;
		}

		@Override
		public String toString() {
			Comparator<Map.Entry<String, IntSummaryStatistics>> comparar = (a, b) -> {
				return a.getKey().compareTo(b.getKey());
			};
			var result = mapMediciones.entrySet().stream()//
					.map(e -> {
						var estacion = new String(e.getKey().array(), 0, e.getKey().limit());
						return Map.entry(estacion, e.getValue());
					})//
					.sorted(comparar)//
					.map(e -> {
						var m = e.getValue();
						return "%s=%.1f/%.1f/%.1f".formatted(e.getKey(), m.getMin() * 0.1f, m.getAverage() * 0.1f,
								m.getMax() * 0.1f);
					})//
					.collect(Collectors.joining(", ", "{", "}"));
			return result;
		}
	}
}
