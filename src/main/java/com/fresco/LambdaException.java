package com.fresco;

import java.util.function.Function;
import java.util.stream.Stream;

public class LambdaException {
	public static void main(String[] args) {
		Function<String, Class<?>> convertir = (s) -> {
			try {
				return Class.forName(s);
			} catch (ClassNotFoundException e) {
				return null;
			}
		};
		Stream.of("java.lang.String", "ch.frankel.blog.Dummy", "java.util.ArrayList")
		      .map(convertir)
			  .forEach(System.out::println);
	}
}
