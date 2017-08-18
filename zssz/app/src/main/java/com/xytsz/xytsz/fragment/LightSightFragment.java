package com.xytsz.xytsz.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.xytsz.xytsz.R;
import com.xytsz.xytsz.adapter.LightSightRecyclerAdapter;
import com.xytsz.xytsz.base.BasefacilityFragment;
import com.xytsz.xytsz.ui.ScrollViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by admin on 2017/7/11.
 * 照明设施具体界面
 */
public class LightSightFragment extends BasefacilityFragment {



    private LinearLayout mHeadViewpager;
    private List<View> mllDots = new ArrayList<>();
    private List<String> mImageUrls = new ArrayList<>();
    private LinearLayout mllDot;
    private RecyclerView lightsightRecycleview;
    private SwipeRefreshLayout lightsightswipe;

    @Override
    protected Object getContentView() {

        return R.layout.fragment_lightsight;
    }

    @Override
    protected void initView() {
        lightsightRecycleview = findView(R.id.lightsight_recycleview);
        lightsightswipe = findView(R.id.lightsight_swiperefresh);

        View headView = View.inflate(getActivity(), R.layout.lightsight_head, null);
        mHeadViewpager = (LinearLayout) headView.findViewById(R.id.lightsight_head_viewpager);
        mllDot = (LinearLayout) headView.findViewById(R.id.lightsight_ll_dot);

        getData();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
        lightsightRecycleview.setLayoutManager(linearLayoutManager);

        LightSightRecyclerAdapter adapter = new LightSightRecyclerAdapter(this.getActivity(),mImageUrls);
        lightsightRecycleview.setAdapter(adapter);

        adapter.addHeaderView(headView);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        lightsightswipe.setColorSchemeColors(Color.RED,Color.BLUE);
        lightsightswipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        lightsightswipe.setRefreshing(false);
                    }
                },2000);
            }
        });
    }

    /**
     * 获取到图片和点
     */
    private void getData() {

        //获取到服务器数据
        mImageUrls.clear();

        //添加数据
        //TOdo:添加数据

        mImageUrls.add("https://ws1.sinaimg.cn/large/610dc034gy1fh9utulf4kj20u011itbo.jpg");
        mImageUrls.add("https://ws1.sinaimg.cn/large/610dc034gy1fh9utulf4kj20u011itbo.jpg");
        mImageUrls.add("https://ws1.sinaimg.cn/large/610dc034gy1fh9utulf4kj20u011itbo.jpg");
        mImageUrls.add("https://ws1.sinaimg.cn/large/610dc034gy1fh9utulf4kj20u011itbo.jpg");
        if (checkDatas(mImageUrls)) {

            initDot();
            ScrollViewPager scrollViewPager = new ScrollViewPager(getActivity(), mllDots);
            scrollViewPager.initImage(mImageUrls);
            scrollViewPager.roll();
            //把滑动的view pager 放进容器中
            mHeadViewpager.removeAllViews();
            mHeadViewpager.addView(scrollViewPager);
        }
    }


    private void initDot() {
        mllDot.removeAllViews();
        mllDots.clear();
        for (int i = 0; i < mImageUrls.size(); i++) {
            View view = new View(getActivity());

            if (i == 0) {
                view.setBackgroundResource(R.mipmap.dot_focus);

            } else {
                view.setBackgroundResource(R.mipmap.dot_normal);
            }
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(10, 10);
            params.setMargins(0, 0, 6, 0);

            view.setLayoutParams(params);
            mllDot.addView(view);

            mllDots.add(view);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
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
