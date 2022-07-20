package com.fresco;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import java.util.ArrayList;

public class FuncionesEstaticas {

	public static <T> void nonNullExec(T obj, Runnable action) {
		if (nonNull(obj)) {
			action.run();
		}
	}

	public static void main(String[] args) {

		String a = null;
		if (isNull(a)) {
			System.out.println("a es nulo");
		}
		String b = "";
		if (nonNull(b)) {
			System.out.println("b no es nulo");
		}
		int[] arreglo = { 1, 2, 3, 4, 5 };
		int nro = 4;
		int pos = 0;
		boolean encontrado = false;
		while (pos < arreglo.length && not(encontrado)) {
			if (arreglo[pos] == nro) {
				encontrado = true;
			} else {
				pos++;
			}
		}
		System.out.println("encontrado: " + encontrado);
		var lista = new ArrayList<String>();
		nonNullExec(b, () -> {
			lista.add(b);
		});
		
	}

	public static boolean not(boolean expresion) {
		return !expresion;
	}
}
