package com.fresco;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class CpuInfo {

	public static void test1() throws IOException {
		var mapCpuInfo = Files.lines(Path.of("/proc/cpuinfo"))//
				.map(l -> {
					var ar = l.split(":");
					return ar.length == 2 ? Map.entry(ar[0].trim().toLowerCase(), ar[1].trim()) : null;
				}).filter(Objects::nonNull)//
				.collect(Collectors.groupingBy(Map.Entry::getKey,
						Collectors.mapping(Map.Entry::getValue, Collectors.toSet())));

		mapCpuInfo.entrySet().stream()//
				.sorted(Map.Entry.comparingByKey())//
				.forEach(e -> {
					var join = String.join(", ", e.getValue());
					System.out.println("%-20s: %s".formatted(e.getKey(), join));
				});

	}

	public static void test2() throws IOException {
		var mapCpuInfo = Files.lines(Path.of("/proc/cpuinfo"))//
				.map(l -> {
					var ar = l.split(":");
					return ar.length == 2 ? Map.entry(ar[0].trim().toLowerCase(), ar[1].trim()) : null;
				}).filter(Objects::nonNull)//
				.collect(Collectors.groupingBy(Map.Entry::getKey,
						Collectors.mapping(Map.Entry::getValue, Collectors.toList())));

		mapCpuInfo.entrySet().stream()//
				.sorted(Map.Entry.comparingByKey())//
				.forEach(e -> {
					var join = String.join(", ", e.getValue().stream().distinct().toList());
					System.out.println("%-20s: %s".formatted(e.getKey(), join));
				});
	}

	public static void main(String[] args) throws IOException {
		test1();
		test2();
	}
}
