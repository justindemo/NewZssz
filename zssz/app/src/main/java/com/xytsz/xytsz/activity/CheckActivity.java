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
    public static final int FAIL = 500;
    private ListView mLv;
    private Handler handler = new Handler() {


        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case ISCHECK:
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
    private CheckAdapter adapter;
    private List<Review.ReviewRoad> list;
    private int position;
    private int size;
    private ProgressBar mProgressBar;
    private TextView mtvfail;
    private String nodata;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);
        nodata = getString(R.string.check_nodata);
        initAcitionbar();
        initView();
        initData();
    }

    private void initView() {
        mLv = (ListView) findViewById(R.id.lv_check);
        mProgressBar = (ProgressBar) findViewById(R.id.review_progressbar);
        mtvfail = (TextView) findViewById(R.id.tv_check_fail);


    }

    private void initData() {

        mProgressBar.setVisibility(View.VISIBLE);

        new Thread() {
            @Override
            public void run() {

                try {
                    //获取所有的病害信息  参数：0
                    String checkData = SendActivity.getReviewData(GlobalContanstant.GETCHECK);
                    if (checkData != null) {

                        Review review = JsonUtil.jsonToBean(checkData, Review.class);
                        list = review.getReviewRoadList();


                        if (list.size() == 0) {
                            Message message = Message.obtain();
                            message.what = ISCHECK;
                            handler.sendMessage(message);

                        } else {
                            adapter = new CheckAdapter(list);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mLv.setAdapter(adapter);
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
                list.get(position).getList().remove(passposition);
                size = list.get(position).getList().size();
                if (size == 0) {
                    list.remove(position);
                }
                adapter.notifyDataSetChanged();
                break;

            case GlobalContanstant.CHECKROADFAIL:
                int failposition = data.getIntExtra("failposition", -1);
                position = data.getIntExtra("position", -1);
                list.get(position).getList().remove(failposition);
                size = list.get(position).getList().size();
                if (size == 0) {
                    list.remove(position);
                }
                adapter.notifyDataSetChanged();
                break;
        }


        super.onActivityResult(requestCode, resultCode, data);
    }


    private void initAcitionbar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setTitle(R.string.check);
        }
    }


    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}
