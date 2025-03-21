package com.example.dbtest.utils;

public class Utils {
    public static long getRandomDelay(int min, int max) {
        return (long) (Math.random() * (max - min) + min);
    }
}
