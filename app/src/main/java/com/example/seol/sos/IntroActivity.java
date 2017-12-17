package com.example.seol.sos;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Seol on 2017-12-17.
 */

public class IntroActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_intro);



        final ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        Handler mHandler = new Handler();
        mHandler.postDelayed(new loadingRun(), 2500);
    }


    class loadingRun implements Runnable
    {
        @Override
        public void run(){
            Intent intentMain = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intentMain);

            finish();

            //commit test 2
        }
    }
}
