package com.xytsz.xytsz.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MapViewLayoutParams;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.model.LatLng;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xytsz.xytsz.MyApplication;
import com.xytsz.xytsz.R;
import com.xytsz.xytsz.bean.ImageUrl;
import com.xytsz.xytsz.bean.Review;
import com.xytsz.xytsz.global.GlobalContanstant;
import com.xytsz.xytsz.util.JsonUtil;
import com.xytsz.xytsz.util.NativeDialog;
import com.xytsz.xytsz.util.SpUtils;
import com.xytsz.xytsz.util.ToastUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by admin on 2017/2/15.
 * 处置界面
 */
public class MakerActivty extends AppCompatActivity implements BaiduMap.OnMarkerClickListener {

    private static final int ISMAKER = 200001;
    private MapView mMV;
    private BaiduMap baiduMap;
    private View pop;
    private TextView mReportName;
    private TextView mTvStatu;
    private ImageView mIvDealIcon;
    private ImageView mIvReportIcon;
    private Button mBtNavi;
    private Button mBtDetail;
    /**
     * 点击的那个list 就是整个pop 的集合
     */
    private List<Review.ReviewRoad.ReviewRoadDetail> list;


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case ISMAKER:
                    list = (List<Review.ReviewRoad.ReviewRoadDetail>) msg.obj;
                    Log.i("list", "list:" + list);
                    int size = list.size();
                    initPop(list);
                    draw(size, list);

                    break;

                case GlobalContanstant.SENDFAIL:
                    ToastUtil.shortToast(getApplicationContext(),"未获取数据,请稍后");
                    break;

            }
        }
    };
    private List<List<ImageUrl>> imageUrlLists = new ArrayList<>();
    private HashMap<String,String> audioUrls = new HashMap<>();
    private int position;
    private LatLng latlngNow;
    private double mylongitude;
    private double mylatitude;
    private int personID;
    private ImageView mIvDealIcon2;
    private ImageView mIvDealIcon3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getIntent() != null) {
            position = getIntent().getIntExtra("position", 0);
        }

        setContentView(R.layout.activity_deal_maker);

        personID = SpUtils.getInt(getApplicationContext(), GlobalContanstant.PERSONID);
        initAcitionbar();
        initView();
        initData();

    }

    private void initView() {
        mMV = (MapView) findViewById(R.id.mv_maker);
        baiduMap = mMV.getMap();
        baiduMap.setOnMapClickListener(new BaiduMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                pop.setVisibility(View.INVISIBLE);
            }

            @Override
            public boolean onMapPoiClick(MapPoi mapPoi) {
                return false;
            }
        });
    }

    private void initData() {
        //是否显示缩放按钮
        locat();

        //网络

        new Thread() {
            @Override
            public void run() {

                try {
                    //文字json

                    String sendData = DealActivity.getDealData(GlobalContanstant.GETDEAL, personID);
                    if (sendData != null) {

                        Review review = JsonUtil.jsonToBean(sendData, Review.class);

                        Review.ReviewRoad reviewRoad = review.getReviewRoadList().get(position);
                        List<Review.ReviewRoad.ReviewRoadDetail> list = reviewRoad.getList();
                        Message message = Message.obtain();
                        message.what = ISMAKER;
                        message.obj = list;
                        handler.sendMessage(message);


                        audioUrls.clear();
                        //遍历list
                        for (Review.ReviewRoad.ReviewRoadDetail detail : list) {
                            String taskNumber = detail.getTaskNumber();
                            /**
                             * 获取到图片的URl
                             */
                            String json = MyApplication.getAllImagUrl(taskNumber, GlobalContanstant.GETREVIEW);

                            if (json != null) {
                                //String list = new JSONObject(json).getJSONArray("").toString();
                                List<ImageUrl> imageUrlList = new Gson().fromJson(json, new TypeToken<List<ImageUrl>>() {
                                }.getType());
                                for (ImageUrl imageUrl : imageUrlList) {
                                    imageUrl.setTaskNumber(taskNumber);
                                }
                                imageUrlLists.add(imageUrlList);
                            }

                            String audioUrl = RoadActivity.getAudio(taskNumber);

                            audioUrls.put(taskNumber,audioUrl);


                        }


                    }


                } catch (Exception e) {
                    Message message = Message.obtain();

                    message.what = GlobalContanstant.SENDFAIL;
                    handler.sendMessage(message);

                }

            }
        }.start();

    }

    /**
     * @param size :画几个mark
     * @param list :huahua
     */
    private void draw(int size, List<Review.ReviewRoad.ReviewRoadDetail> list) {
        mMV.showZoomControls(false);
        //是否显示地图标尺
        mMV.showScaleControl(false);

        /**
         * bitmaps 是显示跳跃的点
         */
        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromResource(R.mipmap.icon_en);
        ArrayList<BitmapDescriptor> bitmaps = new ArrayList<>();
        bitmaps.add(bitmap);
        bitmaps.add(BitmapDescriptorFactory
                .fromResource(R.mipmap.icon_twinkle));


        for (int i = 0; i < size; i++) {
            Review.ReviewRoad.ReviewRoadDetail reviewRoadDetail = list.get(i);
            double latitude = reviewRoadDetail.getLatitude();
            double longitude = reviewRoadDetail.getLongitude();
            LatLng latLng = new LatLng(latitude, longitude);

            MarkerOptions option = new MarkerOptions()
                    .position(latLng).icons(bitmaps)
                    .title(reviewRoadDetail.getTaskNumber());
            baiduMap.setMapStatus(MapStatusUpdateFactory.zoomTo(18));
            baiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(latLng));
            baiduMap.addOverlay(option);
        }

        // 把事件传递给Marker覆盖物
        baiduMap.setOnMarkerClickListener(this);

    }

    /**
     * pop弹框
     *
     * @param list:xianshi几个pop
     */
    private void initPop(List<Review.ReviewRoad.ReviewRoadDetail> list) {
        pop = View.inflate(this, R.layout.maker_pop, null);
        mReportName = (TextView) pop.findViewById(R.id.report_name);
        mTvStatu = (TextView) pop.findViewById(R.id.tv_statu);
        mIvDealIcon = (ImageView) pop.findViewById(R.id.iv_deal_icon1);
        mIvDealIcon2 = (ImageView) pop.findViewById(R.id.iv_deal_icon2);
        mIvDealIcon3 = (ImageView) pop.findViewById(R.id.iv_deal_icon3);
        mIvReportIcon = (ImageView) pop.findViewById(R.id.marker_reporter_icon);
        mBtNavi = (Button) pop.findViewById(R.id.bt_navi);
        mBtDetail = (Button) pop.findViewById(R.id.bt_mark_detail);

        pop.setVisibility(View.INVISIBLE);

        Review.ReviewRoad.ReviewRoadDetail reviewRoadDetail = list.get(0);
        double latitude = reviewRoadDetail.getLatitude();
        double longitude = reviewRoadDetail.getLongitude();
        LatLng latLng = new LatLng(latitude, longitude);


        MapViewLayoutParams param = new MapViewLayoutParams.Builder()
                .layoutMode(MapViewLayoutParams.ELayoutMode.mapMode)// 使用经纬度模式
                .position(latLng)// 设置控件跟着地图移动
                .width(MapViewLayoutParams.WRAP_CONTENT)
                .height(MapViewLayoutParams.WRAP_CONTENT)
                .build();
        mMV.addView(pop, param);
    }


    @Override
    protected void onResume() {
        super.onResume();
        mMV.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        locationClient.start();
    }

    @Override
    public void onPause() {
        mMV.onPause();
        super.onPause();
        if (locationClient != null){
            locationClient.stop();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMV.onDestroy();
        locationClient.stop();
    }

    private int id;

    @Override
    public boolean onMarkerClick(final Marker marker) {

        for (final Review.ReviewRoad.ReviewRoadDetail detail : list) {
            if (TextUtils.equals(detail.getTaskNumber(), marker.getTitle())) {
                //显示的是要求完成人的信息
                //显示的是userName
                //String userName = SpUtils.getString(getApplicationContext(), GlobalContanstant.USERNAME);
                String upload_person_id = detail.getUpload_Person_ID() + "";
                //通过上报人的ID 拿到上报人的名字
                //获取到所有人的列表 把对应的 id 找出名字
                List<String> personNamelist = SpUtils.getStrListValue(getApplicationContext(), GlobalContanstant.PERSONNAMELIST);
                List<String> personIDlist = SpUtils.getStrListValue(getApplicationContext(), GlobalContanstant.PERSONIDLIST);

                for (int i = 0; i < personIDlist.size(); i++) {
                    if (upload_person_id.equals(personIDlist.get(i))) {
                        id = i;
                    }
                }

                String userName = personNamelist.get(id);

                mReportName.setText(userName);

                //做判断
                if (detail.getPhaseIndication() == 2) {
                    mTvStatu.setText("待处置");
                }

                //病害头像
                if (imageUrlLists.size() != 0) {
                    for (List<ImageUrl> imageUrlList : imageUrlLists) {
                        if (imageUrlList.size() != 0 ) {

                            if (TextUtils.equals(imageUrlList.get(0).getTaskNumber(), marker.getTitle())) {
                                if (imageUrlList.size() == 1) {
                                    Glide.with(getApplicationContext()).load(imageUrlList.get(0).getImgurl()).into(mIvDealIcon);
                                }
                                if (imageUrlList.size() == 2) {
                                    Glide.with(getApplicationContext()).load(imageUrlList.get(0).getImgurl()).into(mIvDealIcon);
                                    Glide.with(getApplicationContext()).load(imageUrlList.get(1).getImgurl()).into(mIvDealIcon2);
                                }
                                if (imageUrlList.size() == 3) {
                                    Glide.with(getApplicationContext()).load(imageUrlList.get(0).getImgurl()).into(mIvDealIcon);
                                    Glide.with(getApplicationContext()).load(imageUrlList.get(1).getImgurl()).into(mIvDealIcon2);
                                    Glide.with(getApplicationContext()).load(imageUrlList.get(2).getImgurl()).into(mIvDealIcon3);
                                }
                            }
                        }else {
                            Glide.with(getApplicationContext()).load(R.mipmap.prepost).into(mIvDealIcon);
                            Glide.with(getApplicationContext()).load(R.mipmap.prepost).into(mIvDealIcon2);
                            Glide.with(getApplicationContext()).load(R.mipmap.prepost).into(mIvDealIcon3);
                        }
                    }
                }
                //头像 ：

                final double latitude = detail.getLatitude();
                final double longitude = detail.getLongitude();

                mBtNavi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //跳转到百度地图导航
                        //病害位置
                        LatLng latLng = new LatLng(latitude, longitude);
                        //定位当前的信息
                        /**
                         * 导航
                         */

                        latlngNow = new LatLng(mylatitude, mylongitude);
                        NativeDialog msgDialog = new NativeDialog(v.getContext(), latlngNow, latLng);
                        msgDialog.show();

                        pop.setVisibility(View.INVISIBLE);

                    }
                });

                mBtDetail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(v.getContext(), DiseaseDetailActivity.class);
                        intent.putExtra("detail", detail);

                        for (Map.Entry<String,String> entry: audioUrls.entrySet()){
                           if( entry.getKey() == detail.getTaskNumber()){
                               intent.putExtra("audioUrl",entry.getValue());
                           }

                        }

                        for (List<ImageUrl> imageUrlList : imageUrlLists) {

                            if (TextUtils.equals(imageUrlList.get(0).getTaskNumber(), marker.getTitle())) {
                                intent.putExtra("imageUrls", (Serializable) imageUrlList);
                            }
                        }
                        startActivity(intent);
                    }
                });
            }
        }


        pop.setVisibility(View.VISIBLE);
        MapViewLayoutParams param = new MapViewLayoutParams.Builder()
                .layoutMode(MapViewLayoutParams.ELayoutMode.mapMode)// 使用经纬度模式
                .position(marker.getPosition())// 设置控件跟着地图移动
                .width(MapViewLayoutParams.WRAP_CONTENT)
                .height(MapViewLayoutParams.WRAP_CONTENT)
                .yOffset(-10)
                .build();
        mMV.updateViewLayout(pop, param);

        return true;
    }


    public BDLocationListener myListener = new MyListener();
    private LocationClient locationClient;

    private void locat() {
        locationClient = new LocationClient(getApplicationContext());
        locationClient.registerLocationListener(myListener);

        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);// 可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");// 可选，默认gcj02，设置返回的定位结果坐标系
        int span = 1000;
        option.setScanSpan(span);// 可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);// 可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);// 可选，默认false,设置是否使用gps
        option.setLocationNotify(false);// 可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIgnoreKillProcess(false);// 可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);// 可选，默认false，设置是否收集CRASH信息，默认收集
        locationClient.setLocOption(option);
        locationClient.start();
    }

    private class MyListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            //获取到经度
            mylongitude = bdLocation.getLongitude();
            Log.i("精度", mylongitude + "");
            //获取到纬度
            mylatitude = bdLocation.getLatitude();
            //经纬度  填的是纬度，经度

        }

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
