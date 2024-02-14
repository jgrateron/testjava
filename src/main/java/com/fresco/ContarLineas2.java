package com.fresco;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;

public class ContarLineas2 {
	public static String FILE = "measurements200.txt";
	public static long enter = compilePattern((byte) '\n');
	public static int MAX;
	
	public static void main(String[] args) throws FileNotFoundException, IOException, InterruptedException {
		var ini = System.currentTimeMillis();
		int cores = Runtime.getRuntime().availableProcessors();
		try (var file = new RandomAccessFile(FILE, "r")) {
			var listWorkers = new ArrayList<Worker>();
			var channel = file.getChannel();
			var remaining = channel.size();
			MAX = (int) (remaining / cores);
			var offset = 0l;
			while (remaining > 0) {
				var size = remaining > MAX ? MAX : remaining;
				remaining = remaining - size;
				var byteBuffer = channel.map(FileChannel.MapMode.READ_ONLY, offset, size);
				offset += size;
				var worker = new Worker(byteBuffer);
				worker.start();
				listWorkers.add(worker);
			}
			var count = 0l;
			var lineas = 0l;
			for (var worker : listWorkers) {
				worker.join();
				count += worker.getCount();
				lineas += worker.getLineas();
			}
			System.out.println(" " + lineas + " " + count + " " + FILE);
		}
		var time = System.currentTimeMillis() - ini;
		System.out.println(time / 1000.0);
	}

	static class Worker extends Thread {
		private final MappedByteBuffer byteBuffer;
		private long count = 0l;
		private long lineas = 0l;

		public Worker(MappedByteBuffer byteBuffer) {
			this.byteBuffer = byteBuffer;
		}

		@Override
		public void run() {
			while (byteBuffer.hasRemaining()) {
				if (byteBuffer.remaining() >= Long.BYTES) {
					var b = byteBuffer.getLong();
					var r = firstInstance(b, enter);
					if (r < Long.BYTES) {
						lineas++;
					}
					count += Long.BYTES;
				} else {
					var b = byteBuffer.get();
					if (b == '\n') {
						lineas++;
					}
					count++;
				}
			}
		}

		public long getCount() {
			return count;
		}

		public long getLineas() {
			return lineas;
		}
	}

	public static long compilePattern(byte byteToFind) {
		long pattern = byteToFind & 0xFFL;
		return pattern | (pattern << 8) | (pattern << 16) | (pattern << 24) | (pattern << 32) | (pattern << 40)
				| (pattern << 48) | (pattern << 56);
	}

	public static int firstInstance(long word, long pattern) {
		long input = word ^ pattern;
		long tmp = (input & 0x7F7F7F7F7F7F7F7FL) + 0x7F7F7F7F7F7F7F7FL;
		tmp = ~(tmp | input | 0x7F7F7F7F7F7F7F7FL);
		return Long.numberOfLeadingZeros(tmp) >>> 3;
	}
}
