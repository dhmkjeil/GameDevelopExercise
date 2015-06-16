package com.example.game.system;

import com.example.game.system.particle.ParticleVector;

public abstract class Effect {
    protected ParticleVector velocity, acceleration;
    protected float lifespan;

    public boolean isFinish = false;
    public ParticleVector location;

    public abstract void makeEffect();
}
