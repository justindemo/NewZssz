package com.xytsz.xytsz.adapter;

import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xytsz.xytsz.R;
import com.xytsz.xytsz.activity.MemberShow;

import java.util.List;

/**
 * Created by admin on 2017/7/19.
 *
 *   会员展示adapter
 */
public class MemberShowAdapter extends BaseQuickAdapter<MemberShow> {



    public MemberShowAdapter( List<MemberShow> data) {
        super(R.layout.item_member_show_adapter, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, MemberShow item) {
        Glide.with(mContext).load(item.getImgurl()).asBitmap().into((ImageView) helper.getView(R.id.iv_membershow));

        helper.setText(R.id.tv_membershow,item.getEnname());

        helper.setOnClickListener(R.id.iv_membershow,new OnItemChildClickListener());
        helper.setOnClickListener(R.id.iv_rightarrow,new OnItemChildClickListener());
        helper.setOnClickListener(R.id.tv_membershow,new OnItemChildClickListener());


    }
}
