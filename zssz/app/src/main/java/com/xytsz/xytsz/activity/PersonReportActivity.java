package com.xytsz.xytsz.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.baidu.location.Address;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.xytsz.xytsz.R;
import com.xytsz.xytsz.global.Data;
import com.xytsz.xytsz.util.IntentUtil;
import com.xytsz.xytsz.util.PermissionUtils;
import com.xytsz.xytsz.util.ToastUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by admin on 2017/7/20.
 * 举报界面
 */
public class PersonReportActivity extends AppCompatActivity {

    @Bind(R.id.et_person_phone)
    EditText etPersonPhone;
    @Bind(R.id.et_person_name)
    EditText etPersonName;
    @Bind(R.id.report_spinner)
    Spinner reportSpinner;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.et_personreport_inform)
    EditText etPersonreportInform;
    @Bind(R.id.pic)
    TextView pic;
    @Bind(R.id.iv_person_report_pic1)
    ImageView ivPersonReportPic1;
    @Bind(R.id.iv_person_report_pic2)
    ImageView ivPersonReportPic2;
    @Bind(R.id.iv_person_report_pic3)
    ImageView ivPersonReportPic3;
    @Bind(R.id.ll_iv)
    LinearLayout llIv;
    @Bind(R.id.tv_person_add)
    TextView tvPersonAdd;
    @Bind(R.id.bt_person_commit)
    Button btPersonCommit;
    @Bind(R.id.iv_update_loca)
    ImageView ivUpdateLoca;


    private String filePath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/Zssz/personreport/";
    private String picPath;
    private List<String> imageUrllist = new ArrayList<>();
    private PermissionUtils.PermissionGrant mPermissionGrant = new PermissionUtils.PermissionGrant() {
        @Override
        public void onPermissionGranted(int requestCode) {
            switch (requestCode){
                case PermissionUtils.CODE_ACCESS_COARSE_LOCATION:
                    locat();
                    break;
            }
        }
    };
    private LocationClient locationClient;
    public BDLocationListener myLocaitonListener = new MyLocationListener();
    private GeoCoder mGeoCoder;
    private OnGetGeoCoderResultListener GeoListener = new OnGetGeoCoderResultListener() {
        @Override
        public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {

        }

        @Override
        public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {
            if (reverseGeoCodeResult == null  || reverseGeoCodeResult.error != SearchResult.ERRORNO.NO_ERROR){
                ToastUtil.shortToast(getApplicationContext(),"未找到位置");
            }else {
                String address = reverseGeoCodeResult.getAddress();
                tvPersonAdd.setText(address);
            }
        }
    };

    private class  MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation bdLocation) {

            LatLng currentLatLng = new LatLng(bdLocation.getLatitude(),bdLocation.getLongitude());
            //反向查询
            mGeoCoder.reverseGeoCode((new ReverseGeoCodeOption())
                    .location(currentLatLng));


        }
    }

    private void locat() {
        mGeoCoder = GeoCoder.newInstance();
        mGeoCoder.setOnGetGeoCodeResultListener(GeoListener);

        locationClient = new LocationClient(PersonReportActivity.this);
        locationClient.registerLocationListener(myLocaitonListener);

        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setCoorType("bd09ll");
        option.setIsNeedAddress(true);
        option.setScanSpan(0);
        option.setOpenGps(true);
        option.setLocationNotify(false);
        option.setIgnoreKillProcess(false);
        option.SetIgnoreCacheException(false);// 可选，默认false，设置是否收集CRASH信息，默认收集
        locationClient.setLocOption(option);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personreport);
        ButterKnife.bind(this);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("问题上报");

        }

        initData();

    }

    private void initData() {
        ArrayAdapter adapter = new ArrayAdapter<String>(PersonReportActivity.this, android.R.layout.simple_spinner_item, Data.reportSort);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        reportSpinner.setAdapter(adapter);
        reportSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String grade = reportSpinner.getSelectedItem().toString();
                for (int i = 0; i < Data.reportSort.length; i++) {
                    if (Data.reportSort[i].equals(grade)) {
                        i++;
                        //TODO：提交服务器


                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        etPersonPhone.setText("13111234567");
        String reportName = etPersonName.getText().toString();
        String reportInfor = etPersonreportInform.getText().toString();

        //拿到图片地址 显示到imageView
        imageUrllist.clear();

        for (int i = 0; i < 3; i++) {
            picPath = filePath +i+".jpg";

            Bitmap bitmap = BitmapFactory.decodeFile(picPath);
            Bitmap rotateBitmap = rotateBitmap(bitmap, 90f);
            switch (i){
                case 0:
                    ivPersonReportPic1.setImageBitmap(rotateBitmap);
                    break;
                case 1:
                    ivPersonReportPic2.setImageBitmap(rotateBitmap);
                    break;
                case 2:
                    ivPersonReportPic3.setImageBitmap(rotateBitmap);
                    break;

            }

        }

        PermissionUtils.requestPermission(PersonReportActivity.this,PermissionUtils.CODE_ACCESS_COARSE_LOCATION,mPermissionGrant);

    }

    private Bitmap rotateBitmap(Bitmap bm,float orientationDegree) {
        Matrix m = new Matrix();
        m.setRotate(orientationDegree, (float) bm.getWidth() / 2, (float) bm.getHeight() / 2);

        try {

            Bitmap bm1 = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), m, true);

            return bm1;
        } catch (OutOfMemoryError ex) {
        }

        return null;
    }


    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    @OnClick({R.id.iv_person_report_pic1, R.id.iv_person_report_pic2, R.id.iv_person_report_pic3, R.id.bt_person_commit,R.id.iv_update_loca})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_person_report_pic1:
            case R.id.iv_person_report_pic2:
            case R.id.iv_person_report_pic3:
                imageUrllist.clear();
                for (int i = 0; i < 3; i++) {
                    picPath = filePath + i + ".jpg";
                    imageUrllist.add(picPath);
                }

                Intent intent = new Intent(view.getContext(),ReportPhotoShowActivity.class);
                intent.putExtra("imageUrllist", (Serializable) imageUrllist);
                startActivity(intent);
                break;
            case R.id.bt_person_commit:
                //提交服务器


                finish();
                break;
            case R.id.iv_update_loca:
                //点击更新位置
                //获取地理位置描述
                locat();
                break;
        }
    }


    @Override
    protected void onStart() {
        if(locationClient != null){
            locationClient.start();
        }
        super.onStart();
    }

    @Override
    protected void onPause() {
        locationClient.stop();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        locationClient.stop();
        locationClient.unRegisterLocationListener(myLocaitonListener);

    }
}
