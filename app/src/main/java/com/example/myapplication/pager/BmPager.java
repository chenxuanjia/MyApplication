package com.example.myapplication.pager;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.TextView;

import com.example.myapplication.base.BasePager;

/**
 * Created by 陈宣嘉 on 2018/10/11.
 */

public class BmPager extends BasePager {
    public BmPager(Context context) {
        super(context);
    }

    @Override
    public void initData() {
        super.initData();
        //设置标题
        tv_title.setText("便民页面");
        //2.联网请求，得到数据，创建视图
        TextView textView=new TextView(context);

        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.RED);
        textView.setTextSize(25);
        fl_content.addView(textView);
        //绑定数据
        textView.setText("便民页面内容");

        //3.绑定数据
    }
}
