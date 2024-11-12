package com.fresco;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.stream.Collectors;

public class LimitPrintStackTrace {

	private static void recursive(int i) {
		if (i < 0) {
			throw new RuntimeException("i < 0");
		}
		recursive(i - 1);
	}

	public static void main(String[] args) {
		try {
			recursive(100);
		} catch (Exception e) {
			var baos = new ByteArrayOutputStream();
			e.printStackTrace(new PrintStream(baos));
			var out = baos.toString().lines().limit(10).collect(Collectors.joining("\n"));
			System.err.println(out);
		}
	}
}
