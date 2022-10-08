package com.fresco;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class ClockTime {
	public static void main(String[] args) {

		var now = Instant.now().atZone(ZoneId.of("America/Caracas"));
		System.out.println(now);
		
		var ahora = LocalDateTime.now().atZone(ZoneId.of("America/Lima"));
		System.out.println(ahora);
	}
}
