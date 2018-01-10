package com.example.lpf.finaldemo;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
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

public class Order_editActivity extends AppCompatActivity {
    private String account;
    private String id;
    private Button confirm;
    private EditText num;
    private OptionsPickerView<String> picker;
    private ArrayList<String> content;
    DBUtil dbUtil;
    private final Order_editActivity.MyHandler mHandler = new Order_editActivity.MyHandler(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_edit);
        account=getIntent().getStringExtra("stu_account");//学生账号
        id=getIntent().getStringExtra("dorm_id");//宿舍id

        dbUtil = new DBUtil();
        confirm = (Button)findViewById(R.id.confirm);
        num = (EditText)findViewById(R.id.numcontent);
        picker =  new OptionsPickerView<>(this);
        content = new ArrayList<>();
        setoption();

        picker.setPicker(content);
        picker.setOnOptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int option1, int option2, int option3) {
                num.setText(content.get(option1));
            }
        });
        num.setOnClickListener(getClickEvent());
        confirm.setOnClickListener(getClickEvent());
    }

    private void setoption(){
        for (int i=1;i<6;i++){
            content.add(Integer.toString(i));
        }
    }

    private View.OnClickListener getClickEvent() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v==confirm) {
                    if (num.getText().toString().isEmpty()){
                        LemonHello.getErrorHello("错误", "请输入预定数目。")
                                .addAction(new LemonHelloAction("关闭", new LemonHelloActionDelegate() {
                                    @Override
                                    public void onClick(LemonHelloView helloView, LemonHelloInfo helloInfo, LemonHelloAction helloAction) {
                                        helloView.hide();
                                    }
                                }))
                                .show(Order_editActivity.this);
                    }
                    else onConfirm();
                } else if (v == num) {
                   picker.show();
                }
            }
            };
    }


    private void uploadInfo(){//上传文件
        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
//                String ret= DBUtil.QueryUsers("select top 3 * from Users");

//                System.out.println(stuInfo.get(0));
                dbUtil.Order(num.getText().toString(),id);
                Message msg = new Message();
                msg.what = 1002;
                mHandler.sendMessage(msg);
            }
        });
        thread.start();

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
                                .show(Order_editActivity.this);
                        uploadInfo();

                    }
                }))
                .show(Order_editActivity.this);;
    }

    private  class MyHandler extends Handler {
        private final WeakReference<Order_editActivity> mActivity;

        public MyHandler(Order_editActivity activity) {
            mActivity = new WeakReference<Order_editActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            Order_editActivity activity = mActivity.get();
            if (activity != null) {
                switch (msg.what) {
                    case 1001:
                        break;
                    case 1002:
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                LemonBubble.showRight(Order_editActivity.this, "提交成功！", 2000);
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
