package org.example.math;

public class Logarithm {

    public static double log(double base, double x) {
        if (base == 0 || base == 1) throw new IllegalArgumentException();
        NaturalLog ln = new NaturalLog();
        return NaturalLog.ln(x) / NaturalLog.ln(base);
    }

    public void writeToCSV(String filename, double base, double begin, double step, int count) {
        CSVWriter.write(filename, base, begin, step, count, Logarithm::log, "Base,X,ln(X)");
    }
}