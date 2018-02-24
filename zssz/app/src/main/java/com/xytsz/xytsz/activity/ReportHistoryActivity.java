package com.xytsz.xytsz.activity;

import android.content.Intent;
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

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.reflect.TypeToken;
import com.xytsz.xytsz.R;
import com.xytsz.xytsz.adapter.ReportHistoryAdapter;
import com.xytsz.xytsz.bean.HistoryReport;
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

/**
 * Created by admin on 2017/7/20.
 * <p/>
 * 历史举报
 */
public class ReportHistoryActivity extends AppCompatActivity {

    private static final int SUCCESS = 500;
    private static final int FAIL = 404;
    @Bind(R.id.reporthistory_recycleView)
    RecyclerView reporthistoryRecycleView;
    @Bind(R.id.reporthistory_progressbar)
    ProgressBar reporthistoryProgressbar;

    private List<HistoryReport> list = new ArrayList<>();
    private String error;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SUCCESS:

                    list = (List<HistoryReport>) msg.obj;
                    if (list != null) {
                        if (list.size() == 0) {
                            reporthistoryProgressbar.setVisibility(View.GONE);
                            ToastUtil.shortToast(getApplicationContext(), nodata);
                        } else {
                            ReportHistoryAdapter adapter = new ReportHistoryAdapter(list);
                            reporthistoryProgressbar.setVisibility(View.GONE);
                            reporthistoryRecycleView.setAdapter(adapter);

                            adapter.setOnRecyclerViewItemChildClickListener(new BaseQuickAdapter.OnRecyclerViewItemChildClickListener() {
                                @Override
                                public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                                    List<String> imglist = list.get(position).getImglist();
                                    Intent intent = new Intent(ReportHistoryActivity.this, PersonReportPhotoShowActivity.class);
                                    intent.putExtra("reportimageUrllist", (Serializable) imglist);
                                    startActivity(intent);
                                }
                            });


                            adapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
                                @Override
                                public void onItemClick(View view, int position) {
                                    Intent intent = new Intent(ReportHistoryActivity.this,ReportProccessActivity.class);
                                    intent.putExtra("detail",list.get(position));
                                    intent.putExtra("reportimageUrllist", (Serializable) list.get(position).getImglist());
                                    startActivity(intent);
                                }
                            });
                        }
                    }
                    break;
                case FAIL:
                    reporthistoryProgressbar.setVisibility(View.GONE);
                    ToastUtil.shortToast(getApplicationContext(), error);
                    break;
            }
        }
    };
    private String phone;
    private String nodata;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporthistory);
        ButterKnife.bind(this);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("举报历史");

        }
        error = getString(R.string.login_neterror);
        phone = SpUtils.getString(getApplicationContext(), GlobalContanstant.LOGINID);
        nodata = getString(R.string.review_nodata);

        initData();

        LinearLayoutManager manager = new LinearLayoutManager(this);
        reporthistoryRecycleView.setLayoutManager(manager);


    }

    private void initData() {
        reporthistoryProgressbar.setVisibility(View.VISIBLE);
        new Thread() {
            @Override
            public void run() {
                String data = null;
                try {
                    data = getData();
                    List<HistoryReport> list = JsonUtil.jsonToBean(data, new TypeToken<List<HistoryReport>>() {
                    }.getType());

                    Message message = Message.obtain();
                    message.obj = list;
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

    private String getData() throws Exception {

        SoapObject soapObject = new SoapObject(NetUrl.nameSpace, NetUrl.getHistoryMethodname);
        soapObject.addProperty("tel", phone);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
        envelope.dotNet = true;
        envelope.bodyOut = soapObject;
        envelope.setOutputSoapObject(soapObject);

        HttpTransportSE httpTransportSE = new HttpTransportSE(NetUrl.SERVERURL);

        httpTransportSE.call(NetUrl.getHistory_SOAP_ACTION, envelope);

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
