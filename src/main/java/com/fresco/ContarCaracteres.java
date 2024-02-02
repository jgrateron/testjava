package com.fresco;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class ContarCaracteres {
    public static void main(String[] args) throws IOException {
        var mapCaracteres = Files.lines(Path.of("./CalculateAverage_jgrateron.java"))
                .map(s -> List.of(s.split("")))
                .flatMap(List::stream)
                .filter(c -> !c.isBlank())
                .collect(Collectors.groupingBy(String::toString, Collectors.counting()));

        Comparator<Map.Entry<String, Long>> compararValue = Map.Entry.comparingByValue();

        Comparator<Map.Entry<String, Long>> compararKey = Map.Entry.comparingByKey();

        var i = new AtomicInteger(0);

        Consumer<Map.Entry<String, Long>> consumerEntry = e -> {
            System.out.println("%02d) %s: %d".formatted(i.incrementAndGet(), e.getKey(), e.getValue()));
        };

        mapCaracteres.entrySet().stream()
                .sorted(compararValue.reversed().thenComparing(compararKey))
                .limit(20)
                .forEach(consumerEntry);
    }
}

