package com.example.lpf.finaldemo;

import android.content.Context;
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

import com.airsaid.pickerviewlibrary.OptionsPickerView;

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

/**
 * Created by victory on 2018/1/4.
 */

public class repair_edit extends AppCompatActivity {
    private String account;
    private int id;
    private Button confirm;
    private EditText title;
    private EditText content;
    private int dormId;
    private CardView c1;
    private CardView c2;
    DBUtil dbUtil;
    private final repair_edit.MyHandler mHandler = new repair_edit.MyHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.repair_eidt_layout);
        account=getIntent().getStringExtra("stu_account");//学生账号
        id=getIntent().getIntExtra("dorm_id",0);//宿舍id
        title = (EditText)findViewById(R.id.titlecontent);
        content =(EditText)findViewById(R.id.detailcontent);
        confirm = (Button)findViewById(R.id.buttonconfirm) ;
        c1 = (CardView) findViewById(R.id.repaircardview1);
        c2 = (CardView) findViewById(R.id.repaircardview2);
        c1.setOnClickListener(getClickEvent());
        c2.setOnClickListener(getClickEvent());
        title.setCursorVisible(false);
        content.setCursorVisible(false);
        title.setOnClickListener(getClickEvent());
        content.setOnClickListener(getClickEvent());
        confirm.setOnClickListener(getClickEvent());
    }



    private View.OnClickListener getClickEvent() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v==confirm) {
                    if (title.getText().toString().isEmpty()){
                        LemonHello.getErrorHello("错误", "请输入报修项目。")
                                .addAction(new LemonHelloAction("关闭", new LemonHelloActionDelegate() {
                                    @Override
                                    public void onClick(LemonHelloView helloView, LemonHelloInfo helloInfo, LemonHelloAction helloAction) {
                                        helloView.hide();
                                    }
                                }))
                                .show(repair_edit.this);
                    }
                    else onConfirm();
                } else if (v == title) {
                    title.setCursorVisible(true);
                }
                else if(v==content){
                    content.setCursorVisible(true);
                }
                else if (v == c1 || v == c2) {
                    InputMethodManager imm = (InputMethodManager)
                            getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

                }
            }
        };
    }
    private void onConfirm(){//点击提交后
        LemonHello.getInformationHello("订单确认","您是否确认提交订单？")
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
                                .setLocationStyle(LemonBubbleLocationStyle.CENTER)
                                .setLayoutStyle(LemonBubbleLayoutStyle.ICON_LEFT_TITLE_RIGHT)
                                .setBubbleSize(200, 50)
                                .setProportionOfDeviation(0.1f)
                                .setTitle("正在提交订单...")
                                .show(repair_edit.this);
                        uploadInfo();

                    }
                }))
                .show(repair_edit.this);;
    }

    private void uploadInfo(){//上传文件
        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
//                String ret= DBUtil.QueryUsers("select top 3 * from Users");

//                System.out.println(stuInfo.get(0));
                dbUtil.repair(title.getText().toString(),content.getText().toString(),id);
                Message msg = new Message();
                msg.what = 1002;
                mHandler.sendMessage(msg);
            }
        });
        thread.start();

    }

    private  class MyHandler extends Handler {
        private final WeakReference<repair_edit> mActivity;

        public MyHandler(repair_edit activity) {
            mActivity = new WeakReference<repair_edit>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            repair_edit activity = mActivity.get();
            if (activity != null) {
                switch (msg.what) {
                    case 1001:
                        break;
                    case 1002:
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                LemonBubble.showRight(repair_edit.this, "提交成功！", 2000);
                            }
                        }, 1500);
                        //LemonBubble.showRight(edit_informationActivity.this, "保存成功！", 2000);
                        break;
                    default:
                        break;
                }
            }
        }
    }
}
