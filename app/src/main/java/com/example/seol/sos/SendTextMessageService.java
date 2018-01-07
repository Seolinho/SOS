package com.example.seol.sos;

import android.telephony.SmsManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Seol on 2017-12-17.
 */

public class SendTextMessageService {

    public void sendSMS() {
//        String SENT = "SMS_SENT";
//        String DELIVERED = "SMS_DELIVERED";
        SmsManager sms = SmsManager.getDefault();
        // DB에서 정보를 List으로 받는다고 가정

        List<String> receiverNumber = new ArrayList<>();
        //아마도 receiverNumner = DB연결클래스.getReciverList()

        receiverNumber.add("010-6791-1657");
        String msg = "테스트입니다.";

        // 받은 정보에 따라
        for (String phoneNumber : receiverNumber) {
            sms.sendTextMessage(phoneNumber, null, msg, null, null);
        }
    }
}