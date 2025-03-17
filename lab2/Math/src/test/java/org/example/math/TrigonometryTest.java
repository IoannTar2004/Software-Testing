package org.example.math;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedConstruction;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static java.lang.Math.PI;
import static org.mockito.Mockito.when;

public class TrigonometryTest {

    @ParameterizedTest
    @CsvSource({
            "-1, 1",
            "0, 1",
            "3, 6",
            "5, 120",
            "7, 5040"
    })
    void factorialTest(int x, long expected) {
        long result = new Sine().factorial(x);
        assertEquals(expected, result);
    }

    @ParameterizedTest
    @CsvSource({
            "0, 0",
            "0.785, 0.707",
            "-0.785, -0.707",
            "1.571, 1",
            "10000, -0.306",
            "-10000, 0.306"
    })
    void sinTest(double x, double expected) {
        double result = new Sine().sin(x);
        assertEquals(expected, result, 0.001);
    }

    @ParameterizedTest
    @CsvSource({
            "0, 1, 0",
            "0.785, 0.707, 0.707",
            "-0.785, 0.707, -0.707",
            "1.571, 0, 1",
            "10000, 0.952, -0.306",
            "-10000, 0.952, 0.306"
    })
    void cosTest(double x, double expected, double sin) {
        try(MockedConstruction<Sine> mockedSine = Mockito.mockConstruction(Sine.class,
                (mock, context) -> when(mock.sin(x)).thenReturn(sin))) {

            double result = new Cosine().cos(x);
            assertEquals(expected, result, 0.001);
        }
    }

    @ParameterizedTest
    @CsvSource({
            "0, 0, 0, 1",
            "0.785, 1, 0.707, 0.707",
            "-0.785, -1, -0.707, 0.707",
            "1.561, 100, 0.99995, 0.009999",
            "1.581, -100, 0.99995, -0.009999"
    })
    void tgTest(double x, double expected, double sin, double cos) {
        try(MockedConstruction<Sine> mockedSine = Mockito.mockConstruction(Sine.class,
                (mock, context) -> when(mock.sin(x)).thenReturn(sin));
            MockedConstruction<Cosine> mockedCosine = Mockito.mockConstruction(Cosine.class,
                    (mock, context) -> when(mock.cos(x)).thenReturn(cos))) {

            double result = new Trigonometry().tg(x);
            assertEquals(expected, result, 0.01);
        }
    }

    @ParameterizedTest
    @ValueSource(doubles = {PI / 2, -PI / 2, 5 * PI / 2, -5 * PI / 2})
    void tgTestIllegalArgument(double x) {
        assertThrows(IllegalArgumentException.class, () -> new Trigonometry().tg(x));
    }

    @ParameterizedTest
    @CsvSource({
            "1.571, 0, 0, 1",
            "1.047, 0.577, 0.5, 0.866",
            "-1.047, -0.577, 0.5, -0.866",
            "0.01, 100, 0.99995, 0.009999",
            "-0.01, -100, 0.99995, -0.009999"
    })
    void ctgTest(double x, double expected, double cos, double sin) {
        try(MockedConstruction<Sine> mockedSine = Mockito.mockConstruction(Sine.class,
                (mock, context) -> when(mock.sin(x)).thenReturn(sin));
            MockedConstruction<Cosine> mockedCosine = Mockito.mockConstruction(Cosine.class,
                    (mock, context) -> when(mock.cos(x)).thenReturn(cos))) {

            double result = new Trigonometry().ctg(x);
            assertEquals(expected, result, 0.01);
        }
    }

    @ParameterizedTest
    @ValueSource(doubles = {0, Math.PI, 5 * Math.PI, -5 * Math.PI})
    void ctgTestIllegalArgument(double x) {
        assertThrows(IllegalArgumentException.class, () -> new Trigonometry().ctg(x));
    }

    @ParameterizedTest
    @CsvSource({
            "1.571, 1, 1",
            "-4.712, 1, 1",
            "0.785, 1.414, 0.707",
            "-0.785, -1.414, -0.707"
    })
    void cscTest(double x, double expected, double sin) {
        try(MockedConstruction<Sine> mockedSine = Mockito.mockConstruction(Sine.class,
                (mock, context) -> when(mock.sin(x)).thenReturn(sin))) {

            double result = new Trigonometry().csc(x);
            assertEquals(expected, result, 0.001);
        }
    }

    @ParameterizedTest
    @ValueSource(doubles = {0, Math.PI, 5 * Math.PI, -5 * Math.PI})
    void cscTestIllegalArgument(double x) {
        assertThrows(IllegalArgumentException.class, () -> new Trigonometry().csc(x));
    }
}
