package com.example.seol.sos;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

/**
 * Created by Seol on 2017-12-17.
 */

public class SettingActivity extends AppCompatActivity {

    private String shakingOption;
    private SharedPreferences.Editor editor;
    private SharedPreferences pref;
    private Intent shakingService;

    /**
     * 환경설정 처리 부분
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

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


        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 흔들기 옵션 처리 시작 부분
                int shakingId = radioGroupForShaking.getCheckedRadioButtonId();
                RadioButton shakingChecked = (RadioButton)findViewById(shakingId);
                String shakingValue = shakingChecked.getText().toString().trim(); //흔들기 혹은 버튼클릭

                if(shakingOption.equals("Y")){
                    stopService(shakingService); // 흔들기 기능이 ON인 경우 일단 서비스 중지
                }
//                editor.remove("shakingUse");
//                editor.commit();
                if(shakingValue.equals("흔들기")){
                    editor.putString("shakingUse", "Y");
                    editor.commit();
                    startService(shakingService);
                }else{
                    editor.putString("shakingUse", "N");
                    editor.commit();
                }
                // 흔들기 옵션 처리 끝
                alert.setMessage("설정이 저장되었습니다.");
                alert.show();
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
    public void onBackPressed(){
        super.onBackPressed();
    }
}

