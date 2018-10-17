package com.example.myapplication.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.base.BasePager;

import java.util.ArrayList;

/**
 * Created by 陈宣嘉 on 2018/10/12.
 */

public class ContentFragmentAdapter extends PagerAdapter {
  private final ArrayList<BasePager> basePagers;
    public  ContentFragmentAdapter(ArrayList<BasePager> basePagers){
      this.basePagers=basePagers;
  }

    @Override
    public int getCount() {
        return basePagers.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        BasePager basePager= basePagers.get(position);//各个页面的实例
        View rootview=basePager.rootView;//各个子页面
        //调用各个页面的initData()
//            basePager.initData();//初始化数据
        container.addView(rootview);
        return rootview;
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
