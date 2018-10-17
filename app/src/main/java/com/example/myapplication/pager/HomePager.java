package com.example.myapplication.pager;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.example.myapplication.activity.MainActivity;
import com.example.myapplication.base.BasePager;
import com.example.myapplication.base.MenuDetaiBasePager;
import com.example.myapplication.domain.NewscenterPagerBean2;
import com.example.myapplication.fragment.LeftmenuFragment;
import com.example.myapplication.menudetailpager.InteracMenuDetailPager;
import com.example.myapplication.menudetailpager.NewsMenuDetailPager;
import com.example.myapplication.menudetailpager.PhotosMenuDetailPager;
import com.example.myapplication.menudetailpager.TopicMenuDetailPager;
import com.example.myapplication.utils.CacheUtils;
import com.example.myapplication.utils.Constants;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.common.util.LogUtil;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 陈宣嘉 on 2018/10/11.
 */

public class HomePager extends BasePager {
    //左侧菜单对应的集合
    private List<NewscenterPagerBean2.DetailPagerData> data;
    private ArrayList<MenuDetaiBasePager> detaiBasePagers;//详情页面集合

    public HomePager(Context context) {
        super(context);
    }

    @Override
    public void initData() {
        super.initData();
        ib_menu.setVisibility(View.VISIBLE);
        //设置标题
        tv_title.setText("主页面");
        //2.联网请求，得到数据，创建视图
        TextView textView=new TextView(context);
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.RED);
        textView.setTextSize(25);
        fl_content.addView(textView);
        //绑定数据
        textView.setText("主页面内容");
        //获取缓存数据
        String saveJson = CacheUtils.getString(context,Constants.NEWSCENTER_PAGER_URL);//""
//        if(!TextUtils.isEmpty(saveJson)){
//            processData(saveJson);
//        }
        //联网请求数据
        getDataFromNet();
    }

    private void getDataFromNet() {
        RequestParams params=new RequestParams(Constants.NEWSCENTER_PAGER_URL);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LogUtil.e("成功"+result);
                //缓存数据
                CacheUtils.putString(context,Constants.NEWSCENTER_PAGER_URL,result);
                processData(result);
            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                LogUtil.e("失败"+ex);
            }

            @Override
            public void onCancelled(CancelledException cex) {
                LogUtil.e("据东方时空"+cex);
            }

            @Override
            public void onFinished() {
                LogUtil.e("大师傅士大夫");
            }
        });
    }

    /**
     * 解析json数据和显示
     * @param json
     */
    private void processData(String json) {
//        NewscenterPagerBean bean=parsedJson(json);
        NewscenterPagerBean2 bean=parsedJson2(json);
        String title= bean.getData().get(0).getChildren().get(1).getTitle();
        LogUtil.e("解析的数据"+title);
        String title2= bean.getData().get(0).getChildren().get(1).getTitle();
        LogUtil.e("解析的数据nknknjnjkj"+title2);
        //给左侧数据传递数据
         data= bean.getData();
        MainActivity mainActivity=(MainActivity) context;
        LeftmenuFragment leftmenuFragment= mainActivity.getLeftmenuFragment();

        //添加详情页面
        detaiBasePagers=new ArrayList<>();
        detaiBasePagers.add(new NewsMenuDetailPager(context,data.get(0)));//新闻详情页面
        detaiBasePagers.add(new TopicMenuDetailPager(context));//专题详情页面
        detaiBasePagers.add(new PhotosMenuDetailPager(context));//图组详情页面
        detaiBasePagers.add(new InteracMenuDetailPager(context));//互动详情页面
        //把数据传递给左侧菜单
        leftmenuFragment.setData(data);
    }

    /**
     * 使用Android系统自带的API解析json数据
     * @param json
     * @return
     */
    private NewscenterPagerBean2 parsedJson2(String json) {
        NewscenterPagerBean2 bean2=new NewscenterPagerBean2();
        try {
            JSONObject object=new JSONObject(json);

            int retcode=object.optInt("retcode");
            bean2.setRetcode(retcode);//retcode字段解析成功
            JSONArray data= object.optJSONArray("data");
            if (data != null && data.length() > 0) {

                List<NewscenterPagerBean2.DetailPagerData> detailPagerDatas = new ArrayList<>();
                //设置列表数据
                bean2.setData(detailPagerDatas);
                //for循环，解析每条数据
                for (int i = 0; i < data.length(); i++) {

                    JSONObject jsonObject = (JSONObject) data.get(i);

                    NewscenterPagerBean2.DetailPagerData detailPagerData = new NewscenterPagerBean2.DetailPagerData();
                    //添加到集合中
                    detailPagerDatas.add(detailPagerData);

                    int id = jsonObject.optInt("id");
                    detailPagerData.setId(id);
                    int type = jsonObject.optInt("type");
                    detailPagerData.setType(type);
                    String title = jsonObject.optString("title");
                    detailPagerData.setTitle(title);
                    String url = jsonObject.optString("url");
                    detailPagerData.setUrl(url);
                    String url1 = jsonObject.optString("url1");
                    detailPagerData.setUrl1(url1);
                    String dayurl = jsonObject.optString("dayurl");
                    detailPagerData.setDayurl(dayurl);
                    String excurl = jsonObject.optString("excurl");
                    detailPagerData.setExcurl(excurl);
                    String weekurl = jsonObject.optString("weekurl");
                    detailPagerData.setWeekurl(weekurl);
                    JSONArray children = jsonObject.optJSONArray("children");
                    if (children != null && children.length() > 0) {

                        List<NewscenterPagerBean2.DetailPagerData.ChildrenData> childrenDatas  = new ArrayList<>();

                        //设置集合-ChildrenData
                        detailPagerData.setChildren(childrenDatas);

                        for (int j = 0; j < children.length(); j++) {
                            JSONObject childrenitem = (JSONObject) children.get(j);

                            NewscenterPagerBean2.DetailPagerData.ChildrenData childrenData = new NewscenterPagerBean2.DetailPagerData.ChildrenData();
                            //添加到集合中
                            childrenDatas.add(childrenData);
                            int childId = childrenitem.optInt("id");
                            childrenData.setId(childId);
                            String childTitle = childrenitem.optString("title");
                            childrenData.setTitle(childTitle);
                            String childUrl = childrenitem.optString("url");
                            childrenData.setUrl(childUrl);
                            int childType = childrenitem.optInt("type");
                            childrenData.setType(childType);
                        }
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return bean2;
    }

    /**
     * 解析数据1.使用系统API 2. 第三方框架
     * @param json
     * @return
     */
    private NewscenterPagerBean2 parsedJson(String json) {
//        Gson gson=new Gson();
//        NewscenterPagerBean bean=gson.fromJson(json,NewscenterPagerBean.class);
        return new Gson().fromJson(json,NewscenterPagerBean2.class);
    }

    /*
    根据位置切换详情页面
     */
    public void swichPager(int position) {
        //1.设置标题
        tv_title.setText(data.get(position).getTitle());
        //2.2移除之前内容
        fl_content.removeAllViews();

        //3.添加新内容
        MenuDetaiBasePager detaiBasePager=detaiBasePagers.get(position);
        View rootView=detaiBasePager.rootview;
        detaiBasePager.initData();//初始化数据
        fl_content.addView(rootView);
    }
}
