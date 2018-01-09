package com.example.lpf.finaldemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by lpf on 2018/1/7.
 */

public class ItemAdapter extends BaseAdapter {

    private List<item> list;
    private Context context;

    public ItemAdapter(List<item> list, Context context)
    {
        this.list=list;
        this.context=context;
    }

    @Override
    public int getCount()
    {
        return list==null?0:list.size();
    }

    @Override
    public Object getItem(int i)
    {
        return list==null?null:list.get(i);
    }

    @Override
    public long getItemId(int i)
    {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup)
    {
        //加载布局
        if(view==null)
        {
            view = LayoutInflater.from(context).inflate(R.layout.item_layout,null);
        }

        //获得布局中显示icon、name、price的TextView
        TextView dormName_text = (TextView) view.findViewById(R.id.dorm_name);
        TextView detail_text=(TextView) view.findViewById(R.id.details);
        TextView date_text=(TextView) view.findViewById(R.id.date);


        //从list中取出Item对象，将icon和name和price赋值给这三个TexiView的文本内容
        dormName_text.setText(list.get(i).getDorm_name());
        detail_text.setText(list.get(i).getDetails());
        date_text.setText(list.get(i).getDate());

        return view;
    }

}
