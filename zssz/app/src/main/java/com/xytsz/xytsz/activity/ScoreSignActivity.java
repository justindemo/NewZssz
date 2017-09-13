package com.xytsz.xytsz.activity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.xytsz.xytsz.R;
import com.xytsz.xytsz.adapter.ScoreSignAdapter;
import com.xytsz.xytsz.global.GlobalContanstant;
import com.xytsz.xytsz.net.NetUrl;
import com.xytsz.xytsz.ui.LineView;
import com.xytsz.xytsz.util.IntentUtil;
import com.xytsz.xytsz.util.SpUtils;
import com.xytsz.xytsz.util.ToastUtil;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.zip.Inflater;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by admin on 2017/7/17.
 * 签到: 领奖
 */
public class ScoreSignActivity extends AppCompatActivity {

    private static final int FAIL = 404;
    @Bind(R.id.scoresign_recycleview)
    RecyclerView scoresignRecycleview;

    private static final int SIGN = 122222;
    private List<String> list = new ArrayList<>();
    private RelativeLayout mrlSign;
    private RelativeLayout mrlScore;
    private TextView mtvSign;
    private TextView mtvScore;
    private boolean isSign;
    private String signed;
    private String tip;
    private String error;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SIGN:
                    String sign = (String) msg.obj;
                    if (sign.equals("true")) {
                        mtvSign.setText(signed);
                        ToastUtil.shortToast(getApplicationContext(),signSuccess);
                    }
                    break;
                case FAIL:
                    ToastUtil.shortToast(getApplicationContext(), error);
                    break;
            }
        }
    };
    private String phone;
    private String signSuccess;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoresign);
        ButterKnife.bind(this);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            String title = getString(R.string.sign_title);
            actionBar.setTitle(title);

        }

        signed = getString(R.string.signed);
        tip = getString(R.string.sign_tip);

        phone = SpUtils.getString(getApplicationContext(), GlobalContanstant.LOGINID);

        isSign = SpUtils.getBoolean(getApplicationContext(), GlobalContanstant.SIGN, false);
        signSuccess = getString(R.string.socre_sign_success);

        error = getString(R.string.visitor_neterror);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        scoresignRecycleview.setLayoutManager(manager);

        list.clear();
        list.add("https://ws1.sinaimg.cn/large/610dc034gy1fh9utulf4kj20u011itbo.jpg");
        list.add("https://ws1.sinaimg.cn/large/610dc034gy1fh9utulf4kj20u011itbo.jpg");
        list.add("https://ws1.sinaimg.cn/large/610dc034gy1fh9utulf4kj20u011itbo.jpg");


        //
        long time = System.currentTimeMillis();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);

        int hour = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);


        ScoreSignAdapter adapter = new ScoreSignAdapter(list, this);
        scoresignRecycleview.setAdapter(adapter);

        View headView = getLayoutInflater().inflate(R.layout.scoresign_headview, scoresignRecycleview, false);

        mrlSign = (RelativeLayout) headView.findViewById(R.id.rl_headview_sign);
        mrlScore = (RelativeLayout) headView.findViewById(R.id.rl_headview_score);

        mtvSign = (TextView) headView.findViewById(R.id.tv_headview_sign);
        mtvScore = (TextView) headView.findViewById(R.id.tv_headview_myscorenum);

        if (isSign) {
            mtvSign.setText(signed);
        }
        if (hour == 0 && minute == 0) {
            mtvSign.setText(R.string.sign);
        }
        if (isSign) {
            mtvSign.setText(signed);
        }

        mtvSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new Thread() {
                    @Override
                    public void run() {
                        try {

                            String sign = signed();
                            Message message = Message.obtain();
                            message.obj = sign;
                            message.what = SIGN;
                            handler.sendMessage(message);
                        } catch (Exception e) {
                            Message message = Message.obtain();
                            message.what = FAIL;
                            handler.sendMessage(message);

                        }
                    }
                }.start();

                SpUtils.saveBoolean(getApplicationContext(), GlobalContanstant.SIGN, true);
                if (TextUtils.equals(mtvSign.getText().toString(), signed)) {
                    ToastUtil.shortToast(ScoreSignActivity.this, tip);
                }

                mtvSign.setText(signed);
                Intent intent = getIntent();
                setResult(200, intent);
            }
        });


        mrlScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentUtil.startActivity(v.getContext(), MyScoreActivity.class);

            }
        });


        LinearLayout mline = (LinearLayout) headView.findViewById(R.id.ll_line);
        LineView lineView = new LineView(getApplicationContext(), 200);

        lineView.updateX();


        mline.removeAllViews();
        mline.addView(lineView);


        adapter.addHeaderView(headView);

    }

    private String signed() throws Exception {
        SoapObject soapObject = new SoapObject(NetUrl.nameSpace, NetUrl.isSignMethodname);
        soapObject.addProperty("tel","1523566548");

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
        envelope.dotNet = true;
        envelope.bodyOut = soapObject;
        envelope.setOutputSoapObject(soapObject);

        HttpTransportSE httpTransportSE = new HttpTransportSE(NetUrl.SERVERURL);

        httpTransportSE.call(NetUrl.isSign_SOAP_ACITON, envelope);

        SoapObject object = (SoapObject) envelope.bodyIn;
        String result = object.getProperty(0).toString();
        return result;
    }


    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }


}
