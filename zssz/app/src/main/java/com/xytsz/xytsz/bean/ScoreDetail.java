package com.xytsz.xytsz.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by admin on 2017/9/15.
 * 積分明細
 */
public class ScoreDetail implements Serializable{


    /**
     * Dirty : true
     * Key :
     * SubEntities : []
     * id : 1
     * info : 举报
     * integral : 5
     * tel : 15218730281
     * time : 09 15 2017  4:30PM
     */

    private boolean Dirty;
    private String Key;
    private int id;
    private String info;
    private int integral;
    private String tel;
    private String time;
    private List<?> SubEntities;

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getIntegral() {
        return integral;
    }

    public void setIntegral(int integral) {
        this.integral = integral;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<?> getSubEntities() {
        return SubEntities;
    }

    public void setSubEntities(List<?> SubEntities) {
        this.SubEntities = SubEntities;
    }
}
