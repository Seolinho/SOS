package com.seol.sos;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;

import com.kakao.util.helper.log.Logger;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static com.kakao.util.helper.Utility.getPackageInfo;

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
        checkVersion();

        getKeyHash(getApplicationContext());
        byte[] sha1 = {
                0x3B, (byte)0xDA, (byte)0xA0, 0x5B, 0x4F, 0x35, 0x71, 0x02, 0x4E, 0x27, 0x22, (byte)0xB9, (byte)0xAc, (byte)0xB2, 0x77, 0x2F, (byte)0x9D, (byte)0xA9, (byte)0x9B, (byte)0xD9
        };
        Logger.e("keyHash: " + Base64.encodeToString(sha1, Base64.NO_WRAP));

    }
    public static String getKeyHash(final Context context) {
        PackageInfo packageInfo = getPackageInfo(context, PackageManager.GET_SIGNATURES);
        if (packageInfo == null)
            return null;

        for (Signature signature : packageInfo.signatures) {
            try {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                return Base64.encodeToString(md.digest(), Base64.NO_WRAP);
            } catch (NoSuchAlgorithmException e) {

            }
        }
        return null;
    }



    public void checkVersion(){
        VersionChecker versionChecker =new VersionChecker();
        versionChecker.execute(getPackageName());
    }

    class VersionChecker extends AsyncTask<String,Void,Boolean> {

        @Override
        protected Boolean doInBackground(String... params) {

            boolean update;
            String store_version = MarketVersionChecker.getMarketVersion(params[0]);
            try {
                String device_version = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
                if (store_version != null) {
                    if (store_version.compareTo(device_version) > 0) {
                        // 업데이트 필요
                        update =true;
                    } else {
                        update =false;
                    }
                } else {
                    update =false;
                }

            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
                update= false;
            }
            return update;
        }

        @Override
        protected void onPostExecute(Boolean aVoid) {
            super.onPostExecute(aVoid);
            if (aVoid){
                AlertDialog.Builder builder = new AlertDialog.Builder(IntroActivity.this);     // 여기서 this는 Activity의 this

// 여기서 부터는 알림창의 속성 설정
                builder .setMessage("새로운 업데이트가 있습니다.\n업데이트 하시겠습니까?")        // 메세지 설정
                        .setCancelable(false)        // 뒤로 버튼 클릭시 취소 가능 설정
                        .setPositiveButton("업데이트", new DialogInterface.OnClickListener() {
                            // 확인 버튼 클릭시 설정
                            public void onClick(DialogInterface dialog, int whichButton) {
                                final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
                                try {
                                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                                } catch (android.content.ActivityNotFoundException anfe) {
                                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                                }
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                            // 취소 버튼 클릭시 설정
                            public void onClick(DialogInterface dialog, int whichButton) {

                                dialog.cancel();
                            }
                        });

                final AlertDialog alertDialog = builder.create();    // 알림창 객체 생성
                alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialog) {
                        alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(Color.parseColor("#555555"));
                        alertDialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(Color.parseColor("#64a2f6"));
                    }
                });
                alertDialog.show();
            } else {
                SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
                boolean login = pref.getBoolean("login", false);
                boolean agree = pref.getBoolean("agree", false);
                if (!login) {
                    Intent intentMain = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intentMain);
                }else if(!agree){
                    Intent intent = new Intent(getApplicationContext(), AgreeActivity.class);
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }

            }

        }
    }

}


