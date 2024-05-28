package com.fresco;

import java.util.List;
import java.util.Optional;

public class TestOptionals {
	public record Identificacion(String nombre, Integer edad) {
		public Optional<String> getNombre() {
			return Optional.ofNullable(nombre);
		}

		public Optional<Integer> getEdad() {
			return Optional.ofNullable(edad);
		}
	};

	public record Persona(Identificacion identificacion) {
		public Optional<Identificacion> getIdentificacion() {
			return Optional.ofNullable(identificacion);
		}
	};

	public static boolean isNotEmpty(String cad) {
		return (!cad.isEmpty());
	}

	public static void main(String[] args) {
		var pedro = new Persona(new Identificacion("Pedro", null));
		var maria = new Persona(new Identificacion("Maria", 17));
		var fulano = new Persona(null);
		var desconocido = new Persona(new Identificacion(null, 21));
		var jose = new Persona(new Identificacion("Jose", 22));

		var personas = List.of(pedro, maria, fulano, desconocido, jose);

		personas.stream()
		.map(p -> p.getIdentificacion().flatMap(Identificacion::getNombre))
		.flatMap(Optional::stream)
		.forEach(System.out::println);

		for (var persona : personas) {
			if (persona.getIdentificacion().isPresent()) {
				var identificacion = persona.getIdentificacion().get();
				if (identificacion.getNombre().isPresent()) {
					var nombre = identificacion.getNombre().get();
					System.out.println(nombre);
				}
			}
		}
	}
}
