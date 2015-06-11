package com.example.game.system;

/**
 * Created by ION on 2015-06-11.
 */
public abstract class Effect {
    protected double mainBoatX, mainBoatY, subBoatX, subBoatY;
    protected long changeEffectTime = 500, beforeEffectTime = 0;
    protected int effectCount = 4;
    protected int firstEffectW, secEffectW, firstEffectH, secEffectH;

    public double effectX, effectY;
    public boolean isFinish = false;

    public abstract void makeEffect();
}
