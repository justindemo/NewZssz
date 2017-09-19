package com.xytsz.xytsz.adapter;

import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.xytsz.xytsz.bean.ImageUrl;

import java.util.List;

/**
 * Created by admin on 2017/9/15.
 *
 *
 */
public class PersonReportPhotoShowAdapter extends PagerAdapter {

    private List<String> imageUrlList;


    public PersonReportPhotoShowAdapter(List<String> imageUrlList) {
        this.imageUrlList = imageUrlList;

    }

    @Override
    public int getCount() {
        if (imageUrlList.size() !=0) {
            return imageUrlList.size();
        }
        return 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView = new ImageView(container.getContext());
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setBackgroundColor(Color.BLACK);
        String imgurl = imageUrlList.get(position);
        Glide.with(container.getContext()).load(imgurl).into(imageView);
        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
