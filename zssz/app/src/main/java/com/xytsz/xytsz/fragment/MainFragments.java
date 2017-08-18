package com.xytsz.xytsz.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.xytsz.xytsz.R;
import com.xytsz.xytsz.activity.CityInformationActivity;
import com.xytsz.xytsz.activity.CityPersonReportActivity;
import com.xytsz.xytsz.activity.ContactCityActivity;
import com.xytsz.xytsz.activity.MemberShowActivity;
import com.xytsz.xytsz.activity.PersonSurveyActivity;
import com.xytsz.xytsz.base.BaseFragment;
import com.xytsz.xytsz.ui.ScrollViewPager;
import com.xytsz.xytsz.util.IntentUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by admin on 2017/1/4.
 *
 */
public class MainFragments extends BaseFragment {


    @Bind(R.id.mainfragment_head_vp)
    LinearLayout mainfragmentHeadVp;
    @Bind(R.id.ll_point)
    LinearLayout llPoint;
    @Bind(R.id.rl_citypersonreport)
    RelativeLayout rlCitypersonreport;
    @Bind(R.id.rl_contect_main)
    RelativeLayout rlContectMain;
    @Bind(R.id.rl_informaiton_main)
    RelativeLayout rlInformaitonMain;
    @Bind(R.id.rl_survey_main)
    RelativeLayout rlSurveyMain;
    @Bind(R.id.rl_assiation_main)
    RelativeLayout rlAssiationMain;
    @Bind(R.id.rl_business_main)
    RelativeLayout rlBusinessMain;
    @Bind(R.id.rl_simple_company)
    RelativeLayout rlSimpleCompany;

    private List<View> mllDots = new ArrayList<>();
    private List<String> mImageUrls = new ArrayList<>();

    @Override
    public View initView() {
        View view = View.inflate(getActivity(), R.layout.fragment_main1, null);
        return view;
    }


    @Override
    public void initData() {
        getData();
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
        mainfragmentHeadVp.removeAllViews();
        mainfragmentHeadVp.addView(scrollViewPager);

    }


    private void initDot() {

        llPoint.removeAllViews();
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
            llPoint.addView(view);

            mllDots.add(view);
        }
    }

    /**
     * 初始化 view
     */
    private View inflateView(int layoutId, RecyclerView rv) {
        //升级版的适配器支持添加headerView
        LayoutInflater inflater = getActivity().getLayoutInflater();
        //参三为false 表示 条目视图打气进来之后不添加rv.
        return inflater.inflate(layoutId, rv, false);
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

    @OnClick({R.id.rl_citypersonreport, R.id.rl_contect_main, R.id.rl_informaiton_main, R.id.rl_survey_main, R.id.rl_assiation_main, R.id.rl_business_main, R.id.rl_simple_company})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_citypersonreport:
                IntentUtil.startActivity(view.getContext(), CityPersonReportActivity.class);
                break;
            case R.id.rl_contect_main:
                IntentUtil.startActivity(view.getContext(),ContactCityActivity.class);
                break;
            case R.id.rl_informaiton_main:
                IntentUtil.startActivity(view.getContext(),CityInformationActivity.class);
                break;
            //反馈
            case R.id.rl_survey_main:
                IntentUtil.startActivity(view.getContext(),PersonSurveyActivity.class);
                break;
            case R.id.rl_assiation_main:
                Intent intent = new Intent(view.getContext(), MemberShowActivity.class);
                intent.putExtra("position", "assiation");
                startActivity(intent);
                break;
            case R.id.rl_business_main:
                Intent intent1 = new Intent(view.getContext(), MemberShowActivity.class);
                intent1.putExtra("position", "businessStr");
                startActivity(intent1);
                break;
            case R.id.rl_simple_company:
                Intent intent2 = new Intent(view.getContext(), MemberShowActivity.class);
                intent2.putExtra("position", "company");
                startActivity(intent2);
                break;
        }
    }
}
