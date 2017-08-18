package com.xytsz.xytsz.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xytsz.xytsz.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2017/6/21.
 * table 界面数据填充
 */
public class TableAdapter extends BaseAdapter {

    //数据

    private List<Integer> list ;

    private List<String> memberList;

    public TableAdapter(List<Integer> list, List<String> memberList){

        this.memberList = memberList;
        this.list = list;
    }

    @Override
    public int getCount() {

        return memberList.size();

    }

    @Override
    public Object getItem(int position) {
        return memberList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder ;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(parent.getContext(), R.layout.item_table, null);
            holder.member = (TextView) convertView.findViewById(R.id.member);
            holder.number = (TextView) convertView.findViewById(R.id.tv_table_number);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }



        holder.member.setText(memberList.get(position));

        holder.number.setText(list.get(position)+"");

        return convertView;
    }

    static class ViewHolder {
        public TextView member;
        public TextView number;

    }
}
