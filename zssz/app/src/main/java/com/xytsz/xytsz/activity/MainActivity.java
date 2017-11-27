package com.xytsz.xytsz.activity;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;


import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.mob.commons.SMSSDK;
import com.xytsz.xytsz.MyApplication;
import com.xytsz.xytsz.adapter.LoginAdapter;
import com.xytsz.xytsz.bean.PersonInfo;
import com.xytsz.xytsz.bean.PersonList;
import com.xytsz.xytsz.fragment.LoginFragment;
import com.xytsz.xytsz.fragment.PhoneFragment;
import com.xytsz.xytsz.global.GlobalContanstant;
import com.xytsz.xytsz.R;

import com.xytsz.xytsz.net.NetUrl;
import com.xytsz.xytsz.service.LocationService;
import com.xytsz.xytsz.util.IntentUtil;
import com.xytsz.xytsz.util.JsonUtil;
import com.xytsz.xytsz.util.SpUtils;
import com.xytsz.xytsz.util.ToastUtil;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;


/**
 * 登陆页面
 */
public class MainActivity extends AppCompatActivity {


    private TabLayout logintab;
    private ArrayList<Fragment> fragments = new ArrayList<>();
    private ViewPager loginVg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

    }

    private void initView() {

        logintab = (TabLayout) findViewById(R.id.login_tab);
        loginVg = (ViewPager)findViewById(R.id.login_viewpager);

        String password =  getString(R.string.main_password);
        String phone = getString(R.string.main_phone);


        List<String> titles = new ArrayList<>();

        titles.add(password);
        titles.add(phone);

        for (int i = 0; i < titles.size(); i++) {
            logintab.addTab(logintab.newTab().setText(titles.get(i)));
        }

        fragments.clear();
        fragments.add(new LoginFragment());
        fragments.add(new PhoneFragment());

        LoginAdapter loginAdapter = new LoginAdapter(getSupportFragmentManager(),fragments,titles);
        loginVg.setAdapter(loginAdapter);

        //让标签 跟着viewpager 滑动
        logintab.setupWithViewPager(loginVg);

        logintab.setTabsFromPagerAdapter(loginAdapter);


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }



}
