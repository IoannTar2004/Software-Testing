package org.example.math;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FunctionSystemsTest {

    PrintWriter writer = null;

    @BeforeEach
    void writerOpen(TestInfo testInfo) throws IOException {
        String path = "csv/functionSystems/" + testInfo.getTestMethod().orElseThrow().getName() + ".csv";
        writer = new PrintWriter(new FileWriter(path, true));
    }

    @BeforeAll
    static void clearCsv() throws IOException {
        Path directory = Paths.get("csv/functionSystems");

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
            "-0.85187, 0",
            "-7, -1.622",
            "-8.572, 0.015",
            "-0.001, -1000000"
    })
    void strangeFunctionTestLE(double x, double expected) {
        double result = FunctionSystems.strangeFunction(x);
        assertEquals(expected, result, 1);
        FunctionSystems.writeToCSV(writer, x, x * 0.1, 5, FunctionSystems::strangeFunction, "X,f(X)");
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
        FunctionSystems.writeToCSV(writer, x, x * 0.1, 5, FunctionSystems::strangeFunction, "X,f(X)");
    }
}
