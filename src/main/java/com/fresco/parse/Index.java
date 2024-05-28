package com.fresco.parse;

import java.nio.ByteBuffer;

public class Index implements Comparable<Index> {
	private ByteBuffer buffer;
	private int hashCode;
	private int size;

	public Index() {
	}

	public Index(ByteBuffer buffer) {
		this.buffer = buffer;
		this.size = buffer.limit();
		this.hashCode = buffer.hashCode();
	}

	public Index(ByteBuffer buffer, int hashCode) {
		this.buffer = buffer;
		this.size = buffer.limit();
		this.hashCode = hashCode;
	}

	public void setByteBuffer(ByteBuffer buffer) {
		this.buffer = buffer;
		this.size = buffer.limit();
		this.hashCode = buffer.hashCode();
	}

	public void setByteBuffer(ByteBuffer buffer, int hashCode) {
		this.buffer = buffer;
		this.size = buffer.limit();
		this.hashCode = hashCode;
	}

	@Override
	public int hashCode() {
		return hashCode;
	}

	@Override
	public boolean equals(Object obj) {
		var otro = (Index) obj;
		return size == otro.size && buffer.get(0) == otro.buffer.get(0)
				&& buffer.get(size - 1) == otro.buffer.get(size - 1);
	}

	@Override
	public String toString() {
		return new String(buffer.array(), 0, buffer.limit());
	}

	@Override
	public int compareTo(Index o) {
		return Integer.compare(hashCode, o.hashCode);
	}
}