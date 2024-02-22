package com.fresco;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import com.fresco.parse.Entero;
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
		var result = SplitFile.split(FILE, 2, '|').parallelStream()//
				.map(sp -> {
					var resumen = new ResumenCpe();
					for (;;) {
						var linea = sp.getLine();
						if (linea == null) {
							break;
						}
						resumen.acumular(linea);
					}
					return resumen;
				})//
				.collect(ResumenCpe::new, ResumenCpe::combinar, ResumenCpe::combinar);

		System.out.println(result);
		System.out.println(result.count);
	}

	static class ResumenCpe {
		private int count;
		private Map<Index, Entero> groupCpe;
		private Index index;

		public ResumenCpe() {
			count = 0;
			groupCpe = new HashMap<Index, Entero>();
			index = new Index();
		}

		public void acumular(ByteBuffer[] linea) {
			count++;
			var codCpe = linea[1];
			index.setByteBuffer(codCpe);
			var cantCpe = groupCpe.get(index);
			if (cantCpe == null) {
				var newCodCpe = ByteBuffer.allocate(codCpe.limit());
				newCodCpe.put(codCpe);
				newCodCpe.flip();
				groupCpe.put(new Index(newCodCpe), new Entero(1));
			} else {
				cantCpe.inc(1);
			}
		}

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
	}
}
