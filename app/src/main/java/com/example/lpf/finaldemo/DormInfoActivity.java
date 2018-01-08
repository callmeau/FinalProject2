package com.example.lpf.finaldemo;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DormInfoActivity extends AppCompatActivity {

    private String dorm_id;
    private String building_id;
    private String role;
    private boolean isStudent = true;

    private TextView building_name;
    private TextView dormitory_name;
    private ImageView btn_update1;
    private ImageView btn_update2;
    private CheckBox check_box;
    private boolean order_done = false;

    private static ListView rListView;
    private static ListView wListView;
    private static ListView sListView;
    private static SimpleAdapter sAdapter;
    private static LvAdapter rAdapter;
    private static LvAdapter wAdapter;
    private static List<Map<String, String>> repairDatas = new ArrayList<>();
    private static List<Map<String, String>> waterDatas = new ArrayList<>();
    private final MyHandler mHandler = new MyHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dormitory_layout);
        init();
    }

    private void init(){
        dorm_id = this.getIntent().getStringExtra("dorm_id");
        building_id = this.getIntent().getStringExtra("building_id");
        role = this.getIntent().getStringExtra("role");
        if (role.equals("admin")) {
            isStudent = false;
        }

        building_name = (TextView)findViewById(R.id.Building);
        dormitory_name = (TextView)findViewById(R.id.dormitory_name);
        btn_update1 = (ImageView)findViewById(R.id.btn_update1);
        btn_update2 = (ImageView)findViewById(R.id.btn_update2);
        check_box = (CheckBox)findViewById(R.id.order_check);


//        rAdapter = new LvAdapter(repairDatas);
//        rListView.setAdapter(rAdapter);
//        wAdapter = new LvAdapter(waterDatas);
//        wListView.setAdapter(wAdapter);
    }

    private static class MyHandler extends Handler {
        private final WeakReference<DormInfoActivity> mActivity;

        public MyHandler(DormInfoActivity activity) {
            mActivity = new WeakReference<>(activity);
        }
        @Override
        public void handleMessage(Message msg) {
            DormInfoActivity activity = mActivity.get();
            if (activity != null) {
                switch (msg.what) {
                    case 1003:

                        break;
                    default:
                        break;
                }
            }
        }
    }
}
