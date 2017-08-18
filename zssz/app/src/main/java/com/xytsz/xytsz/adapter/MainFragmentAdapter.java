package com.xytsz.xytsz.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.xytsz.xytsz.R;
import com.xytsz.xytsz.activity.CityPersonReportActivity;
import com.xytsz.xytsz.activity.MemberShowActivity;
import com.xytsz.xytsz.util.IntentUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by admin on 2017/7/7.
 * 主页adapter
 */
public class MainFragmentAdapter extends BaseQuickAdapter {

    private List<List<String>> list = new ArrayList<>();
    private Context context;
    private List<String> memberlist;
    private List<String> civicismList;
    private List<String> convenienceList;
    private List<String> financialPlaneList;
    private ItemMainFragmentAdapter itemMainFragmentAdapter;

    public MainFragmentAdapter(Context context, List data) {
        super(R.layout.item_mainfragment, data);

        this.context = context;
        memberlist = new ArrayList<>();
        memberlist.add("市政协会");
        memberlist.add("市政企业");
        memberlist.add("集采企业");

        civicismList = new ArrayList<>();
        civicismList.add("市政信息");
        civicismList.add("部门政务");
        civicismList.add("社会管理");
        civicismList.add("社区管理");
        civicismList.add("居民生活");

        convenienceList = new ArrayList<>();
        convenienceList.add("即时通讯");
        convenienceList.add("通行交通");
        convenienceList.add("舌尖美食");
        convenienceList.add("名胜古迹");
        convenienceList.add("民俗乡村");
        convenienceList.add("都市购物");
        convenienceList.add("旅游住宿");

        financialPlaneList = new ArrayList<>();
        financialPlaneList.add("保险");
        financialPlaneList.add("基金");
        financialPlaneList.add("风投");
        financialPlaneList.add("众筹");

    }

    @Override
    protected void convert(BaseViewHolder helper, Object item) {

        helper.setText(R.id.tv_mainfragment_title, item.toString());

        int layoutPosition = helper.getLayoutPosition();
        //int position = helper.getPosition();

        View convertView = helper.getConvertView();
        RecyclerView mrv = (RecyclerView) convertView.findViewById(R.id.item_rv_mainfragment);
        GridLayoutManager manager = new GridLayoutManager(context, 4);
        mrv.setLayoutManager(manager);

        if (layoutPosition == 1) {
            list.clear();
            list.add(memberlist);
            itemMainFragmentAdapter = new ItemMainFragmentAdapter(list.get(layoutPosition - 1));
            mrv.setAdapter(itemMainFragmentAdapter);
            itemMainFragmentAdapter.setOnRecyclerViewItemClickListener(new OnRecyclerViewItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    Intent intent = new Intent(view.getContext(), MemberShowActivity.class);
                    intent.putExtra("position", position);
                    view.getContext().startActivity(intent);
                }
            });

        } else if (layoutPosition == 2) {
            list.clear();
            list.add(memberlist);
            list.add(civicismList);
            itemMainFragmentAdapter = new ItemMainFragmentAdapter(list.get(layoutPosition - 1));
            mrv.setAdapter(itemMainFragmentAdapter);
            itemMainFragmentAdapter.setOnRecyclerViewItemClickListener(new OnRecyclerViewItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {

                }
            });
        } else if (layoutPosition == 3) {
            list.clear();
            list.add(memberlist);
            list.add(civicismList);
            list.add(convenienceList);
            itemMainFragmentAdapter = new ItemMainFragmentAdapter(list.get(layoutPosition - 1));
            mrv.setAdapter(itemMainFragmentAdapter);
            itemMainFragmentAdapter.setOnRecyclerViewItemClickListener(new OnRecyclerViewItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    switch (position){
                        case 0:

                            break;
                    }
                }
            });
        } else if (layoutPosition == 4) {
            list.clear();
            list.add(memberlist);
            list.add(civicismList);
            list.add(convenienceList);
            list.add(financialPlaneList);
            itemMainFragmentAdapter = new ItemMainFragmentAdapter(list.get(layoutPosition - 1));
            mrv.setAdapter(itemMainFragmentAdapter);
            itemMainFragmentAdapter.setOnRecyclerViewItemClickListener(new OnRecyclerViewItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {

                }
            });


        }
    }

}
