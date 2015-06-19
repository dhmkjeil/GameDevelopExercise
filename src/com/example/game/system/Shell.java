package com.example.game.system;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.example.game.R;

public class Shell {
    int width, height;
    public double shellX, shellY, shellW, shellH;
    public boolean dead = false;
    public double shellSpeed;
    double diagonal;
    double diagonalX, diagonalY;
    public Bitmap shell;
    private Bitmap shellDirection [] = new Bitmap[8];
    private int DIRECTION;

    public Shell(Context context, double shellX, double shellY, int width, int height, double wayPointX, double wayPointY) {
        this.width = width;
        this.height = height;
        this.shellX = shellX;
        this.shellY = shellY;
        shellDirection[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.shell_0);
        shellDirection[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.shell_2);
        shellDirection[4] = BitmapFactory.decodeResource(context.getResources(), R.drawable.shell_4);
        shellDirection[6] = BitmapFactory.decodeResource(context.getResources(), R.drawable.shell_6);
        shellW = shellDirection[0].getWidth() / 2;
        shellH = shellDirection[0].getHeight() / 2;
        shellSpeed = 8;

        getDiagonal(wayPointX, wayPointY);
        getDirection();
    }

    public void getDiagonal(double wayPointX, double wayPointY) {
        double tempX, tempY;
        tempX = wayPointX - shellX;
        tempY = wayPointY - shellY;
        diagonal = Math.sqrt((tempX * tempX) + (tempY * tempY));
        diagonalX = ((tempX / diagonal) * shellSpeed);
        diagonalY = ((tempY / diagonal) * shellSpeed);
    }

    public void getDirection() {
        double directionX, directionY, tempDirection;
        directionX = diagonalX;
        directionY = -(diagonalY);
        tempDirection = Math.atan2(directionY, directionX);
        if (tempDirection < 0) {
            tempDirection = Math.abs(tempDirection);
        } else {
            tempDirection = 2 * Math.PI - tempDirection;
        }

        double directionAngle = Math.toDegrees(Math.abs(tempDirection));

        if (directionAngle > 135 && directionAngle <= 225) {
            shellW = shellDirection[0].getWidth() / 2;
            shellH = shellDirection[0].getHeight() / 2;
            DIRECTION = 0;
        } else if (directionAngle > 225 && directionAngle <= 315) {
            shellW = shellDirection[2].getWidth() / 2;
            shellH = shellDirection[2].getHeight() / 2;
            DIRECTION = 2;
        } else if (directionAngle > 315 || directionAngle <= 45) {
            shellW = shellDirection[4].getWidth() / 2;
            shellH = shellDirection[4].getHeight() / 2;
            DIRECTION = 4;
        } else if (directionAngle > 45 && directionAngle <= 135) {
            shellW = shellDirection[6].getWidth() / 2;
            shellH = shellDirection[6].getHeight() / 2;
            DIRECTION = 6;
        }

        shell = shellDirection[DIRECTION];
//        if (directionAngle > 157.5 && directionAngle <= 202.5) {
//            DIRECTION = 0;
//        } else if (directionAngle > 202.5 && directionAngle <= 247.5) {
//            DIRECTION = 1;
//        } else if (directionAngle > 247.5 && directionAngle <= 292.5) {
//            DIRECTION = 2;
//        } else if (directionAngle > 292.5 && directionAngle <= 337.5) {
//            DIRECTION = 3;
//        } else if (directionAngle > 337.5 || directionAngle <= 22.5) {
//            DIRECTION = 4;
//        } else if (directionAngle > 22.5 && directionAngle <= 67.5) {
//            DIRECTION = 5;
//        } else if (directionAngle > 67.5 && directionAngle <= 112.5) {
//            DIRECTION = 6;
//        } else if (directionAngle > 112.5 && directionAngle <= 157.5) {
//            DIRECTION = 7;
//        }
    }

    public void moveShell() {
        shellX += diagonalX;
        shellY += diagonalY;
        if ((shellX < 0 || shellX > width) && (shellY < 0 || (shellY > height))) dead = true;
    }
}
