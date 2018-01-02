package com.example.lpf.finaldemo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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

import com.hamsa.twosteppickerdialog.OnStepDataRequestedListener;
import com.hamsa.twosteppickerdialog.OnStepPickListener;
import com.hamsa.twosteppickerdialog.TwoStepPickerDialog;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by victory on 2018/1/1.
 */

public class edit_informationActivity extends AppCompatActivity{

    private EditText textName;
    private CardView c1;
    private CardView c2;
    private Button confirm;
    private EditText school;
    private EditText major;
    private TwoStepPickerDialog picker1;
    private List<String> data;
    private List<String> selection ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_information_layout);
        Init();
    }
    private void Init(){
        textName = (EditText)findViewById(R.id.textView5);
        confirm = (Button)findViewById(R.id.button);
        school = (EditText)findViewById(R.id.school2);
        major = (EditText)findViewById(R.id.major2);
        textName.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case KeyEvent.ACTION_UP:
                    {
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
        data.add("Test1");
        data.add("Test2");
        data.add("Test3");
        picker1 = new TwoStepPickerDialog
                .Builder(this)
                .withBaseData(data)
                .withOkButton("确认")
                .withCancelButton("取消")
                .withBaseOnLeft(true)
                .withInitialBaseSelected(2)
                .withInitialStepSelected(1)
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
        c1=(CardView)findViewById(R.id.cardView);
        c2=(CardView)findViewById(R.id.include);
        c1.setOnClickListener(getClickEvent());
        c2.setOnClickListener(getClickEvent());
        confirm.setOnClickListener(getClickEvent());
        school.setOnClickListener(getClickEvent());
        major.setOnClickListener(getClickEvent());


    }
    private View.OnClickListener getClickEvent(){
        return new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (v==c1||v==c2){
                    InputMethodManager imm = (InputMethodManager)
                            getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
                if (v==confirm){

                }
                if (v==school||v==major){
                    picker1.show();
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
    private List<String> getselection(String str){
           selection.clear();
        if (str==data.get(0)){
            selection.add("test1");
            selection.add("test2");
            selection.add("test3");
        }
        else if (str==data.get(1)){
            selection.add("test4");
            selection.add("test5");
            selection.add("test6");
        }
        else if (str==data.get(2)){
            selection.add("test7");
            selection.add("test8");
            selection.add("test9");
        }
        else{
            selection.add("test10");
            selection.add("test11");
            selection.add("test12");
        }
        return selection;
    }



}
