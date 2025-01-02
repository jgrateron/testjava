package com.fresco;

import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RemoveDuplicados {

	private record WeatherStation(String id, double meanTemperature) {
	}

	public static void main(String[] args) {
		var stations = List.of(
				new WeatherStation("Alexandria", 20.0),
				new WeatherStation("Atlanta", 17.0),
				new WeatherStation("Beirut", 20.9),
				new WeatherStation("Bilbao", 14.7),
				new WeatherStation("Canberra", 13.1),
				new WeatherStation("Dallas", 19.0),
				new WeatherStation("Damascus", 17.0),
				new WeatherStation("Damascus", 17.0),
				new WeatherStation("Damascus", 17.0),
				new WeatherStation("Damascus", 17.0),
				new WeatherStation("Dublin", 9.8),
				new WeatherStation("Guatemala City", 20.4),
				new WeatherStation("Hiroshima", 16.3),
				new WeatherStation("Hong Kong", 23.3),
				new WeatherStation("Hong Kong", 23.3),
				new WeatherStation("Los Angeles", 18.6),
				new WeatherStation("Miami", 24.9),
				new WeatherStation("Milan", 13.0),
				new WeatherStation("Oslo", 5.7),
				new WeatherStation("Oslo", 5.7),
				new WeatherStation("Ottawa", 6.6),
				new WeatherStation("Ottawa", 6.6),
				new WeatherStation("Portland (OR)", 12.4),
				new WeatherStation("Porto", 15.7),
				new WeatherStation("Prague", 8.4),
				new WeatherStation("Saint Petersburg", 5.8),
				new WeatherStation("Saint Petersburg", 5.8),
				new WeatherStation("Saint Petersburg", 5.8),
				new WeatherStation("Saint Petersburg", 5.8),
				new WeatherStation("Saint-Pierre", 5.7),
				new WeatherStation("Taipei", 23.0),
				new WeatherStation("Veracruz", 25.4),
				new WeatherStation("Veracruz", 25.4),
				new WeatherStation("Zanzibar City", 26.0),
				new WeatherStation("Zanzibar City", 26.0),
				new WeatherStation("Zanzibar City", 26.0),
				new WeatherStation("Zanzibar City", 26.0),
				new WeatherStation("Zanzibar City", 26.0),
				new WeatherStation("Zürich", 9.3));

		var unique = stations.stream()
				.collect(Collectors.toMap(WeatherStation::id, weatherStation -> weatherStation, (ol, ne) -> ol))
				.values().stream().toList();
		System.out.println(stations.size());
		System.out.println(unique.size());

		var agrupaEstaciones = stations.stream()
				.collect(Collectors.groupingBy(WeatherStation::id,
						Collectors.summarizingDouble(WeatherStation::meanTemperature)));

		Comparator<Map.Entry<String, DoubleSummaryStatistics>> comparator = (a, b) -> {
			return a.getKey().compareTo(b.getKey());
		};
		agrupaEstaciones.entrySet().stream()
				.sorted(comparator)
				.forEach((e) -> {
					var t = "%.2f".formatted(e.getValue().getAverage());
					var c = "+".repeat((int)e.getValue().getCount());
					System.out.println("%-20s %5s %s".formatted(e.getKey(), t, c));
				});
		var agrupaEstaciones2 = stations.stream()
				.collect(Collectors.groupingBy(WeatherStation::id));
		agrupaEstaciones2.forEach((k, v) -> {
			//System.out.println("%s".formatted(v.get(0).toString()));
		});
	}

}
