package com.example.myapplication.base;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.activity.MainActivity;
import com.example.myapplication.utils.CacheUtils;

/**
 * Created by 陈宣嘉 on 2018/10/11.
 */

public class BasePager {
    public final Context context;
    public View rootView;
    public TextView tv_title;
    public ImageButton ib_menu;
    public FrameLayout fl_content;


    public BasePager(Context context){
        this.context=context;
        rootView=initVew();
    }

    private View initVew() {
        //基类页面
        View view=View.inflate(context, R.layout.base_pager,null);
        tv_title= (TextView) view.findViewById(R.id.tv_title);
        ib_menu= (ImageButton) view.findViewById(R.id.ib_menu);
        fl_content= (FrameLayout) view.findViewById(R.id.fl_content);

        ib_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity mainActivity=(MainActivity) context;
                mainActivity.getSlidingMenu().toggle();//关《-》开
            }
        });
        return view;
    }

    /**
     * 初始化数据；当孩子需要初始化数据，或者绑定数据；联网请求数据并且绑定的时候，重写该方法
     */
    public void initData(){

    }

}
