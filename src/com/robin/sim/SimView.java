package com.robin.sim;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
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

    private void init() {

        message("init");

        myThread = new SimThread(this);

        surfaceHolder = getHolder();

        /*


        */

        surfaceHolder.addCallback(new SurfaceHolder.Callback() {

            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                myThread.setRunning(true);
                 try{   myThread.start();}
                 catch  (Exception ex) {

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

        updateProperties();

        if (buffer != null) {
            drawMethod(new Canvas(buffer));
            surfaceCanvas.drawBitmap(buffer, 0, 0, null);
        }

    }

    private void updateProperties() {
    }

    private void drawMethod(Canvas canvas) {

        Paint p = new Paint();
        int rr = 0, gg = 0, bb = 0;
        float ll = 0;

        while (ll < 0.3) {

            rr = (int) (Math.random() * 255);
            gg = (int) (Math.random() * 255);
            bb = (int) (Math.random() * 255);

            ll = (0.2126f * rr + 0.7152f * gg + 0.0722f * bb);
        }

        p.setARGB(255, rr, gg, bb);

        canvas.drawRect(new Rect(
                        (int) (Math.random() * getWidth()), (int) (Math.random() * getHeight()),
                        (int) (Math.random() * getWidth()), (int) (Math.random() * getHeight())),
                p);

    }

    public void message(CharSequence text) {

        Context context = getContext();
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();

    }
}


