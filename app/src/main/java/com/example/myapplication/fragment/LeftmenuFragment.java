package com.example.myapplication.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.activity.MainActivity;
import com.example.myapplication.base.BaseFragment;
import com.example.myapplication.domain.NewscenterPagerBean2;
import com.example.myapplication.pager.HomePager;
import com.example.myapplication.utils.DensityUtil;

import org.xutils.common.util.LogUtil;

import java.util.List;

/**
 * Created by 陈宣嘉 on 2018/10/11.
 * 左侧菜单的fragment
 */

public class LeftmenuFragment extends BaseFragment {

    private List<NewscenterPagerBean2.DetailPagerData> data;
    private LeftmenuFagmentAdapter adapter;
    private ListView listView;
    private int prePosition;//点击位置
    @Override
    public View initView() {
        listView=new ListView(context);
        listView.setPadding(0, DensityUtil.dip2px(context,40),0,0);
        listView.setDividerHeight(0);//设置分割线高度0
        listView.setCacheColorHint(Color.TRANSPARENT);
        listView.setSelector(android.R.color.transparent);
        //设置左侧菜单条目点击事件
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //1.记录点击的位置，变成红色
                prePosition=position;
                adapter.notifyDataSetChanged();
                //2。把左侧菜单关闭
                MainActivity mainActivity=(MainActivity) context;
                mainActivity.getSlidingMenu().toggle();//关《-》开
                //3.切换到对应的详细页面，
                swichPager(prePosition);
            }
        });

        return listView;
    }

    private void swichPager(int position) {
        MainActivity mainActivity=(MainActivity) context;
        ContentFragment contentFragment=mainActivity.getContentFragmen();
        HomePager homePager=contentFragment.getNewsCenterPager();
        homePager.swichPager(position);
    }

    @Override
    public void initData() {
        super.initData();
    }

    /**
     * 接收数据
     * @param data
     */
    public void setData(List<NewscenterPagerBean2.DetailPagerData> data) {
        this.data=data;
        for (int i=0;i<data.size();i++){
            LogUtil.e("title"+data.get(i).getTitle());
        }
        //设置适配器
        adapter=new LeftmenuFagmentAdapter();
        listView.setAdapter(adapter);
        swichPager(prePosition);//设置默认页面
    }
    class LeftmenuFagmentAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return data.size();
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
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView textView=(TextView) View.inflate(context, R.layout.item_leftmenu,null);
            textView.setText(data.get(position).getTitle());
            textView.setEnabled(position==prePosition);

            return textView;
        }
    }
}
