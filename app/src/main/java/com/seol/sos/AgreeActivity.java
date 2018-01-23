package com.seol.sos;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;


public class AgreeActivity extends AppCompatActivity {
    CheckBox chk1,chk2,chk3;
    Button btn;
    private SharedPreferences.Editor editor;
    private SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agree);
        chk1 = (CheckBox)findViewById(R.id.chkbox1);
        chk2 = (CheckBox)findViewById(R.id.chkbox2);
        chk3 = (CheckBox)findViewById(R.id.chkbox3);
        btn = (Button)findViewById(R.id.btn);


        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss(); // 창 사라지고
            }
        });

        chk3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.getId()==R.id.chkbox3){
                    chk1.setChecked(true);
                    chk2.setChecked(true);
                }
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!chk1.isChecked()) {
                    alert.setMessage("개인정보 수집 이용에 동의해 주세요");
                    alert.show();
                }else if(!chk2.isChecked()){
                    alert.setMessage("위치기반서비스 약관에 동의해 주세요");
                    alert.show();
                }
                if(chk1.isChecked()&&chk2.isChecked()&&chk3.isChecked()||chk1.isChecked()&&chk2.isChecked()) {
                    pref = getSharedPreferences("pref", MODE_PRIVATE);
                    editor = pref.edit();
                    boolean agree = true;
                    editor.putBoolean("agree", agree);
                    editor.commit();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }

            }
        });

    }



}

