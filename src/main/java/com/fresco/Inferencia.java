package com.fresco;

import java.util.ArrayList;
import java.util.HashMap;

public class Inferencia {

	record Point(int x, int y) {
	}

	public static void main(String[] args) {
		var i = 0; // int
		var l = 0l; // long
		var f = 0f; // float
		var d = 0d; // double
		var c = ' '; // char
		var s = ""; // string
		var list = new ArrayList<String>(); // List
		var map = new HashMap<String, String>(); // Map
		var p = new Point(0,0);// Point
		System.out.println(i + ", " + l + ", " + f + ", " + d + ", " + c + ", " + s + ", " + list + ", " + map + ", " + p);
		// 0, 0, 0.0, 0.0,  , , [], {}, Point[x=0, y=0]
	}
}

