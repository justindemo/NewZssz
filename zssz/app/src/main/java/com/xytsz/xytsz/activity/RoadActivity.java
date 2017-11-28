package com.xytsz.xytsz.activity;


import android.content.Intent;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xytsz.xytsz.MyApplication;
import com.xytsz.xytsz.R;
import com.xytsz.xytsz.adapter.ReviewAdapter;
import com.xytsz.xytsz.adapter.RoadAdapter;
import com.xytsz.xytsz.bean.AudioUrl;
import com.xytsz.xytsz.bean.ImageUrl;
import com.xytsz.xytsz.bean.Review;
import com.xytsz.xytsz.global.GlobalContanstant;

import com.xytsz.xytsz.net.NetUrl;
import com.xytsz.xytsz.util.JsonUtil;
import com.xytsz.xytsz.util.SpUtils;
import com.xytsz.xytsz.util.ToastUtil;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2017/1/11.
 * 审核页面的二级道路页面
 */
public class RoadActivity extends AppCompatActivity {

    private static final int ISPASS = 100001;
    private static final int ISFAIL = 100002;
    private static final int NOONE = 100003;

    private ListView mLv;
        private Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case NOONE:
                        ToastUtil.shortToast(getApplicationContext(),"已审核完毕");
                        mProgressBar.setVisibility(View.GONE);
                        break;
                    case GlobalContanstant.SENDFAIL:
                        mProgressBar.setVisibility(View.GONE);
                        ToastUtil.shortToast(getApplicationContext(),"未数据获取,请稍后");
                        break;
                    case ISPASS:
                        int lastposition = msg.getData().getInt("position");

                        String isPass = msg.getData().getString("isPass");
                        if (isPass.equals("true")){
                            ToastUtil.shortToast(getApplicationContext(),"审核通过");
                            //把位置传递过去到Review上  把当前的position 删除掉
                            Intent intent = getIntent();
                            intent.putExtra("lastposition",lastposition);
                            intent.putExtra("position",position);
                            setResult(500,intent);
                            //finish();
                        }

                        break;
                    case ISFAIL:
                        int failposition = msg.getData().getInt("position");

                        String isfail = msg.getData().getString("isfail");

                        if (isfail.equals("true")){
                            ToastUtil.shortToast(getApplicationContext(),"未通过审核");
                            //把位置传递过去到Review上  把当前的position 删除掉
                            Intent intent = getIntent();
                            intent.putExtra("failposition",failposition);
                            intent.putExtra("position",position);
                            setResult(600,intent);
                        }

                        break;


                }
            }
        };
    private List<List<ImageUrl>> imageUrlLists = new ArrayList<>();
    private List<AudioUrl> audioUrls = new ArrayList<>();
    private int personId;
    private int position;
    private ProgressBar mProgressBar;
    private RoadAdapter roadAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getIntent() != null){
            Intent intent = getIntent();
            //这个position 是review.getreviewRoadList
            position = intent.getIntExtra("position",-1);
        }


        setContentView(R.layout.activity_road);

        initAcitionbar();
        personId = SpUtils.getInt(getApplicationContext(), GlobalContanstant.PERSONID);
        initView();
        initData();
    }

    private void initView() {
        mLv = (ListView) findViewById(R.id.road_lv);
        mProgressBar = (ProgressBar) findViewById(R.id.review_progressbar);
    }
    //从服务器获取当前道路的信息  所有

    private void initData() {


        mProgressBar.setVisibility(View.VISIBLE);
        //获取的数据当作 list传入构造中   ***应该传的是bean
        new Thread() {
            @Override
            public void run() {

                try {

                    String serviceData = ReviewActivity.getServiceData(GlobalContanstant.GETREVIEW);


                    if (serviceData != null) {

                        Review review = JsonUtil.jsonToBean(serviceData, Review.class);


                        Review.ReviewRoad reviewRoad = review.getReviewRoadList().get(position);

                        List<Review.ReviewRoad.ReviewRoadDetail> list = reviewRoad.getList();

                        if (list.size() == 0) {
                            Message message = Message.obtain();
                            message.what = NOONE;
                            handler.sendMessage(message);
                        } else {
                            audioUrls.clear();
                            //遍历list
                            for (Review.ReviewRoad.ReviewRoadDetail detail : list) {
                                String taskNumber = detail.getTaskNumber();

                                /**
                                 * 获取到图片的URl
                                 */
                                String json = MyApplication.getAllImagUrl(taskNumber, GlobalContanstant.GETREVIEW);
                                if (json != null) {

                                    //String list = new JSONObject(json).getJSONArray("").toString();
                                    List<ImageUrl> imageUrlList = new Gson().fromJson(json, new TypeToken<List<ImageUrl>>() {
                                    }.getType());

                                    imageUrlLists.add(imageUrlList);
                                }


                                String audioUrljson = getAudio(taskNumber);

                                if (audioUrljson != null){
                                    AudioUrl audioUrl = JsonUtil.jsonToBean(audioUrljson, AudioUrl.class);
                                    audioUrls.add(audioUrl);
                                }



                            }

                            roadAdapter = new RoadAdapter(handler, reviewRoad, imageUrlLists, audioUrls);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mLv.setAdapter(roadAdapter);
                                    mProgressBar.setVisibility(View.GONE);
                                }
                            });


                        }
                    }
                } catch (Exception e) {
                    Message message = Message.obtain();
                    message.what = GlobalContanstant.SENDFAIL;
                    handler.sendMessage(message);
                }
            }
        }.start();


    }



    public static String getAudio(String taskNumber) throws Exception {
        SoapObject soapObject = new SoapObject(NetUrl.nameSpace, NetUrl.getAudioMethodName);
        soapObject.addProperty("TaskNumber",taskNumber);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
        envelope.setOutputSoapObject(soapObject);
        envelope.dotNet = true;
        envelope.bodyOut = soapObject;

        HttpTransportSE httpTransportSE = new HttpTransportSE(NetUrl.SERVERURL);

        httpTransportSE.call(NetUrl.getAudio_SOAP_ACTION, envelope);
        SoapObject object = (SoapObject) envelope.bodyIn;
        String result = object.getProperty(0).toString();
        return result;

    }


    private void initAcitionbar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setTitle(R.string.review);
        }
    }


    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

}