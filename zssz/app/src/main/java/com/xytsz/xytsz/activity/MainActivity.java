package com.xytsz.xytsz.activity;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;


import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.xytsz.xytsz.MyApplication;
import com.xytsz.xytsz.bean.PersonInfo;
import com.xytsz.xytsz.bean.PersonList;
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

    private static final int PERSONDATA = 11123;
    private static final int FAILLOGIN = 11133;
    private static final int ISMEMBER = 11143;

    private List<String> personNameList = new ArrayList<>();
    private List<String> personIDList = new ArrayList<>();
    private EditText login_id;
    private EditText passWord;
    private Button login;
    private Button register;
    private CheckBox save;
    private String loginID;
    private String pWD;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case PERSONDATA:
                    ToastUtil.shortToast(getApplicationContext(), "请检查网络");
                    break;
                case FAILLOGIN:
                    ToastUtil.shortToast(getApplicationContext(), "请先检查网络");
                    break;
                case ISMEMBER:
                    List<PersonList.PersonListBean> list = (List<PersonList.PersonListBean>) msg.obj;
                    if (list.size() != 0) {
                        for (PersonList.PersonListBean detail : list) {
                            personNameList.add(detail.getName());
                            personIDList.add(detail.getId() + "");
                        }
                        SpUtils.putStrListValue(getApplicationContext(), GlobalContanstant.PERSONNAMELIST, personNameList);
                        SpUtils.putStrListValue(getApplicationContext(), GlobalContanstant.PERSONIDLIST, personIDList);
                    }else {
                        ToastUtil.shortToast(getApplicationContext(),"网络异常，未获取数据");
                    }
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initData();
    }

    private void initView() {
        login_id = (EditText) findViewById(R.id.login_id);
        passWord = (EditText) findViewById(R.id.passWord);
        login = (Button) findViewById(R.id.login);
        register = (Button) findViewById(R.id.register);
        //save = (CheckBox) findViewById(R.id.ck);
    }

    private void initData() {


        //点击登陆按钮   从服务器获取到数据 username和pwd
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //检查网络：如果联网，进行操作 不联网不进行操作
                if (isNetworkAvailable(getApplicationContext())) {
                    ToastUtil.shortToast(getApplicationContext(), "正在登录");
                    loginID = login_id.getText().toString();
                    pWD = passWord.getText().toString();


                    if (TextUtils.isEmpty(loginID) || TextUtils.isEmpty(pWD)) {
                        ToastUtil.shortToast(getApplicationContext(), "用户名或密码不能为空");

                    }


                    //上传服务器
                    new Thread() {
                        @Override
                        public void run() {
                            try {
                                final String json = tologin(loginID, pWD);
                                final String personJson = MyApplication.getAllPersonList();
                                if (json != null) {
                                    if (!json.equals("[]")) {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                if (json.equals("false")) {
                                                    ToastUtil.shortToast(getApplicationContext(), "用户名或密码错误");
                                                } else {


                                                    final PersonInfo personInfo = JsonUtil.jsonToBean(json, PersonInfo.class);
                                                    //保存到本地  ID  名字
                                                    int personID = personInfo.get_id();
                                                    String userName = personInfo.get_username();
                                                    String phone = personInfo.get_telephone();
                                                    int department_id = personInfo.get_department_id();

                                                    int role = personInfo.get_role_id();
                                                    //sp 保存
                                                    SpUtils.exit(getApplicationContext());


                                                    SpUtils.saveString(getApplicationContext(), GlobalContanstant.LOGINID, loginID);
                                                    SpUtils.saveString(getApplicationContext(), GlobalContanstant.PASSWORD, pWD);
                                                    //保存不是第一次进入
                                                    SpUtils.saveBoolean(getApplicationContext(), GlobalContanstant.ISFIRSTENTER, false);
                                                    SpUtils.saveInt(getApplicationContext(), GlobalContanstant.PERSONID, personID);
                                                    SpUtils.saveString(getApplicationContext(), GlobalContanstant.USERNAME, userName);
                                                    SpUtils.saveString(getApplicationContext(), GlobalContanstant.PHONE, phone);
                                                    SpUtils.saveInt(getApplicationContext(), GlobalContanstant.DEPARATMENT, department_id);
                                                    SpUtils.saveInt(getApplicationContext(), GlobalContanstant.ROLE, role);

                                                    if (personJson != null) {
                                                        PersonList personList = JsonUtil.jsonToBean(personJson, PersonList.class);
                                                        List<PersonList.PersonListBean> memberList = personList.getPersonList();
                                                        Message message = Message.obtain();
                                                        message.obj = memberList;
                                                        message.what = ISMEMBER;
                                                        handler.sendMessage(message);
                                                    }



                                                    ToastUtil.shortToast(getApplicationContext(), "登录成功");

                                                    IntentUtil.startActivity(MainActivity.this,HomeActivity.class);
                                                   /* Intent intent = new Intent(MainActivity.this, LocationService.class);
                                                    startService(intent);*/
                                                    finish();


                                                }
                                            }
                                        });
                                    } else {
                                        Message message = Message.obtain();
                                        message.what = PERSONDATA;
                                        handler.sendMessage(message);
                                    }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();

                            }

                        }
                    }.start();

                } else {
                    Message message = Message.obtain();
                    message.what = FAILLOGIN;
                    handler.sendMessage(message);
                }
            }
        });


    }

    /**
     * @param context： 上下文
     * @return 网络是否可用
     */

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (info != null && info.isConnected()) {
                // 当前网络是连接的
                if (info.getState() == NetworkInfo.State.CONNECTED) {
                    // 当前所连接的网络可用
                    return true;
                }
            }
        }
        return false;
    }

    private String tologin(String loginID, String pWD) throws Exception {
        SoapObject soapObject = new SoapObject(NetUrl.nameSpace, NetUrl.loginmethodName);
        soapObject.addProperty("loginID", loginID);
        soapObject.addProperty("password", pWD);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
        envelope.bodyOut = soapObject;
        envelope.dotNet = true;
        envelope.setOutputSoapObject(soapObject);

        HttpTransportSE httpTransportSE = new HttpTransportSE(NetUrl.SERVERURL);
        httpTransportSE.call(null, envelope);

        SoapObject object = (SoapObject) envelope.bodyIn;
        String result = object.getProperty(0).toString();
        return result;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

//        if (keyCode == KeyEvent.KEYCODE_BACK){
//            return false;
//        }
        return false;
    }


}
