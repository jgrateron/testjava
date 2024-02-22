package com.fresco;

import java.util.ArrayList;
import java.util.List;

public class ImmutableRecords {

	public static void main(String[] args) {
		record Persona(String nombre, List<String> actividades) {
			Persona{
				actividades = List.copyOf(actividades);
			}
		}

		var persona = new Persona("Jairo", List.of("Leer", "Caminar", "Dormir"));
		try {
			persona.actividades().add("Comer");
		} catch (UnsupportedOperationException e) {
			System.out.println(e);
		}
		System.out.println(persona);
		var otraPersona = new Persona("Maria", new ArrayList<String>(List.of("Leer", "Caminar", "Dormir")));
		try {
			otraPersona.actividades().add("Comer");
		} catch (UnsupportedOperationException e) {
			System.out.println(e);
		}
		System.out.println(otraPersona);
	}

}
