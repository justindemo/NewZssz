package com.xytsz.xytsz.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xytsz.xytsz.R;

import java.util.List;

/**
 * Created by admin on 2017/8/8.
 *
 * 城市居民 举报反馈
 */
public class PersonSurveyAdapter extends BaseQuickAdapter{

    public PersonSurveyAdapter( List data) {
        super(R.layout.item_personsurvey, data);
    }



    @Override
    protected void convert(BaseViewHolder helper, Object item) {

        //设置数据



    }
}
