package com.fresco;

import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Capitalisation {

	public static String names = """
			skye steuber
			francina nolan phd
			mrs. althea mcglynn
			porfirio swift
			mr. birgit funk
			theresia tillman dds
			miss meda o'kon
			otha hyatt
			anthony windler md
			freeda stanton
			minda douglas
			frederic koch
			saul ferry
			pamala dibbert iv
			romana goldner sr.
			edelmira mraz
			mrs. aurelia hodkiewicz
			kasha pollich
			sharri abernathy
									""";

	public static void main(String[] args) {
		names.lines()
				.map(name -> Stream.of(name.split(" "))
						.filter(Predicate.not(String::isBlank))
						.map(s -> s.length() > 3 ? s.substring(0, 1).toUpperCase() + s.substring(1) : s)
						.collect(Collectors.joining(" ")))
				.forEach(name -> {
					System.out.println(name);
				});
	}
}
