package com.xytsz.xytsz.activity;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.io.Serializable;

/**
 * Created by admin on 2017/8/15.
 *
 */
public class MemberShow extends MultiItemEntity implements Serializable{


    /**
     * imgurl : http://110.173.0.231:10003/headimg/bbb.jpg
     * enname : 大兴协会
     * name : 赵云
     * url :
     * tel : 12345678910
     * ison : false
     * longitude : 116.356169
     * latitude : 39.768144
     */

    private String imgurl;
    private String enname;
    private String name;
    private String url;
    private String tel;
    private boolean ison;
    private double longitude;
    private double latitude;

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getEnname() {
        return enname;
    }

    public void setEnname(String enname) {
        this.enname = enname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public boolean isIson() {
        return ison;
    }

    public void setIson(boolean ison) {
        this.ison = ison;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}
