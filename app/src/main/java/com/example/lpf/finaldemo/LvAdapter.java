package com.example.lpf.finaldemo;

/**
 * Created by JingrongFeng on 2018/1/2.
 */


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.solo.library.SlideBaseAdapter;
import com.solo.library.SlideTouchView;

import java.util.List;
import java.util.Map;


public class LvAdapter extends SlideBaseAdapter {
    List<Map<String, String>> list;

    public LvAdapter(List<Map<String, String>> list) {
        this.list = list;
    }

    @Override
    public int[] getBindOnClickViewsIds() {
        return new int[]{R.id.btn_del};  //必须调用, 删除按钮或者其他你想监听点击事件的View的id
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder holder = new MyViewHolder();
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_repair_list, null);
            holder.id = (TextView) convertView.findViewById(R.id.order_id);
            holder.hint1 = (TextView) convertView.findViewById(R.id.hint1);
            holder.hint2 = (TextView) convertView.findViewById(R.id.hint2);
            holder.date = (TextView) convertView.findViewById(R.id.order_date);
            holder.detail = (TextView) convertView.findViewById(R.id.order_detail);
            holder.check_done = (CheckBox) convertView.findViewById(R.id.order_check);
            holder.mSlideTouchView = (SlideTouchView) convertView.findViewById(R.id.mSlideTouchView);
            convertView.setTag(holder);
            bindSlideState(holder.mSlideTouchView); //必须调用
        } else {
            holder = (MyViewHolder) convertView.getTag();
        }

        bindSlidePosition(holder.mSlideTouchView, position);//必须调用

        holder.id.setText(String.valueOf(list.get(position).get("id")));
        holder.hint1.setText(String.valueOf(list.get(position).get("hint1")));
        holder.hint2.setText(String.valueOf(list.get(position).get("hint2")));
        holder.date.setText(String.valueOf(list.get(position).get("o_date")));
        holder.detail.setText(String.valueOf(list.get(position).get("o_detail")));
        if(!String.valueOf(list.get(position).get("check_done")).equals("0")){
            holder.check_done.setChecked(true);
        }else{
            holder.check_done.setChecked(false);
        }
        return convertView;
    }

    class MyViewHolder {
        TextView id;
        TextView hint1;
        TextView hint2;
        TextView date;
        TextView detail;
        CheckBox check_done;
        SlideTouchView mSlideTouchView;
    }
}

