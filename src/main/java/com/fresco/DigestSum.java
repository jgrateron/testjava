package com.fresco;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;

public class DigestSum {

	public static void main(String[] args) throws NoSuchAlgorithmException, IOException {
		var file = new File("/bin/bash");
		showDigestHex("MD5", calDigest(MessageDigest.getInstance("MD5"), file));
		showDigestHex("SHA1", calDigest(MessageDigest.getInstance("SHA1"), file));
		showDigestHex("SHA256", calDigest(MessageDigest.getInstance("SHA256"), file));
		showDigestHex("SHA384", calDigest(MessageDigest.getInstance("SHA384"), file));
		showDigestHex("SHA512", calDigest(MessageDigest.getInstance("SHA512"), file));
	}

	public static byte[] calDigest(MessageDigest msgDigest, File file) throws FileNotFoundException, IOException {
		var b = new byte[8192];
		try (var fis = new FileInputStream(file)) {
			for (var read = fis.read(b); read != -1; read = fis.read(b)) {
				msgDigest.update(b, 0, read);
			}
			return msgDigest.digest();
		}
	}

	private static void showDigestHex(String algo, byte[] digest) {
		var hex = HexFormat.of().formatHex(digest);
		System.out.println("%6s: %s".formatted(algo, hex));
	}
}

