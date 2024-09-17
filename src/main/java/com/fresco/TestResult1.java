package com.fresco;

public class TestResult1 {

	public static Result<Float> arithmeticDivision(float a, float b) {
		if (b == 0) {
			return Result.error("division by 0");
		}
		var c = a / b;
		return Result.ok(c);
	}

	public static void main(String[] args) {
		var result1 = arithmeticDivision(1, 2);
		if (result1.isOk()) {
			System.out.println(result1.getValue());
		} else {
			System.out.println(result1.getError());
		}
		var result2 = arithmeticDivision(3, 0);
		if (result2.isError()) {
			System.out.println(result2.getError());
		} else {
			System.out.println(result2.getValue());
		}
		//wrong
		var result3 = arithmeticDivision(5, 0);
		if (result3.isError()) {
			System.out.println(result2.getValue());
		} else {
			System.out.println(result2.getValue());
		}
		
	}

	static class Result<T> {
		private T value;
		private String error;

		private Result(T value, String error) {
			this.value = value;
			this.error = error;
		}

		public static <U> Result<U> ok(U value) {
			return new Result<>(value, null);
		}

		public static <U> Result<U> error(String error) {
			return new Result<>(null, error);
		}

		public boolean isError() {
			return error != null;
		}

		public boolean isOk() {
			return !isError();
		}

		public T getValue() {
			return value;
		}

		public String getError() {
			return error;
		}
	}
}

