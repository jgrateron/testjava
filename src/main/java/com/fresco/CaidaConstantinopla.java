package com.fresco;

import java.util.ArrayList;
import java.util.List;

public class CaidaConstantinopla {

	public static void main(String[] args) {
		var listaAnios = new ArrayList<Integer>(List.of(1990, 2005, 1453, 1776, 1492, 1945, 2000));
		int anioBuscar = 1453;

		var encontrado = listaAnios.stream()//
				.filter(anio -> anio == anioBuscar)//
				.findAny();
		System.out.println(encontrado.isPresent());
		listaAnios.add(1234);
		
	}

}
