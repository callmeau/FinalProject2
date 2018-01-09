package com.example.lpf.finaldemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

public class Log_in_activity extends Activity {

    private int role=2;
    private String account = "...";
    private String password = "...";
    private Handler handler = new Handler()
    {
        public void handleMessage(Message msg)
        {
            switch(msg.what)
            {
                case 1://密码匹配
                    if(role==0)//学生
                    {
                        Intent intent = new Intent(Log_in_activity.this,student_main_page.class);
                        intent.putExtra("account",account);
                        startActivity(intent);
                    }
                    else if(role==1)//管理员，进入管理员主页
                    {
                        Intent intent = new Intent(Log_in_activity.this,admin_main_page.class);
                        intent.putExtra("account",account);
                        startActivity(intent);
                    }
                    break;
                case 2://密码不匹配
                    Toast.makeText(Log_in_activity.this,"账号与密码不匹配",Toast.LENGTH_LONG).show();
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_in_layout);
        final TextInputLayout Account = (TextInputLayout)findViewById(R.id.login);
        final TextInputLayout Password = (TextInputLayout)findViewById(R.id.password);
        final EditText account_text =Account.getEditText();
        final EditText password_text=Password.getEditText();
        final RadioGroup RG=(RadioGroup)findViewById(R.id.radioGroup);
        final Button login = (Button)findViewById(R.id.btn_Login);

        //radioButton的值确定学生还是管理员
        RG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup,  int i) {

                if(i==R.id.radioButton_student)//学生
                {
                    role=0;
                    Toast.makeText(Log_in_activity.this,"你选择了学生",Toast.LENGTH_LONG).show();
                }
                else if( i==R.id.radioButton_administor )//管理员
                {
                    role=1;
                    Toast.makeText(Log_in_activity.this,"你选择了管理员",Toast.LENGTH_LONG).show();
                }
            }
        });

        //登录按钮监听
        login.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View view) {
                account=account_text.getText().toString();
                password=password_text.getText().toString();
                if (TextUtils.isEmpty(account))//账号为空时
                {
                    Account.setErrorEnabled(true);
                    Account.setError("账号不能为空");
                }
                else if (TextUtils.isEmpty(password))
                {
                    Account.setErrorEnabled(false);
                    Password.setErrorEnabled(true);
                    Password.setError("密码不能为空");
                }
                else
                {
                    Account.setErrorEnabled(false);
                    Password.setErrorEnabled(false);/*一定要设置，不然会导致之前提示过的报错信息不会消失*/
                    final Thread thread = new Thread(new Runnable()
                    {
                        @Override
                        public void run() {
                            String truepassword = DBUtil.QuerryUserPassword(account,role);
                            System.out.println("truepassword:"+truepassword);
                            System.out.println("mypassword:"+password);
                            Message msg = new Message();
                            if(truepassword.equals(password))//匹配
                                msg.what=1;
                            else//不匹配
                                msg.what=2;
                            handler.sendMessage(msg);
                        }
                    });
                    thread.start();
                }
            }
        });

    }
}
