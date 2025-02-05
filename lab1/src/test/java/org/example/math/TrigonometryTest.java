package org.example.math;

import static org.junit.jupiter.api.Assertions.*;
import static java.lang.Math.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class TrigonometryTest {

    private final Trigonometry math = new Trigonometry();

    @ParameterizedTest
    @CsvSource({
            "0.5, 0.5236",
            "0.86603, 1.0472",
            "-0.9, -1.11977",
            "-0.5, -0.5236"
    })
    public void simpleInputs(double input, double expected) {
        double result = math.arcsin_power(17, input);
        double error = abs((expected - result) / expected) * 100;
        assertTrue(error <= 0.05, String.format(
                "The resulting error %f is greater than expected error 0,05", error));
    }

    @ParameterizedTest
    @CsvSource({
            "1, 1.5708",
            "-1, -1.5708"
    })
    public void edgeInputs(double input, double expected) {
        double result = math.arcsin_power(17, input);
        double error = abs((expected - result) / expected) * 100;
        assertTrue(error <= 8, String.format(
                "The resulting error %f is greater than expected error 8", error));
    }

    @ParameterizedTest
    @CsvSource({
            "0, 0.5",
            "-5, 0.5",
            "10, 10",
            "10, -1.05"
    })
    public void illegalArguments(int n, double input) {
        assertThrows(IllegalArgumentException.class, () -> math.arcsin_power(n, input));
    }

    @Test
    public void testZero() {
        assertEquals(math.arcsin_power(1, 0), 0);
    }
}
