package com.fresco;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Lcd {

	public static Map<String, List<String>> alphabet = Map.ofEntries(Map.entry("0", List.of(" _ ", "| |", "|_|")),
			Map.entry("1", List.of("   ", "  |", "  |")), Map.entry("2", List.of(" _ ", " _|", "|_ ")),
			Map.entry("3", List.of(" _ ", " _|", " _|")), Map.entry("4", List.of("   ", "|_|", "  |")),
			Map.entry("5", List.of(" _ ", "|_ ", " _|")), Map.entry("6", List.of(" _ ", "|_ ", "|_|")),
			Map.entry("7", List.of(" _ ", "  |", "  |")), Map.entry("8", List.of(" _ ", "|_|", "|_|")),
			Map.entry("9", List.of(" _ ", "|_|", "  |")), Map.entry(".", List.of("   ", "   ", "  .")),
			Map.entry(":", List.of("   ", " . ", " . ")), Map.entry(" ", List.of("   ", "   ", "   ")),
			Map.entry("J", List.of("   ", "  |", "|_|")), Map.entry("A", List.of(" _ ", "|_|", "| |")),
			Map.entry("V", List.of("   ", "| /", "|/ ")), Map.entry("M", List.of(" _ ", "|||", "|||")),
			Map.entry("P", List.of(" _ ", "|_|", "|  ")));

	public static String textToLCD(String text) {
		var listChar = List.of(text.split(""));
		return IntStream.rangeClosed(0, 2)//
				.mapToObj(i -> listChar.stream()//
						.map(c -> alphabet.get(c))//
						.filter(Objects::nonNull)//
						.map(l -> l.get(i))//
						.collect(Collectors.joining(" ")))//
				.collect(Collectors.joining("\n"));
	}

	public static void main(String[] args) {
		System.out.println(textToLCD("3.141592653589793"));
		System.out.println(textToLCD("1234567890"));
		System.out.println(textToLCD("02:51 PM"));
		System.out.println(textToLCD("JAVA"));
	}
}


