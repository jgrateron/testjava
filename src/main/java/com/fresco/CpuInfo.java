package com.fresco;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class CpuInfo {

	public static void main(String[] args) throws IOException {
		var cpuInfo = Files.lines(Path.of("/proc/cpuinfo"))//
				.map(l -> {
					var ar = l.split(":");
					return ar.length == 2 ? Map.entry(ar[0].trim().toLowerCase(), ar[1].trim()) : null;
				}).filter(Objects::nonNull)//
				.toList();

		var mapCpuInfo = cpuInfo.stream()//
				.collect(Collectors.groupingBy(Map.Entry::getKey));

		mapCpuInfo.entrySet().stream()//
				.sorted(Map.Entry.comparingByKey())//
				.forEach(e -> {
					var set = e.getValue().stream().map(Map.Entry::getValue).collect(Collectors.toSet());
					var join = String.join(", ", set);
					System.out.println("%-20s: %s".formatted(e.getKey(), join));
				});
	}
}
