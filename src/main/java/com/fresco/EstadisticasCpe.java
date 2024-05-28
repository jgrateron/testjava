package com.fresco;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import com.fresco.parse.Entero;
import com.fresco.parse.IProcessor;
import com.fresco.parse.Index;
import com.fresco.parse.SplitFile;

public class EstadisticasCpe {
	public static String FILE = "cpe.txt";

	public static void main(String[] args) throws IOException {
		var ini = System.nanoTime();
		procesar();
		var end = System.nanoTime();
		var time = (end - ini) / 1_000_000;
		System.out.println(time);
	}

	public static void procesar() throws IOException {
		var result = SplitFile.split(FILE).parallelStream()//
				.collect(ResumenCpe::new, ResumenCpe::acumular, ResumenCpe::combinar);

		System.out.println(result);
		System.out.println(result.count);
	}

	static class ResumenCpe implements IProcessor<ResumenCpe> {
		private static byte SEPARATOR = '|';
		private int count;
		private int countRead;
		private Map<Index, Entero> groupCpe;
		private Index index;
		private ByteBuffer codCpe;
		private int hashCodCpe;

		public ResumenCpe() {
			count = 0;
			countRead = 0;
			groupCpe = new HashMap<Index, Entero>();
			index = new Index();
			codCpe = ByteBuffer.allocate(2);
			hashCodCpe = 0;
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

		public void acumular() {
			count++;
			codCpe.flip();
			index.setByteBuffer(codCpe, hashCodCpe);
			var cantCpe = groupCpe.get(index);
			if (cantCpe == null) {
				var newCodCpe = ByteBuffer.allocate(codCpe.limit());
				newCodCpe.put(codCpe);
				newCodCpe.flip();
				cantCpe = new Entero();
				groupCpe.put(new Index(newCodCpe, hashCodCpe), cantCpe);
			}
			cantCpe.inc(1);
		}

		@Override
		public ResumenCpe combinar(ResumenCpe otro) {
			count += otro.count;
			for (var entry : otro.groupCpe.entrySet()) {
				var cantCpe = groupCpe.get(entry.getKey());
				if (cantCpe == null) {
					groupCpe.put(entry.getKey(), entry.getValue());
				} else {
					cantCpe.inc(entry.getValue());
				}
			}
			return this;
		}

		@Override
		public String toString() {
			return groupCpe.entrySet().stream()//
					.sorted(Map.Entry.comparingByValue())//
					.map(e -> {
						var codCpe = e.getKey().toString();
						return codCpe + ": " + e.getValue().toString();
					})//
					.collect(Collectors.joining("\n"));
		}

		@Override
		public boolean processLine(ByteBuffer byteBuffer, long size) {
			if (countRead < size) {
				var b = byteBuffer.get(countRead++);
				while (b != SEPARATOR) {
					b = byteBuffer.get(countRead++);
				}
				codCpe.clear();
				hashCodCpe = 0;
				b = byteBuffer.get(countRead++);
				do {
					codCpe.put(b);
					hashCodCpe = hashCodCpe * 31 + b;
					b = byteBuffer.get(countRead++);
				} while (b != SEPARATOR);
				while (b != '\n') {
					b = byteBuffer.get(countRead++);
				}
				return true;
			}
			return false;
		}
	}
}
