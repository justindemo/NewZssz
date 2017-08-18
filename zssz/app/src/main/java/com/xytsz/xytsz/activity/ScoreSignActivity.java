package com.xytsz.xytsz.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xytsz.xytsz.R;
import com.xytsz.xytsz.adapter.ScoreSignAdapter;
import com.xytsz.xytsz.ui.LineView;
import com.xytsz.xytsz.util.IntentUtil;
import com.xytsz.xytsz.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by admin on 2017/7/17.
 * 签到: 领奖
 */
public class ScoreSignActivity extends AppCompatActivity {

    @Bind(R.id.scoresign_recycleview)
    RecyclerView scoresignRecycleview;

    private List<String> list = new ArrayList<>();
    private RelativeLayout mrlSign;
    private RelativeLayout mrlScore;
    private TextView mtvSign;
    private TextView mtvScore;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoresign);
        ButterKnife.bind(this);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("签到及领奖");

        }

        getData();



        LinearLayoutManager manager = new LinearLayoutManager(this);
        scoresignRecycleview.setLayoutManager(manager);

        list.clear();
        list.add("https://ws1.sinaimg.cn/large/610dc034gy1fh9utulf4kj20u011itbo.jpg");
        list.add("https://ws1.sinaimg.cn/large/610dc034gy1fh9utulf4kj20u011itbo.jpg");
        list.add("https://ws1.sinaimg.cn/large/610dc034gy1fh9utulf4kj20u011itbo.jpg");


        ScoreSignAdapter adapter = new ScoreSignAdapter(list,this);
        scoresignRecycleview.setAdapter(adapter);

        View headView = getLayoutInflater().inflate(R.layout.scoresign_headview,scoresignRecycleview,false);

        mrlSign = (RelativeLayout) headView.findViewById(R.id.rl_headview_sign);
        mrlScore = (RelativeLayout) headView.findViewById(R.id.rl_headview_score);

        mtvSign = (TextView) headView.findViewById(R.id.tv_headview_sign);
        mtvScore = (TextView) headView.findViewById(R.id.tv_headview_myscorenum);

        mtvSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.equals(mtvSign.getText().toString(),"已签到")){
                    ToastUtil.shortToast(ScoreSignActivity.this,"每天只能签一次");
                }

                mtvSign.setText("已签到");
            }
        });


        mrlScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                IntentUtil.startActivity(v.getContext(),MyScoreActivity.class);

            }
        });



        LinearLayout mline = (LinearLayout) headView.findViewById(R.id.ll_line);
        LineView lineView = new LineView(getApplicationContext(), 200);

        lineView.updateX();




        mline.removeAllViews();
        mline.addView(lineView);


        adapter.addHeaderView(headView);
    }


    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    public void getData() {

        //获取数据

        //是否签到  如果签到
//        if (true){
//            mtvSign.setText("已签到");
//        }
    }
}
