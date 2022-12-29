package com.fresco;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class TestPlazoExcepcional {

	public record PlazoExcepcional(String codigo, String fechaEmision, String fechaLimite) {
	};

	public static Date strToDate(String fecha) throws ParseException {
		var sd = new SimpleDateFormat("yyyy-MM-dd");
		return sd.parse(fecha);
	}

	public PlazoExcepcional getPlazoExcepcional(String fechaEmiDoc, String tipoDocumento) {
		var fechas = Map.of("2022-12-31", "2023-01-03", "2022-12-30", "2023-01-02", "2022-12-29", "2023-01-01",
				"2022-12-28", "2022-12-31", "2022-12-27", "2022-12-30", "2022-12-26", "2022-12-29", "2022-12-25",
				"2022-12-28", "2022-12-24", "2022-12-27");

		for (var entry : fechas.entrySet()) {
			var plazo = crearExcepcion(fechaEmiDoc, entry.getKey(), entry.getKey(), entry.getValue(), tipoDocumento);
			if (plazo != null) {
				return plazo;
			}
		}
		return null;
	}

	public PlazoExcepcional crearExcepcion(String fechaEmiDoc, String fechaEmiIni, String fechaEmiFin,
			String fechaLimite, String tipoDocumento) {
		try {
			Date fechaIni = strToDate(fechaEmiIni);
			Date fechaFin = strToDate(fechaEmiFin);
			Date fechaEmision = strToDate(fechaEmiDoc);

			if (fechaEmision.equals(fechaIni) || fechaEmision.equals(fechaFin)) {
				PlazoExcepcional plazo = new PlazoExcepcional(tipoDocumento, fechaEmiDoc, fechaLimite);
				return plazo;
			}
			if (fechaEmision.after(fechaIni) && fechaEmision.before(fechaFin)) {
				PlazoExcepcional plazo = new PlazoExcepcional(tipoDocumento, fechaEmiDoc, fechaLimite);
				return plazo;
			}
		} catch (Exception e) {
			// ignore
		}
		return null;
	}

	public void run() {
		var fechas = List.of("2022-12-23", "2022-12-24", "2022-12-25", "2022-12-26", "2022-12-27", "2022-12-28",
				"2022-12-29", "2022-12-30", "2022-12-31", "2023-01-01");
		for (var fecha : fechas) {
			var plazo = getPlazoExcepcional(fecha, "01");
			System.out.println(fecha + " - " + plazo);
		}

	}

	public static void main(String[] args) {
		new TestPlazoExcepcional().run();
	}
}
