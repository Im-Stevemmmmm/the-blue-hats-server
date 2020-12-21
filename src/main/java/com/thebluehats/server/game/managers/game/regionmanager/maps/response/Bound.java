package com.thebluehats.server.game.managers.game.regionmanager.maps.response;

public class Bound {
    private final float x;
    private final float y;
    private final float z;

    public Bound(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getZ() {
        return z;
    }
}
