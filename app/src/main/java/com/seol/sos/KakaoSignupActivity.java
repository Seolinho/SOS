package com.seol.sos;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.kakao.auth.ErrorCode;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeResponseCallback;
import com.kakao.usermgmt.response.model.UserProfile;
import com.kakao.util.helper.log.Logger;

/**
 * Created by Seol on 2017-12-17.
 */

public class KakaoSignupActivity extends Activity {
    /**
     * Main으로 넘길지 가입 페이지를 그릴지 판단하기 위해 호출되는 액티비티.
     * @param savedInstanceState 기존 session 정보가 저장된 객체
     */
    private SharedPreferences.Editor editor;
    private SharedPreferences pref;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestMe();
    }

    /**
     * 사용자의 상태를 알아 보기 위해 me API 호출을 한다.
     */
    protected void requestMe() { //유저의 정보를 받아오는 함수
        UserManagement.requestMe(new MeResponseCallback() {
            @Override
            public void onFailure(ErrorResult errorResult) {
                String message = "failed to get user info. msg=" + errorResult;
                Logger.d(message);
                Log.v("fail", "fail");

                ErrorCode result = ErrorCode.valueOf(errorResult.getErrorCode());
                if (result == ErrorCode.CLIENT_ERROR_CODE) {
                    finish();
                } else {
                    redirectLoginActivity();
                }
            }

            @Override
            public void onSessionClosed(ErrorResult errorResult) {
                redirectLoginActivity();
            }

            @Override
            public void onNotSignedUp() {} // 카카오톡 회원이 아닐 시 showSignup(); 호출해야함

            @Override
            public void onSuccess(UserProfile userProfile) {  //성공 시 userProfile 형태로 반환
                String kakaoID = String.valueOf(userProfile.getId()); // userProfile에서 ID값을 가져옴
                String kakaoNickname = userProfile.getNickname();     // Nickname 값을 가져옴
                String url = String.valueOf(userProfile.getProfileImagePath());
                //여기서 SharedPreferences로 로그인 여부 코딩
                //SharedUtil 객체생성
                /*SharedPreference sharedUtil = new SharedPreference();
                sharedUtil.put(getApplicationContext(),"login",true);*/
                pref = getSharedPreferences("pref",MODE_PRIVATE);
                editor = pref.edit();
                boolean login = true;
                editor.putBoolean("login",login);
                editor.commit();


                Logger.d("UserProfile : " + userProfile);
                Log.d("kakao", "==========================");
                Log.d("kakao", ""+userProfile);
                Log.d("kakao", kakaoID);
                Log.d("kakao", kakaoNickname);
                Log.d("kakao", "==========================");
                redirectMainActivity(url, kakaoNickname); // 로그인 성공시 MainActivity로
            }
        });
    }


    private void redirectMainActivity(String url, String nickname) {
        Intent intent = new Intent(KakaoSignupActivity.this, AgreeActivity.class);
        intent.putExtra("url", url);
        intent.putExtra("nickname", nickname);
        startActivity(intent);
        finish();
    }

    protected void redirectLoginActivity() {
        final Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        finish();
    }

}
