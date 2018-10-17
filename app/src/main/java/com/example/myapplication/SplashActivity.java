package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.myapplication.activity.GuideActivity;
import com.example.myapplication.activity.MainActivity;
import com.example.myapplication.utils.CacheUtils;

public class SplashActivity extends Activity {

    public static final String START_MAIN = "start_main";//静态常量
    private RelativeLayout rl_splashs_root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        rl_splashs_root = (RelativeLayout) findViewById(R.id.rl_splahs_root);

        //渐变动画
        AlphaAnimation aa = new AlphaAnimation(0, 1);
//        aa.setDuration(500);//持续播放时间
        aa.setFillAfter(true);
        ScaleAnimation sa = new ScaleAnimation(0, 1, 0, 1, ScaleAnimation.RELATIVE_TO_SELF, 0.5f, ScaleAnimation.RELATIVE_TO_SELF, 0.5f);
//        sa.setDuration(500);
        sa.setFillAfter(true);
        RotateAnimation ra = new RotateAnimation(0, 360, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
//        ra.setDuration(500);
        ra.setFillAfter(true);

        AnimationSet set = new AnimationSet(false);
        //添加三个动画没有先后顺序。
        set.addAnimation(ra);
        set.addAnimation(aa);
        set.addAnimation(sa);
        set.setDuration(2000);
        rl_splashs_root.startAnimation(set);
        set.setAnimationListener(new MyAnimationListener());


    }
    class MyAnimationListener implements Animation.AnimationListener {
        /**
         * 当动画播放的时候回调此方法
         *
         * @param animation
         */
        @Override
        public void onAnimationStart(Animation animation) {

        }

        /**
         * 动画结束
         *
         * @param animation
         */
        @Override
        public void onAnimationEnd(Animation animation) {
            //判断是否进入过主页面
            boolean isStartMain = CacheUtils.getBoolean(SplashActivity.this, START_MAIN);
            Intent intent;
            if (isStartMain){
                intent=new Intent(SplashActivity.this,MainActivity.class);
            }else {
                intent = new Intent(SplashActivity.this, GuideActivity.class);
            }
            startActivity(intent);
            finish();
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }
}

