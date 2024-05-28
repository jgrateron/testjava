package com.fresco;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel.MapMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.fresco.parse.Entero;
import com.fresco.parse.Index;

public class EstadisticasContribuyentes4 {
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
		result.show();
	}

	public static class Worker extends Thread {
		private static int MAX_RECORDS = 3;
		private ByteBuffer byteBuffer;
		private ByteBuffer[] records;
		private int posRecord;
		private ResumenContribuyente resumen;
		private long size;

		public Worker(ByteBuffer byteBuffer, long size) {
			this.byteBuffer = byteBuffer;
			this.size = size;
			this.resumen = new ResumenContribuyente();
			this.records = new ByteBuffer[MAX_RECORDS];
			for (int i = 0; i < MAX_RECORDS; i++) {
				records[i] = ByteBuffer.allocate(20);
			}
			this.posRecord = 0;
		}

		@Override
		public void run() {
			int count = 0;
			while (count < size) {
				var b = byteBuffer.get();
				switch (b) {
				case '\n':
					resumen.accumulator(records);
					posRecord = 0;
					break;
				case '|':
					posRecord += 1;
					break;
				default:
					if (posRecord > 0) {
						records[posRecord].put(b);
					}
					break;
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
		private Map<Index, Entero> groupEstado;
		private Map<Index, Entero> groupCondicion;
		private Index index;

		public ResumenContribuyente() {
			count = 0;
			groupEstado = new HashMap<Index, Entero>();
			groupCondicion = new HashMap<Index, Entero>();
			index = new Index();
		}

		public void accumulator(ByteBuffer[] records) {
			count++;
			var estado = records[1];
			estado.flip();
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
			estado.clear();
			var condicion = records[2];
			condicion.flip();
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
			condicion.clear();
		}

		public ResumenContribuyente combiner(ResumenContribuyente other) {
			System.out.println("combinar " + this);
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
