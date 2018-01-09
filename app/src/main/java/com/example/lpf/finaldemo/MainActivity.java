package com.example.lpf.finaldemo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private View btnTest;
    private View btnList;
    private View btnEdit;
    private View order;
    private View repair;
    private View exchange;
    private View notify;
    private static TextView tvTestResult;
    private final MyHandler mHandler = new MyHandler(this);

    private int building_id = 1;
    private String stu_account = "stu1";//学生账号
    private int dorm_id=1;

    private String a_account = "FJR";

    private static MyListView w_listView;//订水列表
    private List<item> water_list = new ArrayList<item>();//订水list数据
    private ItemAdapter water_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnTest=findViewById(R.id.btnTestSql);
        btnList=findViewById(R.id.btnList);
        order = findViewById(R.id.order);
        repair = findViewById(R.id.repair);

        exchange = findViewById(R.id.exchange);
        notify = findViewById(R.id.notify);

        btnEdit=findViewById(R.id.btnInputInfo);
        tvTestResult = (TextView)findViewById(R.id.tvTestResult);
        btnTest.setOnClickListener(getClickEvent());
        btnEdit.setOnClickListener(getClickEvent());
        btnList.setOnClickListener(getClickEvent());
        order.setOnClickListener(getClickEvent());
        repair.setOnClickListener(getClickEvent());
        final Button login = (Button) findViewById(R.id.login);
  
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Log_in_activity.class);
                startActivity(intent);
            }
        });

        final Button stu = (Button) findViewById(R.id.btn_stu);
        stu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, student_main_page.class);
                intent.putExtra("account","stu1");
                startActivity(intent);
            }
        });

        final Button admin = (Button) findViewById(R.id.btn_admin);
        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, admin_main_page.class);
                intent.putExtra("account","FJR");
                startActivity(intent);
            }
        });
}
        exchange.setOnClickListener(getClickEvent());
        notify.setOnClickListener(getClickEvent());
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
                else if(v==btnList){
                    Intent intent = new Intent(MainActivity.this, DormListActivity.class);
                    intent.putExtra("a_account", a_account.toString());
                    startActivity(intent);
                }
                else if(v==btnEdit){
                    Intent intent = new Intent(MainActivity.this,edit_informationActivity.class);
                    intent.putExtra("stu_account",stu_account);
                    startActivity(intent);
                }
                else if(v==order){
                    Intent intent = new Intent(MainActivity.this,Order_editActivity.class);
                    intent.putExtra("dorm_id",dorm_id);
                    startActivity(intent);
                }
                else if(v==repair){
                    Intent intent = new Intent(MainActivity.this,repair_edit.class);
                    intent.putExtra("dorm_id",dorm_id);
                    startActivity(intent);
                }
                else if(v==exchange){
                    Intent intent = new Intent(MainActivity.this,RoomUpdateActivity.class);
                    intent.putExtra("a_account",a_account);
                    startActivity(intent);
                }
                else if(v==notify){
                    Intent intent = new Intent(MainActivity.this,Notification_EditActivity.class);
                    intent.putExtra("a_account",a_account);
                    intent.putExtra("building_id",building_id);
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
