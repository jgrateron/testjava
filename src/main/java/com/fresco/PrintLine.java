package com.fresco;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.PrintStream;

public class PrintLine {

	public static void main(String[] args) throws FileNotFoundException {
		var outStream = System.out;
		
		System.setOut(new PrintStream("salida.log"));
		System.out.println("Hola");
		System.out.println("Mundo");
		System.setOut(outStream);
		
		var output = new ByteArrayOutputStream();
		System.setOut(new PrintStream(output));
		System.out.println("Hola");
		System.out.println("Mundo");
		System.setOut(outStream);
		
		var salida = output.toString();
		System.out.println(salida);
	}
}

