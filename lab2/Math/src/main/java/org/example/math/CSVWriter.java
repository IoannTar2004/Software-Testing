package org.example.math;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.nio.channels.FileChannel;
import java.util.Locale;
import java.util.function.BiFunction;
import java.util.function.Function;

public class CSVWriter {

    public static void write(PrintWriter writer, double begin, double step, int count, Function<Double, Double> f,
                             String headers) {
        double cur = begin;
        writer.println(headers);
        for (int i = 0; i < count; i++) {
            writer.println(String.format(Locale.ENGLISH, "%.4f,%.4f", cur, f.apply(cur)));
            cur += step;
        }
    }

    public static void write(PrintWriter writer, double fixed, double begin,
                             double step, int count, BiFunction<Double, Double, Double> f, String headers) {
            double cur = begin;
            writer.println(headers);
            for (int i = 0; i < count; i++) {
                writer.println(String.format(Locale.ENGLISH, "%.4f,%.4f,%.4f", fixed, cur, f.apply(fixed, cur)));
                cur += step;
            }
    }
}
