package com.fresco;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;

public class WeatherReport {

	public static void main(String[] args) throws MalformedURLException, IOException {
		var con = URI.create("https://wttr.in/Caracas").toURL().openConnection();
		con.setRequestProperty("User-Agent", "curl");
		var is = con.getInputStream();
		var baos = new ByteArrayOutputStream();
		is.transferTo(baos);
		System.out.println(baos.toString());
	}
}

