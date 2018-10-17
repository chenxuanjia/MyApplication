package com.example.myapplication.base;

import android.content.Context;
import android.view.View;

/**
 * Created by 陈宣嘉 on 2018/10/13.
 * 详情页面的基类
 */

public abstract class MenuDetaiBasePager {
    public final Context context;
    public View rootview;//各个页面的视图

    public MenuDetaiBasePager(Context context){
        this.context=context;
        rootview=initView();
    }

    /**
     * 抽象方法，强制孩子实现该方法，每个页面实现不同的效果
     * @return
     */
    public abstract View initView();

    /**
     * 子页面绑定数据，联网请求数据的时候，重写该方法
     */
    public void initData(){

    }
}
