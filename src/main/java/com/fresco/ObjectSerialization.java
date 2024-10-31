package com.fresco;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HexFormat;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectSerialization {

	public static String json = """
			{
				"name": "John Doe",
				"age" : 30,
				"birth_date" : "1980-01-01",
				"address" : {
					"street" : "123 Main St",
					"city" : "Springfield",
					"zip" : "12345"
				}
			}
			""";

	public record Address(String street, String city, String zip) implements Serializable {
	}

	public record Person(String name, int age, String birth_date, Address address) implements Serializable {
	}

	public static void main(String[] args) throws IOException {
		System.out.println();
		printHex(json.getBytes());
		System.out.println();
		var objectMapper = new ObjectMapper();
		var person = objectMapper.readValue(json, Person.class);
		var baos = new ByteArrayOutputStream();
		try (var oos = new ObjectOutputStream(baos)) {
			oos.writeObject(person);
		}
		printHex(baos.toByteArray());
	}

	public static void printHex(byte[] data) throws IOException {
		var buffer = new byte[16];
		var bis = new ByteArrayInputStream(data);
		for (var c = bis.read(buffer); c > 0; c = bis.read(buffer)) {
			var cad1 = IntStream.range(0, c)//
					.mapToObj(i -> {
						var hex = HexFormat.of().formatHex(buffer, i, i + 1);
						return hex + (i % 2 == 0 ? "" : " ");
					})//
					.collect(Collectors.joining());
			var cad2 = IntStream.range(0, c)//
					.map(i -> buffer[i])//
					.mapToObj(b -> b > 32 && b < 127 ? String.valueOf((char) b) : ".")//
					.collect(Collectors.joining());
			System.out.println("%-40s %s".formatted(cad1, cad2));
		}
	}
}
