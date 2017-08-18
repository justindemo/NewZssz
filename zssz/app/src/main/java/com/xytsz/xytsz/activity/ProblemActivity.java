package com.xytsz.xytsz.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.xytsz.xytsz.R;
import com.xytsz.xytsz.adapter.SendAdapter;
import com.xytsz.xytsz.bean.Review;
import com.xytsz.xytsz.net.NetUrl;
import com.xytsz.xytsz.util.IntentUtil;

import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

/**
 * 病害定位
 * Created by admin on 2017/3/1.
 *
 *
 *
 */
public class ProblemActivity extends AppCompatActivity{

    private ListView mLv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_problem);
        initView();
        initData();
    }
    private void initView() {
        mLv = (ListView) findViewById(R.id.Lv_problem);
    }

    private void initData() {

        new Thread(){
            @Override
            public void run() {

                try {
                    String reviewData = getProblemData(1);
                    Gson gson = new Gson();
                    Review review = gson.fromJson(reviewData,Review.class);
                    SendAdapter adapter = new SendAdapter( review.getReviewRoadList());
                    if (adapter != null){
                        mLv.setAdapter(adapter);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();


        mLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                IntentUtil.startActivity(parent.getContext(),MakerProblemActivty.class);
            }
        });
    }

    private String getProblemData(int phaseIndication) throws Exception {
        SoapObject soapObject = new SoapObject(NetUrl.nameSpace,NetUrl.getpersonLocation);
        soapObject.addProperty("PhaseIndication",phaseIndication);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapSerializationEnvelope.VER12);
        envelope.bodyOut = soapObject;//由于是发送请求，所以是设置bodyOut
        envelope.dotNet = true;

        HttpTransportSE httpTransportSE = new HttpTransportSE(NetUrl.SERVERURL);
        httpTransportSE.call(null,envelope);

        SoapObject object = (SoapObject) envelope.bodyIn;
        String json = object.getProperty(0).toString();

        Log.i("json",json);
        return json;

    }
}
