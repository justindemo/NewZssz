package com.xytsz.xytsz.bean;

/**
 * Created by admin on 2017/4/12.
 * 设施名称
 */
public class FacilityName {


    /**
     * FacilityName_ID : 1
     * FacilityType_ID : 1
     * FacilityName_Name : 高粱红花岗岩砖
     */

    private int FacilityName_ID;
    private int FacilityType_ID;
    private String FacilityName_Name;

    public int getFacilityName_ID() {
        return FacilityName_ID;
    }

    public void setFacilityName_ID(int FacilityName_ID) {
        this.FacilityName_ID = FacilityName_ID;
    }

    public int getFacilityType_ID() {
        return FacilityType_ID;
    }

    public void setFacilityType_ID(int FacilityType_ID) {
        this.FacilityType_ID = FacilityType_ID;
    }

    public String getFacilityName_Name() {
        return FacilityName_Name;
    }

    public void setFacilityName_Name(String FacilityName_Name) {
        this.FacilityName_Name = FacilityName_Name;
    }
}
