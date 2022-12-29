package com.fresco;

import java.util.List;
import java.util.Optional;

public class TestOptional2 {

	public static class Identificacion {
		private Optional<String> nombre;
		private Optional<Integer> edad;

		public Identificacion(String nombre, Integer edad) {
			super();
			setNombre(nombre);
			setEdad(edad);
		}

		public Optional<String> getNombre() {
			return nombre;
		}

		public void setNombre(String nombre) {
			this.nombre = Optional.ofNullable(nombre);
		}

		public Optional<Integer> getEdad() {
			return edad;
		}

		public void setEdad(Integer edad) {
			this.edad = Optional.ofNullable(edad);
		}
	};

	public static class Persona{
		private Optional<Identificacion> identificacion;

		public Optional<Identificacion> getIdentificacion() {
			return identificacion;
		}

		public void setIdentificacion(Identificacion identificacion) {
			this.identificacion = Optional.ofNullable(identificacion);
		}

		public Persona(Identificacion identificacion) {
			super();
			setIdentificacion(identificacion);
		}
	};

	public static void main(String[] args) {
		var pedro = new Persona(new Identificacion("Pedro", 18));
		var maria = new Persona(new Identificacion("Maria", 17));
		var juan = new Persona(null);
		var desconocido = new Persona(new Identificacion(null, 21));
		var jose = new Persona(new Identificacion("Jose", null));

		var personas = List.of(pedro, maria, juan, desconocido, jose);
		
		personas.stream()
		.map(Persona::getIdentificacion)
		.flatMap(Optional::stream)
		.map(Identificacion::getNombre)
		.flatMap(Optional::stream)
		.forEach(System.out::println);
		
		for (var persona : personas) {
			if (persona.identificacion.isPresent()) {
				var identificacion = persona.identificacion.get();
				if (identificacion.nombre.isPresent()) {
					var nombre = identificacion.nombre.get();
					System.out.println(nombre);
				}
			}
		}
	}
}
