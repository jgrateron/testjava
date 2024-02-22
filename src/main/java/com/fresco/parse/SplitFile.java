package com.fresco.parse;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel.MapMode;
import java.util.ArrayList;
import java.util.List;

public class SplitFile {

	private MappedByteBuffer byteBuffer;
	private ByteBuffer[] records;
	private ByteBuffer[] line;
	private long size;
	private long count;
	private int maxRecords;
	private int posRecord;
	private byte separator;

	private SplitFile() {
	}

	private SplitFile(MappedByteBuffer byteBuffer, long size, int maxRecords, byte separator) {
		this.byteBuffer = byteBuffer;
		this.size = size;
		this.count = 0;
		this.maxRecords = maxRecords;
		this.records = new ByteBuffer[maxRecords];
		this.line = new ByteBuffer[maxRecords];
		for (int i = 0; i < maxRecords; i++) {
			records[i] = ByteBuffer.allocate(255);
		}
		this.posRecord = 0;
		this.separator = separator;
	}

	public static List<SplitFile> split(String file, int maxRecords, char separator) throws IOException {
		var listSplit = new ArrayList<SplitFile>();
		var fileContribuyente = new RandomAccessFile(new File(file), "r");
		var channel = fileContribuyente.getChannel();
		var cores = Runtime.getRuntime().availableProcessors();
		var length = channel.size();
		var sizeChunk = length / cores;
		var offset = 0l;
		while (offset < length) {
			var remaining = length - offset;
			var chunk = remaining > sizeChunk ? sizeChunk : remaining;
			var byteBuffer = channel.map(MapMode.READ_ONLY, offset, chunk);
			do {
				chunk--;
			} while (byteBuffer.get((int) chunk) != '\n');
			chunk++;
			var worker = new SplitFile(byteBuffer, chunk, maxRecords, (byte) separator);
			listSplit.add(worker);
			offset += chunk;
		}
		return listSplit;
	}

	public ByteBuffer[] getLine() {
		for (int i = 0; i < maxRecords; i++) {
			records[i].clear();
		}
		posRecord = 0;
		while (count < size) {
			var b = byteBuffer.get();
			count++;
			if (b == '\n') {
				for (int i = 0; i < maxRecords; i++) {
					records[i].flip();
					line[i] = records[i];
				}
				return line;
			}
			if (b == separator) {
				posRecord += 1;
				continue;
			}
			if (posRecord < maxRecords) {
				records[posRecord].put(b);
			}
		}
		return null;
	}
}
