package com.fresco;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class FuncionesEstaticas {

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
	}

	public static boolean not(boolean expresion) {
		return !expresion;
	}
}

