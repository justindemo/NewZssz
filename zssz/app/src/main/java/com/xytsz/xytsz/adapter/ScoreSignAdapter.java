package com.xytsz.xytsz.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xytsz.xytsz.R;
import com.xytsz.xytsz.activity.PrizeActivity;
import com.xytsz.xytsz.util.IntentUtil;

import java.util.List;

/**
 * Created by admin on 2017/7/17.
 *
 * 签到及领奖
 */
public class ScoreSignAdapter extends BaseQuickAdapter{

    public ScoreSignAdapter(List data, Context context) {
        super(R.layout.item_scroesignadapter, data);
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, Object item) {
        //
        View convertView = helper.getConvertView();
        ImageView mImageView = (ImageView) convertView.findViewById(R.id.item_iv_scoresign);
        TextView mtvTitle = (TextView) convertView.findViewById(R.id.tv_scoresign_request);
        TextView mtvprize = (TextView) convertView.findViewById(R.id.tv_scoresign_prize);

        final int layoutPosition = helper.getLayoutPosition() -1;
        Glide.with(mContext).load(item.toString()).into(mImageView);
        mtvprize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),PrizeActivity.class);
                intent.putExtra("position",layoutPosition);
                v.getContext().startActivity(intent);
            }
        });
    }
}
