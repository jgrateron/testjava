package com.fresco;

public class Print {

	public void print(Integer i) {
		System.out.println("Integer");
	}

	public void print(int i) {
		System.out.println("int");
	}

	public void print(long i) {
		System.out.println("long");
	}

	public void print(int... i) {
		System.out.println("int ...");
	}

	public static void main(String[] args) {
		new Print().print(10);
	}

}
