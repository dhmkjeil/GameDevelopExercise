package com.example.game.system;

import android.content.Context;
import android.graphics.*;
import android.util.Log;
import com.example.game.R;

import java.util.ArrayList;

public class Player {
    private final int LEFT_TOP = 1;
    private final int RIGHT_TOP = 2;
    private final int RIGHT_BOTTOM = 3;
    private final int LEFT_BOTTOM = 4;

    private final double playerMaxSpeed, playerMinSpeed;
    private double playerNowSpeed;
    private int width, height;

    public int movingTime, hitPoint;
    public int DIRECTION = 0;
    public double playerW, playerH, playerX, playerY;
    public double diagonal, diagonalX, diagonalY;
    public double wayPointX, wayPointY;
    public float destinationX;
    public float destinationY;
    public double reloadTime;
    public boolean crashed = false, reversNow = false, isDestination = true;

    public Bitmap playerBoat;
    private Bitmap playerDirection[] = new Bitmap[8];
    private Paint destinationPaint;

    public Player(Context context, int width, int height) {
        this.width = width;
        this.height = height;

        destinationPaint = new Paint();
        destinationPaint.setColor(Color.WHITE);
        destinationPaint.setStrokeWidth(3);

        playerDirection[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.boat_0);
        playerDirection[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.boat_1);
        playerDirection[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.boat_2);
        playerDirection[3] = BitmapFactory.decodeResource(context.getResources(), R.drawable.boat_3);
        playerDirection[4] = BitmapFactory.decodeResource(context.getResources(), R.drawable.boat_4);
        playerDirection[5] = BitmapFactory.decodeResource(context.getResources(), R.drawable.boat_5);
        playerDirection[6] = BitmapFactory.decodeResource(context.getResources(), R.drawable.boat_6);
        playerDirection[7] = BitmapFactory.decodeResource(context.getResources(), R.drawable.boat_7);

        playerW = playerDirection[0].getWidth() / 2;
        playerH = playerDirection[0].getHeight() / 2;

        playerBoat = playerDirection[0];
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

        if (crashed) {
            playerNowSpeed = 2;
        }

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
        }
    }

    public void getDirection() {
        double directionX, directionY, tempDirection;
        directionX = wayPointX - playerX;
        directionY = -(wayPointY - playerY);
        tempDirection = Math.atan2(directionY, directionX);
        if (tempDirection < 0) {
            tempDirection = Math.abs(tempDirection);
        } else {
            tempDirection = 2 * Math.PI - tempDirection;
        }

        double directionAngle = Math.toDegrees(Math.abs(tempDirection));
        if (directionAngle > 157.5 && directionAngle <= 202.5) {
            playerW = playerDirection[0].getWidth() / 2;
            playerH = playerDirection[0].getHeight() / 2;
            DIRECTION = 0;
        } else if (directionAngle > 202.5 && directionAngle <= 247.5) {
            playerW = playerDirection[1].getWidth() / 2;
            playerH = playerDirection[1].getHeight() / 2;
            DIRECTION = 1;
        } else if (directionAngle > 247.5 && directionAngle <= 292.5) {
            playerW = playerDirection[2].getWidth() / 2;
            playerH = playerDirection[2].getHeight() / 2;
            DIRECTION = 2;
        } else if (directionAngle > 292.5 && directionAngle <= 337.5) {
            playerW = playerDirection[3].getWidth() / 2;
            playerH = playerDirection[3].getHeight() / 2;
            DIRECTION = 3;
        } else if (directionAngle > 337.5 || directionAngle <= 22.5) {
            playerW = playerDirection[4].getWidth() / 2;
            playerH = playerDirection[4].getHeight() / 2;
            DIRECTION = 4;
        } else if (directionAngle > 22.5 && directionAngle <= 67.5) {
            playerW = playerDirection[5].getWidth() / 2;
            playerH = playerDirection[5].getHeight() / 2;
            DIRECTION = 5;
        } else if (directionAngle > 67.5 && directionAngle <= 112.5) {
            playerW = playerDirection[6].getWidth() / 2;
            playerH = playerDirection[6].getHeight() / 2;
            DIRECTION = 6;
        } else if (directionAngle > 112.5 && directionAngle <= 157.5) {
            playerW = playerDirection[7].getWidth() / 2;
            playerH = playerDirection[7].getHeight() / 2;
            DIRECTION = 7;
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
        playerBoat = playerDirection[DIRECTION];

        if (movingTime > 0) {
            playerX += diagonalX;
            playerY += diagonalY;

            if (movingTime == 1) {
                isDestination = true;
                crashed = false;
                reversNow = false;
            }
            movingTime -= 1;
        }
    }

    public void movePlayer(ArrayList<Point> shortestPath, int count) {
        if (shortestPath.size() > 0) {
            playerX = shortestPath.get(count).x;
            playerY = shortestPath.get(count).y;
        }

        if (shortestPath.size() - 1 == count) {
            shortestPath.clear();
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

                if (playerX >= enemyLeft && playerY >= enemyTop) {
                    wayPointX = playerX + move;
                    wayPointY = playerY + move;
                    targetEnemy.wayPointX = targetEnemy.enemyX - move;
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

                if (playerX <= enemyRight && playerY >= enemyTop) {
                    wayPointX = playerX - move;
                    wayPointY = playerY + move;
                    targetEnemy.wayPointX = targetEnemy.enemyX + move;
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

                if (playerX <= enemyRight && playerY <= enemyBottom) {
                    wayPointX = playerX - move;
                    wayPointY = playerY - move;
                    targetEnemy.wayPointX = targetEnemy.enemyX + move;
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

                if (playerX >= enemyLeft && playerY < enemyBottom) {
                    wayPointX = playerX + move;
                    wayPointY = playerY - move;
                    targetEnemy.wayPointX = targetEnemy.enemyX - move;
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
