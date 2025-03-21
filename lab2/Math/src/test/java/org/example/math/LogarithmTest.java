package org.example.math;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedConstruction;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class LogarithmTest {

    static PrintWriter writer = null;

    @BeforeEach
    void writerOpen(TestInfo testInfo) throws IOException {
        String path = "csv/logarithms/" + testInfo.getTestMethod().orElseThrow().getName() + ".csv";
        writer = new PrintWriter(new FileWriter(path, true));
    }

    @BeforeAll
    static void clearCsv() throws IOException {
        Path directory = Paths.get("csv/logarithms");

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
    @CsvFileSource(resources = "/lnTest.txt")
    void lnTest(double x, double expected) {
        double result = NaturalLog.ln(x);
        assertEquals(expected, result, 0.05);
        NaturalLog.writeToCSV(writer, x, x * 0.1, 5);
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
        NaturalLog.writeToCSV(writer, x, x * 0.1, 5);
    }

    @ParameterizedTest
    @CsvSource({
            "3, 3, 1, 1.098, 1.098",
            "2, 1024, 10, 0.693, 6.931",
            "2.32, 105, 5.53, 0.842, 4.654"
    })
    void logTest(double base, double x, double expected, double baseStub, double xStub) {
        try(MockedStatic<NaturalLog> mockedLn = Mockito.mockStatic(NaturalLog.class)) {
            mockedLn.when(() -> NaturalLog.ln(x)).thenReturn(xStub);
            mockedLn.when(() -> NaturalLog.ln(base)).thenReturn(baseStub);
            double result = Logarithm.log(base, x);
            assertEquals(expected, result, 0.05);
        }

        Logarithm.writeToCSV(writer, base, x, x * 0.5, 5);
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.0, -4, -0.3})
    void lnTestIllegalArgument(double x) {
        assertThrows(IllegalArgumentException.class, () -> NaturalLog.ln(x));
    }

    @ParameterizedTest
    @CsvSource({
            "0, 0, 0, 0",
            "2, 0, 0.693, 0",
            "0, 2, 0, 0.693",
            "1, 2.718, 0, 1"
    })
    void logTestIllegalArgument(double base, double x, double baseStub, double xStub) {
        try(MockedStatic<NaturalLog> mockedLn = Mockito.mockStatic(NaturalLog.class)) {
            if (x <= 0)
                mockedLn.when(() -> NaturalLog.ln(x)).thenThrow(new IllegalArgumentException());
            else
                mockedLn.when(() -> NaturalLog.ln(x)).thenReturn(xStub);

            if (base <= 0)
                mockedLn.when(() -> NaturalLog.ln(base)).thenThrow(new IllegalArgumentException());
            else
                mockedLn.when(() -> NaturalLog.ln(base)).thenReturn(baseStub);
            assertThrows(IllegalArgumentException.class, () -> Logarithm.log(base, x));
        }
    }
}
