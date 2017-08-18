package com.xytsz.xytsz.adapter;

import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xytsz.xytsz.R;

import com.xytsz.xytsz.bean.ImageUrl;
import com.xytsz.xytsz.bean.Review;
import com.xytsz.xytsz.global.GlobalContanstant;
import com.xytsz.xytsz.util.SpUtils;

import java.util.List;

/**
 * Created by admin on 2017/2/22
 */
public class CheckRoadAdapter extends BaseAdapter {


    private Review.ReviewRoad reviewRoad;
    private List<List<ImageUrl>> imageUrlLists;
    private List<List<ImageUrl>> imageUrlPostLists;

    private List<Review.ReviewRoad.ReviewRoadDetail> reviewRoadDetails;

    public CheckRoadAdapter(Review.ReviewRoad reviewRoad, List<List<ImageUrl>> imageUrlLists, List<List<ImageUrl>> imageUrlPostLists) {

        this.reviewRoad = reviewRoad;
        this.imageUrlLists = imageUrlLists;
        this.imageUrlPostLists = imageUrlPostLists;
        reviewRoadDetails = reviewRoad.getList();
    }

    /**
     * 通知adapter更新
     *
     * @param reviewRoadDetailss gebg
     */
    public void updateAdapter(List<Review.ReviewRoad.ReviewRoadDetail> reviewRoadDetailss) {
        this.reviewRoadDetails = reviewRoadDetailss;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {

        return reviewRoadDetails.size();
    }

    @Override
    public Object getItem(int position) {
        return reviewRoadDetails.get(position);
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
            convertView = View.inflate(parent.getContext(), R.layout.item_checkroad, null);
            holder.ivReport = (ImageView) convertView.findViewById(R.id.iv_check_report);
            holder.ivDealed = (ImageView) convertView.findViewById(R.id.iv_check_dealed);
            holder.tvReporter = (TextView) convertView.findViewById(R.id.tv_check_reporter);
            holder.tvReviewer = (TextView) convertView.findViewById(R.id.tv_check_reviewer);
            holder.tvDealer = (TextView) convertView.findViewById(R.id.tv_check_dealer);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        //显示图片
        //false  显示的是否通过
        Review.ReviewRoad.ReviewRoadDetail detail = reviewRoadDetails.get(position);
        String upload_person_id = detail.getUpload_Person_ID()+"";

        String actualCompletion_person_id = detail.getActualCompletion_Person_ID() +"";
        //通过上报人的ID 拿到上报人的名字
        //获取到所有人的列表 把对应的 id 找出名字
        List<String> personNamelist = SpUtils.getStrListValue(parent.getContext(), GlobalContanstant.PERSONNAMELIST);
        List<String> personIDlist = SpUtils.getStrListValue(parent.getContext(), GlobalContanstant.PERSONIDLIST);

        for (int i = 0; i < personIDlist.size(); i++) {
            if (upload_person_id.equals(personIDlist.get(i))){
                id = i;
            }

            if (actualCompletion_person_id.equals(personIDlist.get(i))){
                acID = i;
            }
        }

        String userName = personNamelist.get(id);
        String acName = personNamelist.get(acID);

        // String userName = SpUtils.getString(parent.getContext(), GlobalContanstant.USERNAME);
        holder.tvReporter.setText(userName);

        holder.tvDealer.setText(acName);


        //获取到当前点击的URL集合
        urlList = imageUrlLists.get(position);
        //显示的第一张图片
        if (urlList.size() != 0) {
            ImageUrl imageUrl = urlList.get(0);
            String imgurl = imageUrl.getImgurl();

            Glide.with(parent.getContext()).load(imgurl).into(holder.ivReport);
        }

        List<ImageUrl> urlpostList = imageUrlPostLists.get(position);

        if (urlpostList.size() != 0) {
            ImageUrl imageUrlpost = urlpostList.get(0);
            String imgurlPost = imageUrlpost.getImgurl();
            Glide.with(parent.getContext()).load(imgurlPost).into(holder.ivDealed);
        }

        return convertView;
    }
    private  int id;
    private  int acID;
    private List<ImageUrl> urlList;
    static class ViewHolder {
        public TextView tvReporter;
        public TextView tvDealer;
        public TextView tvReviewer;

        public ImageView ivReport;
        public ImageView ivDealed;

    }
}
