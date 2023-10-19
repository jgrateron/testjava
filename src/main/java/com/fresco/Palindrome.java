package com.fresco;

public class Palindrome {
	public static void main(String[] args) {
		String cadena = "reconocer".toLowerCase();
		
		
		System.out.println(cadena.equals(new StringBuilder(cadena).reverse().toString()));
	}
}
