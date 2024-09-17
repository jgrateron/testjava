package com.fresco;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;

public class DownloadFile {

	public static String URL = "https://worldradiohistory.com/Archive-Electronics/90s/92/Electronics-1992-01.pdf";
	public static byte[] buffer = new byte[8192];

	public static void main(String[] args) throws IOException {
		var url = URI.create(URL).toURL();
		var array = url.getFile().split("/");
		var fileDest = "/tmp/" + (array.length > 0 ? array[array.length - 1] : "temp.out");
		try (var is = url.openConnection().getInputStream()) {
			try (var outputStream = new FileOutputStream(fileDest)) {
				var downloadedBytes = is.transferTo(outputStream);
				System.out.println("File downloaded successfully. " + downloadedBytes);
			}
		}

	}
}
