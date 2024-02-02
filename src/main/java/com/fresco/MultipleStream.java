package com.fresco;

import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

public class MultipleStream {
    public static AtomicInteger cuantos = new AtomicInteger();
    public static AtomicBoolean end = new AtomicBoolean();

    public record Medicion(ByteBuffer estacion, double temperatura) {
    }

    public static void main(String[] args) {
        var hilo = new Thread(() -> {
            while (!end.get()) {
                try {
                    Thread.sleep(1000);
                    System.out.println(cuantos);
                } catch (InterruptedException e) {
                }
            }
        });
        hilo.start();
        var ini = System.currentTimeMillis();
        var archivo = List.of("xaa", "xab", "xac", "xad", "xae", "xaf", "xag", "xah", "xai", "xaj");
        var ruta = "/opt/desafio/partes";

        var lista = archivo.stream().parallel()
                .map(f -> {
                    Stream<String> result = Stream.empty();
                    try {
                        result = Files.lines(Path.of(ruta + "/" + f));
                        System.out.println(f);
                    } catch (Exception e) {
                    }
                    return result;
                })
                .flatMap(Function.identity())
                .map(s -> split(s))
                .collect(groupingBy(Medicion::estacion, summarizingDouble(Medicion::temperatura)));

        Comparator<Entry<String, DoubleSummaryStatistics>> comparator = (a, b) -> {
            return a.getKey().compareTo(b.getKey());
        };

        Function<Entry<String, DoubleSummaryStatistics>, String> resultToStr = e -> {
            return "%s=%.1f/%.1f/%.1f".formatted(e.getKey(), e.getValue().getMin(), e.getValue().getAverage(),
                    e.getValue().getMax());
        };

        var resultado = lista.entrySet().stream().parallel()
                .map(e -> {
                    var estacion = new String(e.getKey().array());
                    return Map.entry(estacion, e.getValue());
                })
                .sorted(comparator)
                .map(resultToStr)
                .collect(joining(","));
        System.out.println(resultado);
        var time = System.currentTimeMillis() - ini;
        System.out.println(time);
        end.set(true);
    }

    public static Medicion split(String l) {
        cuantos.incrementAndGet();
        var ar = l.getBytes();
        var posSemicolon = ar.length - 4;

        posSemicolon = ar[posSemicolon] == ';' ? posSemicolon
                : ar[posSemicolon - 1] == ';' ? posSemicolon - 1
                        : ar[posSemicolon - 2] == ';' ? posSemicolon - 2
                                : ar[posSemicolon - 3] == ';' ? posSemicolon - 3
                                        : ar[posSemicolon - 4] == ';' ? posSemicolon - 4 : 0;

        var estacion = new byte[posSemicolon];
        System.arraycopy(ar, 0, estacion, 0, posSemicolon);
        return new Medicion(ByteBuffer.wrap(estacion), parseDouble(ar, posSemicolon + 1));
    }

    /*
     * 
     */
    public static double parseDouble(byte s[], int from) {
        int pos = from;
        boolean esNegativo = s[from] == '-';
        pos = esNegativo ? pos + 1 : pos;
        double number = 0;
        if (s[pos + 1] == '.') {
            number = (s[pos] - 48) * 10 + s[pos + 2] - 48;
        } else {
            number = (s[pos] - 48) * 100 + (s[pos + 1] - 48) * 10 + s[pos + 3] - 48;
        }
        number = number / 10.0;
        return esNegativo ? -number : number;
    }
}
