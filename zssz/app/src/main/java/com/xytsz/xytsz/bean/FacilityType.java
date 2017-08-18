package com.xytsz.xytsz.bean;

/**
 * Created by admin on 2017/4/12.
 *
 * 设施类型
 */
public class FacilityType {


    /**
     * DealTypeID : 1
     * FacilityTypeID : 1
     * FacilityTypeName : 步道
     */

    private int DealTypeID;
    private int FacilityTypeID;
    private String FacilityTypeName;

    public int getDealTypeID() {
        return DealTypeID;
    }

    public void setDealTypeID(int DealTypeID) {
        this.DealTypeID = DealTypeID;
    }

    public int getFacilityTypeID() {
        return FacilityTypeID;
    }

    public void setFacilityTypeID(int FacilityTypeID) {
        this.FacilityTypeID = FacilityTypeID;
    }

    public String getFacilityTypeName() {
        return FacilityTypeName;
    }

    public void setFacilityTypeName(String FacilityTypeName) {
        this.FacilityTypeName = FacilityTypeName;
    }
}
