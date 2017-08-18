package com.xytsz.xytsz.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xytsz.xytsz.R;
import com.xytsz.xytsz.adapter.MayorPhoneAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by admin on 2017/7/28.
 * 市长热线表
 */
public class MayorPhoneAcitvity extends AppCompatActivity {

    @Bind(R.id.mayorphone_recyclerview)
    RecyclerView mayorphoneRecyclerview;
    private List<List<String>> allList = new ArrayList<>();
    private List<String> cityList = new ArrayList<>();
    private List<String> mayorphoneList = new ArrayList<>();
    private List<String> mayorList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mayorphone);
        ButterKnife.bind(this);

        cityList.clear();
        mayorList.clear();
        mayorphoneList.clear();
        for (int i = 0; i < 10; i++) {

            cityList.add("北京市");
            mayorList.add("陈吉宁");
            mayorphoneList.add("13121211234");
        }


        allList.add(cityList);
        allList.add(mayorList);
        allList.add(mayorphoneList);


        LinearLayoutManager manager = new LinearLayoutManager(this);
        mayorphoneRecyclerview.setLayoutManager(manager);
        MayorPhoneAdapter adapter = new MayorPhoneAdapter(cityList,allList);

        mayorphoneRecyclerview.setAdapter(adapter);

        adapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);

        adapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //打电话
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + mayorphoneList.get(position).toString()));
                if (ActivityCompat.checkSelfPermission(MayorPhoneAcitvity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                startActivity(intent);
            }
        });
    }
}
