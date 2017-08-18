package com.xytsz.xytsz.activity;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.RadioGroup;

import com.baidu.mapapi.SDKInitializer;
import com.google.gson.reflect.TypeToken;
import com.xytsz.xytsz.bean.Deal;
import com.xytsz.xytsz.bean.DealType;
import com.xytsz.xytsz.bean.DiseaseType;
import com.xytsz.xytsz.bean.FacilityName;
import com.xytsz.xytsz.bean.FacilitySpecifications;
import com.xytsz.xytsz.bean.FacilityType;
import com.xytsz.xytsz.bean.Road;
import com.xytsz.xytsz.bean.UpdateStatus;
import com.xytsz.xytsz.bean.VersionInfo;
import com.xytsz.xytsz.fragment.MainFragment;
import com.xytsz.xytsz.fragment.MainFragments;
import com.xytsz.xytsz.fragment.SuperviseFragment;
import com.xytsz.xytsz.global.GlobalContanstant;
import com.xytsz.xytsz.base.BaseFragment;
import com.xytsz.xytsz.fragment.HomeFragment;
import com.xytsz.xytsz.adapter.MainAdapter;
import com.xytsz.xytsz.fragment.MeFragment;

import com.xytsz.xytsz.net.NetUrl;
import com.xytsz.xytsz.ui.NoScrollViewpager;
import com.xytsz.xytsz.R;
import com.xytsz.xytsz.fragment.TableFragment;
import com.xytsz.xytsz.util.IntentUtil;
import com.xytsz.xytsz.util.JsonUtil;
import com.xytsz.xytsz.util.SpUtils;
import com.xytsz.xytsz.util.ToastUtil;
import com.xytsz.xytsz.util.UpdateVersionUtil;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2017/1/3.
 * <p/>
 * 主页
 */
public class HomeActivity extends AppCompatActivity {

    private RadioGroup mRadiogroup;
    private NoScrollViewpager mViewpager;
    private ArrayList<Fragment> fragments;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /**
         * 没有登陆的时候，先登陆
         */


        SDKInitializer.initialize(getApplicationContext());

        setContentView(R.layout.activity_home);
        String loginId = SpUtils.getString(getApplicationContext(), GlobalContanstant.LOGINID);

        if (loginId == null || TextUtils.isEmpty(loginId)) {
            Intent intent = new Intent(HomeActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            HomeActivity.this.finish();
        }

        /**
         *
         * 最后去掉注释
         */
        getData();

        initView();
        initData();
    }

    private void getData() {
        new Thread() {
            @Override
            public void run() {

                try {
                    String dealTypejson = getJson(NetUrl.dealtypemethodName, NetUrl.dealtype_SOAP_ACTION);
                    String fatypejson = getJson(NetUrl.fatypemethodName, NetUrl.fatype_SOAP_ACTION);
                    String pbTypejson = getJson(NetUrl.problemmethodName, NetUrl.pbtype_SOAP_ACTION);
                    String faNamejson = getJson(NetUrl.faNamemethodName, NetUrl.faname_SOAP_ACTION);
                    String faSizejson = getJson(NetUrl.faSizemethodName, NetUrl.fasize_SOAP_ACTION);
                    String streetjson = getJson(NetUrl.streetmethodName, NetUrl.street_SOAP_ACTION);


                    List<DealType> dealtypeList = JsonUtil.jsonToBean(dealTypejson, new TypeToken<List<DealType>>() {
                    }.getType());
                    List<FacilityType> fatypeList = JsonUtil.jsonToBean(fatypejson, new TypeToken<List<FacilityType>>() {
                    }.getType());
                    List<DiseaseType> pbtypeList = JsonUtil.jsonToBean(pbTypejson, new TypeToken<List<DiseaseType>>() {
                    }.getType());
                    List<FacilityName> faNameList = JsonUtil.jsonToBean(faNamejson, new TypeToken<List<FacilityName>>() {
                    }.getType());
                    List<FacilitySpecifications> faSizeList = JsonUtil.jsonToBean(faSizejson, new TypeToken<List<FacilitySpecifications>>() {
                    }.getType());
                    List<Road> streetList = JsonUtil.jsonToBean(streetjson, new TypeToken<List<Road>>() {
                    }.getType());

                    Deal.dealType.clear();
                    Deal.facilityTypes.clear();
                    Deal.problemTypes.clear();
                    Deal.facilityNames.clear();
                    Deal.facilitySizes.clear();
                    Deal.selectFatype.clear();
                    Deal.selectPbtype.clear();
                    Deal.selectFaSizetype.clear();
                    Deal.selectFaNametype.clear();

                    if (dealtypeList.size() != 0 && fatypeList.size() != 0 && pbtypeList.size() != 0 && faNameList.size() != 0 && faSizeList.size() != 0  && streetList.size() != 0) {
                        //开始添加数据
                        ArrayList<String> dealtype = new ArrayList<>();
                        dealtype.clear();
                        for (int i = 0; i < dealtypeList.size(); i++) {
                            dealtype.add(dealtypeList.get(i).getDealTypeName());
                        }


                        Deal.dealType.addAll(dealtype);

                        Deal.roadS = new String[streetList.size()];
                        for (int i = 0; i < streetList.size(); i++) {
                            Deal.roadS[i] = streetList.get(i).getStreetName();
                        }



                        //设施类型
                        //zhangyi
                        for (int i = 0; i < dealtypeList.size(); i++) {
                            ArrayList<String> strings = new ArrayList<String>();
                            strings.clear();
                            for (int j = 0; j < fatypeList.size(); j++) {
                                if (dealtypeList.get(i).getID() == fatypeList.get(j).getDealTypeID()) {
                                    strings.add(fatypeList.get(j).getFacilityTypeName());
                                }
                            }
                            Deal.facilityTypes.add(strings);

                        }

                        for (int i = 0; i < fatypeList.size(); i++) {
                            Deal.selectFatype.add(fatypeList.get(i).getFacilityTypeName());
                        }

                        for (int i = 0; i < pbtypeList.size(); i++) {
                            Deal.selectPbtype.add(pbtypeList.get(i).getDiseaseType_Name());
                        }

                        for (int i = 0; i < faNameList.size(); i++) {
                            Deal.selectFaNametype.add(faNameList.get(i).getFacilityName_Name());
                        }

                        for (int i = 0; i < faSizeList.size(); i++) {
                            Deal.selectFaSizetype.add(faSizeList.get(i).getFacilitySpecifications_Name());
                        }

                        //病害类型

                        for (int i = 0; i < dealtypeList.size(); i++) {
                            ArrayList<ArrayList<String>> problemtypes = new ArrayList<ArrayList<String>>();
                            problemtypes.clear();
                            for (int j = 0; j < fatypeList.size(); j++) {
                                if (dealtypeList.get(i).getID() == fatypeList.get(j).getDealTypeID()) {

                                    ArrayList<String> list = new ArrayList<String>();
                                    list.clear();
                                    for (int k = 0; k < pbtypeList.size(); k++) {

                                        if (fatypeList.get(j).getFacilityTypeID() == pbtypeList.get(k).getFacilityType_ID()) {

                                            list.add(pbtypeList.get(k).getDiseaseType_Name());
                                        }
                                    }

                                    problemtypes.add(list);
                                }
                            }
                            Deal.problemTypes.add(problemtypes);
                        }

                        //设施名称

                        for (int i = 0; i < dealtypeList.size(); i++) {
                            ArrayList<ArrayList<String>> facilitynames = new ArrayList<ArrayList<String>>();
                            facilitynames.clear();
                            for (int j = 0; j < fatypeList.size(); j++) {
                                if (dealtypeList.get(i).getID() == fatypeList.get(j).getDealTypeID()) {
                                    ArrayList<String> faNames = new ArrayList<String>();
                                    faNames.clear();
                                    for (int k = 0; k < faNameList.size(); k++) {
                                        if (fatypeList.get(j).getFacilityTypeID() == faNameList.get(k).getFacilityType_ID()) {

                                            faNames.add(faNameList.get(k).getFacilityName_Name());
                                        }
                                    }
                                    facilitynames.add(faNames);
                                }
                            }
                            Deal.facilityNames.add(facilitynames);
                        }


                        // 设施规格
                        for (int i = 0; i < dealtypeList.size(); i++) {
                            ArrayList<ArrayList<ArrayList<String>>> facilitysizes = new ArrayList<ArrayList<ArrayList<String>>>();
                            facilitysizes.clear();
                            for (int j = 0; j < fatypeList.size(); j++) {

                                if (dealtypeList.get(i).getID() == fatypeList.get(j).getDealTypeID()) {
                                    ArrayList<ArrayList<String>> faSizes = new ArrayList<ArrayList<String>>();
                                    faSizes.clear();
                                    for (int k = 0; k < faNameList.size(); k++) {
                                        if (fatypeList.get(j).getFacilityTypeID() == faNameList.get(k).getFacilityType_ID()) {
                                            ArrayList<String> faSize = new ArrayList<String>();
                                            faSize.clear();
                                            for (int g = 0; g < faSizeList.size(); g++) {
                                                if (faNameList.get(k).getFacilityName_ID() == faSizeList.get(g).getFacilityName_ID()) {

                                                    faSize.add(faSizeList.get(g).getFacilitySpecifications_Name());

                                                }
                                            }
                                            faSizes.add(faSize);
                                        }
                                    }
                                    facilitysizes.add(faSizes);
                                }
                            }
                            Deal.facilitySizes.add(facilitysizes);
                        }


                    }else {
                        Message message = Message.obtain();
                        message.what = DATA_REPORT;
                        handler.sendMessage(message);
                    }


                } catch (Exception e) {
                    e.printStackTrace();

                }
            }
        }.start();

    }



    private void initView() {
        mRadiogroup = (RadioGroup) findViewById(R.id.homeactivity_rg_radiogroup);
        mViewpager = (NoScrollViewpager) findViewById(R.id.homeactivity_vp);
        //默认显示home界面
        mRadiogroup.check(R.id.homeactivity_rbtn_home);
    }

    private void initData() {

        fragments = new ArrayList<>();
        fragments.clear();
        fragments.add(new MainFragments());
        fragments.add(new HomeFragment());
        fragments.add(new SuperviseFragment());
        fragments.add(new TableFragment());
        fragments.add(new MeFragment());
        //把fragment填充到viewpager

        MainAdapter adapter = new MainAdapter(getSupportFragmentManager(), fragments);
        mViewpager.setAdapter(adapter);
        mViewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            //当界面切换完成的时候
            @Override
            public void onPageSelected(int position) {
                BaseFragment fragment = (BaseFragment) fragments.get(position);
                //加载的时候可能会出错
                try {
                    fragment.initData();
                } catch (Exception e) {

                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });


        mRadiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {

                    //加载首页的时候加载Main
                    case R.id.homeactivity_rbtn_home:
                        mViewpager.setCurrentItem(0, false);
                        break;

                        //加载作业
                    case R.id.homeactivity_rbtn_working:
                        mViewpager.setCurrentItem(1, false);
                        break;

                    //加载更多的时候  加载 监管页面
                    case R.id.homeactivity_rbtn_more:
                        mViewpager.setCurrentItem(2, false);
                        break;


                    //加载数据 Table
                    case R.id.homeactivity_rbtn_data:
                        mViewpager.setCurrentItem(3, false);
                        break;

                    //我的界面
                    case R.id.homeactivity_rbtn_me:
                        mViewpager.setCurrentItem(4, false);
                        break;


                }
            }
        });


        if (getIntent() != null) {
            String backHome = getIntent().getStringExtra("backHome");
            if (backHome != null && backHome.equals(GlobalContanstant.BACKHOME)) {
                mViewpager.setCurrentItem(0, false);
            }
        }



        //先判断有没有现版本
        new Thread(){
            @Override
            public void run() {
                try {
                    String versionInfo = UpdateVersionUtil.getVersionInfo();
                    Message message = Message.obtain();
                    message.obj = versionInfo;
                    message.what = VERSIONINFO;
                    handler.sendMessage(message);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }.start();



    }

    private String getJson(String method, String soap_action) throws Exception {
        SoapObject soapObject = new SoapObject(NetUrl.nameSpace, method);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
        envelope.bodyOut = soapObject;
        envelope.dotNet = true;
        envelope.setOutputSoapObject(soapObject);

        HttpTransportSE httpTransportSE = new HttpTransportSE(NetUrl.SERVERURL);
        httpTransportSE.call(soap_action, envelope);

        SoapObject object = (SoapObject) envelope.bodyIn;

        return object.getProperty(0).toString();
    }

    public static String getAllPersonList() throws Exception {
        SoapObject soapObject = new SoapObject(NetUrl.nameSpace, NetUrl.getPersonList);


        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
        envelope.setOutputSoapObject(soapObject);
        envelope.dotNet = true;
        envelope.bodyOut = soapObject;

        HttpTransportSE httpTransportSE = new HttpTransportSE(NetUrl.SERVERURL);

        httpTransportSE.call(NetUrl.getPersonList_SOAP_ACTION, envelope);
        SoapObject object = (SoapObject) envelope.bodyIn;
        String result = object.getProperty(0).toString();
        return result;


    }




    private static final int VERSIONINFO = 144211;
    private static final int DATA_REPORT = 155552;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case VERSIONINFO:
                    String  info = (String) msg.obj;
                    if (info !=null){
                        //检查更新
                        UpdateVersionUtil.localCheckedVersion(getApplicationContext(),new UpdateVersionUtil.UpdateListener() {

                            @Override
                            public void onUpdateReturned(int updateStatus, final VersionInfo versionInfo) {
                                //判断回调过来的版本检测状态
                                switch (updateStatus) {
                                    case UpdateStatus.YES:
                                        //弹出更新提示
                                        UpdateVersionUtil.showDialog(HomeActivity.this,versionInfo);
                                        break;
                                    case UpdateStatus.NO:
                                        //没有新版本
                                        //ToastUtil.shortToast(getApplicationContext(), "已经是最新版本了!");
                                        break;
                                    case UpdateStatus.NOWIFI:
                                        //当前是非wifi网络
                                        //UpdateVersionUtil.showDialog(getContext(),versionInfo);

                                        new AlertDialog.Builder(HomeActivity.this).setTitle("温馨提示").setMessage("当前非wifi网络,下载会消耗手机流量!").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                                UpdateVersionUtil.showDialog(getApplicationContext(),versionInfo);
                                            }
                                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        }).create().show();


                                        break;
                                    case UpdateStatus.ERROR:
                                        //检测失败
                                        ToastUtil.shortToast(getApplicationContext(), "检测失败，请稍后重试！");
                                        break;
                                    case UpdateStatus.TIMEOUT:
                                        //链接超时
                                        ToastUtil.shortToast(getApplicationContext(), "链接超时，请检查网络设置!");
                                        break;
                                }
                            }
                        },info);
                    }
                    break;

                case DATA_REPORT:
                    ToastUtil.shortToast(getApplicationContext(),"网络异常，未获取数据");
                    break;
            }
        }
    };



    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK)

        {

            // 创建退出对话框

            AlertDialog.Builder isExit = new AlertDialog.Builder(this);

            // 设置对话框标题

            isExit.setTitle("系统提示");

            // 设置对话框消息

            isExit.setMessage("确定要退出吗");

            // 添加选择按钮并注册监听

            isExit.setPositiveButton("确定", listener);

            isExit.setNegativeButton("取消", listener);

            // 显示对话框

            isExit.show();


        }

        return false;
    }


    DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener()

    {

        public void onClick(DialogInterface dialog, int which)

        {

            switch (which)

            {

                case AlertDialog.BUTTON_POSITIVE:// "确认"按钮退出程序

                    finish();

                    break;

                case AlertDialog.BUTTON_NEGATIVE:// "取消"第二个按钮取消对话框

                    break;

                default:

                    break;

            }

        }

    };


    @Override
    protected void onStart() {
        super.onStart();


    }
}
