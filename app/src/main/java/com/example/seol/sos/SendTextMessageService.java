package com.example.seol.sos;

import android.content.Context;
import android.content.SharedPreferences;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Seol on 2017-12-17.
 */

public class SendTextMessageService {
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    Context context;
    List<String> receiverNumber = new ArrayList<>();
    SmsManager sms = SmsManager.getDefault();

    public SendTextMessageService(Context context) {
        this.context = context;
    }

    public void sendSMS(int flag) {

        //        String SENT = "SMS_SENT";

        //        String DELIVERED = "SMS_DELIVERED";

        // DB에서 정보를 List으로 받는다고 가정
        pref = context.getSharedPreferences("pref", MODE_PRIVATE);

        //String position = pref.getString("position","");
        String frame = pref.getString("frame", "");
        String type = pref.getString("typeOfDisable", "");


        String msg = "";

        if (flag == 1) {
            msg = "테스트입니다." + "\n"
                    + "현재 위치 : " + ShakingSensor.markerTitle + "\n" +
                    "보장구 유형 : " + frame + "\n" +
                    "장애 급수 : " + type;
            send(msg, flag);
        } else {
            msg = "테스트입니다." + "\n"
                    + "현재 위치 : " + ShakingSensor.markerTitle;
            send(msg, flag);
        }

        // 받은 정보에 따라

    }

    private void send(String msg, int flag) {
        receiverNumber.clear();
        if (flag == 1) {
            settingPhone();
            receiverNumber.add("112");
            receiverNumber.add("119");
        } else {
            settingPhone();
        }
        for (String phoneNumber : receiverNumber) {
            Log.d("String", phoneNumber);
            if (!TextUtils.isEmpty(phoneNumber)) {
                sms.sendTextMessage(phoneNumber, null, msg, null, null);
            }
        }
    }

    private void settingPhone() {
        for (int i = 0; i < 5; i++) {
            String pNumber = pref.getString("pNumber" + i, "");
            receiverNumber.add(pNumber);
        }
    }
}