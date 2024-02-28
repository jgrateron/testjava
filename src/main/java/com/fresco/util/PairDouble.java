package com.fresco.util;

import java.util.Objects;

public class PairDouble {
	private double value1;
	private double value2;

	public PairDouble(double value1, double value2) {
		super();
		this.value1 = value1;
		this.value2 = value2;
	}

	public double getValue1() {
		return value1;
	}

	public void setValue1(double value1) {
		this.value1 = value1;
	}

	public double getValue2() {
		return value2;
	}

	public void setValue2(double value2) {
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
		PairDouble other = (PairDouble) obj;
		return Double.doubleToLongBits(value1) == Double.doubleToLongBits(other.value1)
				&& Double.doubleToLongBits(value2) == Double.doubleToLongBits(other.value2);
	}

	@Override
	public String toString() {
		return "PairDouble [value1=" + value1 + ", value2=" + value2 + "]";
	}
	
	public static PairDouble of(double value1, double value2) {
		return new PairDouble(value1, value2);
	}
}
