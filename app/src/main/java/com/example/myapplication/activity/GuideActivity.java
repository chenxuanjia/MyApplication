package com.example.myapplication.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.support.v4.content.PermissionChecker;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.myapplication.R;
import com.example.myapplication.SplashActivity;
import com.example.myapplication.utils.CacheUtils;
import com.example.myapplication.utils.DensityUtil;

import java.util.ArrayList;

public class GuideActivity extends Activity {
    private ViewPager viewPager;
    private Button btn_start_main;
    private LinearLayout ll_point_group;
    private ArrayList<ImageView> imageviews;
    private ImageView iv_red_point;
    private int leftmax;
    private int widthdpi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        viewPager=(ViewPager) findViewById(R.id.viewpager);
        btn_start_main=(Button) findViewById(R.id.btn_start_main);
        ll_point_group=(LinearLayout) findViewById(R.id.ll_point_group);
        iv_red_point=(ImageView) findViewById(R.id.iv_red_point);

        //准备数据
        int[] ids=new int[]{
                R.drawable.guide_1,
                R.drawable.guide_2,
                R.drawable.guide_3
        };
        widthdpi= DensityUtil.dip2px(this,10);

        imageviews=new ArrayList<>();
        for (int i=0;i<ids.length;i++){
            ImageView imageView =new ImageView(this);
            imageView.setBackgroundResource(ids[i]);

            //添加到集合中
            imageviews.add(imageView);
            //创建点
            ImageView point =new ImageView(this);
            point.setBackgroundResource(R.drawable.point_normal);
            /**
             * 单位是像素
             * 把单位当成dp转成对应的像素
             */
            LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(widthdpi,widthdpi);//单位像素
            if (i!=0){
                params.leftMargin=widthdpi;
            }
            point.setLayoutParams(params);
            //添加到线性布局
            ll_point_group.addView(point);

        }
        //设置viewpage适配器
        viewPager.setAdapter(new MyPageAdapter());

        //根据view的生命周期，当试图执行到onlayout或者onDraw的时候，边距都有了
        iv_red_point.getViewTreeObserver().addOnGlobalLayoutListener(new MyOnGlobalLayoutListener());
        //得到屏幕百分比
        viewPager.addOnPageChangeListener(new MyonPageChangeListener());
        btn_start_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //保持曾经进入过页面
                CacheUtils.PutBoolean(GuideActivity.this, SplashActivity.START_MAIN,true);
                Intent intent=new Intent(GuideActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
    class MyonPageChangeListener implements ViewPager.OnPageChangeListener{
        /**
         * 页面滚动回调此方法
         * @param position 当前位置
         * @param positionOffset 滑动百分比
         * @param positionOffsetPixels
         */
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//            int leftmargin=(int) positionOffset*leftmax;
            int leftmargin=(int) (position*leftmax+(positionOffset*leftmax));
            RelativeLayout.LayoutParams params=(RelativeLayout.LayoutParams)iv_red_point.getLayoutParams();
            params.leftMargin=leftmargin;
            iv_red_point.setLayoutParams(params);
        }

        /**
         * 当页面被选中的时候回调此方法
         * @param position 被选中页面的位置
         */
        @Override
        public void onPageSelected(int position) {
            if (position==imageviews.size()-1){
                btn_start_main.setVisibility(View.VISIBLE);
            }else {
                btn_start_main.setVisibility(View.GONE);
            }
        }

        /**
         * 当view pager页面滑动状态发生变化的时候
         * @param state
         */
        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
    class MyOnGlobalLayoutListener implements ViewTreeObserver.OnGlobalLayoutListener{
        @Override
        public void onGlobalLayout() {
            //执行不止一次
            iv_red_point.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            leftmax=ll_point_group.getChildAt(1).getLeft()-ll_point_group.getChildAt(0).getLeft();

        }
    }

    class MyPageAdapter extends PagerAdapter{
        /**
         * 返回数据的总个数
         * @return
         */
        @Override
        public int getCount() {
            return imageviews.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        /**
         *
         * @param container viewpager
         * @param position 要创建页面的位置
         * @return 返回和创建当前页面关系的值
         */
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView =imageviews.get(position);
            //添加的容器中
            container.addView(imageView);
            return imageView;
//            return super.instantiateItem(container, position);
        }

        /**
         * 销毁页面
         * @param container viewpager
         * @param position 要销毁的个数
         * @param object
         */
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
//            super.destroyItem(container, position, object);
            container.removeView((View) object);
        }
    }
}
