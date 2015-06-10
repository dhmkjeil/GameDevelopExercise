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

    private final double playerMaxSpeed, playerMinSpeed;
    private double playerNowSpeed;
    private int width, height;

    public int movingTime, hitPoint;
    public double playerW, playerH, playerX, playerY;
    public double diagonal, diagonalX, diagonalY;
    public double wayPointX, wayPointY;
    public float destinationX;
    public float destinationY;
    public double reloadTime;
    public boolean crashed = false, reverse = false, isDestination = true;

    public Bitmap playerBoat;
    private Paint destinationPaint;

    public Player(Context context, int width, int height) {
        this.width = width;
        this.height = height;

        destinationPaint = new Paint();
        destinationPaint.setColor(Color.WHITE);
        destinationPaint.setStrokeWidth(3);

        playerBoat = BitmapFactory.decodeResource(context.getResources(), R.drawable.boat);

        playerW = playerBoat.getWidth() / 2;
        playerH = playerBoat.getHeight() / 2;
        playerX = this.width / 2;
        playerY = this.height / 2;

        playerMaxSpeed = 8;
        playerMinSpeed = 3;
        playerNowSpeed = playerMaxSpeed;
        hitPoint = 3;
        reloadTime = 1.5;

        wayPointX = playerX;
        wayPointY = playerY;

        diagonalX = 0;
        diagonalY = 0;
    }

    public void getDiagonal() {
        double tempX, tempY;
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

        movingTime = (int) (tempXMove > tempYMove ? tempYMove : tempXMove);

        if (crashed) {
            movingTime = 10;
            crashed = false;
        }
    }

    public void getSpeed(long beforeTouchTime, long nowTouchTime) {
        double tempSpeed, timeSpace;
        timeSpace = Math.abs(beforeTouchTime - nowTouchTime);
        tempSpeed = 1000 / timeSpace;
        Log.i("speed", "" + tempSpeed);
        playerNowSpeed = tempSpeed > playerMaxSpeed ? playerMaxSpeed : tempSpeed > playerMinSpeed ? tempSpeed : playerMinSpeed;
    }

    public void moveTrack(Canvas canvas) {
        if (!isDestination) {
            canvas.drawLine((float) playerX, (float) playerY, destinationX, destinationY, destinationPaint);
        }
    }

    public void movePlayer() {
        if (movingTime > 0) {
            if (crashed && !reverse) {
                playerNowSpeed = 2;
                reverse = true;
                getDiagonal();
            }
            playerX += diagonalX;
            playerY += diagonalY;

            if (movingTime == 1) {
                isDestination = true;
            }
            movingTime -= 1;
        }

        if (movingTime == 0) {
            crashed = false;
            reverse = false;
        }
    }

    public void crash(Enemy targetEnemy) {
        crashed = true;
        targetEnemy.crashed = true;
        targetEnemy.crashedTime = 0;

        int move = 500;
        double enemyLeft = targetEnemy.enemyX - targetEnemy.enemyW;
        double enemyRight = targetEnemy.enemyX + targetEnemy.enemyW;
        double enemyTop = targetEnemy.enemyY - targetEnemy.enemyH;
        double enemyBottom = targetEnemy.enemyY + targetEnemy.enemyH;

        switch (targetEnemy.checkBeforeDirection()) {
            case LEFT_TOP:
                if (playerX < enemyLeft && playerY < enemyTop) {
                    wayPointX = playerX - move;
                    wayPointY = playerY - move;
                    targetEnemy.wayPointX = targetEnemy.enemyX + move;
                    targetEnemy.wayPointY = targetEnemy.enemyY + move;
                }

                if (playerX >= enemyLeft && playerY < enemyTop) {
                    wayPointX = playerX + move;
                    wayPointY = playerY - move;
                    targetEnemy.wayPointX = targetEnemy.enemyX - move;
                    targetEnemy.wayPointY = targetEnemy.enemyY + move;
                }

                if (playerX < enemyLeft && playerY >= enemyTop) {
                    wayPointX = playerX - move;
                    wayPointY = playerY + move;
                    targetEnemy.wayPointX = targetEnemy.enemyX + move;
                    targetEnemy.wayPointY = targetEnemy.enemyY - move;
                }
                break;
            case RIGHT_TOP:
                if (playerX > enemyRight && playerY < enemyTop) {
                    wayPointX = playerX + move;
                    wayPointY = playerY - move;
                    targetEnemy.wayPointX = targetEnemy.enemyX - move;
                    targetEnemy.wayPointY = targetEnemy.enemyY + move;
                }

                if (playerX <= enemyRight && playerY < enemyTop) {
                    wayPointX = playerX - move;
                    wayPointY = playerY - move;
                    targetEnemy.wayPointX = targetEnemy.enemyX + move;
                    targetEnemy.wayPointY = targetEnemy.enemyY + move;
                }

                if (playerX > enemyRight && playerY >= enemyTop) {
                    wayPointX = playerX + move;
                    wayPointY = playerY + move;
                    targetEnemy.wayPointX = targetEnemy.enemyX - move;
                    targetEnemy.wayPointY = targetEnemy.enemyY - move;
                }
                break;
            case RIGHT_BOTTOM:
                if (playerX > enemyRight && playerY > enemyBottom) {
                    wayPointX = playerX + move;
                    wayPointY = playerY + move;
                    targetEnemy.wayPointX = targetEnemy.enemyX - move;
                    targetEnemy.wayPointY = targetEnemy.enemyY - move;
                }

                if (playerX <= enemyRight && playerY > enemyBottom) {
                    wayPointX = playerX - move;
                    wayPointY = playerY + move;
                    targetEnemy.wayPointX = targetEnemy.enemyX + move;
                    targetEnemy.wayPointY = targetEnemy.enemyY - move;
                }

                if (playerX > enemyRight && playerY <= enemyBottom) {
                    wayPointX = playerX + move;
                    wayPointY = playerY - move;
                    targetEnemy.wayPointX = targetEnemy.enemyX - move;
                    targetEnemy.wayPointY = targetEnemy.enemyY + move;
                }
                break;
            case LEFT_BOTTOM:
                if (playerX < enemyLeft && playerY > enemyBottom) {
                    wayPointX = playerX - move;
                    wayPointY = playerY + move;
                    targetEnemy.wayPointX = targetEnemy.enemyX + move;
                    targetEnemy.wayPointY = targetEnemy.enemyY - move;
                }

                if (playerX >= enemyLeft && playerY > enemyBottom) {
                    wayPointX = playerX + move;
                    wayPointY = playerY + move;
                    targetEnemy.wayPointX = targetEnemy.enemyX - move;
                    targetEnemy.wayPointY = targetEnemy.enemyY - move;
                }

                if (playerX < enemyLeft && playerY < enemyBottom) {
                    wayPointX = playerX - move;
                    wayPointY = playerY - move;
                    targetEnemy.wayPointX = targetEnemy.enemyX + move;
                    targetEnemy.wayPointY = targetEnemy.enemyY + move;
                }
                break;
        }
        wayPointX = wayPointX >= width ? width : wayPointX <= 0 ? 0 : wayPointX;
        wayPointY = wayPointY >= height ? height : wayPointY <= 0 ? 0 : wayPointY;
        targetEnemy.wayPointX = targetEnemy.wayPointX >= width ? width : targetEnemy.wayPointX <= 0 ? 0 : targetEnemy.wayPointX;
        targetEnemy.wayPointY = targetEnemy.wayPointY >= height ? height : targetEnemy.wayPointY <= 0 ? 0 : targetEnemy.wayPointY;

        destinationX = (float) wayPointX;
        destinationY = (float) wayPointY;
    }
}
