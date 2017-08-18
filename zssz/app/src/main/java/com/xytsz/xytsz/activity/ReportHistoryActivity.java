package com.xytsz.xytsz.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.xytsz.xytsz.R;
import com.xytsz.xytsz.adapter.ReportHistoryAdapter;

import org.w3c.dom.ls.LSInput;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by admin on 2017/7/20.
 *
 *
 */
public class ReportHistoryActivity extends AppCompatActivity {

    @Bind(R.id.reporthistory_recycleView)
    RecyclerView reporthistoryRecycleView;

    private List<String> list = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporthistory);
        ButterKnife.bind(this);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("举报历史");

        }



        LinearLayoutManager manager = new LinearLayoutManager(this);
        reporthistoryRecycleView.setLayoutManager(manager);

        list.clear();
        for (int i = 0; i < 5; i++) {

            list.add("张三"+i);
        }

        ReportHistoryAdapter adapter = new ReportHistoryAdapter(list);

        reporthistoryRecycleView.setAdapter(adapter);

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}
