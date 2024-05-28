package com.fresco;

import java.util.Scanner;

public class InputOuput {

	public static void main(String[] args) {
		try (var sc = new Scanner(System.in)) {
			System.out.println("Enter user name: ");
			var name = sc.nextLine();
			System.out.println("The user name is: " + name);
		}
	}

}

