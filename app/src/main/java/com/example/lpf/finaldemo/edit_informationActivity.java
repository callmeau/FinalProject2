package com.example.lpf.finaldemo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.airsaid.pickerviewlibrary.OptionsPickerView;
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

import org.w3c.dom.Text;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;



/**
 * Created by victory on 2018/1/1.
 */

public class edit_informationActivity extends AppCompatActivity {

    private static EditText textName;
    private static TextView dormname;
    private static TextView dor_no;
    private CardView c1;
    private CardView c2;
    private Button confirm;
    private static EditText school;
    private static EditText major;
    private static EditText classnum;
    private TwoStepPickerDialog picker1;
    private List<String> data;
    private List<String> selection;
    private OptionsPickerView<String> picker2;
    private OptionsPickerView<String> picker3;
    private OptionsPickerView<String> picker4;
    private OptionsPickerView<String> picker5;
    private OptionsPickerView<String> picker6;
    private ArrayList<String> selection2;
    private ArrayList<String> Hobby;
    private ArrayList<String> time1;
    private ArrayList<String> time2;
    private ArrayList<String> op;
    private  ArrayList<String> stuInfo;
    private int chosen;
    private static EditText hobby1;
    private static EditText hobby2;
    private static EditText hobby3;
    private static EditText waketime;
    private static EditText sleeptime;
    private static EditText plan;
    private String account;
    private DBUtil dbUtil;
    private final edit_informationActivity.MyHandler mHandler = new edit_informationActivity.MyHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_information_layout);
        account=getIntent().getStringExtra("stu_account");
        Init();
        loadInfo();
    }

    private void Init() {
         dbUtil = new DBUtil();
         dor_no  = (TextView)findViewById(R.id.textView9);
         dormname = (TextView)findViewById(R.id.textView8);
        textName = (EditText) findViewById(R.id.textView5);
        confirm = (Button) findViewById(R.id.button);
        school = (EditText) findViewById(R.id.school2);
        classnum = (EditText) findViewById(R.id.cls2);
        major = (EditText) findViewById(R.id.major2);
        hobby1 = (EditText) findViewById(R.id.hobby12);
        hobby2 = (EditText) findViewById(R.id.hobby22);
        hobby3 = (EditText) findViewById(R.id.hobby32);
        waketime = (EditText) findViewById(R.id.wake_time2);
        sleeptime = (EditText) findViewById(R.id.sleep_time2);
        plan = (EditText)findViewById(R.id.future2) ;
        textName.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case KeyEvent.ACTION_UP: {
                        textName.setFocusable(true);
                        textName.setFocusableInTouchMode(true);
                        textName.requestFocus();
                        edit_informationActivity.this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                        textName.setSelection(textName.length());
                    }

                }
                return false;
            }

        });
        data = new ArrayList<>();
        selection = new ArrayList<>();
        selection2 = new ArrayList<>();
        time1 = new ArrayList<>();
        time2 = new ArrayList<>();
        stuInfo= new ArrayList<>();
        Hobby = new ArrayList<>();
        op = new ArrayList<>();
        Addselection2();//添加数据
        picker2 = new OptionsPickerView<>(this);
        picker2.setPicker(selection2);
        picker2.setOnOptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int option1, int option2, int option3) {
                classnum.setText(selection2.get(option1));
            }
        });

        picker3 = new OptionsPickerView<>(this);
        picker3.setPicker(Hobby);
        picker3.setOnOptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int option1, int option2, int option3) {
                if (chosen == 1) {
                    hobby1.setText(Hobby.get(option1));
                } else if (chosen == 2) {
                    hobby2.setText(Hobby.get(option1));
                } else {
                    hobby3.setText(Hobby.get(option1));
                }
            }
        });

        picker4 = new OptionsPickerView<>(this);
        picker4.setPicker(time1);
        picker4.setOnOptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int option1, int option2, int option3) {
                sleeptime.setText(time1.get(option1));
            }
        });

        picker5 = new OptionsPickerView<>(this);
        picker5.setPicker(time2);
        picker5.setOnOptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int option1, int option2, int option3) {
                waketime.setText(time2.get(option1));
            }
        });

        picker6 = new OptionsPickerView<>(this);
        picker6.setPicker(op);
        picker6.setOnOptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int option1, int option2, int option3) {
                plan.setText(op.get(option1));
            }
        });


        picker1 = new TwoStepPickerDialog
                .Builder(this)
                .withBaseData(data)
                .withOkButton("确认")
                .withCancelButton("取消")
                .withBaseOnLeft(true)
                .withInitialBaseSelected(0)
                .withInitialStepSelected(0)
                .withOnStepDataRequested(new OnStepDataRequestedListener() {
                    @Override
                    public List<String> onStepDataRequest(int i) {
                        return getselection(data.get(i));
                    }
                })
                .withDialogListener(new OnStepPickListener() {
                    @Override
                    public void onStepPicked(int i, int i1) {
                        school.setText(data.get(i));
                        major.setText(selection.get(i1));
                    }

                    @Override
                    public void onDismissed() {

                    }
                })
                .build();
        c1 = (CardView) findViewById(R.id.cardView);
        c2 = (CardView) findViewById(R.id.include);
        c1.setOnClickListener(getClickEvent());
        c2.setOnClickListener(getClickEvent());
        confirm.setOnClickListener(getClickEvent());
        school.setOnClickListener(getClickEvent());
        major.setOnClickListener(getClickEvent());
        classnum.setOnClickListener(getClickEvent());
        hobby1.setOnClickListener(getClickEvent());
        hobby2.setOnClickListener(getClickEvent());
        hobby3.setOnClickListener(getClickEvent());
        waketime.setOnClickListener(getClickEvent());
        sleeptime.setOnClickListener(getClickEvent());
        plan.setOnClickListener(getClickEvent());

    }

    private View.OnClickListener getClickEvent() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == c1 || v == c2) {
                    InputMethodManager imm = (InputMethodManager)
                            getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                } else if (v == confirm) {
                    onConfirm();
                } else if (v == school || v == major) {
                    picker1.show();
                } else if (v == classnum) {
                    picker2.show();
                } else if (v == hobby1) {
                    picker3.show();
                    chosen = 1;
                } else if (v == hobby2) {
                    picker3.show();
                    chosen = 2;
                } else if (v == hobby3) {
                    picker3.show();
                    chosen = 3;
                }

            }


        };
    }

    //private void onEditclick(){
    //textName.setFocusable(true);
    //textName.setFocusableInTouchMode(true);
    //textName.requestFocus();
    //this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    //textName.setSelection(textName.length());

    //}
    private List<String> getselection(String str) {
        selection.clear();
        if (str == data.get(0)) {
            selection.add("数学系");
            selection.add("统计科学系");
            selection.add("科学计算与计算机应用系");
        } else if (str == data.get(1)) {
            selection.add("移动信息工程");
            selection.add("软件工程");
            selection.add("计算机科学");
            selection.add("保密学");

        } else if (str == data.get(2)) {
            selection.add("英语专业");
            selection.add("阿拉伯语专业");
            selection.add("朝鲜（韩国）语专业");
            selection.add("俄语专业");
            selection.add("西班牙语专业");

        } else {
            selection.add("英语专业");
            selection.add("法语专业");
            selection.add("德语专业");
            selection.add("日语专业");

        }
        return selection;
    }

    private void Addselection2() {
        for (int i = 1; i < 20; i++) {
            selection2.add(Integer.toString(i) + "班");
        }
        data.add("数学学院");
        data.add("数据科学与计算机学院");
        data.add("国际翻译学院");
        data.add("外语学院");

        Hobby.add("体育");
        Hobby.add("文学");
        Hobby.add("音乐");
        Hobby.add("旅行");
        time1.add("21:00~22:00");
        time1.add("22:00~23:00");
        time1.add("23:00~00:00");
        time1.add("00:00以后");
        time2.add("7:00~8:00");
        time2.add("8:00~9:00");
        time2.add("9:00~10:00");
        time2.add("10:00以后");
        op.add("国内读研");
        op.add("出国留学");
        op.add("毕业工作");
    }
    private void loadInfo()//加载已经存在的数据
    {
        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
//                String ret= DBUtil.QueryUsers("select top 3 * from Users");
                stuInfo = dbUtil.QueryStu(account);
                System.out.println(account);
//                System.out.println(stuInfo.get(0));
                Message msg = new Message();
                msg.what = 1001;
                msg.obj = stuInfo;
                mHandler.sendMessage(msg);
            }
        });
        thread.start();
    }
    private void uploadInfo(){//上传文件
        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
//                String ret= DBUtil.QueryUsers("select top 3 * from Users");

//                System.out.println(stuInfo.get(0));
               dbUtil.Uploaddata(school.getText().toString(),major.getText().toString(),classnum.getText().toString(),
                       sleeptime.getText().toString(),waketime.getText().toString(),
                       hobby1.getText().toString(),hobby2.getText().toString(),hobby3.getText().toString(),
                       plan.getText().toString(),account);
                Message msg = new Message();
                msg.what = 1002;
                mHandler.sendMessage(msg);
            }
        });
        thread.start();

    }


    private void onConfirm(){//点击提交后
        LemonHello.getInformationHello("确认提交？","您是否确认信息输入无误？")
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
                                .setBubbleSize(200, 50)
                                .setProportionOfDeviation(0.1f)
                                .setTitle("正在保存...")
                                .show(edit_informationActivity.this);
                                uploadInfo();

                    }
                }))
                .show(edit_informationActivity.this);;
    }


    private  class MyHandler extends Handler {
        private final WeakReference<edit_informationActivity> mActivity;

        public MyHandler(edit_informationActivity activity) {
            mActivity = new WeakReference<edit_informationActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            edit_informationActivity activity = mActivity.get();
            if (activity != null) {
                switch (msg.what) {
                    case 1001:
                        ArrayList<String>temp = (ArrayList<String>) msg.obj;
                        //String str = msg.obj.toString();
                        textName.setText(temp.get(0));
                        school.setText(temp.get(3));
                        major.setText(temp.get(4));
                        classnum.setText(temp.get(5));
                        sleeptime.setText(temp.get(6));
                        waketime.setText(temp.get(7));
                        hobby1.setText(temp.get(8));
                        hobby2.setText(temp.get(9));
                        hobby3.setText(temp.get(10));
                        plan.setText(temp.get(11));
                        dormname.setText(temp.get(12));
                        dor_no.setText(temp.get(13));
                        break;
                    case 1002:
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                LemonBubble.showRight(edit_informationActivity.this, "保存成功！", 2000);
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





