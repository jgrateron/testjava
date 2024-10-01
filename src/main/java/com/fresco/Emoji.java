package com.fresco;

import java.util.stream.Stream;

public class Emoji {
	public static int egg = 0x1F95A;
	public static int chicken = 0x1F414;

	public static void main(String[] args) {
		Stream.of(egg, chicken)//
				.sorted()//
				.map(c -> Character.toString(c))//
				.forEach(System.out::println);
	}
}



