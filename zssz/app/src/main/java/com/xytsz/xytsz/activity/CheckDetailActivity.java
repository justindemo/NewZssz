package com.xytsz.xytsz.activity;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xytsz.xytsz.R;
import com.xytsz.xytsz.bean.ImageUrl;
import com.xytsz.xytsz.bean.Review;
import com.xytsz.xytsz.global.Data;
import com.xytsz.xytsz.global.GlobalContanstant;
import com.xytsz.xytsz.net.NetUrl;
import com.xytsz.xytsz.util.SpUtils;
import com.xytsz.xytsz.util.ToastUtil;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.Serializable;
import java.util.List;

/**
 * Created by admin on 2017/3/7.
 * 验收详细单
 */
public class CheckDetailActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int ISCHECKPASS = 600001;
    private static final int ISUNCHECKPASS = 600002;
    private TextView mtvRoad;
    private ImageView mivReporte;
    private ImageView mivDealed;
    private TextView mtvReporter;
    private TextView mtvDiseaseName;
    private TextView mtvGrade;
    private TextView mtvFatype;
    private TextView mtvPbtype;
    private TextView mtvDealtype;
    private TextView mtvReportePlace;
    private TextView mtvReviewer;
    private TextView mtvDealer;
    private TextView mtvReporteTime;
    private TextView mtvReviewtime;
    private TextView mtvDesc;
    private TextView mtvBack;
    private TextView mtvPass;

    private TextView mtvRequestTime;
    private TextView mtvResultTime;
    public  boolean ispass;
    public  boolean isShow;
    private int position;
    private Review.ReviewRoad reviewRoad;
    private Review.ReviewRoad.ReviewRoadDetail detail;

    private int personID;


    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case ISCHECKPASS:
                    String isPass = msg.getData().getString("ispass");
                    int passposition = msg.getData().getInt("passposition");
                    if (isPass.equals("true")){
                        ToastUtil.shortToast(getApplicationContext(),"验收通过");

                        Intent intent = getIntent();
                        intent.putExtra("passposition",passposition);
                        setResult(GlobalContanstant.CHECKPASS,intent);
                        finish();
                        //goHome();
                    }
                    break;
                case ISUNCHECKPASS:
                    String isFail = msg.getData().getString("isfail");
                    int failposition = msg.getData().getInt("failposition");
                    if (isFail.equals("true")){
                        ToastUtil.shortToast(getApplicationContext(),"验收未通过");
                        Intent intent = getIntent();
                        intent.putExtra("failposition",failposition);
                        setResult(GlobalContanstant.CHECKFAIL,intent);
                        finish();

                        //goHome();
                    }
                    break;
            }
        }
    };
    private List<List<ImageUrl>> imageUrlReport;
    private List<List<ImageUrl>> imageUrlPost;
    private List<ImageUrl> imageUrl;
    private List<ImageUrl> imageUrlpost;
    private int id;
    private int acid;
    private int reviewid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (getIntent() != null) {
            position = getIntent().getIntExtra("position", 0);
            reviewRoad = (Review.ReviewRoad) getIntent().getSerializableExtra("reviewRoad");
            imageUrlReport = (List<List<ImageUrl>>) getIntent().getSerializableExtra("imageUrlReport");
            imageUrlPost = (List<List<ImageUrl>>) getIntent().getSerializableExtra("imageUrlPost");

        }

        detail = reviewRoad.getList().get(position);
        setContentView(R.layout.activity_checkdetail);
        personID = SpUtils.getInt(getApplicationContext(),GlobalContanstant.PERSONID);
        initView();
        initData();
    }
    private void initView() {
        mtvReporter = (TextView) findViewById(R.id.tv_check_reporter);
        mtvDiseaseName = (TextView) findViewById(R.id.tv_check_diseasename);
        mtvGrade = (TextView) findViewById(R.id.tv_check_grade);
        mtvDealtype = (TextView) findViewById(R.id.tv_check_dealtype);
        mtvFatype = (TextView) findViewById(R.id.tv_check_fatype);
        mtvPbtype = (TextView) findViewById(R.id.tv_check_pbtype);
        mtvReportePlace = (TextView) findViewById(R.id.tv_check_reporteplace);
        mtvReviewer = (TextView) findViewById(R.id.tv_check_reviewer);
        mtvDealer = (TextView) findViewById(R.id.tv_check_dealer);
        mtvReporteTime = (TextView) findViewById(R.id.tv_check_reportetime);
        mtvReviewtime = (TextView) findViewById(R.id.tv_check_reviewtime);
        mtvDesc = (TextView) findViewById(R.id.tv_check_decs);
        mtvRequestTime = (TextView) findViewById(R.id.tv_check_requesttime);
        mtvResultTime = (TextView) findViewById(R.id.tv_check_resulttime);
        mtvPass = (TextView) findViewById(R.id.tv_check_pass);
        mtvBack = (TextView) findViewById(R.id.tv_check_back);
        mtvRoad = (TextView) findViewById(R.id.tv_check_road);
        mivReporte = (ImageView) findViewById(R.id.iv_check_detail_report);
        mivDealed = (ImageView) findViewById(R.id.iv_check_detail_dealed);
    }

    private void initData() {
        //根据传来的任务单号来获取数据
        //String userName = SpUtils.getString(getApplicationContext(), GlobalContanstant.USERNAME);
        String upload_person_id = detail.getUpload_Person_ID()+"";
        //detail.getr
        String actualCompletion_person_id = detail.getActualCompletion_Person_ID()+"";
        String reviewed_person_id = detail.getReviewed_Person_ID()+"";
        //通过上报人的ID 拿到上报人的名字
        //获取到所有人的列表 把对应的 id 找出名字
        List<String> personNamelist = SpUtils.getStrListValue(getApplicationContext(), GlobalContanstant.PERSONNAMELIST);
        List<String> personIDlist = SpUtils.getStrListValue(getApplicationContext(), GlobalContanstant.PERSONIDLIST);

        for (int i = 0; i < personIDlist.size(); i++) {
            if (upload_person_id.equals(personIDlist.get(i))){
                id = i;
            }
            if (actualCompletion_person_id.equals(personIDlist.get(i))){
                acid = i;
            }
            if (reviewed_person_id.equals(personIDlist.get(i))){
                reviewid = i;
            }
        }

        String userName = personNamelist.get(id);
        String acName = personNamelist.get(acid);
        String reviewName = personNamelist.get(reviewid);


        mtvReporter.setText(userName);
        mtvDealer.setText(acName);

        //没有审核人员的数据
        mtvReviewer.setText(reviewName);


        int disposalLevel_id = detail.getDisposalLevel_ID()-1;
        int level = detail.getLevel();
        mtvDiseaseName.setText(Data.pbname[level]);
        mtvGrade.setText(Data.grades[disposalLevel_id]);
        mtvDealtype.setText(detail.getDealType_Name());
        mtvFatype.setText(detail.getFacilityType_Name());


        mtvPbtype.setText(detail.getDiseaseType_Name());

        mtvReportePlace.setText(detail.getStreetAddress_Name());


        String uploadTime = detail.getUploadTime();
        mtvReporteTime.setText(uploadTime);
        String reviewedTime = detail.getReviewedTime();
        mtvReviewtime.setText(reviewedTime);

        String actualCompletionInfo = detail.getActualCompletionInfo();
        mtvDesc.setText(actualCompletionInfo);


        String requestTime = detail.getRequirementsCompleteTime();
        mtvRequestTime.setText(requestTime);

        String actualCompletionTime = detail.getActualCompletionTime();
        mtvResultTime.setText(actualCompletionTime);

        mtvRoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(v.getContext(),PositionActivity.class);
                intent.putExtra("detail",detail);
                startActivity(intent);
            }
        });

        if (imageUrlReport != null) {
            imageUrl = imageUrlReport.get(position);
            String imgurl = imageUrl.get(0).getImgurl();
            Glide.with(getApplicationContext()).load(imgurl).into(mivReporte);
        }
        if (imageUrlPost != null) {
            imageUrlpost = imageUrlPost.get(position);
            String imgurlpost = imageUrlpost.get(0).getImgurl();
            Glide.with(getApplicationContext()).load(imgurlpost).into(mivDealed);
        }

        mivDealed.setOnClickListener(this);
        mivReporte.setOnClickListener(this);
        mtvPass.setOnClickListener(this);
        mtvBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //点击显示大图
            case R.id.iv_check_detail_report:
                Intent intent = new Intent(v.getContext(),CheckReportBigPhotoActivity.class);
                intent.putExtra("imageurl",(Serializable) imageUrl);
                v.getContext().startActivity(intent);
                break;
            case R.id.iv_check_detail_dealed:
                Intent intent1 = new Intent(v.getContext(),CheckPostBigPhotoActivity.class);
                intent1.putExtra("imageUrlpost",(Serializable) imageUrlpost);
                v.getContext().startActivity(intent1);
                break;
            case R.id.tv_check_back:

                //记录当前状态
                //上传服务器
                new Thread(){
                    @Override
                    public void run() {
                        try {
                            personID = SpUtils.getInt(getApplicationContext(),GlobalContanstant.PERSONID);
                            String result = toInspection(GlobalContanstant.GETUNCHECK,personID);
                            Message message = Message.obtain();
                            message.what = ISUNCHECKPASS;
                            Bundle bundle = new Bundle();
                            bundle.putInt("failpostion",position);
                            bundle.putString("isfail",result);
                            message.setData(bundle);
                            //message.obj =result;
                            handler.sendMessage(message);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
               ;
                break;
            case R.id.tv_check_pass:
                //记录当前状态


                new Thread(){
                    @Override
                    public void run() {
                        try {
                            personID = SpUtils.getInt(getApplicationContext(),GlobalContanstant.PERSONID);
                            String result = toInspection(GlobalContanstant.GETPASSCHECK, personID);
                            Message message = Message.obtain();
                            message.what = ISCHECKPASS;
                            Bundle bundle = new Bundle();
                            bundle.putInt("passpostion",position);
                            bundle.putString("ispass",result);
                            message.setData(bundle);
                            handler.sendMessage(message);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }.start();


                break;
        }


    }




    private String toInspection(int phaseIndication, int personID)throws Exception{
        SoapObject soapObject = new SoapObject(NetUrl.nameSpace, NetUrl.checkmethodName);
        soapObject.addProperty("TaskNumber", detail.getTaskNumber());
        soapObject.addProperty("PhaseIndication", phaseIndication);
        soapObject.addProperty("PersonId", personID);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
        envelope.setOutputSoapObject(soapObject);
        envelope.dotNet = true;
        envelope.bodyOut = soapObject;


        HttpTransportSE httpTransportSE = new HttpTransportSE(NetUrl.SERVERURL);

        httpTransportSE.call(NetUrl.toInspection_SOAP_ACTION, envelope);
        SoapObject object = (SoapObject) envelope.bodyIn;
        String result =  object.getProperty(0).toString();
        return result;
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        //goHome();
    }


    private void goHome() {
        Intent intent = new Intent(CheckDetailActivity.this,HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("backHome",GlobalContanstant.BACKHOME);
        startActivity(intent);
        finish();
    }
}
