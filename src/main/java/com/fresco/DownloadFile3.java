package com.fresco;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class DownloadFile3 {

	public static String[] URL_DOWNLOADS = { "http://192.168.1.95/libro.txt", "http://192.168.1.95/librobig.txt",
			"http://localhost:8080/libro.txt", "http://localhost:9090/libro.txt" };

	public static void metodoGzip() throws URISyntaxException, IOException, InterruptedException {
		var request = HttpRequest.newBuilder()//
				.uri(new URI(URL_DOWNLOADS[3]))//
				.timeout(Duration.ofSeconds(10))//
				.header("Accept-Encoding", "gzip").build();
		try (var httpClient = HttpClient.newHttpClient()) {
			var response = httpClient.send(request, BodyHandlers.ofByteArray());
			if (response.statusCode() != 200) {
				throw new IOException(new String(response.body()));
			}
			var isGzip = response.headers()//
					.firstValue("content-encoding")//
					.filter(s -> s.equals("gzip"));
			var baos = new ByteArrayOutputStream();
			if (isGzip.isPresent()) {
				System.out.println("size recv:  " + response.body().length);
				var bais = new ByteArrayInputStream(response.body());
				var gis = new GZIPInputStream(bais);
				gis.transferTo(baos);
			} else {
				baos.write(response.body());
			}
			System.out.println("size total: " + baos.size());
		}
	}

	public static void main(String[] args) {
		createServer();
		try {
			metodoGzip();
		} catch (URISyntaxException | IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}

	private static void createServer() {
		try {
			var server = HttpServer.create(new InetSocketAddress("localhost", 9090), 0);
			server.createContext("/", new RootHttpHandler());
			server.start();
			System.out.println("Listen server ");
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}

	static class RootHttpHandler implements HttpHandler {

		@Override
		public void handle(HttpExchange exchange) throws IOException {
			var headers = exchange.getRequestHeaders();
			var isGzip = Optional.ofNullable(headers.getFirst("accept-encoding"))//
					.filter(enconding -> "gzip".equals(enconding));
			var path = exchange.getRequestURI().toString();
			var userHome = System.getProperty("user.home");
			var file = new File(userHome + "/" + path);
			var newHeaders = new HashMap<String, String>();
			newHeaders.put("Content-Type", "text/plain");
			if (file.exists()) {
				try (var in = new FileInputStream(file)) {
					if (isGzip.isPresent()) {
						var baos = new ByteArrayOutputStream();
						try (var gzipOS = new GZIPOutputStream(baos)) {
							in.transferTo(gzipOS);
						}
						newHeaders.put("content-encoding", "gzip");
						writeResponse(exchange, 200, baos.toByteArray(), newHeaders);
					} else {
						writeResponse(exchange, 200, in, newHeaders);
					}
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
