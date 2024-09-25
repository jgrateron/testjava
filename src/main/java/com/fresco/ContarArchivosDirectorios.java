package com.fresco;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ContarArchivosDirectorios {

	private record Acum(int nroArc, int nroDir, int total) {
	};

	public static void main(String[] args) throws IOException {
		String path = "/home/jairo/";

		var result = Files.list(Paths.get(path))
				.map(p -> new Acum(Files.isRegularFile(p) ? 1 : 0, Files.isDirectory(p) ? 1 : 0, 1))
				.reduce(new Acum(0, 0, 0),
						(a, n) -> new Acum(a.nroArc + n.nroArc, a.nroDir + n.nroDir, a.total + n.total));

		System.out.println("Nro Archivos: %d".formatted(result.nroArc));
		System.out.println("Nro de directorios: %d".formatted(result.nroDir));
		System.out.println("Total: %d".formatted(result.total));
	}
}
