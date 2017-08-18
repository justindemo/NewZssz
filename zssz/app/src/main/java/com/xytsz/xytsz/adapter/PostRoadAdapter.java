package com.xytsz.xytsz.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.media.MediaMetadata;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xytsz.xytsz.R;
import com.xytsz.xytsz.activity.PostBigPhotoActivity;
import com.xytsz.xytsz.activity.SendBigPhotoActivity;
import com.xytsz.xytsz.activity.UnCheckActivity;
import com.xytsz.xytsz.bean.ImageUrl;
import com.xytsz.xytsz.bean.Review;
import com.xytsz.xytsz.global.Data;
import com.xytsz.xytsz.global.GlobalContanstant;
import com.xytsz.xytsz.util.IntentUtil;
import com.xytsz.xytsz.util.SpUtils;

import java.util.List;

/**
 * Created by admin on 2017/2/22.
 *
 */
public class PostRoadAdapter extends BaseAdapter {


    private Review.ReviewRoad list;
    private List<List<ImageUrl>> imageUrlLists;
    private String imgurl;

    public PostRoadAdapter(Review.ReviewRoad list, List<List<ImageUrl>> imageUrlLists) {

        this.list = list;
        this.imageUrlLists = imageUrlLists;
    }

    @Override
    public int getCount() {
        return list.getList().size();
    }

    @Override
    public Object getItem(int position) {
        return list.getList().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(parent.getContext(), R.layout.item_postroad, null);
            holder.Vname = (TextView) convertView.findViewById(R.id.tv_send_Vname);
            holder.Pname = (TextView) convertView.findViewById(R.id.tv_send_Pname);
            holder.date = (TextView) convertView.findViewById(R.id.tv_send_date);
            holder.sendIcon = (ImageView) convertView.findViewById(R.id.iv_send_icon);
            holder.btPost = (Button) convertView.findViewById(R.id.bt_post_send);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        //
        final Review.ReviewRoad.ReviewRoadDetail reviewRoadDetail = list.getList().get(position);
        //上报人的ID
        String upload_person_id = reviewRoadDetail.getUpload_Person_ID() +"";



        //通过上报人的ID 拿到上报人的名字
        //获取到所有人的列表 把对应的 id 找出名字
        List<String> personNamelist = SpUtils.getStrListValue(parent.getContext(), GlobalContanstant.PERSONNAMELIST);
        List<String> personIDlist = SpUtils.getStrListValue(parent.getContext(), GlobalContanstant.PERSONIDLIST);

        for (int i = 0; i < personIDlist.size(); i++) {
            if (upload_person_id.equals(personIDlist.get(i))){
                id = i;
            }
        }

        String userName = personNamelist.get(id);

        String uploadTime = reviewRoadDetail.getUploadTime();
        int level = reviewRoadDetail.getLevel();
        //String userName = SpUtils.getString(parent.getContext(), GlobalContanstant.USERNAME);
        holder.Pname.setText(userName);
        holder.Vname.setText(Data.pbname[level]);
        holder.date.setText(uploadTime);
        holder.btPost.setTag(position);

        holder.btPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = (int) v.getTag();
                Intent intent  = new Intent(v.getContext(),UnCheckActivity.class);
                intent.putExtra("reviewRoadDetail",reviewRoadDetail);
                v.getContext().startActivity(intent);
            }
        });

        if (imageUrlLists.size() != 0) {
            urlList = imageUrlLists.get(position);
            //显示的第一张图片
            ImageUrl imageUrl = urlList.get(0);
            imgurl = imageUrl.getImgurl();
            Glide.with(parent.getContext()).load(imgurl).into(holder.sendIcon);
            holder.sendIcon.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(),SendBigPhotoActivity.class);
                    intent.putExtra("imageurl",imageUrlLists.get(position).get(0).getImgurl());
                    v.getContext().startActivity(intent);
                }
            });
        }

        return convertView;
    }

    private int id;

    private List<ImageUrl> urlList;
    static class ViewHolder {
        public TextView Vname;
        public TextView date;
        public TextView Pname;
        public ImageView sendIcon;
        public Button btPost;
    }

}
