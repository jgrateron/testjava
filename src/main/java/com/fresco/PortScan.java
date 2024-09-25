package com.fresco;

import java.io.IOException;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.List;
import java.util.function.IntFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PortScan {
	public static int MAXSHOWPORTS = 10;
	public static String HOST = "192.168.122.11";

	public record PortResult(int port, String status) {
		public final String toString() {
			return "Port: %5d, Status: %s".formatted(port, status);
		}
	}

	public static void main(String[] args) {
		System.out.println("Starting deep port scan");
		IntFunction<PortResult> scanPort = p -> {
			try (var sock = new Socket()) {
				sock.connect(new InetSocketAddress(HOST, p), 250);
				return new PortResult(p, "Open");
			} catch (ConnectException e) {
				return new PortResult(p, "Close");
			} catch (IOException e) {
				return new PortResult(p, "Filtered");
			}
		};
		var groupByStatus = IntStream.rangeClosed(21, 8080).parallel()//
				.mapToObj(scanPort)//
				.collect(Collectors.groupingBy(PortResult::status));
		groupByStatus.entrySet().stream()//
				.filter(e -> e.getValue().size() > MAXSHOWPORTS)//
				.forEach(e -> System.out.println("Not show: %d %s ports".formatted(e.getValue().size(), e.getKey())));
		groupByStatus.entrySet().stream()//
				.filter(e -> e.getValue().size() <= MAXSHOWPORTS)//
				.map(e -> e.getValue()).flatMap(List::stream)//
				.forEach(System.out::println);
		System.out.println("End deep port scan");
	}
}



