package com.xytsz.xytsz.fragment;


import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xytsz.xytsz.R;
import com.xytsz.xytsz.activity.CityPersonReportActivity;
import com.xytsz.xytsz.activity.MemberShowActivity;
import com.xytsz.xytsz.adapter.RecommendAdapter;
import com.xytsz.xytsz.base.BaseFragment;
import com.xytsz.xytsz.global.GlobalContanstant;
import com.xytsz.xytsz.ui.ScrollViewPager;
import com.xytsz.xytsz.util.IntentUtil;
import com.xytsz.xytsz.util.SpUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by admin on 2017/1/4.
 * 会员界面
 */
public class MainFragments extends BaseFragment {


    @Bind(R.id.actionbar_text)
    TextView actionbarText;
    @Bind(R.id.mainfragment_head_vp)
    LinearLayout mainfragmentHeadVp;
    @Bind(R.id.ll_point)
    LinearLayout llPoint;
    @Bind(R.id.rl_assiation_main)
    LinearLayout rlAssiationMain;
    @Bind(R.id.rl_business_main)
    LinearLayout rlBusinessMain;
    @Bind(R.id.rl_simple_company)
    LinearLayout rlSimpleCompany;
    @Bind(R.id.mains_recyclerview)
    RecyclerView mainsRecyclerview;
    @Bind(R.id.main_ib)
    ImageButton mainIb;

    private List<View> mllDots = new ArrayList<>();
    private List<String> mImageUrls = new ArrayList<>();
    private int role;
    private String toHome;
    private TextView mActionbartext;
    private List<String> titles = new ArrayList<>();

    @Override
    public View initView() {
        View view = View.inflate(getActivity(), R.layout.fragment_main1, null);
        mActionbartext = (TextView) view.findViewById(R.id.actionbar_text);

        return view;
    }


    @Override
    public void initData() {
        toHome = getString(R.string.to_home);
        role = SpUtils.getInt(getActivity(), GlobalContanstant.ROLE);
        mActionbartext.setText(R.string.member_title);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mainsRecyclerview.setLayoutManager(linearLayoutManager);
        getData();


    }


    private void getData() {
        //获取到服务器数据
        mImageUrls.clear();
        //添加数据
        //TOdo:添加数据
        mImageUrls.add("http://ww2.sinaimg.cn/large/610dc034jw1f3q5semm0wj20qo0hsacv.jpg");
        mImageUrls.add("http://ww2.sinaimg.cn/large/c85e4a5cgw1f62hzfvzwwj20hs0qogpo.jpg");
        titles.clear();
        titles.add("北京市政协会");
        titles.add("大兴市政");
        titles.add("星光裕华");
        titles.add("天津市政协会");
        titles.add("泰达市政");
        titles.add("金顺通");


        initDot();
        ScrollViewPager scrollViewPager = new ScrollViewPager(getActivity(), mllDots);
        scrollViewPager.initImage(mImageUrls);
        scrollViewPager.roll();
        //把滑动的view pager 放进容器中
        mainfragmentHeadVp.removeAllViews();
        mainfragmentHeadVp.addView(scrollViewPager);

        RecommendAdapter adapter = new RecommendAdapter(titles);
        mainsRecyclerview.setAdapter(adapter);

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
//        ButterKnife.unbind(this);
    }

    @OnClick({R.id.rl_assiation_main, R.id.rl_business_main, R.id.rl_simple_company})
    public void onViewClicked(View view) {
        switch (view.getId()) {

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

    @OnClick(R.id.main_ib)
    public void onViewClicked() {
        //if (role != 0 ){
//                    ToastUtil.shortToast(getContext(),toHome);
//                }else {
//                IntentUtil.startActivity(view.getContext(), CityPersonReportActivity.class);
//                }
        IntentUtil.startActivity(MainFragments.this.getActivity(), CityPersonReportActivity.class);
    }
}
