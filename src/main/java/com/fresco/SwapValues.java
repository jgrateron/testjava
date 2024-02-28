package com.fresco;

public class SwapValues {

	public static void swap(String a, String b) {
		var x = a;
		a = b;
		b = x;
	}

	public static void swap(Integer x, Integer y) {
		var z = x;
		x = y;
		y = z;
	}

	public static void main(String[] args) {

		var a = "a";
		var b = "b";
		swap(a, b);
		System.out.println(a + " " + b);

		var x = Integer.valueOf(1);
		var y = Integer.valueOf(2);
		swap(x, y);
		System.out.println(x + " " + y);
	}

}
