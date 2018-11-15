package com.xytsz.xytsz.fragment;


import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;



import com.xytsz.xytsz.R;
import com.xytsz.xytsz.activity.FacilityManageActivity;
import com.xytsz.xytsz.activity.FloodActivity;
import com.xytsz.xytsz.activity.MakerProblemActivty;
import com.xytsz.xytsz.activity.MemberLocationActivity;
import com.xytsz.xytsz.adapter.SuperviseAdapter;
import com.xytsz.xytsz.base.BaseFragment;
import com.xytsz.xytsz.global.GlobalContanstant;
import com.xytsz.xytsz.ui.MarqueeView;
import com.xytsz.xytsz.util.IntentUtil;
import com.xytsz.xytsz.util.SpUtils;
import com.xytsz.xytsz.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by admin on 2017/6/29.
 *
 *  监管界面
 */
public class SuperviseFragment extends BaseFragment  {

    private RecyclerView recycleView;
    private int role;
    private static final int PERSON = 0;
    private static final int PROBLEM = 1;
    private static final int FACILITY = 3;
    private static final int FLOOD = 4;
    private static final int CAROIL = 2;
    private MarqueeView mheadMarquee;
    private int alluser;
    private TextView mActionbartext;


    @Override
    public View initView() {

        View view = View.inflate(getActivity(), R.layout.fragment_supervise, null);
        recycleView = (RecyclerView) view.findViewById(R.id.recycle_view);
        mActionbartext = (TextView) view.findViewById(R.id.actionbar_text);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),3);

        recycleView.setLayoutManager(gridLayoutManager);
        return view;
    }

    private List<String>  titles = new ArrayList<>();
    @Override
    public void initData() {
        String alltitle = getString(R.string.alltitle);
        alluser = SpUtils.getInt(getContext(), GlobalContanstant.ALLUSERCOUNT);
        titles.clear();
        titles.add("人员定位");
        titles.add("病害处置");
        titles.add("车辆管理");
        titles.add("设施管理");
        titles.add("防汛警告");
        titles.add("智能井盖");
        titles.add("智慧路灯");

        mActionbartext.setText(R.string.supervise);
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
                    case FACILITY:
                        IntentUtil.startActivity(getContext(), FacilityManageActivity.class);
                        //井盖
                        break;
                    case CAROIL:
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

        mheadMarquee = (MarqueeView) headview.findViewById(R.id.tv_headmarquee);

        mheadMarquee.startWithText(alltitle + alluser);
        mheadMarquee.startWithText(alltitle + alluser,R.anim.anim_right_in,R.anim.anim_left_out);

        adapter.addHeaderView(headview);



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
        role = SpUtils.getInt(getContext(), GlobalContanstant.ROLE);
        mheadMarquee.startFlipping();
    }

    @Override
    public void onPause() {
        super.onPause();
        mheadMarquee.stopFlipping();
    }

    @Override
    public void onResume() {
        super.onResume();
        mheadMarquee.startFlipping();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mheadMarquee.stopFlipping();
    }
}

