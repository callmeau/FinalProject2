package com.example.lpf.finaldemo;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.widget.AdapterView.OnItemClickListener;

public class DormListActivity extends AppCompatActivity {

    private SearchView mSearchView;
    private static ListView listView;
    private static SimpleAdapter mAdapter;
    private static List<Map<String, Object>> datas;  //与宿舍列表适配器绑定的数据list
    private static List<Map<String, Object>> dormList;  //整栋宿舍楼的宿舍list
    private String a_account;
    private final MyHandler mHandler = new MyHandler(this);
    private String[] mStrs = {"dorm_no","stu1","stu2","stu3","stu4"};
    private int[] ids = {R.id.dorm_no, R.id.stu1, R.id.stu2, R.id.stu3, R.id.stu4};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dorm_list_layout);

        init();  // 初始化控件
        dataInit();
        setOnSearchResult();
    }

    private void init(){
        a_account = this.getIntent().getStringExtra("a_account");

        mSearchView = (SearchView) findViewById(R.id.search_view);
        mSearchView.setIconified(false);
        listView = (ListView) findViewById(R.id.dorm_list);
        datas = new ArrayList<>();
        dormList = new ArrayList<>();
        mAdapter = new SimpleAdapter(this, datas, R.layout.item_dorm_list, mStrs, ids);
        listView.setAdapter(mAdapter);
        listView.setOnItemClickListener(new MyListener());
        listView.setTextFilterEnabled(false);
    }

    private void dataInit(){
        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String ret= DBUtil.QueryAllDorm(a_account);
                Message msg = new Message();
                msg.what = 1002;
                msg.obj = ret;
                mHandler.sendMessage(msg);
            }
        });
        thread.start();
    }

    private static class MyHandler extends Handler {
        private final WeakReference<DormListActivity> mActivity;

        public MyHandler(DormListActivity activity) {
            mActivity = new WeakReference<>(activity);
        }
        @Override
        public void handleMessage(Message msg) {
            DormListActivity activity = mActivity.get();
            if (activity != null) {
                switch (msg.what) {
                    case 1002:
                        datas.clear();
                        dormList.clear();
                        String ret = msg.obj.toString();
                        String[] lines = ret.split("\n");
                        for(int i=0; i<lines.length;i++){
                            String[] v = lines[i].split(",");
                            Map<String, Object> temp = new HashMap<>();
                            temp.put("dorm_id", v[0]);
                            temp.put("building_id", v[1]);
                            temp.put("dorm_no", v[2]);
                            temp.put("stu1", v[3]);
                            temp.put("stu2", v[4]);
                            temp.put("stu3", v[5]);
                            temp.put("stu4", v[6]);
                            datas.add(temp);
                            dormList.add(temp);
                            System.out.println("查询结果"+i+"：\n" + datas.get(i).toString());
                        }
                        mAdapter.notifyDataSetChanged();
                        break;
                    default:
                        break;
                }
            }
        }
    }


    private void setOnSearchResult() {
        // 设置搜索文本监听
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            // 当搜索内容改变时触发该方法
            @Override
            public boolean onQueryTextChange(String newText) {
                if (!TextUtils.isEmpty(newText)){
                    listView.setFilterText(newText);
                    datas.clear();
                    for(int i=0; i<dormList.size();i++){
                        Map<String, Object> temp = dormList.get(i);
                        String d_no = temp.get("dorm_no").toString();
                        String s1 = temp.get("stu1").toString();
                        String s2 = temp.get("stu2").toString();
                        String s3 = temp.get("stu3").toString();
                        String s4 = temp.get("stu4").toString();
                        if(d_no.contains(newText)||s1.contains(newText)||s2.contains(newText)||s3.contains(newText)||s4.contains(newText)) {
                            datas.add(dormList.get(i));
                        }
                    }
                    mAdapter.notifyDataSetChanged();
                }else{
                    listView.clearTextFilter();
                    cloneDormList();
                    mAdapter.notifyDataSetChanged();
                }
                return false;
            }

            // 当点击搜索按钮时触发该方法
            @Override
            public boolean onQueryTextSubmit(String query) {
                // TODO Auto-generated method stub
                //在该方法内执行实际查询
                datas.clear();
                for(int i=0; i<dormList.size();i++){
                    Map<String, Object> temp = dormList.get(i);
                    if(query.equals(temp.get("dorm_no")) || query.equals(temp.get("stu1")) || query.equals(temp.get("stu2"))
                            || query.equals(temp.get("stu3")) || query.equals(temp.get("stu4"))) {
                        datas.add(dormList.get(i));
                    }
                }
                mAdapter.notifyDataSetChanged();
                return true;
            }

        });



    }

    public void cloneDormList(){
        datas.clear();
        for(int i=0; i<dormList.size();i++) {
            Map<String, Object> temp = new HashMap<>();
            temp.put("dorm_no", dormList.get(i).get("dorm_no"));
            temp.put("stu1", dormList.get(i).get("stu1"));
            temp.put("stu2", dormList.get(i).get("stu2"));
            temp.put("stu3", dormList.get(i).get("stu3"));
            temp.put("stu4", dormList.get(i).get("stu4"));
            datas.add(temp);
        }
    }

    class MyListener implements OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
            Map<String, Object> temp = datas.get(position);
            String dorm_id = temp.get("dorm_id").toString();

            Intent intent = new Intent(DormListActivity.this, DormInfoActivity.class);
            intent.putExtra("dorm_id", dorm_id);
            intent.putExtra("role", "admin");
            startActivity(intent);
        }

    }

}
