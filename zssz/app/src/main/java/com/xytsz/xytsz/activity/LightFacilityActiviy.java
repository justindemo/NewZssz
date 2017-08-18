package com.xytsz.xytsz.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;


import com.xytsz.xytsz.R;
import com.xytsz.xytsz.adapter.LightFragmentAdapter;
import com.xytsz.xytsz.fragment.LightSightFragment;


import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by admin on 2017/7/10.
 *
 */
public class LightFacilityActiviy extends AppCompatActivity {


    @Bind(R.id.facility_tab)
    TabLayout facilityTab;
    @Bind(R.id.facility_viewpager)
    ViewPager facilityViewpager;
    private ArrayList<Fragment> fragments;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lightfacility);
        ButterKnife.bind(this);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("照明设施");
        }


        initData();



    }

    private void initData() {
        List<String> titles = new ArrayList<>();
        titles.clear();
        titles.add("景观照明");
        titles.add("市政照明");
        titles.add("园林照明");
        titles.add("道路照明");
        titles.add("桥梁照明");

        for (int i = 0; i < titles.size(); i++) {

            facilityTab.addTab(facilityTab.newTab().setText(titles.get(i)));
        }



        fragments = new ArrayList<>();
        fragments.clear();
        fragments.add(new LightSightFragment());
        fragments.add(new LightSightFragment());
        fragments.add(new LightSightFragment());
        fragments.add(new LightSightFragment());
        fragments.add(new LightSightFragment());

        //添加viewpager的adapter
        LightFragmentAdapter lightFragmentAdapter = new LightFragmentAdapter(getSupportFragmentManager(), fragments, titles);
        facilityViewpager.setAdapter(lightFragmentAdapter);

        //让标签 跟着viewpager 滑动
        facilityTab.setupWithViewPager(facilityViewpager);

        facilityTab.setTabsFromPagerAdapter(lightFragmentAdapter);


    }


    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}
