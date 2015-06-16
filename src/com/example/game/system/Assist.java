package com.example.game.system;

import java.util.Random;

/**
 * Created by ION on 2015-05-27.
 */
public class Assist {

    public static float randomNumRangeFloat(int min, int max) {
        Random random = new Random();
        return random.nextFloat() * (max - min) + min;
    }

    public static int randomNumIntRangeOverZero(int min, int max) {
        Random random = new Random();
        min = min < 0 ? 0 : min;
        max = max < 0 ? 0 : max;
        return random.nextInt((max - min) + 1) + min;
    }
}
