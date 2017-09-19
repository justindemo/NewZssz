package com.xytsz.xytsz.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xytsz.xytsz.R;
import com.xytsz.xytsz.bean.HistoryReport;
import com.xytsz.xytsz.global.Data;

import java.util.List;

/**
 * Created by admin on 2017/7/20.
 * <p/>
 * 举报历史 adapter
 */
public class ReportHistoryAdapter extends BaseQuickAdapter<HistoryReport> {

    public ReportHistoryAdapter(List<HistoryReport> data) {
        super(R.layout.item_reporthistory, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HistoryReport item) {
        helper.setText(R.id.tv_history_reporter, item.getName());

        if (item.getDealtype_id() == 0) {

            helper.setText(R.id.tv_history_faci, Data.reportSort[item.getDealtype_id()]);
        } else {
            helper.setText(R.id.tv_history_faci, Data.reportSort[item.getDealtype_id() - 1]);

        }
        helper.setText(R.id.tv_history_report_time, item.getUploadtime());
        helper.setOnClickListener(R.id.iv_history_reporte, new OnItemChildClickListener());

        if (item.getImglist().size() != 0) {
            Glide.with(mContext).load(item.getImglist().get(0)).into((ImageView) helper.getView(R.id.iv_history_reporte));
        }
    }
}
