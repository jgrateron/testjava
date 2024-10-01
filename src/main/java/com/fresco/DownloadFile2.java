package com.fresco;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.file.Path;

public class DownloadFile2 {
	public static String URL_DOWNLOAD = "https://www.worldradiohistory.com/Archive-Electronics/90s/92/Electronics-1992-01.pdf";

	public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException {
		var request = HttpRequest.newBuilder()//
				.uri(new URI(URL_DOWNLOAD))//
				.build();
		try (var httpClient = HttpClient.newHttpClient()) {
			var response = httpClient.send(request, BodyHandlers.ofFile(Path.of("/tmp/salida.pdf")));
			System.out.println(response.headers());
		}
	}
}
