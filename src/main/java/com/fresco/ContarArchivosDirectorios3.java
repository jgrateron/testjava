package com.fresco;

import static java.nio.file.Files.isDirectory;
import static java.nio.file.Files.isSymbolicLink;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ContarArchivosDirectorios3 {

	private record Acum(int nroArc, int nroDir, int total) {
		@Override
		public final String toString() {
			return "[%d %d %d]".formatted(nroArc, nroDir, total);
		}
	}

	public static Acum contarArchivosDirectorio(Path path) throws IOException {
		var result = Files.list(path)//
				.peek(p -> {
					if (isDirectory(p) && !isSymbolicLink(p)) {
						try {
							var inter = contarArchivosDirectorio(p);
							System.out.println(p + " " + inter);
						} catch (IOException e) {
							System.out.println(e.getMessage());
						}
					}
				})//
				.map(p -> new Acum(Files.isRegularFile(p) ? 1 : 0, isDirectory(p) ? 1 : 0, 1))//
				.reduce(new Acum(0, 0, 0),
						(a, n) -> new Acum(a.nroArc + n.nroArc, a.nroDir + n.nroDir, a.total + n.total));
		return result;
	}

	public static void main(String[] args) throws IOException {
		String path = "/etc/";
		var result = contarArchivosDirectorio(Path.of(path));
		System.out.println(path + " " + result);
	}
}
