package com.fresco;

import java.util.List;
import java.util.Optional;

public class TestOptionals3 {

	public record Pais(String nombre, String codigo) {
		public Optional<String> getCodigo(){
			return Optional.ofNullable(codigo);
		}
	};

	public record Direccion(Pais pais, String ciudad) {
		public Optional<Pais> getPais(){
			return Optional.ofNullable(pais);
		}
	};

	public record Persona(String nombre, Direccion direccion) {
		public Optional<Direccion> getDireccion(){
			return Optional.ofNullable(direccion);
		}
	};
	
	public static void main(String[] args) {
		var personas = List.of(new Persona("Persona1", null), new Persona("Persona3", new Direccion(null, null)),
				new Persona("Persona3", new Direccion(new Pais(null, null), null)),
				new Persona("Persona4", new Direccion(new Pais("Pais", "PA"), null)));

		for (var pe : personas) {
			var codigo = Optional.ofNullable(pe)
								.flatMap(Persona::getDireccion)
					            .flatMap(Direccion::getPais)
					            .map(Pais::codigo)
					            .orElse("No existe codigo");
			System.out.println(codigo);
		}
	}
}

