package com.example.myapplication.activity;

import android.app.Activity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.fragment.ContentFragment;
import com.example.myapplication.fragment.LeftmenuFragment;
import com.example.myapplication.utils.DensityUtil;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

import org.xutils.common.util.LogUtil;

public class MainActivity extends SlidingFragmentActivity {

    public static final String MAIN_CONTENT_TAG = "main_content_tag";
    public static final String LEFTMENU_TAG = "leftmenu_tag";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initslidingMenu();
        //初始化Fragment
        initFragment();

    }
    private void initslidingMenu(){
        //1.设置主页面
        setContentView(R.layout.activity_main);
        //2.设置左侧菜单
        setBehindContentView(R.layout.activity_leftmenu);

        //3设置右侧菜单
        SlidingMenu slidingMenu=getSlidingMenu();
        slidingMenu.setSecondaryMenu(R.layout.activity_rightmenu);
        //4.设置显示的模式，左侧菜单+主页，左侧菜单+主页面+右侧菜单，主页+右侧菜单
        slidingMenu.setMode(SlidingMenu.LEFT);
        //5.设置滑动模式，滑动边缘，全屏滑动
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        //6.设置主页占据宽度
        slidingMenu.setBehindOffset(DensityUtil.dip2px(MainActivity.this,150));

    }


    private void initFragment() {
        //1.得到FragmentManger
        FragmentManager fm=getSupportFragmentManager();
        //2.开启事务
        FragmentTransaction ft=fm.beginTransaction();
        //3.替换
        ft.replace(R.id.fl_main_content,new ContentFragment(), MAIN_CONTENT_TAG);
        ft.replace(R.id.fl_leftmenu,new LeftmenuFragment(), LEFTMENU_TAG);
        //4.提交
        ft.commit();
    }

    /**
     * 得到左侧菜单fragment
     * @return
     */
    public LeftmenuFragment getLeftmenuFragment() {
//        FragmentManager fm= getSupportFragmentManager();
//        LeftmenuFragment leftmenuFragment=(LeftmenuFragment) fm.findFragmentByTag(LEFTMENU_TAG);
        return (LeftmenuFragment) getSupportFragmentManager().findFragmentByTag(LEFTMENU_TAG);
    }

    //得到正文fragment
    public ContentFragment getContentFragmen() {
        return (ContentFragment) getSupportFragmentManager().findFragmentByTag(MAIN_CONTENT_TAG);
    }
}
