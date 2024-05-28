package com.fresco;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class ContarArchivosDirectorios2 {

    public static AtomicInteger countInstance = new AtomicInteger();

    public static void main(String[] args) throws IOException {
        var rutas = List.of("/usr/", "/usr/bin/", "/usr/lib/");
        DirectorioEstadistica dirEstadistica;

        countInstance.set(0);
        dirEstadistica = rutas.stream().parallel()
                .map(d -> list(d))
                .flatMap(Stream::sequential)
                .collect(DirectorioEstadistica::new, DirectorioEstadistica::accept, DirectorioEstadistica::combine);

        System.out.println(dirEstadistica);
        System.out.println(countInstance.get());

        String path = "/usr/bin/";
        countInstance.set(0);
        dirEstadistica = Files.list(Paths.get(path))
                .collect(DirectorioEstadistica::new, DirectorioEstadistica::accept, DirectorioEstadistica::combine);

        System.out.println(dirEstadistica);
        System.out.println(countInstance.get());

        countInstance.set(0);
        dirEstadistica = Files.list(Paths.get(path))
                .map(p -> new DirectorioEstadistica(p))
                .reduce(new DirectorioEstadistica(), DirectorioEstadistica::combine);

        System.out.println(dirEstadistica);
        System.out.println(countInstance.get());

    }

    public static Stream<Path> list(String dir) {
        try {
            return Files.list(Paths.get(dir));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static class DirectorioEstadistica {
        private int nroArc;
        private int nroDir;
        private int total;

        public DirectorioEstadistica() {
            nroArc = 0;
            nroDir = 0;
            total = 0;
            countInstance.incrementAndGet();
        }

        public DirectorioEstadistica(Path path) {
            nroArc = Files.isRegularFile(path) ? nroArc + 1 : nroArc;
            nroDir = Files.isDirectory(path) ? nroDir + 1 : nroDir;
            total++;
            countInstance.incrementAndGet();
        }

        public void accept(Path path) {
            nroArc = Files.isRegularFile(path) ? nroArc + 1 : nroArc;
            nroDir = Files.isDirectory(path) ? nroDir + 1 : nroDir;
            total++;
        }

        public DirectorioEstadistica combine(DirectorioEstadistica other) {
            nroArc += other.nroArc;
            nroDir += other.nroDir;
            total += other.total;
            return this;
        }

        public int getNroArc() {
            return nroArc;
        }

        public int getNroDir() {
            return nroDir;
        }

        public int getTotal() {
            return total;
        }

        @Override
        public String toString() {
            return "DirectorioEstadistica [nroArc=" + nroArc + ", nroDir=" + nroDir + ", total=" + total + "]";
        }
    }
}
