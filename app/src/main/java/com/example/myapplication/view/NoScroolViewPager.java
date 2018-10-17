package com.example.myapplication.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by 陈宣嘉 on 2018/10/11.
 * 自定义不可以滑动的view pager
 */

public class NoScroolViewPager extends ViewPager {

    /**
     * 通常在代码中实例化的时候用该方法
     * @param context
     */
    public NoScroolViewPager(Context context) {
        super(context);
    }

    /**
     * 在布局文件中使用该类的时候，实例化改类用该构造方法，这个方法不能少，少的话会崩溃
     * @param context
     * @param attrs
     */
    public NoScroolViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /*
    重写触摸事件，消费掉
     */
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return true;
    }
}
