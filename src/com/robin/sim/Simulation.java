package com.robin.sim;

import android.graphics.*;
import android.util.Log;
import android.view.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by potterr on 03/03/2017.
 */
public class Simulation implements View.OnTouchListener, GestureDetector.OnGestureListener, ScaleGestureDetector.OnScaleGestureListener, Worm.WormWrangler {

    SimView simView;
    ArrayList<Worm> worms = new ArrayList<Worm>();
    int width, height;
    int initialWorms = 10;
    Worm selectedWorm;
    Paint blackpaint = new Paint();
    float SELECT_BOX_SIZE = 6;
    

    static String OPTION_TARGET_NEAREST_TEXT = "Set Target Nearest";
    static String OPTION_UNSET_TARGET_TEXT="Unset Target";

    public Simulation(SimView simView) {
        this.simView = simView;

        Log.d("Simulation", "Create Simulation");

        addWorm(initialWorms);

    }

    private float distSquared(float x1, float y1, float x2, float y2) {
        return (x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1);

    }

    protected void updateProperties(int width, int height) {

        Log.d("Simulation", "update props");

        this.width = width;
        this.height = height;

        if (this.worms != null) {
            synchronized (worms) {
                for (Worm w : worms) {
                    if (w != null) {
                        if (!w.alive) w.init(width, height);
                        w.update();
                    } else {
                        Log.d("Simulation", "worm is null " + w);
                    }
                }
            }
        } else {
            Log.d("Simulation", "worms null");
        }

    }

    protected void drawMethod(int width, int height) {

        Log.d("Simulation", "draw");
        Canvas c = new Canvas(simView.getBuffer());
        c.drawRect(0, 0, width, height, blackpaint);

        if (worms != null) {
            synchronized (worms) {
                for (Worm w : worms) {
                    if (w != null)
                        w.draw(new Canvas(simView.getBuffer()));
                    if (w == selectedWorm) {
                        Paint green = new Paint();
                        green.setARGB(255, 0, 255, 0);
                        green.setStyle(Paint.Style.STROKE);
                        c.drawRect(new RectF(w.x - SELECT_BOX_SIZE, w.y - SELECT_BOX_SIZE, w.x + SELECT_BOX_SIZE, w.y + SELECT_BOX_SIZE), green);
                    }
                }
            }
        }

    }


/*
* actions*/

    protected boolean action(String command) {
        if (command.equals(OPTION_TARGET_NEAREST_TEXT)) {
            if (selectedWorm != null) {
                synchronized (worms) {
selectedWorm.nearestWorm=findAllWorms(selectedWorm,20).get(0);
selectedWorm.targetting=selectedWorm.nearestWorm;
                }

            }
        }  else      if (command.equals(OPTION_UNSET_TARGET_TEXT)) {
            if (selectedWorm != null) {
                synchronized (worms) {
selectedWorm.nearestWorm=findAllWorms(selectedWorm,20).get(0);
selectedWorm.targetting=null;
                }

            }
        }
        return false;
    }

    /*
    gesture methods
     */

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
        Worm w = findWorm(e.getX(), e.getY(), 2 * SELECT_BOX_SIZE * SELECT_BOX_SIZE);
        selectedWorm = w;
        Log.i("Worm", "got worm" + w);
       
           
       
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
 selectedWorm = addWorm(e.getX(), e.getY());
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


/*
interface methods WormWrangler
 */

    public void addWorm(int numWorms) {

        for (int ww = 0; ww < numWorms; ww++) {
            Worm w = new Worm(this);
            w.size = (int) (30 * Math.random()) + 15;
            w.state = Math.random();
            w.speed = (float) (Math.random() * 1.5 + 0.5);
            w.initSegments();
            worms.add(w);
            Log.d("Simulation", "worm is " + w);
        }

    }

    public Worm addWorm(float x, float y) {

        Worm w = new Worm(this);
        w.size = (int) (30 * Math.random()) + 15;
        w.x = x;
        w.y = y;
        w.targetx = x;
        w.targety = y;
        //set this worm alive to preserve x,y
        w.alive = true;
        w.state = Math.random();
        w.speed = (float) (Math.random() * 1.5 + 0.5);
        w.initSegments();

        synchronized (worms) {
            worms.add(w);
        }
        Log.d("Simulation", "worm is " + w);
        return w;

    }

    public void setNewTarget(Worm w) {

        Log.d("Simulation", "set target for " + w);
        w.targetx = (float) (Math.random() * width);
        w.targety = (float) (Math.random() * height);

    }

    public Worm findWorm(float x, float y, float dist2) {

        for (Worm w : worms) {
            if (distSquared(x, y, w.x, w.y) < dist2) { //ie 4 px away
                return w;
            }
        }
        return null;
    }
    
    public ArrayList<Worm> findAllWorms(float x, float y, float dist2) {
        ArrayList<Worm> list=new ArrayList<Worm>();
        float lastnearest=-0;
        
        for (Worm w : worms) {
            float d=distSquared(x, y, w.x, w.y);
              if (d!=0.0f&&d < dist2) { //ie 4 px away
                if (lastnearest==-1 || d<lastnearest) {
                    list.add(0,w);
                } else list.add(w);
            }
        }
        return list;
    }
    
    
    public Worm findWorm(Worm wormy, float dist2) {

        for (Worm w : worms) {
            if (distSquared(wormy.x, wormy.y, w.x, w.y) < dist2) { //ie 4 px away
                return w;
            }
        }
        return null;
    }
    
    public ArrayList<Worm> findAllWorms(Worm wormy, float dist2) {
        ArrayList<Worm> list=new ArrayList<Worm>();
        float lastnearest=-0;
        for (Worm w : worms) {
            float d=distSquared(wormy.x, wormy.y, w.x, w.y);
            if (d!=0.0f && d < dist2) { //ie 4 px away
                if (lastnearest==-1 || d<lastnearest) {
                    list.add(0,w);
                } else list.add(w);
            }
        }
        return list;
    }

}
