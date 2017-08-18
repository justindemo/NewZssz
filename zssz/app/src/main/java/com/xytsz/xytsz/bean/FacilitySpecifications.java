package com.xytsz.xytsz.bean;

/**
 * Created by admin on 2017/4/12.
 * 设施规格
 */
public class FacilitySpecifications {


    /**
     * ID : 1
     * FacilityName_ID : 1
     * FacilitySpecifications_Name : 200*400*60
     */

    private int ID;
    private int FacilityName_ID;
    private String FacilitySpecifications_Name;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getFacilityName_ID() {
        return FacilityName_ID;
    }

    public void setFacilityName_ID(int FacilityName_ID) {
        this.FacilityName_ID = FacilityName_ID;
    }

    public String getFacilitySpecifications_Name() {
        return FacilitySpecifications_Name;
    }

    public void setFacilitySpecifications_Name(String FacilitySpecifications_Name) {
        this.FacilitySpecifications_Name = FacilitySpecifications_Name;
    }
}
