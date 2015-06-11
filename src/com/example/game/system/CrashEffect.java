package com.example.game.system;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.example.game.R;

/**
 * Created by ION on 2015-06-11.
 */
public class CrashEffect extends Effect {

    public Bitmap crashEffect;

    public CrashEffect(Context context, double mainBoatX, double mainBoatY, double subBoatX, double subBoatY) {
        this.mainBoatX = mainBoatX;
        this.mainBoatY = mainBoatY;
        this.subBoatX = subBoatX;
        this.subBoatY = subBoatY;
        crashEffect = BitmapFactory.decodeResource(context.getResources(), R.drawable.crash_effect);
        firstEffectW = (crashEffect.getWidth() / 3) * 2;
        firstEffectH = (crashEffect.getHeight() / 3) * 2;
        secEffectW = crashEffect.getWidth() / 2;
        secEffectH = crashEffect.getHeight() / 2;
    }

    @Override
    public void makeEffect() {
        Long currentEffectTime = System.currentTimeMillis();
        if (effectCount > 0 && currentEffectTime - beforeEffectTime > changeEffectTime) {
            beforeEffectTime = currentEffectTime;

            if (effectCount % 2 == 0) {
                crashEffect = Bitmap.createScaledBitmap(crashEffect, firstEffectW, firstEffectH, false);
            } else {
                crashEffect = Bitmap.createScaledBitmap(crashEffect, secEffectW, secEffectH, false);
            }

            effectX = ((mainBoatX + subBoatX) / 2) - (crashEffect.getWidth() / 2);
            effectY = ((mainBoatY + subBoatY) / 2) - (crashEffect.getHeight() / 2);
            effectCount--;
        }

        if (effectCount == 0) {
            isFinish = true;
        }
    }
}
