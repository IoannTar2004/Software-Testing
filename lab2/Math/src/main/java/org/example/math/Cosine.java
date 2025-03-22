package org.example.math;

import java.io.PrintWriter;

public class Cosine {

    public static double cos(double x) {
        return Math.sqrt(1 - Math.pow(Sine.sin(x), 2));
    }

    public static void writeToCSV(PrintWriter writer, double begin, double step, int count) {
        CSVWriter.write(writer, begin, step, count, Cosine::cos, "X,cos(X)");
    }
}
