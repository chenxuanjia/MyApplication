package com.example.myapplication.fragment;

import android.graphics.Color;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.activity.MainActivity;
import com.example.myapplication.adapter.ContentFragmentAdapter;
import com.example.myapplication.base.BaseFragment;
import com.example.myapplication.base.BasePager;
import com.example.myapplication.pager.BmPager;
import com.example.myapplication.pager.HomePager;
import com.example.myapplication.pager.MePager;
import com.example.myapplication.pager.VedioPager;
import com.example.myapplication.view.NoScroolViewPager;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;

/**
 * Created by 陈宣嘉 on 2018/10/11.
 */

public class ContentFragment extends BaseFragment {
    @ViewInject(R.id.viewpager)
    private NoScroolViewPager  viewPager;
    @ViewInject(R.id.rg_main)
    private RadioGroup rg_main;

    private ArrayList<BasePager> basePagers;

    @Override
    public View initView() {
       View view=View.inflate(context, R.layout.content_fragment,null);
        x.view().inject(this,view);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        //初始化页面
        basePagers=new ArrayList<>();
        basePagers.add(new HomePager(context));//主页面
        basePagers.add(new VedioPager(context));//视频中心
        basePagers.add(new BmPager(context));//便民页面
        basePagers.add(new MePager(context));//个人中心

        //设置view pager的适配器
        viewPager.setAdapter(new ContentFragmentAdapter(basePagers));
        rg_main.setOnCheckedChangeListener(new MyOnCheckedChangeListener());
        //监听某个页面被选中，初始化对应的页面数据
        viewPager.addOnPageChangeListener(new MyOnPageChangeListener());
        //设置默认选中首页
        rg_main.check(R.id.rb_home);
        basePagers.get(0).initData();
    }

    //得到新闻数据数据
    public HomePager getNewsCenterPager() {
        return (HomePager)basePagers.get(0);
    }

    class MyOnPageChangeListener implements ViewPager.OnPageChangeListener{
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        /*
        当某个页面被选中回调此方法
         */
        @Override
        public void onPageSelected(int position) {
            basePagers.get(position).initData();//调用被选中页面的initdata()
            basePagers.get(0).initData();
        }
        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    class MyOnCheckedChangeListener implements RadioGroup.OnCheckedChangeListener{
        /**
         *
         * @param group RadioGroup
         * @param checkedId 被选中的RadioButton的id
         */
        @Override
        public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
            switch (checkedId){
                case R.id.rb_home ://主页
                    viewPager.setCurrentItem(0);
                    isEnableSlidingMenu(SlidingMenu.TOUCHMODE_FULLSCREEN);
                    break;
                case R.id.rb_video ://视频
                    viewPager.setCurrentItem(1);
                    isEnableSlidingMenu(SlidingMenu.TOUCHMODE_NONE);
                    break;
                case R.id.rb_bm ://便民
                    viewPager.setCurrentItem(2);
                    isEnableSlidingMenu(SlidingMenu.TOUCHMODE_NONE);
                    break;
                case R.id.rb_me ://个人中心
                    viewPager.setCurrentItem(3);
                    isEnableSlidingMenu(SlidingMenu.TOUCHMODE_NONE);
                    break;
            }
        }
    }

    /**
     * 根据传入的参数设置是否可以滑动
     * @param touchmodeFullscreen
     */
    private void isEnableSlidingMenu(int touchmodeFullscreen){
        MainActivity mainActivity=(MainActivity) context;
        mainActivity.getSlidingMenu().setTouchModeAbove(touchmodeFullscreen);
    }
}
