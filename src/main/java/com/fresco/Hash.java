package com.fresco;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.util.HexFormat;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class Hash {

    public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchProviderException {
        Security.addProvider(new BouncyCastleProvider());
        var digest = MessageDigest.getInstance("MD5", "BC");
        var password = "123456";
        while (password.length() != 16) {
            password = password + " ";
        }
        System.out.println("|" + password + "|");
        System.out.println(password.length());
        var encodedhash = digest.digest(password.getBytes());
        System.out.println(HexFormat.ofDelimiter(":").formatHex(encodedhash).toUpperCase());
    }
}
