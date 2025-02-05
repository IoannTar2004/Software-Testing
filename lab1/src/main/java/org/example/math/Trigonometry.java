package org.example.math;

import static java.lang.Math.*;

public class Trigonometry {

    private long double_factorial(int x) {
        if (x <= 0) return 1;

        long result = x;
        for (int i = x - 2; i > 1; i -= 2)
            result *= i;
        return result;
    }

    public double arcsin_power(int n, double x) {
        if (n <= 0 || n > 17) throw new IllegalArgumentException("Число n должно быть в пределах 0 < n <= 17!");
        if (abs(x) > 1) throw new IllegalArgumentException("Аргумент x должен находится в пределе -1 <= x <= 1!");

        double result = 0;
        for (int i = 0; i < n; i++) {
            double a = (double_factorial(2 * i - 1) * pow(x, 2 * i + 1));
            double b = (double_factorial(2 * i) * (2 * i + 1));
            result += a / b;
        }
        return result;
    }
}
