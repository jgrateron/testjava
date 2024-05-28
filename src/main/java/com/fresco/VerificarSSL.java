package com.fresco;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class VerificarSSL {

    public static void main(String[] args) throws InterruptedException {
        if (args.length == 0) {
            System.err.println("Falta la url del archivo");
        } else {
            System.setProperty("javax.net.debug", "ssl,handshake");
            try {
                var url = new URL(args[0]);
                var array = url.getFile().split("/");
                var downloadFile = array.length > 0 ? array[array.length - 1] : "file.tmp";
                System.out.println("download: " + downloadFile);
                var connection = url.openConnection();
                try (var inputStream = connection.getInputStream()) {
                    var file = new File(downloadFile);
                    try (var outputStream = new FileOutputStream(file)) {
                        inputStream.transferTo(outputStream);
                    }
                }
                System.out.println("end download");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
