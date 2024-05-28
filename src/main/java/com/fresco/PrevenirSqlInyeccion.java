package com.fresco;

import java.util.List;
import java.util.stream.Collectors;

public class PrevenirSqlInyeccion {
	
	public static String limpiarSqlTexto(String sql) {
		var listatags = List.of("'"," or ", " and ", " AND ", "=", "<", ">", "<>", " not ", " NOT ");
		return listatags.stream().collect(Collectors.joining());
	}
	
	public static void main(String[] args) {
		
	}
}
