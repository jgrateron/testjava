package com.fresco;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HexFormat;

public class EncoderDecoders {

	public static void main(String[] args) throws UnsupportedEncodingException {
		var texto = "https://www.google.com";

		System.out.println("-".repeat(50));
		System.out.println("Encoders");
		var hexEnc = HexFormat.of().formatHex(texto.getBytes());
		System.out.println("%-7s: %s".formatted("Hex", hexEnc));
		var base64Enc = Base64.getEncoder().encodeToString(texto.getBytes());
		System.out.println("%-7s: %s".formatted("B64", base64Enc));
		var urlEnc = URLEncoder.encode(texto, StandardCharsets.UTF_8);
		System.out.println("%-7s: %s".formatted("UrlEnc", urlEnc));

		System.out.println("-".repeat(50));
		System.out.println("Decoders");
		var bytesHex = HexFormat.of().parseHex(hexEnc);
		System.out.println("%-7s: %s".formatted("Hex", new String (bytesHex)));
		var bytesB64 = Base64.getDecoder().decode(base64Enc);
		System.out.println("%-7s: %s".formatted("B64", new String(bytesB64)));
		var bytesUrl = URLDecoder.decode(urlEnc, StandardCharsets.UTF_8).getBytes();
		System.out.println("%-7s: %s".formatted("UrlEnc", new String(bytesUrl)));
	}
}



