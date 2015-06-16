package com.example.game.system;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.example.game.R;
import com.example.game.system.particle.ParticleVector;

public class CrashEffect extends Effect {

    public Bitmap crashEffect;

    public CrashEffect(Context context, double mainBoatX, double mainBoatY, double subBoatX, double subBoatY) {
        location = new ParticleVector((float) ((mainBoatX + subBoatX) / 2), (float) ((mainBoatY + subBoatY) / 2));
        acceleration = new ParticleVector(0, (float) 0.05);
        velocity = new ParticleVector(Assist.randomNumRangeFloat(-1, 1), Assist.randomNumRangeFloat(0, 2));
        lifespan = Assist.randomNumIntRangeOverZero(110, 170);
        crashEffect = BitmapFactory.decodeResource(context.getResources(), R.drawable.crash_effect2);
    }

    @Override
    public void makeEffect() {
        velocity.add(acceleration);
        location.add(velocity);
        lifespan -= 2.0;

        if (lifespan < 0.0) {
            isFinish = true;
        }
    }
}
