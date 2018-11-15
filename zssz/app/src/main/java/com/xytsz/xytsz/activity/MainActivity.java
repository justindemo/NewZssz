package com.xytsz.xytsz.activity;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;


import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
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
import com.xytsz.xytsz.util.PermissionUtils;


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


        PermissionUtils.requestPermission(MainActivity.this, PermissionUtils.CODE_WRITE_EXTERNAL_STORAGE, mPermissionGrant);
        PermissionUtils.requestPermission(MainActivity.this, PermissionUtils.CODE_READ_EXTERNAL_STORAGE, mPermissionGrant);
        PermissionUtils.requestPermission(MainActivity.this, PermissionUtils.CODE_RECORD_AUDIO, mPermissionGrant);

        PermissionUtils.requestPermission(MainActivity.this, PermissionUtils.CODE_ACCESS_FINE_LOCATION, mPermissionGrant);
        PermissionUtils.requestPermission(MainActivity.this, PermissionUtils.CODE_ACCESS_COARSE_LOCATION, mPermissionGrant);



        logintab = (TabLayout) findViewById(R.id.login_tab);
        loginVg = (ViewPager) findViewById(R.id.login_viewpager);

        String password = getString(R.string.main_password);
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

        LoginAdapter loginAdapter = new LoginAdapter(getSupportFragmentManager(), fragments, titles);
        loginVg.setAdapter(loginAdapter);

        //让标签 跟着viewpager 滑动
        logintab.setupWithViewPager(loginVg);

        logintab.setTabsFromPagerAdapter(loginAdapter);


    }


    private PermissionUtils.PermissionGrant mPermissionGrant = new PermissionUtils.PermissionGrant() {
        @Override
        public void onPermissionGranted(int requestCode) {
            switch (requestCode) {
                case PermissionUtils.CODE_RECORD_AUDIO:
                    break;
            }
        }
    };


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PermissionUtils.requestPermissionsResult(MainActivity.this, requestCode, permissions, grantResults, mPermissionGrant);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }


}
