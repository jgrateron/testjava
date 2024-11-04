package com.fresco;

import java.net.URI;
import java.util.stream.Stream;

public class GetIpInternet {

	public static void main(String[] args) {
		Stream.of("http://ifconfig.me", "http://checkip.amazonaws.com",
				"http://ipecho.net/plain", "http://stappers.it/t")
				.forEach(url -> {
					try (var is = URI.create(url).toURL().openConnection().getInputStream()) {
						var ip = new String(is.readAllBytes());
						System.out.println("%s\n%s\n".formatted(url, ip));
					} catch (Exception e) {
						throw new RuntimeException(e.getMessage());
					}
				});
	}
}
