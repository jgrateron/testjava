package com.fresco;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class TestMundial {
	record Mundial(String year, String champion, String teams, String host, String matches, String runnerUp,
			String thirdPlace, String goals) {
	}

	public static void main(String[] args) throws IOException {
		var doc = Jsoup.connect("https://www.foxsports.com/soccer/2022-fifa-world-cup/history").get();
		var tabla = parseTable(doc);
		var mundiales = tabla.stream().map(map -> new Mundial(map.get("year"), map.get("champion"), map.get("teams"),
				map.get("host"), map.get("matches"), map.get("runner up"), map.get("third place"), map.get("goals")))
				.toList();
		Consumer<Entry<String, Long>> print = a -> {
			System.out.println(a.getKey() + " ".repeat(20 - a.getKey().length()) + a.getValue());
		};
		Comparator<Entry<String, Long>> cmp = (a, b) -> {
			int cmp1 = a.getValue().compareTo(b.getValue());
			return cmp1 != 0 ? cmp1 : b.getKey().compareTo(a.getKey());
		};
		var ganadores = mundiales.stream().collect(Collectors.groupingBy(Mundial::champion, Collectors.counting()))
				.entrySet().stream().sorted(cmp.reversed()).toList();
		System.out.println("Ganadores");
		System.out.println("-".repeat(25));
		ganadores.forEach(print);
		System.out.println("-".repeat(25));
		var allChRu = mundiales.stream().map(m -> List.of(m.champion, m.runnerUp)).flatMap(List::stream).toList();
		var participaciones = allChRu.stream()
				.collect(Collectors.groupingBy(Function.identity(), Collectors.counting())).entrySet().stream()
				.sorted(cmp.reversed()).toList();
		System.out.println("Participaciones en finales");
		System.out.println("-".repeat(25));
		participaciones.forEach(print);
		var sedes = mundiales.stream().collect(Collectors.groupingBy(Mundial::host,Collectors.counting()))
				.entrySet().stream().sorted(cmp.reversed()).toList();
		System.out.println("-".repeat(25));
		System.out.println("Sedes");
		System.out.println("-".repeat(25));
		sedes.forEach(print);		
	}

	public static List<Map<String, String>> parseTable(Document doc) {
		var table = doc.select("table").get(0);
		var rows = table.select("tr");
		var first = rows.get(0).select("th,td");
		var headers = new ArrayList<String>();
		for (var header : first) {
			headers.add(header.text().isBlank() ? "year" : header.text().toLowerCase());
		}
		table = doc.select("table").get(1);
		rows = table.select("tr");
		var listMap = new ArrayList<Map<String, String>>();
		for (int row = 0; row < rows.size(); row++) {
			var colVals = rows.get(row).select("th,td");
			int colCount = 0;
			var tuple = new HashMap<String, String>();
			for (var colVal : colVals)
				tuple.put(headers.get(colCount++), colVal.text());
			listMap.add(tuple);
		}
		return listMap;
	}
}
