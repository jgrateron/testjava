package com.fresco;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Consejo {

	public record Slip(String id, String advice) {
	}

	public record AdviceSlip(Slip slip) {
	}

	public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException {
		var client = HttpClient.newHttpClient();
		var request = HttpRequest.newBuilder()//
				.uri(new URI("https://api.adviceslip.com/advice"))//
				.build();
		var response = client.send(request, BodyHandlers.ofString());
		if (response.statusCode() == 200) {
			var mapper = new ObjectMapper();
			var adviceSlip = mapper.readValue(response.body(), AdviceSlip.class);
			System.out.println(String.format("id: %s ", adviceSlip.slip.id));
			System.out.println(String.format("advice: %s", adviceSlip.slip.advice));
		} else {
			System.out.println("Error: " + response.statusCode());
		}
	}
}

