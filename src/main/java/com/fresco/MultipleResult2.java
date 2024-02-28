package com.fresco;

import com.fresco.util.PairInt;

public class MultipleResult2 {

	public static PairInt multiBy(int x, int y, int n) {
		return new PairInt(x * n, y * n);
	}

	public static TripletInt multiBy(int x, int y, int z, int n) {
		return TripletInt.of(x * n, y * n, z * n);
	}

	public static void main(String[] args) {
		var result1 = multiBy(1, 2, 2);
		System.out.println(result1.getValue1() + " " + result1.getValue2());
		var result2 = multiBy(1, 2, 3);
		System.out.println(result2.getValue1() + " " + result2.getValue2());
		var result3 = multiBy(1, 2, 3, 5);
		System.out.println(result3.value1 + " " + result3.value2 + " " + result3.value3);
	}

	static class TripletInt {
		public final int value1;
		public final int value2;
		public final int value3;

		private TripletInt(int value1, int value2, int value3) {
			this.value1 = value1;
			this.value2 = value2;
			this.value3 = value3;
		}

		public static TripletInt of(int value1, int value2, int value3) {
			return new TripletInt(value1, value2, value3);
		}
	}
}
