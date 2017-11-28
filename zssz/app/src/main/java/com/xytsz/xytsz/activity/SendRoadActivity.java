package com.xytsz.xytsz.activity;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xytsz.xytsz.MyApplication;
import com.xytsz.xytsz.R;
import com.xytsz.xytsz.adapter.SendRoadAdapter;
import com.xytsz.xytsz.bean.AudioUrl;
import com.xytsz.xytsz.bean.Deal;
import com.xytsz.xytsz.bean.ImageUrl;
import com.xytsz.xytsz.bean.PersonList;
import com.xytsz.xytsz.bean.Review;
import com.xytsz.xytsz.global.GlobalContanstant;
import com.xytsz.xytsz.net.NetUrl;
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
 * Created by admin on 2017/2/23.
 * 派发二级页面
 */
public class SendRoadActivity extends AppCompatActivity {

    private static final int ISSEND = 1000002;
    private static final int ISPERSONLIST = 111002;
    private static final int ISSENDPERSON = 1000003;
    private static final int ISSENDBACK = 1000004;
    private static final int ISSENDTASK = 1000005;
    private static final int NOONE = 100003;

    private ListView mlv;
    private Bitmap largeBitmap;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){


                case GlobalContanstant.SENDFAIL:
                    mProgressBar.setVisibility(View.GONE);
                    ToastUtil.shortToast(getApplicationContext(),"未数据获取,请稍后");
                    break;
                case NOONE:
                    ToastUtil.shortToast(getApplicationContext(),"已下派完毕");
                    mProgressBar.setVisibility(View.GONE);
                    break;

                case ISSEND:
                    int passposition = msg.getData().getInt("passposition");
                    String isPass = msg.getData().getString("issend");
                    if (isPass.equals("true")){

                        ToastUtil.shortToast(getApplicationContext(),"下派成功");
                        Intent intent = getIntent();
                        intent.putExtra("passposition",passposition);
                        intent.putExtra("position",position);
                        setResult(505,intent);

                        //finish();
                    }
                    break;
                case ISSENDBACK:

                    int failposition = msg.getData().getInt("failposition");

                    String isBack = msg.getData().getString("isSendBack");


                    if (isBack.equals("true")){
                        ToastUtil.shortToast(getApplicationContext(),"已驳回");
                        Intent intent = getIntent();
                        intent.putExtra("failposition",failposition);
                        intent.putExtra("position",position);
                        setResult(605,intent);
                        //finish();
                    }
                    break;
                case ISSENDTASK:

                        final int failPosition = msg.getData().getInt("failposition");
                        final String advice = msg.getData().getString("advice");
                        final String tasknumber = msg.getData().getString("taskNumber");


                    new Thread() {
                            @Override
                            public void run() {

                                try {
                                    String isSendBack = toBack(tasknumber, GlobalContanstant.GETUNREVIEW,personId,advice);

                                    Message message = Message.obtain();
                                    message.what = ISSENDBACK;
                                    Bundle bundle = new Bundle();
                                    bundle.putInt("failposition",failPosition);
                                    bundle.putString("isSendBack",isSendBack);
                                    message.setData(bundle);

                                    handler.sendMessage(message);

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }.start();
                    break;

                case ISSENDPERSON :
                    String userName = (String) msg.obj;
                    largeBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
                    NotificationManager nm = (NotificationManager)getSystemService(android.content.Context.NOTIFICATION_SERVICE);
                    //Uri ringUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                    Notification noti = new NotificationCompat.Builder(getApplicationContext())
                            .setTicker("任务已派发给："+userName )
                            .setContentTitle(userName)
                            .setContentText("已收到新的派发任务")
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
                    nm.notify(1, noti);
                    break;


            }
        }
    };
    private int personId;
    private ProgressBar mProgressBar;
    private SendRoadAdapter sendRoadAdapter;

    private PendingIntent getContentIntent() {
        Intent intent = new Intent(this, DealActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);

        return PendingIntent.getActivity(getApplicationContext(),2,intent,PendingIntent.FLAG_UPDATE_CURRENT);
    }


    private List<List<ImageUrl>> imageUrlReportLists = new ArrayList<>();


    private int position;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getIntent() != null){
            Intent intent = getIntent();
            //这个position 是review.getreviewRoadList
            position = intent.getIntExtra("position",-1);
        }



        setContentView(R.layout.activity_sendroad);

        initAcitionbar();
        personId = SpUtils.getInt(getApplicationContext(), GlobalContanstant.PERSONID);
        mlv = (ListView) findViewById(R.id.lv_sendroad);
        mProgressBar = (ProgressBar) findViewById(R.id.review_progressbar);
        initData();


    }


    private String toBack(String taskNumber, int phaseIndication,int personID,String advice)throws Exception {
        SoapObject soapObject = new SoapObject(NetUrl.nameSpace, NetUrl.reviewmethodName);
        soapObject.addProperty("TaskNumber", taskNumber);
        soapObject.addProperty("PhaseIndication", phaseIndication);
        soapObject.addProperty("PersonId",personID);
        soapObject.addProperty("RejectInfo",advice);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
        envelope.setOutputSoapObject(soapObject);
        envelope.dotNet = true;
        envelope.bodyOut = soapObject;

        HttpTransportSE httpTransportSE = new HttpTransportSE(NetUrl.SERVERURL);

        httpTransportSE.call(NetUrl.toExamine_SOAP_ACTION, envelope);
        SoapObject object = (SoapObject) envelope.bodyIn;
        String result =  object.getProperty(0).toString();
        return result;
    }

    private List<AudioUrl> audioUrls = new ArrayList<>();
    private void initData() {

        ToastUtil.shortToast(getApplicationContext(), "正在加载数据..");
        mProgressBar.setVisibility(View.VISIBLE);
        //点击的时候请求参数

        new Thread() {



            @Override
            public void run() {

                try {
                    String reviewData = SendActivity.getReviewData(GlobalContanstant.GETSEND);
                    String allPersonList = getAllPersonList();

                    if (reviewData != null) {

                        Review review = JsonUtil.jsonToBean(reviewData, Review.class);

                        Review.ReviewRoad reviewRoad = review.getReviewRoadList().get(position);
                        List<Review.ReviewRoad.ReviewRoadDetail> list = reviewRoad.getList();
                        if (list.size()== 0){
                            Message message = Message.obtain();
                            message.what = NOONE;
                            handler.sendMessage(message);
                        }else {


                        audioUrls.clear();

                        //遍历list

                        for (Review.ReviewRoad.ReviewRoadDetail detail :list){
                            String taskNumber = detail.getTaskNumber();
                            /**
                             * 获取到图片的URl
                             */
                            String json = MyApplication.getAllImagUrl(taskNumber, GlobalContanstant.GETREVIEW);

                            if(json != null) {
                                List<ImageUrl> imageUrlList = new Gson().fromJson(json, new TypeToken<List<ImageUrl>>() {
                                }.getType());

                                imageUrlReportLists.add(imageUrlList);

                            }

                            String audioUrljson = RoadActivity.getAudio(taskNumber);
                            if (audioUrljson != null){
                                AudioUrl audioUrl = JsonUtil.jsonToBean(audioUrljson, AudioUrl.class);
                                audioUrls.add(audioUrl);
                            }


                        }

                        PersonList personList = JsonUtil.jsonToBean(allPersonList, PersonList.class);
                        //人员数量
                        List<PersonList.PersonListBean> personlist = personList.getPersonList();


                        sendRoadAdapter = new SendRoadAdapter(handler,reviewRoad, imageUrlReportLists,personlist,audioUrls);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mlv.setAdapter(sendRoadAdapter);
                                    mProgressBar.setVisibility(View.GONE);
                                }
                            });

                    }
                    }
                } catch (Exception e) {

                    Message message = Message.obtain();
                    message.what =GlobalContanstant.SENDFAIL;
                    handler.sendMessage(message);
                }
            }
        }.start();




    }


    private String  getAllPersonList() throws Exception {
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

    private void goHome() {
        Intent intent = new Intent(SendRoadActivity.this, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("backHome",GlobalContanstant.BACKHOME);
        startActivity(intent);
        finish();
    }

    private void initAcitionbar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setTitle(R.string.send);
        }
    }


    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}
