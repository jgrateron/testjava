package com.fresco;

public class TestResult2 {

	public static Result<Float> arithmeticDivision(float a, float b) {
		if (b == 0) {
			return ResultError.of("division by 0");
		}
		var c = a / b;
		return ResultOk.of(c);
	}

	public static void main(String[] args) {
		var result1 = arithmeticDivision(1, 2);
		switch (result1) {
		case ResultOk<Float> ok -> System.out.println(ok.getValue());
		case ResultError<Float> err -> System.out.println(err.getMsgError());
		}
		var result2 = arithmeticDivision(3, 0);
		switch (result2) {
		case ResultOk<Float> ok -> System.out.println(ok.getValue());
		case ResultError<Float> err -> System.out.println(err.getMsgError());
		}
	}

	static sealed interface Result<T> permits ResultOk, ResultError {
	}

	static final class ResultOk<T> implements Result<T> {
		private T value;

		public ResultOk(T value) {
			this.value = value;
		}

		public T getValue() {
			return value;
		}

		public void setValue(T value) {
			this.value = value;
		}

		public static <U> ResultOk<U> of(U value) {
			return new ResultOk<U>(value);
		}
	}

	static final class ResultError<T> implements Result<T> {
		private String msgError;

		public ResultError(String msgError) {
			this.msgError = msgError;
		}

		public String getMsgError() {
			return msgError;
		}

		public void setMsgError(String msgError) {
			this.msgError = msgError;
		}

		public static <U> Result<U> of(String error) {
			return new ResultError<U>(error);
		}
	}
}







