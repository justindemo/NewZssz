package com.xytsz.xytsz.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.dalong.marqueeview.MarqueeView;
import com.xytsz.xytsz.R;
import com.xytsz.xytsz.activity.BridgeNumberActivity;
import com.xytsz.xytsz.activity.FloodActivity;
import com.xytsz.xytsz.activity.LibActivity;
import com.xytsz.xytsz.activity.MakerProblemActivty;
import com.xytsz.xytsz.activity.MemberLocationActivity;
import com.xytsz.xytsz.activity.RoadNumberActivity;
import com.xytsz.xytsz.adapter.SuperviseAdapter;
import com.xytsz.xytsz.base.BaseFragment;
import com.xytsz.xytsz.bean.Road;
import com.xytsz.xytsz.global.GlobalContanstant;
import com.xytsz.xytsz.util.IntentUtil;
import com.xytsz.xytsz.util.SpUtils;
import com.xytsz.xytsz.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2017/6/29.
 *
 */
public class SuperviseFragment extends BaseFragment implements View.OnClickListener {

    private RecyclerView recycleView;
    private int role;
    private static final int PERSON = 0;
    private static final int PROBLEM = 1;
    private static final int LIB = 3;
    private static final int FLOOD = 4;
    private static final int NOTICE = 2;
    private MarqueeView mheadMarquee;


    @Override
    public View initView() {

        View view = View.inflate(getActivity(), R.layout.fragment_supervise, null);
        recycleView = (RecyclerView) view.findViewById(R.id.recycle_view);


        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),3);

        recycleView.setLayoutManager(gridLayoutManager);
        return view;
    }

    private List<String>  titles = new ArrayList<>();
    @Override
    public void initData() {
        titles.clear();
        titles.add("人员定位");
        titles.add("病害处置");
        titles.add("掌上公告");
        titles.add("井盖定位");
        titles.add("防汛警告");


        SuperviseAdapter adapter = new SuperviseAdapter(titles,role);
        recycleView.setAdapter(adapter);

        adapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                switch (position) {
                    case PERSON:
                        //定位
                        if (role == 1) {
                            IntentUtil.startActivity(getContext(), MemberLocationActivity.class);
                        } else {
                            ToastUtil.shortToast(getContext(), "您没有权限");
                        }

                        break;
                    case PROBLEM:
                        if (role == 1) {
                            IntentUtil.startActivity(getContext(), MakerProblemActivty.class);
                        } else {
                            ToastUtil.shortToast(getContext(), "您没有权限");
                        }
                        //IntentUtil.startActivity(parent.getContext(), MakerProblemActivty.class);

                        break;
                    case LIB:
                        IntentUtil.startActivity(getContext(), LibActivity.class);
                        //井盖
                        break;
                    case NOTICE:
                        //公告
                        //showDialog();
                        break;
                    case FLOOD:
                        IntentUtil.startActivity(getContext(), FloodActivity.class);
                        //防汛
                        break;
                }
            }
        });


        View headview = inflateView(R.layout.supervise_header, recycleView);
        RelativeLayout mroad = (RelativeLayout) headview.findViewById(R.id.head_road);
        RelativeLayout mbridge = (RelativeLayout) headview.findViewById(R.id.head_bridge);

        mheadMarquee = (MarqueeView) headview.findViewById(R.id.tv_headmarquee);

        mheadMarquee.setText("全国掌上市政上线人数："+"10086");
        mheadMarquee.setFocusable(true);
        mheadMarquee.requestFocus();
        mheadMarquee.sepX = 2;
        mheadMarquee.startScroll();


        adapter.addHeaderView(headview);

        mroad.setOnClickListener(this);
        mbridge.setOnClickListener(this);

    }

    private View inflateView(int layoutId,RecyclerView rv) {
        //升级版的适配器支持添加headerView
        LayoutInflater inflater = getActivity().getLayoutInflater();
        //参三为false 表示 条目视图打气进来之后不添加rv.
        return inflater.inflate(layoutId,rv,false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.head_bridge:
                //桥梁
                IntentUtil.startActivity(getContext(),BridgeNumberActivity.class);
                break;
            case R.id.head_road:
                //道路
                IntentUtil.startActivity(getContext(), RoadNumberActivity.class);
                break;
        }
    }


    @Override
    public void onStart() {
        super.onStart();
        role = SpUtils.getInt(getContext(), GlobalContanstant.ROLE);
        mheadMarquee.startScroll();
    }

    @Override
    public void onPause() {
        super.onPause();
        mheadMarquee.stopScroll();
    }

    @Override
    public void onResume() {
        super.onResume();
        mheadMarquee.startScroll();
    }

}

