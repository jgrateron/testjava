package com.fresco;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URI;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class SubDomains {

	public static String DOMAIN = ".google.com";
	public static String Pattern = "%s%s";
	public static String URL[] = { "https://github.com/rbsec/dnscan/raw/refs/heads/master/subdomains-100.txt",
			"https://github.com/rbsec/dnscan/raw/refs/heads/master/subdomains-1000.txt" };

	public static void main(String[] args) throws IOException {
		var is = URI.create(URL[0]).toURL().openConnection().getInputStream();
		var subDomains = new String(is.readAllBytes());
		var listDomains = subDomains.lines().parallel()//
				.filter(Predicate.not(String::isEmpty))//
				.map(subdom -> {
					var host = Pattern.formatted(subdom, DOMAIN);
					try {
						var ipTarget = InetAddress.getByName(host);
						System.out.println("FOUND: %s".formatted(host + " (" + ipTarget.getHostAddress() + ")"));
						return Map.entry(true, host);
					} catch (UnknownHostException e) {
						System.out.println("ERROR: %s".formatted(host));
						return Map.entry(false, host);
					}
				})//
				.collect(Collectors.groupingBy(Map.Entry::getKey));
		System.out.println("-".repeat(50));
		listDomains.forEach((found, listHosts) -> {
			System.out.println("%s %d".formatted(found ? "FOUND" : "ERROR", listHosts.size()));
		});
	}
}
