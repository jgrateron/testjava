package com.fresco.util;

import java.util.Objects;

public class Tuple<T, U> {
	private T value1;
	private U value2;

	public Tuple(T value1, U value2) {
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

	public U getValue2() {
		return value2;
	}

	public void setValue2(U value2) {
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
		Tuple other = (Tuple) obj;
		return Objects.equals(value1, other.value1) && Objects.equals(value2, other.value2);
	}

	@Override
	public String toString() {
		return "Tuple [value1=" + value1 + ", value2=" + value2 + "]";
	}

	public static <T, U> Tuple<T, U> of(T value1, U value2) {
		return new Tuple<T, U>(value1, value2);
	}
}
