package com.fresco;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

public class Desafio1BR6 {
    public static String FILE = "./measurements200.txt";
    public static Map<Index, Medicion> mapMedicionesTotales = new HashMap<Index, Medicion>();

    public static void main(String[] args) throws IOException, InterruptedException {
        Locale.setDefault(Locale.US);
        var startTime = System.nanoTime();
        var cores = Runtime.getRuntime().availableProcessors();
        var listaWorkers = new ArrayList<Worker>();
        try (var file = new RandomAccessFile(FILE, "r")) {
            var channel = file.getChannel();
            var size = channel.size();
            var MAX_CHUNK = (int) (size / cores);
            var offset = 0l;
            while (offset < size) {
                int remaining = MAX_CHUNK;
                long count = offset;
                count += MAX_CHUNK;
                if (count > size) {
                    remaining = (int) (size - offset);
                }
                var byteBuffer = channel.map(FileChannel.MapMode.READ_ONLY, offset, remaining);
                if (remaining == MAX_CHUNK) {
                    remaining--;
                    while (byteBuffer.get(remaining) != '\n') {
                        remaining--;
                    }
                    remaining++;
                }
                var worker = new Worker(byteBuffer, remaining);
                listaWorkers.add(worker);
                worker.start();
                offset += remaining;
            }
            for (var worker : listaWorkers) {
                worker.join();
            }
            ordenaryMostrar(mapMedicionesTotales);
        }
        System.out.println("Total: " + (System.nanoTime() - startTime) / 1000000 + "ms");
    }

    public static void ordenaryMostrar(Map<Index, Medicion> mapMediciones) {
        Comparator<Map.Entry<String, Medicion>> comparar = (a, b) -> {
            return a.getKey().compareTo(b.getKey());
        };
        var result = mapMediciones.entrySet().stream()
                .map(e -> {
                    var estacion = e.getKey().toString();
                    return Map.entry(estacion, e.getValue());
                })
                .sorted(comparar)
                .map(e -> {
                    var m = e.getValue();
                    return "%s=%.1f/%.1f/%.1f".formatted(e.getKey(), m.getMin(), m.getAverage(), m.getMax());
                })
                .collect(Collectors.joining(", "));
        System.out.println("{" + result + "}");
    }

    public static class Worker extends Thread {
        private int MAX_ESTACION = 120;
        private Index index;
        private ByteBuffer byteBuffer;
        private ByteBuffer estacion;
        private int temperatura;
        private boolean esNegativo;
        private byte campo;
        private int remaining;
        private int hashCode;
        private Map<Index, Medicion> mapMediciones;

        public Worker(ByteBuffer byteBuffer, int remaining) throws IOException {
            this.byteBuffer = byteBuffer;
            this.remaining = remaining;
            this.mapMediciones = new HashMap<>(500);
            this.estacion = ByteBuffer.allocate(MAX_ESTACION);
            this.temperatura = 0;
            this.esNegativo = false;
            this.campo = 1;
            this.hashCode = 1;
            this.index = new Index();
        }

        @Override
        public void run() {
            procesar();
            for (var entry : mapMediciones.entrySet()) {
                synchronized (mapMedicionesTotales) {
                    var medicion = mapMedicionesTotales.get(entry.getKey());
                    if (medicion == null) {
                        mapMedicionesTotales.put(entry.getKey(), entry.getValue());
                        medicion = entry.getValue();
                    } else {
                        var otraMed = entry.getValue();
                        medicion.merge(otraMed);
                    }
                }
            }
        }

        public void procesar() {
            var count = 0;
            while (count < remaining) {
                var b = byteBuffer.get();
                if (campo == 1) {
                    if (b != ';') {
                        estacion.put(b);
                        hashCode = hashCode * 31 + b;
                    } else {
                        campo = 2;
                    }
                } else {
                    switch (b) {
                        case '\n':
                            proccessLine();
                            break;
                        case '-':
                            esNegativo = true;
                            break;
                        case '.':
                            break;
                        default:
                            temperatura = temperatura * 10 + (b - 48);
                            break;
                    }
                }
                count++;
            }
        }

        private void proccessLine() {
            temperatura = esNegativo ? -temperatura : temperatura;
            estacion.flip();
            index.setByteBuffer(estacion, hashCode);
            var medicion = mapMediciones.get(index);
            if (medicion == null) {
                medicion = new Medicion(temperatura);
                var newEstacion = ByteBuffer.allocate(MAX_ESTACION);
                newEstacion.put(estacion);
                newEstacion.flip();
                var newIndex = new Index();
                newIndex.setByteBuffer(newEstacion, hashCode);
                mapMediciones.put(newIndex, medicion);
            } else {
                medicion.update(temperatura);
            }
            temperatura = 0;
            esNegativo = false;
            campo = 1;
            hashCode = 1;
            estacion.clear();
        }

        public Map<Index, Medicion> getMediciones() {
            return mapMediciones;
        }
    }

    public static class Medicion {
        private int min;
        private int max;
        private int sum;
        private int count;

        public Medicion(int temperatura) {
            this.min = temperatura;
            this.max = temperatura;
            this.sum = temperatura;
            this.count = 1;
        }

        public void update(int temp) {
            this.min = Math.min(this.min, temp);
            this.max = Math.max(this.max, temp);
            this.sum += temp;
            this.count++;
        }

        public void merge(Medicion otraMed) {
            this.min = Math.min(this.min, otraMed.min);
            this.max = Math.max(this.max, otraMed.max);
            this.sum += otraMed.sum;
            this.count += otraMed.count;
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

    public static class Index {
        private ByteBuffer buffer;
        private int hashCode;

        public void setByteBuffer(ByteBuffer buffer, int hashCode) {
            this.buffer = buffer;
            this.hashCode = hashCode;
        }

        @Override
        public int hashCode() {
            return hashCode;
        }

        @Override
        public boolean equals(Object obj) {
            var otro = (Index) obj;
            return buffer.equals(otro.buffer);
        }

        @Override
        public String toString() {
            return new String(buffer.array(), 0, buffer.limit());
        }
    }
}
