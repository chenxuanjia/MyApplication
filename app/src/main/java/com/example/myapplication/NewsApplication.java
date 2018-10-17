package com.example.myapplication;

import android.app.Application;

import org.xutils.x;

/**
 * Created by 陈宣嘉 on 2018/10/11.
 */

public class NewsApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.setDebug(true);
        x.Ext.init(this);
    }
}
