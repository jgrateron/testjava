package com.fresco;

public class TestResult4 {
	public static Result<Error, Float> arithmeticDivision(float a, float b) {
		if (b == 0) {
			return Failure.of(Error.of(101, "division by 0"));
		}
		var c = a / b;
		return Success.of(c);
	}

	public static void main(String[] args) {
		var result1 = arithmeticDivision(1, 2);
		switch (result1) {
		case Success<?, Float> su -> System.out.println(su.value);
		case Failure<Error, ?> fa -> System.out.println(fa.error);
		}
		var result2 = arithmeticDivision(3, 0);
		if (result2 instanceof Success<?, Float> su) {
			System.out.println(su.value);
		} else if (result2 instanceof Failure<Error, ?> fa) {
			System.out.println(fa.error);
		}
	}

	static sealed interface Result<T, A> permits Success, Failure {
	}

	static record Success<T, A>(A value) implements Result<T, A> {
		public static <T, A> Success<T, A> of(A value) {
			return new Success<T, A>(value);
		}
	}

	static record Failure<T, A>(T error) implements Result<T, A> {
		public static <T, A> Failure<T, A> of(T error) {
			return new Failure<T, A>(error);
		}
	}

	static record Error(int code, String msg) {
		static Error of(int code, String msg) {
			return new Error(code, msg);
		}
	}
}


