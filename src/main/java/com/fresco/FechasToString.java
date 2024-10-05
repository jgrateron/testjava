package com.fresco;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class FechasToString {
	public static void main(String[] args) {
		var calendar = Calendar.getInstance();
		var fecha = calendar.getTime();
		System.out.println(fecha);
		var formatoLargo = new SimpleDateFormat("E d 'de' MMMM 'de' yyyy", Locale.of("ES"));
		var fechaLarga = formatoLargo.format(fecha);
		System.out.println(fechaLarga);
	}
}
