package com.xytsz.xytsz.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xytsz.xytsz.R;
import com.xytsz.xytsz.bean.ForMyDis;
import com.xytsz.xytsz.bean.ImageUrl;
import com.xytsz.xytsz.global.Data;
import com.xytsz.xytsz.global.GlobalContanstant;
import com.xytsz.xytsz.util.SpUtils;

import java.io.Serializable;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by admin on 2017/5/31.
 *
 */
public class MyReporteDetailActivity extends AppCompatActivity implements View.OnClickListener {


    @Bind(R.id.tv_mydetail_name)
    TextView tvMydetailName;
    @Bind(R.id.tv_mydetail_reporter)
    TextView tvMydetailReporter;
    @Bind(R.id.tv_mydetail_diseasename)
    TextView tvMydetailDiseasename;
    @Bind(R.id.tv_mydetail_grade)
    TextView tvMydetailGrade;
    @Bind(R.id.tv_mydetail_dealtype)
    TextView tvMydetailDealtype;
    @Bind(R.id.tv_mydetail_fatype)
    TextView tvMydetailFatype;
    @Bind(R.id.tv_mydetail_pbtype)
    TextView tvMydetailPbtype;
    @Bind(R.id.tv_mydetail_reporteplace)
    TextView tvMydetailReporteplace;
    @Bind(R.id.tv_mydetail_faname)
    TextView tvMydetailFaname;
    @Bind(R.id.tv_mydetail_fasize)
    TextView tvMydetailFasize;
    @Bind(R.id.tv_mydetail_reportetime)
    TextView tvMydetailReportetime;
    @Bind(R.id.iv_detail_photo1)
    ImageView ivDetailPhoto1;
    @Bind(R.id.iv_detail_photo2)
    ImageView ivDetailPhoto2;
    @Bind(R.id.iv_detail_photo3)
    ImageView ivDetailPhoto3;
    @Bind(R.id.ll_iv)
    LinearLayout llIv;
    private ForMyDis detail;
    private List<ImageUrl> imageUrlReport;
    private int id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent() != null) {

            detail = (ForMyDis) getIntent().getSerializableExtra("detail");

            imageUrlReport = (List<ImageUrl>) getIntent().getSerializableExtra("imageUrlReport");
        }


        setContentView(R.layout.activity_myreportdetail);
        ButterKnife.bind(this);


        initData();


    }



    private void initData() {


        String upload_person_id = detail.getUpload_Person_ID() + "";

        //通过上报人的ID 拿到上报人的名字
        //获取到所有人的列表 把对应的 id 找出名字
        List<String> personNamelist = SpUtils.getStrListValue(getApplicationContext(), GlobalContanstant.PERSONNAMELIST);
        List<String> personIDlist = SpUtils.getStrListValue(getApplicationContext(), GlobalContanstant.PERSONIDLIST);

        for (int i = 0; i < personIDlist.size(); i++) {
            if (upload_person_id.equals(personIDlist.get(i))) {
                id = i;
            }
        }

        String userName = personNamelist.get(id);
        tvMydetailReporter.setText(userName);
        int disposalLevel_id = detail.getDisposalLevel_ID() - 1;
        int level = detail.getLevel();
        tvMydetailDiseasename.setText(Data.pbname[level]);
        tvMydetailGrade.setText(Data.grades[disposalLevel_id]);

        tvMydetailFatype.setText(detail.getFacilityType_Name());
        tvMydetailDealtype.setText(detail.getDealType_Name());

        tvMydetailPbtype.setText(detail.getDiseaseType_Name());


        tvMydetailReporteplace.setText(detail.getStreetAddress_Name());


        tvMydetailFaname.setText(detail.getFacilityName_Name());


        tvMydetailFasize.setText(detail.getFacilitySpecifications_Name());

        String uploadTime = detail.getUploadTime();
        tvMydetailReportetime.setText(uploadTime);
        if (imageUrlReport.size() != 0) {
            if (imageUrlReport.size() == 1) {
                ivDetailPhoto1.setVisibility(View.VISIBLE);
                Glide.with(getApplicationContext()).load(imageUrlReport.get(0).getImgurl()).into(ivDetailPhoto1);
                ivDetailPhoto2.setVisibility(View.INVISIBLE);
                ivDetailPhoto3.setVisibility(View.INVISIBLE);
            } else if (imageUrlReport.size() == 2) {
                ivDetailPhoto2.setVisibility(View.VISIBLE);
                Glide.with(getApplicationContext()).load(imageUrlReport.get(1).getImgurl()).into(ivDetailPhoto2);
                ivDetailPhoto3.setVisibility(View.INVISIBLE);
            } else if (imageUrlReport.size() == 3) {
                ivDetailPhoto1.setVisibility(View.VISIBLE);
                ivDetailPhoto2.setVisibility(View.VISIBLE);
                ivDetailPhoto3.setVisibility(View.VISIBLE);
                Glide.with(getApplicationContext()).load(imageUrlReport.get(2).getImgurl()).into(ivDetailPhoto3);
            }

        }


        ivDetailPhoto1.setOnClickListener(this);
        ivDetailPhoto2.setOnClickListener(this);
        ivDetailPhoto3.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        Intent intent = new Intent(MyReporteDetailActivity.this, BigPictureActivity.class);
        intent.putExtra("imageUrls", (Serializable) imageUrlReport);
        startActivity(intent);
    }

}
