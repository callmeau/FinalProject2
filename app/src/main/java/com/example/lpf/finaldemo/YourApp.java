package com.example.lpf.finaldemo;

import android.app.Application;
import android.util.Log;

import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;

import io.yunba.android.manager.YunBaManager;

/**
 * Created by victory on 2018/1/5.
 */

public class YourApp extends Application {

    public void onCreate() {

        super.onCreate();
        YunBaManager.start(getApplicationContext());

        YunBaManager.subscribe(getApplicationContext(), new String[]{"t1"}, new IMqttActionListener() {

            @Override
            public void onSuccess(IMqttToken arg0) {
                String TAG = "log: ";
                Log.d(TAG, "Subscribe topic succeed");
            }

            @Override
            public void onFailure(IMqttToken arg0, Throwable arg1) {
                String TAG = "log: ";
                Log.d(TAG, "Subscribe topic failed") ;
            }
        });

    }
}