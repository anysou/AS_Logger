package com.anysou.as_logger;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.anysou.aslogger.ASLogFileUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShowLogByListViewAcitvity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_log_by_list_view_acitvity);

        ListView listview = findViewById(R.id.showasloglistview);
        //listview.setAdapter(new ShowListAdapter()); //给listview设置适配器

        //下面有SimpleAdapter 适配器来显示;把map对象的数据对应绘制到界面当中
        List<Map<String,Object>> data = new ArrayList<>();
        String[] allmsg = ASLogFileUtils.readLogList();
        for(int i=0;i<allmsg.length;i++){
            Map<String, Object> showitem = new HashMap<String, Object>();
            showitem.put("time", allmsg[i].split("]:")[0]+"]:");
            showitem.put("msg", allmsg[i].split("]:")[1]);
            data.add(showitem);
        }
        //创建一个simpleAdapter
        SimpleAdapter simpleAdapter = new SimpleAdapter(getApplicationContext(), data, R.layout.listview_item, new String[]{"time", "msg"}, new int[]{R.id.textViewTime, R.id.textViewMsg});
        listview.setAdapter(simpleAdapter);
    }

    //复杂（图片文字、动态更新）的用 BaseAdapter + 打气筒
    public class ShowListAdapter extends BaseAdapter{

        @Override //总共显示多少行项目元素
        public int getCount() {
            return ASLogFileUtils.readLogList().length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        //每次元素出现在屏幕上都会调用这里获取。 参数：i序号，从0开始；
        //convertView 是刚刚隐藏的元素，此处可以重复利用,提高资源重用
        //用打气筒 inflate 引入 R.layout.listview_item 布局文件 转换成 View对象
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = null;
            if(convertView!=null){
                view = convertView;
            } else{
                //打气筒第一种方案
                view = View.inflate(ShowLogByListViewAcitvity.this,R.layout.listview_item,null);
                /*
                //打气筒第二种方案
                view = LayoutInflater.from(ShowLogByListViewAcitvity.this).inflate(R.layout.listview_item,null);
                //打气筒第三种方案
                LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.listview_item,null);
                */
            }
            return view;
        }
    }



}
