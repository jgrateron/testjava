package com.fresco;

public class EjemploResult {

	public static Result<Float> dividir(float a, float b) {

		if (b == 0) {
			return Result.error("division por 0");
		}
		float c = a / b;
		return Result.ok(c);
	}

	public static void main(String[] args) {

		var result = dividir(1, 2);
		if (result.isError()) {
			System.out.println(result.getError());
		} else {
			System.out.println(result.getValue());
		}
	}

}
