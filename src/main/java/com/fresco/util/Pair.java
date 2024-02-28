package com.fresco.util;

import java.util.Objects;

public class Pair<T> {
	private T value1;
	private T value2;

	public Pair(T value1, T value2) {
		super();
		this.value1 = value1;
		this.value2 = value2;
	}

	public T getValue1() {
		return value1;
	}

	public void setValue1(T value1) {
		this.value1 = value1;
	}

	public T getValue2() {
		return value2;
	}

	public void setValue2(T value2) {
		this.value2 = value2;
	}

	@Override
	public int hashCode() {
		return Objects.hash(value1, value2);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pair other = (Pair) obj;
		return Objects.equals(value1, other.value1) && Objects.equals(value2, other.value2);
	}

	@Override
	public String toString() {
		return "Pair [value1=" + value1 + ", value2=" + value2 + "]";
	}

	public static <T> Pair<T> of(T value1, T value2) {
		return new Pair<T>(value1, value2);
	}
}
