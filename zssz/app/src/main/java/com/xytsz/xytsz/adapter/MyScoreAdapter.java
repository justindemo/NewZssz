package com.xytsz.xytsz.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xytsz.xytsz.R;
import com.xytsz.xytsz.bean.ScoreDetail;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2017/7/17.
 *
 * 我的积分详细 adapter
 */
public class MyScoreAdapter extends BaseQuickAdapter<ScoreDetail>{

    public MyScoreAdapter(List<ScoreDetail> list) {
        super(R.layout.item_myscoreadapter,list);

    }

    @Override
    protected void convert(BaseViewHolder helper, ScoreDetail item) {

        helper.setText(R.id.tv_myscore_msc,item.getInfo());
        helper.setText(R.id.tv_myscore_num,"+"+item.getIntegral());
        helper.setText(R.id.tv_myscore_signtime,item.getTime());
    }
}
