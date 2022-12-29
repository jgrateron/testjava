package com.fresco;

import java.util.List;

public class PrevenirSqlInyeccion {
	
	public static String limpiarSqlTexto(String sql) {
		var listatags = List.of("'"," or ", " and ", " AND ", "=", "<", ">", "<>", " not ", " NOT ");
		return "";
	}
	
	public static void main(String[] args) {
		
	}
}
