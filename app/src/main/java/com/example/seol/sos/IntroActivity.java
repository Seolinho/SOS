package com.example.seol.sos;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Seol on 2017-12-17.
 */

public class IntroActivity extends AppCompatActivity {

    private SharedPreferences pref;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_intro);
        //액션바 설정하기//
        //액션바 타이틀 변경하기
        getSupportActionBar().setTitle("SOS 헬프 앱");
        //액션바 배경색 변경
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFF339999));


        Handler mHandler = new Handler();
        mHandler.postDelayed(new loadingRun(), 2500);
    }


    class loadingRun implements Runnable //쓰레드 만드는것
    {
        @Override
        public void run(){

            SharedPreferences pref = getSharedPreferences("pref",MODE_PRIVATE);
            boolean login = pref.getBoolean("login",false);


           if(!login) {
                Intent intentMain = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intentMain);
            }else {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
           /* Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();*/

            //commit test 3
        }
    }
}
