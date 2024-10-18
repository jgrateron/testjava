package com.fresco;

import java.io.IOException;
import java.io.StringReader;

public class Text2Bin {
	public static String text = """
			The most important thing is constant practice! The more you code, the faster you will learn and the better your code will be.""";

	public static void main(String[] args) throws IOException {
		var sb = new StringBuffer();
		for (var b : text.getBytes()) {
			var unsignedInt = Byte.toUnsignedInt(b);
			var bin = Integer.toBinaryString(unsignedInt);
			sb.append("%s%s ".formatted("0".repeat(8 - bin.length()), bin));
		}
		System.out.println(sb.toString());
		System.out.println();
		var blockBits = new char[8 * 8 + 8];
		var reader = new StringReader(sb.toString());
		for (var i = reader.read(blockBits); i != -1; i = reader.read(blockBits)) {
			System.out.println(new String(blockBits, 0, i));
		}
	}
}
