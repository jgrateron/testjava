package com.fresco;

import java.util.List;

public class FiltrarNotas {

	public static boolean isNotEmpty(String s) {
		return !(s.isEmpty());
	}

	public static String extraerNota(String nota) {
		int max = nota.length() >= 4 ? 4 : nota.length();
		return nota.substring(0, max);
	}

	public static void main(String[] args) {
		var lista = List.of(
				"4207 - El DNI debe tener 8 caracteres numéricos - Error en la linea: 94. : 4207 (nodo: cac:AccountingCustomerParty/cbc:CustomerAssignedAccountID valor: 000235422)",
				"4207 - El DNI debe tener 8 caracteres numéricos - Error en la linea: 111. : 4207 (nodo: cac:AccountingCustomerParty/cbc:CustomerAssignedAccountID valor: 996847279)",
				"4207 - El DNI debe tener 8 caracteres numéricos - Error en la linea: 148. : 4207 (nodo: cac:AccountingCustomerParty/cbc:CustomerAssignedAccountID valor: CF2NTTMX1)",
				"4207 - El DNI debe tener 8 caracteres numéricos - Error en la linea: 156. : 4207 (nodo: cac:AccountingCustomerParty/cbc:CustomerAssignedAccountID valor: 047451191)",
				"4207 - El DNI debe tener 8 caracteres numéricos - Error en la linea: 158. : 4207 (nodo: cac:AccountingCustomerParty/cbc:CustomerAssignedAccountID valor: 1238339028)",
				"3305 - El DNI debe tener 8 caracteres numéricos - Error en la linea: 190. : 4207 (nodo: cac:AccountingCustomerParty/cbc:CustomerAssignedAccountID valor: 575370468)",
				"4207 - El DNI debe tener 8 caracteres numéricos - Error en la linea: 192. : 4207 (nodo: cac:AccountingCustomerParty/cbc:CustomerAssignedAccountID valor: PYG4O0K3)",
				"4207 - El DNI debe tener 8 caracteres numéricos - Error en la linea: 196. : 4207 (nodo: cac:AccountingCustomerParty/cbc:CustomerAssignedAccountID valor: 1025331929)",
				"4207 - El DNI debe tener 8 caracteres numéricos - Error en la linea: 227. : 4207 (nodo: cac:AccountingCustomerParty/cbc:CustomerAssignedAccountID valor: SDI9DRHA)",
				"4207 - El DNI debe tener 8 caracteres numéricos - Error en la linea: 234. : 4207 (nodo: cac:AccountingCustomerParty/cbc:CustomerAssignedAccountID valor: JRXQWFK0)",
				"4207 - El DNI debe tener 8 caracteres numéricos - Error en la linea: 243. : 4207 (nodo: cac:AccountingCustomerParty/cbc:CustomerAssignedAccountID valor: MSLTO5UM)");
		var codigos = lista.stream()//
				.filter(FiltrarNotas::isNotEmpty)//
				.map(FiltrarNotas::extraerNota)//
				.toList();
		codigos.stream().forEach(System.out::println);
	}

}
