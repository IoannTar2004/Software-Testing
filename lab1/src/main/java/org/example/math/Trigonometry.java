package org.example.math;

import java.math.BigInteger;
import java.util.LinkedList;
import java.util.List;

import static java.lang.Math.*;

public class Trigonometry {

    public static BigInteger factorial(int x) {
        if (x == 0 || x == 1) return BigInteger.ONE;
        BigInteger result = BigInteger.ONE;
        for (int i = 2; i <= x; i++)
            result = result.multiply(BigInteger.valueOf(i));
        return result;
    }

    public static long combination(int n, int k) {
        return factorial(n).divide(factorial(k).multiply(factorial(n - k))).longValue();
    }

    public static double bernulli(int n) {
        if (n % 2 == 1 && n != 1) return 0.0;

        List<Double> B = new LinkedList<>(List.of(1D));
        for (int i = 1; i <= n; i++) {
            double sum = 0;
            for (int k = 0; k <= i - 1; k++)
                sum += combination(i + 1, k) * B.get(k);
            B.add(-1D / (i + 1) * sum);
        }
        return B.get(B.size() - 1);
    }

    public static double tanPower(double x, int n) {
        if (abs(x) > PI / 2) throw new IllegalArgumentException("Число x должно быть по модулю меньше PI / 2");
        double res = 0;
        for (int i = 1; i < n; i++) {
            res += (abs(bernulli(2*i)) * pow(2, 2*i) * (pow(2, 2*i) - 1) * pow(x, 2*i - 1)) / factorial(2*i).doubleValue();
        }
        return res;
    }
}
