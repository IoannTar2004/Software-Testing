package org.example.math;

public class Logarithm {

    public static double log(double base, double x) {
        if (base == 1) throw new IllegalArgumentException();
        return NaturalLog.ln(x) / NaturalLog.ln(base);
    }
}
