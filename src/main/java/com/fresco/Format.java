package com.fresco;

import static java.text.MessageFormat.format;

public class Format {
	public static void main(String[] args) {
		System.out.println(format("{0} {1}","Maria", "Jose"));
		System.out.println("%s %s".formatted("Maria", "Jose"));
	}
}
