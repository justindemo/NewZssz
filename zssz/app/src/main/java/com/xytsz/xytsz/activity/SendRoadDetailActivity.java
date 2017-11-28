package com.xytsz.xytsz.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
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
import com.xytsz.xytsz.net.NetUrl;
import com.xytsz.xytsz.util.SoundUtil;
import com.xytsz.xytsz.util.SpUtils;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.Serializable;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by admin on 2017/6/16.
 * 病害详情单
 */
public class SendRoadDetailActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int SUCCESS = 500;
    @Bind(R.id.tv_send_detail_name)
    TextView tvSendDetailName;
    @Bind(R.id.tv_send_detail_reporter)
    TextView tvSendDetailReporter;
    @Bind(R.id.tv_send_detail_diseasename)
    TextView tvSendDetailDiseasename;
    @Bind(R.id.tv_send_detail_grade)
    TextView tvSendDetailGrade;
    @Bind(R.id.tv_send_detail_dealtype)
    TextView tvSendDetailDealtype;
    @Bind(R.id.tv_send_detail_fatype)
    TextView tvSendDetailFatype;
    @Bind(R.id.tv_send_detail_pbtype)
    TextView tvSendDetailPbtype;
    @Bind(R.id.tv_send_detail_reporteplace)
    TextView tvSendDetailReporteplace;
    @Bind(R.id.tv_send_detail_faname)
    TextView tvSendDetailFaname;

    @Bind(R.id.tv_send_detail_reportetime)
    TextView tvSendDetailReportetime;
    @Bind(R.id.tv_send_detail_address)
    TextView tvSendDetailAddress;
    @Bind(R.id.iv_send_detail_photo1)
    ImageView ivSendDetailPhoto1;
    @Bind(R.id.iv_send_detail_photo2)
    ImageView ivSendDetailPhoto2;
    @Bind(R.id.iv_send_detail_photo3)
    ImageView ivSendDetailPhoto3;
    @Bind(R.id.ll_iv)
    LinearLayout llIv;
    @Bind(R.id.bt_send_detail_back)
    Button btSendDetailBack;
    @Bind(R.id.tv_send_detail_loca)
    TextView tvSendDetailLoca;
    @Bind(R.id.tv_send_problem_audio)
    TextView tvSendProblemAudio;
    @Bind(R.id.tv_send_detail_address1)
    FrameLayout tvSendDetailAddress1;
    @Bind(R.id.tv_send_detail_reporteiamg_title)
    TextView tvSendDetailReporteiamgTitle;
    @Bind(R.id.iv_play_video)
    ImageView ivPlayVideo;
    @Bind(R.id.ll_video)
    LinearLayout llVideo;
    @Bind(R.id.tv_send_detail_diseasedes)
    TextView tvSendDetailDiseasedes;

    private Review.ReviewRoad.ReviewRoadDetail detail;
    private List<ImageUrl> imageUrls;
    private int id;
    private AudioUrl audioUrl;
    private SoundUtil soundUtil;
    private String videopath;
    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SUCCESS:
                    videopath = (String) msg.obj;
                    if (videopath.isEmpty() || videopath == null || videopath.equals("false")) {
                        llVideo.setVisibility(View.GONE);
                    } else {
                        llVideo.setVisibility(View.VISIBLE);
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

        setContentView(R.layout.activity_sendroaddetail);
        ButterKnife.bind(this);

        initAcitionbar();
        //请求是否有视屏
        new Thread() {
            @Override
            public void run() {
                try {
                    String videopath = getVideo(detail.getTaskNumber());
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

    public static String getVideo(String taskNumber) throws Exception {
        SoapObject soapObject = new SoapObject(NetUrl.nameSpace, NetUrl.getVideoMethodName);
        //传递的参数
        soapObject.addProperty("TaskNumber", taskNumber);

        Log.i("soapo", soapObject.toString());
        //设置访问地址 和 超时时间
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
        envelope.bodyOut = soapObject;
        envelope.dotNet = true;
        envelope.setOutputSoapObject(soapObject);


        HttpTransportSE httpTranstation = new HttpTransportSE(NetUrl.SERVERURL);
        //链接后执行的回调
        httpTranstation.call(null, envelope);
        SoapObject object = (SoapObject) envelope.bodyIn;

        String result = object.getProperty(0).toString();
        return result;

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
        tvSendDetailReporter.setText(userName);
        int disposalLevel_id = detail.getDisposalLevel_ID() - 1;
        int level = detail.getLevel();
        tvSendDetailDiseasename.setText(Data.pbname[level]);
        tvSendDetailGrade.setText(Data.grades[disposalLevel_id]);

        tvSendDetailFatype.setText(detail.getFacilityType_Name());
        tvSendDetailDealtype.setText(detail.getDealType_Name());

        tvSendDetailPbtype.setText(detail.getDiseaseType_Name());


        tvSendDetailReporteplace.setText(detail.getStreetAddress_Name());


        tvSendDetailFaname.setText(detail.getFacilityName_Name());


        String uploadTime = detail.getUploadTime();
        tvSendDetailReportetime.setText(uploadTime);
        tvSendDetailAddress.setText(detail.getAddressDescription());

        tvSendDetailDiseasedes.setText(detail.getDiseaseDescription());

        //点击返回的时候
        btSendDetailBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if (imageUrls.size() != 0) {
            if (imageUrls.size() == 1) {
                Glide.with(getApplicationContext()).load(imageUrls.get(0).getImgurl()).into(ivSendDetailPhoto1);
                ivSendDetailPhoto2.setVisibility(View.INVISIBLE);
                ivSendDetailPhoto3.setVisibility(View.INVISIBLE);
            } else if (imageUrls.size() == 2) {
                Glide.with(getApplicationContext()).load(imageUrls.get(0).getImgurl()).into(ivSendDetailPhoto1);
                Glide.with(getApplicationContext()).load(imageUrls.get(1).getImgurl()).into(ivSendDetailPhoto2);
                ivSendDetailPhoto3.setVisibility(View.INVISIBLE);
            } else if (imageUrls.size() == 3) {
                Glide.with(getApplicationContext()).load(imageUrls.get(0).getImgurl()).into(ivSendDetailPhoto1);
                Glide.with(getApplicationContext()).load(imageUrls.get(1).getImgurl()).into(ivSendDetailPhoto2);
                Glide.with(getApplicationContext()).load(imageUrls.get(2).getImgurl()).into(ivSendDetailPhoto3);
            }
        } else {

            ivSendDetailPhoto1.setVisibility(View.VISIBLE);
            ivSendDetailPhoto1.setVisibility(View.INVISIBLE);
            ivSendDetailPhoto1.setVisibility(View.INVISIBLE);
            Glide.with(getApplicationContext()).load(R.mipmap.prepost).into(ivSendDetailPhoto1);

        }

        ivSendDetailPhoto1.setOnClickListener(this);
        ivSendDetailPhoto2.setOnClickListener(this);
        ivSendDetailPhoto3.setOnClickListener(this);


        if (detail.getAddressDescription().isEmpty()) {
            if (audioUrl != null) {
                if (!audioUrl.getAudioUrl().equals("false")) {

                    if (!audioUrl.getTime().isEmpty()) {
                        tvSendDetailAddress.setVisibility(View.GONE);
                        tvSendProblemAudio.setVisibility(View.VISIBLE);
                        soundUtil = new SoundUtil();
                        tvSendProblemAudio.setText(audioUrl.getTime());

                        tvSendProblemAudio.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Drawable drawable = getResources().getDrawable(R.mipmap.pause);
                                final Drawable drawableRight = getResources().getDrawable(R.mipmap.play);

                                tvSendProblemAudio.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);


                                soundUtil.setOnFinishListener(new SoundUtil.OnFinishListener() {
                                    @Override
                                    public void onFinish() {
                                        tvSendProblemAudio.setCompoundDrawablesWithIntrinsicBounds(null, null, drawableRight, null);
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
                    tvSendDetailAddress.setVisibility(View.VISIBLE);
                    tvSendProblemAudio.setVisibility(View.GONE);
                }
            } else {
                tvSendDetailAddress.setVisibility(View.VISIBLE);
                tvSendProblemAudio.setVisibility(View.GONE);
            }

        } else {
            tvSendDetailAddress.setVisibility(View.VISIBLE);
            tvSendProblemAudio.setVisibility(View.GONE);
        }


    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(SendRoadDetailActivity.this, BigPictureActivity.class);
        intent.putExtra("imageUrls", (Serializable) imageUrls);
        startActivity(intent);
    }

    @OnClick({R.id.tv_send_detail_loca, R.id.iv_play_video})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_send_detail_loca:
                Intent intent = new Intent(SendRoadDetailActivity.this, PositionActivity.class);
                intent.putExtra("detail", detail);
                startActivity(intent);
                break;
            case R.id.iv_play_video:
                Intent intent1 = new Intent(SendRoadDetailActivity.this, PlayVideoActivity.class);
                intent1.putExtra("videoPath", videopath);
                startActivity(intent1);
                break;
        }
    }


}
