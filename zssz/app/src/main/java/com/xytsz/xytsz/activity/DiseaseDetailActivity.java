package com.xytsz.xytsz.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xytsz.xytsz.R;
import com.xytsz.xytsz.bean.ImageUrl;
import com.xytsz.xytsz.bean.Review;
import com.xytsz.xytsz.global.Data;
import com.xytsz.xytsz.global.GlobalContanstant;
import com.xytsz.xytsz.util.SpUtils;

import java.io.Serializable;
import java.util.List;

/**
 * Created by admin on 2017/3/2.
 * 地图上的显示详细信息
 */
public class DiseaseDetailActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mbtBack;
    private TextView mtvReporter;
    private TextView mtvDisasename;
    private TextView mtvGrade;
    private TextView mtvFatype;
    private TextView mtvDealtype;
    private TextView mtvPbtype;
    private TextView mtvReporteplace;
    private TextView mtvFaname;
    private TextView mtvFasize;
    private TextView mtvReportetime;
    private TextView mtvRequesttime;
    private ImageView mivPhoto1;
    private ImageView mivPhoto2;
    private ImageView mivPhoto3;
    private Review.ReviewRoad.ReviewRoadDetail detail;
    private List<ImageUrl> imageUrls;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent() != null) {
            detail = (Review.ReviewRoad.ReviewRoadDetail) getIntent().getSerializableExtra("detail");

            imageUrls = (List<ImageUrl>) getIntent().getSerializableExtra("imageUrls");

        }
        setContentView(R.layout.activity_diseasedetail);
        initView();
        initData();
    }

    private void initView() {
        mbtBack = (Button) findViewById(R.id.bt_detail_back);
        mtvReporter = (TextView) findViewById(R.id.tv_detail_reporter);
        mtvDisasename = (TextView) findViewById(R.id.tv_detail_diseasename);
        mtvGrade = (TextView) findViewById(R.id.tv_detail_grade);
        mtvDealtype = (TextView) findViewById(R.id.tv_detail_dealtype);
        mtvFatype = (TextView) findViewById(R.id.tv_detail_fatype);
        mtvPbtype = (TextView) findViewById(R.id.tv_detail_pbtype);
        mtvReporteplace = (TextView) findViewById(R.id.tv_detail_reporteplace);
        mtvFaname = (TextView) findViewById(R.id.tv_detail_faname);
        mtvFasize = (TextView) findViewById(R.id.tv_detail_fasize);
        mtvReportetime = (TextView) findViewById(R.id.tv_detail_reportetime);
        mtvRequesttime = (TextView) findViewById(R.id.tv_detail_requesttime);
        mivPhoto1 = (ImageView) findViewById(R.id.iv_detail_photo1);
        mivPhoto2 = (ImageView) findViewById(R.id.iv_detail_photo2);
        mivPhoto3 = (ImageView) findViewById(R.id.iv_detail_photo3);
    }


    private  int id;
    private void initData() {
        //赋值

        String upload_person_id = detail.getUpload_Person_ID()+"";


        //通过上报人的ID 拿到上报人的名字
        //获取到所有人的列表 把对应的 id 找出名字
        List<String> personNamelist = SpUtils.getStrListValue(getApplicationContext(), GlobalContanstant.PERSONNAMELIST);
        List<String> personIDlist = SpUtils.getStrListValue(getApplicationContext(), GlobalContanstant.PERSONIDLIST);

        for (int i = 0; i < personIDlist.size(); i++) {
            if (upload_person_id.equals(personIDlist.get(i))){
                id = i;
            }
        }

        String userName = personNamelist.get(id);
        mtvReporter.setText(userName);
        int disposalLevel_id = detail.getDisposalLevel_ID() - 1;
        int level = detail.getLevel();
        mtvDisasename.setText(Data.pbname[level]);
        mtvGrade.setText(Data.grades[disposalLevel_id]);

        mtvFatype.setText(detail.getFacilityType_Name());
        mtvDealtype.setText(detail.getDealType_Name());

        mtvPbtype.setText(detail.getDiseaseType_Name());


        mtvReporteplace.setText(detail.getStreetAddress_Name());


        mtvFaname.setText(detail.getFacilityName_Name());



        mtvFasize.setText(detail.getFacilitySpecifications_Name());

        String uploadTime = detail.getUploadTime();
        mtvReportetime.setText(uploadTime);
        mtvRequesttime.setText(detail.getRequirementsCompleteTime());

        //点击返回的时候
        mbtBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if (imageUrls.size() != 0) {
            if (imageUrls.size() == 1) {
                mivPhoto1.setVisibility(View.VISIBLE);
                Glide.with(getApplicationContext()).load(imageUrls.get(0).getImgurl()).fitCenter().into(mivPhoto1);
                mivPhoto2.setVisibility(View.INVISIBLE);
                mivPhoto3.setVisibility(View.INVISIBLE);
            } else if (imageUrls.size() == 2) {
                mivPhoto2.setVisibility(View.VISIBLE);
                Glide.with(getApplicationContext()).load(imageUrls.get(0).getImgurl()).fitCenter().into(mivPhoto1);
                Glide.with(getApplicationContext()).load(imageUrls.get(1).getImgurl()).fitCenter().into(mivPhoto2);
                mivPhoto3.setVisibility(View.INVISIBLE);
            } else if (imageUrls.size() == 3) {
                mivPhoto1.setVisibility(View.VISIBLE);
                mivPhoto2.setVisibility(View.VISIBLE);
                mivPhoto3.setVisibility(View.VISIBLE);
                Glide.with(getApplicationContext()).load(imageUrls.get(0).getImgurl()).fitCenter().into(mivPhoto1);
                Glide.with(getApplicationContext()).load(imageUrls.get(1).getImgurl()).fitCenter().into(mivPhoto2);
                Glide.with(getApplicationContext()).load(imageUrls.get(2).getImgurl()).fitCenter().into(mivPhoto3);
            }

        }
        mivPhoto1.setOnClickListener(this);
        mivPhoto2.setOnClickListener(this);
        mivPhoto3.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

        Intent intent = new Intent(DiseaseDetailActivity.this,BigPictureActivity.class);
        intent.putExtra("imageUrls",(Serializable)imageUrls);
        startActivity(intent);
    }
}
