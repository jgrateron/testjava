package com.fresco;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import com.fresco.parse.IProcessor;
import com.fresco.parse.Index;
import com.fresco.parse.SplitFile;

public class Desafio1BR7 {
	public static String FILE = "/opt/desafio/measurements.txt";

	public static void main(String[] args) throws IOException {
		Locale.setDefault(Locale.US);
		var ini = System.nanoTime();
		procesar();
		var end = System.nanoTime();
		var time = (end - ini) / 1_000_000;
		System.out.println(time);
	}

	private static void procesar() throws IOException {
		var result = SplitFile.split(FILE).parallelStream()//
				.collect(ResumenTemperatura::new, ResumenTemperatura::acumular, ResumenTemperatura::combinar);
		System.out.println("{" + result + "}");
	}

	public static class ResumenTemperatura implements IProcessor<ResumenTemperatura> {
		private int MAX_ESTACION = 100;
		private Index index;
		private ByteBuffer estacion;
		private int temperatura;
		private boolean esNegativo;
		private int hashCode;
		private int count;
		private Map<Index, Medicion> mapMediciones;

		public ResumenTemperatura() {
			this.mapMediciones = new HashMap<>();
			this.estacion = ByteBuffer.allocate(MAX_ESTACION);
			this.temperatura = 0;
			this.esNegativo = false;
			this.hashCode = 0;
			this.count = 0;
			this.index = new Index();
		}

		public void acumular(SplitFile sp) {
			sp.setProcessor(this);
			for (;;) {
				var continuar = sp.processLine();
				if (!continuar) {
					break;
				}
				proccessLine();
			}
		}

		@Override
		public boolean processLine(ByteBuffer byteBuffer, long size) {
			if (count < size) {
				var b = byteBuffer.get(count++);
				do {
					estacion.put(b);
					hashCode = hashCode * 31 + b;
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
					temperatura = (b - 48) * 10 + (c - 48);
				} else {
					var c = byteBuffer.get(count++);
					count++;// punto
					var d = byteBuffer.get(count++);
					temperatura = (b - 48) * 100 + (c - 48) * 10 + (d - 48);
				}
				count++;// enter
				return true;
			}
			return false;
		}

		private void proccessLine() {
			temperatura = esNegativo ? -temperatura : temperatura;
			estacion.flip();
			index.setByteBuffer(estacion, hashCode);
			var medicion = mapMediciones.get(index);
			if (medicion == null) {
				var newEstacion = ByteBuffer.allocate(MAX_ESTACION);
				newEstacion.put(estacion);
				newEstacion.flip();
				var newIndex = new Index(newEstacion, hashCode);
				medicion = new Medicion();
				mapMediciones.put(newIndex, medicion);
			}
			medicion.update(temperatura);
			temperatura = 0;
			esNegativo = false;
			hashCode = 0;
			estacion.clear();
		}

		public ResumenTemperatura combinar(ResumenTemperatura otro) {
			for (var entry : otro.mapMediciones.entrySet()) {
				var medicion = mapMediciones.get(entry.getKey());
				if (medicion == null) {
					mapMediciones.put(entry.getKey(), entry.getValue());
				} else {
					var otraMed = entry.getValue();
					medicion.merge(otraMed);
				}
			}
			return this;
		}

		@Override
		public String toString() {
			Comparator<Map.Entry<String, Medicion>> comparar = (a, b) -> {
				return a.getKey().compareTo(b.getKey());
			};
			var result = mapMediciones.entrySet().stream().map(e -> {
				var estacion = e.getKey().toString();
				return Map.entry(estacion, e.getValue());
			}).sorted(comparar).map(e -> {
				var m = e.getValue();
				return "%s=%.1f/%.1f/%.1f".formatted(e.getKey(), m.getMin(), m.getAverage(), m.getMax());
			}).collect(Collectors.joining(", "));
			return result;
		}
	}

	public static class Medicion {
		private int min;
		private int max;
		private long sum;
		private int count;

		public Medicion() {
			this.min = 1000;
			this.max = -1000;
			this.sum = 0;
			this.count = 0;
		}

		public void update(int temp) {
			this.min = Math.min(this.min, temp);
			this.max = Math.max(this.max, temp);
			this.sum += temp;
			this.count++;
		}

		public void merge(Medicion otraMed) {
			this.min = Math.min(this.min, otraMed.min);
			this.max = Math.max(this.max, otraMed.max);
			this.sum += otraMed.sum;
			this.count += otraMed.count;
		}

		public double getMin() {
			return min / 10.0;
		}

		public double getMax() {
			return max / 10.0;
		}

		public double getAverage() {
			return (sum / 10.0) / count;
		}
	}
}
