package com.example.seol.sos;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Seol on 2017-12-17.
 */

public class ShakingSensor extends Service implements SensorEventListener {

    private long lastTime;
    private float speed;
    private float lastX;
    private float lastY;
    private float lastZ;

    private float x, y, z;
    private static final int SHAKE_THRESHOLD = 800;

    private static final int DATA_X = SensorManager.DATA_X;
    private static final int DATA_Y = SensorManager.DATA_Y;
    private static final int DATA_Z = SensorManager.DATA_Z;
    private int cnt = 0;
    public static String markerTitle="";
    private SensorManager sensorManager;
    private Sensor accelerormeterSensor;
    private Timer timer;
    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        Log.i("MyServiceIntent", "Service is Create");

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerormeterSensor = sensorManager
                .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        SharedPreferences pref = getSharedPreferences("pref",MODE_PRIVATE);
        String shakingOption = pref.getString("shakingUse","N");
        if (accelerormeterSensor != null&&shakingOption.equals("Y"))
            sensorManager.registerListener(this, accelerormeterSensor,
                    SensorManager.SENSOR_DELAY_GAME);

        boolean loctionOn =pref.getBoolean("locationOn",false);

        if (loctionOn){
            Log.d("timer",loctionOn+"");

            long interval =pref.getLong("Interval",1800000);

            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {

                    SendTextMessageService sms = new SendTextMessageService(getApplicationContext());
                    sms.sendSMS(2);
                }
            };
            if (timer==null){
                timer = new Timer();
                timer.schedule(timerTask,0,interval);
            }

        } else {
            if (timer!=null){
                timer.cancel();
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        Log.i("MyServiceIntent", "Service is destroy");
        cnt = 0;
        if (sensorManager != null)
            sensorManager.unregisterListener(this);

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        // TODO Auto-generated method stub
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            long currentTime = System.currentTimeMillis();
            long gabOfTime = (currentTime - lastTime);

            if (gabOfTime > 100) {
                lastTime = currentTime;

                x = event.values[SensorManager.DATA_X];
                y = event.values[SensorManager.DATA_Y];
                z = event.values[SensorManager.DATA_Z];

                speed = Math.abs(x + y + z - lastX - lastY - lastZ) / gabOfTime
                        * 10000;


                if (speed > SHAKE_THRESHOLD) {
                    SendTextMessageService send = new SendTextMessageService(getApplicationContext());
                    if (cnt == 0) {   //한 번 문자 보내고 그만 보냄
                        send.sendSMS(1);
                        cnt++;
                        Toast.makeText(getApplicationContext(), "SOS문자가 전송되었습니다.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "이미 SOS문자가 전송되었습니다.", Toast.LENGTH_SHORT).show();
                    }


                }

                if (speed == 0) {
                    cnt = 0;
                }// 멈췄다가 다시 하면 문자 전송 가능하도록
                lastX = event.values[DATA_X];
                lastY = event.values[DATA_Y];
                lastZ = event.values[DATA_Z];
            }
        }

    }
}
