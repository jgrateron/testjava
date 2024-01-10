package com.fresco;

public class Recursion {

	public void test()
	{
		test();
		System.out.println("Rescursion!!!");
	}
	public static void main(String[] args) {
		new Recursion().test();
	}

}
