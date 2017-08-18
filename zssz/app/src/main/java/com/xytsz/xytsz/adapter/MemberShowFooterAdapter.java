package com.xytsz.xytsz.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xytsz.xytsz.R;

import java.util.List;

/**
 * Created by admin on 2017/7/19.
 * 尾部  会员展示
 */
public class MemberShowFooterAdapter extends BaseQuickAdapter<String> {
    public MemberShowFooterAdapter( List data) {
        super(R.layout.item_footer_membershow, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_footer,item);

    }
}
