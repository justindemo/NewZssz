package com.xytsz.xytsz.bean;


import android.graphics.Color;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.overlayutil.DrivingRouteOverlay;
import com.baidu.mapapi.search.route.DrivingRoutePlanOption;
import com.xytsz.xytsz.R;

/**
 * Created by admin on 2017/6/30.
 *
 */
public class MyDrivingRouteOverlay extends DrivingRouteOverlay {


    /**
     * 构造函数
     *
     * @param baiduMap 该DrivingRouteOvelray引用的 BaiduMap
     */
    public MyDrivingRouteOverlay(BaiduMap baiduMap) {
        super(baiduMap);
    }


    @Override
    public int getLineColor() {
        return Color.BLUE;
    }

    @Override
    public BitmapDescriptor getStartMarker() {
        return BitmapDescriptorFactory.fromResource(R.mipmap.icon_start);
    }

    @Override
    public BitmapDescriptor getTerminalMarker() {
        return BitmapDescriptorFactory.fromResource(R.mipmap.icon_en);
    }


}
