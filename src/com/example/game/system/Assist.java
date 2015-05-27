package com.example.game.system;

import java.util.Random;

/**
 * Created by ION on 2015-05-27.
 */
public class Assist {
    private final int LEFT_TOP = 1;
    private final int RIGHT_TOP = 2;
    private final int RIGHT_BOTTOM = 3;
    private final int LEFT_BOTTOM = 4;

    public int randomNum(int min, int max) {
        Random random = new Random();
        min = min < 0 ? 0 : min;
        max = max < 0 ? 0 : max;
        int randomCord = random.nextInt((max - min) + 1) + min;
        return randomCord;
    }

    public int checkBeforeDirection(double diagonal, double targetMaxSpeed, double targetX, double targetY) {
        double beforeWayPointX, beforeWayPointY;
        beforeWayPointX = ((diagonal / targetMaxSpeed) + targetX);
        beforeWayPointY = ((diagonal / targetMaxSpeed) + targetY);

        if (beforeWayPointX < targetX && beforeWayPointY < targetY) {
            return LEFT_TOP;
        }
        if (beforeWayPointX > targetX && beforeWayPointY < targetY) {
            return RIGHT_TOP;
        }
        if (beforeWayPointX > targetX && beforeWayPointY > targetY) {
            return RIGHT_BOTTOM;
        }
        if (beforeWayPointX < targetX && beforeWayPointY > targetY) {
            return LEFT_BOTTOM;
        }
        return 0;
    }
}
