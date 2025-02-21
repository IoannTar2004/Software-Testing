package org.example.sort;

import com.sun.management.OperatingSystemMXBean;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.lang.management.ManagementFactory;
import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SortsTest {

    final OperatingSystemMXBean osBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();

    record TestClass(int number) implements Comparable<TestClass> {

        @Override
        public int compareTo(TestClass o) {
            return Integer.compare(this.number, o.number);
        }

    }

        @AfterEach
        void tearDown() throws InterruptedException {
            Thread.sleep(500);
            System.out.printf("CPU load: %.3f%n", osBean.getProcessCpuLoad() * 100);
        }

        static Stream<Arguments> selectionSortInputs() {
            return Stream.of(
                    Arguments.of((Object) new Integer[]{5, -3, 10, 8, 2, 57, 44, 1}),          // Разнообразный набор целых чисел
                    Arguments.of((Object) new Integer[]{4, 4, 4, 4}),                          // Массив из одинаковых элементов
                    Arguments.of((Object) new Integer[]{9, 8, 7, 6, 5}),                       // Массив в обратном порядке (худший случай)
                    Arguments.of((Object) new Integer[]{-1, 0, 1, 2, 3, 4}),                   // Отсортированный массив
                    Arguments.of((Object) new Integer[]{}),                                    // Пустой массив
                    Arguments.of((Object) new Integer[]{42}),                                  // Массив из одного элемента
                    Arguments.of((Object) new Double[]{4.0, 2.1, 5.96, 5.96, 0.0, -0.4}),      // Массив типа Double
                    Arguments.of((Object) new Double[]{Double.MIN_VALUE, 0.0, Double.MAX_VALUE}),   // Массив с экстремальными значениями
                    Arguments.of((Object) new String[]{"Chocolate", "Work", "Adidas", "Volga", "Ivan"}), // Массив строк
                    Arguments.of((Object) new TestClass[] {new TestClass(89),
                                                            new TestClass(20),
                                                            new TestClass(-67)})    // Массив объектов Comparable
            );
        }

        @ParameterizedTest
        @MethodSource("selectionSortInputs")
        <T extends Comparable<T>> void selectionSortTest(T[] array) {
            Sorts.selectionSort(array);
            assertTrue(IntStream.range(0, array.length - 1).allMatch(i -> array[i + 1].compareTo(array[i]) >= 0));
        }

        @Test
        void selectionSortTestNull() {
            assertThrows(NullPointerException.class, () -> Sorts.selectionSort(null));
        }
    }
