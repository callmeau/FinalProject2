package com.example.lpf.finaldemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import io.yunba.android.manager.YunBaManager;

/**
 * Created by victory on 2018/1/5.
 */


public class MyReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
// TODO Auto-generated method stub
        if (YunBaManager.MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
            String topic = intent.getStringExtra(YunBaManager.MQTT_TOPIC);
            String msg = intent.getStringExtra(YunBaManager.MQTT_MSG);

// 在这里处理从服务器发布下来的消息， 比如显示通知栏， 打开 Activity 等等
            StringBuilder showMsg = new StringBuilder();
            showMsg.append("Received message from server: ")
                    .append(YunBaManager.MQTT_TOPIC).append(" = ")
                    .append(topic).append(" ").append(YunBaManager.MQTT_MSG)
                    .append(" = ").append(msg);
            DemoUtil.showNotifation(context, topic, msg);
        }
    }
}