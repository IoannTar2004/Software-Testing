package org.example.math;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FunctionSystemsTest {

    @ParameterizedTest
    @CsvSource({
            "-0.85187, 0",
            "-7, -1.622",
            "-8.572, 0.015",
            "-0.001, -1000000"
    })
    void strangeFunctionTestLE(double x, double expected) {
        double result = FunctionSystems.strangeFunction(x);
        assertEquals(expected, result, 1);
    }

    @ParameterizedTest
    @CsvSource({
            "10, 0.037",
            "50, -0.372",
            "23.57, -0.052"
    })
    void strangeFunctionTestG(double x, double expected) {
        double result = FunctionSystems.strangeFunction(x);
        assertEquals(expected, result, 1);
    }
}
