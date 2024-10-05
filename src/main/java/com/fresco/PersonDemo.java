package com.fresco;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class PersonDemo {

	public static String json = """
			{
				"name": "John Doe",
				"age" : 30,
				"address" : {
					"street" : "123 Main St",
					"city" : "Springfield",
					"zip" : "12345"
				}
			}
			""";

	public record Address(String street, String city, String zip) {
	}

	public record Person(String name, int age, Address address) {
	}

	public static void main(String[] args) throws JsonMappingException, JsonProcessingException {
		var objectMapper = new ObjectMapper();
		var person = objectMapper.readValue(json, Person.class);
		System.out.println(person);
	}
}


