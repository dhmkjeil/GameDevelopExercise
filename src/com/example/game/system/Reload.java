package com.example.game.system;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.example.game.R;

/**
 * Created by ION on 2015-06-02.
 */
public class Reload {
    public boolean isReloading;
    public int reloadTime;
    public Bitmap reloadBitmap;

    public Reload(Context context) {
        reloadBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.reload);
    }

    public void isReloading(long fireTime) {
        long nowTime = System.currentTimeMillis();
        if ((nowTime - fireTime) >= reloadTime * 1000) {
            isReloading = false;
        } else {
            isReloading = true;
        }
    }
}
