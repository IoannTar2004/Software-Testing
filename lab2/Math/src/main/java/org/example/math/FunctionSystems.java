package org.example.math;

import java.io.PrintWriter;
import java.util.function.Function;

import static java.lang.Math.pow;
import static org.example.math.Logarithm.log;
import static org.example.math.NaturalLog.ln;
import static org.example.math.Trigonometry.*;

public class FunctionSystems {

    public static double strangeFunction(double x) {
        if (x <= 0)
            return ((pow(((csc(x) / ctg(x)) * tg(x)), 2) - pow(((tg(x) / csc(x)) - ctg(x)), 2)));

        return pow(((log(3, x) - ln(x)) / ln(x)) * log(3, x) * log(5, x), 3) +
                (pow(log(5, x) / (2 * log(3, x)), 3) * log(5, x));
    }

    public static void writeToCSV(PrintWriter writer, double begin, double step, int count, Function<Double, Double> f,
                           String headers) {
        CSVWriter.write(writer, begin, step, count, f, headers);
    }
}
