package com.xytsz.xytsz.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.xytsz.xytsz.bean.ImageUrl;
import com.xytsz.xytsz.fragment.ImageFragment;

import java.util.List;

/**
 * Created by admin on 2017/3/14.
 *
 */

public class ImageViewPagerAdapter extends FragmentPagerAdapter {


    private List<ImageUrl> list;

    public ImageViewPagerAdapter(FragmentManager fm, List<ImageUrl> list) {
        super(fm);

        this.list = list;
    }



    @Override
    public Fragment getItem(int position) {
        ImageUrl imageUrl = list.get(position);
        String imgurl = imageUrl.getImgurl();
        ImageFragment fragment = ImageFragment.newInstance(imgurl);
        return fragment;
    }

    @Override
    public int getCount() {
        return list.size();
    }
}
