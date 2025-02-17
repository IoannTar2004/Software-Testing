package org.example.math;

import static org.junit.jupiter.api.Assertions.*;
import static java.lang.Math.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

public class TrigonometryTest {

    private final int N = 55;

    @ParameterizedTest
    @CsvSource({
            "0, 0",
            "0.7853, 1",
            "0.5235, 0.5774",
            "-0.5235, -0.5774",
            "1.0472, 1.7321",
            "1.4708, 9.967",
            "-1.4708, -9.967"
    })
    public void simpleInputs(double input, double expected) {
        double result = Trigonometry.tanPower(input, N);
        assertEquals(expected, result, 0.08);
    }

    @ParameterizedTest
    @ValueSource(doubles = {1.58, -1.58, Integer.MAX_VALUE, Integer.MIN_VALUE})
    public void illegalArguments(double input) {
        assertThrows(IllegalArgumentException.class, () -> Trigonometry.tanPower(input, N));
    }

    @ParameterizedTest
    @CsvSource({
            "0, 1",
            "1, -0.5",
            "2, 0.1666",
            "3, 0",
            "4, -0.03333"
    })
    public void bernulliTest(int input, double expected) {
        double res = Trigonometry.bernulli(input);
        assertEquals(expected, res, 1e-2);
    }
}
