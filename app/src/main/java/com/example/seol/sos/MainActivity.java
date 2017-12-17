package com.example.seol.sos;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Intent shakingService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityCompat.requestPermissions(this ,new String[]{Manifest.permission.SEND_SMS},1);
        shakingService = new Intent(this, ShakingSensor.class);
        SharedPreferences pref = getSharedPreferences("pref",MODE_PRIVATE);
        String shakingOption = pref.getString("shakingUse","N");
        //옵션이 켜져 있으면 앱 시작과 동시에 흔들기 서비스 실행
        if(shakingOption.equals("Y")){
            startService(shakingService);
        }
        //액션바 설정하기//
        //액션바 타이틀 변경하기
        getSupportActionBar().setTitle("액션바");
        //액션바 배경색 변경
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFF339999));
        //홈버튼 표시
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //액션바 숨기기
        //hideActionBar();



        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss(); // 창 사라지고
            }
        });

       /* Button sosBtn = (Button) findViewById(R.id.sosButton);
        sosBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendTextMessageService sendSMS = new SendTextMessageService();
                sendSMS.sendSMS();
                alert.setMessage("SOS문자가 전송되었습니다.");
                alert.show();
            }
        });*/


    }



    //액션버튼 메뉴 액션바에 집어 넣기
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    /**
     * 앱 종료시 흔들기 서비스도 함께 종료
     * author by 배진섭 2017.12.02
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(shakingService);
    }

    //액션버튼을 클릭했을때의 동작
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        Intent menuClick;
        //or switch문을 이용하면 될듯 하다.
        if (id == R.id.home) {
            Toast.makeText(this, "홈아이콘 클릭", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (id == R.id.myinfo) {
            Toast.makeText(this, "검색 클릭", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (id == R.id.setting) {
            menuClick = new Intent(getApplicationContext(), SettingActivity.class);
            startActivity(menuClick);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //액션바 숨기기
    private void hideActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null)
            actionBar.hide();
    }
}

