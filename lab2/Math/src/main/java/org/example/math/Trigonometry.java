package org.example.math;

import java.io.PrintWriter;
import java.util.function.Function;

import static java.lang.Math.*;

public class Trigonometry {

    public static double tg(double x) {
        if (x % (PI / 2) == 0 && x % PI != 0) throw new IllegalArgumentException();
        return Sine.sin(x) / Cosine.cos(x);
    }

    public static double ctg(double x) {
        if (x % PI == 0) throw new IllegalArgumentException();
        return Cosine.cos(x) / Sine.sin(x);
    }

    public static double csc(double x) {
        if (x % PI == 0) throw new IllegalArgumentException();
        return 1 / Sine.sin(x);
    }

    public static void writeToCSV(PrintWriter writer, double begin, double step, int count, Function<Double, Double> f,
                                  String headers) {
        CSVWriter.write(writer, begin, step, count, f, headers);
    }
}
