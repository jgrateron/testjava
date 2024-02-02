package com.fresco;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class Desafio1BR3 {

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
                .collect(Collectors.toMap(Medicion::getKey, Medicion::getValue, Medicion::merge, HashMap::new));

        Comparator<Entry<String, Medicion>> comparator = (a, b) -> {
            return a.getKey().compareTo(b.getKey());
        };
        var result = resultados.entrySet().stream()
                .map(e -> {
                    var estacion = new String(e.getKey().array());
                    return Map.entry(estacion, e.getValue());
                })
                .sorted(comparator)
                .map(e -> {
                    var key = e.getKey();
                    var value = e.getValue();
                    return "%s=%.1f/%.1f/%.1f".formatted(key, value.getMin(), value.getAverage(), value.getMax());
                })
                .collect(Collectors.joining(","));
        return "{" + result + "}";
    }

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
        var temp = parseInt(ar, posSemicolon + 1);
        return new Medicion(ByteBuffer.wrap(estacion), temp, temp, temp);
    }

    /*
     * 
     */
    public static int parseInt(byte s[], int from) {
        int pos = from;
        boolean esNegativo = s[from] == '-';
        pos = esNegativo ? pos + 1 : pos;
        int number = 0;
        if (s[pos + 1] == '.') {
            number = (s[pos] - 48) * 10 + s[pos + 2] - 48;
        } else {
            number = (s[pos] - 48) * 100 + (s[pos + 1] - 48) * 10 + s[pos + 3] - 48;
        }
        return esNegativo ? -number : number;
    }

    public static class Medicion {
        private ByteBuffer estation;
        private int min;
        private int max;
        private int sum;
        private int count;

        public Medicion(ByteBuffer estation, int min, int max, int sum) {
            this.estation = estation;
            this.min = min;
            this.max = max;
            this.sum = sum;
            this.count = 1;
        }

        public ByteBuffer getKey() {
            return estation;
        }

        public Medicion getValue() {
            return this;
        }

        public Medicion merge(Medicion other) {
            this.min = Math.min(min, other.min);
            this.max = Math.max(max, other.max);
            this.sum += other.sum;
            this.count += other.count;
            return this;
        }

        public double getMin() {
            return min / 10.0;
        }

        public double getMax() {
            return max / 10.0;
        }

        public double getAverage() {
            return (sum / 10.0) / count;
        }
    }
}
