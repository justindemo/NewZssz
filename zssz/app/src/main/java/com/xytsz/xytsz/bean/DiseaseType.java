package com.xytsz.xytsz.bean;

/**
 * Created by admin on 2017/4/12.
 * 病害类型
 */
public class DiseaseType {


    /**
     * ID : 1
     * FacilityType_ID : 1
     * DiseaseType_Name : 沉陷
     */

    private int ID;
    private int FacilityType_ID;
    private String DiseaseType_Name;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getFacilityType_ID() {
        return FacilityType_ID;
    }

    public void setFacilityType_ID(int FacilityType_ID) {
        this.FacilityType_ID = FacilityType_ID;
    }

    public String getDiseaseType_Name() {
        return DiseaseType_Name;
    }

    public void setDiseaseType_Name(String DiseaseType_Name) {
        this.DiseaseType_Name = DiseaseType_Name;
    }
}
