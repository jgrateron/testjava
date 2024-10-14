package com.fresco;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class GZip {

	public static void gzip(File input, File outPut) throws IOException {
		try (var fis = new FileInputStream(input); var fos = new FileOutputStream(outPut)) {
			try (var gzos = new GZIPOutputStream(fos)) {
				fis.transferTo(gzos);
			}
		}
	}

	public static void gunzip(File input, File outPut) throws IOException {
		try (var fis = new FileInputStream(input); var fos = new FileOutputStream(outPut)) {
			try (var gzis = new GZIPInputStream(fis)) {
				gzis.transferTo(fos);
			}
		}
	}

	public static void main(String[] args) throws IOException {
		var fileInput1 = new File("lorem.txt");
		var fileOut1 = new File("lorem.txt.gz");
		gzip(fileInput1, fileOut1);
		System.out.println("I: %6d O: %6d".formatted(fileInput1.length(), fileOut1.length()));

		var fileInput2 = new File("lorem.txt.gz");
		var fileOut2 = new File("lorem-2.txt");
		gunzip(fileInput2, fileOut2);
		System.out.println("I: %6d O: %6d".formatted(fileInput2.length(), fileOut2.length()));

		var porc = (double) (fileOut1.length() - fileInput1.length()) / fileInput1.length() * 100;
		System.out.println("New size: %.2f%%".formatted(porc));
	}
}
