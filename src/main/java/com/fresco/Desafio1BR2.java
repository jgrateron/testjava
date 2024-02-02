package com.fresco;

import static java.util.stream.Collectors.summarizingDouble;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class Desafio1BR2 {
    public record Medicion(ByteBuffer estation, double temperatura) {
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        calcular();
        var ini = System.currentTimeMillis();
        var resultado = calcular();
        System.out.println(resultado);
        var end = System.currentTimeMillis();
        var time = (end - ini) / 1000.0;
        System.out.println("%.2f".formatted(time));
    }

    public static String calcular() throws IOException {
        var resultados = Files.lines(Path.of("./measurements.txt")).parallel()
                .map(s -> split(s))
                .collect(Collectors.groupingBy(Medicion::estation, summarizingDouble(Medicion::temperatura)));

        Comparator<Entry<String, DoubleSummaryStatistics>> comparator = (a, b) -> {
            return a.getKey().compareTo(b.getKey());
        };
        var resultado = resultados.entrySet().stream().parallel()
                .map(e -> {
                    var estacion = new String(e.getKey().array());
                    return Map.entry(estacion, e.getValue());
                })
                .sorted(comparator)
                .map(e -> {
                    return "%s=%.1f/%.1f/%.1f".formatted(e.getKey(), e.getValue().getMin(),
                            e.getValue().getAverage(),
                            e.getValue().getMax());
                })
                .collect(Collectors.joining(","));
        return "{" + resultado + "}";
    }

    /*
     * 
     */
    public static Medicion split(String l) {
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
