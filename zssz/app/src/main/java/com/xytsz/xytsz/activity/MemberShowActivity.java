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
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.reflect.TypeToken;
import com.xytsz.xytsz.R;
import com.xytsz.xytsz.adapter.MemberShowAdapter;
import com.xytsz.xytsz.net.NetUrl;
import com.xytsz.xytsz.util.IntentUtil;
import com.xytsz.xytsz.util.JsonUtil;

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
 * Created by admin on 2017/7/19.
 * <p/>
 * 会员展示
 */
public class MemberShowActivity extends AppCompatActivity {

    @Bind(R.id.membershow_recycleview)
    RecyclerView membershowRecycleview;
    @Bind(R.id.membershow_progressbar)
    ProgressBar membershowProgressbar;

    private String position;
    private static final int ASSIATION = 12213;
    private static final int BUSINESSSTR = 12214;
    private static final int COMPANY = 12215;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case ASSIATION:
                    final List<MemberShow> assiationList = (List<MemberShow>) msg.obj;
                    MemberShowAdapter adapter = new MemberShowAdapter(assiationList);

                    membershowRecycleview.setAdapter(adapter);
                    membershowProgressbar.setVisibility(View.INVISIBLE);
                    adapter.setOnRecyclerViewItemChildClickListener(new BaseQuickAdapter.OnRecyclerViewItemChildClickListener() {
                        @Override
                        public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

                            //返回的数据做判断  如果url 为null;就进入一个为加盟的界面

                            if (TextUtils.isEmpty(assiationList.get(position).getUrl())) {
                                Intent intent = new Intent(MemberShowActivity.this, NoMemberActivity.class);
                                intent.putExtra("dataList", (Serializable) assiationList);
                                intent.putExtra("position", position);
                                startActivity(intent);

                            } else {
                                Intent intent = new Intent(MemberShowActivity.this, MemberShowContentActivity.class);
                                intent.putExtra("url", assiationList.get(position).getUrl());
                                startActivity(intent);

                            }

                        }
                    });
                    break;

                case BUSINESSSTR:
                    final List<MemberShow> businessList = (List<MemberShow>) msg.obj;
                    MemberShowAdapter businessadapter = new MemberShowAdapter(businessList);

                    membershowRecycleview.setAdapter(businessadapter);
                    membershowProgressbar.setVisibility(View.INVISIBLE);
                    businessadapter.setOnRecyclerViewItemChildClickListener(new BaseQuickAdapter.OnRecyclerViewItemChildClickListener() {
                        @Override
                        public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

                            //返回的数据做判断  如果url 为null;就进入一个为加盟的界面

                            if (businessList.get(position).getUrl() == null) {
                                Intent intent = new Intent(MemberShowActivity.this, NoMemberActivity.class);
                                intent.putExtra("dataList", (Serializable) businessList);
                                intent.putExtra("position", position);
                                view.getContext().startActivity(intent);

                            } else {
                                Intent intent = new Intent(MemberShowActivity.this, MemberShowContentActivity.class);

                                intent.putExtra("url", businessList.get(position).getUrl());
                                startActivity(intent);

                            }


                        }
                    });

                    break;
                case COMPANY:
                    final List<MemberShow> companyList = (List<MemberShow>) msg.obj;
                    MemberShowAdapter companyadapter = new MemberShowAdapter(companyList);

                    membershowRecycleview.setAdapter(companyadapter);
                    membershowProgressbar.setVisibility(View.INVISIBLE);

                    companyadapter.setOnRecyclerViewItemChildClickListener(new BaseQuickAdapter.OnRecyclerViewItemChildClickListener() {
                        @Override
                        public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

                            //返回的数据做判断  如果url 为null;就进入一个为加盟的界面

//                            if (companyList.get(position).getUrl() == null) {
                                Intent intent = new Intent(MemberShowActivity.this, NoMemberActivity.class);
                                intent.putExtra("dataList", (Serializable) companyList);
                                intent.putExtra("position", position);
                                view.getContext().startActivity(intent);

//                            } else {
//                                Intent intent = new Intent(MemberShowActivity.this, MemberShowContentActivity.class);
//                                intent.putExtra("url", companyList.get(position).getUrl());
//                                startActivity(intent);
//
//                            }


                        }
                    });
                    break;


            }


        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_membershow);
        ButterKnife.bind(this);

        if (getIntent() != null) {
            position = getIntent().getStringExtra("position");
        }


        initActionBar();
        membershowProgressbar.setVisibility(View.VISIBLE);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        membershowRecycleview.setLayoutManager(manager);

        initData();


    }

    private void initData() {
        if (position.equals("assiation")) {
            //获取到数据
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        String result = getenList(1);
                        //解析
                        List<MemberShow> assiationlist = JsonUtil.jsonToBean(result, new TypeToken<List<MemberShow>>() {
                        }.getType());

                        Message message = Message.obtain();
                        message.obj = assiationlist;
                        message.what = ASSIATION;
                        handler.sendMessage(message);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        } else if (position.equals("businessStr")) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        String result = getenList(2);
                        //解析
                        List<MemberShow> businesslist = JsonUtil.jsonToBean(result, new TypeToken<List<MemberShow>>() {
                        }.getType());

                        Message message = Message.obtain();
                        message.obj = businesslist;
                        message.what = BUSINESSSTR;
                        handler.sendMessage(message);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        } else if (position.equals("company")) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        String result = getenList(3);
                        //解析
                        List<MemberShow> companylist = JsonUtil.jsonToBean(result, new TypeToken<List<MemberShow>>() {
                        }.getType());

                        Message message = Message.obtain();
                        message.obj = companylist;
                        message.what = COMPANY;
                        handler.sendMessage(message);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    /**
     * 初始化actionbar
     */
    private void initActionBar() {
        ActionBar.LayoutParams lp = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
        View actionbarview = View.inflate(this, R.layout.actionbar_layout, null);
        LinearLayout mllView = (LinearLayout) actionbarview.findViewById(R.id.ll_back);
        TextView mtitle = (TextView) actionbarview.findViewById(R.id.actionbar_title);
        ImageView mJoin = (ImageView) actionbarview.findViewById(R.id.actionbar_join);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            actionBar.setDisplayShowCustomEnabled(true);
            actionBar.setCustomView(actionbarview, lp);
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setHomeButtonEnabled(true);
            mtitle.setText("会员展示");


            mJoin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    IntentUtil.startActivity(v.getContext(), MemberJoinActivity.class);
                }
            });
            mllView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });

        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }

    public String getenList(int type) throws Exception {

        SoapObject soapObject = new SoapObject(NetUrl.nameSpace, NetUrl.getenlist);
        soapObject.addProperty("type", type);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
        envelope.dotNet = true;
        envelope.bodyOut = soapObject;
        envelope.setOutputSoapObject(soapObject);

        HttpTransportSE httptransportse = new HttpTransportSE(NetUrl.SERVERURL);
        httptransportse.call(NetUrl.getenlist_SOAP_ACTION, envelope);

        SoapObject object = (SoapObject) envelope.bodyIn;
        String result = object.getProperty(0).toString();
        return result;
    }
}
