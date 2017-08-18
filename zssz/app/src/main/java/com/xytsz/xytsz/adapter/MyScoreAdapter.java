package com.xytsz.xytsz.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xytsz.xytsz.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2017/7/17.
 *
 * 我的积分详细 adapter
 */
public class MyScoreAdapter extends BaseQuickAdapter<String>{
    private List<String> list = new ArrayList<>();
    public MyScoreAdapter(List<String> list) {
        super(R.layout.item_myscoreadapter,list);
        this.list = list;
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_myscore_msc,item);
        helper.setText(R.id.tv_myscore_num,"+"+list.size());
    }
}
