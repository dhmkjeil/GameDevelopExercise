package com.example.game.system.particle;

/**
 * Created by ION on 2015-06-15.
 */
public class ParticleVector {
    public float x;
    public float y;

    public ParticleVector(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void add(ParticleVector pVector) {
        x = x + pVector.x;
        y = y + pVector.y;
    }
}
