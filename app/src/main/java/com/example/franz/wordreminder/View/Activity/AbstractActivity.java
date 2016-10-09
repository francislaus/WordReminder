package com.example.franz.wordreminder.View.Activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Created by franz on 27.09.16.
 */

public class AbstractActivity extends AppCompatActivity {

    private static final String LOG_TAG = AbstractActivity.class.getSimpleName();

    //to switch activities
    public void switchActivity(Class<? extends Activity> clazz){
        Log.e(LOG_TAG, "SwitchActivity: "+clazz.getSimpleName());
        startActivity(new Intent(getApplicationContext(), clazz));
        finish();
    }

}
