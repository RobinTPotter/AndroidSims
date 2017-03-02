package com.robin.sim;

import android.graphics.Canvas;

/**
 * Created by potterr on 02/03/2017.
 */
public class SimThread extends Thread {

    SimView simView;
    private boolean running = false;

    public SimThread(SimView view) {
        simView = view;
    }

    public void setRunning(boolean run) {
        running = run;
    }

    @Override
    public void run() {
        while (running) {

            Canvas canvas = simView.getHolder().lockCanvas();

            if (canvas != null) {
                synchronized (simView.getHolder()) {
                    simView.drawSomething(canvas);
                }
                simView.getHolder().unlockCanvasAndPost(canvas);
            }

            try {
                sleep(50);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
    }

}


