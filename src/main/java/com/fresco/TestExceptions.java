package com.fresco;

public class TestExceptions {

	public static void main(String[] args) {
		var min = 1;
		var max = 10;
		var countExceptions = 0;
		var startTime = System.nanoTime();
		for (var i = 0; i < 1000; i++) {
			try {
				var value = Math.round(Math.random() * (max - min + 1) + min);
				if (value % 2 == 0) {
					System.out.println("Es par");
				} else {
					throw new RuntimeException("No es par");
				}
			} catch (Exception e) {
				countExceptions++;
				System.out.println(e.getMessage());
			}
		}
		var endTime = System.nanoTime();
		var duration = endTime - startTime;
		var milseconds = duration / 1000000;
		System.out.println(milseconds + "ms " + countExceptions + " ex");
	}

}
