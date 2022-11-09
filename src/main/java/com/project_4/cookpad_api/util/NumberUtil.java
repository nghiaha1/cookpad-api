package com.project_4.cookpad_api.util;

public class NumberUtil {

    public static int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    public static void main(String[] args) {
        for (int i = 0; i < 1000; i++) {
            System.out.println(getRandomNumber(1, 100));
        }
    }
}
