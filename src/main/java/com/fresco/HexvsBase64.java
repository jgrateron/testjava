package com.fresco;

import java.util.Base64;
import java.util.HexFormat;

public class HexvsBase64 {

	public static void main(String[] args) {
		var texto = "https://www.google.com";
		
		var hex = HexFormat.of().formatHex(texto.getBytes());
		var base64 = Base64.getEncoder().encodeToString(texto.getBytes());
		System.out.println(hex);
		System.out.println(base64);
	}

}
