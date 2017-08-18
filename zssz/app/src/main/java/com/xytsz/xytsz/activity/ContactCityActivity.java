package com.xytsz.xytsz.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.xytsz.xytsz.R;
import com.xytsz.xytsz.util.PermissionUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by admin on 2017/8/8.
 * 联系市政
 */
public class ContactCityActivity extends AppCompatActivity {

    @Bind(R.id.tv_cityname)
    TextView tvCityname;
    @Bind(R.id.tv_citychargeperson)
    TextView tvCitychargeperson;
    @Bind(R.id.tv_city_loca)
    TextView tvCityLoca;
    @Bind(R.id.tv_chargepersonphone)
    TextView tvChargepersonphone;
    @Bind(R.id.tv_citycontent)
    TextView tvCitycontent;
    @Bind(R.id.btn_contactcity)
    Button btnContactcity;
    @Bind(R.id.rl_contact_bottom)
    RelativeLayout rlContactBottom;
    @Bind(R.id.contact_progressbar)
    ProgressBar contactProgressbar;
    private PermissionUtils.PermissionGrant mPermissionGrant = new PermissionUtils.PermissionGrant() {
        @Override
        public void onPermissionGranted(int requestCode) {
            switch (requestCode) {
                case PermissionUtils.CODE_ACCESS_COARSE_LOCATION:
                    locat();
                    break;
            }
        }
    };


    private BDLocationListener myListener = new BDLocationListener() {
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            //城市名称
            String city = bdLocation.getCity();


            double latitude = bdLocation.getLatitude();
            double longitude = bdLocation.getLongitude();

            //获取到省的名称：
            String province = bdLocation.getProvince();


        }
    };
    private LocationClient locationClient;


    private void locat() {
        locationClient = new LocationClient(this);
        locationClient.registerLocationListener(myListener);

        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setCoorType("bd09ll");
        option.setIsNeedAddress(true);
        option.setOpenGps(true);
        option.setIsNeedLocationDescribe(false);
        option.setIgnoreKillProcess(false);
        option.setLocationNotify(false);

        locationClient.setLocOption(option);

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactcity);
        ButterKnife.bind(this);


        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("联系市政");

        }

        //根据定位地址获取所在区  或者县 的市政名称
        //先定位当前的位置 然后传递给服务器 拿到 当前位置管理的市政


        PermissionUtils.requestPermission(this, PermissionUtils.CODE_ACCESS_COARSE_LOCATION, mPermissionGrant);
        //默认是不显示的
        rlContactBottom.setVisibility(View.INVISIBLE);

        getData();




        // 首先


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionUtils.requestPermissionsResult(this, requestCode, permissions, grantResults, mPermissionGrant);
    }

    @Override
    protected void onStart() {
        super.onStart();
        locationClient.start();

    }


    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    @OnClick({R.id.tv_city_loca, R.id.btn_contactcity})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_city_loca:
                Intent intent = new Intent(view.getContext(), MemberMapActivity.class);
                //xu要传递位置
                startActivity(intent);
                break;
            case R.id.btn_contactcity:
                Intent intent1 = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "13122231233"));
                if (ActivityCompat.checkSelfPermission(ContactCityActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                startActivity(intent1);
                break;
        }
    }

    public void getData() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                contactProgressbar.setVisibility(View.GONE);
                rlContactBottom.setVisibility(View.VISIBLE);

            }
        },2000);

        tvCitycontent.setMovementMethod(ScrollingMovementMethod.getInstance());
        tvChargepersonphone.setText("13122231233");
    }
}
