package com.fresco;

import java.io.IOException;
import java.net.ServerSocket;

public class WebServer {
	public static int CRLF = 218762506;// '\r\n\r\n'
	public static String response = """
			HTTP/1.1 200 OK
			Server: MyWebServer

			<H1>Hello %s</H1>
			""";

	public static void main(String[] args) throws IOException {
		try (var serverSocker = new ServerSocket(9999)) {
			for (;;) {
				var client = serverSocker.accept();
				var thread = new Thread(() -> {
					try {
						var request = new StringBuffer();
						var end = 0;
						do {
							var b = client.getInputStream().read();
							if (b == -1)
								break;
							request.append((char) b);
							end <<= 8;
							end |= b;
						} while (end != CRLF);
						var strRequest = request.toString();
						var userAgent = strRequest.lines()//
								.filter(s -> s.startsWith("User-Agent: "))//
								.map(s -> s.substring(12))//
								.findFirst().orElse("Unknown");
						client.getOutputStream().write(response.formatted(userAgent).getBytes());
						client.getOutputStream().flush();
						client.close();
					} catch (IOException e) {
						System.err.println(e.getMessage());
					}
				});
				thread.start();
			}
		}
	}
}
