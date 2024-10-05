package com.fresco;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpClient.Redirect;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class DownloadFile4 {
	public static String[] URL_DOWNLOADS = { "http://192.168.1.95/libro.txt", "http://192.168.1.95/librobig.txt",
			"http://localhost:8080/libro.txt", "http://localhost:9090/libro.txt" };

	public static void metodoRedirect() throws URISyntaxException, IOException, InterruptedException {
		var request = HttpRequest.newBuilder()//
				.uri(new URI(URL_DOWNLOADS[3]))//
				.timeout(Duration.ofSeconds(10))// .
				.build();
		try (var httpClient = HttpClient.newBuilder().followRedirects(Redirect.NORMAL).build()) {
			var response = httpClient.send(request, BodyHandlers.ofByteArray());
			if (response.statusCode() != 200) {
				throw new IOException(new String(response.body()));
			}
			var baos = new ByteArrayOutputStream();
			baos.write(response.body());
			System.out.println("size total: " + baos.size());
		}
	}

	public static void main(String[] args) {
		createServer();
		try {
			metodoRedirect();
		} catch (URISyntaxException | IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}

	private static void createServer() {
		try {
			var server = HttpServer.create(new InetSocketAddress("localhost", 9090), 0);
			server.createContext("/", new RootHttpHandler());
			server.createContext("/Descargas", new DownloadHttpHandler());
			server.start();
			System.out.println("Listen server ");
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}

	static class RootHttpHandler implements HttpHandler {

		@Override
		public void handle(HttpExchange exchange) throws IOException {
			var path = exchange.getRequestURI().toString();
			var userHome = System.getProperty("user.home");
			var file = new File(userHome + "/" + path);
			var newHeaders = new HashMap<String, String>();
			newHeaders.put("Content-Type", "text/plain");
			if (file.exists()) {
				newHeaders.put("Location", "/Descargas" + path);
				writeResponse(exchange, 301, "The document has moved".getBytes(), newHeaders);
			} else {
				var response = path + " not found";
				writeResponse(exchange, 404, response.getBytes(), newHeaders);
			}
		}

		public void writeResponse(HttpExchange he, int responseCode, byte[] response, Map<String, String> headers)
				throws IOException {
			for (var e : headers.entrySet()) {
				he.getResponseHeaders().add(e.getKey(), e.getValue());
			}
			he.sendResponseHeaders(responseCode, response.length);
			var os = he.getResponseBody();
			os.write(response);
			os.close();
		}
	}

	static class DownloadHttpHandler implements HttpHandler {

		@Override
		public void handle(HttpExchange exchange) throws IOException {
			var path = exchange.getRequestURI().toString();
			var userHome = System.getProperty("user.home");
			var file = new File(userHome + "/" + path);
			var newHeaders = new HashMap<String, String>();
			newHeaders.put("Content-Type", "text/plain");
			if (file.exists()) {
				try (var in = new FileInputStream(file)) {
					writeResponse(exchange, 200, in, newHeaders);
				}
			} else {
				var response = path + " not found";
				writeResponse(exchange, 404, response.getBytes(), newHeaders);
			}
		}

		public void writeResponse(HttpExchange he, int responseCode, byte[] response, Map<String, String> headers)
				throws IOException {
			for (var e : headers.entrySet()) {
				he.getResponseHeaders().add(e.getKey(), e.getValue());
			}
			he.sendResponseHeaders(responseCode, response.length);
			var os = he.getResponseBody();
			os.write(response);
			os.close();
		}

		public void writeResponse(HttpExchange he, int responseCode, InputStream response, Map<String, String> headers)
				throws IOException {
			for (var e : headers.entrySet()) {
				he.getResponseHeaders().add(e.getKey(), e.getValue());
			}
			int length = response.available();
			he.sendResponseHeaders(responseCode, length);
			try (var os = he.getResponseBody()) {
				response.transferTo(os);
				os.flush();
			}
		}
	}
}
