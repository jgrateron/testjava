package com.fresco;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FindPidProcess {

	public static String readContentFile(Path path) {
		try {
			return Files.readString(path);
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	public static boolean processExistsByName(Path path, String nameProcess) {
		try {
			return Files.list(path)
					.filter(p -> p.toString().endsWith("comm"))
					.map(p -> readContentFile(p))
					.map(content -> content.contains(nameProcess))
					.findFirst().orElse(false);
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	public static void main(String[] args) throws IOException {
		var searchProcess = "chrome";
		var listProcess = Files.list(Path.of("/proc"))
				.filter(Files::isDirectory)
				.filter(p -> processExistsByName(p, searchProcess))
				.map(p -> p.getFileName().toString())
				.toList();
		listProcess.forEach(System.out::println);
		System.out.println("-".repeat(10));
		System.out.println("Total: " + listProcess.size());
	}
}

