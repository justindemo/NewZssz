package com.xytsz.xytsz.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xytsz.xytsz.R;

import java.util.List;

import javax.microedition.khronos.opengles.GL;

/**
 * Created by admin on 2017/7/11.
 *
 *
 */
public class LightSightRecyclerAdapter extends BaseQuickAdapter<String> {
    public LightSightRecyclerAdapter(Context context,List<String> mImageUrls) {
        super(R.layout.item_lightsightrecycle,mImageUrls);
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        View convertView = helper.getConvertView();
        LinearLayout mlltitle = (LinearLayout) convertView.findViewById(R.id.item_lightsight_ll_title);
        ImageView mImageView = (ImageView) convertView.findViewById(R.id.lightsight_imagview);

        int position = helper.getLayoutPosition() - 1;
        if (position != 0 ){
            mlltitle.setVisibility(View.GONE);
        }

        Glide.with(mContext).load(item).into(mImageView);
    }
}
