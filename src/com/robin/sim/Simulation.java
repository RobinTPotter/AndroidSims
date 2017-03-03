package com.robin.sim;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

/**
 * Created by potterr on 03/03/2017.
 */
public class Simulation implements View.OnTouchListener, GestureDetector.OnGestureListener, ScaleGestureDetector.OnScaleGestureListener {

    SimView simView;

    public Simulation(SimView simView) {
        this.simView = simView;
    }

    protected void updateProperties(int width, int height) {
    }

    protected void drawMethod(int width, int height) {
        defaultDrawMethod(width, height);
    }

    protected void defaultDrawMethod(int width, int height) {

        Canvas canvas = new Canvas(simView.getBuffer());

        Paint p = new Paint();
        int rr = 0, gg = 0, bb = 0;
        float ll = 0;

        while (ll < 0.5) {

            rr = (int) (Math.random() * 255);
            gg = (int) (Math.random() * 255);
            bb = (int) (Math.random() * 255);

            ll = (0.2126f * rr + 0.7152f * gg + 0.0722f * bb);
        }

        p.setARGB(255, rr, gg, bb);

        canvas.drawRect(new Rect(
                        (int) (Math.random() * width), (int) (Math.random() * height),
                        (int) (Math.random() * width), (int) (Math.random() * height)),
                p);

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        simView.message("touch");
        return simView.getGestureDetector().onTouchEvent(event) || simView.getScaleGestureDetector().onTouchEvent(event)  ;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        simView.message("down");
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {
        simView.message("show press");
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        simView.message("single tap up");
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        simView.message("scroll");
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        simView.message("long press");
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        simView.message("fling");
        return false;
    }

    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        simView.message("scale");
        return false;
    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {
        simView.message("scale begin");
        return false;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {
        simView.message("scale end");
    }
}
