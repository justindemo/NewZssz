package com.xytsz.xytsz.activity;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.google.gson.reflect.TypeToken;
import com.xytsz.xytsz.R;
import com.xytsz.xytsz.bean.Deal;

import com.xytsz.xytsz.bean.DiseaseInformation;
import com.xytsz.xytsz.bean.Road;
import com.xytsz.xytsz.global.Data;
import com.xytsz.xytsz.global.GlobalContanstant;
import com.xytsz.xytsz.net.NetUrl;

import com.xytsz.xytsz.util.JsonUtil;
import com.xytsz.xytsz.util.PermissionUtils;
import com.xytsz.xytsz.util.SpUtils;
import com.xytsz.xytsz.util.ToastUtil;


import org.ksoap2.SoapEnvelope;

import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Created by admin on 2017/1/10.
 * 上报页面
 */
public class ReportActivity extends AppCompatActivity {

    private LocationClient locationClient;
    public BDLocationListener myListener = new MyListener();
    private ImageView mIvphoto1;
    private ImageView mIvphoto2;
    private ImageView mIvphoto3;
    private EditText mEtDesc;
    private EditText mEtlocation;
    private EditText mEtName;
    private Button mbtReport;
    //图片的存储位置
    //private static final String iconPath = "/sdcard/Zssz/Image/";//图片的存储目录
    private static final String iconPath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/Zssz/Image/";//图片的存储目录
    private Spinner spGrades;
    private Spinner spPbName;
    private Spinner spDep;
    private Spinner spFatype;
    private Spinner spPbtype;
    private Spinner spFaName;
    private Spinner spFaSize;
    private Spinner spRoadName;
    private Spinner spDealFatype;
    ArrayAdapter<String> gradesAdapter;
    ArrayAdapter<String> departmentAdapter;
    ArrayAdapter<String> fatypeAdapter;
    ArrayAdapter<String> pbtypeAdapter;
    ArrayAdapter<String> fanameAdapter;
    ArrayAdapter<String> fasizeAdapter;
    ArrayAdapter<String> roadnameAdapter;
    ArrayAdapter<String> pbnameAdapter;
    ArrayAdapter<String> dealtypeAdapter;
    private int fatypePosition;
    private int dealtypePostion;
    private DiseaseInformation diseaseInformation;
    private String reportResult;
    ReportTask reportTask = new ReportTask();
    private String isphotoSuccess1;
    private String path;
    private List<String> fileNames = new ArrayList<>();
    private List<String> imageBase64Strings = new ArrayList<>();
    private String taskNumber;
    private Uri fileUri;
    private int personId;
    private String dialogtitle;
    private String dialogcontent1;
    private String dialogcontent2;
    private String uploading;
    private String upimg;
    private String imgsuccess;
    private String request;
    private String notifa;
    private String notifatitle;
    private String error;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_report);

        personId = SpUtils.getInt(getApplicationContext(), GlobalContanstant.PERSONID);
        dialogtitle = this.getString(R.string.report_dialog_title);
        dialogcontent1 =this.getString(R.string.report_dialog_content1);
        dialogcontent2 =this.getString(R.string.report_dialog_content2);
        uploading = this.getString(R.string.report_uploading);
        upimg = this.getString(R.string.report_upimg);
        imgsuccess = this.getString(R.string.report_imgsuccess);
        request =this.getString(R.string.report_request);

        notifa = this.getString(R.string.report_notifica);
        notifatitle = this.getString(R.string.report_notifica_title);
        error = this.getString(R.string.report_error);
        initView();
        initData();
    }

    private void initView() {

        spPbName = (Spinner) findViewById(R.id.sp_problemname);
        spGrades = (Spinner) findViewById(R.id.sp_grades);
        spDep = (Spinner) findViewById(R.id.sp_department);
        spDealFatype = (Spinner) findViewById(R.id.sp_deal_facility);
        spFatype = (Spinner) findViewById(R.id.sp_facilitytype);
        spPbtype = (Spinner) findViewById(R.id.sp_problemtype);
        spFaName = (Spinner) findViewById(R.id.sp_facilityname);
        spFaSize = (Spinner) findViewById(R.id.sp_facilitysize);
        spRoadName = (Spinner) findViewById(R.id.sp_roadname);
        mIvphoto1 = (ImageView) findViewById(R.id.iv_report_icon1);
        mIvphoto2 = (ImageView) findViewById(R.id.iv_report_icon2);
        mIvphoto3 = (ImageView) findViewById(R.id.iv_report_icon3);
        mEtDesc = (EditText) findViewById(R.id.problemDesc);
        mEtlocation = (EditText) findViewById(R.id.locationDesc);
        mbtReport = (Button) findViewById(R.id.report);

    }


    private void initData() {
        diseaseInformation = new DiseaseInformation();

        //初始化上传图片列表
        taskNumber = getTaskNumber();
        diseaseInformation.taskNumber = taskNumber;
        PermissionUtils.requestPermission(this,PermissionUtils.CODE_ACCESS_COARSE_LOCATION,mPermissionGrant);
        PermissionUtils.requestPermission(this,PermissionUtils.CODE_ACCESS_FINE_LOCATION,mPermissionGrant);
        PermissionUtils.requestPermission(this,PermissionUtils.CODE_READ_EXTERNAL_STORAGE,mPermissionGrant);
        PermissionUtils.requestPermission(this,PermissionUtils.CODE_WRITE_EXTERNAL_STORAGE,mPermissionGrant);
        //locat();

        //病害名称
        pbnameAdapter = new ArrayAdapter<>(ReportActivity.this, android.R.layout.simple_spinner_item, Data.pbname);
        pbnameAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spPbName.setAdapter(pbnameAdapter);
        String pbname = spPbName.getSelectedItem().toString();
        for (int i = 0; i < Data.pbname.length; i++) {
            if (Data.pbname[i].equals(pbname)) {
                diseaseInformation.level = i;
            }
        }
        //处置等级
        gradesAdapter = new ArrayAdapter<>(ReportActivity.this, android.R.layout.simple_spinner_item, Data.grades);
        gradesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spGrades.setAdapter(gradesAdapter);
        spGrades.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String grade = spGrades.getSelectedItem().toString();
                for (int i = 0; i < Data.grades.length; i++) {
                    if (Data.grades[i].equals(grade)) {
                        i++;
                        diseaseInformation.disposalLevel_ID = i;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //部门
        departmentAdapter = new ArrayAdapter<>(ReportActivity.this, android.R.layout.simple_spinner_item, Data.departments);
        departmentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spDep.setAdapter(departmentAdapter);

        spDep.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String department = spDep.getSelectedItem().toString();
                for (int i = 0; i < Data.departments.length; i++) {
                    if (Data.departments[i].equals(department)) {
                        i++;
                        diseaseInformation.department_ID = i;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if (Deal.dealType.size() == 0){
            ToastUtil.shortToast(getApplicationContext(),error);
        }else {
            //处置类型
            dealtypeAdapter = new ArrayAdapter<>(ReportActivity.this, android.R.layout.simple_spinner_item, Deal.dealType);
            dealtypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spDealFatype.setAdapter(dealtypeAdapter);
            spDealFatype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    //设施类型
                    fatypeAdapter = new ArrayAdapter<>(ReportActivity.this, android.R.layout.simple_spinner_item, Deal.facilityTypes.get(position));
                    fatypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spFatype.setAdapter(fatypeAdapter);


                    //拿到当前处置设施的position
                    dealtypePostion = position;

                    diseaseInformation.dealtype_ID = ++position;

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            //设施类型
            fatypeAdapter = new ArrayAdapter<>(ReportActivity.this, android.R.layout.simple_spinner_item, Deal.facilityTypes.get(0));
            fatypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spFatype.setAdapter(fatypeAdapter);
            //病害类型
            pbtypeAdapter = new ArrayAdapter<>(ReportActivity.this, android.R.layout.simple_spinner_item, Deal.problemTypes.get(0).get(0));
            pbtypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spPbtype.setAdapter(pbtypeAdapter);
            //设施名称
            fanameAdapter = new ArrayAdapter<>(ReportActivity.this, android.R.layout.simple_spinner_item, Deal.facilityNames.get(0).get(0));
            fanameAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spFaName.setAdapter(fanameAdapter);
            //设施规格
            fasizeAdapter = new ArrayAdapter<>(ReportActivity.this, android.R.layout.simple_spinner_item, Deal.facilitySizes.get(0).get(0).get(0));
            fasizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spFaSize.setAdapter(fasizeAdapter);

            //设施类型
            spFatype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    //病害类型
                    pbtypeAdapter = new ArrayAdapter<>(ReportActivity.this, android.R.layout.simple_spinner_item, Deal.problemTypes.get(dealtypePostion).get(position));
                    pbtypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spPbtype.setAdapter(pbtypeAdapter);
                    //设施名称
                    fanameAdapter = new ArrayAdapter<>(ReportActivity.this, android.R.layout.simple_spinner_item, Deal.facilityNames.get(dealtypePostion).get(position));
                    fanameAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spFaName.setAdapter(fanameAdapter);

                    fatypePosition = position;

                    //设施类型
                    String fatype = spFatype.getSelectedItem().toString();
                    for (int i = 0; i < Deal.selectFatype.size(); i++) {
                        if (fatype.equals(Deal.selectFatype.get(i))) {
                            diseaseInformation.facilityType_ID = ++i;
                        }
                    }

                    Log.i("fa", diseaseInformation.facilityType_ID + "");
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });


            //道路信息
            roadnameAdapter = new ArrayAdapter<>(ReportActivity.this, android.R.layout.simple_spinner_item, Deal.roadS);
            roadnameAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spRoadName.setAdapter(roadnameAdapter);


            spRoadName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    diseaseInformation.streetAddress_ID = ++position;
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });


            spFaName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    fasizeAdapter = new ArrayAdapter<>(ReportActivity.this,
                            android.R.layout.simple_spinner_item, Deal.facilitySizes.get(dealtypePostion).get(fatypePosition).get(position));
                    fasizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spFaSize.setAdapter(fasizeAdapter);

                    //设施名称
                    String faNametype = spFaName.getSelectedItem().toString();
                    for (int i = 0; i < Deal.selectFaNametype.size(); i++) {
                        if (faNametype.equals(Deal.selectFaNametype.get(i))) {
                            diseaseInformation.facilityName_ID = ++i;
                        }
                    }
                    Log.i("fanameID", diseaseInformation.facilityName_ID + "");

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });


            mIvphoto1.setOnClickListener(listener);
            mIvphoto2.setOnClickListener(listener);
            mIvphoto3.setOnClickListener(listener);
            mbtReport.setOnClickListener(listener);

            spPbtype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    //病害类型
                    String pbtype = spPbtype.getSelectedItem().toString();
                    for (int i = 0; i < Deal.selectPbtype.size(); i++) {
                        if (pbtype.equals(Deal.selectPbtype.get(i))) {
                            diseaseInformation.diseaseType_ID = ++i;
                        }
                    }

                    Log.i("fadiseaseID", diseaseInformation.diseaseType_ID + "");

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });


            spFaSize.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    //设施规格
                    String faSizetype = spFaSize.getSelectedItem().toString();
                    for (int i = 0; i < Deal.selectFaSizetype.size(); i++) {
                        if (faSizetype.equals(Deal.selectFaSizetype.get(i))) {
                            diseaseInformation.facilitySize_ID = ++i;
                        }
                    }
                    Log.i("fasizeID", diseaseInformation.facilitySize_ID + "");
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }

    }

    private void locat() {
        //进入上报页面的 时候 开始定位
        locationClient = new LocationClient(this);
        locationClient.registerLocationListener(myListener);

        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);// 可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");// 可选，默认gcj02，设置返回的定位结果坐标系
        int span = 3600 * 1000;
        option.setScanSpan(span);// 可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);// 可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);// 可选，默认false,设置是否使用gps
        option.setLocationNotify(false);// 可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIgnoreKillProcess(false);// 可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);// 可选，默认false，设置是否收集CRASH信息，默认收集
        locationClient.setLocOption(option);
    }
    private String person_id;

    private String[] items = new String[]{"拍照","照片"};

    private PermissionUtils.PermissionGrant mPermissionGrant = new PermissionUtils.PermissionGrant(){
        @Override
        public void onPermissionGranted(int requestCode) {
            switch (requestCode){

                case PermissionUtils.CODE_CAMERA:

                    new AlertDialog.Builder(ReportActivity.this).setTitle(dialogtitle).setItems(items, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                           switch (which){
                               case 0:
                                   dialog.dismiss();
                                   Intent intent1 = new Intent("android.media.action.IMAGE_CAPTURE");
                                   File file = new File(getPhotopath(1));
                                   fileUri = Uri.fromFile(file);
                                   intent1.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                                   startActivityForResult(intent1, 1);
                                   break;
                               case 1:
                                   dialog.dismiss();
                                   Intent intent4 = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                   startActivityForResult(intent4, 4);
                                   break;
                           }
                        }
                    }).create().show();
                    break;

                //case PermissionUtils.CODE_ACCESS_FINE_LOCATION:
                case PermissionUtils.CODE_ACCESS_COARSE_LOCATION:
                    locat();
                    break;
            }
        }
    };


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionUtils.requestPermissionsResult(this,requestCode,permissions,grantResults,mPermissionGrant);
    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.iv_report_icon1:
                    //拍照

                    PermissionUtils.requestPermission(ReportActivity.this,PermissionUtils.CODE_CAMERA,mPermissionGrant);
                    //PermissionUtils.requestMultiPermissions(ReportActivity.this,mPermissionGrant);
                    break;
                case R.id.iv_report_icon2:
                    //拍照
                    new AlertDialog.Builder(ReportActivity.this).setTitle(dialogtitle).setItems(items, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which){
                                case 0:
                                    dialog.dismiss();
                                    Intent intent2 = new Intent("android.media.action.IMAGE_CAPTURE");
                                    File file2 = new File(getPhotopath(2));
                                    fileUri = Uri.fromFile(file2);
                                    intent2.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                                    startActivityForResult(intent2, 2);
                                    break;
                                case 1:
                                    dialog.dismiss();
                                    Intent intent5 = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                    startActivityForResult(intent5, 5);
                                    break;
                            }
                        }
                    }).create().show();





                    break;
                case R.id.iv_report_icon3:
                    //拍照
                    new AlertDialog.Builder(ReportActivity.this).setTitle(dialogtitle).setItems(items, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which){
                                case 0:
                                    dialog.dismiss();

                                    Intent intent3 = new Intent("android.media.action.IMAGE_CAPTURE");
                                    File file3 = new File(getPhotopath(3));
                                    fileUri = Uri.fromFile(file3);
                                    intent3.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                                    startActivityForResult(intent3, 3);
                                    break;
                                case 1:
                                    dialog.dismiss();
                                    Intent intent6 = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                    startActivityForResult(intent6, 6);
                                    break;
                            }
                        }
                    }).create().show();


                    //并显示到iv 上
                    break;
                case R.id.report:


                    diseaseInformation.diseaseDescription = mEtDesc.getText().toString();
                    //保存到服务器  弹吐司
                    diseaseInformation.locationDesc = mEtlocation.getText().toString();
                    diseaseInformation.uploadTime = getCurrentTime();
                    diseaseInformation.photoName = createPhotoName();

                    diseaseInformation.upload_Person_ID = SpUtils.getInt(getApplicationContext(), GlobalContanstant.PERSONID);

                    person_id = diseaseInformation.upload_Person_ID+"";
                    /**
                     * 上报信息
                     */
                    if (imageBase64Strings.size() != 0) {

                        try {
                            reportTask.execute(diseaseInformation);
                        } catch (Exception e) {
                            e.printStackTrace();
                            ToastUtil.shortToast(getApplicationContext(), error);
                        }
                        ToastUtil.shortToast(getApplicationContext(), uploading);
                    } else {
                        ToastUtil.shortToast(getApplicationContext(), request);
                        return;
                    }

                    break;
            }
        }
    };



    //上传所有的数据
    public String getRemoteInfo(DiseaseInformation diseaseInformation) throws Exception {

        SoapObject soapObject = new SoapObject(NetUrl.nameSpace, NetUrl.reportmethodName);
        //传递的参数
        soapObject.addProperty("Level", diseaseInformation.level);
        soapObject.addProperty("TaskNumber", diseaseInformation.taskNumber);
        soapObject.addProperty("DisposalLevel_ID", diseaseInformation.disposalLevel_ID);
        soapObject.addProperty("DealType_ID", diseaseInformation.dealtype_ID);
        soapObject.addProperty("FacilityType_ID", diseaseInformation.facilityType_ID);
        soapObject.addProperty("DiseaseType_ID", diseaseInformation.diseaseType_ID);
        soapObject.addProperty("FacilityName_ID", diseaseInformation.facilityName_ID);
        soapObject.addProperty("FacilitySpecifications_ID", diseaseInformation.facilitySize_ID);
        soapObject.addProperty("StreetAddress_ID", diseaseInformation.streetAddress_ID);
        soapObject.addProperty("DiseaseDescription", diseaseInformation.diseaseDescription);
        //上报类型
        soapObject.addProperty("Channel", diseaseInformation.channel);
        //任务阶段标识
        soapObject.addProperty("PhaseIndication", diseaseInformation.phaseIndication);
        soapObject.addProperty("UploadTime", diseaseInformation.uploadTime);
        soapObject.addProperty("Upload_Person_ID", diseaseInformation.upload_Person_ID);
        soapObject.addProperty("Department_ID", diseaseInformation.department_ID);
        soapObject.addProperty("Longitude", diseaseInformation.longitude);
        soapObject.addProperty("Latitude", diseaseInformation.latitude);

        soapObject.addProperty("AddressDescription", diseaseInformation.locationDesc);

        //创建SoapSerializationEnvelope 对象，同时指定soap版本号(之前在wsdl中看到的)
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapSerializationEnvelope.VER12);
        envelope.bodyOut = soapObject;//由于是发送请求，所以是设置bodyOut
        envelope.dotNet = true;//由于是.net开发的webservice，所以这里要设置为true

        Log.i("Soap", soapObject.toString());
        HttpTransportSE httpTransportSE = new HttpTransportSE(NetUrl.SERVERURL);
        httpTransportSE.call(NetUrl.report_SOAP_ACTION, envelope);//调用

        // 获取返回的数据
        SoapObject object = (SoapObject) envelope.bodyIn;
        // 获取返回的结果
        reportResult = object.getProperty(0).toString();
        Log.i("debug", reportResult);
        return reportResult;

    }


    class ReportTask extends AsyncTask<DiseaseInformation, Integer, String> {

        @Override
        protected String doInBackground(DiseaseInformation... params) {
            try {
                Log.i("tag", params[0] + "");
                reportResult = getRemoteInfo(params[0]);

            } catch (Exception e) {
                e.printStackTrace();
            }
            //将结果返回给onPostExecute方法
            return reportResult;
        }

        @Override
        //此方法可以在主线程改变UI
        protected void onPostExecute(String reportResult) {
            // 将WebService返回的结果显示在TextView中


            if (reportResult != null) {
                if (reportResult.equals("true")) {
                    ToastUtil.shortToast(getApplicationContext(), upimg);

                    /**
                     * 上报图片
                     */
                    new Thread() {
                        @Override
                        public void run() {
                            for (int i = 0; i < fileNames.size(); i++) {
                                diseaseInformation.photoName = fileNames.get(i);
                                diseaseInformation.encode = imageBase64Strings.get(i);
                                diseaseInformation.taskNumber = taskNumber;
                                Log.i("taskNumber", diseaseInformation.taskNumber);
                                try {
                                    isphotoSuccess1 = connectWebService(diseaseInformation);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    return;
                                }


                            }

                            Message message = Message.obtain();
                            message.what = IS_PHOTO_SUCCESS1;
                            message.obj = isphotoSuccess1;
                            handler.sendMessage(message);

                        }
                    }.start();

                } else if (reportResult.equals("false")) {
                    ToastUtil.shortToast(getApplicationContext(), error);
                }

            }

            finish();
        }
    }


    private String connectWebService(DiseaseInformation diseaseInformation) throws Exception {
        //构建初始化soapObject
        SoapObject soapObject = new SoapObject(NetUrl.nameSpace, NetUrl.photomethodName);
        //传递的参数
        soapObject.addProperty("TaskNumber", diseaseInformation.taskNumber);
        soapObject.addProperty("FileName", diseaseInformation.photoName);  //文件类型
        soapObject.addProperty("ImgBase64String", diseaseInformation.encode);   //参数2  图片字符串
        soapObject.addProperty("PhaseId", GlobalContanstant.GETREVIEW);
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

    /**
     * 给拍的照片命名
     */
    public String createPhotoName() {
        //以系统的当前时间给图片命名
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        String fileName = format.format(date) + ".jpg";
        return fileName;
    }


    /**
     * 获取原图片存储路径
     *
     * @param i
     * @return
     */
    private String getPhotopath(int i) {
        // 照片全路径
        String fileName = "";
        // 文件夹路径
        String pathUrl = Environment.getExternalStorageDirectory().getAbsolutePath()+"/Zssz/Image/mymy/";
        //String pathUrl = "/sdcard/Zssz/Image/mymy/";
        //String pathUrl = Environment.getExternalStorageDirectory().getPath()+"/Zssz/Image/mymy/";
        String imageName = "imageTest" + i + ".jpg";
        File file = new File(pathUrl);
        file.mkdirs();// 创建文件夹
        fileName = pathUrl + imageName;
        return fileName;
    }


    /**
     * 保存到本地
     */
    public String saveToSDCard(Bitmap bitmap) {
        //先要判断SD卡是否存在并且挂载
        String photoName = createPhotoName();
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File file = new File(iconPath);
            if (!file.exists()) {
                file.mkdirs();
            }
            path = iconPath + photoName;
            FileOutputStream outputStream = null;
            try {
                outputStream = new FileOutputStream(path);

                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);//把图片数据写入文件
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } finally {
                if (outputStream != null) {
                    try {
                        outputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {
            ToastUtil.shortToast(getApplicationContext(), "SD卡不存在");
        }

        return photoName;
    }


    /**
     * 显示图片
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_CANCELED) {

            return;
        }


        Bitmap bitmap = null;
        if (data == null) {
            //当data为空的时候，不做任何处理
            if (resultCode == RESULT_OK ) {
                if (requestCode == 1) {

                    //bitmap = (Bitmap) data.getExtras().get("data");
                    bitmap = getBitmap(mIvphoto1,fileUri.getPath());

                    String fileName1 = saveToSDCard(bitmap);
                    //将选择的图片设置到控件上
                    mIvphoto1.setImageBitmap(bitmap);
                    mIvphoto1.setFocusable(false);
                    String encode1 = photo2Base64(path);
                    fileNames.add(fileName1);
                    imageBase64Strings.add(encode1);

                } else if (requestCode == 2) {
                    // bitmap = (Bitmap) data.getExtras().get("data");

                    bitmap = getBitmap(mIvphoto2,fileUri.getPath());
                    String fileName2 = saveToSDCard(bitmap);
                    //将选择的图片设置到控件上
                    mIvphoto2.setImageBitmap(bitmap);
                    mIvphoto2.setFocusable(false);

                    String encode2 = photo2Base64(path);

                    fileNames.add(fileName2);

                    imageBase64Strings.add(encode2);

                } else if (requestCode == 3) {
                    //bitmap = (Bitmap) data.getExtras().get("data");

                    bitmap = getBitmap(mIvphoto3,fileUri.getPath());
                    String fileName3 = saveToSDCard(bitmap);
                    //将选择的图片设置到控件上
                    mIvphoto3.setImageBitmap(bitmap);
                    mIvphoto3.setFocusable(false);

                    String encode3 = photo2Base64(path);
                    fileNames.add(fileName3);

                    imageBase64Strings.add(encode3);

                }
            }
        //新加的
        }else if (requestCode == 4){

            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();


            bitmap = getBitmap(mIvphoto1, picturePath);
            String fileName4 = saveToSDCard(bitmap);
            //将选择的图片设置到控件上
            mIvphoto1.setImageBitmap(bitmap);
            mIvphoto1.setFocusable(false);

            String encode4 = photo2Base64(path);
            fileNames.add(fileName4);

            imageBase64Strings.add(encode4);

        }else if (requestCode == 5) {

            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            bitmap = getBitmap(mIvphoto2,picturePath);
            String fileName5 = saveToSDCard(bitmap);
            //将选择的图片设置到控件上
            mIvphoto2.setImageBitmap(bitmap);
            mIvphoto2.setFocusable(false);

            String encode5 = photo2Base64(path);
            fileNames.add(fileName5);

            imageBase64Strings.add(encode5);
        }else if (requestCode == 6) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            bitmap = getBitmap(mIvphoto3,picturePath);
            String fileName6 = saveToSDCard(bitmap);
            //将选择的图片设置到控件上
            mIvphoto3.setImageBitmap(bitmap);
            mIvphoto3.setFocusable(false);

            String encode6 = photo2Base64(path);
            fileNames.add(fileName6);

            imageBase64Strings.add(encode6);

        }
    }

    private Bitmap getBitmap(ImageView imageView,String path) {
        Bitmap bitmap;
        int width = imageView.getWidth();

        int height = imageView.getHeight();

        BitmapFactory.Options factoryOptions = new BitmapFactory.Options();

        factoryOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, factoryOptions);

        int imageWidth = factoryOptions.outWidth;
        int imageHeight = factoryOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(imageWidth / width, imageHeight
                / height);

        // Decode the image file into a Bitmap sized to fill the
        // View
        factoryOptions.inJustDecodeBounds = false;
        factoryOptions.inSampleSize = scaleFactor;
        factoryOptions.inPurgeable = true;

        bitmap = BitmapFactory.decodeFile(path,
                factoryOptions);
        return bitmap;
    }

    private Bitmap largeBitmap = null;
    private String photo2Base64(String path) {

        try {
            FileInputStream fis = new FileInputStream(path);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[8192];
            int count = 0;
            while ((count = fis.read(buffer)) >= 0) {
                baos.write(buffer, 0, count);
            }

            byte[] encode = Base64.encode(baos.toByteArray(),Base64.DEFAULT);
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

    private static final int IS_PHOTO_SUCCESS1 = 101001;
    private int id;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case IS_PHOTO_SUCCESS1:
                    String isPhotoSuccess = (String) msg.obj;
                    if (isPhotoSuccess != null) {
                        if (isPhotoSuccess.equals("true")) {
                            //弹出通知：并提示音
                            List<String> personNamelist = SpUtils.getStrListValue(getApplicationContext(), GlobalContanstant.PERSONNAMELIST);
                            List<String> personIDlist = SpUtils.getStrListValue(getApplicationContext(), GlobalContanstant.PERSONIDLIST);

                            for (int i = 0; i < personIDlist.size(); i++) {
                                if (person_id.equals(personIDlist.get(i))){
                                    id = i;
                                }
                            }

                            if (personNamelist.size()!= 0) {
                                String userName = personNamelist.get(id);
                                largeBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
                                NotificationManager nm = (NotificationManager) getSystemService(android.content.Context.NOTIFICATION_SERVICE);
                                //Uri ringUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                                Notification noti = new NotificationCompat.Builder(getApplicationContext())
                                        .setTicker(userName + notifatitle)
                                        .setContentTitle(userName)
                                        .setContentText(notifa)
                                        .setSmallIcon(R.mipmap.ic_launcher)
                                        .setLargeIcon(largeBitmap)
                                        .setContentIntent(getContentIntent())
                                        .setPriority(Notification.PRIORITY_HIGH)//高优先级
                                        .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE)
                                        .setVisibility(Notification.VISIBILITY_PRIVATE)
                                        //自动隐藏
                                        .setAutoCancel(true)
                                        .build();
                                //id =0 =  用来定义取消的id
                                nm.notify(0, noti);

                                ToastUtil.shortToast(getApplicationContext(), imgsuccess);
                            }
                        } else {
                            ToastUtil.shortToast(getApplicationContext(), error);
                        }
                    }
                    break;

            }
        }
    };


    private PendingIntent getContentIntent() {

        Intent intent = new Intent(this,HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("backHome",GlobalContanstant.BACKHOME);
        return PendingIntent.getActivity(getApplicationContext(),1,intent,PendingIntent.FLAG_UPDATE_CURRENT);

    }

    private class MyListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            //经度
            diseaseInformation.longitude = bdLocation.getLongitude() + "";
            //维度
            diseaseInformation.latitude = bdLocation.getLatitude() + "";

        }
    }

    //得到任务单号的方法
    private String getTaskNumber() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date(System.currentTimeMillis());
        String str = formatter.format(date);
        return str;
    }

    //得到上穿时间
    private String getCurrentTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        String str = formatter.format(date);
        return str;
    }

    @Override
    protected void onStart() {
        locationClient.start();
        super.onStart();


    }

    @Override
    protected void onPause() {
        locationClient.stop();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
