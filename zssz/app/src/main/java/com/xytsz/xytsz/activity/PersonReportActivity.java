package com.xytsz.xytsz.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.baidu.location.BDAbstractLocationListener;
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
import com.xytsz.xytsz.bean.CityPersonReporte;
import com.xytsz.xytsz.global.Data;
import com.xytsz.xytsz.global.GlobalContanstant;
import com.xytsz.xytsz.net.NetUrl;
import com.xytsz.xytsz.util.PermissionUtils;
import com.xytsz.xytsz.util.SpUtils;
import com.xytsz.xytsz.util.ToastUtil;


import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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


    private String filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Zssz/personreport/";
    private String picPath;
    private List<String> imageUrllist = new ArrayList<>();
    private List<String> imageBase64code = new ArrayList<>();
    private List<String> phtotNames = new ArrayList<>();

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

    private LocationClient locationClient;
    public BDAbstractLocationListener myLocaitonListener = new MyLocationListener();
    private GeoCoder mGeoCoder;
    private OnGetGeoCoderResultListener GeoListener = new OnGetGeoCoderResultListener() {
        @Override
        public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {

        }

        @Override
        public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {
            if (reverseGeoCodeResult == null || reverseGeoCodeResult.error != SearchResult.ERRORNO.NO_ERROR) {
                ToastUtil.shortToast(getApplicationContext(), "未找到位置");
            } else {
                String address = reverseGeoCodeResult.getAddress();
                tvPersonAdd.setText(address);
            }
        }
    };
    private CityPersonReporte cityPersonReporte;
    private String imageResult;
    private List<String> picpaths;

    public String getTasknumber() {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date(System.currentTimeMillis());
        String str = formatter.format(date);
        return str;
    }

    private class MyLocationListener extends BDAbstractLocationListener {

        @Override
        public void onReceiveLocation(BDLocation bdLocation) {

            cityPersonReporte.setLatitude(bdLocation.getLatitude() + "");
            cityPersonReporte.setLongitude(bdLocation.getLongitude() + "");


            LatLng currentLatLng = new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude());
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
        option.setScanSpan(10000);
        option.setOpenGps(true);
        option.setLocationNotify(false);
        option.setIgnoreKillProcess(false);
        option.SetIgnoreCacheException(false);// 可选，默认false，设置是否收集CRASH信息，默认收集
        locationClient.setLocOption(option);
        locationClient.start();

    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent() !=null){
            picpaths = (List<String>) getIntent().getSerializableExtra("picpaths");
        }
        setContentView(R.layout.activity_personreport);
        ButterKnife.bind(this);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            String title = getString(R.string.personreporte_title);
            actionBar.setTitle(title);

        }

        initData();

    }

    /**
     * 给拍的照片命名
     */
    public String createPhotoName() {
        //以系统的当前时间给图片命名
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINA);
        String fileName = format.format(date);
        return fileName;
    }

    private void initData() {
        cityPersonReporte = new CityPersonReporte();

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
                        cityPersonReporte.setType_id(i);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        String phone = SpUtils.getString(getApplicationContext(), GlobalContanstant.LOGINID);
        etPersonPhone.setText(phone);


        cityPersonReporte.setTelNumber(phone);



        //拿到图片地址 显示到imageView
        imageUrllist.clear();
        imageBase64code.clear();
        phtotNames.clear();

        for (int i = 0; i < 3; i++) {
            picPath = filePath + i + ".jpg";
            String photoname = createPhotoName() + i + ".jpg";
            phtotNames.add(photoname);
            Bitmap bitmap = BitmapFactory.decodeFile(picPath);
            String base64code = getBase64code(picPath);
            imageBase64code.add(base64code);

            //Bitmap rotateBitmap = rotateBitmap(bitmap, 90f);
            switch (i) {
                case 0:
                    ivPersonReportPic1.setImageBitmap(bitmap);
                    break;
                case 1:
                    ivPersonReportPic2.setImageBitmap(bitmap);
                    break;
                case 2:
                    ivPersonReportPic3.setImageBitmap(bitmap);
                    break;

            }

        }


        //定位
        PermissionUtils.requestPermission(PersonReportActivity.this, PermissionUtils.CODE_ACCESS_COARSE_LOCATION, mPermissionGrant);
        PermissionUtils.requestPermission(PersonReportActivity.this, PermissionUtils.CODE_ACCESS_FINE_LOCATION, mPermissionGrant);

    }

    private String getBase64code(String path) {

        try {
            FileInputStream fis = new FileInputStream(path);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[8192];
            int count = 0;
            while ((count = fis.read(buffer)) >= 0) {
                baos.write(buffer, 0, count);
            }

            byte[] encode = Base64.encode(baos.toByteArray(), Base64.DEFAULT);
            String uploadBuffer = new String(encode);
            Log.i("upload", uploadBuffer);
            fis.close();
            return uploadBuffer;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

    private Bitmap rotateBitmap(Bitmap bm, float orientationDegree) {
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


    private static final int REPORTE_RESULT = 100221;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case REPORTE_RESULT:
                    List<String> results = (List<String>) msg.obj;
                    String success = getString(R.string.personreporte_success);
                    String fail = getString(R.string.personreporte_fail);
                    String reported = getString(R.string.person_reproted);
                    //获取是否已上报
                    if (results.get(0).equals("true")) {

                        if (results.get(1).equals("true")) {
                            ToastUtil.shortToast(getApplicationContext(), success);
                        }else {
                            ToastUtil.shortToast(getApplicationContext(), fail);
                        }
                    }else
                    {
                        ToastUtil.shortToast(getApplicationContext(),reported);
                    }

                    finish();
                    break;
            }
        }
    };


    @OnClick({R.id.iv_person_report_pic1, R.id.iv_person_report_pic2, R.id.iv_person_report_pic3, R.id.bt_person_commit, R.id.iv_update_loca})
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

                Intent intent = new Intent(view.getContext(), ReportPhotoShowActivity.class);
                intent.putExtra("imageUrllist", (Serializable) imageUrllist);
                startActivity(intent);
                break;
            case R.id.bt_person_commit:
                //提交服务器
                String commiting = getString(R.string.personreporte_commiting);
                ToastUtil.shortToast(getApplicationContext(),commiting);

                String tasknumber = getTasknumber();

                cityPersonReporte.setName(etPersonName.getText().toString());
                cityPersonReporte.setInfo(etPersonreportInform.getText().toString());
                cityPersonReporte.setTasknumber(tasknumber);
                new Thread() {
                    @Override
                    public void run() {

                        try {
                            String result = commitServer(cityPersonReporte);
                            //String result ="true";
                            if (result.equals("true")) {
                                for (int i = 0; i < imageBase64code.size(); i++) {
                                    cityPersonReporte.setIamgeBase64code(imageBase64code.get(i));
                                    cityPersonReporte.setPhotoName(phtotNames.get(i));
                                    imageResult = uploadImage(cityPersonReporte);
                                }
                            }

                            List<String> results = new ArrayList<>();
                            results.clear();
                            results.add(result);
                            results.add(imageResult);

                            Message message = Message.obtain();
                            message.what = REPORTE_RESULT;
                            message.obj = results;
                            handler.sendMessage(message);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }.start();

                break;
            case R.id.iv_update_loca:
                //点击更新位置
                //获取地理位置描述
                locat();
                break;
        }
    }

    private String uploadImage(CityPersonReporte cityPersonReporte) throws Exception {
        SoapObject soapObject = new SoapObject(NetUrl.nameSpace, NetUrl.photomethodName);
        //传递的参数
        soapObject.addProperty("TaskNumber", cityPersonReporte.getTasknumber());
        soapObject.addProperty("FileName", cityPersonReporte.getPhotoName());  //文件类型
        soapObject.addProperty("ImgBase64String", cityPersonReporte.getIamgeBase64code());   //参数2  图片字符串
        soapObject.addProperty("PhaseId", "10");
        Log.i("soapo", soapObject.toString());
        //设置访问地址 和 超时时间
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
        envelope.bodyOut = soapObject;
        envelope.dotNet = true;
        envelope.setOutputSoapObject(soapObject);


        HttpTransportSE httpTranstation = new HttpTransportSE(NetUrl.SERVERURL);
        //链接后执行的回调
        httpTranstation.call(null, envelope);
        SoapObject object = (SoapObject) envelope.bodyIn;

        String isphotoSuccess = object.getProperty(0).toString();
        return isphotoSuccess;
    }

    private String commitServer(CityPersonReporte cityPersonReporte) throws Exception {

        SoapObject soapObject = new SoapObject(NetUrl.nameSpace, NetUrl.citypersonreportemethodName);
        //传递的参数
        soapObject.addProperty("tasknumber", cityPersonReporte.getTasknumber());
        soapObject.addProperty("telnumber", cityPersonReporte.getTelNumber());
        soapObject.addProperty("name", cityPersonReporte.getName());   //
        soapObject.addProperty("dealtype_id", cityPersonReporte.getType_id());
        soapObject.addProperty("info", cityPersonReporte.getInfo());
        soapObject.addProperty("longitude", cityPersonReporte.getLongitude());
        soapObject.addProperty("latitude", cityPersonReporte.getLatitude());
//        soapObject.addProperty("longitude", "116.351377");
//        soapObject.addProperty("latitude", "39.892574");
        //设置访问地址 和 超时时间
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
        envelope.bodyOut = soapObject;
        envelope.dotNet = true;
        envelope.setOutputSoapObject(soapObject);


        HttpTransportSE httpTranstation = new HttpTransportSE(NetUrl.SERVERURL);
        //链接后执行的回调
        httpTranstation.call(null, envelope);
        SoapObject object = (SoapObject) envelope.bodyIn;
        String result = object.getProperty(0).toString();
        return result;
    }


    @Override
    protected void onStart() {
        if (locationClient != null) {
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
        finish();
    }
}
