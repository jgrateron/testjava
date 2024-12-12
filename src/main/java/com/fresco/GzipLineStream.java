package com.fresco;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Stream;
import java.util.zip.GZIPInputStream;

public class GzipLineStream {

	public static void main(String[] args) throws IOException {
		try (var fis = new FileInputStream("lorem.txt.gz")) {
			gzipLines(fis)
					.forEach(s -> {
						System.out.println(s);
					});
		}
	}

	public static Stream<String> gzipLines(InputStream inputStream) throws IOException {
		var gzipInputStream = new GZIPInputStream(inputStream);
		var reader = new InputStreamReader(gzipInputStream, "UTF-8");
		var bufferedReader = new BufferedReader(reader);
		return bufferedReader.lines();
	}
}
