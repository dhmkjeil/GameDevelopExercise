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
    public boolean dead = false;
    public boolean crashed = false;
    public Bitmap enemy;
    private Assist assist;

    public Enemy(Context context, double enemyX, double enemyY, int width, int height) {
        this.context = context;
        this.enemyX = enemyX;
        this.enemyY = enemyY;
        this.width = width;
        this.height = height;
        assist = new Assist();
        enemy = BitmapFactory.decodeResource(this.context.getResources(), R.drawable.enemy);
        enemyW = enemy.getWidth() / 2;
        enemyH = enemy.getHeight() / 2;
        enemyMaxSpeed = 3;
        enemyNowSpeed = enemyMaxSpeed;
        hitPoint = 3;
        lastFireTime = System.currentTimeMillis() + 1500;
        reloadTime = assist.randomNum(3000, 5000);
    }

    public void getDiagonal() {
        double tempX, tempY;

        if (!crashed) {
            wayPointX = assist.randomNum(0, width);
            wayPointY = assist.randomNum(0, height);
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
        }

        if (crashed && (movingTime - crashedTime > 1000)) {
            crashed = false;
            enemyNowSpeed = enemyMaxSpeed;
            crashedTime = 0;
        }

        enemyX += diagonalX;
        enemyY += diagonalY;
    }

    public void makeShell(double enemyX, double enemyY, double playerX, double playerY, ArrayList<Shell> enemiesShell) {
        long fireTime = System.currentTimeMillis();
        if (fireTime - lastFireTime >= reloadTime) {
            enemiesShell.add(new Shell(context, enemyX, enemyY, width, height, assist.randomNum((int) (playerX - 500), (int) (playerX + 500)), assist.randomNum((int) (playerY - 200), (int) (playerY +
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

        switch (checkBeforeDirection()) {
            case LEFT_TOP:
                if (enemyY <= targetEnemy.enemyY) {
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
                if (enemyY <= targetEnemy.enemyY) {
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
                if (enemyY <= targetEnemy.enemyY) {
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
                if (enemyY <= targetEnemy.enemyY) {
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
}
