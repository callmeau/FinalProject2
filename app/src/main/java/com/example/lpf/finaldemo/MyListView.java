package com.example.lpf.finaldemo;

import android.content.Context;
import android.view.MotionEvent;
import android.widget.ListView;

/**
 * Created by JingrongFeng on 2018/1/7.
 */

public class MyListView extends ListView {
    public MyListView(Context context) {
        super(context);
    }

    public MyListView(android.content.Context context,android.util.AttributeSet attrs){
        super(context, attrs);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        // TODO Auto-generated method stub
        getParent().requestDisallowInterceptTouchEvent(true);//这句话的作用 告诉父view，我的单击事件我自行处理，不要阻碍我。
        return super.dispatchTouchEvent(ev);
    }
}
