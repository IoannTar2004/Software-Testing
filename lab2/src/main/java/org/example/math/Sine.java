package org.example.math;

import static java.lang.Math.pow;

public class Sine {

    public long factorial(int x) {
        if (x <= 1) return 1;
        long result = x;
        for (int i = x - 1; i > 1; i--)
            result *= i;

        return result;
    }
    public double sin(double x) {
        x %= 2 * Math.PI;
        double result = 0;
        for (int i = 0; i < 10; i++)
            result += (pow(-1, i) * pow(x, 2 * i + 1)) / factorial(2 * i + 1);

        return result;
    }

    public void writeToCSV(String filename, double begin, double step, int count) {
        CSVWriter.write(filename, begin, step, count, this::sin, "X,sin(X)");
    }
}
