package com.fresco;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestZero {

	public static boolean isZero(String number) {
		if (number.isEmpty()) {
			return false;
		}
		for (var c : number.toCharArray()) {
			if (c != '0') {
				return false; 
			}
		}
		return true;
	}
	
	public static void main(String[] args) {
		assertEquals(true, isZero("0"));
		assertEquals(true, isZero("00"));
		assertEquals(true, isZero("000"));
		assertEquals(true, isZero("0000"));
		assertEquals(true, isZero("00000"));
		assertEquals(true, isZero("000000"));
		assertEquals(true, isZero("0000000"));
		assertEquals(true, isZero("00000000"));
		assertEquals(false, isZero(""));
		assertEquals(false, isZero("1"));
		assertEquals(false, isZero("01"));
		assertEquals(false, isZero("001"));
		assertEquals(false, isZero("0001"));
		assertEquals(false, isZero("00001"));
		assertEquals(false, isZero("000001"));
		assertEquals(false, isZero("0000001"));
		assertEquals(false, isZero("00000001"));
		System.out.println("end");
	}

}
