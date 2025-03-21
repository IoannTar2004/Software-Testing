package org.example.math;

import java.io.PrintWriter;

import static org.example.math.NaturalLog.ln;

public class Logarithm {

    public static double log(double base, double x) {
        if (base == 0 || base == 1) throw new IllegalArgumentException();
        return ln(x) / ln(base);
    }

    public static void writeToCSV(PrintWriter writer, double base, double begin, double step, int count) {
        CSVWriter.write(writer, base, begin, step, count, Logarithm::log, "Base,X,ln(X)");
    }
}