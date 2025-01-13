package com.fresco;

import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Acronyms {

	public static void main(String[] args) {

		var texts = """
				Global Positioning System
				National Aeronautics Space Administration
				Hyper Text Markup Language
				Uniform Resource Locator
				Carbon copy
				Laugh out loud
				Chief Executive Officer
				British Broadcasting Corporation
				""";
		texts.lines()
				.forEach(text -> {
					var acron = Stream.of(text.split(" "))
							.filter(Predicate.not(String::isBlank))
							.map(s -> s.substring(0, 1))
							.collect(Collectors.joining())
							.toUpperCase();
					System.out.println("%-5s: %s".formatted(acron, text));
				});
	}
}
