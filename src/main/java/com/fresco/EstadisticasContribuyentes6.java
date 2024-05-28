package com.fresco;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

import com.fresco.parse.Entero;
import com.fresco.parse.IProcessor;
import com.fresco.parse.Index;
import com.fresco.parse.SplitFile;

public class EstadisticasContribuyentes6 {

	public static String FILE = "contribuyentes.txt";

	public static void main(String[] args) throws IOException {
		var ini = System.nanoTime();
		procesar();
		var end = System.nanoTime();
		var time = (end - ini) / 1_000_000;
		System.out.println(time);
	}

	private static void procesar() throws IOException {
		var result = SplitFile.split(FILE).parallelStream()//
				.collect(ResumenContribuyente::new, ResumenContribuyente::acumular, ResumenContribuyente::combinar);
		result.show();
	}

	public static class ResumenContribuyente implements IProcessor<ResumenContribuyente> {
		private static final byte separator = '|';
		private int count;
		private int countBytes;
		private Map<Index, Entero> groupEstado;
		private Map<Index, Entero> groupCondicion;
		private Index index;
		private ByteBuffer estado;
		private ByteBuffer condicion;
		private int hashCode1;
		private int hashCode2;

		public ResumenContribuyente() {
			count = 0;
			countBytes = 0;
			groupEstado = new HashMap<Index, Entero>();
			groupCondicion = new HashMap<Index, Entero>();
			index = new Index();
			estado = ByteBuffer.allocate(2);
			condicion = ByteBuffer.allocate(2);
		}

		@Override
		public void acumular(SplitFile sf) {
			sf.setProcessor(this);
			for (;;) {
				var continuar = sf.processLine();
				if (!continuar) {
					break;
				}
				acumular();
			}
		}

		@Override
		public boolean processLine(ByteBuffer bb, long size) {
			if (countBytes < size) {
				var b = bb.get(countBytes++);
				while (b != separator) {
					b = bb.get(countBytes++);
				}
				estado.clear();
				hashCode1 = 0;
				b = bb.get(countBytes++);
				do {
					estado.put(b);
					hashCode1 = hashCode1 * 31 + b;
					b = bb.get(countBytes++);
				} while (b != separator);
				estado.flip();
				condicion.clear();
				hashCode2 = 0;
				b = bb.get(countBytes++);
				do {
					condicion.put(b);
					hashCode2 = hashCode2 * 31 + b;
					b = bb.get(countBytes++);
				} while (b != separator);
				condicion.flip();
				countBytes++;// enter
				return true;
			}
			return false;
		}

		public void acumular() {
			count++;
			index.setByteBuffer(estado, hashCode1);
			var cuantosXEstado = groupEstado.get(index);
			if (cuantosXEstado == null) {
				var newEstado = ByteBuffer.allocate(estado.limit());
				newEstado.put(estado);
				newEstado.flip();
				var newIndex = new Index(newEstado, hashCode1);
				cuantosXEstado = new Entero();
				groupEstado.put(newIndex, cuantosXEstado);
			}
			cuantosXEstado.inc();

			index.setByteBuffer(condicion, hashCode2);
			var cuantosXCondicion = groupCondicion.get(index);
			if (cuantosXCondicion == null) {
				var newCondicion = ByteBuffer.allocate(condicion.limit());
				newCondicion.put(condicion);
				newCondicion.flip();
				var newIndex = new Index(newCondicion, hashCode2);
				cuantosXCondicion = new Entero();
				groupCondicion.put(newIndex, cuantosXCondicion);
			}
			cuantosXCondicion.inc();
		}

		@Override
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
			System.out.println(count);
			System.out.println("-".repeat(40));
		}
	}
}
