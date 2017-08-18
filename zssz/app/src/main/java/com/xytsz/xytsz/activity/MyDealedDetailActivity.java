package com.xytsz.xytsz.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
 */
public class MyDealedDetailActivity extends AppCompatActivity implements View.OnClickListener {


    @Bind(R.id.tv_mycheck_name)
    TextView tvMycheckName;
    @Bind(R.id.tv_mycheck_reporter)
    TextView tvMycheckReporter;
    @Bind(R.id.tv_mycheck_diseasename)
    TextView tvMycheckDiseasename;
    @Bind(R.id.tv_mycheck_grade)
    TextView tvMycheckGrade;
    @Bind(R.id.tv_mycheck_dealtype)
    TextView tvMycheckDealtype;
    @Bind(R.id.tv_mycheck_fatype)
    TextView tvMycheckFatype;
    @Bind(R.id.tv_mycheck_pbtype)
    TextView tvMycheckPbtype;
    @Bind(R.id.tv_mycheck_reporteplace)
    TextView tvMycheckReporteplace;
    @Bind(R.id.tv_mycheck_reviewer)
    TextView tvMycheckReviewer;
    @Bind(R.id.tv_mycheck_dealer)
    TextView tvMycheckDealer;
    @Bind(R.id.tv_mycheck_reportetime)
    TextView tvMycheckReportetime;
    @Bind(R.id.tv_mycheck_reviewtime)
    TextView tvMycheckReviewtime;
    @Bind(R.id.tv_mycheck_requesttime)
    TextView tvMycheckRequesttime;
    @Bind(R.id.tv_mycheck_resulttime)
    TextView tvMycheckResulttime;
    @Bind(R.id.tv_mycheck_decs_title)
    TextView tvMycheckDecsTitle;
    @Bind(R.id.tv_mycheck_decs)
    TextView tvMycheckDecs;
    @Bind(R.id.tv_mycheck_road)
    TextView tvMycheckRoad;
    @Bind(R.id.ll_mycheck_detail_title)
    LinearLayout llMycheckDetailTitle;
    @Bind(R.id.iv_mycheck_detail_report)
    ImageView ivMycheckDetailReport;
    @Bind(R.id.iv_mycheck_detail_dealed)
    ImageView ivMycheckDetailDealed;
    @Bind(R.id.ll_mycheck_detail_photo)
    LinearLayout llMycheckDetailPhoto;
    private ForMyDis detail;
    private List<ImageUrl> imageUrlReport;
    private List<ImageUrl> imageUrlPost;
    private int id;
    //实际完成人
    private int acid;
    private int reviewid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (getIntent() != null) {

            detail = (ForMyDis) getIntent().getSerializableExtra("detail");

            imageUrlReport = (List<ImageUrl>) getIntent().getSerializableExtra("imageUrlreport");
            imageUrlPost = (List<ImageUrl>) getIntent().getSerializableExtra("imageUrlpost");

        }

        setContentView(R.layout.activity_mypostdetail);
        ButterKnife.bind(this);


        initData();


    }

    private void initData() {
        String upload_person_id = detail.getUpload_Person_ID() + "";
        //detail.getr
        String actualCompletion_person_id = detail.getActualCompletion_Person_ID() + "";
        String issued_person_id = detail.getIssued_Person_ID() + "";
        //通过上报人的ID 拿到上报人的名字
        //获取到所有人的列表 把对应的 id 找出名字
        List<String> personNamelist = SpUtils.getStrListValue(getApplicationContext(), GlobalContanstant.PERSONNAMELIST);
        List<String> personIDlist = SpUtils.getStrListValue(getApplicationContext(), GlobalContanstant.PERSONIDLIST);

        for (int i = 0; i < personIDlist.size(); i++) {
            if (upload_person_id.equals(personIDlist.get(i))) {
                id = i;
            }
            if (actualCompletion_person_id.equals(personIDlist.get(i))) {
                acid = i;
            }
            if (issued_person_id.equals(personIDlist.get(i))) {
                reviewid = i;
            }
        }

        String userName = personNamelist.get(id);
        String acName = personNamelist.get(acid);
        String reviewName = personNamelist.get(reviewid);


        tvMycheckReporter.setText(userName);
        tvMycheckDealer.setText(acName);

        //没有审核人员的数据
        tvMycheckReviewer.setText(reviewName);


        int disposalLevel_id = detail.getDisposalLevel_ID() - 1;
        int level = detail.getLevel();
        tvMycheckDiseasename.setText(Data.pbname[level]);
        tvMycheckGrade.setText(Data.grades[disposalLevel_id]);
        tvMycheckDealtype.setText(detail.getDealType_Name());
        tvMycheckFatype.setText(detail.getFacilityType_Name());


        tvMycheckPbtype.setText(detail.getDiseaseType_Name());

        tvMycheckReporteplace.setText(detail.getStreetAddress_Name());


        String uploadTime = detail.getUploadTime();
        tvMycheckReportetime.setText(uploadTime);
        String reviewedTime = detail.getReviewedTime();
        tvMycheckReviewtime.setText(reviewedTime);

        String actualCompletionInfo = detail.getActualCompletionInfo();
        tvMycheckDecs.setText(actualCompletionInfo);


        String requestTime = detail.getRequirementsCompleteTime();
        tvMycheckRequesttime.setText(requestTime);

        String actualCompletionTime = detail.getActualCompletionTime();
        tvMycheckResulttime.setText(actualCompletionTime);

        if (imageUrlReport != null) {

            String imgurl = imageUrlReport.get(0).getImgurl();
            Glide.with(getApplicationContext()).load(imgurl).into(ivMycheckDetailReport);
        }
        if (imageUrlPost != null) {

            String imgurlpost = imageUrlPost.get(0).getImgurl();
            Glide.with(getApplicationContext()).load(imgurlpost).into(ivMycheckDetailDealed);
        }
        ivMycheckDetailDealed.setOnClickListener(this);
        ivMycheckDetailReport.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.iv_mycheck_detail_report:
                Intent intent = new Intent(v.getContext(), CheckReportBigPhotoActivity.class);
                intent.putExtra("imageurl", (Serializable) imageUrlReport);
                v.getContext().startActivity(intent);
                break;
            case R.id.iv_mycheck_detail_dealed:
                Intent intent1 = new Intent(v.getContext(), CheckPostBigPhotoActivity.class);
                intent1.putExtra("imageUrlpost", (Serializable) imageUrlPost);
                v.getContext().startActivity(intent1);
                break;
        }
    }
}

