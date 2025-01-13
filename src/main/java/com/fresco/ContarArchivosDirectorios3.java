package com.fresco;

import static java.nio.file.Files.isDirectory;
import static java.nio.file.Files.isSymbolicLink;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ContarArchivosDirectorios3 {

	private record Acum(int nroArc, int nroDir, int total) {

		public Acum combine(Acum o) {
			return new Acum(nroArc + o.nroArc, nroDir + o.nroDir, total + o.total);
		}

		@Override
		public final String toString() {
			var strNroArc = String.valueOf(nroArc);
			var strNroDir = String.valueOf(nroDir);
			var strTotal = String.valueOf(total);
			return "[%4s, %4s, %4s]".formatted(strNroArc, strNroDir, strTotal);
		}
	}

	public static Acum contarArchivosDirectorio(Path path) throws IOException {
		var result = Files.list(path)//
				.sorted()
				.peek(p -> {
					if (isDirectory(p) && !isSymbolicLink(p)) {
						try {
							var inter = contarArchivosDirectorio(p);
							System.out.println(inter + " " + p);
						} catch (AccessDeniedException e) {
							// ignore
						} catch (IOException e) {
							throw new UncheckedIOException(e);
						}
					}
				})//
				.map(p -> new Acum(Files.isRegularFile(p) ? 1 : 0, isDirectory(p) ? 1 : 0, 1))//
				.reduce(new Acum(0, 0, 0), Acum::combine);
		return result;
	}

	public static void main(String[] args) throws IOException {
		String path = "/var/log";
		var result = contarArchivosDirectorio(Path.of(path));
		System.out.println(result + " " + path);
	}
}
