package com.example.game.system;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.example.game.R;

/**
 * Created by ION on 2015-05-20.
 */
public class Shell {
    int width, height;
    public double shellX, shellY, shellW, shellH;
    public boolean dead = false;
    public double shellSpeed;
    double diagonal;
    double diagonalX, diagonalY;
    public Bitmap shell;

    public Shell(Context context, double shellX, double shellY, int width, int height, double wayPointX, double wayPointY) {
        this.width = width;
        this.height = height;
        this.shellX = shellX;
        this.shellY = shellY;
        shell = BitmapFactory.decodeResource(context.getResources(), R.drawable.shell);
        shellW = shell.getWidth() / 2;
        shellH = shell.getHeight() / 2;
        shellSpeed = 8;

        getDiagonal(wayPointX, wayPointY);
    }

    public void getDiagonal(double wayPointX, double wayPointY) {
        double tempX, tempY;
        tempX = wayPointX - shellX;
        tempY = wayPointY - shellY;
        diagonal = Math.sqrt((tempX * tempX) + (tempY * tempY));
        diagonalX = ((tempX / diagonal) * shellSpeed);
        diagonalY = ((tempY / diagonal) * shellSpeed);
    }

    public void moveShell() {
        shellX += diagonalX;
        shellY += diagonalY;
        if ((shellX < 0 || shellX > width) && (shellY < 0 || (shellY > height))) dead = true;
    }
}
