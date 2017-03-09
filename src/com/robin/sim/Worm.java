package com.robin.sim;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

public class Worm {

    float x, y;
    int size;
    float segsize = 8.0f;
    float targetx;
    float targety;
    float dirx;
    float diry;
    float mag;
    float segments[];
    float step = 1f;

    double state = 0;

    double speed = 0.5;

    Paint p = new Paint();
    Simulation wormWrangler;

    public Worm(Simulation ww) {

        Log.d("Worm", "Create worm");
        this.wormWrangler = ww;

        int rr = 0, gg = 0, bb = 0;
        float ll = 0;

        while (ll < 0.5) {

            rr = (int) (Math.random() * 255);
            gg = (int) (Math.random() * 255);
            bb = (int) (Math.random() * 255);

            ll = (0.2126f * rr + 0.7152f * gg + 0.0722f * bb);
        }

        p.setARGB(255, rr, gg, bb);

        segments = new float[size * 2];

        update();
    }

    public void initSegments() {

        segments = new float[size * 2];
        int sss=0;
        for (int ss =  segments.length-1;ss>1; ss -= 2) {
            segments[ss] = y -(sss++)*segsize;
            segments[ss-1] = x;
        }
    }

    public void update() {

        Log.d("Worm", "update worm");

        dirx = targetx - x;
        diry = targety - y;
        mag = (float) (Math.sqrt(dirx * dirx + diry * diry));

        if (mag < 10) {
            wormWrangler.setNewTarget(this);
            dirx = targetx - x;
            diry = targety - y;
            mag = (float) (Math.sqrt(dirx * dirx + diry * diry));
        }

        dirx *= 1 / mag;
        diry *= 1 / mag;

        x += dirx * (speed + Math.random() * 0.1);
        y += diry * (speed + Math.random() * 0.1);

        Log.d("Worm", this + " " + x + " " + y);

        if (segments.length == 0) return;

        /*

        last to first
        distance * step of last to next
         */

        segments[0] = x;
        segments[1] = y;

        for (int ss = segments.length - 1; ss > 2; ss -= 2) {

            float lasty = segments[ss];
            float lastx = segments[ss - 1];
            float nexty = segments[ss - 2];
            float nextx = segments[ss - 3];
            dirx = nextx - lastx;
            diry = nexty - lasty;
            mag = (float) (Math.sqrt(dirx * dirx + diry * diry));
            dirx *= step*segsize  / mag;
            diry *= step*segsize  / mag;
            segments[ss] += diry;
            segments[ss - 1] += dirx;
           // Log.i("worm", " " + segments[ss - 1] + " " + segments[ss]);
        }

    }

    public void draw(Canvas c) {

        Log.d("Worm", "draw worm");
        c.drawCircle(x, y, 5, p);
        c.drawLines(segments, p);
    }

    public interface WormWrangler {
        void setNewTarget(Worm w);

        void addWorm(int numToAdd);
    }
}
