package com.example.seol.sos;

/**
 * Created by Seol on 2017-12-17.
 */

import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class VersionAskActivity extends AppCompatActivity {
    public TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_version_ask);

        //액션바 설정하기//
        //액션바 타이틀 변경하기
        getSupportActionBar().setTitle("SOS 헬프 앱");
        //액션바 배경색 변경
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFF339999));
        //홈버튼 표시
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //액션바 숨기기
        //hideActionBar();

        textView=findViewById(R.id.versionName);
        textView.setText(getAppVersionName());

        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss(); // 창 사라지고
            }
        });

        Button versionCheck = (Button) findViewById(R.id.versionCheck);
        versionCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                alert.setMessage("현재 버전은 "+getAppVersionName()+" 입니다.");
                alert.show();
            }
        });
    }
    public String getAppVersionName(){
        PackageInfo packageInfo = null;
        //패키지 인포 초기화
        try{
            packageInfo = getPackageManager().getPackageInfo(getPackageName(),0);

        }catch(PackageManager.NameNotFoundException e){
            e.printStackTrace();
            return "";
        }
        return packageInfo.versionName;
    }
}
