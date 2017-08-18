package com.xytsz.xytsz.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.xytsz.xytsz.R;
import com.xytsz.xytsz.adapter.ImageViewPagerAdapter;
import com.xytsz.xytsz.bean.ImageUrl;
import com.xytsz.xytsz.ui.HackyViewPager;

import java.util.List;

/**
 * Created by admin on 2017/3/14.
 * 大图显示
 */
public class BigPictureActivity extends AppCompatActivity{

    HackyViewPager pager ;
    ImageViewPagerAdapter adapter;
    private List<ImageUrl> imageUrls;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent() != null){
            imageUrls = (List<ImageUrl>) getIntent().getSerializableExtra("imageUrls");
        }

        setContentView(R.layout.activity_bigpicture);

        pager = (HackyViewPager) findViewById(R.id.pager);

        adapter = new ImageViewPagerAdapter(getSupportFragmentManager(), imageUrls);
        pager.setAdapter(adapter);
    }
}
