package com.fresco;

import java.io.ByteArrayOutputStream;
import java.util.stream.IntStream;

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

	public static void combiner(ByteArrayOutputStream a, ByteArrayOutputStream b) {
	}

	public static String binaryToText(String binary) {
		var partition = 8;
		var groups = binary.length() / partition;
		var res = IntStream.range(0, groups)
				.mapToObj(i -> {
					var ini = i * partition;
					var end = ini + partition;
					return binary.substring(ini, end);
				})
				.map(s -> Integer.parseInt(s, 2))
				.collect(ByteArrayOutputStream::new, ByteArrayOutputStream::write, Bin2Text::combiner);
		return new String(res.toByteArray());
	}

}
