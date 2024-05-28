package com.fresco;

public class TextToDouble {
    public static void main(String[] args) {

        printDouble( strToDouble("aa;99.9".getBytes(), 2));
        printDouble(strToDouble("aa;-99.9".getBytes(), 2));
        printDouble(strToDouble("aa;9.9".getBytes(), 2));
        printDouble(strToDouble("aa;16.1".getBytes(), 2));
        printDouble(strToDouble("aa;16.1".getBytes(), 2));
        printDouble(strToDouble("aa;16.1".getBytes(), 2));
        printDouble(strToDouble("aa;16.0".getBytes(), 2));
    }

    public static void printDouble(double num){
        System.out.println("%.1f".formatted(num));
    }

    public static double strToDouble(byte linea[], int posSeparator) {
        double number[] = { 0, 0 };
        int pos = 0;
        boolean esNegativo = false;
        for (int i = posSeparator + 1; i < linea.length; i++) {
            switch (linea[i]) {
                case '-':
                    esNegativo = true;
                    break;
                case '0', '1', '2', '3', '4':
                case '5', '6', '7', '8', '9':
                    number[pos] = number[pos] * 10;
                    number[pos] = number[pos] + (linea[i] - '0');
                    break;
                case '.':
                    pos = 1;
                    break;
            }
        }
        double num = number[0];
        if (number[1] > 0){
            num = num + (number[1] / 10);
        }
        if (esNegativo) {
            num = num * -1;
        }
        return num;
    }
}
