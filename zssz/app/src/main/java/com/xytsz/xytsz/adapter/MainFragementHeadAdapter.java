package com.xytsz.xytsz.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xytsz.xytsz.R;

import java.util.List;

/**
 * Created by admin on 2017/7/7.
 *
 *
 */

public class MainFragementHeadAdapter extends BaseQuickAdapter<String> {


    public MainFragementHeadAdapter(Context context,List<String> headList) {
        super(R.layout.item_rv_simple,headList);
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, String  item) {
        helper.setText(R.id.tv_rv_simple,item);

    }
}
