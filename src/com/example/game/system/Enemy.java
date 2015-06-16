package com.example.game.system;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.example.game.R;

import java.util.ArrayList;

/**
 * Created by ION on 2015-05-20.
 */
public class Enemy {
    private final int LEFT_TOP = 1;
    private final int RIGHT_TOP = 2;
    private final int RIGHT_BOTTOM = 3;
    private final int LEFT_BOTTOM = 4;

    Context context;

    public int hitPoint;
    int width, height;
    public double enemyX, enemyY, enemyW, enemyH;
    final double enemyMaxSpeed;
    double enemyNowSpeed;
    double diagonal, diagonalX, diagonalY, wayPointX, wayPointY;
    public long crashedTime;
    long lastChangedDirection, lastFireTime, reloadTime;
    public boolean dead = false, crashed = false;
    public Bitmap enemy;

    public Enemy(Context context, double enemyX, double enemyY, int width, int height) {
        this.context = context;
        this.enemyX = enemyX;
        this.enemyY = enemyY;
        this.width = width;
        this.height = height;
        enemy = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.enemy);
        enemyW = enemy.getWidth() / 2;
        enemyH = enemy.getHeight() / 2;
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
        diagonal = Math.sqrt((tempX * tempX) + (tempY * tempY));
        diagonalX = ((tempX / diagonal) * enemyNowSpeed);
        diagonalY = ((tempY / diagonal) * enemyNowSpeed);
    }

    public void moveEnemy() {
        long movingTime = System.currentTimeMillis();

        if (enemyX < 0 || enemyY < 0) {
            getDiagonal();
            lastChangedDirection = movingTime;
        }

        if (enemyX > width - enemy.getWidth() || enemyY > height - enemy.getHeight()) {
            getDiagonal();
            lastChangedDirection = movingTime;
        }

        if ((Math.abs(wayPointX - enemyX) < 10) && (Math.abs(wayPointY - enemyY) < 10)) {
            getDiagonal();
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
            enemyNowSpeed = enemyMaxSpeed;
            crashedTime = 0;
        }

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
