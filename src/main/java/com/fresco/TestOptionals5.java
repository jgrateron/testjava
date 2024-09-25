package com.fresco;

import java.util.ArrayList;
import java.util.Optional;

public class TestOptionals5 {

	public record Country(String name, String code) {
	}

	public record Address(Country country, String city) {
	}

	public record Person(String name, Address address) {
	}

	public static void main(String[] args) {
		var people = new ArrayList<Person>();
		people.add(null);
		people.add(new Person("Olivia", null));
		people.add(new Person("Aurora", new Address(null, null)));
		people.add(new Person("Jasper", new Address(new Country(null, null), null)));
		people.add(new Person("Ethan", new Address(new Country("USA", "01"), null)));

		System.out.println("-".repeat(50));
		for (var person : people) {
			try {
				var nameCountry = person.address.country.name;
				System.out.println(person.name + ", " + nameCountry);
			} catch (NullPointerException e) {
				System.err.println("NullPointerException: " + e.getMessage());
			}
		}
		System.out.println("-".repeat(50));
		for (var person : people) {
			var personName = "No person name ";
			var countryName = "No country name";
			if (person != null) {
				personName = person.name;
				if (person.address != null) {
					if (person.address.country != null) {
						if (person.address.country.name != null) {
							countryName = person.address.country.name;
						}
					}
				}
			}
			System.out.println(personName + ", " + countryName);
		}
		System.out.println("-".repeat(50));
		for (var person : people) {
			var personName = Optional.ofNullable(person)//
					.map(Person::name)//
					.orElse("No person name");
			var countryName = Optional.ofNullable(person)//
					.map(Person::address)//
					.map(Address::country)//
					.map(Country::name)//
					.orElse("No country name");
			System.out.println(personName + ", " + countryName);
		}
	}
}


