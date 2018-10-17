package com.example.myapplication.menudetailpager.tabdetailpager;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.base.MenuDetaiBasePager;
import com.example.myapplication.domain.NewscenterPagerBean2;
import com.example.myapplication.domain.TabDetailPagerBean;
import com.example.myapplication.utils.CacheUtils;
import com.example.myapplication.utils.Constants;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.common.util.DensityUtil;
import org.xutils.common.util.LogUtil;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

/**
 * Created by 陈宣嘉 on 2018/10/15.
 */

public class TabDetailPager extends MenuDetaiBasePager {
    private ViewPager viewPager;
    private TextView tv_title;
    private LinearLayout ll_pont_group;
    private ListView listview;


    private final NewscenterPagerBean2.DetailPagerData.ChildrenData childrenData;
    private String url;
    private List<TabDetailPagerBean.DataEntity.TopnewsData> topnews;
    private List<TabDetailPagerBean.DataEntity.NewsData> news;
    private TabDetailPagerListAdapter adapter;
//    private TextView textView;

    public TabDetailPager(Context context, NewscenterPagerBean2.DetailPagerData.ChildrenData childrenData) {
        super(context);
        this.childrenData=childrenData;
    }

    @Override
    public View initView() {
        View view=View.inflate(context, R.layout.tabdetail_pager,null);
        viewPager= (ViewPager) view.findViewById(R.id.viewpager);
        tv_title= (TextView) view.findViewById(R.id.tv_title);
        ll_pont_group= (LinearLayout) view.findViewById(R.id.ll_point_group);
        listview= (ListView) view.findViewById(R.id.listview);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        url= Constants.BASE_URL+childrenData.getUrl();
        String saveJson=CacheUtils.getString(context,url);
        LogUtil.e("缓存的数据"+saveJson);
        if (!TextUtils.isEmpty(saveJson))
        {
            processData(saveJson);
        }
        getDataFromNet();
    }
    /**
     * 之前点高亮显示的位置
     */
    private int prePosition;
    private void processData(String json) {
        TabDetailPagerBean bean=parsedJson(json);
        LogUtil.e(childrenData.getTitle()+"解析成功"+ bean.getData().getNews().get(0).getTitle());
        //顶部轮播图数据
        topnews= bean.getData().getTopnews();
        //设置适配器
        viewPager.setAdapter(new TabDetailPagerTopNewsAdapter());
        addPoint();//添加红点
        //监听页面的改变，设置红点变化和文本变化
        viewPager.addOnPageChangeListener(new MyOnPageChangeListener());
        tv_title.setText(topnews.get(prePosition).getTitle());

        //1.设置listview对应的数据
        news=bean.getData().getNews();
        //2.设置listview的适配器
        adapter=new TabDetailPagerListAdapter();
        listview.setAdapter(new TabDetailPagerListAdapter());

    }
    class TabDetailPagerListAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return news.size();
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
            ViewHolder viewHolder;
            if (convertView==null){
                convertView=View.inflate(context,R.layout.item_tabdetail_pager,null);
                viewHolder=new ViewHolder();
                viewHolder.iv_icon= (ImageView) convertView.findViewById(R.id.iv_icon);
                viewHolder.tv_title= (TextView) convertView.findViewById(R.id.tv_title);
                viewHolder.tv_time= (TextView) convertView.findViewById(R.id.tv_time);
                convertView.setTag(viewHolder);

            }else {
                viewHolder=(ViewHolder)convertView.getTag();
            }
            TabDetailPagerBean.DataEntity.NewsData newsData=news.get(position);
            String imageUrl=Constants.BASE_URL+ newsData.getListimage();
           //请求图片
            x.image().bind(viewHolder.iv_icon,imageUrl);
            //设置标题
            viewHolder.tv_title.setText(newsData.getTitle());
            //设置时间
            viewHolder.tv_time.setText(newsData.getPubdate());
            return convertView;
        }
    }
    static class ViewHolder{
        ImageView iv_icon;
        TextView tv_title;
        TextView tv_time;
    }

    private void addPoint() {
        ll_pont_group.removeAllViews();//移除所有的红点
        for (int i=0;i<topnews.size();i++)
        {
            ImageView imageView=new ImageView(context);
            imageView.setBackgroundResource(R.drawable.point_selector);
            LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(DensityUtil.dip2px(5),DensityUtil.dip2px(5));
            if (i==0){
                imageView.setEnabled(true);
            }else {
                imageView.setEnabled(false);
                params.leftMargin=DensityUtil.dip2px(8);
            }

            imageView.setLayoutParams(params);
            ll_pont_group.addView(imageView);
        }
    }

    class MyOnPageChangeListener implements ViewPager.OnPageChangeListener{

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            //设置文本
            tv_title.setText(topnews.get(position).getTitle());
            //2.对应页面的点高亮
            //把之前的变为灰色
            ll_pont_group.getChildAt(prePosition).setEnabled(false);
            //把当前的设为红色
            ll_pont_group.getChildAt(position).setEnabled(true);
            prePosition=position;
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }




    class TabDetailPagerTopNewsAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return topnews.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView =new ImageView(context);
            //设置图片默认北京
            imageView.setBackgroundResource(R.drawable.home_scroll_default);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            container.addView(imageView);
            TabDetailPagerBean.DataEntity.TopnewsData topnewsData= topnews.get(position);
            String imageUrl=Constants.BASE_URL+topnewsData.getTopimage();
            //联网请求数据
            x.image().bind(imageView,imageUrl);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }


    private TabDetailPagerBean parsedJson(String json) {
        return new Gson().fromJson(json,TabDetailPagerBean.class);

    }

    private void getDataFromNet() {
        RequestParams params=new RequestParams(url);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                CacheUtils.putString(context,url,result);
                LogUtil.e(childrenData.getTitle()+"--页面数据请求成功"+result);
                processData(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                LogUtil.e(childrenData.getTitle()+"页面数据请求失败"+ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {
                LogUtil.e(childrenData.getTitle()+"页面数据请求onCancelled"+cex.getMessage());
            }

            @Override
            public void onFinished() {
                LogUtil.e(childrenData.getTitle()+"页面数据请求onFinished");
            }
        });
    }
}
