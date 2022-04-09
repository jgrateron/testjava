package com.fresco;

import java.util.stream.Stream;

public class RomanToInt {

	public static void main(String[] args) {
		assertEquals(romanToInt("XXX"), 30);
		assertEquals(romanToInt("VII"), 7);
		assertEquals(romanToInt("CLXVIII"), 168);
		assertEquals(romanToInt("IV"), 4);
		assertEquals(romanToInt("MMXXII"), 2022);
		assertEquals(romanToInt("MCMXCIX"), 1999);
	}

	public static int romanToInt(String str) {
		record Roman(String ch, Integer cur, Integer old) {};
		return Stream.of(str.split(""))
		      .map(ch -> new Roman(ch, RoToIn(ch), RoToIn(ch)))
		      .reduce((ac, ro) -> {
		    	  if (ac.old >= ro.cur)
		    		  return new Roman(ro.ch, ac.cur + ro.cur, ro.old);
		    	  else
		    		  return new Roman(ro.ch, ac.cur - ac.old  + ro.cur - ac.old, ro.old);
		      })
		      .orElseThrow().cur;
	}

	public static int RoToIn(String c) {
		return switch (c) {
		case "I" -> 1;
		case "V" -> 5;
		case "X" -> 10;
		case "L" -> 50;
		case "C" -> 100;
		case "D" -> 500;
		case "M" -> 1000;
		default -> throw new IllegalArgumentException("Unexpected value: " + c);
		};
	}

	public static void assertEquals(int exp, int res) {
		if (exp == res)
			System.out.println("OK %d".formatted(exp));
		else
			System.err.println("ER %d %d".formatted(exp, res));
	}
}
