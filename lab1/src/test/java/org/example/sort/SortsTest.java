package org.example.sort;

import com.sun.management.OperatingSystemMXBean;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.management.ManagementFactory;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SortsTest {

    PrintStream originalOut;
    final OperatingSystemMXBean osBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();

    ByteArrayOutputStream getOutputStream() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        originalOut = System.out;
        System.setOut(new PrintStream(outputStream));
        return outputStream;
    }

    Path getPathFromResources(String path) throws URISyntaxException {
        return Path.of(getClass().getClassLoader().getResource(path).toURI());
    }

    @AfterEach
    void tearDown() throws InterruptedException {
        Thread.sleep(500);
        if (originalOut != null)
            System.setOut(originalOut);
        System.out.printf("CPU load: %.3f%n", osBean.getProcessCpuLoad() * 100);
    }

    static Stream<Arguments> selectionSortInputs() {
        return Stream.of(
                Arguments.of("sortTest1.txt", new Integer[]{5, 3, 10, 5, 2, 57, 44, 1}),
                Arguments.of("sortTest2.txt", new Double[]{4.0, 2.1, 5.96, 5.96, 0.0, -0.4}),
                Arguments.of("sortTest3.txt", new Integer[]{4, 4, 4, 4}),
                Arguments.of("sortTest4.txt", new String[]{"Chocolate", "Work", "Adidas", "Volga", "Ivan"})
        );
    }

    @ParameterizedTest
    @MethodSource("selectionSortInputs")
    <T extends Comparable<T>> void selectionSortTest(String filepath, T[] array) throws URISyntaxException, IOException {
        String excepted = Files.readString(getPathFromResources(filepath));
        ByteArrayOutputStream outputStream = getOutputStream();
        Sorts.selectionSort(array);
        assertEquals(excepted, outputStream.toString());
    }
}
