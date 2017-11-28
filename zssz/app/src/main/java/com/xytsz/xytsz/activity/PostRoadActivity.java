package com.xytsz.xytsz.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xytsz.xytsz.MyApplication;
import com.xytsz.xytsz.R;
import com.xytsz.xytsz.adapter.PostRoadAdapter;
import com.xytsz.xytsz.bean.AudioUrl;
import com.xytsz.xytsz.bean.ImageUrl;
import com.xytsz.xytsz.bean.Review;
import com.xytsz.xytsz.global.GlobalContanstant;
import com.xytsz.xytsz.util.JsonUtil;
import com.xytsz.xytsz.util.SpUtils;
import com.xytsz.xytsz.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2017/2/22.
 * 报验二级菜单
 */
public class PostRoadActivity extends AppCompatActivity {

    private ListView mlv;
    private int personID;
    private List<List<ImageUrl>> imageUrlLists = new ArrayList<>();
    private ProgressBar mProgressBar;
    private PostRoadAdapter adapter;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case GlobalContanstant.SENDFAIL:
                    mProgressBar.setVisibility(View.GONE);
                    ToastUtil.shortToast(getApplicationContext(), "未数据获取,请稍后");
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postroad);

        initAcitionbar();
        mlv = (ListView) findViewById(R.id.lv_postRoad);
        mProgressBar = (ProgressBar) findViewById(R.id.review_progressbar);

        //sp 获取当前登陆人的ID
        personID = SpUtils.getInt(getApplicationContext(), GlobalContanstant.PERSONID);

        initData();

    }

    private List<AudioUrl> audioUrls = new ArrayList<>();

    private void initData() {

        mProgressBar.setVisibility(View.VISIBLE);

        new Thread() {
            @Override
            public void run() {

                try {
                    String sendData = DealActivity.getDealData(GlobalContanstant.GETPOST, personID);

                    if (sendData != null) {

                        Review review = JsonUtil.jsonToBean(sendData, Review.class);
                        Intent intent = getIntent();
                        int position = intent.getIntExtra("position", 0);
                        List<Review.ReviewRoad> reviewRoadList = review.getReviewRoadList();
                        Review.ReviewRoad reviewRoad = reviewRoadList.get(position);
                        List<Review.ReviewRoad.ReviewRoadDetail> list = reviewRoad.getList();


                        audioUrls.clear();
                        //遍历list
                        for (Review.ReviewRoad.ReviewRoadDetail detail : list) {
                            String taskNumber = detail.getTaskNumber();
                            /**
                             * 获取到图片的URl
                             */
                            String json = MyApplication.getAllImagUrl(taskNumber, GlobalContanstant.GETREVIEW);

                            if (json != null) {

                                List<ImageUrl> imageUrlList = new Gson().fromJson(json, new TypeToken<List<ImageUrl>>() {
                                }.getType());

                                imageUrlLists.add(imageUrlList);
                            }

                            String audioUrljson = RoadActivity.getAudio(taskNumber);

                            if (audioUrljson != null) {
                                AudioUrl audioUrl = JsonUtil.jsonToBean(audioUrljson, AudioUrl.class);
                                audioUrls.add(audioUrl);
                            }
                        }
                        adapter = new PostRoadAdapter(review.getReviewRoadList().get(position), imageUrlLists, audioUrls);
                        //主线程更新UI
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mlv.setAdapter(adapter);
                                mProgressBar.setVisibility(View.GONE);
                            }
                        });

                    }
                } catch (Exception e) {
                    Message message = Message.obtain();
                    message.what = GlobalContanstant.SENDFAIL;
                    handler.sendMessage(message);
                }
            }
        }.start();


    }

    private void initAcitionbar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setTitle(R.string.post);
        }
    }


    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}
