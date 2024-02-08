package com.fresco;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Desafio1BR5 {
    public static String FILE = "./measurementsbig.txt";

    public static void main(String[] args) throws IOException, InterruptedException {
        var ini = System.currentTimeMillis();
        int cores = Runtime.getRuntime().availableProcessors();
        var mapMediciones = new HashMap<ByteBuffer, Medicion>();
        var workers = new ArrayList<Worker>();
        try (var file = new RandomAccessFile(FILE, "r")) {
            var channel = file.getChannel();
            var size = channel.size();
            int MAX_CHUNK = (int) (size / cores);
            var offset = 0l;
            while (offset < size) {
                int remaining = MAX_CHUNK;
                long limit = offset;
                limit += MAX_CHUNK;
                if (limit > size) {
                    remaining = (int) (size - offset);
                    limit = size;
                }
                if (remaining == MAX_CHUNK) {
                    file.seek(limit);
                    for (;;) {
                        var b = file.read();
                        if (b == '\n' || b == '\r')
                            break;
                        limit++;
                    }
                    limit--;
                }
                var worker = new Worker(offset, limit);
                workers.add(worker);
                worker.start();
                offset = limit;
            }
        }
        for (var worker : workers) {
            worker.join();
            var medicionesWorker = worker.getMediciones();
            for (var entry : medicionesWorker.entrySet()) {
                var medicion = mapMediciones.get(entry.getKey());
                if (medicion == null) {
                    mapMediciones.put(entry.getKey(), entry.getValue());
                } else {
                    var otraMed = entry.getValue();
                    medicion.merge(otraMed);
                }
            }
        }
        ordenaryMostrar(mapMediciones);
        var time = System.currentTimeMillis() - ini;
        System.out.println(time / 1000.0);
    }

    /*
     * 
     */
    public static void ordenaryMostrar(Map<ByteBuffer, Medicion> mapMediciones) {
        Comparator<Map.Entry<String, Medicion>> comparar = (a, b) -> {
            return a.getKey().compareTo(b.getKey());
        };
        var result = mapMediciones.entrySet().stream()
                .map(e -> {
                    var estacion = new String(e.getKey().array());
                    return Map.entry(estacion, e.getValue());
                })
                .sorted(comparar)
                .map(e -> {
                    var m = e.getValue();
                    return "%s=%.1f/%.1f/%.1f".formatted(e.getKey(), m.getMin(), m.getAverage(), m.getMax());
                })
                .collect(Collectors.joining(","));
        System.out.println("{" + result + "}");
    }

    /*
     * 
     */
    public static class Worker extends Thread implements AutoCloseable {
        private int MAX_BUFFER = 4096 * 8;
        private int MAX_LINE = 255;
        private ByteBuffer byteBuffer;
        private ByteBuffer estacion;
        private int temperatura;
        private boolean esNegativo;
        private int campo;
        private Map<ByteBuffer, Medicion> mapMediciones;
        private FileChannel channel;
        private RandomAccessFile file;
        private long limit;
        private long offset;

        public Worker(long offset, long limit) throws IOException {
            file = new RandomAccessFile(FILE, "r");
            file.seek(offset);
            channel = file.getChannel();
            mapMediciones = new HashMap<>();
            estacion = ByteBuffer.allocate(MAX_LINE);
            byteBuffer = ByteBuffer.allocate(MAX_BUFFER);
            temperatura = 0;
            esNegativo = false;
            campo = 1;
            this.limit = limit;
            this.offset = offset;
        }

        /*
         * 
         */
        public void procesar() throws IOException {
            long reads = offset;
            while (reads < limit) {
                int count = channel.read(byteBuffer);
                if (count == -1) {
                    break;
                }
                byteBuffer.flip();
                while (byteBuffer.hasRemaining() && reads < limit ) {
                    var b = byteBuffer.get();
                    if (campo == 1 && b != ';') {
                        estacion.put(b);
                    } else {
                        switch (b) {
                            case '\n':
                                proccessLine();
                                break;
                            case '-':
                                esNegativo = true;
                                break;
                            case ';':
                                campo = 2;
                                break;
                            case '.':
                                break;
                            default:
                                temperatura = temperatura * 10 + (b - 48);
                                break;
                        }
                    }
                    reads++;
                }
                byteBuffer.clear();
            }
        }

        /*
         * 
         */
        private void proccessLine() {
            temperatura = esNegativo ? -temperatura : temperatura;
            estacion.flip();
            var medicion = mapMediciones.get(estacion);
            if (medicion == null) {
                var newEstacion = ByteBuffer.allocate(MAX_LINE);
                newEstacion.put(estacion);
                newEstacion.flip();
                medicion = new Medicion(temperatura, temperatura, temperatura);
                mapMediciones.put(newEstacion, medicion);
            } else {
                medicion.update(temperatura);
            }
            estacion.clear();
            temperatura = 0;
            esNegativo = false;
            campo = 1;
        }

        /*
         * 
         */
        public Map<ByteBuffer, Medicion> getMediciones() {
            return mapMediciones;
        }

        @Override
        public void close() throws Exception {
            file.close();
        }

        @Override
        public void run() {
            try {
                procesar();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /*
     * 
     */
    public static class Medicion {
        public int min;
        public int max;
        public int sum;
        public int count;

        public Medicion(int min, int max, int sum) {
            this.min = min;
            this.max = max;
            this.sum = sum;
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
}
