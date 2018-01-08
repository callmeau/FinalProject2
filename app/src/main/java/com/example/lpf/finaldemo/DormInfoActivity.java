package com.example.lpf.finaldemo;

import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.solo.library.ISlide;
import com.solo.library.OnClickSlideItemListener;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DormInfoActivity extends AppCompatActivity {

    private String dorm_id;
    private String role;
    private boolean isStudent = true;

    private static TextView building_name;
    private static TextView dormitory_name;
    private ImageView btn_update1;
    private ImageView btn_update2;

    private static MyListView rListView;
    private static MyListView wListView;
    private static ListView sListView;
    private static SimpleAdapter sAdapter;
    private static LvAdapter rAdapter;
    private static LvAdapter wAdapter;
    private static List<Map<String, String>> repairDatas;
    private static List<Map<String, String>> waterDatas;
    private static List<Map<String, Object>> stuList;
    private final MyHandler mHandler = new MyHandler(this);
    private String[] mStrs = {"stu_img","stu_name","stu_no","stu_school"};
    private int[] ids = {R.id.imageView4, R.id.item_name, R.id.item_sno, R.id.item_school};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dormitory_layout);
        init();
        dataInit();
        setOnClickListener();  // 按键监听
    }

    private void init(){
        dorm_id = this.getIntent().getStringExtra("dorm_id");
        role = this.getIntent().getStringExtra("role");
        if (!role.equals("student")) {
            isStudent = false;
        }

        building_name = (TextView)findViewById(R.id.Building);
        dormitory_name = (TextView)findViewById(R.id.dormitory_name);
        btn_update1 = (ImageView)findViewById(R.id.btn_update1);
        btn_update2 = (ImageView)findViewById(R.id.btn_update2);

        rListView = (MyListView) findViewById(R.id.RepairList);
        wListView = (MyListView) findViewById(R.id.WaterList);
        sListView = (ListView) findViewById(R.id.stuList);

        repairDatas = new ArrayList<>();
        waterDatas = new ArrayList<>();
        stuList = new ArrayList<>();

        rAdapter = new LvAdapter(repairDatas);
        rListView.setAdapter(rAdapter);
        wAdapter = new LvAdapter(waterDatas);
        wListView.setAdapter(wAdapter);
        sAdapter = new SimpleAdapter(this, stuList, R.layout.student_item_layout, mStrs, ids);
        sListView.setAdapter(sAdapter);
    }

    private void dataInit() {
        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String ret = DBUtil.QueryDormInfo(dorm_id);
                Message msg = new Message();
                msg.what = 1004;
                msg.obj = ret;
                mHandler.sendMessage(msg);

                String ret1 = DBUtil.QueryRepair(dorm_id);
                Message msg1 = new Message();
                msg1.what = 1005;
                msg1.obj = ret1;
                mHandler.sendMessage(msg1);

                String ret2 = DBUtil.QueryWater(dorm_id);
                Message msg2 = new Message();
                msg2.what = 1006;
                msg2.obj = ret2;
                mHandler.sendMessage(msg2);
            }
        });
        thread.start();
    }

    private void setOnClickListener(){
        btn_update1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String ret1 = DBUtil.QueryRepair(dorm_id);
                        Message msg1 = new Message();
                        msg1.what = 1005;
                        msg1.obj = ret1;
                        mHandler.sendMessage(msg1);
                    }
                });
                thread.start();
            }
        });

        btn_update2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String ret2 = DBUtil.QueryWater(dorm_id);
                        Message msg2 = new Message();
                        msg2.what = 1006;
                        msg2.obj = ret2;
                        mHandler.sendMessage(msg2);
                    }
                });
                thread.start();
            }
        });

        rAdapter.setupListView(rListView); //里面的逻辑是监听滚动关闭按钮显示
        rAdapter.setOnClickSlideItemListener(new OnClickSlideItemListener() {
            @Override
            public void onItemClick(ISlide iSlideView, View view, final int position) {
                // listview的单击事件
                if (isStudent) {
                    AlertDialog.Builder message = new AlertDialog.Builder(DormInfoActivity.this);
                    message.setTitle("确认订单");
                    message.setMessage("确认订单已完成？");
                    message.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogInterface, int i) {
                            // 在数据库中进行update数据
                            final Thread thread = new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    String R_id = repairDatas.get(position).get("id");
                                    DBUtil.UpdateRepair(R_id);
                                    Message msg = new Message();
                                    msg.arg1 = position;
                                    msg.what = 1009;
                                    mHandler.sendMessage(msg);
                                }
                            });
                            thread.start();
                        }
                    });
                    message.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });
                    message.create().show();
                }
                else {
                    Toast.makeText(DormInfoActivity.this, "没有确认权限", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onClick(ISlide iSlideView, View view, final int position) {
                //listview的滑动删除事件--控件的所有子控件的点击回调都会回调此方法
                if (view.getId() == R.id.btn_del) { //对删除按钮的监听（上面adapter的getBindOnClickViewsIds()中设置了R.id.btn_del）
                    iSlideView.close(); //关闭当前的按钮
                    if (isStudent) {
                        AlertDialog.Builder message = new AlertDialog.Builder(DormInfoActivity.this);
                        message.setTitle("删除订单");
                        message.setMessage("是否删除？");
                        message.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // 在数据库中进行delete数据
                                final Thread thread = new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        String R_id = repairDatas.get(position).get("id");
                                        DBUtil.DeleteRepair(R_id);
                                        Message msg = new Message();
                                        msg.arg1 = position;
                                        msg.what = 1007;
                                        mHandler.sendMessage(msg);
                                    }
                                });
                                thread.start();
                            }
                        });
                        message.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogInterface, int i) {
                            }
                        });
                        message.create().show();
                    }
                    else {
                        Toast.makeText(DormInfoActivity.this, "没有删除权限", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        wAdapter.setupListView(wListView); //里面的逻辑是监听滚动关闭按钮显示
        wAdapter.setOnClickSlideItemListener(new OnClickSlideItemListener() {
            @Override
            public void onItemClick(ISlide iSlideView, View view, final int position) {
                // listview的单击事件
                if (isStudent) {
                    AlertDialog.Builder message = new AlertDialog.Builder(DormInfoActivity.this);
                    message.setTitle("确认订单");
                    message.setMessage("确认订单已完成？");
                    message.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogInterface, int i) {
                            // 在数据库中进行update数据
                            final Thread thread = new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    String W_id = waterDatas.get(position).get("id");
                                    DBUtil.UpdateWater(W_id);
                                    Message msg = new Message();
                                    msg.arg1 = position;
                                    msg.what = 1010;
                                    mHandler.sendMessage(msg);
                                }
                            });
                            thread.start();
                        }
                    });
                    message.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });
                    message.create().show();
                }
                else {
                    Toast.makeText(DormInfoActivity.this, "没有确认权限", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onClick(ISlide iSlideView, View view, final int position) {
                //listview的滑动删除事件--控件的所有子控件的点击回调都会回调此方法
                if (view.getId() == R.id.btn_del) { //对删除按钮的监听（上面adapter的getBindOnClickViewsIds()中设置了R.id.btn_del）
                    iSlideView.close(); //关闭当前的按钮
                    if (isStudent){
                        AlertDialog.Builder message = new AlertDialog.Builder(DormInfoActivity.this);
                        message.setTitle("删除订单");
                        message.setMessage("是否删除？");
                        message.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // 在数据库中进行delete数据
                                final Thread thread = new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        String W_id = waterDatas.get(position).get("id");
                                        DBUtil.DeleteWater(W_id);
                                        Message msg = new Message();
                                        msg.arg1 = position;
                                        msg.what = 1008;
                                        mHandler.sendMessage(msg);
                                    }
                                });
                                thread.start();
                            }
                        });
                        message.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogInterface, int i) {
                            }
                        });
                        message.create().show();
                    }
                    else {
                        Toast.makeText(DormInfoActivity.this, "没有删除权限", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
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
                    case 1004:
                        stuList.clear();
                        String ret = msg.obj.toString();
                        String[] v = ret.split(",");
                        building_name.setText(v[0]);
                        dormitory_name.setText(v[1]);
                        if(v[2] != null){
                            Map<String, Object> temp = new HashMap<>();
                            temp.put("stu_name", v[2]);
                            temp.put("stu_no", v[3]);
                            temp.put("stu_school", v[4]);
                            stuList.add(temp);
                        }
                        if(v[5] != null){
                            Map<String, Object> temp = new HashMap<>();
                            temp.put("stu_name", v[5]);
                            temp.put("stu_no", v[6]);
                            temp.put("stu_school", v[7]);
                            stuList.add(temp);
                        }
                        if(v[8] != null){
                            Map<String, Object> temp = new HashMap<>();
                            temp.put("stu_name", v[8]);
                            temp.put("stu_no", v[9]);
                            temp.put("stu_school", v[10]);
                            stuList.add(temp);
                        }
                        if(v[11] != null){
                            Map<String, Object> temp = new HashMap<>();
                            temp.put("stu_name", v[11]);
                            temp.put("stu_no", v[12]);
                            temp.put("stu_school", v[13]);
                            stuList.add(temp);
                        }
                        sAdapter.notifyDataSetChanged();
                        break;
                    case 1005:
                        repairDatas.clear();
                        String ret1 = msg.obj.toString();
                        String[] lines = ret1.split("\n");
                        if(!lines[0].split(",")[0].equals("")){
                            for(int i=0; i<lines.length;i++){
                                System.out.println("查询结果"+i+"："+"\n"+lines[i]);
                                String[] v1 = lines[i].split(",");
                                Map<String, String> temp = new HashMap<>();
                                temp.put("id", v1[0]);
                                temp.put("hint1", "报修日期");
                                temp.put("hint2", "备注");
                                temp.put("o_date", v1[1]);
                                temp.put("o_detail", v1[2]);
                                temp.put("check_done", v1[3]);
                                if(v1[3].equals("0")) {
                                    repairDatas.add(temp);
                                }
                            }
                        }
                        rAdapter.notifyDataSetChanged();
                        break;
                    case 1006:
                        waterDatas.clear();
                        String ret2 = msg.obj.toString();
                        String[] lines2 = ret2.split("\n");
                        if(!lines2[0].split(",")[0].equals("")) {
                            for (int i = 0; i < lines2.length; i++) {
                                String[] v2 = lines2[i].split(",");
                                Map<String, String> temp = new HashMap<>();
                                temp.put("id", v2[0]);
                                temp.put("hint1", "订水日期");
                                temp.put("hint2", "数量");
                                temp.put("o_date", v2[1].split(" ")[0]);
                                temp.put("o_detail", v2[2]);
                                temp.put("check_done", v2[3]);
                                if (v2[3].equals("0")) {
                                    waterDatas.add(temp);
                                }
                            }
                        }
                        wAdapter.notifyDataSetChanged();
                        break;
                    case 1007:
                        int position = msg.arg1;
                        // 删除listview中的对应数据
                        repairDatas.remove(position);
                        rAdapter.notifyDataSetChanged();
                        break;
                    case 1008:
                        int position2 = msg.arg1;
                        // 删除listview中的对应数据
                        waterDatas.remove(position2);
                        wAdapter.notifyDataSetChanged();
                        break;
                    case 1009:
                        int position3 = msg.arg1;
                        // 勾选listview中的确认框
                        repairDatas.get(position3).put("check_done", "1");
                        rAdapter.notifyDataSetChanged();
                        break;
                    case 1010:
                        int position4 = msg.arg1;
                        // 勾选listview中的确认框
                        waterDatas.get(position4).put("check_done", "1");
                        wAdapter.notifyDataSetChanged();
                        break;
                    default:
                        break;
                }
            }
        }
    }
}