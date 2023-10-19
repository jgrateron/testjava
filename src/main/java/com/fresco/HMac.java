package com.fresco;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class HMac {
	
	public static String hmacWithJava(String algorithm, String data, byte[] sharedSecret)
			  throws NoSuchAlgorithmException, InvalidKeyException {
			    SecretKeySpec secretKeySpec = new SecretKeySpec(sharedSecret, algorithm);
			    Mac mac = Mac.getInstance(algorithm);
			    mac.init(secretKeySpec);
			    return Base64.getEncoder().encodeToString(mac.doFinal(data.getBytes()));
			}
	
	public static void main(String[] args) throws InvalidKeyException, NoSuchAlgorithmException {
		SecureRandom random = new SecureRandom();
		byte[] sharedSecret = new byte[32];
		random.nextBytes(sharedSecret);
		var hmac = hmacWithJava("HmacSHA256","Jairo Grateron",sharedSecret);
		System.out.println(hmac);
	}
}
