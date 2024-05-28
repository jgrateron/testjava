package com.fresco.util;

import java.util.Objects;

public class PairLong {
	private long value1;
	private long value2;

	public PairLong(long value1, long value2) {
		super();
		this.value1 = value1;
		this.value2 = value2;
	}

	public long getValue1() {
		return value1;
	}

	public void setValue1(long value1) {
		this.value1 = value1;
	}

	public long getValue2() {
		return value2;
	}

	public void setValue2(long value2) {
		this.value2 = value2;
	}

	@Override
	public int hashCode() {
		return Objects.hash(value1, value2);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PairLong other = (PairLong) obj;
		return value1 == other.value1 && value2 == other.value2;
	}

	@Override
	public String toString() {
		return "PairLong [value1=" + value1 + ", value2=" + value2 + "]";
	}
	
	public static PairLong of(long value1, long value2) {
		return new PairLong(value1, value2);
	}
}
