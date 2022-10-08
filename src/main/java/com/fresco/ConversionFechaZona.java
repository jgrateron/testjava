package com.fresco;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

public class ConversionFechaZona {
	public static void main(String[] args) throws ParseException {
		var strFecha = "2022-07-25 11:50:45 UTC";
		var sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
		var fecha = sdf.parse(strFecha);
		TimeZone.setDefault(TimeZone.getTimeZone("America/Caracas"));
		System.out.println(fecha.toString());
		//Mon Jul 25 07:50:45 VET 2022
	}
}

