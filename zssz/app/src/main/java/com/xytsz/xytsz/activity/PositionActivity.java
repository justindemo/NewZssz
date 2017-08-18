package com.xytsz.xytsz.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.model.LatLng;
import com.xytsz.xytsz.R;
import com.xytsz.xytsz.bean.Review;

import java.util.ArrayList;

/**
 * Created by admin on 2017/3/7.
 * 位置界面
 */
public class PositionActivity extends AppCompatActivity {


    private BaiduMap map;

    private Review.ReviewRoad.ReviewRoadDetail detail;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getIntent()!= null){
            detail = (Review.ReviewRoad.ReviewRoadDetail) getIntent().getSerializableExtra("detail");
        }

        setContentView(R.layout.activity_postion);
        MapView  mMapView = (MapView) findViewById(R.id.map_position);
        mMapView.showZoomControls(false);
        map = mMapView.getMap();

        map.setMapStatus(MapStatusUpdateFactory.zoomTo(17));
        double latitude = detail.getLatitude();
        double longitude = detail.getLongitude();
        marke(latitude,longitude);
    }

    private void marke(double latitude, double longitude) {
       //拿到要maker的图片

        BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.mipmap.icon_position);
        ArrayList<BitmapDescriptor> bitmaps = new ArrayList<>();
        bitmaps.add(BitmapDescriptorFactory.fromResource(R.mipmap.icon_twinkle));
        bitmaps.add(bitmapDescriptor);


        LatLng latLng = new LatLng(latitude,longitude);
        map.setMapStatus(MapStatusUpdateFactory.newLatLng(latLng));

        MarkerOptions options =new MarkerOptions();
        options.position(latLng).icons(bitmaps);
        map.clear();
        map.addOverlay(options);
    }


}
