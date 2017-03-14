package com.robin.sim;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;

public class Sim extends Activity {


    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

    }

    /*
    menu stuff
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        if (menu.findItem(0) == null)
            return createMenu(menu);
        else return true;

    }

    public boolean createMenu(Menu menu) {

        menu.clear();

        int order = 0;

        menu.add(0, Menu.NONE, order++, Simulation.OPTION_TARGET_NEAREST_TEXT);
        menu.add(0, Menu.NONE, order++, Simulation.OPTION_UNSET_TARGET_TEXT);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        boolean success = false;
        Simulation sim =((SimView)findViewById(R.id.simView)).getSimulation();

        /// Handle item selection
        if (item.getTitle().equals(Simulation.OPTION_TARGET_NEAREST_TEXT) ) {
            success= sim.action(Simulation.OPTION_TARGET_NEAREST_TEXT);
        }
        return success;
    }

}
