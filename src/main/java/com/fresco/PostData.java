package com.fresco;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.stream.Collectors;

public class PostData {

	public static String urlEncodeUTF8(String value) {
		return URLEncoder.encode(value, StandardCharsets.UTF_8);
	}

	public static void main(String[] args) {
		var data = new HashMap<String, String>();
		data.put("number", "123456");
		data.put("type", "issue");
		data.put("href", "http://api.example.com");
		data.put("email", "janesmith@example.com");

		var dataPost = data.entrySet().stream()//
				.map(e -> e.getKey() + "=" + urlEncodeUTF8(e.getValue()))//
				.collect(Collectors.joining("&"));
		System.out.println();
		System.out.println(dataPost);
	}
}
