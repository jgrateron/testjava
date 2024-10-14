package com.fresco;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URI;
import java.net.UnknownHostException;
import java.util.stream.Collectors;

public class Subdominios {
	record Dominio(boolean existe, String name) {
	}

	public static void main(String[] args) throws IOException {
		var con = URI.create("https://github.com/rbsec/dnscan/raw/refs/heads/master/subdomains-100.txt").toURL()
				.openConnection();
		var is = con.getInputStream();
		var baos = new ByteArrayOutputStream();
		is.transferTo(baos);
		var listado = baos.toString().lines().parallel()//
				.map(subdom -> {
					var name = subdom + ".google.com";
					try {
						var ipTarget = InetAddress.getByName(name);
						name = name + " (" + ipTarget.getHostAddress() + ")";
						return new Dominio(true, name);
					} catch (UnknownHostException e) {
						return new Dominio(false, name);
					}
				})//
				.peek(dominio -> {
					if (dominio.existe) {
						System.out.println("FOUND: %s".formatted(dominio.name));
					} else {
						System.out.println("ERROR: %s".formatted(dominio.name));
					}
				}).collect(Collectors.groupingBy(Dominio::existe));

		listado.forEach((encontrado, dominios) -> {
			System.out.println(encontrado + " " + dominios.size());
		});
	}
}
