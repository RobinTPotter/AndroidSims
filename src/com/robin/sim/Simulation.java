package com.robin.sim;

import android.graphics.*;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

/**
 * Created by potterr on 03/03/2017.
 */
public class Simulation implements View.OnTouchListener, GestureDetector.OnGestureListener, ScaleGestureDetector.OnScaleGestureListener {

    private interface WormWrangler {
        public void setNewTarget(Worm w);
    }

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
            this.wormWrangler = ww;
        }

        public void update() {

            dirx = targetx - x;
            diry = targety - y;
            mag = Math.sqrt(dirx * dirx + diry * diry);

            if (mag < 10) wormWrangler.setNewTarget(this);

            dirx *= size / mag;
            diry *= size / mag;

            x += dirx * speed;
            y += diry * speed;

        }

        public void draw(Canvas c) {

            c.drawLine((float) (x + 0 * dirx), (float) (y + 0 * diry), (float) (x + 1 * dirx), (float) (y + 1 * diry), p);

        }
    }

    SimView simView;
    Worm[] worms;
    int width, height;

    public Simulation(SimView simView) {
        this.simView = simView;
    }

    public void setNewTarget(Worm w) {

        w.targetx = Math.random() * width;
        w.targety = Math.random() * height;

    }

    protected void updateProperties(int width, int height) {
        this.width = width;
        this.height = height;
        if (worms == null) {

            worms = new Worm[10];
            for (Worm w : worms) {
                w = new Worm(this);
                w.size = (int) (10 * Math.random()) + 5;
                w.x = (Math.random() * width);
                w.y = (Math.random() * height);
                w.targetx = (Math.random() * width);
                w.targety = (Math.random() * height);
                w.state = Math.random();
            }

        }

        for (Worm w : worms) {
            w.update();
        }
        
    }

    protected void drawMethod(int width, int height) {

        //defaultDrawMethod(width, height);

        for (Worm w : worms) {
            w.draw(new Canvas(simView.getBuffer()));
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
