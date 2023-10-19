package com.fresco;

import java.util.Arrays;

public class Bin2Text {

	public static void main(String[] args) {
		var binary = "0110011001100101011011000110100101111010001000000110001101110101011011010111000001101100011001010110000111000011101100010110111101110011";
		var cad = binaryToText(binary);
		System.out.println(cad);
	}

	public static String convert(String input) {
		StringBuilder sb = new StringBuilder();
		for (var s : input.split(" ")) {
			sb.append((char) Integer.parseInt(s, 2));
		}
		return sb.toString();
	}

	public static String binaryToText(String binary) {
		return Arrays.stream(binary.replaceAll(" ", "").split("(?<=\\G.{8})"))/* regex to split the bits array by 8 */
				.map(eightBits -> (char) Integer.parseInt(eightBits, 2)) // transforma
				.collect(StringBuilder::new, StringBuilder::append, StringBuilder::append) // collect
				.toString();
	}

}
