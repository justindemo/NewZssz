package com.xytsz.xytsz.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xytsz.xytsz.R;

import java.util.List;

/**
 * Created by admin on 2017/11/21.
 *
 * 推荐会员展示
 */
public class RecommendAdapter extends BaseQuickAdapter<String> {

    private int[] mIcons = {R.mipmap.member_bot1,R.mipmap.member_bot2,R.mipmap.member_bot3,R.mipmap.member_bot1,R.mipmap.member_bot2,R.mipmap.member_bot3};

    public RecommendAdapter(List<String> data) {
        super(R.layout.item_recommend, data);
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, String item) {
        viewHolder.setText(R.id.tv_member_name,item);
        int layoutPosition = viewHolder.getLayoutPosition();
        viewHolder.setImageResource(R.id.iv_member,mIcons[layoutPosition]);
    }
}
