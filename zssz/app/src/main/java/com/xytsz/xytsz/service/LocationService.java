package com.xytsz.xytsz.service;


import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;


import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.xytsz.xytsz.global.GlobalContanstant;
import com.xytsz.xytsz.net.NetUrl;
import com.xytsz.xytsz.util.SpUtils;
import com.xytsz.xytsz.util.ToastUtil;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

/**
 * Created by admin on 2017/3/26.
 *
 */
public class LocationService extends Service {

    private static final int ISLOAD = 33301;
    private LocationClient locationClient;
    public BDLocationListener myListener = new MyListener();
    private String longitude;
    private String latitude;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what){
                case ISLOAD:
                    String isload = (String) msg.obj;
                    if (!isload.equals("true")){
                        ToastUtil.shortToast(getApplicationContext(),"上报位置信息失败，请检查网络");
                    }
                    break;
            }
        }
    };
    private int personID;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        personID = SpUtils.getInt(getApplicationContext(), GlobalContanstant.PERSONID);
        //获取当前登陆人的ID
        //SP获取
        locat();
    }




    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //定位
        locationClient.requestNotifyLocation();
        locationClient.start();
        return super.onStartCommand(intent, flags, startId);

    }

    private void locat() {
        locationClient = new LocationClient(getApplicationContext());
        locationClient.registerLocationListener(myListener);

        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);// 可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");// 可选，默认gcj02，设置返回的定位结果坐标系
        option.setScanSpan(5000);// 可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);// 可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);// 可选，默认false,设置是否使用gps
        option.setLocationNotify(false);// 可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIgnoreKillProcess(false);// 可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);// 可选，默认false，设置是否收集CRASH信息，默认收集
        locationClient.setLocOption(option);
    }



    private class MyListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            //经度
            longitude = bdLocation.getLongitude() +"";
            //维度
            latitude = bdLocation.getLatitude()+"";

            //发送数据到服务器
            new Thread(){
                @Override
                public void run() {

                    try {
                        String isLoad = toUpLoadLocation(personID, latitude, longitude);

                        Message message = Message.obtain();
                        message.what = ISLOAD;
                        message.obj = isLoad;
                        handler.sendMessage(message);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }.start();
        }
    }

    private String toUpLoadLocation(int personID, String latitude, String longitude) throws  Exception{
        SoapObject soapObject = new SoapObject(NetUrl.nameSpace,NetUrl.uploadLocationmethodName);
        soapObject.addProperty("personId",personID);
        soapObject.addProperty("latitude",latitude);
        soapObject.addProperty("longitude",longitude);

        SoapSerializationEnvelope envelope  =new SoapSerializationEnvelope(SoapEnvelope.VER12);
        envelope.setOutputSoapObject(soapObject);
        envelope.bodyOut = soapObject;
        envelope.dotNet= true;

        HttpTransportSE httpTransportSE = new HttpTransportSE(NetUrl.SERVERURL);
        httpTransportSE.call(NetUrl.toUploadlocation_SOAP_ACTION,envelope);

        SoapObject object = (SoapObject) envelope.bodyIn;
        String result = object.getProperty(0).toString();
        return result;
    }


    @Override
    public void onDestroy() {
        locationClient.stop();
        super.onDestroy();
    }
}
