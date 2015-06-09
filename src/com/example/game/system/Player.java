package com.example.game.system;

import android.content.Context;
import android.graphics.*;
import android.util.Log;
import com.example.game.R;

import java.util.HashMap;

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
    private Assist assist;

    public Player(Context context, int width, int height) {
        this.width = width;
        this.height = height;

        assist = new Assist();
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

    public void crash(Enemy targetEnemy) {
        crashed = true;
        targetEnemy.crashed = true;
        targetEnemy.crashedTime = 0;

        int moveEnemy = 500;
        double enemyLeft = targetEnemy.enemyX - targetEnemy.enemyW;
        double enemyRight = targetEnemy.enemyX + targetEnemy.enemyW;
        double enemyTop = targetEnemy.enemyY - targetEnemy.enemyH;
        double enemyBottom = targetEnemy.enemyY + targetEnemy.enemyH;

        if (isDestination) {
            switch (targetEnemy.checkBeforeDirection()) {
                case LEFT_TOP:
                    if (playerX < enemyLeft && playerY < enemyTop) {
                        targetEnemy.wayPointX = targetEnemy.enemyX + moveEnemy;
                        targetEnemy.wayPointY = targetEnemy.enemyY + moveEnemy;
                    }

                    if (playerX >= enemyLeft && playerY < enemyTop) {
                        targetEnemy.wayPointX = targetEnemy.enemyX - moveEnemy;
                        targetEnemy.wayPointY = targetEnemy.enemyY + moveEnemy;
                    }

                    if (playerX < enemyLeft && playerY >= enemyTop) {
                        targetEnemy.wayPointX = targetEnemy.enemyX + moveEnemy;
                        targetEnemy.wayPointY = targetEnemy.enemyY - moveEnemy;
                    }
                    break;
                case RIGHT_TOP:
                    if (playerX > enemyRight && playerY < enemyTop) {
                        targetEnemy.wayPointX = targetEnemy.enemyX - moveEnemy;
                        targetEnemy.wayPointY = targetEnemy.enemyY + moveEnemy;
                    }

                    if (playerX <= enemyRight && playerY < enemyTop) {
                        targetEnemy.wayPointX = targetEnemy.enemyX + moveEnemy;
                        targetEnemy.wayPointY = targetEnemy.enemyY + moveEnemy;
                    }

                    if (playerX > enemyRight && playerY >= enemyTop) {
                        targetEnemy.wayPointX = targetEnemy.enemyX - moveEnemy;
                        targetEnemy.wayPointY = targetEnemy.enemyY - moveEnemy;
                    }
                    break;
                case RIGHT_BOTTOM:
                    if (playerX > enemyRight && playerY > enemyBottom) {
                        targetEnemy.wayPointX = targetEnemy.enemyX - moveEnemy;
                        targetEnemy.wayPointY = targetEnemy.enemyY - moveEnemy;
                    }

                    if (playerX <= enemyRight && playerY > enemyBottom) {
                        targetEnemy.wayPointX = targetEnemy.enemyX + moveEnemy;
                        targetEnemy.wayPointY = targetEnemy.enemyY - moveEnemy;
                    }

                    if (playerX > enemyRight && playerY <= enemyBottom) {
                        targetEnemy.wayPointX = targetEnemy.enemyX - moveEnemy;
                        targetEnemy.wayPointY = targetEnemy.enemyY + moveEnemy;
                    }
                    break;
                case LEFT_BOTTOM:
                    if (playerX < enemyLeft && playerY > enemyBottom) {
                        targetEnemy.wayPointX = targetEnemy.enemyX + moveEnemy;
                        targetEnemy.wayPointY = targetEnemy.enemyY - moveEnemy;
                    }

                    if (playerX >= enemyLeft && playerY > enemyBottom) {
                        targetEnemy.wayPointX = targetEnemy.enemyX - moveEnemy;
                        targetEnemy.wayPointY = targetEnemy.enemyY - moveEnemy;
                    }

                    if (playerX < enemyLeft && playerY < enemyBottom) {
                        targetEnemy.wayPointX = targetEnemy.enemyX + moveEnemy;
                        targetEnemy.wayPointY = targetEnemy.enemyY + moveEnemy;
                    }
                    break;
            }
            targetEnemy.wayPointX = targetEnemy.wayPointX >= width ? width : targetEnemy.wayPointX <= 0 ? 0 : targetEnemy.wayPointX;
            targetEnemy.wayPointY = targetEnemy.wayPointY >= height ? height : targetEnemy.wayPointY <= 0 ? 0 : targetEnemy.wayPointY;
        } else {
            switch (checkBeforeDirection()) {
                case LEFT_TOP:
                    if (playerY <= targetEnemy.enemyY) {
                        switch (targetEnemy.checkBeforeDirection()) {
                            case LEFT_TOP:
                                targetEnemy.wayPointX = width - targetEnemy.wayPointX;
                                targetEnemy.wayPointY = height - targetEnemy.wayPointY;
                                break;
                            case LEFT_BOTTOM:
                                wayPointY = height - wayPointY;
                                targetEnemy.wayPointX = width - targetEnemy.wayPointX;
                                break;
                            case RIGHT_BOTTOM:
                                wayPointX = width - wayPointX;
                                wayPointY = height - wayPointY;
                                targetEnemy.wayPointX = width - targetEnemy.wayPointX;
                                break;
                            case RIGHT_TOP:
                                wayPointX = width - wayPointX;
                                targetEnemy.wayPointX = width - targetEnemy.wayPointX;
                                targetEnemy.wayPointY = height - targetEnemy.wayPointY;
                                break;
                        }
                    } else {
                        wayPointX = width - wayPointX;
                        wayPointY = height - wayPointY;
                        switch (targetEnemy.checkBeforeDirection()) {
                            case LEFT_TOP:
                                break;
                            case LEFT_BOTTOM:
                                targetEnemy.wayPointY = height - targetEnemy.wayPointY;
                                break;
                            case RIGHT_BOTTOM:
                                targetEnemy.wayPointX = width - targetEnemy.wayPointX;
                                targetEnemy.wayPointY = height - targetEnemy.wayPointY;
                                break;
                            case RIGHT_TOP:
                                targetEnemy.wayPointX = width - targetEnemy.wayPointX;
                                break;
                        }
                    }
                    break;
                case LEFT_BOTTOM:
                    if (playerY <= targetEnemy.enemyY) {
                        switch (targetEnemy.checkBeforeDirection()) {
                            case LEFT_TOP:
                                wayPointY = height - wayPointY;
                                targetEnemy.wayPointX = width - targetEnemy.wayPointX;
                                targetEnemy.wayPointY = height - targetEnemy.wayPointY;
                                break;
                            case LEFT_BOTTOM:
                                wayPointX = width - wayPointX;
                                wayPointY = height - wayPointY;
                                break;
                            case RIGHT_BOTTOM:
                                wayPointX = width - wayPointX;
                                wayPointY = height - wayPointY;
                                targetEnemy.wayPointX = width - wayPointX;
                                break;
                            case RIGHT_TOP:
                                wayPointX = width - wayPointX;
                                wayPointY = height - wayPointY;
                                targetEnemy.wayPointX = width - targetEnemy.wayPointX;
                                targetEnemy.wayPointY = height - targetEnemy.wayPointY;
                                break;
                        }
                    } else {
                        switch (targetEnemy.checkBeforeDirection()) {
                            case LEFT_TOP:
                                wayPointX = width - wayPointX;
                                targetEnemy.wayPointY = height - wayPointY;
                                break;
                            case LEFT_BOTTOM:
                                targetEnemy.wayPointX = width - targetEnemy.wayPointX;
                                targetEnemy.wayPointY = height - targetEnemy.wayPointY;
                                break;
                            case RIGHT_BOTTOM:
                                wayPointX = width - wayPointX;
                                targetEnemy.wayPointX = width - targetEnemy.wayPointX;
                                targetEnemy.wayPointY = height - targetEnemy.wayPointY;
                                break;
                            case RIGHT_TOP:
                                wayPointX = width - wayPointX;
                                targetEnemy.wayPointX = width - targetEnemy.wayPointX;
                                targetEnemy.wayPointY = height - targetEnemy.wayPointY;
                                break;
                        }
                    }
                    break;
                case RIGHT_BOTTOM:
                    if (playerY <= targetEnemy.enemyY) {
                        switch (targetEnemy.checkBeforeDirection()) {
                            case LEFT_TOP:
                                wayPointX = width - wayPointX;
                                wayPointY = height - wayPointY;
                                targetEnemy.wayPointX = width - targetEnemy.wayPointX;
                                targetEnemy.wayPointY = height - targetEnemy.wayPointY;
                                break;
                            case LEFT_BOTTOM:
                                wayPointX = width - wayPointX;
                                wayPointY = height - wayPointY;
                                targetEnemy.wayPointX = width - targetEnemy.wayPointX;
                                break;
                            case RIGHT_BOTTOM:
                                wayPointX = width - wayPointX;
                                wayPointY = height - wayPointY;
                                break;
                            case RIGHT_TOP:
                                wayPointY = height - wayPointY;
                                targetEnemy.wayPointX = width - targetEnemy.wayPointX;
                                targetEnemy.wayPointY = height - targetEnemy.wayPointY;
                                break;
                        }
                    } else {
                        wayPointX = width - wayPointX;
                        targetEnemy.wayPointX = width - targetEnemy.wayPointX;
                        targetEnemy.wayPointY = height - targetEnemy.wayPointY;
                    }
                    break;
                case RIGHT_TOP:
                    if (playerY <= targetEnemy.enemyY) {
                        switch (targetEnemy.checkBeforeDirection()) {
                            case LEFT_TOP:
                                wayPointX = width - wayPointX;
                                targetEnemy.wayPointX = width - targetEnemy.wayPointX;
                                targetEnemy.wayPointY = height - targetEnemy.wayPointY;
                                break;
                            case LEFT_BOTTOM:
                                wayPointX = width - wayPointX;
                                wayPointY = height - wayPointY;
                                targetEnemy.wayPointX = width - targetEnemy.wayPointX;
                                break;
                            case RIGHT_BOTTOM:
                                wayPointY = height - wayPointY;
                                targetEnemy.wayPointX = width - targetEnemy.wayPointX;
                                break;
                            case RIGHT_TOP:
                                targetEnemy.wayPointX = width - targetEnemy.wayPointX;
                                targetEnemy.wayPointY = height - targetEnemy.wayPointY;
                                break;
                        }
                    } else {
                        wayPointX = width - wayPointX;
                        wayPointY = height - wayPointY;
                        switch (targetEnemy.checkBeforeDirection()) {
                            case LEFT_TOP:
                                targetEnemy.wayPointX = width - targetEnemy.wayPointX;
                                break;
                            case LEFT_BOTTOM:
                                targetEnemy.wayPointX = width - targetEnemy.wayPointX;
                                targetEnemy.wayPointY = height - targetEnemy.wayPointY;
                                break;
                            case RIGHT_BOTTOM:
                                targetEnemy.wayPointY = height - targetEnemy.wayPointY;
                                break;
                            case RIGHT_TOP:
                                break;
                        }
                    }
                    break;
            }
        }
        destinationX = (float) wayPointX;
        destinationY = (float) wayPointY;
    }
}
