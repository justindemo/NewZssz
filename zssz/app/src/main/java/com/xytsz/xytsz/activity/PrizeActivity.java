package com.xytsz.xytsz.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.widget.ImageView;
import android.widget.TextView;

import com.xytsz.xytsz.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by admin on 2017/7/18.
 * 领奖界面
 */
public class PrizeActivity extends AppCompatActivity {

    @Bind(R.id.iv_prize)
    ImageView ivPrize;
    @Bind(R.id.tv_prize_apply)
    TextView tvPrizeApply;
    @Bind(R.id.tv_prize_des)
    TextView tvPrizeDes;
    private int position;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent() != null) {
            position = getIntent().getIntExtra("position", -1);

        }
        setContentView(R.layout.activity_prize);
        ButterKnife.bind(this);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            String title = getString(R.string.prize_title);
            actionBar.setTitle(title);

        }


        //Glide.with(getApplicationContext()).load("https://ws1.sinaimg.cn/large/610dc034gy1fh9utulf4kj20u011itbo.jpg").into(ivPrize);
        tvPrizeDes.setMovementMethod(ScrollingMovementMethod.getInstance());
    }


    @OnClick(R.id.tv_prize_apply)
    public void onViewClicked() {
        //领取界面 需要填报  webView

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

}
