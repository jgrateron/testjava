package com.fresco;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TestMapMulti2 {

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

	public record Address(String street, String city, String zip) {
	}

	public record Person(String name, int age, String birth_date, Address address) {
	}

	public static void main(String[] args) throws JsonMappingException, JsonProcessingException {
		var objectMapper = new ObjectMapper();
		var person = objectMapper.readValue(json, Person.class);
		var cadenas = List.of(person).stream()//
				.<String>mapMulti((p, consumer) -> {
					consumer.accept(p.name);
					consumer.accept(Integer.toString(p.age));
					consumer.accept(p.birth_date);
					consumer.accept(p.address.street);
					consumer.accept(p.address.city);
					consumer.accept(p.address.zip);
				})//
				.toList();
		cadenas.forEach(System.out::println);
	}

}
