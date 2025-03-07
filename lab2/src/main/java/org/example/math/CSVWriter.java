package org.example.math;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.function.BiFunction;
import java.util.function.Function;

public class CSVWriter {

    public static void write(String filename, double begin, double step, int count, Function<Double, Double> f,
                             String headers) {
        try (PrintWriter writer = new PrintWriter(filename)) {
            double cur = begin;
            writer.println(headers);
            for (int i = 0; i < count; i++) {
                writer.println(String.format(Locale.ENGLISH, "%.3f,%.3f", cur, f.apply(cur)));
                cur += step;
            }
            writer.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void write(String filename, double fixed, double begin,
                             double step, int count, BiFunction<Double, Double, Double> f, String headers) {
        try (PrintWriter writer = new PrintWriter(filename)) {
            double cur = begin;
            writer.println(headers);
            for (int i = 0; i < count; i++) {
                writer.println(String.format(Locale.ENGLISH, "%.3f,%.3f,%.3f", fixed, cur, f.apply(fixed, cur)));
                cur += step;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
