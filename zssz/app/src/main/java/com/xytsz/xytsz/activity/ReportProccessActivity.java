package com.xytsz.xytsz.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xytsz.xytsz.R;
import com.xytsz.xytsz.bean.HistoryReport;

import java.io.Serializable;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by admin on 2017/12/11.
 * 历史举报的数据的进程
 */
public class ReportProccessActivity extends AppCompatActivity {

    @Bind(R.id.iv_reporthistory_statu)
    ImageView ivReporthistoryStatu;
    @Bind(R.id.iv_reporthistory_statu1)
    ImageView ivReporthistoryStatu1;
    @Bind(R.id.iv_reviewhistory_statu)
    ImageView ivReviewhistoryStatu;
    @Bind(R.id.iv_reviewhistory_statu1)
    ImageView ivReviewhistoryStatu1;
    @Bind(R.id.iv_dealhistory_statu)
    ImageView ivDealhistoryStatu;
    @Bind(R.id.iv_dealhistory_statu1)
    ImageView ivDealhistoryStatu1;
    @Bind(R.id.iv_checkhistory_statu)
    ImageView ivCheckhistoryStatu;
    @Bind(R.id.tv_reporthistory_time)
    TextView tvReporthistoryTime;
    @Bind(R.id.tv_reviewhistory_time)
    TextView tvReviewhistoryTime;
    @Bind(R.id.tv_dealhistory_time)
    TextView tvDealhistoryTime;
    @Bind(R.id.tv_checkhistory_time)
    TextView tvCheckhistoryTime;
    @Bind(R.id.tv_reporthistory_tasknuber)
    TextView tvReporthistoryTasknuber;
    @Bind(R.id.predealhistory_photo)
    ImageView predealhistoryPhoto;
    @Bind(R.id.dealinghistory_photo)
    ImageView dealinghistoryPhoto;
    @Bind(R.id.dealedhistory_photo)
    ImageView dealedhistoryPhoto;
    @Bind(R.id.tv_reporthistory_name)
    TextView tvReporthistoryName;
    @Bind(R.id.tv_reviewhistory_name)
    TextView tvReviewhistoryName;
    @Bind(R.id.tv_dealhistory_name)
    TextView tvDealhistoryName;
    @Bind(R.id.tv_checkhistory_name)
    TextView tvCheckhistoryName;

    private HistoryReport detail;
    private List<String> reportimageUrllist;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getIntent() != null){
            detail = (HistoryReport) getIntent().getSerializableExtra("detail");
            reportimageUrllist = (List<String>) getIntent().getSerializableExtra("reportimageUrllist");
        }
        setContentView(R.layout.activity_reportproccess);
        ButterKnife.bind(this);

        initAcitionbar();

        initData();


    }

    private void initData() {

        int phaseindication = detail.getPhaseindication();
        String uploadtime = detail.getUploadtime();
        String reviewedTime = detail.getReviewedTime();
        String dealedTime = detail.getDealedTime();
        String checkedTime = detail.getCheckedTime();
        String tasknumber = detail.getTasknumber();
        String upload_person_name = detail.getUpload_Person_name();
        String reviewed_person_name = detail.getReviewed_Person_Name();
        String dealed_person_name = detail.getDealed_Person_name();

        String checked_person_name = detail.getChecked_Person_Name();
        tvReporthistoryTasknuber.setText(tasknumber);

        switch (phaseindication) {
            //上报完
            case 0:
                ivReporthistoryStatu.setImageResource(R.mipmap.iv_complete);
                ivReporthistoryStatu1.setImageResource(R.mipmap.iv_complete_nor);
                break;
            //shenhe 完
            case 1:
                ivReporthistoryStatu.setImageResource(R.mipmap.iv_complete);
                ivReporthistoryStatu1.setImageResource(R.mipmap.iv_complete_nor);
                ivReviewhistoryStatu.setImageResource(R.mipmap.iv_complete);
                ivReviewhistoryStatu1.setImageResource(R.mipmap.iv_complete_nor);
                break;
            //post完了
            case 2:
                ivReporthistoryStatu.setImageResource(R.mipmap.iv_complete);
                ivReporthistoryStatu1.setImageResource(R.mipmap.iv_complete_nor);
                ivReviewhistoryStatu.setImageResource(R.mipmap.iv_complete);
                ivReviewhistoryStatu1.setImageResource(R.mipmap.iv_complete_nor);

                ivDealhistoryStatu.setImageResource(R.mipmap.iv_complete);
                ivDealhistoryStatu1.setImageResource(R.mipmap.iv_complete_nor);

                break;

            //验收
            case 3:
                ivReporthistoryStatu.setImageResource(R.mipmap.iv_complete);
                ivReporthistoryStatu1.setImageResource(R.mipmap.iv_complete_nor);
                ivReviewhistoryStatu.setImageResource(R.mipmap.iv_complete);
                ivReviewhistoryStatu1.setImageResource(R.mipmap.iv_complete_nor);
                ivDealhistoryStatu.setImageResource(R.mipmap.iv_complete);
                ivDealhistoryStatu1.setImageResource(R.mipmap.iv_complete_nor);
                ivCheckhistoryStatu.setImageResource(R.mipmap.iv_complete);
                break;

        }


        tvReporthistoryTime.setText(uploadtime);
//        tvReviewhistoryTime.setText(reviewedTime);
//
//        tvDealhistoryTime.setText(dealedTime);
//
//        tvCheckhistoryTime.setText(checkedTime);
//
        tvReporthistoryName.setText(upload_person_name);
//        tvReviewhistoryName.setText(reviewed_person_name);
//        tvDealhistoryName.setText(dealed_person_name);
//        tvCheckhistoryName.setText(checked_person_name);


        if (reportimageUrllist.size() != 0){
            switch (reportimageUrllist.size()){
                case 1:
                    Glide.with(getApplicationContext()).load(reportimageUrllist.get(0)).into(predealhistoryPhoto);
                    break;
                case 2:
                    Glide.with(getApplicationContext()).load(reportimageUrllist.get(0)).into(predealhistoryPhoto);
                    Glide.with(getApplicationContext()).load(reportimageUrllist.get(1)).into(dealinghistoryPhoto);

                    break;
                case 3:
                    Glide.with(getApplicationContext()).load(reportimageUrllist.get(0)).into(predealhistoryPhoto);
                    Glide.with(getApplicationContext()).load(reportimageUrllist.get(1)).into(dealinghistoryPhoto);
                    Glide.with(getApplicationContext()).load(reportimageUrllist.get(2)).into(dealedhistoryPhoto);

                    break;
            }
        }else {
            predealhistoryPhoto.setImageResource(R.mipmap.prepost);
            dealinghistoryPhoto.setImageResource(R.mipmap.prepost);
            dealedhistoryPhoto.setImageResource(R.mipmap.prepost);
        }

    }

    private void initAcitionbar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setTitle(R.string.processdetail);
        }
    }


    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}
