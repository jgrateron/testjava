package com.fresco;

import static java.nio.file.Files.isDirectory;
import static java.nio.file.Files.isSymbolicLink;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.Files;
import java.nio.file.Path;

public class CountFilesDirectories {

	private record Accumulator(int fileCount, int directoryCount) {

		public Accumulator combine(Accumulator other) {
			return new Accumulator(fileCount + other.fileCount, directoryCount + other.directoryCount);
		}

		@Override
		public final String toString() {
			var strFileCount = String.valueOf(fileCount);
			var strDirectoryCount = String.valueOf(directoryCount);
			var strTotalCount = String.valueOf(fileCount + directoryCount);
			return "[%6s, %6s, %6s]".formatted(strFileCount, strDirectoryCount, strTotalCount);
		}
	}

	public static Accumulator countFilesDirectory(Path path) throws IOException {
		return Files.list(path)
				.sorted()
				.map(p -> {
					if (isDirectory(p) && !isSymbolicLink(p)) {
						var accDir = new Accumulator(0, 1);
						try {
							var resultDir = countFilesDirectory(p);
							System.out.println(resultDir + " " + p);
							accDir = resultDir.combine(accDir);
						} catch (AccessDeniedException e) {
							// ignore
						} catch (IOException e) {
							throw new UncheckedIOException(e);
						}
						return accDir;
					}
					return new Accumulator(1, 0);
				})
				.reduce(new Accumulator(0, 0), Accumulator::combine);
	}

	public static void main(String[] args) throws IOException {
		var path = "/usr/lib/jvm";
		var result = countFilesDirectory(Path.of(path));
		System.out.println(result + " " + path);
	}
}