package com.fresco;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.stream.Collectors;

public class calendario {

	public static void main(String[] args) {
		var localDate = LocalDate.of(2024, 1, 1);
		System.out.println(localDate);

		var text = "National Aeronautics Space Administration";
		var ar = text.split(" ");
		var acro = Arrays.stream(ar).map(s -> s.substring(0, 1)).collect(Collectors.joining());
		System.out.println(acro.toUpperCase());
	}

}
