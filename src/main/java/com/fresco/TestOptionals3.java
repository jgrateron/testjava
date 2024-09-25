package com.fresco;

import java.util.List;
import java.util.Optional;

public class TestOptionals3 {

	public record Pais(String nombre, String codigo) {
	}

	public record Direccion(Pais pais, String ciudad) {
	}

	public record Persona(String nombre, Direccion direccion) {
	}

	public static void main(String[] args) {
		var personas = List.of(new Persona("Persona1", null), new Persona("Persona3", new Direccion(null, null)),
				new Persona("Persona3", new Direccion(new Pais(null, null), null)),
				new Persona("Persona4", new Direccion(new Pais("PERU", "PE"), null)));

		for (var persona : personas) {
			try {
				var codigo = persona.direccion.pais.codigo;
				System.out.println(codigo + " - " + codigo.substring(0, 2));
			} catch (NullPointerException e) {
				System.err.println(e.getMessage());
			}
		}

		for (var persona : personas) {
			var codigo = Optional.ofNullable(persona)//
					.map(Persona::direccion)//
					.map(Direccion::pais)//
					.map(Pais::nombre)//
					.map(c -> persona.nombre + " " + c)
					.orElse( persona.nombre + " "  + "No tiene pais");					
			System.out.println(codigo + " - " + codigo.substring(0, 2));
		}

		try {
			personas.stream()//
					.map(Persona::direccion)//
					.map(Direccion::pais)//
					.map(Pais::nombre)//
					.forEach(codigo -> {
						System.out.println(codigo + " - " + codigo.substring(0, 2));
					});
		} catch (NullPointerException e) {
			System.err.println(e.getMessage());
		}

		personas.stream()//
				.map(persona -> {
					return Optional.ofNullable(persona)//
							.map(Persona::direccion)//
							.map(Direccion::pais)//
							.map(Pais::nombre)//
							.map(c -> persona.nombre + " " + c)
							.orElse( persona.nombre + " "  + "No pais");
				})//
				.forEach(codigo -> {
					System.out.println(codigo + " - " + codigo.substring(0, 2));
				});
	}
}
