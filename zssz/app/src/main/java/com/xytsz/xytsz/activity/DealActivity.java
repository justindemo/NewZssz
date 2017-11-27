package com.xytsz.xytsz.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.xytsz.xytsz.R;
import com.xytsz.xytsz.adapter.DealAdapter;
import com.xytsz.xytsz.bean.Review;
import com.xytsz.xytsz.global.GlobalContanstant;
import com.xytsz.xytsz.net.NetUrl;
import com.xytsz.xytsz.util.JsonUtil;
import com.xytsz.xytsz.util.SpUtils;
import com.xytsz.xytsz.util.ToastUtil;

import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.List;

/**
 * Created by admin on 2017/2/15.
 * 处置界面
 * 处置界面是根据下派 来获取的需要处置的信息
 */
public class DealActivity extends AppCompatActivity {

    private static final int ISDEAL = 3003;
    private static final int FAIL = 500;
    private ListView mLv;
    private int personID;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case ISDEAL:
                    mProgressBar.setVisibility(View.GONE);
                    mtvfail.setText(nodata);
                    mtvfail.setVisibility(View.VISIBLE);
                    break;
                case FAIL:
                    mProgressBar.setVisibility(View.GONE);
                    ToastUtil.shortToast(getApplicationContext(), "未获取数据,请稍后");
                    break;
            }
        }
    };
    private ProgressBar mProgressBar;
    private List<Review.ReviewRoad> list;
    private TextView mtvfail;
    private String nodata;
    private DealAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deal);


        personID = SpUtils.getInt(getApplicationContext(), GlobalContanstant.PERSONID);
        nodata = getString(R.string.deal_nodata);

        initAcitionbar();
        initView();

        initData();
    }

    private void initView() {
        mLv = (ListView) findViewById(R.id.Lv_deal);
        mProgressBar = (ProgressBar) findViewById(R.id.review_progressbar);
        mtvfail = (TextView) findViewById(R.id.tv_deal_fail);
    }

    private void initData() {

        mProgressBar.setVisibility(View.VISIBLE);
        new Thread() {
            @Override
            public void run() {
                try {

                    String dealData = getDealData(GlobalContanstant.GETDEAL, personID);
                    if (dealData != null) {

                        Review review = JsonUtil.jsonToBean(dealData, Review.class);
                        list = review.getReviewRoadList();
                        if (list.size() == 0) {

                            Message message = Message.obtain();
                            message.what = ISDEAL;
                            handler.sendMessage(message);

                        } else {

                            adapter = new DealAdapter(list);

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    mLv.setAdapter(adapter);

                                    mProgressBar.setVisibility(View.GONE);

                                }
                            });

                        }
                    }
                } catch (Exception e) {
                    Message message = Message.obtain();
                    message.what = FAIL;
                    handler.sendMessage(message);
                }
            }
        }.start();


        mLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(DealActivity.this, MakerActivty.class);
                intent.putExtra("position", position);
                startActivity(intent);
            }
        });
    }

    public static String getDealData(int phaseIndication, int personID) throws Exception {
        SoapObject soapObject = new SoapObject(NetUrl.nameSpace, NetUrl.getManagementList);
        soapObject.addProperty("PhaseIndication", phaseIndication);
        soapObject.addProperty("personId", personID);
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapSerializationEnvelope.VER12);
        envelope.bodyOut = soapObject;//由于是发送请求，所以是设置bodyOut
        envelope.dotNet = true;
        envelope.setOutputSoapObject(soapObject);

        HttpTransportSE httpTransportSE = new HttpTransportSE(NetUrl.SERVERURL);
        httpTransportSE.call(NetUrl.getManagementList_SOAP_ACTION, envelope);

        SoapObject object = (SoapObject) envelope.bodyIn;
        String json = object.getProperty(0).toString();

        Log.i("json", json);
        return json;
    }

    private void initAcitionbar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setTitle(R.string.deal);
        }
    }


    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }


}
