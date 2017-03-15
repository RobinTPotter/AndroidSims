package com.robin.sim;

import android.graphics.Canvas;

/**
 * Created by potterr on 14/03/2017.
 */

public interface WormTarget {
    float getX();
    float getY();
    boolean isAlive();
    void draw(Canvas c);
}