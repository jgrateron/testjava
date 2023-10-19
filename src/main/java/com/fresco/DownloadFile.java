package com.fresco;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class DownloadFile {

	public static void main(String[] args) throws IOException {
		var url = new URL("https://worldradiohistory.com/Archive-Electronics/90s/92/Electronics-1992-01.pdf");
		var resource = url.getFile();
		var array = resource.split("/");
		if (array.length > 0) {
			System.out.println(array[array.length - 1]);
		}
		var connection = url.openConnection();
		try (var inputStream = connection.getInputStream()) {
			var file = new File("/tmp/Electronics-1992-01.pdf");
			try (var outputStream = new FileOutputStream(file)) {
				byte[] buffer = new byte[8192];
				int bytesRead;
				int downloadedBytes = 0;
				long fileSize = connection.getContentLengthLong();
				while ((bytesRead = inputStream.read(buffer)) != -1) {
					outputStream.write(buffer, 0, bytesRead);
					downloadedBytes += bytesRead;
					System.out.println("Downloaded " + (downloadedBytes * 100) / fileSize + "%");
				}
			}
		}
		System.out.println("File downloaded successfully.");
	}

}
