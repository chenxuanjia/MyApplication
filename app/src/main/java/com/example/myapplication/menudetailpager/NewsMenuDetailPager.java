package com.example.myapplication.menudetailpager;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.activity.MainActivity;
import com.example.myapplication.base.MenuDetaiBasePager;
import com.example.myapplication.domain.NewscenterPagerBean2;
import com.example.myapplication.menudetailpager.tabdetailpager.TabDetailPager;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.viewpagerindicator.TabPageIndicator;

import org.xutils.common.util.LogUtil;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 陈宣嘉 on 2018/10/13.
 * 新闻详情页面
 */

public class NewsMenuDetailPager extends MenuDetaiBasePager {
    @ViewInject(R.id.tabPageIndicator)
    private TabPageIndicator tabPageIndicator;

    @ViewInject(R.id.viewpager)
    private ViewPager viewPager;
    @ViewInject(R.id.ib_tab_next)
    private ImageButton ib_tab_next;

    private List<NewscenterPagerBean2.DetailPagerData.ChildrenData> children;//页签页面的数据
    private ArrayList<TabDetailPager> tabDetailPagers;//页签页面的集合
    public NewsMenuDetailPager(Context context, NewscenterPagerBean2.DetailPagerData detailPagerData) {
        super(context);
        children= detailPagerData.getChildren();
    }
    @Override
    public View initView() {
        View view=View.inflate(context, R.layout.newsmenu_detail_pager,null);
        x.view().inject(NewsMenuDetailPager.this,view);
        //设置下一个标题点击事件
        ib_tab_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
            }
        });

        return view;
    }

    @Override
    public void initData() {
        super.initData();
        LogUtil.e("新闻页面初始化");
        //准备新闻详情页面的数据
        tabDetailPagers=new ArrayList<>();
        for (int i=0;i<children.size();i++)
        {
            tabDetailPagers.add(new TabDetailPager(context,children.get(i)));
        }
        //设置适配器
        viewPager.setAdapter(new MyNewsMenuDetailPagerAdapter());
        tabPageIndicator.setViewPager(viewPager);
        //以后监听页面的变化
        tabPageIndicator.setOnPageChangeListener(new MyOnPageChangeListener());


    }
    class MyOnPageChangeListener implements ViewPager.OnPageChangeListener{

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            if (position==0){
                //可以向左滑动
                isEnableSlidingMenu(SlidingMenu.TOUCHMODE_FULLSCREEN);
            }else {
                isEnableSlidingMenu(SlidingMenu.TOUCHMODE_NONE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

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

    class MyNewsMenuDetailPagerAdapter extends PagerAdapter{

        @Override
        public CharSequence getPageTitle(int position) {
            return children.get(position).getTitle();
        }

        @Override
        public int getCount() {
            return tabDetailPagers.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            TabDetailPager tabDetailPager= tabDetailPagers.get(position);
            View rootview=tabDetailPager.rootview;
            tabDetailPager.initData();//初始化数据
            container.addView(rootview);
            return rootview;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View)object);
        }
    }
}
