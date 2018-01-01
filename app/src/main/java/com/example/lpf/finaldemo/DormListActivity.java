package com.example.lpf.finaldemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DormListActivity extends AppCompatActivity {

    private SearchView mSearchView;
    private ListView listView;
    private SimpleAdapter mAdapter;
    private ArrayList<Map<String, Object>> datas;
    private int building_id;
    private String[] mStrs = {"dorm_name","stu1","stu2","stu3","stu4"};
    private int[] ids = {R.id.dorm_name, R.id.stu1, R.id.stu2, R.id.stu3, R.id.stu4};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dorm_list_layout);

        init();  // 初始化控件
        dataInit();
        setOnSearchResult();
    }

    private void init(){
        building_id = this.getIntent().getIntExtra("b_id", 1);
        mSearchView = (SearchView) findViewById(R.id.search_view);
        listView = (ListView) findViewById(R.id.dorm_list);
        datas = new ArrayList<>();
        mAdapter = new SimpleAdapter(this, datas, R.layout.item_dorm_list,mStrs, ids);
        listView.setAdapter(mAdapter);
        listView.setTextFilterEnabled(false);
    }

    private void dataInit(){
        Map<String, Object> temp = new HashMap<>();
        temp.put("dorm_name", "716");
        temp.put("stu1", "张仁杰");
        temp.put("stu2", "张仁杰");
        temp.put("stu3", "张仁杰");
        temp.put("stu4", "张仁杰");
        datas.add(temp);
        mAdapter.notifyDataSetChanged();
    }

    private void setOnSearchResult() {
        // 设置搜索文本监听
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            // 当点击搜索按钮时触发该方法
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            // 当搜索内容改变时触发该方法
            @Override
            public boolean onQueryTextChange(String newText) {
                if (!TextUtils.isEmpty(newText)){
                    listView.setFilterText(newText);

                }else{
                    listView.clearTextFilter();
                }
                return false;
            }
        });

    }

}
