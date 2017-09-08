package com.xytsz.xytsz.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xytsz.xytsz.R;
import com.xytsz.xytsz.util.IntentUtil;
import com.xytsz.xytsz.util.ToastUtil;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by admin on 2017/8/2.
 *
 */
public class NoMemberActivity extends AppCompatActivity {

    @Bind(R.id.association_tv_name)
    TextView associationTvName;
    @Bind(R.id.association_tv_username)
    TextView associationTvUsername;
    @Bind(R.id.tv_see_loca)
    TextView tvSeeLoca;
    @Bind(R.id.association_tv_phone)
    TextView associationTvPhone;
    @Bind(R.id.tv_getcontent)
    TextView tvGetcontent;
    @Bind(R.id.btn_getcotent)
    Button btnGetcotent;
    @Bind(R.id.ll_bottom)
    LinearLayout llBottom;
    private List<MemberShow> dataList;
    private int position;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nomember);
        ButterKnife.bind(this);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("会员信息");

        }

        if (getIntent() != null){
            dataList = (List<MemberShow>) getIntent().getSerializableExtra("dataList");
            position = getIntent().getIntExtra("position",-1);
        }
        MemberShow memberShow = dataList.get(position);
        associationTvName.setText(memberShow.getEnname());
        associationTvUsername.setText(memberShow.getName());
        associationTvPhone.setText(memberShow.getTel());

        tvGetcontent.setMovementMethod(ScrollingMovementMethod.getInstance());
    }

    @OnClick({R.id.tv_see_loca, R.id.btn_getcotent})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_see_loca:
                Intent intent = new Intent(view.getContext(),MemberMapActivity.class);
                intent.putExtra("latitude",dataList.get(position).getLatitude());
                intent.putExtra("longitude",dataList.get(position).getLongitude());
                startActivity(intent);
                break;
            case R.id.btn_getcotent:
                //支付  判断 如果支付成功然后显示电话

                //setresult 中去显示的


                //TODO:支付

                Intent intent1 = new Intent(view.getContext(),AllPayActivity.class);

                startActivityForResult(intent1,2);

                break;
        }
    }


    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode ==2){
            switch (resultCode){
                case 1:
                    boolean result = data.getBooleanExtra("result", false);
                    if (result){
                        associationTvPhone.setBackgroundColor(Color.WHITE);
                    }else {
                        ToastUtil.shortToast(getApplicationContext(),"数据未获取成功");
                    }
                    break;
            }
        }
    }
}
