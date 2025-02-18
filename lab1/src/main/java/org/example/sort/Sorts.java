package org.example.sort;

public class Sorts {
    public static <T extends Comparable<T>> void selectionSort(T[] array) {
        for (int i = 0; i < array.length; i++) {
            System.out.println("Loop number " + (i + 1) + ". Current min: " + array[i]);
            T min = array[i];
            int minIndex = i;
            for (int j = i + 1; j < array.length; j++) {
                if (min.compareTo(array[j]) > 0) {
                    System.out.println("Found new min: " + array[j]);
                    min = array[j];
                    minIndex = j;
                }
            }
            System.out.printf("Swap indexes %d and %d%n%n", i, minIndex);
            T temp = array[i];
            array[i] = min;
            array[minIndex] = temp;
        }
    }
}
