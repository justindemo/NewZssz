package com.xytsz.xytsz.activity;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.util.Base64;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.xytsz.xytsz.R;
import com.xytsz.xytsz.bean.Deal;

import com.xytsz.xytsz.bean.DiseaseInformation;
import com.xytsz.xytsz.global.Data;
import com.xytsz.xytsz.global.GlobalContanstant;
import com.xytsz.xytsz.net.NetUrl;

import com.xytsz.xytsz.receive.CustomBroadCast;
import com.xytsz.xytsz.util.FileBase64Util;
import com.xytsz.xytsz.util.IntentUtil;
import com.xytsz.xytsz.util.PermissionUtils;
import com.xytsz.xytsz.util.SoundUtil;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


/**
 * Created by admin on 2017/1/10.
 * 上报页面
 */
public class ReportActivity extends AppCompatActivity  {

    private static final int AUDIO_SUCCESS = 500;
    private static final int FAIL = 404;
    private static final int VIDEO_SUCCESS = 200;
    private static final int VIDEO_FAIL = 303;
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
    private static final String audioPath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/Zssz/Audio/";
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
    private ImageView mIvInput;
    private TextView mtvPressAudio;
    private TextView mtvAudio;
    private TextView mtvReset;
    private LinearLayout mllAudio;

    private boolean btn_vocie = false;
    private boolean btn_road = false;
    private boolean videoflg = false;
    private static long startTime;
    private SoundUtil soundUtil;
    private String deleteTitle;
    private String deleteContent;
    private String deleteOk;
    private String deleteCancle;
    private String recordLittle;
    private String recordMax;
    private String audiosuccess;
    private String videosuccess;
    private String audioFail;
    private String disrecord;
    private String reportMore;
    private String reportLocation;
    private ImageView mIvReplace;
    private TextView mEtRoad;
    private ImageView mIvAdd;
    private ImageView mIvPlay;
    private ImageView mIvVideoDelete;
    private ImageView mIvAddVideo;
    private FrameLayout mFlvideo;
    private RelativeLayout mllplay;
    private LinearLayout mllAddVideo;
    private String videopath;
    private String videoFail;
    private String videoName;
    private String deletevideoContent;
    private String deleteVideoTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_report);

        initAcitionbar();

        personId = SpUtils.getInt(getApplicationContext(), GlobalContanstant.PERSONID);
        dialogtitle = this.getString(R.string.report_dialog_title);
        dialogcontent1 =this.getString(R.string.report_dialog_content1);
        dialogcontent2 =this.getString(R.string.report_dialog_content2);
        uploading = this.getString(R.string.report_uploading);
        upimg = this.getString(R.string.report_upimg);
        imgsuccess = this.getString(R.string.report_imgsuccess);
        request =this.getString(R.string.report_request);
        deleteTitle = getString(R.string.report_delete_title);
        deleteVideoTitle = getString(R.string.report_delete_video_title);
        deleteContent = getString(R.string.report_delete_content);
        deletevideoContent = getString(R.string.report_delete_video_content);
        deleteOk = getString(R.string.report_delete_ok);
        deleteCancle = getString(R.string.report_delete_cancle);
        recordLittle = getString(R.string.report_record_little);
        recordMax = getString(R.string.report_record_max);


        notifa = this.getString(R.string.report_notifica);
        notifatitle = this.getString(R.string.report_notifica_title);
        error = this.getString(R.string.report_error);

        audiosuccess = getString(R.string.audio_success);
        videosuccess = getString(R.string.video_success);
        audioFail = getString(R.string.audio_fail);
        videoFail = getString(R.string.video_fail);

        disrecord = getString(R.string.disrecord);

        reportMore = getString(R.string.reportmore);

        reportLocation = getString(R.string.reportlocation);
        CustomBroadCast.getInstance().registerAction(getApplicationContext());
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

        //语音录入
        mIvInput = (ImageView) findViewById(R.id.iv_input_style);
        mtvPressAudio = (TextView) findViewById(R.id.tv_press_audio);
        mtvAudio = (TextView) findViewById(R.id.tv_audio);
        mtvReset = (TextView) findViewById(R.id.tv_re_record);
        mllAudio = (LinearLayout) findViewById(R.id.ll_audio);

        mIvReplace = (ImageView) findViewById(R.id.iv_replace);
        mEtRoad = (TextView) findViewById(R.id.et_road_name);


        //視頻输入
        mIvAdd = (ImageView) findViewById(R.id.iv_add_video);
        mIvPlay = (ImageView) findViewById(R.id.iv_play_video);
        mIvVideoDelete = (ImageView) findViewById(R.id.iv_delete);
        mIvAddVideo = (ImageView) findViewById(R.id.iv_video_des);
        mFlvideo = (FrameLayout) findViewById(R.id.fl_video);

        mllAddVideo = (LinearLayout) findViewById(R.id.ll_add_video);
        mllplay = (RelativeLayout) findViewById(R.id.ll_play_video);


    }


    private void initData() {
        soundUtil = new SoundUtil();
        diseaseInformation = new DiseaseInformation();
        //初始化上传图片列表
        taskNumber = getTaskNumber();
        diseaseInformation.taskNumber = taskNumber;
        PermissionUtils.requestPermission(this,PermissionUtils.CODE_ACCESS_COARSE_LOCATION,mPermissionGrant);
        PermissionUtils.requestPermission(this,PermissionUtils.CODE_ACCESS_FINE_LOCATION,mPermissionGrant);
        PermissionUtils.requestPermission(this,PermissionUtils.CODE_READ_EXTERNAL_STORAGE,mPermissionGrant);
        PermissionUtils.requestPermission(this,PermissionUtils.CODE_WRITE_EXTERNAL_STORAGE,mPermissionGrant);
        PermissionUtils.requestPermission(this,PermissionUtils.CODE_RECORD_AUDIO,mPermissionGrant);

        //locat();

        //病害名称
        pbnameAdapter = new ArrayAdapter<>(ReportActivity.this, android.R.layout.simple_spinner_item, Data.pbname);
        pbnameAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spPbName.setAdapter(pbnameAdapter);
        spPbName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String pbname = spPbName.getSelectedItem().toString();
                for (int i = 0; i < Data.pbname.length; i++) {
                    if (Data.pbname[i].equals(pbname)) {
                        diseaseInformation.level = i;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

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


            mIvInput.setOnClickListener(listener);
            mIvReplace.setOnClickListener(listener);

            mIvAdd.setOnClickListener(listener);
            mIvAddVideo.setOnClickListener(listener);
            mIvVideoDelete.setOnClickListener(listener);
            mIvPlay.setOnClickListener(listener);



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


        mEtRoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReportActivity.this,SearchRoadActivity.class);
                startActivityForResult(intent,200);
            }
        });


        mtvPressAudio.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                PermissionUtils.requestPermission(ReportActivity.this,PermissionUtils.CODE_RECORD_AUDIO,mPermissionGrant);

                if (!Environment.getExternalStorageDirectory().exists()) {
                    ToastUtil.shortToast(getApplicationContext(),"No SDcard");
                    return false;
                }
                switch (event.getAction())
                {
                    case MotionEvent.ACTION_DOWN:
                        mtvPressAudio.setBackgroundResource(R.drawable.shape_tv_audio_press);
                        //开始录音，开始倒计时计时
                        audioName = createAudioName();
                        audioNamepath  = audioPath + audioName;
                        start(audioName);
                        startTime = System.currentTimeMillis();
                        startTimer.start();
                        break;
                    case MotionEvent.ACTION_UP:
                        mtvPressAudio.setBackgroundResource(R.drawable.shape_tv_audio);
                        //先判断录音是否小于一秒,
                        //不小于：让重录显示出来，。
                        // 点击重录的时候。弹窗确定后time为0，删除文件
                        //
                        startTimer.cancel();
                        long endtime = System.currentTimeMillis();
                        if( endtime- startTime <= MAX_TIME){
                            finishRecord(endtime);
                        }else {  //当手指向上滑，会cancel
                            cancelRecord();
                        }

                        if (endtime-startTime < MIN_INTERVAL_TIME){
                            mtvPressAudio.setEnabled(true);
                            mtvPressAudio.setFocusable(true);

                            mtvAudio.setVisibility(View.GONE);
                        }else {
                            mtvPressAudio.setEnabled(false);
                            mtvPressAudio.setFocusable(false);
                            mtvAudio.setVisibility(View.VISIBLE);
                        }



                        break;

                }

                return true;
            }
        });

        //点击播放按钮
        mtvAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (audioNamepath == null) {
                    ToastUtil.shortToast(getApplicationContext(), disrecord);
                    return;
                }else {

                final Drawable drawableleft  = getResources().getDrawable(R.mipmap.iv_audio_play);
                final Drawable drawable = getResources().getDrawable(R.mipmap.iv_audio_pause);
                mtvAudio.setCompoundDrawablesWithIntrinsicBounds(drawable,null,null,null);

                soundUtil.setOnFinishListener(new SoundUtil.OnFinishListener() {
                    @Override
                    public void onFinish() {
                        mtvAudio.setCompoundDrawablesWithIntrinsicBounds(drawableleft,null,null,null);
                    }

                    @Override
                    public void onError() {
                        ToastUtil.shortToast(getApplicationContext(),reportMore);
                        mtvAudio.setCompoundDrawablesWithIntrinsicBounds(drawableleft, null, null, null);
                    }
                });

                paly(audioNamepath);
             }
            }
        });

        mtvReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(ReportActivity.this).setTitle(deleteTitle).setMessage(deleteContent).setPositiveButton(deleteOk, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        File file = new File(audioNamepath);
                        if(file.isFile() && file.exists()){
                            file.delete();
                        }

                        mtvReset.setVisibility(View.GONE);
                        mtvAudio.setText("");
                        mtvPressAudio.setEnabled(true);
                        mtvPressAudio.setFocusable(true);
                        dialog.dismiss();
                    }
                }).setNegativeButton(deleteCancle, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create().show();
            }
        });

    }

    private void paly(String name) {

        soundUtil.play(name);
    }

    private void cancelRecord() {
        stopRecording();
        File file = new File(audioNamepath);

        if (file.isFile()&& file.exists()){
            file.delete();
        }
    }
    private String audioName;
    private String audioNamepath;

    private static final int MIN_INTERVAL_TIME = 1500;// 1s 最短
    public final static int MAX_TIME = 60*1000 + 500;// 1分钟，最长

    /**
     * 录音开始计时器，允许的最大时长进入倒计时
     */
    private CountDownTimer startTimer = new CountDownTimer(MAX_TIME - 500, 1000) { // 50秒后开始倒计时
        @Override
        public void onFinish() {
            ToastUtil.shortToast(getApplicationContext(),recordMax);
            long endtime = System.currentTimeMillis();
            finishRecord(endtime);
        }
        @Override
        public void onTick(long millisUntilFinished) {
        }};

    private void finishRecord(long endtime) {
        long intervalTime = endtime - startTime;
        if (intervalTime < MIN_INTERVAL_TIME) {
            ToastUtil.shortToast(getApplicationContext(),recordLittle);
            stopRecording();
            File file = new File(audioNamepath);
            if (file.isFile() && file.exists()) {
                file.delete();
            }
            return;
        }else{
            int time = (int) (intervalTime/1000);
            mtvAudio.setText(time +"s");
            mtvReset.setVisibility(View.VISIBLE);
            stopRecording();
        }
    }

    private void stopRecording() {
        soundUtil.stop();
    }


    private String createAudioName() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINA);
        String name = simpleDateFormat.format(new Date())+".aac";
        return name;
    }

    private void start(String name) {
        soundUtil = new SoundUtil();
        soundUtil.start(name);
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

    private boolean isVideo;
    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {

                case R.id.iv_replace:
                    if (btn_road){
                        spRoadName.setVisibility(View.GONE);
                        mEtRoad.setVisibility(View.VISIBLE);

                        btn_road = false;
                    }else {

                        spRoadName.setVisibility(View.VISIBLE);
                        mEtRoad.setVisibility(View.GONE);
                        btn_road = true;
                    }
                    diseaseInformation.streetAddress_ID = 0;
                    break;
                case R.id.iv_add_video:
                    //加号
                    if (videoflg){
                        mFlvideo.setVisibility(View.GONE);
                        videoflg = false;
                    }else {
                        mFlvideo.setVisibility(View.VISIBLE);
                        //是否有视频
                        if (isVideo){
                            mllplay.setVisibility(View.VISIBLE);
                            mllAddVideo.setVisibility(View.GONE);
                        }else {
                            mllAddVideo.setVisibility(View.VISIBLE);
                            mllplay.setVisibility(View.GONE);
                        }
                        videoflg = true;
                    }
                    break;

                //添加视频
                case R.id.iv_video_des:
                    Intent intent1 = new Intent(ReportActivity.this,RecordVideoActivity.class);
                    startActivityForResult(intent1,300);
                    break;
                //删除
                case R.id.iv_delete:

                    new AlertDialog.Builder(ReportActivity.this).setTitle(deleteVideoTitle).setMessage(deletevideoContent).setPositiveButton(deleteOk, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            File file = new File(videopath);
                            file.delete();
                            videopath = null;
                            mllAddVideo.setVisibility(View.VISIBLE);
                            mllplay.setVisibility(View.GONE);

                            dialog.dismiss();
                        }
                    }).setNegativeButton(deleteCancle, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).create().show();

                    break;
                //播放
                case R.id.iv_play_video:
                    Intent intent = new Intent(ReportActivity.this,PlayVideoActivity.class);
                    intent.putExtra("videoPath",videopath);
                    startActivity(intent);
                    break;

                case R.id.iv_input_style:
                    if (btn_vocie){
                        mllAudio.setVisibility(View.GONE);
                        mEtlocation.setVisibility(View.VISIBLE);
                        mIvInput.setImageResource(R.mipmap.iv_audio);
                        btn_vocie = false;
                    }else {
                        mIvInput.setImageResource(R.mipmap.iv_normal_text);
                        mllAudio.setVisibility(View.VISIBLE);
                        mEtlocation.setVisibility(View.GONE);
                        btn_vocie = true;
                    }

                    break;
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
                    diseaseInformation.audioTime = mtvAudio.getText().toString();
                    //保存到服务器  弹吐司
                    if (mEtlocation != null){
                        diseaseInformation.locationDesc = mEtlocation.getText().toString();

                        if (diseaseInformation.locationDesc.isEmpty() && audioNamepath == null){

                            ToastUtil.shortToast(getApplicationContext(),reportLocation);
                            return;
                        }

                    }


                    diseaseInformation.uploadTime = getCurrentTime();
                    diseaseInformation.photoName = createPhotoName();

                    diseaseInformation.upload_Person_ID = SpUtils.getInt(getApplicationContext(), GlobalContanstant.PERSONID);

                    person_id = diseaseInformation.upload_Person_ID+"";


                    PermissionUtils.requestPermission(ReportActivity.this,PermissionUtils.CODE_ACCESS_COARSE_LOCATION,mPermissionGrant);

                    /**
                     * 上报信息
                     */
                    if (imageBase64Strings.size() != 0) {
                        if (audioNamepath != null) {
                            diseaseInformation.locationDesc = "";
                        }
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
                //Log.i("tag", params[0] + "");
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

                            if (audioNamepath != null) {
                                diseaseInformation.locationDesc ="";
                                File file = new File(audioNamepath);
                                //如果文件存在
                                if (file.exists()) {
                                    try {
                                        String encodeBase64File = FileBase64Util.encodeBase64File(audioNamepath);
                                        diseaseInformation.taskNumber = taskNumber;
                                        diseaseInformation.encodeBase64File = encodeBase64File;
                                        diseaseInformation.audioName = audioName;
                                        String result = updateAudio(diseaseInformation);
                                        Message message = Message.obtain();
                                        message.what = AUDIO_SUCCESS;
                                        message.obj = result;
                                        handler.sendMessage(message);
                                    } catch (Exception e) {
                                        Message message = Message.obtain();
                                        message.what = FAIL;
                                        handler.sendMessage(message);
                                    }


                                }
                            }

                            if(videopath != null){

                                File file = new File(videopath);
                                if (file.exists()){
                                    try {
                                        String encodeBase64File = FileBase64Util.encodeBase64File(videopath);

                                        diseaseInformation.taskNumber = taskNumber;
                                        diseaseInformation.encodeBase64File = encodeBase64File;
                                        diseaseInformation.videoName = videoName;
                                        String result = updateVideo(diseaseInformation);
                                        Message message = Message.obtain();
                                        message.what = VIDEO_SUCCESS;
                                        message.obj = result;
                                        handler.sendMessage(message);
                                    } catch (Exception e) {
                                        Message message = Message.obtain();
                                        message.what = VIDEO_FAIL;
                                        handler.sendMessage(message);
                                    }
                                }
                            }


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


        }
    }

    private String updateAudio(DiseaseInformation diseaseInformation) throws Exception {
        SoapObject soapObject = new SoapObject(NetUrl.nameSpace, NetUrl.audiomethodName);
        //传递的参数
        soapObject.addProperty("TaskNumber", diseaseInformation.taskNumber);
        soapObject.addProperty("FileName", diseaseInformation.audioName);  //文件类型
        soapObject.addProperty("AudioBase64", diseaseInformation.encodeBase64File);   //参数2  图片字符串
        soapObject.addProperty("time", diseaseInformation.audioTime);   //参数2  图片字符串

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
    private String updateVideo(DiseaseInformation diseaseInformation) throws Exception {
        SoapObject soapObject = new SoapObject(NetUrl.nameSpace, NetUrl.videomethodName);
        //传递的参数
        soapObject.addProperty("TaskNumber", diseaseInformation.taskNumber);
        soapObject.addProperty("FileName", diseaseInformation.videoName);  //文件类型
        soapObject.addProperty("Mp4Base64", diseaseInformation.encodeBase64File);   //参数2  图片字符串
        Log.i("soap", soapObject.toString());
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
        if (requestCode == 300){
            if (resultCode == 301){
                videopath = data.getStringExtra("videoPath");
                if (videopath != null){
                    mllplay.setVisibility(View.VISIBLE);
                    mllAddVideo.setVisibility(View.GONE);
                }else {
                    mllplay.setVisibility(View.GONE);
                    mllAddVideo.setVisibility(View.VISIBLE);
                }
                videoName = data.getStringExtra("videoName");
            }
        }



        if (requestCode == 200){
            if (resultCode == 500){
                String roadname = data.getStringExtra("roadname");
                mEtRoad.setText(roadname);
                for (int i = 0; i < Deal.roadS.length; i++) {
                    if (roadname == Deal.roadS[i]){
                        diseaseInformation.streetAddress_ID = ++i;
                    }
                }
            }
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
                    mIvphoto1.setClickable(false);
                    String encode1 = photo2Base64(path);
                    fileNames.add(fileName1);
                    imageBase64Strings.add(encode1);

                } else if (requestCode == 2) {
                    // bitmap = (Bitmap) data.getExtras().get("data");

                    bitmap = getBitmap(mIvphoto2,fileUri.getPath());
                    String fileName2 = saveToSDCard(bitmap);
                    //将选择的图片设置到控件上
                    mIvphoto2.setImageBitmap(bitmap);
                    mIvphoto2.setClickable(false);

                    String encode2 = photo2Base64(path);

                    fileNames.add(fileName2);

                    imageBase64Strings.add(encode2);

                } else if (requestCode == 3) {
                    //bitmap = (Bitmap) data.getExtras().get("data");

                    bitmap = getBitmap(mIvphoto3,fileUri.getPath());
                    String fileName3 = saveToSDCard(bitmap);
                    //将选择的图片设置到控件上
                    mIvphoto3.setImageBitmap(bitmap);
                    mIvphoto3.setClickable(false);

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
            if (bitmap == null){
                ToastUtil.shortToast(getApplicationContext(),"此照片为空,重新选择");
                return;
            }
            String fileName4 = saveToSDCard(bitmap);
            //将选择的图片设置到控件上
            mIvphoto1.setImageBitmap(bitmap);
            mIvphoto1.setClickable(false);

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
            if (bitmap == null){
                ToastUtil.shortToast(getApplicationContext(),"此照片为空,重新选择");
                return;
            }
            String fileName5 = saveToSDCard(bitmap);
            //将选择的图片设置到控件上
            mIvphoto2.setImageBitmap(bitmap);
            mIvphoto2.setClickable(false);

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
            if (bitmap == null){
                ToastUtil.shortToast(getApplicationContext(),"此照片为空,重新选择");
                return;
            }
            String fileName6 = saveToSDCard(bitmap);
            //将选择的图片设置到控件上
            mIvphoto3.setImageBitmap(bitmap);
            mIvphoto3.setClickable(false);

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

        int bitmapDegree = getBitmapDegree(path);
        Bitmap rotateBitmap = rotateBitmap(bitmap, bitmapDegree);
        return rotateBitmap;
    }

    private int getBitmapDegree(String path) {
        int degree = 0;
        try {
            //从指定路径读取图片，获取exif信息
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);

            switch (orientation){
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }

            return degree;

        } catch (IOException e) {
            //e.printStackTrace();
        }

        return degree;
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
                case FAIL:
                    ToastUtil.shortToast(getApplicationContext(),audioFail);
                    break;
                case VIDEO_FAIL:
                    ToastUtil.shortToast(getApplicationContext(),videoFail);
                    break;
                case AUDIO_SUCCESS:
                    String audioSuccess = (String) msg.obj;
                    if (audioSuccess.equals("true")){
                        ToastUtil.shortToast(getApplicationContext(), audiosuccess);
                    }
                    break;
                case VIDEO_SUCCESS:
                    String videoSuccess = (String) msg.obj;
                    if (videoSuccess.equals("true")){
                        ToastUtil.shortToast(getApplicationContext(), videosuccess);
                    }
                    break;


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

                        finish();
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

    private void initAcitionbar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setTitle(R.string.reprote);
        }
    }


    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}
