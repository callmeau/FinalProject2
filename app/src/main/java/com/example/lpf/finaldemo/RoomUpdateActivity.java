package com.example.lpf.finaldemo;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hamsa.twosteppickerdialog.OnStepDataRequestedListener;
import com.hamsa.twosteppickerdialog.OnStepPickListener;
import com.hamsa.twosteppickerdialog.TwoStepPickerDialog;

import net.lemonsoft.lemonbubble.LemonBubble;
import net.lemonsoft.lemonbubble.enums.LemonBubbleLayoutStyle;
import net.lemonsoft.lemonbubble.enums.LemonBubbleLocationStyle;
import net.lemonsoft.lemonhello.LemonHello;
import net.lemonsoft.lemonhello.LemonHelloAction;
import net.lemonsoft.lemonhello.LemonHelloInfo;
import net.lemonsoft.lemonhello.LemonHelloView;
import net.lemonsoft.lemonhello.interfaces.LemonHelloActionDelegate;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by fangqizhan on 2018/1/6.
 */

public class RoomUpdateActivity extends AppCompatActivity{
    private CardView c1,c2;
    private static EditText room1;
    private static EditText name1;
    private static EditText room2;
    private static EditText name2;
    //private String stu11,stu12,stu13,stu14,stu21,stu22,stu23,stu24;//名字
    private String sc11,sc12,sc13,sc14,sc21,sc22,sc23,sc24;//学生账号
    private int dorm1,dorm2;
    private TwoStepPickerDialog pickerDialog;
    private int chosen;
    private List<Map<String, Object>> dormList = new ArrayList<>();
    private List<String> roomselect = new ArrayList<>();
    private List<String> memberselect = new ArrayList<>();
    private List<String> sc = new ArrayList<>();//学生账号
    private Button confirm;
    private final MyHandler myHandler = new MyHandler(this);
    private DBUtil dbUtil;
    //private boolean flag = false;
    private String a_account;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_update_dorm_layout);
        init();

    }


    private void init(){
        a_account = this.getIntent().getStringExtra("a_account");
        dbUtil = new DBUtil();
//        dormList = new ArrayList<>();
//        roomselect = new ArrayList<>();
//        memberselect = new ArrayList<>();
//        sc = new ArrayList<>();
        System.out.println("inti==============================================");
        dataInit();
        room1 = (EditText)findViewById(R.id.roomnum11);
        name1 = (EditText)findViewById(R.id.name11);
        room2 = (EditText)findViewById(R.id.roomnum22);
        name2 = (EditText)findViewById(R.id.name22);
        confirm = (Button)findViewById(R.id.button12);
        pickerDialog = new TwoStepPickerDialog
                .Builder(this)
                .withBaseData(roomselect)
                .withOkButton("确认")
                .withCancelButton("取消")
                .withBaseOnLeft(true)
                .withInitialBaseSelected(0)
                .withInitialStepSelected(0)
                .withOnStepDataRequested(new OnStepDataRequestedListener() {
                    @Override
                    public List<String> onStepDataRequest(int i) {
                        return getMemberselect(roomselect.get(i).toString());
                    }
                })
                .withDialogListener(new OnStepPickListener() {
                    @Override
                    public void onStepPicked(int i, int i1) {
                        if(chosen==1){
                            System.out.println("room1set");
                            if(roomselect.get(i).equals(room2.getText().toString())) {
                                room1.setText("");
                                name1.setText("");
                                Toast.makeText(RoomUpdateActivity.this,"请选择不同宿舍",Toast.LENGTH_SHORT).show();
                            }else {
                                room1.setText(roomselect.get(i));
                                Map<String, Object> temp = dormList.get(i);
                                String dorm_id1 = temp.get("dorm_id").toString();
                                dorm1 = Integer.parseInt(dorm_id1);
                                name1.setText(memberselect.get(i1));
                                //stu11 = memberselect.get(i1).toString();
                                sc11 = sc.get(i1).toString();
                                if (i1 == 0) {
//                                stu12 = memberselect.get(1).toString();
//                                stu13 = memberselect.get(2).toString();
//                                stu14 = memberselect.get(3).toString();
                                    sc12 = sc.get(1).toString();
                                    sc13 = sc.get(2).toString();
                                    sc14 = sc.get(3).toString();
                                } else if (i1 == 1) {
//                                stu12 = memberselect.get(0).toString();
//                                stu13 = memberselect.get(2).toString();
//                                stu14 = memberselect.get(3).toString();
                                    sc12 = sc.get(0).toString();
                                    sc13 = sc.get(2).toString();
                                    sc14 = sc.get(3).toString();
                                } else if (i1 == 2) {
//                                stu12 = memberselect.get(0).toString();
//                                stu13 = memberselect.get(1).toString();
//                                stu14 = memberselect.get(3).toString();
                                    sc12 = sc.get(0).toString();
                                    sc13 = sc.get(1).toString();
                                    sc14 = sc.get(3).toString();
                                } else if (i1 == 3) {
//                                stu12 = memberselect.get(0).toString();
//                                stu13 = memberselect.get(1).toString();
//                                stu14 = memberselect.get(2).toString();
                                    sc12 = sc.get(0).toString();
                                    sc13 = sc.get(1).toString();
                                    sc14 = sc.get(2).toString();
                                }
                            }
                        } else if(chosen==2) {
                            if(roomselect.get(i).equals(room1.getText().toString())){
                                room2.setText("");
                                name2.setText("");
                                Toast.makeText(RoomUpdateActivity.this,"请选择不同宿舍",Toast.LENGTH_SHORT).show();
                            }else {
                                room2.setText(roomselect.get(i));
                                Map<String, Object> temp = dormList.get(i);
                                String dorm_id2 = temp.get("dorm_id").toString();
                                dorm2 = Integer.parseInt(dorm_id2);
                                name2.setText(memberselect.get(i1));
                                // stu21 = memberselect.get(i1).toString();
                                sc21 = sc.get(i1).toString();
                                if (i1 == 0) {
                                    //stu22 = memberselect.get(1).toString();
                                    //stu23 = memberselect.get(2).toString();
                                    //stu24 = memberselect.get(3).toString();
                                    sc22 = sc.get(1).toString();
                                    sc23 = sc.get(2).toString();
                                    sc24 = sc.get(3).toString();
                                } else if (i1 == 1) {
                                    //stu22 = memberselect.get(0).toString();
                                    //stu23 = memberselect.get(2).toString();
                                    //stu24 = memberselect.get(3).toString();
                                    sc22 = sc.get(0).toString();
                                    sc23 = sc.get(2).toString();
                                    sc24 = sc.get(3).toString();
                                } else if (i1 == 2) {
                                    // stu22 = memberselect.get(0).toString();
                                    //  stu23 = memberselect.get(1).toString();
                                    //  stu24 = memberselect.get(3).toString();
                                    sc22 = sc.get(0).toString();
                                    sc23 = sc.get(1).toString();
                                    sc24 = sc.get(3).toString();
                                } else if (i1 == 3) {
                                    // stu22 = memberselect.get(0).toString();
                                    // stu23 = memberselect.get(1).toString();
                                    //  stu24 = memberselect.get(2).toString();
                                    sc22 = sc.get(0).toString();
                                    sc23 = sc.get(1).toString();
                                    sc24 = sc.get(2).toString();
                                }
                            }
                        }
                    }

                    @Override
                    public void onDismissed() {

                    }
                })
                .build();

        c1 = (CardView)findViewById(R.id.card);
        c2 = (CardView)findViewById(R.id.include12);
        c1.setOnClickListener(getClickEvent());
        c2.setOnClickListener(getClickEvent());
        confirm.setOnClickListener(getClickEvent());
        room1.setOnClickListener(getClickEvent());
        name1.setOnClickListener(getClickEvent());
        room2.setOnClickListener(getClickEvent());
        name2.setOnClickListener(getClickEvent());
    }
    private View.OnClickListener getClickEvent(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // dataInit();
                if (v == c1 || v == c2) {
                    InputMethodManager imm = (InputMethodManager)
                            getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                } else if (v == confirm) {
                    if(room1.getText().toString().isEmpty() || room2.getText().toString().isEmpty()){
                        LemonHello.getErrorHello("错误","输入不能为空")
                                .addAction(new LemonHelloAction("关闭", new LemonHelloActionDelegate() {
                                    @Override
                                    public void onClick(LemonHelloView lemonHelloView, LemonHelloInfo lemonHelloInfo, LemonHelloAction lemonHelloAction) {
                                        lemonHelloView.hide();
                                    }
                                }))
                                .show(RoomUpdateActivity.this);
                    }
                    else onConfirm();
                } else if (v == room1 || v == name1) {
                    System.out.println("room1click");
                   // System.out.println("测试"+"：\n" + roomselect.get(0).toString());
                    if(roomselect.size()>0) pickerDialog.show();
                    chosen = 1;
                } else if (v == room2 || v == name2) {
                    if(roomselect.size()>0) pickerDialog.show();
                    System.out.println("room2click");
                    chosen = 2;
                }
            }
        };
    }

    private void dataInit(){
        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String ret = DBUtil.QueryAllDorm(a_account);
                Message msg = new Message();
                msg.what = 1001;
                msg.obj = ret;
                myHandler.sendMessage(msg);
            }
        });
        thread.start();
    }


    private class  MyHandler extends Handler {
        private final WeakReference<RoomUpdateActivity> mActivity;
        public MyHandler (RoomUpdateActivity activity) {
            mActivity = new WeakReference<>(activity);
        }
        @Override
        public void handleMessage(Message msg) {
            RoomUpdateActivity activity = mActivity.get();
            if(activity != null) {
                switch (msg.what) {
                    case 1001:
                        dormList.clear();
                        roomselect.clear();

                        String ret = msg.obj.toString();
                        String[] lines = ret.split("\n");
                        for(int i=0;i<lines.length;i++){
                            String[] v = lines[i].split(",");
                            Map<String,Object> temp = new HashMap<>();
                            temp.put("dorm_id", v[0]);
                            temp.put("building_id", v[1]);
                            temp.put("dorm_no", v[2]);
                            temp.put("stu1_name", v[3]);
                            temp.put("stu2_name", v[4]);
                            temp.put("stu3_name", v[5]);
                            temp.put("stu4_name", v[6]);
                            temp.put("stu1", v[7]);
                            temp.put("stu2", v[8]);
                            temp.put("stu3", v[9]);
                            temp.put("stu4", v[10]);

                            dormList.add(temp);
                            System.out.println("列表"+i+"：\n" + dormList.get(i).toString());
                            String d_no = temp.get("dorm_no").toString();
                            roomselect.add(d_no);
                            System.out.println("addbase");
                        }
                        break;
                    case 1002:
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                LemonBubble.showRight(RoomUpdateActivity.this,"调整成功！",2000);
                            }
                        },1500);
                        dataInit();
                        break;
                    default:break;
                }
            }
        }
    }


    private List<String> getMemberselect(String str) {
        memberselect.clear();
        sc.clear();
        System.out.println("addmemselection");
        for(int i=0;i<roomselect.size();i++){
            if(str == roomselect.get(i).toString()){
                Map<String, Object> temp = dormList.get(i);
                String s1 = temp.get("stu1_name").toString();
                String s2 = temp.get("stu2_name").toString();
                String s3 = temp.get("stu3_name").toString();
                String s4 = temp.get("stu4_name").toString();
                String sc1 = temp.get("stu1").toString();
                String sc2 = temp.get("stu2").toString();
                String sc3 = temp.get("stu3").toString();
                String sc4 = temp.get("stu4").toString();
                memberselect.add(s1);
                memberselect.add(s2);
                memberselect.add(s3);
                memberselect.add(s4);
                sc.add(sc1);
                sc.add(sc2);
                sc.add(sc3);
                sc.add(sc4);

                System.out.println("addselection2");
            }
        }
        return memberselect;
    }

    private void uploadInfo(){
        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                dbUtil.UpdateExchange(sc11,sc12,sc13,sc14,sc21,sc22,sc23,sc24,dorm1,dorm2);
                Message msg = new Message();
                msg.what = 1002;
                myHandler.sendMessage(msg);
            }
        });
        thread.start();
    }

    private void onConfirm(){
        LemonHello.getInformationHello("确定提交？","您是否确定做此调整？")
                .addAction(new LemonHelloAction("取消", new LemonHelloActionDelegate() {
                    @Override
                    public void onClick(LemonHelloView lemonHelloView, LemonHelloInfo lemonHelloInfo, LemonHelloAction lemonHelloAction) {
                        lemonHelloView.hide();
                    }
                }))
                .addAction(new LemonHelloAction("提交", Color.RED, new LemonHelloActionDelegate() {
                    @Override
                    public void onClick(LemonHelloView lemonHelloView, LemonHelloInfo lemonHelloInfo, LemonHelloAction lemonHelloAction) {
                        lemonHelloView.hide();
                        LemonBubble.getRoundProgressBubbleInfo()
                                .setLocationStyle(LemonBubbleLocationStyle.BOTTOM)
                                .setLayoutStyle(LemonBubbleLayoutStyle.ICON_LEFT_TITLE_RIGHT)
                                .setBubbleSize(200,50)
                                .setProportionOfDeviation(0.1f)
                                .setTitle("正在调整...")
                                .show(RoomUpdateActivity.this);
                        uploadInfo();
                    }
                }))
                .show(RoomUpdateActivity.this);

    }

}
