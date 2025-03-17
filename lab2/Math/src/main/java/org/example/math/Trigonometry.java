package org.example.math;

import java.util.function.Function;

import static java.lang.Math.*;

public class Trigonometry {

    public double tg(double x) {
        if (x % (PI / 2) == 0 && x % PI != 0) throw new IllegalArgumentException();
        return new Sine().sin(x) / new Cosine().cos(x);
    }

    public double ctg(double x) {
        if (x % PI == 0) throw new IllegalArgumentException();
        return new Cosine().cos(x) / new Sine().sin(x);
    }

    public double csc(double x) {
        if (x % PI == 0) throw new IllegalArgumentException();
        return 1 / new Sine().sin(x);
    }

    public void writeToCSV(String filename, double begin, double step, int count, Function<Double, Double> f,
                           String headers) {
        CSVWriter.write(filename, begin, step, count, f, headers);
    }
}
