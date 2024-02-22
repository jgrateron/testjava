package com.fresco.parse;

public class Entero implements Comparable<Entero> {
	private int value;

	public Entero(int value) {
		this.value = value;
	}

	public void inc(int value) {
		this.value += value;
	}

	public void inc(Entero entero) {
		this.value += entero.value;
	}

	public int intValue() {
		return value;
	}

	@Override
	public int compareTo(Entero o) {
		return Integer.compare(value, o.value);
	}

	@Override
	public String toString() {
		return Integer.toString(value);
	}
}