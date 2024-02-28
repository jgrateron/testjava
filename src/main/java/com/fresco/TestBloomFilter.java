package com.fresco;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import com.fresco.parse.BloomFilter;
import com.fresco.parse.Index;
import com.fresco.parse.SplitFile;

public class TestBloomFilter {
	private static final String FILE = "./measurements300.txt";

	public static void main(String[] args) throws IOException {
		Locale.setDefault(Locale.US);
		var ini = System.nanoTime();
		procesar();
		var end = System.nanoTime();
		var time = (end - ini) / 1_000_000;
		System.out.println(time);
	}

	private static void procesar() throws IOException {
		var result = SplitFile.split(FILE, 2, ';').parallelStream()//
				.collect(ResumenTemperatura::new, ResumenTemperatura::acumular, ResumenTemperatura::combinar);
		System.out.println("{" + result + "}");
	}

	static class ResumenTemperatura {
		private Map<Index, Medicion> groupEstacion;
		private BloomFilter<Index> filter;
		private Index index;

		@SuppressWarnings("unchecked")
		public ResumenTemperatura() {
			groupEstacion = new TreeMap<Index, Medicion>();
			index = new Index();
			filter = new BloomFilter<>(1024, s -> s.hashCode());
		}

		public void acumular(SplitFile sp) {
			for (;;) {
				var linea = sp.getLine();
				if (linea == null) {
					break;
				}
				procesarLinea(linea);
			}
		}

		public void procesarLinea(ByteBuffer[] linea) {
			var estacion = linea[0];
			var temperatura = linea[1];
			index.setByteBuffer(estacion);
			if (filter.contains(index)) {
				var medicion = groupEstacion.get(index);
				if (medicion != null) {
					medicion.acumular(toInt(temperatura));
				} else {
					var newEstacion = ByteBuffer.allocate(estacion.limit());
					newEstacion.put(estacion);
					newEstacion.flip();
					medicion = new Medicion();
					medicion.acumular(toInt(temperatura));
					groupEstacion.put(new Index(newEstacion), medicion);
					filter.add(index);
				}				
			}else {
				var newEstacion = ByteBuffer.allocate(estacion.limit());
				newEstacion.put(estacion);
				newEstacion.flip();
				var medicion = new Medicion();
				medicion.acumular(toInt(temperatura));
				groupEstacion.put(new Index(newEstacion), medicion);
				filter.add(index);
			}
			
		}

		public ResumenTemperatura combinar(ResumenTemperatura otro) {
			for (var e : otro.groupEstacion.entrySet()) {
				var medicion = groupEstacion.get(e.getKey());
				if (medicion == null) {
					groupEstacion.put(e.getKey(), e.getValue());
				} else {
					medicion.combine(e.getValue());
				}
			}
			return this;
		}

		public int toInt(ByteBuffer temp) {
			boolean esNegativo = temp.get(0) == '-';
			int pos = esNegativo ? 1 : 0;
			int result = temp.get(pos + 1) == '.' ? (temp.get(pos) - 48) * 10 + temp.get(pos + 2) - 48
					: (temp.get(pos) - 48) * 100 + (temp.get(pos + 1) - 48) * 10 + (temp.get(pos + 3) - 48);
			return esNegativo ? -result : result;
		}

		@Override
		public String toString() {
			return groupEstacion.entrySet().stream()//
					.map(e -> Map.entry(e.getKey().toString(), e.getValue()))//
					.sorted(Map.Entry.comparingByKey())//
					.map(e -> e.getKey() + "=" + e.getValue().toString())//
					.collect(Collectors.joining(", "));
		}
	}

	static class Medicion {
		private int min;
		private int max;
		private int sum;
		private int count;

		public Medicion() {
			min = Integer.MAX_VALUE;
			max = Integer.MIN_VALUE;
			sum = 0;
			count = 0;
		}

		public void acumular(int temperatura) {
			min = temperatura < min ? temperatura : min;
			max = temperatura > max ? temperatura : max;
			sum += temperatura;
			count++;
		}

		public void combine(Medicion otro) {
			min = otro.min < min ? otro.min : min;
			max = otro.max > max ? otro.max : max;
			sum += otro.sum;
			count += otro.count;
		}

		@Override
		public String toString() {
			var fmin = min / 10.0;
			var avg = (sum / 10.0) / count;
			var fmax = max / 10.0;
			return "%.1f/%.1f/%.1f".formatted(fmin, avg, fmax);
		}
	}

}
