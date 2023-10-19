package com.fresco;

public record Person(String name, String address) {

	public Person setName(String name) {
		return new Person(name, this.address);
	}

	public Person setAddress(String address) {
		return new Person(this.name, address);
	}
}

