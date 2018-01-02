package com.example.lpf.finaldemo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MainActivity extends AppCompatActivity {

    private View btnTest;
    private View btnList;
    private static TextView tvTestResult;
    private final MyHandler mHandler = new MyHandler(this);
    private String a_account = "FJR";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnTest=findViewById(R.id.btnTestSql);
        btnList=findViewById(R.id.btnList);
        tvTestResult = (TextView)findViewById(R.id.tvTestResult);

        btnTest.setOnClickListener(getClickEvent());
        btnList.setOnClickListener(getClickEvent());
    }

    private View.OnClickListener getClickEvent(){
        return new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                tvTestResult.setText("...");
                if(v==btnTest){
                    test();
                }
                if(v==btnList){
                    Intent intent = new Intent(MainActivity.this, DormListActivity.class);
                    intent.putExtra("a_account", a_account.toString());
                    startActivity(intent);
                }
            }
        };
    }
    private void test() {
        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
//                String ret= DBUtil.QueryUsers("select top 3 * from Users");
                Message msg = new Message();
                msg.what = 1001;
                msg.obj = "test";
                mHandler.sendMessage(msg);
            }
        });
        thread.start();
    }


    static class MyHandler extends Handler {
        private final WeakReference<MainActivity> mActivity;

        public MyHandler(MainActivity activity) {
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            MainActivity activity = mActivity.get();
            if (activity != null) {
                switch (msg.what) {
                    case 1001:
                        String str = msg.obj.toString();
                        tvTestResult.setText(str);
                        break;
                    default:
                        break;
                }
            }
        }
    }

}
