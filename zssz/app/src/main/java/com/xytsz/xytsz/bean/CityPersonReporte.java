package com.xytsz.xytsz.bean;

/**
 * Created by admin on 2017/8/24.
 *
 * 城市举报的bean
 */
public class CityPersonReporte {

    private String telNumber;
    private String name;
    private String tasknumber;
    private int type_id;
    private String info;
    private String longitude;
    private String latitude;
    private String photoName;
    private String iamgeBase64code;

    public String getPhotoName() {
        return photoName;
    }

    public void setPhotoName(String photoName) {
        this.photoName = photoName;
    }

    public String getIamgeBase64code() {
        return iamgeBase64code;
    }

    public void setIamgeBase64code(String iamgeBase64code) {
        this.iamgeBase64code = iamgeBase64code;
    }

    public String getTelNumber() {
        return telNumber;
    }

    public void setTelNumber(String telNumber) {
        this.telNumber = telNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTasknumber() {
        return tasknumber;
    }

    public void setTasknumber(String tasknumber) {
        this.tasknumber = tasknumber;
    }

    public int getType_id() {
        return type_id;
    }

    public void setType_id(int type_id) {
        this.type_id = type_id;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    @Override
    public String toString() {
        return "CityPersonReporte{" +
                "telNumber='" + telNumber + '\'' +
                ", name='" + name + '\'' +
                ", tasknumber='" + tasknumber + '\'' +
                ", type_id=" + type_id +
                ", info='" + info + '\'' +
                ", longitude='" + longitude + '\'' +
                ", latitude='" + latitude + '\'' +
                '}';
    }
}
