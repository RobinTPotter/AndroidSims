package com.robin.sim;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.ScaleGestureDetector;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

/**
 * Created by potterr on 02/03/2017.
 */
public class SimView extends SurfaceView {

    private SurfaceHolder surfaceHolder;
    private SimThread myThread;
    private Bitmap buffer;
    private Simulation simulation;
    GestureDetector gestureDetector;
    ScaleGestureDetector scaleGestureDetector;

    public SimView(Context context) {
        super(context);
        init();
    }

    public SimView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    public SimView(Context context, AttributeSet attributeSet, int defStyle) {
        super(context, attributeSet, defStyle);
        init();
    }

    public Bitmap getBuffer() { return buffer;}

    public GestureDetector getGestureDetector() { return gestureDetector; }
    public ScaleGestureDetector getScaleGestureDetector() { return scaleGestureDetector; }

    private void init() {

        message("init");

        myThread = new SimThread(this);

        surfaceHolder = getHolder();

        simulation=new Simulation(this);

        setOnTouchListener(simulation);

        gestureDetector = new GestureDetector(getContext(), simulation);
        scaleGestureDetector = new ScaleGestureDetector(getContext(), simulation);

        /*


        */

        surfaceHolder.addCallback(new SurfaceHolder.Callback() {

            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                myThread.setRunning(true);
                try {
                    myThread.start();
                } catch (Exception ex) {

                    message(ex.getMessage());
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder,
                                       int format, int width, int height) {
                // TODO Auto-generated method stub
                buffer = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                boolean retry = true;
                myThread.setRunning(false);
                while (retry) {
                    try {
                        myThread.join();
                        retry = false;
                    } catch (InterruptedException e) {
                        message("thread interrupted");
                    }
                }
            }
        });
    }

    protected void drawSomething(Canvas surfaceCanvas) {

        simulation.updateProperties(getWidth(), getHeight());

        if (buffer != null) {
            simulation.drawMethod( getWidth(), getHeight());
            surfaceCanvas.drawBitmap(buffer, 0, 0, null);
        }

    }

    public void message(CharSequence text) {

        Context context = getContext();
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();

    }
}


