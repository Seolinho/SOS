package com.example.seol.sos;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Seol on 2017-12-17.
 */

public class MyinfoActivity extends AppCompatActivity {

    void setTextView(){
        SharedPreferences pref = getSharedPreferences("pref",MODE_PRIVATE);
        String userNameText = pref.getString("userName","홍길동");
        String userBirthdayText = pref.getString("userBirthday", "1900-01-01");
        String genderText = pref.getString("gender", "");
        String typeOfFrameText = pref.getString("frame","");
        String typeOfDisableText = pref.getString("typeOfDisable", "테스트");
        TextView userName = (TextView) findViewById(R.id.userName);
        TextView userBirthday = (TextView) findViewById(R.id.birthday);
        TextView typeOfDisable = (TextView) findViewById(R.id.typeOfDisable);
        TextView gender = (TextView) findViewById(R.id.gender);
        TextView typeOfFrame = (TextView) findViewById(R.id.typeOfZimmerFrame);
        userName.setText(userNameText);
        userBirthday.setText(userBirthdayText);
        typeOfDisable.setText(typeOfDisableText);
        gender.setText(genderText);
        typeOfFrame.setText(typeOfFrameText);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        setTextView();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myinfo);
        //액션바 설정하기//
        //액션바 타이틀 변경하기
        getSupportActionBar().setTitle("SOS 헬프 앱");
        //액션바 배경색 변경
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFF339999));
        //홈버튼 표시
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //액션바 숨기기
        //hideActionBar();
        setTextView();

        Button updateBtn = (Button) findViewById(R.id.updateMyInfo);

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), UpdateMyInfo.class);
                startActivity(intent);
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        defalut:
        return super.onOptionsItemSelected(item);
    }


}

