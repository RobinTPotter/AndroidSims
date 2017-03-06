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

    SimView simView;

    public Simulation(SimView simView) {
        this.simView = simView;
    }

    protected void updateProperties(int width, int height) {
    }

    protected void drawMethod(int width, int height) {

        //defaultDrawMethod(width, height);

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

        Canvas c = new Canvas(simView.getBuffer());
        Paint p = new Paint();
        p.setColor(Color.RED);
        c.drawOval(new RectF(event.getX() - 10, event.getY() - 10, event.getX() + 10, event.getY() + 10), p);

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
        Canvas c = new Canvas(simView.getBuffer());
        Paint p = new Paint();
        p.setColor(Color.CYAN);
        p.setStyle(Paint.Style.STROKE);
        c.drawOval(new RectF(e.getX()-20,e.getY()-20,e.getX()+20,e.getY()+20), p);
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        // simView.message("fling");
        Canvas c = new Canvas(simView.getBuffer());
        Paint p = new Paint();
        p.setColor(Color.CYAN);
        c.drawLine(e1.getX(),e1.getY(),e2.getX(),e2.getY(), p);
        return false;
    }

    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        //simView.message("scale");
        //

        Canvas c = new Canvas(simView.getBuffer());
        Paint p = new Paint();
        p.setColor(Color.YELLOW);
        p.setStyle(Paint.Style.STROKE);
        c.drawRect(new RectF(
                detector.getFocusX() - detector.getCurrentSpanX() / 2,
                detector.getFocusY() - detector.getCurrentSpanY() / 2,
                detector.getFocusX() + detector.getCurrentSpanX() / 2,
                detector.getFocusY() + detector.getCurrentSpanY() / 2
        ), p);
        return true;
    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {
        //simView.message("scale begin");

        Canvas c = new Canvas(simView.getBuffer());
        Paint p = new Paint();
        p.setColor(Color.GREEN);
        c.drawRect(new RectF(
                detector.getFocusX() - detector.getCurrentSpanX() / 2,
                detector.getFocusY() - detector.getCurrentSpanY() / 2,
                detector.getFocusX() + detector.getCurrentSpanX() / 2,
                detector.getFocusY() + detector.getCurrentSpanY() / 2
        ), p);

        return true;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {
        //simView.message("scale end");
        Canvas c = new Canvas(simView.getBuffer());
        Paint p = new Paint();
        p.setColor(Color.BLUE);
        c.drawRect(new RectF(
                detector.getFocusX() - detector.getCurrentSpanX() / 2,
                detector.getFocusY() - detector.getCurrentSpanY() / 2,
                detector.getFocusX() + detector.getCurrentSpanX() / 2,
                detector.getFocusY() + detector.getCurrentSpanY() / 2
        ), p);
    }
}
