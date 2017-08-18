package com.xytsz.xytsz.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xytsz.xytsz.R;

import java.util.List;

/**
 * Created by admin on 2017/8/8.
 *
 *
 */
public class CityInformationAdapter extends BaseQuickAdapter{


    public CityInformationAdapter(List data) {
        super(R.layout.item_cityinformation, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Object item) {

        helper.setText(R.id.tv_news_title,item.toString());

    }
}
