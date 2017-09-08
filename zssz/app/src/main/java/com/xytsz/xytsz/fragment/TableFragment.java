package com.xytsz.xytsz.fragment;


import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;

import android.widget.AbsListView;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;


import com.baidu.mapapi.map.BaiduMap;

import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.model.LatLng;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ValueFormatter;
import com.google.gson.reflect.TypeToken;
import com.xytsz.xytsz.R;
import com.xytsz.xytsz.adapter.TableAdapter;
import com.xytsz.xytsz.base.BaseFragment;
import com.xytsz.xytsz.bean.SzUserInfo;
import com.xytsz.xytsz.net.NetUrl;
import com.xytsz.xytsz.ui.ListViewInScrollView;
import com.xytsz.xytsz.util.JsonUtil;
import com.xytsz.xytsz.util.ToastUtil;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by admin on 2017/1/4.
 * <p/>
 * 数据界面
 */
public class TableFragment extends BaseFragment {

    private static final int FAIL = 505;
    private BarChart mBarchart;
    private MapView mMv;
    private ListViewInScrollView mLv;
    private LatLng latlng = new LatLng(39.768234, 116.355976);
    private List<Integer> list = new ArrayList<>();
    private List<String> memberList = new ArrayList<>();
    private ScrollView mScroll;


    private List<SzUserInfo> infos;
    public static final int USERINFO = 100100;

    private Handler handler = new Handler() {


        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case USERINFO:
                    infos = (List<SzUserInfo>) msg.obj;
                    if (infos != null) {
                        BarData barData = getBarChartData(infos.size(), 4000);
                        //设置柱状图
                        setBarData(mBarchart, barData);
                        //添加表数据
                        //设置地图,添加数据
                        setMapView();
                        //设置lv
                        setListView();
                    }
                    break;

                case FAIL:
                    mrlnoData.setVisibility(View.VISIBLE);
                    mbtRefresh.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            initData();
                        }
                    });
                    ToastUtil.shortToast(getContext(), nodata);
                    break;
            }
        }
    };
    private String nodata;
    private String title;
    private RelativeLayout mrlnoData;
    private Button mbtRefresh;

    @Override
    public View initView() {
        View view = View.inflate(getActivity(), R.layout.fragment_table, null);
        mScroll = (ScrollView) view.findViewById(R.id.root_scroll);
        mBarchart = (BarChart) view.findViewById(R.id.bar_chart);
        mMv = (MapView) view.findViewById(R.id.mv_table);
        mLv = (ListViewInScrollView) view.findViewById(R.id.lv_table);

        mrlnoData = (RelativeLayout) view.findViewById(R.id.rl_nodata);
        mbtRefresh = (Button) view.findViewById(R.id.table_refresh);

        return view;
    }

    @Override
    public void initData() {
        nodata = getString(R.string.table_nodata);
        title = getString(R.string.table_top_title);

        new Thread() {
            @Override
            public void run() {

                try {
                    String json = getSZDisCountInfo(NetUrl.getszUserCountInfomethod, NetUrl.getszUserCountInfo_SOAP_ACTION);
                    List<SzUserInfo> list = JsonUtil.jsonToBean(json, new TypeToken<List<SzUserInfo>>() {
                    }.getType());

                    if (list != null) {
                        if (list.size() != 0) {
                            Message message = Message.obtain();
                            message.obj = list;
                            message.what = USERINFO;
                            handler.sendMessage(message);
                        }
                    } else {
                        Message message = Message.obtain();
                        message.what = FAIL;
                        handler.sendMessage(message);
                    }
                } catch (Exception e) {

                    Message message = Message.obtain();
                    message.what = FAIL;
                    handler.sendMessage(message);
                }

            }
        }.start();



    }


    private String getSZDisCountInfo(String method, String soap_action) throws Exception {
        SoapObject soapObject = new SoapObject(NetUrl.nameSpace, method);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
        envelope.bodyOut = soapObject;
        envelope.dotNet = true;
        envelope.setOutputSoapObject(soapObject);

        HttpTransportSE httpTransportSE = new HttpTransportSE(NetUrl.SERVERURL);
        httpTransportSE.call(soap_action, envelope);

        SoapObject object = (SoapObject) envelope.bodyIn;

        return object.getProperty(0).toString();
    }

    private void setListView() {
        list.clear();
        memberList.clear();
        for (int i = 0; i < infos.size(); i++) {

            list.add(infos.get(i).getCount());
            memberList.add(infos.get(i).getName());
        }


        final TableAdapter adapter = new TableAdapter(list, memberList);
        mLv.setAdapter(adapter);

        mLv.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem == 0) {
                    View firstview = mLv.getChildAt(0);
                    if (firstview != null && firstview.getTop() == 0) {
                        //滚动到顶部了
                        mLv.getParent().requestDisallowInterceptTouchEvent(false);
                    }
                } else if (firstVisibleItem + visibleItemCount == totalItemCount) {
                    View lastView = mLv.getChildAt(mLv.getChildCount() - 1);
                    if (lastView != null && lastView.getBottom() == mLv.getHeight()) {
                        mLv.getParent().requestDisallowInterceptTouchEvent(false);
                    }
                }

            }
        });
    }

    private boolean flag;
    private List<LatLng> latLngs = new ArrayList<>();

    private void setMapView() {


        mMv.showZoomControls(true);
        mMv.showScaleControl(false);
        BaiduMap map = mMv.getMap();
        View v = mMv.getChildAt(0);

        v.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    mScroll.requestDisallowInterceptTouchEvent(false);
                } else {
                    mScroll.requestDisallowInterceptTouchEvent(true);

                }
                return false;
            }
        });


        map.setMapStatus(MapStatusUpdateFactory.zoomTo(7));
        map.setMapStatus(MapStatusUpdateFactory.newLatLng(latlng));
        list.clear();
        list.add(4);
        list.add(1);

        latLngs.clear();
        latLngs.add(latlng);
        latLngs.add(new LatLng(47.379552, 123.691898));

        for (int i = 0; i < 2; i++) {
            View view = View.inflate(getContext(), R.layout.pop_tv, null);
            TextView tv = (TextView) view.findViewById(R.id.tv_number);
            tv.setText(list.get(i) + "");
            view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
            view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
            view.buildDrawingCache();


            Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
            BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(bitmap);

            MarkerOptions options = new MarkerOptions();
            options.position(latLngs.get(i)).icon(bitmapDescriptor);

            map.addOverlay(options);
        }


    }

    private void setBarData(BarChart barChart, BarData barData) {
        barChart.setDrawBorders(false);  ////是否在折线图上添加边框

        barChart.setDescription("");// 数据描述

        // 如果没有数据的时候，会显示这个，类似ListView的EmptyView
        barChart.setNoDataTextDescription("You need to provide data for the chart.");

        barChart.setDrawGridBackground(false); // 是否显示表格颜色
        barChart.setGridBackgroundColor(Color.WHITE & 0x70FFFFFF); // 表格的的颜色，在这里是是给颜色设置一个透明度

        barChart.setTouchEnabled(true); // 设置是否可以触摸

        barChart.setDragEnabled(true);// 是否可以拖拽
        barChart.setScaleEnabled(true);// 是否可以缩放

        barChart.setPinchZoom(false);//

//      barChart.setBackgroundColor();// 设置背景
        barChart.getAxisRight().setEnabled(false);

        barChart.setDrawBarShadow(true);

        barChart.setData(barData); // 设置数据   // 设置数据

        Legend mLegend = barChart.getLegend(); // 设置比例图标示

        mLegend.setForm(Legend.LegendForm.CIRCLE);// 样式
        mLegend.setFormSize(6f);// 字体
        mLegend.setTextColor(Color.BLACK);// 颜色

        //X轴设定
        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        barChart.animateX(2500);


    }

    private BarData getBarChartData(int count, int range) {
        ArrayList<String> xValues = new ArrayList<String>();
        ArrayList<BarEntry> yValues = new ArrayList<BarEntry>();

        for (int i = 0; i < count; i++) {
            xValues.add(infos.get(i).getName());
            float value = (float) (infos.get(i).getCount());
            yValues.add(new BarEntry(value, i));
        }

        // y轴的数据集合
        BarDataSet barDataSet = new BarDataSet(yValues, title);

        barDataSet.setColor(Color.parseColor("#F36c21"));

        barDataSet.setValueTextSize(13f);
        barDataSet.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                int y = (int) value;

                String s = y + "";

                return s;
            }
        });


        ArrayList<BarDataSet> barDataSets = new ArrayList<BarDataSet>();
        barDataSets.add(barDataSet); // add the datasets

        BarData barData = new BarData(xValues, barDataSets);
        return barData;
    }

    @Override
    public void onResume() {
        mMv.onResume();
        super.onResume();
    }

    @Override
    public void onPause() {
        mMv.onPause();
        super.onPause();

    }

    @Override
    public void onDestroy() {
        mMv.onDestroy();
        super.onDestroy();
    }


}
