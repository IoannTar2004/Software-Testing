package org.example.math;

public class Cosine {

    public double cos(double x) {
        return Math.sqrt(1 - Math.pow(new Sine().sin(x), 2));
    }

    public void writeToCSV(String filename, double begin, double step, int count) {
        CSVWriter.write(filename, begin, step, count, this::cos, "X,cos(X)");
    }
}
