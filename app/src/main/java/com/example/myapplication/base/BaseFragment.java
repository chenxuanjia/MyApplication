package com.example.myapplication.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by 陈宣嘉 on 2018/10/11.
 * 基本的fragment leftfragment和contentfragment继承此类
 */

public abstract class BaseFragment extends Fragment {
    public Activity context;

    /**
     * 当fragment被创建的时候回调此方法
     * @param savedInstanceState
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=getActivity();
    }

    /**
     * 当视图被创建的时候回调
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return initView();
    }

    //让孩子实现自己的视图，达到自己特有的效果
    public abstract View initView();

    /**
     * 当activity被创建之后被回调
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    /**
     *1.如果自己页面没有数据，联网请求数据，并且绑定到initview初始化的视图上
     * 2.绑定到initview初始化的视图上
     */
    public void initData() {
    }
}
