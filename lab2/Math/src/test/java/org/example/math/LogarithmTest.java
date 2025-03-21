package org.example.math;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedConstruction;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;


public class LogarithmTest {

    @ParameterizedTest
    @CsvFileSource(resources = "/lnTest.txt")
    void lnTest(double x, double expected) {
        double result = new NaturalLog().ln(x);
        assertEquals(expected, result, 0.05);
    }

    @ParameterizedTest
    @CsvSource({
        "0.0001, -9.21, 2",
        "15000, 9.615, 2.1",
        "300000, 12.611, 5"
    })
    void lnTestDiverge(double x, double expected, double div) {
        double result = new NaturalLog().ln(x);
        assertEquals(expected, result, div);
    }

    @ParameterizedTest
    @CsvSource({
            "3, 3, 1, 1.098, 1.098",
            "2, 1024, 10, 0.693, 6.931",
            "2.32, 105, 5.53, 0.842, 4.654"
    })
    void logTest(double base, double x, double expected, double baseStub, double xStub) {
        try(MockedConstruction<NaturalLog> mockedLn = Mockito.mockConstruction(NaturalLog.class,
                (mock, context) -> {
                    when(mock.ln(base)).thenReturn(baseStub);
                    when(mock.ln(x)).thenReturn(xStub);
                })) {

            double result = new Logarithm().log(base, x);
            assertEquals(expected, result, 0.05);
        }
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.0, -4, -0.3})
    void lnTestIllegalArgument(double x) {
        assertThrows(IllegalArgumentException.class, () -> new NaturalLog().ln(x));
    }

    @ParameterizedTest
    @CsvSource({
            "0, 0, 0, 0",
            "2, 0, 0.693, 0",
            "0, 2, 0, 0.693",
            "1, 2.718, 0, 1"
    })
    void logTestIllegalArgument(double base, double x, double baseStub, double xStub) {
        try(MockedConstruction<NaturalLog> mockedLn = Mockito.mockConstruction(NaturalLog.class,
                (mock, context) -> {
                    if (x <= 0)
                        when(mock.ln(x)).thenThrow(new IllegalArgumentException());
                    else
                        when(mock.ln(x)).thenReturn(xStub);

                    if (base <= 0)
                        when(mock.ln(base)).thenThrow(new IllegalArgumentException());
                    else
                        when(mock.ln(base)).thenReturn(baseStub);
                })) {

            assertThrows(IllegalArgumentException.class, () -> new Logarithm().log(base, x));
        }
    }
}
