package com.fresco;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

public class ContarLineas {
    public static String FILE = "measurements200.txt";

    public static void main(String[] args) throws FileNotFoundException, IOException {
        var ini = System.currentTimeMillis();
        try (var file = new RandomAccessFile(FILE, "r")) {
            var count = 0l;
            var lineas = 0l;
            var channel = file.getChannel();
            var remaining = channel.size();
            var offset = 0l;
            var enter = compilePattern((byte) '\n');
            while (remaining > 0) {
                var size = remaining > Integer.MAX_VALUE ? Integer.MAX_VALUE : remaining;
                remaining = remaining - size;
                var byteBuffer = channel.map(FileChannel.MapMode.READ_ONLY, offset, size);
                offset += size;
                while (byteBuffer.hasRemaining()) {
                    if (byteBuffer.remaining() >= Long.BYTES) {
                        var b = byteBuffer.getLong();
                        var r = firstInstance(b, enter);
                        if (r < Long.BYTES) {
                            lineas++;
                        }
                        count += Long.BYTES;
                    } else {
                        var b = byteBuffer.get();
                        if (b == '\n') {
                            lineas++;
                        }
                        count++;
                    }
                }
            }
            System.out.println(" " + lineas + " " + count + " " + FILE);
        }
        var time = System.currentTimeMillis() - ini;
        System.out.println(time / 1000.0);
    }

    public static long compilePattern(byte byteToFind) {
        long pattern = byteToFind & 0xFFL;
        return pattern
                | (pattern << 8)
                | (pattern << 16)
                | (pattern << 24)
                | (pattern << 32)
                | (pattern << 40)
                | (pattern << 48)
                | (pattern << 56);
    }

    public static int firstInstance(long word, long pattern) {
        long input = word ^ pattern;
        long tmp = (input & 0x7F7F7F7F7F7F7F7FL) + 0x7F7F7F7F7F7F7F7FL;
        tmp = ~(tmp | input | 0x7F7F7F7F7F7F7F7FL);
        return Long.numberOfLeadingZeros(tmp) >>> 3;
    }
}
