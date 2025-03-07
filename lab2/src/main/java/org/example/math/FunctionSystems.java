package org.example.math;

import java.util.function.Function;

import static java.lang.Math.pow;

public class FunctionSystems {

    public double strangeFunction(double x) {
        if (x <= 0) {
            Trigonometry t = new Trigonometry();
            return ((pow(((t.csc(x) / t.ctg(x)) * t.tg(x)), 2) - pow(((t.tg(x) / t.csc(x)) - t.ctg(x)), 2)));
        }

        Logarithm l = new Logarithm();
        NaturalLog ln = new NaturalLog();
        return pow(((l.log(3, x) - ln.ln(x)) / ln.ln(x)) * l.log(3, x) * l.log(5, x), 3) +
                (pow(l.log(5, x) / (2 * l.log(3, x)), 3) * l.log(5, x));
    }

    public void writeToCSV(String filename, double begin, double step, int count, Function<Double, Double> f,
                           String headers) {
        CSVWriter.write(filename, begin, step, count, f, headers);
    }
}
