package com.fresco;

import java.util.Map;
import java.util.stream.Stream;

public class RomanToInt3 {

	public static Map<String, Integer> mapRom = Map.of("I", 1, "V", 5, "X", 10, "L", 50, "C", 100, "D", 500, "M", 1000);

	record Roman(String ch, Integer cur, Integer old) {
	}

	public static int romanToInt(String str) {
		return Stream.of(str.toUpperCase().split(""))//
				.map(ch -> new Roman(ch, mapRom.get(ch), mapRom.get(ch)))//
				.reduce((ac, ro) -> ac.old >= ro.cur ? //
						new Roman(ro.ch, ac.cur + ro.cur, ro.old) : //
						new Roman(ro.ch, ac.cur - ac.old + ro.cur - ac.old, ro.old))//
				.get().cur;
	}

	public static void main(String[] args) {
		Stream.of("CLXVIII", "MCMXCIV", "MMXXIV", "MMMDCCCLXXXVIII")//
				.forEach(r -> {
					System.out.println("%-16s: %4s".formatted(r, romanToInt(r)));
				});
	}
}
