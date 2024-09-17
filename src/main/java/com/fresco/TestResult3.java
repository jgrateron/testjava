package com.fresco;

public class TestResult3 {

	public static Result<Float> arithmeticDivision(float a, float b) {
		if (b == 0) {
			return Failure.of("division by 0");
		}
		var c = a / b;
		return Success.of(c);
	}

	public static void main(String[] args) {
		var result1 = arithmeticDivision(1, 2);
		switch (result1) {
		case Success<Float> su -> System.out.println(su.value);
		case Failure<Float> fa -> System.out.println(fa.error);
		}
		var result2 = arithmeticDivision(3, 0);
		if (result2 instanceof Success<Float> su) {
			System.out.println(su.value);
		} else if (result2 instanceof Failure<Float> fa) {
			System.out.println(fa.error);
		}
	}

	public static sealed interface Result<A> permits Success, Failure {
	}

	static public record Success<A>(A value) implements Result<A> {
		public static <A> Success<A> of(A value) {
			return new Success<A>(value);
		}
	}

	static public record Failure<A>(String error) implements Result<A> {
		public static <A> Failure<A> of(String error) {
			return new Failure<A>(error);
		}
	}
}

