package com.robin.sim;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by deadmeat on 15/03/17.
 */
public class Settings extends Dialog {
    Simulation simulation;

    public Settings(Context c, Simulation simulation) {
        super(c);
this.simulation=simulation;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.burst_settings_panel);

    }

    protected void onStart() {



    }

}
