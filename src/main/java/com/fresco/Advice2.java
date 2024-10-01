package com.fresco;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Advice2 {
	public record Slip(String id, String advice) {
	}

	public record AdviceSlip(Slip slip) {
	}

	public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException {
		var request = HttpRequest.newBuilder()//
				.uri(new URI("https://api.adviceslip.com/advice"))//
				.build();
		try (var httpClient = HttpClient.newHttpClient()) {
			var response = httpClient.send(request, BodyHandlers.ofString());
			var mapper = new ObjectMapper();
			var adviceSlip = mapper.readValue(response.body(), AdviceSlip.class);
			System.out.println("Advice: %s".formatted(adviceSlip.slip.advice));
		}
	}
}
