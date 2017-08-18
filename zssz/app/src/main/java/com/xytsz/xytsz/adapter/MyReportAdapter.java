package com.xytsz.xytsz.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lidroid.xutils.db.annotation.Id;
import com.xytsz.xytsz.R;
import com.xytsz.xytsz.bean.ForMyDis;
import com.xytsz.xytsz.bean.ImageUrl;
import com.xytsz.xytsz.global.Data;
import com.xytsz.xytsz.global.GlobalContanstant;
import com.xytsz.xytsz.util.SpUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2017/5/26.
 * 我上报界面的显示数据
 */
public class MyReportAdapter extends BaseAdapter{
    private List<ForMyDis> details;
    private List<List<ImageUrl>> imageurlList;


    public MyReportAdapter(List<ForMyDis> details,List<List<ImageUrl>> imageurlList) {

        this.details = details;

        this.imageurlList = imageurlList;
    }

    @Override
    public int getCount() {

        return details.size();
    }

    @Override
    public Object getItem(int position) {
        return details.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(parent.getContext(), R.layout.item_myreport, null);
            holder.reporter = (TextView) convertView.findViewById(R.id.tv_my_reporter);
            holder.pbname = (TextView) convertView.findViewById(R.id.tv_my_pbname);
            holder.time = (TextView) convertView.findViewById(R.id.tv_my_report_time);
            holder.ivIcon = (ImageView)convertView.findViewById(R.id.iv_reporte);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        ForMyDis forMyDis = details.get(position);

        String upload_person_id = forMyDis.getUpload_Person_ID()+"";
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

        holder.reporter.setText(userName);

        holder.pbname.setText(Data.pbname[forMyDis.getLevel()]);

        holder.time.setText(forMyDis.getUploadTime());
        List<ImageUrl> imageUrlList = imageurlList.get(position);
            if (imageUrlList.size()!= 0){
                Glide.with(parent.getContext()).load(imageUrlList.get(0).getImgurl()).into(holder.ivIcon);
            }
        return convertView;
    }


    private int id;
    static class ViewHolder {
        public TextView reporter;
        public TextView pbname;
        public TextView time;
        public ImageView ivIcon;
    }
}
