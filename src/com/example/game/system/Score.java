package com.example.game.system;

import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by ION on 2015-06-02.
 */
public class Score {

    public double positionX, positionY;
    public int score;
    public Paint paint;

    private int showTime;

    public Score(double positionX, double positionY, int score) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.score = score;
        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setTextSize(54);
        showTime = 0;
    }

    public void moveScore() {
        if (positionY < 4) {
            positionY = 0;
        } else {
            positionY -= 1;
        }
        showTime += 1;
    }

    public boolean isMoveFinish() {
        if (showTime > 100)
            return true;
        return false;
    }
}
