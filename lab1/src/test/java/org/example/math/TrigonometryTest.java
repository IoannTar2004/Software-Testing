package org.example.math;

import static org.junit.jupiter.api.Assertions.*;

import com.sun.management.OperatingSystemMXBean;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.lang.management.ManagementFactory;

public class TrigonometryTest {

    final int N = 55;
    final OperatingSystemMXBean osBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();

    @AfterEach
    void tearDown() throws InterruptedException {
        Thread.sleep(500);
        System.out.printf("CPU load: %.3f%n", osBean.getProcessCpuLoad() * 100);
    }

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
    void simpleInputs(double input, double expected) {
        double result = Trigonometry.tanPower(input, N);
        assertEquals(expected, result, 0.08);
    }

    @ParameterizedTest
    @ValueSource(doubles = {1.58, -1.58, Integer.MAX_VALUE, Integer.MIN_VALUE})
    void illegalArguments(double input) {
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
    void bernulliTest(int input, double expected) {
        double res = Trigonometry.bernulli(input);
        assertEquals(expected, res, 1e-2);
    }

    @ParameterizedTest
    @CsvSource({
            "0, 1",
            "1, 1",
            "2, 2",
            "3, 6",
            "4, 24",
            "7, 5040",
            "10, 3628800"
    })
    void factorialTest(int x, int expected) {
        assertEquals(expected, Trigonometry.factorial(x).longValue());
    }
}
