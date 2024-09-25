package com.fresco;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HexFormat;

public class HexvsBase64 {

	public static void main(String[] args) throws UnsupportedEncodingException {
		var texto = "https://www.google.com";
		
		var hexEnc = HexFormat.of().formatHex(texto.getBytes());
		System.out.println(hexEnc);
		var base64Enc = Base64.getEncoder().encodeToString(texto.getBytes());
		System.out.println(base64Enc);
		var urlEnc = URLEncoder.encode(texto,StandardCharsets.UTF_8);
		System.out.println(urlEnc);
	}
}
