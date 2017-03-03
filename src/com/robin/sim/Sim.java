package com.robin.sim;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.GestureDetector;

public class Sim extends Activity


{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

    }
}
