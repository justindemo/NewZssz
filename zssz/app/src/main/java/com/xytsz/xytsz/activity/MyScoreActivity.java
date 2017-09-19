package com.xytsz.xytsz.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.xytsz.xytsz.R;
import com.xytsz.xytsz.adapter.MyScoreAdapter;
import com.xytsz.xytsz.bean.ScoreDetail;
import com.xytsz.xytsz.global.GlobalContanstant;
import com.xytsz.xytsz.net.NetUrl;
import com.xytsz.xytsz.util.IntentUtil;
import com.xytsz.xytsz.util.JsonUtil;
import com.xytsz.xytsz.util.SpUtils;
import com.xytsz.xytsz.util.ToastUtil;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by admin on 2017/7/17.
 * <p/>
 * 我的积分界面
 */
public class MyScoreActivity extends AppCompatActivity {

    private static final int SUCCESS = 500;
    private static final int FAIL = 404;
    @Bind(R.id.tv_myscore_help)
    TextView tvMyscoreHelp;
    @Bind(R.id.tv_myscore)
    TextView tvMyscore;
    @Bind(R.id.tv_sign)
    TextView tvSign;
    @Bind(R.id.myscore_recycleview)
    RecyclerView myscoreRecycleview;
    @Bind(R.id.tv_myscore_zero)
    TextView tvMyscoreZero;
    @Bind(R.id.myscore_progressbar)
    ProgressBar myscoreProgressbar;

    private List<ScoreDetail> list = new ArrayList<>();
    private String phone;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SUCCESS:

                    Bundle data = msg.getData();
                    list = (List<ScoreDetail>) data.getSerializable("list");
                    String score = data.getString("score");

                    tvMyscore.setText(score);
                    if (list != null) {
                        if (list.size() == 0) {
                            myscoreProgressbar.setVisibility(View.GONE);
                            tvMyscoreZero.setVisibility(View.VISIBLE);
                        } else {
                            MyScoreAdapter adapter = new MyScoreAdapter(list);
                            myscoreProgressbar.setVisibility(View.GONE);
                            myscoreRecycleview.setAdapter(adapter);
                        }
                    }

                    break;
                case FAIL:
                    ToastUtil.shortToast(getApplicationContext(), error);
                    break;
            }
        }
    };
    private String error;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myscore);
        ButterKnife.bind(this);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            String score = getString(R.string.mine_score);
            actionBar.setTitle(score);

        }

        phone = SpUtils.getString(getApplicationContext(), GlobalContanstant.LOGINID);

        error = getString(R.string.login_neterror);
        initData();

        LinearLayoutManager manager = new LinearLayoutManager(this);
        myscoreRecycleview.setLayoutManager(manager);


    }

    private void initData() {
        myscoreProgressbar.setVisibility(View.VISIBLE);
        new Thread() {
            @Override
            public void run() {
                try {
                    String data = getData();
                    String score = getintegal(phone);

                    List<ScoreDetail> list = JsonUtil.jsonToBean(data, new TypeToken<List<ScoreDetail>>() {
                    }.getType());

                    Message message = Message.obtain();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("list", (Serializable) list);
                    bundle.putString("score",score);
                    message.setData(bundle);
                    message.what = SUCCESS;
                    handler.sendMessage(message);

                } catch (Exception e) {
                    Message message = Message.obtain();
                    message.what = FAIL;
                    handler.sendMessage(message);

                }
            }
        }.start();
    }

    private String getintegal(String phone) throws Exception {

        SoapObject soapObject = new SoapObject(NetUrl.nameSpace, NetUrl.getScoreMethodname);
        soapObject.addProperty("tel", phone);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
        envelope.dotNet = true;
        envelope.bodyOut = soapObject;
        envelope.setOutputSoapObject(soapObject);

        HttpTransportSE httpTransportSE = new HttpTransportSE(NetUrl.SERVERURL);

        httpTransportSE.call(NetUrl.getScore_SOAP_ACTION, envelope);

        SoapObject object = (SoapObject) envelope.bodyIn;
        String result = object.getProperty(0).toString();

        return result;
    }

    private String getData() throws Exception {
        SoapObject soapObject = new SoapObject(NetUrl.nameSpace, NetUrl.getScoreDetailMethodname);
        soapObject.addProperty("tel", phone);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
        envelope.dotNet = true;
        envelope.bodyOut = soapObject;
        envelope.setOutputSoapObject(soapObject);

        HttpTransportSE httpTransportSE = new HttpTransportSE(NetUrl.SERVERURL);

        httpTransportSE.call(NetUrl.getScoreDetail_SOAP_ACTION, envelope);

        SoapObject object = (SoapObject) envelope.bodyIn;
        String result = object.getProperty(0).toString();

        return result;
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    @OnClick({R.id.tv_myscore_help, R.id.tv_sign})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_myscore_help:
                //webView  或者 dialog
                break;
            case R.id.tv_sign:
                //签到界面
                IntentUtil.startActivity(view.getContext(), ScoreSignActivity.class);
                break;
        }
    }
}
