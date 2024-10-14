package com.fresco;

import java.util.Stack;

public class Balanceado {

	public static void main(String[] args) {
		System.out.println(esBalanceado("{ [ a * ( c + d ) ] - 5 }"));
		System.out.println(esBalanceado("{ a * ( c + d ) ] - 5 }"));
		System.out.println(esBalanceado("{ [ a * ( c + d ) ] - 5 } }"));
		System.out.println(esBalanceado("[[]]"));
		System.out.println(esBalanceado("[[]]["));
	}

	public static boolean esBalanceado(String cadena) {
		System.out.print("%-30s: ".formatted(cadena));
		var caracteres = cadena.toCharArray();
		var pila = new Stack<Character>();
		for (var c : caracteres) {
			switch (c) {
			case '[', '{', '(':
				pila.push(cierreDe(c));
				break;
			case ']', '}', ')':
				if (pila.isEmpty() || c != pila.pop()) {
					return false;
				}
				break;
			}
		}
		return pila.isEmpty();
	}

	public static char cierreDe(char c) {
		return switch (c) {
		case '[' -> ']';
		case '{' -> '}';
		case '(' -> ')';
		default -> c;
		};
	}
}
