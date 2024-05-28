package com.fresco.util;

import java.util.Objects;

public class PairInt {
	private int value1;
	private int value2;

	public PairInt(int value1, int value2) {
		super();
		this.value1 = value1;
		this.value2 = value2;
	}

	public int getValue1() {
		return value1;
	}

	public void setValue1(int value1) {
		this.value1 = value1;
	}

	public int getValue2() {
		return value2;
	}

	public void setValue2(int value2) {
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
		PairInt other = (PairInt) obj;
		return value1 == other.value1 && value2 == other.value2;
	}

	@Override
	public String toString() {
		return "PairInt [value1=" + value1 + ", value2=" + value2 + "]";
	}

	public static PairInt of(int value1, int value2) {
		return new PairInt(value1, value2);
	}
}
