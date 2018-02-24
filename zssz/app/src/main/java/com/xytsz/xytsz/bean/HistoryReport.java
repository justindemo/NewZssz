package com.xytsz.xytsz.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by admin on 2017/9/15.
 * 历史举报
 */
public class HistoryReport implements Serializable {
    /**
     * ID : 3
     * name : 测试
     * dealtype_id : 1
     * info : 测试
     * longitude : 116.355943
     * latitude : 39.768523
     * tasknumber : 20171211165753
     * phaseindication : 1
     * tel : 15235666548
     * uploadtime : 2017/12/11 16:57:42
     * reviewedTime : 2017/12/11 18:21:55
     * dealedTime : 2017/12/11 18:21:55
     * checkedTime : 2017/12/11 18:21:55
     * upload_Person_name : 测试
     * reviewed_Person_Name : 向阳天
     * dealed_Person_name : 向阳天
     * checked_Person_Name : 向阳天
     * imglist : ["http://123.126.40.12:8081/UpLoadMedia/xytsz/201712111657430.jpg","http://123.126.40.12:8081/UpLoadMedia/xytsz/201712111657431.jpg","http://123.126.40.12:8081/UpLoadMedia/xytsz/201712111657432.jpg"]
     */

    private int ID;
    private String name;
    private int dealtype_id;
    private String info;
    private String longitude;
    private String latitude;
    private String tasknumber;
    private int phaseindication;
    private String tel;
    private String uploadtime;
    private String reviewedTime;
    private String dealedTime;
    private String checkedTime;
    private String upload_Person_name;
    private String reviewed_Person_Name;
    private String dealed_Person_name;
    private String checked_Person_Name;
    private List<String> imglist;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
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

    public int getPhaseindication() {
        return phaseindication;
    }

    public void setPhaseindication(int phaseindication) {
        this.phaseindication = phaseindication;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getUploadtime() {
        return uploadtime;
    }

    public void setUploadtime(String uploadtime) {
        this.uploadtime = uploadtime;
    }

    public String getReviewedTime() {
        return reviewedTime;
    }

    public void setReviewedTime(String reviewedTime) {
        this.reviewedTime = reviewedTime;
    }

    public String getDealedTime() {
        return dealedTime;
    }

    public void setDealedTime(String dealedTime) {
        this.dealedTime = dealedTime;
    }

    public String getCheckedTime() {
        return checkedTime;
    }

    public void setCheckedTime(String checkedTime) {
        this.checkedTime = checkedTime;
    }

    public String getUpload_Person_name() {
        return upload_Person_name;
    }

    public void setUpload_Person_name(String upload_Person_name) {
        this.upload_Person_name = upload_Person_name;
    }

    public String getReviewed_Person_Name() {
        return reviewed_Person_Name;
    }

    public void setReviewed_Person_Name(String reviewed_Person_Name) {
        this.reviewed_Person_Name = reviewed_Person_Name;
    }

    public String getDealed_Person_name() {
        return dealed_Person_name;
    }

    public void setDealed_Person_name(String dealed_Person_name) {
        this.dealed_Person_name = dealed_Person_name;
    }

    public String getChecked_Person_Name() {
        return checked_Person_Name;
    }

    public void setChecked_Person_Name(String checked_Person_Name) {
        this.checked_Person_Name = checked_Person_Name;
    }

    public List<String> getImglist() {
        return imglist;
    }

    public void setImglist(List<String> imglist) {
        this.imglist = imglist;
    }


   /* *//**
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
     *//*

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
    }*/



}
