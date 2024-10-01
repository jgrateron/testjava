package com.fresco;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Advice {
	public record Slip(String id, String advice) {
	}

	public record AdviceSlip(Slip slip) {
	}

	public static void main(String[] args) throws MalformedURLException, IOException {
		var con = URI.create("https://api.adviceslip.com/advice").toURL().openConnection();
		var is = con.getInputStream();
		var baos = new ByteArrayOutputStream();
		is.transferTo(baos);
		var response = baos.toString();
		var mapper = new ObjectMapper();
		var adviceSlip = mapper.readValue(response, AdviceSlip.class);
		System.out.println("Advice: %s".formatted(adviceSlip.slip.advice));
	}
}


