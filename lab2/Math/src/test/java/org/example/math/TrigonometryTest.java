package org.example.math;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TrigonometryTest {

    PrintWriter writer = null;

    @BeforeEach
    void writerOpen(TestInfo testInfo) throws IOException {
        String path = "csv/trigonometry/" + testInfo.getTestMethod().orElseThrow().getName() + ".csv";
        writer = new PrintWriter(new FileWriter(path, true));
    }

    @BeforeAll
    static void clearCsv() throws IOException {
        Path directory = Paths.get("csv/trigonometry");

        if (Files.exists(directory) && Files.isDirectory(directory)) {
            try (var files = Files.list(directory)) {
                files.forEach(file -> {
                    try {
                        Files.delete(file);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
        }
    }

    @AfterEach
    void writerClose() {
        writer.close();
    }

    @ParameterizedTest
    @CsvSource({
            "-1, 1",
            "0, 1",
            "3, 6",
            "5, 120",
            "7, 5040"
    })
    void factorialTest(int x, long expected) {
        long result = Sine.factorial(x);
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
        double result = Sine.sin(x);
        assertEquals(expected, result, 0.001);
        Sine.writeToCSV(writer, x, x * 0.5, 5);
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
        try (MockedStatic<Sine> mockedSine = Mockito.mockStatic(Sine.class)) {
            mockedSine.when(() -> Sine.sin(x)).thenReturn(sin);

            double result = Cosine.cos(x);
            assertEquals(expected, result, 0.001);
        }

        Cosine.writeToCSV(writer, x, x * 0.5, 5);
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
        try (MockedStatic<Sine> mockedSine = Mockito.mockStatic(Sine.class);
             MockedStatic<Cosine> mockedCosine = Mockito.mockStatic(Cosine.class)) {

            mockedSine.when(() -> Sine.sin(x)).thenReturn(sin);
            mockedCosine.when(() -> Cosine.cos(x)).thenReturn(cos);

            double result = Trigonometry.tg(x);
            assertEquals(expected, result, 0.01);
        }

        Trigonometry.writeToCSV(writer, x, x * 0.5, 5, Trigonometry::tg, "X,tg(X)");
    }

    @ParameterizedTest
    @ValueSource(doubles = {Math.PI / 2, -Math.PI / 2, 5 * Math.PI / 2, -5 * Math.PI / 2})
    void tgTestIllegalArgument(double x) {
        assertThrows(IllegalArgumentException.class, () -> Trigonometry.tg(x));
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
        try (MockedStatic<Cosine> mockedCosine = Mockito.mockStatic(Cosine.class);
             MockedStatic<Sine> mockedSine = Mockito.mockStatic(Sine.class)) {

            mockedCosine.when(() -> Cosine.cos(x)).thenReturn(cos);
            mockedSine.when(() -> Sine.sin(x)).thenReturn(sin);

            double result = Trigonometry.ctg(x);
            assertEquals(expected, result, 0.01);
        }

        Trigonometry.writeToCSV(writer, x, x * 0.5, 5, Trigonometry::ctg, "X,ctg(X)");
    }

    @ParameterizedTest
    @ValueSource(doubles = {0, Math.PI, 5 * Math.PI, -5 * Math.PI})
    void ctgTestIllegalArgument(double x) {
        assertThrows(IllegalArgumentException.class, () -> Trigonometry.ctg(x));
    }

    @ParameterizedTest
    @CsvSource({
            "1.571, 1, 1",
            "-4.712, 1, 1",
            "0.785, 1.414, 0.707",
            "-0.785, -1.414, -0.707"
    })
    void cscTest(double x, double expected, double sin) {
        try (MockedStatic<Sine> mockedSine = Mockito.mockStatic(Sine.class)) {
            mockedSine.when(() -> Sine.sin(x)).thenReturn(sin);

            double result = Trigonometry.csc(x);
            assertEquals(expected, result, 0.001);
        }

        Trigonometry.writeToCSV(writer, x, x * 0.5, 5, Trigonometry::csc, "X,csc(X)");
    }

    @ParameterizedTest
    @ValueSource(doubles = {0, Math.PI, 5 * Math.PI, -5 * Math.PI})
    void cscTestIllegalArgument(double x) {
        assertThrows(IllegalArgumentException.class, () -> Trigonometry.csc(x));
    }

}