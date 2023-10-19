package com.fresco;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Random;

public class PwdHash {
	private byte salt[];
	private byte hash[];

	public PwdHash(String pwd) {
		salt = new byte[20];
		new Random().nextBytes(salt);
		hash = generateHash(pwd);
	}

	public PwdHash(byte data[]) {
		if (data.length != 40) {
			throw new IllegalArgumentException();
		}
		salt = new byte[20];
		hash = new byte[20];
		System.arraycopy(data, 0, salt, 0, salt.length);
		System.arraycopy(data, salt.length, hash, 0, hash.length);
	}

	public boolean checkpw(String password) {
		var hashNew = generateHash(password);
		return Arrays.equals(hash, hashNew);
	}

	private byte[] generateHash(String password) {
		var pwd = password.getBytes();
		var data = new byte[pwd.length + salt.length];
		System.arraycopy(pwd, 0, data, 0, pwd.length);
		System.arraycopy(salt, 0, data, pwd.length, salt.length);
		return sha1(data);
	}

	private byte[] sha1(byte data[]) {
		try {
			var md = MessageDigest.getInstance("SHA-1");
			md.update(data, 0, data.length);
			var sha1hash = md.digest();
			return sha1hash;

		} catch (NoSuchAlgorithmException en) {
		}
		return new byte[0];
	}

	public static byte[] decode(String src) {
		return Base64.getDecoder().decode(src);
	}

	public String byteArrayToB64(byte[] a) {
		return Base64.getEncoder().encodeToString(a);
	}

	@Override
	public String toString() {
		var data = new byte[salt.length + hash.length];
		System.arraycopy(salt, 0, data, 0, salt.length);
		System.arraycopy(hash, 0, data, salt.length, hash.length);
		return byteArrayToB64(data);
	}

	public static void main(String[] args) {
		var pwdHash = new PwdHash("1234");
		System.out.println(pwdHash);
		System.out.println(pwdHash.checkpw("1234"));

		pwdHash = new PwdHash(PwdHash.decode("KJNAcBz5bY+pTfM6DESbmfX4KHqHTKkI/AAGVE50yglTl6CVH5DnmA=="));
		System.out.println(pwdHash);
		System.out.println(pwdHash.checkpw("1234"));
		
		pwdHash = new PwdHash(PwdHash.decode("sMzvF5z7Yqp5Y25o7aHPdySF9uIzqgX91/ODKY6bH9Jk1ttiQwRDyA=="));
		System.out.println(pwdHash);
		System.out.println(pwdHash.checkpw("1234"));

	}
}
