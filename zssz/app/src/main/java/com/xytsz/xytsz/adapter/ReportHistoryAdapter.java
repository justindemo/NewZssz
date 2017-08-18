package com.xytsz.xytsz.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xytsz.xytsz.R;

import java.util.List;

/**
 * Created by admin on 2017/7/20.
 *
 * 举报历史 adapter
 */
public class ReportHistoryAdapter extends BaseQuickAdapter {

    public ReportHistoryAdapter( List data) {
        super(R.layout.item_reporthistory, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Object item) {
        helper.setText(R.id.tv_history_reporter,item.toString());
    }
}
