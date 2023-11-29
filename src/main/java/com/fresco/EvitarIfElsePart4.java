package com.fresco;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

public class EvitarIfElsePart4 {

	public static String obtenerCalificacion(int valor) {
		if (valor >= 9 && valor <= 10) {
			return "Excelente";
		} else if (valor >= 7 && valor <= 8) {
			return "Buena";
		} else if (valor >= 5 && valor <= 6) {
			return "Regular";
		} else if (valor >= 3 && valor <= 4) {
			return "Deficiente";
		} else if (valor >= 1 && valor <= 2) {
			return "Pésimo";
		} else if (valor == 0) {
			return "Malo";
		} else {
			return "Ninguna";
		}
	}

	public static record Calificacion(int min, int max, String valor) {
		public boolean rango(int num) {
			return num >= min && num <= max;
		}
	}

	public static String obtenerCalificacion(List<Calificacion> listaCalificaciones, int valor) {
		return listaCalificaciones.stream()//
				.filter(c -> c.rango(valor))//
				.map(Calificacion::valor)//
				.findFirst()//
				.orElse("Ninguna");
	}

	public static void main(String[] args) {
		var listaCalificaciones = List.of(calif(0, 0, "Malo"), calif(1, 2, "Pésimo"), calif(3, 4, "Deficiente"),
				calif(5, 6, "Regular"), calif(7, 8, "Buena"), calif(9, 10, "Excelente"));
		assertEquals("Excelente", obtenerCalificacion(listaCalificaciones, 10));
	}

	public static Calificacion calif(int min, int max, String valor) {
		return new Calificacion(min, max, valor);
	}
}
