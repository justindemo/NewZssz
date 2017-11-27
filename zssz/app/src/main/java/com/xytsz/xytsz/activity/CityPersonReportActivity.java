package com.xytsz.xytsz.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dalong.marqueeview.MarqueeView;
import com.xytsz.xytsz.R;
import com.xytsz.xytsz.global.GlobalContanstant;
import com.xytsz.xytsz.util.IntentUtil;
import com.xytsz.xytsz.util.PermissionUtils;
import com.xytsz.xytsz.util.SpUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by admin on 2017/7/12.
 *
 * 城市居民举报界面
 */
public class CityPersonReportActivity extends AppCompatActivity {

    @Bind(R.id.tv_person_report)
    TextView tvPersonReport;
    @Bind(R.id.tv_person_history)
    TextView tvPersonHistory;
    @Bind(R.id.tv_person_help)
    TextView tvPersonHelp;
    @Bind(R.id.tv_marqueeview)
    MarqueeView tvMarqueeview;
    private int alluser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_citypersonreport);
        ButterKnife.bind(this);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            String personreport = getString(R.string.personreport);
            actionBar.setTitle(personreport);

        }

        alluser = SpUtils.getInt(getApplicationContext(), GlobalContanstant.ALLUSERCOUNT);
        String alltitle = getString(R.string.alltitle);
        tvMarqueeview.setText(alltitle + alluser);
        tvMarqueeview.setFocusable(true);
        tvMarqueeview.requestFocus();
        tvMarqueeview.sepX = 2;
        tvMarqueeview.startScroll();

    }


    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    @OnClick({R.id.tv_person_report, R.id.tv_person_history, R.id.tv_person_help})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_person_report:
                //调用相机
                PermissionUtils.requestPermission(CityPersonReportActivity.this, PermissionUtils.CODE_CAMERA, mPermissionGrant);
                break;
            case R.id.tv_person_history:
                IntentUtil.startActivity(view.getContext(), ReportHistoryActivity.class);

                break;
            case R.id.tv_person_help:
                IntentUtil.startActivity(view.getContext(), ReportHelpActivity.class);
                break;
        }
    }


    private PermissionUtils.PermissionGrant mPermissionGrant = new PermissionUtils.PermissionGrant() {
        @Override
        public void onPermissionGranted(int requestCode) {
            switch (requestCode) {
                case PermissionUtils.CODE_CAMERA:
                    IntentUtil.startActivity(CityPersonReportActivity.this, CameraActivity.class);
                    break;
            }
        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionUtils.requestPermissionsResult(CityPersonReportActivity.this, requestCode, permissions, grantResults, mPermissionGrant);
    }

    @Override
    protected void onStart() {
        super.onStart();
        tvMarqueeview.startScroll();
    }

    @Override
    public void onPause() {
        super.onPause();
        tvMarqueeview.stopScroll();
    }

    @Override
    public void onResume() {
        super.onResume();
        tvMarqueeview.startScroll();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        tvMarqueeview.stopScroll();
    }
}
