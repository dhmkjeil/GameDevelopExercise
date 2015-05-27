package com.example.game.system;

import android.content.Context;
import android.graphics.*;
import android.util.Log;
import com.example.game.R;

/**
 * Created by ION on 2015-05-22.
 */
public class Player {
    private final int LEFT_TOP = 1;
    private final int RIGHT_TOP = 2;
    private final int RIGHT_BOTTOM = 3;
    private final int LEFT_BOTTOM = 4;

    Context context;

    final double playerMaxSpeed;
    int width, height;
    double playerNowSpeed;
    long lastFireTime;

    public int movingTime, playerHitPoint, beforeDirection;
    public double playerW, playerH, playerX, playerY;
    public double diagonal, diagonalX, diagonalY;
    public double wayPointX, wayPointY;
    public float destinationX, destinationY;
    public boolean crashed = false, reverse = false, isDestination = true;

    public Bitmap playerBoat;
    private Paint destinationPaint;
    private Assist assist;

    public Player(Context context, int width, int height) {
        this.context = context;
        this.width = width;
        this.height = height;

        assist = new Assist();
        destinationPaint = new Paint();
        destinationPaint.setColor(Color.WHITE);
        destinationPaint.setStrokeWidth(3);

        playerBoat = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.boat);

        playerW = playerBoat.getWidth() / 2;
        playerH = playerBoat.getHeight() / 2;
        playerX = this.width / 2;
        playerY = this.height / 2;

        playerMaxSpeed = 7;
        playerNowSpeed = playerMaxSpeed;
        playerHitPoint = 3;
        lastFireTime = 0;

        wayPointX = playerX;
        wayPointY = playerY;

        diagonalX = 0;
        diagonalY = 0;
    }

    public void getDiagonal() {
        int addDirection = 200;
        double tempX, tempY;

        if (crashed) {
            switch (checkBeforeDirection()) {
                case LEFT_TOP:
                    beforeDirection = LEFT_TOP;
                    wayPointX = assist.randomNum((int) playerX + addDirection, width);
                    wayPointY = assist.randomNum((int) playerY + addDirection, height);
                    break;
                case RIGHT_TOP:
                    beforeDirection = RIGHT_TOP;
                    wayPointX = assist.randomNum(0, (int) playerX - addDirection);
                    wayPointY = assist.randomNum((int) playerY + addDirection, height);
                    break;
                case RIGHT_BOTTOM:
                    beforeDirection = RIGHT_BOTTOM;
                    wayPointX = assist.randomNum(0, (int) playerX - addDirection);
                    wayPointY = assist.randomNum(0, (int) playerY - addDirection);
                    break;
                case LEFT_BOTTOM:
                    beforeDirection = LEFT_BOTTOM;
                    wayPointX = assist.randomNum((int) playerX + addDirection, width);
                    wayPointY = assist.randomNum(0, (int) playerY - addDirection);
                    break;
                default:
                    beforeDirection = 0;
                    wayPointX = assist.randomNum(0, width);
                    wayPointY = assist.randomNum(0, height);
                    break;
            }
        }

        tempX = wayPointX - playerX;
        tempY = wayPointY - playerY;
        diagonal = Math.sqrt((tempX * tempX) + (tempY * tempY));
        if (diagonal > 0) {
            diagonalX = ((tempX / diagonal) * playerNowSpeed);
            diagonalY = ((tempY / diagonal) * playerNowSpeed);
        }
        getMovingTime(tempX, tempY);
    }

    private void getMovingTime(double x, double y) {
        double tempXMove, tempYMove;
        tempXMove = x / diagonalX;
        tempYMove = y / diagonalY;

        if (crashed) {
            movingTime = 10;
        } else {
            movingTime = (int) (tempXMove > tempYMove ? tempYMove : tempXMove);
        }
    }

    public void getSpeed(long beforeTouchTime, long nowTouchTime) {
        double tempSpeed, timeSpace;
        timeSpace = Math.abs(beforeTouchTime - nowTouchTime);
        tempSpeed = 1000 / timeSpace;
        Log.i("speed", "" + tempSpeed);
        playerNowSpeed = tempSpeed > playerMaxSpeed ? playerMaxSpeed : tempSpeed;
    }

    public void moveTrack(Canvas canvas) {
        if (!isDestination) {
            canvas.drawLine((float) playerX, (float) playerY, destinationX, destinationY, destinationPaint);
        }
    }

    public void movePlayer() {
        if (movingTime > 0) {
            if (crashed && !reverse) {
                playerNowSpeed = 1;
                reverse = true;
                getDiagonal();
            }
            playerX += diagonalX;
            playerY += diagonalY;

            if (movingTime == 1) {
                crashed = false;
                reverse = false;
                isDestination = true;
            }

            movingTime -= 1;
        }

        if (movingTime == 0 && crashed) {
            crashed = false;
        }
    }

    public int checkBeforeDirection() {

        if (wayPointX < playerX && wayPointY < playerY) {
            return LEFT_TOP;
        }
        if (wayPointX > playerX && wayPointY < playerY) {
            return RIGHT_TOP;
        }
        if (wayPointX > playerX && wayPointY > playerY) {
            return RIGHT_BOTTOM;
        }
        if (wayPointX < playerX && wayPointY > playerY) {
            return LEFT_BOTTOM;
        }
        return 0;
    }
}
