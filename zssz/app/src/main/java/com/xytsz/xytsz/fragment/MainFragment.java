package com.xytsz.xytsz.fragment;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xytsz.xytsz.R;
import com.xytsz.xytsz.activity.CityPersonReportActivity;
import com.xytsz.xytsz.activity.LightFacilityActiviy;
import com.xytsz.xytsz.activity.MayorPhoneAcitvity;
import com.xytsz.xytsz.adapter.MainFragementHeadAdapter;
import com.xytsz.xytsz.adapter.MainFragmentAdapter;
import com.xytsz.xytsz.base.BaseFragment;
import com.xytsz.xytsz.ui.ScrollViewPager;
import com.xytsz.xytsz.util.IntentUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by admin on 2017/1/4.
 *
 *
 */
public class MainFragment extends BaseFragment {


    @Bind(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;
    @Bind(R.id.main_rv)
    RecyclerView mainRecycleView;
    private LinearLayout mheadViewPage;
    private LinearLayout mllDot;
    private List<View> mllDots = new ArrayList<>();
    private List<String> mImageUrls = new ArrayList<>();
    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.ll_citypersonreport:
                    IntentUtil.startActivity(v.getContext(), CityPersonReportActivity.class);
                    break;
                case R.id.ll_mayorphone:
                    //打电话
                    IntentUtil.startActivity(v.getContext(), MayorPhoneAcitvity.class);
                    break;
            }
        }
    };

    @Override
    public View initView() {
        View view = View.inflate(getActivity(), R.layout.fragment_main, null);
        return view;
    }

    private List<String> itemList;
    private List<String> headList;

    @Override
    public void initData() {

       LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getActivity());

        mainRecycleView.setLayoutManager(linearLayoutManager);
        itemList = new ArrayList<>();
        itemList.clear();
        itemList.add("会员展示");
        itemList.add("市政服务");
        itemList.add("便民服务");
        itemList.add("金融理财");



        MainFragmentAdapter mainAdapter  = new MainFragmentAdapter(this.getActivity(),itemList);
        mainRecycleView.setAdapter(mainAdapter);

        addHeadView1(mainAdapter);

        swipeRefresh.setColorSchemeColors(Color.RED, Color.BLUE);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        swipeRefresh.setRefreshing(false);
                    }
                }, 2000);
            }
        });
    }


    private void addHeadView1(MainFragmentAdapter mainAdapter) {
        //添加头布局
        View headview = inflateView(R.layout.item_mainfragment_head, mainRecycleView);
        mheadViewPage = (LinearLayout) headview.findViewById(R.id.mainfragment_head_vp);
        mllDot = (LinearLayout) headview.findViewById(R.id.ll_point);
        LinearLayout  mreport = (LinearLayout) headview.findViewById(R.id.ll_citypersonreport);
        LinearLayout  mayorphone = (LinearLayout) headview.findViewById(R.id.ll_mayorphone);
        mreport.setOnClickListener(listener);
        mayorphone.setOnClickListener(listener);


        RecyclerView mheadRecycleView = (RecyclerView) headview.findViewById(R.id.mainfragment_head_rv);
        //添加Viewpage 的 数据 4张图片
        getData();


        //添加市政采购平台的列表
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this.getActivity(),5);
        mheadRecycleView.setLayoutManager(gridLayoutManager);
        //获取到数据  进行填充
        headList = new ArrayList<>();
        headList.clear();
        headList.add("道路设施");
        headList.add("桥梁设施");
        headList.add("交通设施");
        headList.add("照明设施");
        headList.add("排水设施");
        headList.add("污水处理");
        headList.add("机械装备");
        headList.add("河道管理");
        headList.add("防汛物资");
        headList.add("市政技术");



        MainFragementHeadAdapter mainFragementHeadAdapter = new MainFragementHeadAdapter(this.getActivity(),headList);
        mheadRecycleView.setAdapter(mainFragementHeadAdapter);
        //设计点击事件
        mainFragementHeadAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                Intent intent = new Intent(MainFragment.this.getActivity(),LightFacilityActiviy.class);
                startActivity(intent);

            }
        });


        //添加 头内容
        mainAdapter.addHeaderView(headview);
    }

    private void getData() {
        //获取到服务器数据
        mImageUrls.clear();
        //添加数据
        //TOdo:添加数据
        mImageUrls.add("http://ww2.sinaimg.cn/large/610dc034jw1f3q5semm0wj20qo0hsacv.jpg");
        mImageUrls.add("http://ww1.sinaimg.cn/large/610dc034jw1f4d4iji38kj20sg0izdl1.jpg");
        mImageUrls.add("http://ww3.sinaimg.cn/large/610dc034jw1f070hyadzkj20p90gwq6v.jpg");
        mImageUrls.add("https://ws1.sinaimg.cn/large/610dc034gy1fh9utulf4kj20u011itbo.jpg");
        mImageUrls.add("http://ww2.sinaimg.cn/large/c85e4a5cgw1f62hzfvzwwj20hs0qogpo.jpg");


        initDot();
        ScrollViewPager scrollViewPager = new ScrollViewPager(getActivity(), mllDots);
        scrollViewPager.initImage(mImageUrls);
        scrollViewPager.roll();
        //把滑动的view pager 放进容器中
        mheadViewPage.removeAllViews();
        mheadViewPage.addView(scrollViewPager);

    }


    private void initDot() {
        mllDot.removeAllViews();
        mllDots.clear();
        for (int i = 0; i < mImageUrls.size(); i++) {
            View view = new View(getActivity());

            if (i == 0){
                view.setBackgroundResource(R.mipmap.dot_focus);

            }else {
                view.setBackgroundResource(R.mipmap.dot_normal);
            }
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(10,10);
            params.setMargins(0,0,6,0);

            view.setLayoutParams(params);
            mllDot.addView(view);

            mllDots.add(view);
        }
    }


    private View inflateView(int layoutId,RecyclerView rv) {
        //升级版的适配器支持添加headerView
        LayoutInflater inflater = getActivity().getLayoutInflater();
        //参三为false 表示 条目视图打气进来之后不添加rv.
        return inflater.inflate(layoutId,rv,false);
    }


    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


}
