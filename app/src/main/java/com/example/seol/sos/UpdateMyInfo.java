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
import android.widget.RadioButton;
import android.widget.RadioGroup;

/**
 * Created by Seol on 2017-12-17.
 */

public class UpdateMyInfo extends AppCompatActivity {

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myinfoupdate);

        //액션바 설정하기//
        //액션바 타이틀 변경하기
        getSupportActionBar().setTitle("SOS 헬프 앱");
        //액션바 배경색 변경
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFF339999));
        //홈버튼 표시
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        pref = getSharedPreferences("pref",MODE_PRIVATE);
        editor = pref.edit();

        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss(); // 창 사라지고
                onBackPressed(); // 뒤로가기
            }
        });

        String userNameText = pref.getString("userName","홍길동");
        final String userBirthdayText = pref.getString("userBirthday", "1900-01-01");
        String genderText = pref.getString("gender", "");
        String typeOfFrameText = pref.getString("frame","");
        String typeOfDisableText = pref.getString("typeOfDisable", "테스트");

        final EditText userName = (EditText) findViewById(R.id.userNameUpdate);
        final EditText userBirthday = (EditText) findViewById(R.id.birthdayUpdate);
        final EditText typeOfDisable = (EditText) findViewById(R.id.typeOfDisableUpdate);
        final RadioGroup genderGroup = (RadioGroup) findViewById(R.id.genderUpdate);
        final RadioGroup typeOfFrameGroup = (RadioGroup) findViewById(R.id.typeOfZimmerFrameUpdate);
        typeOfDisable.setText(typeOfDisableText);
        userName.setText(userNameText);
        userBirthday.setText(userBirthdayText);

        // 성별 setChecked
        int genderIdForSet;
        if(genderText.equals("여자")){
            genderIdForSet = R.id.female;
        }else{
            genderIdForSet = R.id.male;
        }
        RadioButton genderSetChecked = (RadioButton) findViewById(genderIdForSet);
        genderSetChecked.setChecked(true);

        // 보장구 setChecked
        int frameIdForSet;
        if(typeOfFrameText.equals("수동휠체어")){
            frameIdForSet = R.id.manualWheelChair;
        }else if(typeOfFrameText.equals("전동휠체어")){
            frameIdForSet = R.id.autoWheelChair;
        }else{
            frameIdForSet = R.id.walker;
        }
        RadioButton frameSetChecked = (RadioButton) findViewById(frameIdForSet);
        frameSetChecked.setChecked(true);


        Button updateMyinfo = (Button) findViewById(R.id.saveMyInfoButton);
        updateMyinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int genderId = genderGroup.getCheckedRadioButtonId();
                RadioButton gender = (RadioButton) findViewById(genderId);
                int frameId = typeOfFrameGroup.getCheckedRadioButtonId();
                RadioButton typeOfFrame = (RadioButton) findViewById(frameId);

                editor.putString("frame", typeOfFrame.getText().toString());
                editor.putString("userName",userName.getText().toString());
                editor.putString("userBirthday", userBirthday.getText().toString());
                editor.putString("typeOfDisable", typeOfDisable.getText().toString() );
                editor.putString("gender",gender.getText().toString());
                editor.commit();

                alert.setMessage("설정이 저장되었습니다.");
                alert.show();

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
