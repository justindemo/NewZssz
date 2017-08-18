package com.xytsz.xytsz.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.xytsz.xytsz.R;
import com.xytsz.xytsz.adapter.CheckAdapter;

import com.xytsz.xytsz.bean.Review;
import com.xytsz.xytsz.global.GlobalContanstant;

import com.xytsz.xytsz.util.JsonUtil;
import com.xytsz.xytsz.util.SpUtils;
import com.xytsz.xytsz.util.ToastUtil;


import java.util.List;

/**
 * Created by admin on 2017/2/17.
 * 验收界面
 */
public class CheckActivity extends AppCompatActivity {

    private static final int ISCHECK = 6003;
    private ListView mLv;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case ISCHECK:
                    ToastUtil.shortToast(getApplicationContext(), "没有已审核的数据，请稍后重试");
                    break;
            }
        }
    };
    private CheckAdapter adapter;
    private List<Review.ReviewRoad> list;
    private int position;
    private int size;
    private ProgressBar mProgressBar;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);
        initView();
        initData();
    }

    private void initView() {
        mLv = (ListView) findViewById(R.id.lv_check);
        mProgressBar = (ProgressBar) findViewById(R.id.review_progressbar);
    }

    private void initData() {

        mProgressBar.setVisibility(View.VISIBLE);

        ToastUtil.shortToast(getApplicationContext(), "正在加载数据...");
        new Thread() {
            @Override
            public void run() {

                try {
                    //获取所有的病害信息  参数：0
                    String checkData = SendActivity.getReviewData(GlobalContanstant.GETCHECK);
                    if (checkData != null) {

                        Review review = JsonUtil.jsonToBean(checkData, Review.class);
                        list = review.getReviewRoadList();

                        int checkSum = 0;
                        for (Review.ReviewRoad reviewRoad : list) {
                            checkSum += reviewRoad.getList().size();
                        }
                        SpUtils.saveInt(getApplicationContext(), GlobalContanstant.CHECKSUM, checkSum);


                        adapter = new CheckAdapter(list);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (adapter != null) {
                                    mLv.setAdapter(adapter);
                                    mProgressBar.setVisibility(View.GONE);
                                }
                            }
                        });


                    } else {
                        Message message = Message.obtain();
                        message.what = ISCHECK;
                        handler.sendMessage(message);


                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();


        mLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(CheckActivity.this, CheckRoadActivity.class);
                intent.putExtra("position", position);
                startActivityForResult(intent, 700);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            case GlobalContanstant.CHECKROADPASS:
                int passposition = data.getIntExtra("passposition", -1);
                position = data.getIntExtra("position", -1);
                size = list.get(position).getList().size();
                if (size != 0) {

                    list.get(position).getList().remove(passposition);
                } else {
                    list.remove(position);
                }
                adapter.notifyDataSetChanged();
                break;

            case GlobalContanstant.CHECKROADFAIL:
                int failposition = data.getIntExtra("failposition", -1);
                position = data.getIntExtra("position",-1);
                size = list.get(position).getList().size();
                if (size != 0) {

                    list.get(position).getList().remove(failposition);
                } else {
                    list.remove(position);
                }
                adapter.notifyDataSetChanged();
                break;
        }


        super.onActivityResult(requestCode, resultCode, data);
    }
}
