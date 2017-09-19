package com.xytsz.xytsz.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by admin on 2017/9/15.
 * 历史举报
 */
public class HistoryReport implements Serializable {


    /**
     * imglist : ["http://123.126.40.12:8081/UpLoadMedia/xytsz/201709131528010.jpg ","http://123.126.40.12:8081/UpLoadMedia/xytsz/201709131528011.jpg ","http://123.126.40.12:8081/UpLoadMedia/xytsz/201709131528012.jpg "]
     * uploadtime :
     * ID : 37
     * tel : 15218730281
     * name : tea
     * dealtype_id : 0
     * info :
     * longitude : 116.355832
     * latitude : 39.76854
     * tasknumber : 20170913152817
     * Dirty : true
     * Key :
     * SubEntities : []
     */

    private String uploadtime;
    private int ID;
    private String tel;
    private String name;
    private int dealtype_id;
    private String info;
    private String longitude;
    private String latitude;
    private String tasknumber;
    private boolean Dirty;
    private String Key;
    private List<String> imglist;
    private List<?> SubEntities;

    public String getUploadtime() {
        return uploadtime;
    }

    public void setUploadtime(String uploadtime) {
        this.uploadtime = uploadtime;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDealtype_id() {
        return dealtype_id;
    }

    public void setDealtype_id(int dealtype_id) {
        this.dealtype_id = dealtype_id;
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

    public String getTasknumber() {
        return tasknumber;
    }

    public void setTasknumber(String tasknumber) {
        this.tasknumber = tasknumber;
    }

    public boolean isDirty() {
        return Dirty;
    }

    public void setDirty(boolean Dirty) {
        this.Dirty = Dirty;
    }

    public String getKey() {
        return Key;
    }

    public void setKey(String Key) {
        this.Key = Key;
    }

    public List<String> getImglist() {
        return imglist;
    }

    public void setImglist(List<String> imglist) {
        this.imglist = imglist;
    }

    public List<?> getSubEntities() {
        return SubEntities;
    }

    public void setSubEntities(List<?> SubEntities) {
        this.SubEntities = SubEntities;
    }
}
