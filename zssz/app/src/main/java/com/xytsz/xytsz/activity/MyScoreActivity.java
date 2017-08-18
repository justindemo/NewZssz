package com.xytsz.xytsz.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.xytsz.xytsz.R;
import com.xytsz.xytsz.adapter.MyScoreAdapter;
import com.xytsz.xytsz.util.IntentUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by admin on 2017/7/17.
 * <p/>
 * 我的积分界面
 */
public class MyScoreActivity extends AppCompatActivity {

    @Bind(R.id.tv_myscore_help)
    TextView tvMyscoreHelp;
    @Bind(R.id.tv_myscore)
    TextView tvMyscore;
    @Bind(R.id.tv_sign)
    TextView tvSign;
    @Bind(R.id.myscore_recycleview)
    RecyclerView myscoreRecycleview;

    private List<String> list = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myscore);
        ButterKnife.bind(this);
//        ActionBar.LayoutParams lp =new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT, Gravity.CENTER);
//        View mActionBar = LayoutInflater.from(this).inflate(R.layout.actionbar_layout, null);
//        TextView mtitle = (TextView) mActionBar.findViewById(R.id.actionbar_title);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
//            actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
//            actionBar.setDisplayShowCustomEnabled(true);
            //actionBar.setCustomView(mActionBar,lp);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("我的积分");

        }
        LinearLayoutManager manager = new LinearLayoutManager(this);
        myscoreRecycleview.setLayoutManager(manager);
        list.clear();
        for (int i = 0; i < 10; i++) {
            list.add("签到"+i);
        }

        MyScoreAdapter adapter = new MyScoreAdapter(list);
        myscoreRecycleview.setAdapter(adapter);

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    @OnClick({R.id.tv_myscore_help, R.id.tv_myscore, R.id.tv_sign})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_myscore_help:
                //webView  或者 dialog

                break;
            case R.id.tv_myscore:
                //积分是从服务器获取

                break;
            case R.id.tv_sign:
                //签到界面
                IntentUtil.startActivity(view.getContext(),ScoreSignActivity.class);
                break;
        }
    }
}
