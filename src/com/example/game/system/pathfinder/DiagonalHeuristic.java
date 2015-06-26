package com.example.game.system.pathfinder;

import android.graphics.Point;

public class DiagonalHeuristic {

    public float getDistanceToEnd(Point start, Point end) {
        float diagonal = (float) Math.min(Math.abs(start.x - end.x), Math.abs(start.y - end.y));
        float straight = (float) Math.abs(start.x - end.x) + Math.abs(start.y - end.y);
        float result = (float) (Math.sqrt(2) * diagonal + (straight - 2 * diagonal));

        float p = 1 / 10000;
        result *= (1.0 + p);

        return result;
    }
}
