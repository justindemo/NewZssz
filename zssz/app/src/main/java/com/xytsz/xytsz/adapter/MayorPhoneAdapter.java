package com.xytsz.xytsz.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xytsz.xytsz.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2017/7/28.
 * 市长热线
 */
public class MayorPhoneAdapter extends BaseQuickAdapter<String>{

    private List<List<String>> allList = new ArrayList<>();
    public MayorPhoneAdapter(List data, List<List<String>> allList) {
        super(R.layout.item_mayorphone, data);
        this.allList= allList;
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        int position = helper.getLayoutPosition();
        helper.setText(R.id.tv_mayor_area,item);
        helper.setText(R.id.tv_mayor_name,allList.get(1).get(position));
        helper.setText(R.id.tv_mayorphone,allList.get(2).get(position));

    }


}
