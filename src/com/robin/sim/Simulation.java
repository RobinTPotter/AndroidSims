package com.robin.sim;

import android.graphics.*;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

/**
 * Created by potterr on 03/03/2017.
 */
public class Simulation implements View.OnTouchListener, GestureDetector.OnGestureListener, ScaleGestureDetector.OnScaleGestureListener {

    private class Worm {
        double x, y, size;
        double targetx;
        double targety;
        double dirx;
        double diry;
        double mag;

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

            if (mag < 10){
                wormWrangler.setNewTarget(this);

                dirx = targetx - x;
                diry = targety - y;
                mag = Math.sqrt(dirx * dirx + diry * diry);

            }

            dirx *= size / mag;
            diry *= size / mag;

            x += dirx * speed;
            y += diry * speed;

            Log.d("Worm",this+" "+x+" "+y);

        }

        public void draw(Canvas c) {

            Log.d("Worm", "draw worm");
            c.drawLine((float) (x + 0 * dirx), (float) (y + 0 * diry), (float) (x + 1 * dirx), (float) (y + 1 * diry), p);

        }
    }

    SimView simView;
    Worm[] worms;
    int width, height;
    int numWorms=10;

    public Simulation(SimView simView) {
        this.simView = simView;
        this.worms = new Worm[numWorms];

        Log.d("Simulation", "Create Simulation");

       for (int ww= 0;ww < numWorms; ww++){
            this.worms[ww] = new Worm(this);
           this.worms[ww].size = (int) (10 * Math.random()) + 5;
           this.worms[ww].x = 20;
           this.worms[ww].y = 20;
           this.worms[ww].targetx = 20;
           this.worms[ww].targety = 20;
           this.worms[ww].state = Math.random();
            Log.d("Simulation", "worm is " + this.worms[ww]);
        }

    }

    public void setNewTarget(Worm w) {

        Log.d("Simulation", "set target for " + w);
        w.targetx = Math.random() * width;
        w.targety = Math.random() * height;

    }

    protected void updateProperties(int width, int height) {

        Log.d("Simulation", "update props");

        this.width = width;
        this.height = height;

        if (this.worms != null) {
            for (Worm w : this.worms) {
                if (w != null) {
                    w.update();
                } else {
                    Log.d("Simulation", "worm is null " + w);
                }
            }
        } else {

            Log.d("Simulation", "worms null");
        }

    }

    protected void drawMethod(int width, int height) {

        Canvas c=new Canvas(simView.getBuffer());
        Paint p=new Paint();
        c.drawRect(0,0,width, height,p);

        Log.d("Simulation", "draw");
        if (worms != null) {
            for (Worm w : worms) {
                if (w != null)
                    w.draw(new Canvas(simView.getBuffer()));
            }
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        //simView.message("touch");
        boolean ret = simView.getGestureDetector().onTouchEvent(event);
        ret = simView.getScaleGestureDetector().onTouchEvent(event) || ret;
        return ret;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        //simView.message("down");
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {
        // simView.message("show press");
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        // simView.message("single tap up");
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        //simView.message("scroll");
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        //simView.message("long press");

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        // simView.message("fling");
        return true;
    }

    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        //simView.message("scale");
        //

        return true;
    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {
        //simView.message("scale begin");

        return true;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {
        //simView.message("scale end");

    }
}
