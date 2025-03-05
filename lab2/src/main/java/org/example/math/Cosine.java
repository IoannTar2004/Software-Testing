package org.example.math;

public class Cosine {

    public static double cos(double x) {
        return Math.sqrt(1 - Math.pow(Sine.sin(x), 2));
    }
}
