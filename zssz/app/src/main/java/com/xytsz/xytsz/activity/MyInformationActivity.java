package com.xytsz.xytsz.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.xytsz.xytsz.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by admin on 2017/7/13.
 * 我的资料界面
 */
public class MyInformationActivity extends AppCompatActivity {

    @Bind(R.id.my_name)
    EditText myName;
    @Bind(R.id.my_telephone)
    EditText myTelephone;
    @Bind(R.id.my_department)
    EditText myDepartment;
    @Bind(R.id.my_ll_activite)
    LinearLayout myLlActivite;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myinformation);
        ButterKnife.bind(this);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("我的资料");

        }



        //逻辑：现在是根据获取显示
        //未来：用用户名和密码登录 2：用手机登录
        //用账号密码登录的  这边显示部门， 否则则不显示
        //具体还要显示什么再确定


    }


    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}
