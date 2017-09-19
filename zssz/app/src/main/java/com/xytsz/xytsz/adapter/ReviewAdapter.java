package com.xytsz.xytsz.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xytsz.xytsz.R;
import com.xytsz.xytsz.bean.Review;
import com.xytsz.xytsz.global.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by admin on 2017/1/11.
 *
 */
public class ReviewAdapter extends BaseAdapter implements Serializable {


    private List<Review.ReviewRoad> reviewRoads;

    public ReviewAdapter(List<Review.ReviewRoad> reviewRoads) {
        this.reviewRoads = reviewRoads;
    }

    @Override
    public int getCount() {
        return reviewRoads.size();
    }

    @Override
    public Object getItem(int position) {
        return reviewRoads.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder ;
        if (convertView ==null){
            holder = new ViewHolder();
            convertView = View.inflate(parent.getContext(), R.layout.item_review, null);
            holder.unreadmsg = (TextView) convertView.findViewById(R.id.tv_un_read_msg_count);
            holder.roadname = (TextView) convertView.findViewById(R.id.tv_road_name);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        //设置未读的点击时间  3:代表从服务器获取当前街道的所有病害
        Review.ReviewRoad reviewRoad = reviewRoads.get(position);

        int size = reviewRoad.getList().size();
        //在去更改
        holder.roadname.setText(reviewRoad.getStreetName());

        if (size == 0){
            holder.unreadmsg.setVisibility(View.INVISIBLE);

        }else {
            holder.unreadmsg.setVisibility(View.VISIBLE);
            holder.unreadmsg.setText(size + "");
        }
        notifyDataSetChanged();

        return convertView;
    }

    static class ViewHolder
    {
        public TextView unreadmsg;
        public TextView roadname;

    }
}
