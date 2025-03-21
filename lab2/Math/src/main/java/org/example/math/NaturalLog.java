package org.example.math;

import java.io.PrintWriter;

public class NaturalLog {

    public static double ln(double x) {
        if (x <= 0) throw new IllegalArgumentException();
        if (x == 1) return 0;

        int n = x < 1 && x > 0.0025 ? (int) Math.ceil(3 / x) : x > 1 && x < 400 ? (int) Math.ceil(3 * x) : 1200;
        double result = 0;
        double mult = 1;
        for (int i = 1; i <= n; i++) {
            mult = x < 1 ? -(Math.abs(mult) * (1 - x)) : mult * (1 - 1 / x);
            result += mult / i;
        }

        return result;
    }

    public static void writeToCSV(PrintWriter writer, double begin, double step, int count) {
        CSVWriter.write(writer, begin, step, count, NaturalLog::ln, "X,ln(X)");
    }
}
