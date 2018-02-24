package com.xytsz.xytsz.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xytsz.xytsz.R;
import com.xytsz.xytsz.bean.AudioUrl;
import com.xytsz.xytsz.bean.ImageUrl;
import com.xytsz.xytsz.bean.Review;
import com.xytsz.xytsz.global.Data;
import com.xytsz.xytsz.global.GlobalContanstant;
import com.xytsz.xytsz.util.SoundUtil;
import com.xytsz.xytsz.util.SpUtils;

import java.io.Serializable;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by admin on 2017/3/2.
 * 地图上的显示详细信息
 */
public class DiseaseDetailActivity extends AppCompatActivity implements View.OnClickListener {

    @Bind(R.id.tv_detail_diseasedes)
    TextView tvDetailDiseasedes;
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
    private AudioUrl audioUrl;
    private TextView mtvProblemLoca;
    private TextView mtvProblemAudio;
    private SoundUtil soundUtil;
    private LinearLayout mllVideo;
    private ImageView mivPlay;
    private static final int SUCCESS = 500;
    private String videopath;

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SUCCESS:
                    videopath = (String) msg.obj;
                    if (videopath.isEmpty() || videopath == null || videopath.equals("false")) {
                        mllVideo.setVisibility(View.GONE);
                    } else {
                        mllVideo.setVisibility(View.VISIBLE);
                    }
                    break;
            }
        }
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent() != null) {
            detail = (Review.ReviewRoad.ReviewRoadDetail) getIntent().getSerializableExtra("detail");

            imageUrls = (List<ImageUrl>) getIntent().getSerializableExtra("imageUrls");

            audioUrl = (AudioUrl) getIntent().getSerializableExtra("audioUrl");

        }
        setContentView(R.layout.activity_diseasedetail);
        ButterKnife.bind(this);
        initAcitionbar();
        initView();
        //请求是否有视屏
        new Thread() {
            @Override
            public void run() {
                try {
                    String videopath = SendRoadDetailActivity.getVideo(detail.getTaskNumber());
                    Message message = Message.obtain();
                    message.what = SUCCESS;
                    message.obj = videopath;
                    handler.sendMessage(message);
                } catch (Exception e) {

                }
            }
        }.start();

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

        mtvProblemLoca = (TextView) findViewById(R.id.tv_detail_problem_loca);
        mtvProblemAudio = (TextView) findViewById(R.id.tv_detail_problem_audio);


        mllVideo = (LinearLayout) findViewById(R.id.ll_video);
        mivPlay = (ImageView) findViewById(R.id.iv_play_video);


    }


    private int id;

    private void initData() {
        //赋值

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
        mtvProblemLoca.setText(detail.getAddressDescription());
        tvDetailDiseasedes.setText(detail.getDiseaseDescription());

        //点击返回的时候
        mbtBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if (imageUrls.size() != 0) {
            if (imageUrls.size() == 1) {
                Glide.with(getApplicationContext()).load(imageUrls.get(0).getImgurl()).into(mivPhoto1);
                mivPhoto2.setVisibility(View.INVISIBLE);
                mivPhoto3.setVisibility(View.INVISIBLE);
            } else if (imageUrls.size() == 2) {
                Glide.with(getApplicationContext()).load(imageUrls.get(0).getImgurl()).into(mivPhoto1);
                Glide.with(getApplicationContext()).load(imageUrls.get(1).getImgurl()).into(mivPhoto2);
                mivPhoto3.setVisibility(View.INVISIBLE);
            } else if (imageUrls.size() == 3) {
                Glide.with(getApplicationContext()).load(imageUrls.get(0).getImgurl()).into(mivPhoto1);
                Glide.with(getApplicationContext()).load(imageUrls.get(1).getImgurl()).into(mivPhoto2);
                Glide.with(getApplicationContext()).load(imageUrls.get(2).getImgurl()).into(mivPhoto3);
            }
        } else {

            mivPhoto1.setVisibility(View.VISIBLE);
            mivPhoto2.setVisibility(View.INVISIBLE);
            mivPhoto3.setVisibility(View.INVISIBLE);
            Glide.with(getApplicationContext()).load(R.mipmap.prepost).into(mivPhoto1);

        }
        mivPhoto1.setOnClickListener(this);
        mivPhoto2.setOnClickListener(this);
        mivPhoto3.setOnClickListener(this);


        if (detail.getAddressDescription().isEmpty()) {
            if (audioUrl != null) {
                if (audioUrl.getAudioUrl() != null) {
                    if (!audioUrl.getAudioUrl().equals("false")) {
                        if (!audioUrl.getTime().isEmpty()) {
                            mtvProblemLoca.setVisibility(View.GONE);
                            mtvProblemAudio.setVisibility(View.VISIBLE);
                            soundUtil = new SoundUtil();
                            mtvProblemAudio.setText(audioUrl.getTime());

                            mtvProblemAudio.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    Drawable drawable = getResources().getDrawable(R.mipmap.pause);
                                    final Drawable drawableRight = getResources().getDrawable(R.mipmap.play);

                                    mtvProblemAudio.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);
                                    //soundUtil.play(audioUrl);

                                    soundUtil.setOnFinishListener(new SoundUtil.OnFinishListener() {
                                        @Override
                                        public void onFinish() {
                                            mtvProblemAudio.setCompoundDrawablesWithIntrinsicBounds(null, null, drawableRight, null);
                                        }

                                        @Override
                                        public void onError() {

                                        }
                                    });


                                    soundUtil.play(audioUrl.getAudioUrl());
                                }
                            });
                        }
                    } else {
                        mtvProblemLoca.setVisibility(View.VISIBLE);
                        mtvProblemLoca.setText(detail.getAddressDescription());
                        mtvProblemAudio.setVisibility(View.GONE);
                    }
                }
            } else {
                mtvProblemLoca.setVisibility(View.VISIBLE);

                mtvProblemAudio.setVisibility(View.GONE);
            }
        } else {
            mtvProblemLoca.setVisibility(View.VISIBLE);
            mtvProblemLoca.setText(detail.getAddressDescription());
            mtvProblemAudio.setVisibility(View.GONE);
        }

        mivPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(DiseaseDetailActivity.this, PlayVideoActivity.class);
                intent1.putExtra("videoPath", videopath);
                startActivity(intent1);
            }
        });


    }


    @Override
    public void onClick(View v) {

        Intent intent = new Intent(DiseaseDetailActivity.this, BigPictureActivity.class);
        intent.putExtra("imageUrls", (Serializable) imageUrls);
        startActivity(intent);
    }


    private void initAcitionbar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setTitle(R.string.problem_detail);
        }
    }


    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}
