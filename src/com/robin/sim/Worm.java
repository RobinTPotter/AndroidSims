package com.robin.sim;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

public class Worm {


    double x, y;
    int size;
    float segsize=10.0f;
    double targetx;
    double targety;
    double dirx;
    double diry;
    double mag;
    float[] body=null;


    double MAX_ANGLE = (60.0 * 2 * Math.PI / 360);


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
        update();
    }

    public void update() {

        Log.d("Worm", "update worm");

        dirx = targetx - x;
        diry = targety - y;
        mag = Math.sqrt(dirx * dirx + diry * diry);

        if (mag < 10) {
            wormWrangler.setNewTarget(this);

            dirx = targetx - x;
            diry = targety - y;
            mag = Math.sqrt(dirx * dirx + diry * diry);

        }

        dirx *= size / mag;
        diry *= size/ mag;

        x += dirx * speed;
        y += diry * speed;

        Log.d("Worm", this + " " + x + " " + y);

        if (body==null )body=new float[size*4];
        int bb=0;

        body[bb++]=(float)x;
        body[bb++]=(float)y;
        body[bb++]=(float)(x-segsize*dirx);
        body[bb++]=(float)(y-segsize*diry);

        for (int ss=1;ss<size; ss++) {
            body[bb++]=(float)(x-segsize*(ss)*dirx);
            body[bb++]=(float)(y-segsize*(ss)*diry);
            body[bb]+=(float)(x-segsize*(ss+1)*dirx);
            body[bb++]/=2;
            body[bb]+=(float)(y-segsize*(ss+1)*diry);
            body[bb++]/=2;
        }

    }

    public void draw(Canvas c) {

        Log.d("Worm", "draw worm");
        c.drawLines(body, p);

    }

    public interface WormWrangler {
        void setNewTarget(Worm w);
        void addWorm(int numToAdd);
    }
}
