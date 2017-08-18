package com.xytsz.xytsz.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xytsz.xytsz.R;
import com.xytsz.xytsz.adapter.CityInformationAdapter;
import com.xytsz.xytsz.adapter.PersonSurveyAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by admin on 2017/8/8.
 * 人员反馈
 */
public class PersonSurveyActivity extends AppCompatActivity {

    @Bind(R.id.personsurvey_recycleview)
    RecyclerView personsurveyRecycleview;
    @Bind(R.id.personsurvey_progressbar)
    ProgressBar personsurveyProgressbar;

    private List<String> list = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personsurvey);
        ButterKnife.bind(this);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("举报反馈");

        }



        initData();
    }

    private void initData() {
        //加载数据前
        personsurveyProgressbar.setVisibility(View.VISIBLE);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                personsurveyProgressbar.setVisibility(View.GONE);
            }
        },2000);
        //获取数据后 取消显示
        list.clear();
        list.add("引入市政休闲带 阜内“美丽大街”将获重生");
        list.add("西安市政部门昨启动三级防汛预案");
        list.add("市政公司弄虚作假 湘潭严处景观竹喷漆事件");
        list.add("战高温酷暑 新泰130名市政工人让城市“经脉”更畅通");
        list.add("找准“市政债”的“最大公约数”");


        LinearLayoutManager manager = new LinearLayoutManager(this);
        personsurveyRecycleview.setLayoutManager(manager);

        PersonSurveyAdapter adapter = new PersonSurveyAdapter(list);
        personsurveyRecycleview.setAdapter(adapter);

        adapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //

            }
        });


    }


    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}
