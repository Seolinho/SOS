package com.example.seol.sos;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

/**
 * Created by Seol on 2017-12-17.
 */

public class ProtectorCallActivity extends AppCompatActivity {

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    final int[] nameIdSet = {R.id.pName1, R.id.pName2, R.id.pName3, R.id.pName4, R.id.pName5};
    final int[] numberIdSet = {R.id.pNumber1, R.id.pNumber2, R.id.pNumber3, R.id.pNumber4, R.id.pNumber5};
    final int[] switchIdSet = {R.id.switch1, R.id.switch2, R.id.switch3, R.id.switch4, R.id.switch5};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_protector_call);
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
        setView();

        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss(); // 창 사라지고
                onBackPressed();
            }
        });

        Button save = (Button) findViewById(R.id.saveContactButton);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i = 1; i <= 5 ; i++){
                    String nameKey = "pName" + i;
                    String numberKey = "pNumber" + i;
                    final String switchKey = "switch" + i;
                    EditText name = (EditText) findViewById(nameIdSet[i-1]);
                    EditText number = (EditText) findViewById(numberIdSet[i-1]);
                    Switch smsOption = (Switch) findViewById(switchIdSet[i-1]);
                    String nameValue = name.getText().toString();
                    String numberValue = number.getText().toString();

                    if(smsOption.isChecked())
                        editor.putBoolean(switchKey, true);
                    else
                        editor.putBoolean(switchKey, false);

                    editor.putString(nameKey, nameValue);
                    editor.putString(numberKey, numberValue);
                    editor.commit();
                }
                alert.setMessage("연락처가 저장되었습니다.");
                alert.show();

            }
        });

    }


    void setView(){
        for(int i = 1; i <= 5; i++){
            String nameKey = "pName" + i;
            String numberKey = "pNumber" + i;
            final String switchKey = "switch" + i;
            EditText name = (EditText) findViewById(nameIdSet[i-1]);
            EditText number = (EditText) findViewById(numberIdSet[i-1]);
            Switch smsOption = (Switch) findViewById(switchIdSet[i-1]);

            String nameValue = pref.getString(nameKey,"");
            String numberValue = pref.getString(numberKey,"");
            boolean switchValue = pref.getBoolean(switchKey, false);

            name.setText(nameValue);
            number.setText(numberValue);

            if(switchValue)
                smsOption.setChecked(true);
            else
                smsOption.setChecked(false);

        }

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        setView();
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

