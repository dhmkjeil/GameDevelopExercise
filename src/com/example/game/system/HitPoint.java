package com.example.game.system;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.example.game.R;

/**
 * Created by ION on 2015-06-02.
 */
public class HitPoint {
    private int width, height;
    private Bitmap hitPoint;

    public HitPoint(Context context, int width, int height) {
        this.width = width;
        this.height = height;

        hitPoint = BitmapFactory.decodeResource(context.getResources(), R.drawable.player_hitpoint);
    }
}
