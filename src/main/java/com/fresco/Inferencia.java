package com.fresco;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class Inferencia {

	record Point(int x, int y) {
	}

	public static void main(String[] args) {
		var i = 0; // int
		System.out.println(i);
		var l = 0l; // long
		System.out.println(l);
		var f = 0f; // float
		System.out.println(f);
		var d = 0d; // double
		System.out.println(d);
		var c = '#'; // char
		System.out.println(c);
		var s = "string"; // string
		System.out.println(s);
		var list = List.of(""); // List
		System.out.println(list);
		var set = Set.of("");// Set
		System.out.println(set);
		var map = Map.of("", "");// Map
		System.out.println(map);
		var p = new Point(0, 0);// Point, record
		System.out.println(p);
	}
}
