package org.example.math;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class LogarithmTest {

    @ParameterizedTest
    @CsvFileSource(resources = "/lnTest.txt")
    void lnTest(double x, double expected) {
        double result = NaturalLog.ln(x);
        assertEquals(expected, result, 0.05);
    }

    @ParameterizedTest
    @CsvSource({
        "0.0001, -9.21, 2",
        "15000, 9.615, 2.1",
        "300000, 12.611, 5"
    })
    void lnTestDiverge(double x, double expected, double div) {
        double result = NaturalLog.ln(x);
        assertEquals(expected, result, div);
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.0, -4, -0.3})
    void lnTestIllegalArgument(double x) {
        assertThrows(IllegalArgumentException.class, () -> NaturalLog.ln(x));
    }

    @ParameterizedTest
    @CsvSource({
            "3, 3, 1, 1.098, 1.098",
            "2, 1024, 10, 0.693, 6.931",
            "2.32, 105, 5.53, 0.842, 4.654"
    })
    void logTest(double base, double x, double expected, double stub1, double stub2) {
        try (MockedStatic<NaturalLog> mockedStatic = Mockito.mockStatic(NaturalLog.class)) {
            mockedStatic.when(() -> NaturalLog.ln(base)).thenReturn(stub1);
            mockedStatic.when(() -> NaturalLog.ln(x)).thenReturn(stub2);

            double result = Logarithm.log(base, x);
            assertEquals(expected, result, 0.05);
        }
    }

    @ParameterizedTest
    @CsvSource({
            "0, 0, 0, 0",
            "2, 0, 0.693, 0",
            "0, 2, 0, 0.693",
            "1, 2.718, 0, 1"
    })
    void logTestIllegalArgument(double base, double x, double stub1, double stub2) {
        try (MockedStatic<NaturalLog> mockedStatic = Mockito.mockStatic(NaturalLog.class)) {
            if (x <= 0)
                mockedStatic.when(() -> NaturalLog.ln(x)).thenThrow(IllegalArgumentException.class);
            else
                mockedStatic.when(() -> NaturalLog.ln(x)).thenReturn(stub1);

            if (base <= 0)
                mockedStatic.when(() -> NaturalLog.ln(base)).thenThrow(IllegalArgumentException.class);
            else
                mockedStatic.when(() -> NaturalLog.ln(base)).thenReturn(stub2);

            assertThrows(IllegalArgumentException.class, () -> Logarithm.log(base, x));
        }
    }
}
