package com.robin.sim;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

public class Worm {

    float x, y;
    int size;
    float segsize = 12.0f;
    float targetx;
    float targety;
    float dirx;
    float diry;
    float mag;
    float segments[];
    float step = 1f;

    double state = 0;

    float speed = 0.5f;

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
        int sss = 0;
        for (int ss = segments.length - 1; ss > 1; ss -= 2) {
            segments[ss] = y - (sss++) * segsize;
            segments[ss - 1] = x;
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

        float[] newsegments = new float[segments.length];
        newsegments[0] = x;
        newsegments[1] = y;

        for (int ss = 2; ss < segments.length ; ss += 4) {
            float lastx = segments[ss - 2];
            float lasty = segments[ss - 1];
            float thisx = segments[ss];
            float thisy = segments[ss + 1];
            float dirx2 = thisx - lastx;
            float diry2 = thisy - lasty;
            mag = (float) (Math.sqrt(dirx2 * dirx2 + diry2 * diry2));
            dirx2 *= 1 / mag;
            diry2 *= 1 / mag;
            newsegments[ss] = lastx + dirx2 * (segsize);// + speed);
            newsegments[ss + 1] = lasty + diry2 * (segsize);// + speed);
            if (ss + 2 <= segments.length - 1) {
                newsegments[ss + 2]=newsegments[ss];
                newsegments[ss + 3] = newsegments[ss + 1];
            }

        }
/*
        for (int ss = segments.length - 1; ss > 1; ss -= 2) {

            float lasty = segments[ss];
            float lastx = segments[ss - 1];
            float nexty = segments[ss - 2];
            float nextx = segments[ss - 3];
            dirx = nextx - lastx;
            diry = nexty - lasty;
            mag = (float) (Math.sqrt(dirx * dirx + diry * diry));
            dirx *= 1 / mag;
            diry *= 1 / mag;
            segments[ss] += diry * segsize * speed;
            segments[ss - 1] += dirx * segsize * speed;
            // Log.i("worm", " " + segments[ss - 1] + " " + segments[ss]);
        }
*/
        segments = newsegments;
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
