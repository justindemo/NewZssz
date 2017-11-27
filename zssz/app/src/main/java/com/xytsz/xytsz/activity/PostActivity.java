package com.xytsz.xytsz.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.xytsz.xytsz.R;
import com.xytsz.xytsz.adapter.PostAdapter;
import com.xytsz.xytsz.bean.Review;
import com.xytsz.xytsz.global.GlobalContanstant;
import com.xytsz.xytsz.util.JsonUtil;
import com.xytsz.xytsz.util.SpUtils;
import com.xytsz.xytsz.util.ToastUtil;


import java.util.List;

/**
 * Created by admin on 2017/2/22.
 * 报验界面
 */
public class PostActivity extends AppCompatActivity {

    private static final int ISPOST = 5003;
    private static final int FAIL = 500;
    private ListView mlv;
    private int personID;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case ISPOST:
                    mProgressBar.setVisibility(View.GONE);
                    mtvfail.setText(nodata);
                    mtvfail.setVisibility(View.VISIBLE);
                    break;
                case FAIL:
                    mProgressBar.setVisibility(View.GONE);
                    ToastUtil.shortToast(getApplicationContext(), "未获取数据,请稍后");
                    break;
            }
        }
    };
    private ProgressBar mProgressBar;
    private TextView mtvfail;
    private List<Review.ReviewRoad> list;
    private String nodata;
    private PostAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        initAcitionbar();
        mlv = (ListView) findViewById(R.id.lv_post);

        mProgressBar = (ProgressBar) findViewById(R.id.review_progressbar);

        mtvfail = (TextView) findViewById(R.id.tv_post_fail);
        //获取当前登陆人的ID;   sp 获取
        personID = SpUtils.getInt(getApplicationContext(), GlobalContanstant.PERSONID);
        nodata = getString(R.string.deal_nodata);

        initData();
    }

    private void initData() {

        //ToastUtil.shortToast(getApplicationContext(), "正在加载数据...");
        mProgressBar.setVisibility(View.VISIBLE);
        new Thread() {
            @Override
            public void run() {

                try {
                    String sendData = DealActivity.getDealData(GlobalContanstant.GETPOST, personID);
                    if (sendData != null) {
                        Review review = JsonUtil.jsonToBean(sendData, Review.class);
                        list = review.getReviewRoadList();
                        if (list.size() == 0) {

                            Message message = Message.obtain();
                            message.what = ISPOST;
                            handler.sendMessage(message);

                        } else {
                            adapter = new PostAdapter(list);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mlv.setAdapter(adapter);
                                    mProgressBar.setVisibility(View.GONE);

                                }
                            });
                        }

                    }
                } catch (Exception e) {
                    Message message = Message.obtain();
                    message.what = FAIL;
                    handler.sendMessage(message);
                }
            }
        }.start();


        mlv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(PostActivity.this, PostRoadActivity.class);
                intent.putExtra("position", position);
                startActivity(intent);
            }
        });
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
