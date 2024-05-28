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
	@SuppressWarnings("rawtypes")
	private IProcessor processor;

	public SplitFile(MappedByteBuffer byteBuffer, long size) {
		this.byteBuffer = byteBuffer;
		this.size = size;
		this.count = 0;
		this.maxRecords = 0;
		this.posRecord = 0;
	}

	public void setProcessor(@SuppressWarnings("rawtypes") IProcessor processor) {
		this.processor = processor;
	}

	public boolean processLine() {
		return processor.processLine(byteBuffer, size);
	}

	public void setSeparator(char c) {
		this.separator = (byte) c;
	}

	public void setMaxRecord(int maxRecords) {
		this.maxRecords = maxRecords;
		this.records = new ByteBuffer[maxRecords];
		this.line = new ByteBuffer[maxRecords];
		for (int i = 0; i < maxRecords; i++) {
			records[i] = ByteBuffer.allocate(255);
		}
	}

	@Deprecated
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

	public static List<SplitFile> split(String file) throws IOException {
		var listSplit = new ArrayList<SplitFile>();
		try (var fileContribuyente = new RandomAccessFile(new File(file), "r")) {
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
				var worker = new SplitFile(byteBuffer, chunk);
				listSplit.add(worker);
				offset += chunk;
			}
		}
		return listSplit;
	}
}
