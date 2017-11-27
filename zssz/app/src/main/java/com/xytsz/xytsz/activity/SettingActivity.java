package com.xytsz.xytsz.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.xytsz.xytsz.R;
import com.xytsz.xytsz.bean.UpdateStatus;
import com.xytsz.xytsz.bean.VersionInfo;
import com.xytsz.xytsz.global.GlobalContanstant;
import com.xytsz.xytsz.util.SpUtils;
import com.xytsz.xytsz.util.ToastUtil;
import com.xytsz.xytsz.util.UpdateVersionUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by admin on 2017/6/19.
 * 设置界面
 */
public class SettingActivity extends AppCompatActivity {

    private static final int VERSIONINFO = 100211;
    @Bind(R.id.set_getVersion)
    Button setGetVersion;
    @Bind(R.id.set_exit)
    Button setExit;

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
                                        UpdateVersionUtil.showDialog(SettingActivity.this,versionInfo);
                                        break;
                                    case UpdateStatus.NO:
                                        //没有新版本
                                        ToastUtil.shortToast(getApplicationContext(), "已经是最新版本了!");
                                        break;
                                    case UpdateStatus.NOWIFI:
                                        //当前是非wifi网络
                                        //UpdateVersionUtil.showDialog(getContext(),versionInfo);

                                        new AlertDialog.Builder(SettingActivity.this).setTitle("温馨提示").setMessage("当前非wifi网络,下载会消耗手机流量!").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                                UpdateVersionUtil.showDialog(SettingActivity.this,versionInfo);
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
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);


    }


    @OnClick({R.id.set_getVersion, R.id.set_exit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.set_getVersion:
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
                break;
            case R.id.set_exit:

                Intent intent = new Intent(SettingActivity.this,MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                break;
        }
    }
}
