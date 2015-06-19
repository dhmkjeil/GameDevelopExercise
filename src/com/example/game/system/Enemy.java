package com.example.game.system;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import com.example.game.R;

import java.util.ArrayList;

public class Enemy {
    private final int LEFT_TOP = 1;
    private final int RIGHT_TOP = 2;
    private final int RIGHT_BOTTOM = 3;
    private final int LEFT_BOTTOM = 4;
    private final double enemyMaxSpeed;

    Context context;
    private int width, height;
    private double enemyNowSpeed;
    private double diagonalX;
    private double diagonalY;
    double wayPointX, wayPointY;
    long lastChangedDirection, lastFireTime, reloadTime;

    public int hitPoint;
    public double enemyX, enemyY, enemyW, enemyH;
    public long crashedTime;
    public boolean dead = false, crashed = false;
    public int DIRECTION;

    public Bitmap enemy;
    private Bitmap enemyDirection[] = new Bitmap[8];

    public Enemy(Context context, double enemyX, double enemyY, int width, int height) {
        this.context = context;
        this.enemyX = enemyX;
        this.enemyY = enemyY;
        this.width = width;
        this.height = height;

        enemyDirection[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.enemy_0);
        enemyDirection[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.enemy_1);
        enemyDirection[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.enemy_2);
        enemyDirection[3] = BitmapFactory.decodeResource(context.getResources(), R.drawable.enemy_3);
        enemyDirection[4] = BitmapFactory.decodeResource(context.getResources(), R.drawable.enemy_4);
        enemyDirection[5] = BitmapFactory.decodeResource(context.getResources(), R.drawable.enemy_5);
        enemyDirection[6] = BitmapFactory.decodeResource(context.getResources(), R.drawable.enemy_6);
        enemyDirection[7] = BitmapFactory.decodeResource(context.getResources(), R.drawable.enemy_7);

        enemyW = enemyDirection[0].getWidth() / 2;
        enemyH = enemyDirection[0].getHeight() / 2;

        enemy = enemyDirection[0];
        enemyMaxSpeed = 3;
        enemyNowSpeed = enemyMaxSpeed;
        hitPoint = 3;
        lastFireTime = System.currentTimeMillis() + 1500;
        reloadTime = Assist.randomNumIntRangeOverZero(3000, 5000);
    }

    public void getDiagonal() {
        double tempX, tempY;

        if (!crashed) {
            wayPointX = Assist.randomNumIntRangeOverZero(0, width);
            wayPointY = Assist.randomNumIntRangeOverZero(0, height);
        }

        tempX = wayPointX - enemyX;
        tempY = wayPointY - enemyY;
        double diagonal = Math.sqrt((tempX * tempX) + (tempY * tempY));
        diagonalX = ((tempX / diagonal) * enemyNowSpeed);
        diagonalY = ((tempY / diagonal) * enemyNowSpeed);
    }

    public void getDirection() {
        double directionX, directionY, tempDirection;
        directionX = wayPointX - enemyX;
        directionY = -(wayPointY - enemyY);
        tempDirection = Math.atan2(directionY, directionX);
        if (tempDirection < 0) {
            tempDirection = Math.abs(tempDirection);
        } else {
            tempDirection = 2 * Math.PI - tempDirection;
        }

        double directionAngle = Math.toDegrees(Math.abs(tempDirection));

        if (directionAngle > 157.5 && directionAngle <= 202.5) {
            enemyW = enemyDirection[0].getWidth() / 2;
            enemyH = enemyDirection[0].getHeight() / 2;
            DIRECTION = 0;
        } else if (directionAngle > 202.5 && directionAngle <= 247.5) {
            enemyW = enemyDirection[1].getWidth() / 2;
            enemyH = enemyDirection[1].getHeight() / 2;
            DIRECTION = 1;
        } else if (directionAngle > 247.5 && directionAngle <= 292.5) {
            enemyW = enemyDirection[2].getWidth() / 2;
            enemyH = enemyDirection[2].getHeight() / 2;
            DIRECTION = 2;
        } else if (directionAngle > 292.5 && directionAngle <= 337.5) {
            enemyW = enemyDirection[3].getWidth() / 2;
            enemyH = enemyDirection[3].getHeight() / 2;
            DIRECTION = 3;
        } else if (directionAngle > 337.5 || directionAngle <= 22.5) {
            enemyW = enemyDirection[4].getWidth() / 2;
            enemyH = enemyDirection[4].getHeight() / 2;
            DIRECTION = 4;
        } else if (directionAngle > 22.5 && directionAngle <= 67.5) {
            enemyW = enemyDirection[5].getWidth() / 2;
            enemyH = enemyDirection[5].getHeight() / 2;
            DIRECTION = 5;
        } else if (directionAngle > 67.5 && directionAngle <= 112.5) {
            enemyW = enemyDirection[6].getWidth() / 2;
            enemyH = enemyDirection[6].getHeight() / 2;
            DIRECTION = 6;
        } else if (directionAngle > 112.5 && directionAngle <= 157.5) {
            enemyW = enemyDirection[7].getWidth() / 2;
            enemyH = enemyDirection[7].getHeight() / 2;
            DIRECTION = 7;
        }
    }

    public void moveEnemy() {
        long movingTime = System.currentTimeMillis();

        if (enemyX < 0 || enemyY < 0) {
            getDiagonal();
            getDirection();
            lastChangedDirection = movingTime;
        }

        if (enemyX > width - enemy.getWidth() || enemyY > height - enemy.getHeight()) {
            getDiagonal();
            getDirection();
            lastChangedDirection = movingTime;
        }

        if ((Math.abs(wayPointX - enemyX) < 10) && (Math.abs(wayPointY - enemyY) < 10)) {
            getDiagonal();
            getDirection();
            lastChangedDirection = movingTime;
        }

        if (!crashed && (movingTime - lastChangedDirection) > 5000) {
            getDiagonal();
            lastChangedDirection = movingTime;
        }

        if (crashed && (crashedTime == 0)) {
            enemyNowSpeed = 1;
            getDiagonal();
            crashedTime = movingTime;
            crashed = false;
        }

        if (movingTime - crashedTime > 1000) {
            getDirection();
            enemyNowSpeed = enemyMaxSpeed;
            crashedTime = 0;
        }

        enemy = enemyDirection[DIRECTION];

        enemyX += diagonalX;
        enemyY += diagonalY;
    }

    public void makeShell(double enemyX, double enemyY, double playerX, double playerY, ArrayList<Shell> enemiesShell) {
        long fireTime = System.currentTimeMillis();
        if (fireTime - lastFireTime >= reloadTime) {
            enemiesShell.add(new Shell(context, enemyX, enemyY, width, height, Assist.randomNumIntRangeOverZero((int) (playerX - 500), (int) (playerX + 500)), Assist.randomNumIntRangeOverZero((int) (playerY - 200), (int) (playerY +
                    200))));
            lastFireTime = fireTime;
        }
    }

    public int checkBeforeDirection() {

        if (wayPointX < enemyX && wayPointY < enemyY) {
            return LEFT_TOP;
        }
        if (wayPointX > enemyX && wayPointY < enemyY) {
            return RIGHT_TOP;
        }
        if (wayPointX > enemyX && wayPointY > enemyY) {
            return RIGHT_BOTTOM;
        }
        if (wayPointX < enemyX && wayPointY > enemyY) {
            return LEFT_BOTTOM;
        }
        return 0;
    }

    public void crash(Enemy targetEnemy) {

        crashed = true;
        crashedTime = 0;
        targetEnemy.crashed = true;
        targetEnemy.crashedTime = 0;

        int move = 500;
        double enemyLeft = targetEnemy.enemyX - targetEnemy.enemyW;
        double enemyRight = targetEnemy.enemyX + targetEnemy.enemyW;
        double enemyTop = targetEnemy.enemyY - targetEnemy.enemyH;
        double enemyBottom = targetEnemy.enemyY + targetEnemy.enemyH;

        switch (targetEnemy.checkBeforeDirection()) {
            case LEFT_TOP:
                if (enemyX < enemyLeft && enemyY < enemyTop) {
                    wayPointX = enemyX - move;
                    wayPointY = enemyY - move;
                    targetEnemy.wayPointX = targetEnemy.enemyX + move;
                    targetEnemy.wayPointY = targetEnemy.enemyY + move;
                }

                if (enemyX >= enemyLeft && enemyY < enemyTop) {
                    wayPointX = enemyX + move;
                    wayPointY = enemyY - move;
                    targetEnemy.wayPointX = targetEnemy.enemyX - move;
                    targetEnemy.wayPointY = targetEnemy.enemyY + move;
                }

                if (enemyX < enemyLeft && enemyY >= enemyTop) {
                    wayPointX = enemyX - move;
                    wayPointY = enemyY + move;
                    targetEnemy.wayPointX = targetEnemy.enemyX + move;
                    targetEnemy.wayPointY = targetEnemy.enemyY - move;
                }

                if (enemyX >= enemyLeft && enemyY >= enemyTop) {
                    wayPointX = enemyX + move;
                    wayPointY = enemyY + move;
                    targetEnemy.wayPointX = targetEnemy.enemyX - move;
                    targetEnemy.wayPointY = targetEnemy.enemyY - move;
                }
                break;
            case RIGHT_TOP:
                if (enemyX > enemyRight && enemyY < enemyTop) {
                    wayPointX = enemyX + move;
                    wayPointY = enemyY - move;
                    targetEnemy.wayPointX = targetEnemy.enemyX - move;
                    targetEnemy.wayPointY = targetEnemy.enemyY + move;
                }

                if (enemyX <= enemyRight && enemyY < enemyTop) {
                    wayPointX = enemyX - move;
                    wayPointY = enemyY - move;
                    targetEnemy.wayPointX = targetEnemy.enemyX + move;
                    targetEnemy.wayPointY = targetEnemy.enemyY + move;
                }

                if (enemyX > enemyRight && enemyY >= enemyTop) {
                    wayPointX = enemyX + move;
                    wayPointY = enemyY + move;
                    targetEnemy.wayPointX = targetEnemy.enemyX - move;
                    targetEnemy.wayPointY = targetEnemy.enemyY - move;
                }

                if (enemyX <= enemyRight && enemyY >= enemyTop) {
                    wayPointX = enemyX - move;
                    wayPointY = enemyY + move;
                    targetEnemy.wayPointX = targetEnemy.enemyX + move;
                    targetEnemy.wayPointY = targetEnemy.enemyY - move;
                }
                break;
            case RIGHT_BOTTOM:
                if (enemyX > enemyRight && enemyY > enemyBottom) {
                    wayPointX = enemyX + move;
                    wayPointY = enemyY + move;
                    targetEnemy.wayPointX = targetEnemy.enemyX - move;
                    targetEnemy.wayPointY = targetEnemy.enemyY - move;
                }

                if (enemyX <= enemyRight && enemyY > enemyBottom) {
                    wayPointX = enemyX - move;
                    wayPointY = enemyY + move;
                    targetEnemy.wayPointX = targetEnemy.enemyX + move;
                    targetEnemy.wayPointY = targetEnemy.enemyY - move;
                }

                if (enemyX > enemyRight && enemyY <= enemyBottom) {
                    wayPointX = enemyX + move;
                    wayPointY = enemyY - move;
                    targetEnemy.wayPointX = targetEnemy.enemyX - move;
                    targetEnemy.wayPointY = targetEnemy.enemyY + move;
                }

                if (enemyX <= enemyRight && enemyY <= enemyBottom) {
                    wayPointX = enemyX - move;
                    wayPointY = enemyY - move;
                    targetEnemy.wayPointX = targetEnemy.enemyX + move;
                    targetEnemy.wayPointY = targetEnemy.enemyY + move;
                }
                break;
            case LEFT_BOTTOM:
                if (enemyX < enemyLeft && enemyY > enemyBottom) {
                    wayPointX = enemyX - move;
                    wayPointY = enemyY + move;
                    targetEnemy.wayPointX = targetEnemy.enemyX + move;
                    targetEnemy.wayPointY = targetEnemy.enemyY - move;
                }

                if (enemyX >= enemyLeft && enemyY > enemyBottom) {
                    wayPointX = enemyX + move;
                    wayPointY = enemyY + move;
                    targetEnemy.wayPointX = targetEnemy.enemyX - move;
                    targetEnemy.wayPointY = targetEnemy.enemyY - move;
                }

                if (enemyX < enemyLeft && enemyY < enemyBottom) {
                    wayPointX = enemyX - move;
                    wayPointY = enemyY - move;
                    targetEnemy.wayPointX = targetEnemy.enemyX + move;
                    targetEnemy.wayPointY = targetEnemy.enemyY + move;
                }

                if (enemyX >= enemyLeft && enemyY <= enemyBottom) {
                    wayPointX = enemyX + move;
                    wayPointY = enemyY - move;
                    targetEnemy.wayPointX = targetEnemy.enemyX - move;
                    targetEnemy.wayPointY = targetEnemy.enemyY + move;
                }
                break;
        }

        wayPointX = wayPointX >= width ? width : wayPointX <= 0 ? 0 : wayPointX;
        wayPointY = wayPointY >= height ? height : wayPointY <= 0 ? 0 : wayPointY;
        targetEnemy.wayPointX = targetEnemy.wayPointX >= width ? width : targetEnemy.wayPointX <= 0 ? 0 : targetEnemy.wayPointX;
        targetEnemy.wayPointY = targetEnemy.wayPointY >= height ? height : targetEnemy.wayPointY <= 0 ? 0 : targetEnemy.wayPointY;
    }
}
