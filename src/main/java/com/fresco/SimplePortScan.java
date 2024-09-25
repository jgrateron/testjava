package com.fresco;

import java.io.IOException;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.List;
import java.util.function.Function;

public class SimplePortScan {
	public static List<Integer> PORTS = List.of(21, 22, 23, 25, 80, 135, 161, 389, 443, 445, 5432, 8080);
	public static String HOST = "192.168.122.11";

	public record PortResult(int port, String status) {
		public final String toString() {
			return "Port: %5d, Status: %s".formatted(port, status);
		}
	}

	public static void main(String[] args) {
		System.out.println("Starting port scan");
		Function<Integer, PortResult> scanPort = p -> {
			try (var sock = new Socket()) {
				sock.connect(new InetSocketAddress(HOST, p), 250);
				return new PortResult(p, "Open");
			} catch (ConnectException e) {
				return new PortResult(p, "Close");
			} catch (IOException e) {
				return new PortResult(p, "Filtered");
			}
		};
		var listPorts = PORTS.stream().parallel()//
				.map(scanPort).toList();
		listPorts.forEach(System.out::println);
		System.out.println("End port scan");
	}
}




