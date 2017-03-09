package com.robin.sim;

import android.graphics.*;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by potterr on 03/03/2017.
 */
public class Simulation implements View.OnTouchListener, GestureDetector.OnGestureListener, ScaleGestureDetector.OnScaleGestureListener, Worm.WormWrangler {

    SimView simView;
    ArrayList<Worm> worms=new ArrayList<Worm>();
    int width, height;
    int initialWorms=5;

    public Simulation(SimView simView) {
        this.simView = simView;


        Log.d("Simulation", "Create Simulation");

        addWorm(initialWorms    );

    }
public void addWorm(int numWorms) {


    for (int ww = 0; ww < numWorms; ww++) {
        Worm w = new Worm(this);
        w.size = (int) (10 * Math.random()) + 25;
        w.x = 100;
        w.y = 100;
        w.targetx = 100;
        w.targety = 100;
        w.state = Math.random();
        w.speed = (float)(Math.random()*0.5+0.5);
        w.initSegments();
       worms.add( w);
        Log.d("Simulation", "worm is " + w);
    }


}
    public void setNewTarget(Worm w) {

        Log.d("Simulation", "set target for " + w);
        w.targetx = (float)(Math.random() * width);
        w.targety = (float)(Math.random() * height);


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

        Canvas c = new Canvas(simView.getBuffer());
        Paint p = new Paint();
        c.drawRect(0, 0, width, height, p);

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
