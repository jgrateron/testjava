package com.fresco.parse;

import java.nio.ByteBuffer;

public class Index implements Comparable<Index>{
	private ByteBuffer buffer;
	private int hashCode;

	public Index() {

	}

	public Index(ByteBuffer buffer) {
		this.buffer = buffer;
		this.hashCode = buffer.hashCode();
	}

	public void setByteBuffer(ByteBuffer buffer) {
		this.buffer = buffer;
		this.hashCode = buffer.hashCode();
	}
	
	@Override
	public int hashCode() {
		return hashCode;
	}

	@Override
	public boolean equals(Object obj) {
		var otro = (Index) obj;
		return buffer.equals(otro.buffer);
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