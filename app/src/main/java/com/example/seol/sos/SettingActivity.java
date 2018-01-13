package com.example.seol.sos;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;

/**
 * Created by Seol on 2017-12-17.
 */

public class SettingActivity extends AppCompatActivity {

    private String shakingOption;
    private SharedPreferences.Editor editor;
    private SharedPreferences pref;
    private Intent shakingService;
    RadioButton m30;
    RadioButton m60;
    /**
     * 환경설정 처리 부분
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        //액션바 설정하기//
        //액션바 타이틀 변경하기
        getSupportActionBar().setTitle("SOS 헬프 앱");
        //액션바 배경색 변경
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFF339999));
        //홈버튼 표시
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //액션바 숨기기
        //hideActionBar();
        pref = getSharedPreferences("pref",MODE_PRIVATE);
        editor = pref.edit();

        shakingService = new Intent(getApplicationContext(), ShakingSensor.class);
        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss(); // 창 사라지고
                onBackPressed(); // 뒤로가기
            }
        });
        final RadioGroup radioGroupForShaking = (RadioGroup) findViewById(R.id.radioGroupForShaking);
        Button saveButton = (Button)findViewById(R.id.okButton);
        Button myInfo = (Button) findViewById(R.id.myInfoSetting);
        Button contactInfo = (Button) findViewById(R.id.contactInfo);

        shakingOption = pref.getString("shakingUse", "N");
        int useShakingId;
        if(shakingOption.equals("Y")){
            useShakingId = R.id.useShaking;
        }else{
            useShakingId = R.id.notUseShaking;
        }
        RadioButton shakingSetChecked = (RadioButton) findViewById(useShakingId);
        shakingSetChecked.setChecked(true);

        boolean loctionOn =pref.getBoolean("locationOn",false);
        long interval = pref.getLong("Interval",0);
        m30 = findViewById(R.id.m30);
        m60 = findViewById(R.id.m60);
        if (interval==0){
            m30.setChecked(false);
            m60.setChecked(false);
        } else if (interval==1800000){
            m30.setChecked(true);
            m60.setChecked(false);
        } else {
            m30.setChecked(false);
            m60.setChecked(true);
        }
        final Switch swichChecked = (Switch) findViewById(R.id.locationTime);
        if(loctionOn){
            swichChecked.setChecked(true);
        }






        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 흔들기 옵션 처리 시작 부분
                int shakingId = radioGroupForShaking.getCheckedRadioButtonId();
                RadioButton shakingChecked = (RadioButton)findViewById(shakingId);
                String shakingValue = shakingChecked.getText().toString().trim(); //흔들기 혹은 버튼클릭
                editor.putBoolean("locationOn",swichChecked.isChecked());
                if (m30.isChecked()){
                    editor.putLong("Interval",1800000);

                }else if(m60.isChecked()){
                    editor.putLong("Interval",3600000);
                }

                if(shakingOption.equals("Y")){
                    stopService(shakingService); // 흔들기 기능이 ON인 경우 일단 서비스 중지
                }
//                editor.remove("shakingUse");
//                editor.commit();
                if(shakingValue.equals("흔들기")){
                    editor.putString("shakingUse", "Y");
                }else{
                    editor.putString("shakingUse", "N");
                }
                editor.commit();
                // 흔들기 옵션 처리 끝
                alert.setMessage("설정이 저장되었습니다.");
                alert.show();
                startService(shakingService);
                //Toast.makeText(getApplicationContext(),"설정이 저장되었습니다.", Toast.LENGTH_SHORT).show();


            }
        });

        myInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MyinfoActivity.class);
                startActivity(intent);
            }
        });

        contactInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ProtectorCallActivity.class);
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

