package com.xytsz.xytsz.bean;


import java.io.Serializable;
import java.util.List;


/**
 * Created by admin on 2017/3/14.
 * 审核数据
 */
public class Review implements Serializable {
    private List<ReviewRoad> reviewRoadList;

    public List<ReviewRoad> getReviewRoadList() {
        return reviewRoadList;
    }

    public void setReviewRoadList(List<ReviewRoad> reviewRoadList) {
        this.reviewRoadList = reviewRoadList;
    }

    public static class ReviewRoad implements Serializable {
        /**
         * streetName : 城乡北路
         * list : [{"DI_ID":8,"Level":0,"TaskNumber":"20170411103632","DisposalLevel_ID":null,"DealType_Name":"道路巡察","FacilityType_Name":"步道","DiseaseType_Name":"缺砖","FacilityName_Name":"五莲花花岗岩砖","FacilitySpecifications_Name":"800*400*100 ","StreetAddress_Name":"城乡北路","AddressDescription":"","DiseaseDescription":"车站附近缺砖一块。","Channel":0,"PhaseIndication":1,"UploadTime":"2017/4/14 11:06:53","Upload_Person_ID":1,"reviewed_Person_ID":5,"reviewedTime":"2017/4/14 11:06:53","Issued_Person_ID":5,"IssuedTime":"2017/4/14 11:06:53","RequirementsComplete_Person_ID":5,"RequirementsCompleteTime":"2017/4/14 11:06:53","postedTime":"2017/4/14 11:06:53","DealedTime":"2017/4/14 11:06:53","ActualCompletion_Person_ID":5,"ActualCompletionTime":"2017/4/14 11:06:53","ActualCompletionInfo":"","Checked_Person_ID":null,"CheckedTime":null,"Department_ID":1,"Longitude":"118.025403","Latitude":"37.387388","node":null}]
         */

        private String streetName;
        private List<ReviewRoadDetail> list;

        public String getStreetName() {
            return streetName;
        }

        public void setStreetName(String streetName) {
            this.streetName = streetName;
        }

        public List<ReviewRoadDetail> getList() {
            return list;
        }

        public void setList(List<ReviewRoadDetail> list) {
            this.list = list;
        }

        public static class ReviewRoadDetail implements Serializable{
            /**
             * DI_ID : 8
             * Level : 0
             * TaskNumber : 20170411103632
             * DisposalLevel_ID : null
             * DealType_Name : 道路巡察
             * FacilityType_Name : 步道
             * DiseaseType_Name : 缺砖
             * FacilityName_Name : 五莲花花岗岩砖
             * FacilitySpecifications_Name : 800*400*100
             * StreetAddress_Name : 城乡北路
             * AddressDescription :
             * DiseaseDescription : 车站附近缺砖一块。
             * Channel : 0
             * PhaseIndication : 1
             * UploadTime : 2017/4/14 11:06:53
             * Upload_Person_ID : 1
             * reviewed_Person_ID : 5
             * reviewedTime : 2017/4/14 11:06:53
             * Issued_Person_ID : 5
             * IssuedTime : 2017/4/14 11:06:53
             * RequirementsComplete_Person_ID : 5
             * RequirementsCompleteTime : 2017/4/14 11:06:53
             * postedTime : 2017/4/14 11:06:53
             * DealedTime : 2017/4/14 11:06:53
             * ActualCompletion_Person_ID : 5
             * ActualCompletionTime : 2017/4/14 11:06:53
             * ActualCompletionInfo :
             * Checked_Person_ID : null
             * CheckedTime : null
             * Department_ID : 1
             * Longitude : 118.025403
             * Latitude : 37.387388
             * node : null
             */

            private int DI_ID;
            private int Level;
            private String TaskNumber;
            private int DisposalLevel_ID;
            private String DealType_Name;
            private String FacilityType_Name;
            private String DiseaseType_Name;
            private String FacilityName_Name;
            private String FacilitySpecifications_Name;
            private String StreetAddress_Name;
            private String AddressDescription;
            private String DiseaseDescription;
            private int Channel;
            private int PhaseIndication;
            private String UploadTime;
            private int Upload_Person_ID;
            private int reviewed_Person_ID;
            private String reviewedTime;
            private int Issued_Person_ID;
            private String IssuedTime;
            private int RequirementsComplete_Person_ID;
            private String RequirementsCompleteTime;
            private String postedTime;
            private String DealedTime;
            private int ActualCompletion_Person_ID;
            private String ActualCompletionTime;
            private String ActualCompletionInfo;
            private Object Checked_Person_ID;
            private Object CheckedTime;
            private int Department_ID;
            private String Longitude;
            private String Latitude;
            private Object node;
            private String sendPerson;
            private String requestTime;
            private String bitmapUrl;
            private boolean isCheck;
            private boolean isShow;

            public int getDI_ID() {
                return DI_ID;
            }

            public void setDI_ID(int DI_ID) {
                this.DI_ID = DI_ID;
            }

            public int getLevel() {
                return Level;
            }

            public void setLevel(int Level) {
                this.Level = Level;
            }

            public String getTaskNumber() {
                return TaskNumber;
            }

            public void setTaskNumber(String TaskNumber) {
                this.TaskNumber = TaskNumber;
            }

            public int getDisposalLevel_ID() {
                return DisposalLevel_ID;
            }

            public void setDisposalLevel_ID(int DisposalLevel_ID) {
                this.DisposalLevel_ID = DisposalLevel_ID;
            }

            public String getDealType_Name() {
                return DealType_Name;
            }

            public void setDealType_Name(String DealType_Name) {
                this.DealType_Name = DealType_Name;
            }

            public String getFacilityType_Name() {
                return FacilityType_Name;
            }

            public void setFacilityType_Name(String FacilityType_Name) {
                this.FacilityType_Name = FacilityType_Name;
            }

            public String getDiseaseType_Name() {
                return DiseaseType_Name;
            }

            public void setDiseaseType_Name(String DiseaseType_Name) {
                this.DiseaseType_Name = DiseaseType_Name;
            }

            public String getFacilityName_Name() {
                return FacilityName_Name;
            }

            public void setFacilityName_Name(String FacilityName_Name) {
                this.FacilityName_Name = FacilityName_Name;
            }

            public String getFacilitySpecifications_Name() {
                return FacilitySpecifications_Name;
            }

            public void setFacilitySpecifications_Name(String FacilitySpecifications_Name) {
                this.FacilitySpecifications_Name = FacilitySpecifications_Name;
            }

            public String getStreetAddress_Name() {
                return StreetAddress_Name;
            }

            public void setStreetAddress_Name(String StreetAddress_Name) {
                this.StreetAddress_Name = StreetAddress_Name;
            }

            public String getAddressDescription() {
                return AddressDescription;
            }

            public void setAddressDescription(String AddressDescription) {
                this.AddressDescription = AddressDescription;
            }

            public String getDiseaseDescription() {
                return DiseaseDescription;
            }

            public void setDiseaseDescription(String DiseaseDescription) {
                this.DiseaseDescription = DiseaseDescription;
            }

            public int getChannel() {
                return Channel;
            }

            public void setChannel(int Channel) {
                this.Channel = Channel;
            }

            public int getPhaseIndication() {
                return PhaseIndication;
            }

            public void setPhaseIndication(int PhaseIndication) {
                this.PhaseIndication = PhaseIndication;
            }

            public String getUploadTime() {
                return UploadTime;
            }

            public void setUploadTime(String UploadTime) {
                this.UploadTime = UploadTime;
            }

            public int getUpload_Person_ID() {
                return Upload_Person_ID;
            }

            public void setUpload_Person_ID(int Upload_Person_ID) {
                this.Upload_Person_ID = Upload_Person_ID;
            }

            public int getReviewed_Person_ID() {
                return reviewed_Person_ID;
            }

            public void setReviewed_Person_ID(int reviewed_Person_ID) {
                this.reviewed_Person_ID = reviewed_Person_ID;
            }

            public String getReviewedTime() {
                return reviewedTime;
            }

            public void setReviewedTime(String reviewedTime) {
                this.reviewedTime = reviewedTime;
            }

            public int getIssued_Person_ID() {
                return Issued_Person_ID;
            }

            public void setIssued_Person_ID(int Issued_Person_ID) {
                this.Issued_Person_ID = Issued_Person_ID;
            }

            public String getIssuedTime() {
                return IssuedTime;
            }

            public void setIssuedTime(String IssuedTime) {
                this.IssuedTime = IssuedTime;
            }

            public int getRequirementsComplete_Person_ID() {
                return RequirementsComplete_Person_ID;
            }

            public void setRequirementsComplete_Person_ID(int RequirementsComplete_Person_ID) {
                this.RequirementsComplete_Person_ID = RequirementsComplete_Person_ID;
            }

            public String getRequirementsCompleteTime() {
                return RequirementsCompleteTime;
            }

            public void setRequirementsCompleteTime(String RequirementsCompleteTime) {
                this.RequirementsCompleteTime = RequirementsCompleteTime;
            }

            public String getPostedTime() {
                return postedTime;
            }

            public void setPostedTime(String postedTime) {
                this.postedTime = postedTime;
            }

            public String getDealedTime() {
                return DealedTime;
            }

            public void setDealedTime(String DealedTime) {
                this.DealedTime = DealedTime;
            }

            public int getActualCompletion_Person_ID() {
                return ActualCompletion_Person_ID;
            }

            public void setActualCompletion_Person_ID(int ActualCompletion_Person_ID) {
                this.ActualCompletion_Person_ID = ActualCompletion_Person_ID;
            }

            public String getActualCompletionTime() {
                return ActualCompletionTime;
            }

            public void setActualCompletionTime(String ActualCompletionTime) {
                this.ActualCompletionTime = ActualCompletionTime;
            }

            public String getActualCompletionInfo() {
                return ActualCompletionInfo;
            }

            public void setActualCompletionInfo(String ActualCompletionInfo) {
                this.ActualCompletionInfo = ActualCompletionInfo;
            }

            public Object getChecked_Person_ID() {
                return Checked_Person_ID;
            }

            public void setChecked_Person_ID(Object Checked_Person_ID) {
                this.Checked_Person_ID = Checked_Person_ID;
            }

            public Object getCheckedTime() {
                return CheckedTime;
            }

            public void setCheckedTime(Object CheckedTime) {
                this.CheckedTime = CheckedTime;
            }

            public int getDepartment_ID() {
                return Department_ID;
            }

            public void setDepartment_ID(int Department_ID) {
                this.Department_ID = Department_ID;
            }

            public double getLongitude() {
                return Double.parseDouble(Longitude);
            }

            public void setLongitude(String Longitude) {
                this.Longitude = Longitude;
            }

            public double getLatitude() {
                return Double.parseDouble(Latitude);
            }

            public void setLatitude(String Latitude) {
                this.Latitude = Latitude;
            }

            public Object getNode() {
                return node;
            }

            public void setNode(Object node) {
                this.node = node;
            }

            public String getSendPerson() {
                return sendPerson;
            }

            public void setSendPerson(String sendPerson) {
                this.sendPerson = sendPerson;
            }

            public String getRequestTime() {
                return requestTime;
            }

            public void setRequestTime(String requestTime) {
                this.requestTime = requestTime;
            }

            public String getBitmapUrl() {
                return bitmapUrl;
            }

            public void setBitmapUrl(String bitmapUrl) {
                this.bitmapUrl = bitmapUrl;
            }

            public boolean isCheck() {
                return isCheck;
            }

            public void setCheck(boolean check) {
                isCheck = check;
            }

            public boolean isShow() {
                return isShow;
            }

            public void setShow(boolean show) {
                isShow = show;
            }
        }
    }
    /*private List<ReviewRoad> reviewRoadList;

    public List<ReviewRoad> getReviewRoadList() {
        return reviewRoadList;
    }

    public void setReviewRoadList(List<ReviewRoad> reviewRoadList) {
        this.reviewRoadList = reviewRoadList;
    }


    public static class ReviewRoad implements Serializable {
        *//**
         * streetId : 1
         * list : [{"DI_ID":1,"TaskNumber":"20170316164153","DisposalLevel_ID":1,"FacilityType_ID":1,"DiseaseType_ID":1,"FacilityName_ID":1,"FacilitySpecifications_ID":1,"StreetAddress_ID":1,"AddressDescription":"","DiseaseDescription":"20170316164153","Channel":1,"PhaseIndication":1,"UploadTime":"2017/3/15 23:14:15","Upload_Person_ID":1,"Issued_Person_ID":0,"IssuedTime":null,"RequirementsComplete_Person_ID":0,"RequirementsCompleteTime":null,"ActualCompletion_Person_ID":0,"ActualCompletionTime":null,"ActualCompletionInfo":"","Department_ID":1,"Longitude":"116.343208","Latitude":"39.810088","node":0,"Dirty":true,"Key":"","SubEntities":[]},{"DI_ID":2,"TaskNumber":"123035256","DisposalLevel_ID":1,"FacilityType_ID":1,"DiseaseType_ID":1,"FacilityName_ID":1,"FacilitySpecifications_ID":1,"StreetAddress_ID":1,"AddressDescription":"","DiseaseDescription":"123035256","Channel":1,"PhaseIndication":1,"UploadTime":"2017/3/15 23:14:15","Upload_Person_ID":1,"Issued_Person_ID":0,"IssuedTime":null,"RequirementsComplete_Person_ID":0,"RequirementsCompleteTime":null,"ActualCompletion_Person_ID":0,"ActualCompletionTime":null,"ActualCompletionInfo":"","Department_ID":1,"Longitude":"133.33333","Latitude":"26.33333","node":0,"Dirty":true,"Key":"","SubEntities":[]},{"DI_ID":35,"TaskNumber":"20170320104229","DisposalLevel_ID":1,"FacilityType_ID":1,"DiseaseType_ID":1,"FacilityName_ID":1,"FacilitySpecifications_ID":1,"StreetAddress_ID":1,"AddressDescription":"","DiseaseDescription":"","Channel":0,"PhaseIndication":0,"UploadTime":"2017/3/15 23:14:15","Upload_Person_ID":0,"Issued_Person_ID":0,"IssuedTime":null,"RequirementsComplete_Person_ID":0,"RequirementsCompleteTime":null,"ActualCompletion_Person_ID":0,"ActualCompletionTime":null,"ActualCompletionInfo":"","Department_ID":1,"Longitude":"116.343215","Latitude":"39.81007","node":0,"Dirty":true,"Key":"","SubEntities":[]},{"DI_ID":36,"TaskNumber":"20170320104351","DisposalLevel_ID":1,"FacilityType_ID":1,"DiseaseType_ID":1,"FacilityName_ID":1,"FacilitySpecifications_ID":1,"StreetAddress_ID":1,"AddressDescription":"","DiseaseDescription":"","Channel":0,"PhaseIndication":0,"UploadTime":"2017/3/15 23:14:15","Upload_Person_ID":0,"Issued_Person_ID":0,"IssuedTime":null,"RequirementsComplete_Person_ID":0,"RequirementsCompleteTime":null,"ActualCompletion_Person_ID":0,"ActualCompletionTime":null,"ActualCompletionInfo":"","Department_ID":1,"Longitude":"116.34317","Latitude":"39.810115","node":0,"Dirty":true,"Key":"","SubEntities":[]},{"DI_ID":37,"TaskNumber":"20170320105621","DisposalLevel_ID":1,"FacilityType_ID":1,"DiseaseType_ID":1,"FacilityName_ID":1,"FacilitySpecifications_ID":1,"StreetAddress_ID":1,"AddressDescription":"","DiseaseDescription":"","Channel":0,"PhaseIndication":0,"UploadTime":"2017/3/15 23:14:15","Upload_Person_ID":0,"Issued_Person_ID":0,"IssuedTime":null,"RequirementsComplete_Person_ID":0,"RequirementsCompleteTime":null,"ActualCompletion_Person_ID":0,"ActualCompletionTime":null,"ActualCompletionInfo":"","Department_ID":1,"Longitude":"116.343192","Latitude":"39.810077","node":0,"Dirty":true,"Key":"","SubEntities":[]},{"DI_ID":38,"TaskNumber":"20170320155115","DisposalLevel_ID":1,"FacilityType_ID":1,"DiseaseType_ID":1,"FacilityName_ID":1,"FacilitySpecifications_ID":1,"StreetAddress_ID":1,"AddressDescription":"","DiseaseDescription":"","Channel":0,"PhaseIndication":0,"UploadTime":"2017/3/15 23:14:15","Upload_Person_ID":0,"Issued_Person_ID":0,"IssuedTime":null,"RequirementsComplete_Person_ID":0,"RequirementsCompleteTime":null,"ActualCompletion_Person_ID":0,"ActualCompletionTime":null,"ActualCompletionInfo":"","Department_ID":1,"Longitude":"116.343405","Latitude":"39.809799","node":0,"Dirty":true,"Key":"","SubEntities":[]},{"DI_ID":39,"TaskNumber":"20170320170003","DisposalLevel_ID":1,"FacilityType_ID":1,"DiseaseType_ID":1,"FacilityName_ID":1,"FacilitySpecifications_ID":1,"StreetAddress_ID":1,"AddressDescription":"","DiseaseDescription":"","Channel":0,"PhaseIndication":0,"UploadTime":"2017/3/15 23:14:15","Upload_Person_ID":0,"Issued_Person_ID":0,"IssuedTime":null,"RequirementsComplete_Person_ID":0,"RequirementsCompleteTime":null,"ActualCompletion_Person_ID":0,"ActualCompletionTime":null,"ActualCompletionInfo":"","Department_ID":1,"Longitude":"116.343408","Latitude":"39.809809","node":0,"Dirty":true,"Key":"","SubEntities":[]}]
         *//*

        private String streetName;
        private List<ReviewRoadDetail> list;



        public List<ReviewRoadDetail> getList() {
            return list;
        }

        public void setList(List<ReviewRoadDetail> list) {
            this.list = list;
        }

        public String getStreetName() {
            return streetName;
        }

        public void setStreetName(String streetName) {
            this.streetName = streetName;
        }

        public static class ReviewRoadDetail implements Serializable {
            *//**
             * DI_ID : 1
             * TaskNumber : 20170316164153
             * //处置等级
             * DisposalLevel_ID : 1
             * FacilityType_ID : 1
             * DiseaseType_ID : 1
             * FacilityName_ID : 1
             * FacilitySpecifications_ID : 1
             * StreetAddress_ID : 1
             * AddressDescription :
             * DiseaseDescription : 20170316164153
             * Channel : 1
             * PhaseIndication : 1
             * UploadTime : 2017/3/15 23:14:15
             * Upload_Person_ID : 1
             * //审核人员
             * Issued_Person_ID : 0
             * //审核时间
             * IssuedTime : null
             * //要去完成人
             *
             * RequirementsComplete_Person_ID : 0
             * RequirementsCompleteTime : null
             * ActualCompletion_Person_ID : 0
             * ActualCompletionTime : null
             * //维修情况
             * ActualCompletionInfo :
             * Department_ID : 1
             * Longitude : 116.343208
             * Latitude : 39.810088
             * node : 0
             * Dirty : true
             * Key :
             * SubEntities : []
             *//*

            private int DI_ID;
            private int Level;
            private String TaskNumber;
            private String DealType_Name;

            private int DisposalLevel_ID;
            private int FacilityType_Name;
            private String DiseaseType_Name;
            private String FacilityName_Name;
            private String FacilitySpecifications_Name;
            private String StreetAddress_Name;
            private String AddressDescription;
            private String DiseaseDescription;
            private int Channel;
            private int PhaseIndication;
            private String UploadTime;
            private int Upload_Person_ID;
            private int Issued_Person_ID;
            private Object IssuedTime;
            private int RequirementsComplete_Person_ID;
            private Object RequirementsCompleteTime;
            private int ActualCompletion_Person_ID;
            private Object ActualCompletionTime;
            private String ActualCompletionInfo;
            private int Department_ID;
            private String Longitude;
            private String Latitude;
            private Object postedTime;
            private Object DealedTime;
            private int node;
            private boolean Dirty;
            private String Key;
            private String sendPerson;
            private String requestTime;
            private String bitmapUrl;
            private boolean isCheck;
            private boolean isShow;


            private List<?> SubEntities;

            public ReviewRoadDetail() {
            }


            public String getBitmapUrl() {
                return bitmapUrl;
            }

            public void setBitmapUrl(String bitmapUrl) {
                this.bitmapUrl = bitmapUrl;
            }

            public int getDI_ID() {
                return DI_ID;
            }

            public void setDI_ID(int DI_ID) {
                this.DI_ID = DI_ID;
            }

            public String getTaskNumber() {
                return TaskNumber;
            }

            public void setTaskNumber(String TaskNumber) {
                this.TaskNumber = TaskNumber;
            }

            public int getDisposalLevel_ID() {
                return DisposalLevel_ID;
            }

            public void setDisposalLevel_ID(int DisposalLevel_ID) {
                this.DisposalLevel_ID = DisposalLevel_ID;
            }







            public String getAddressDescription() {
                return AddressDescription;
            }

            public void setAddressDescription(String AddressDescription) {
                this.AddressDescription = AddressDescription;
            }

            public String getDiseaseDescription() {
                return DiseaseDescription;
            }

            public void setDiseaseDescription(String DiseaseDescription) {
                this.DiseaseDescription = DiseaseDescription;
            }

            public int getChannel() {
                return Channel;
            }

            public void setChannel(int Channel) {
                this.Channel = Channel;
            }

            public int getPhaseIndication() {
                return PhaseIndication;
            }

            public void setPhaseIndication(int PhaseIndication) {
                this.PhaseIndication = PhaseIndication;
            }

            public String getUploadTime() {
                return UploadTime;
            }

            public void setUploadTime(String UploadTime) {
                this.UploadTime = UploadTime;
            }

            public int getUpload_Person_ID() {
                return Upload_Person_ID;
            }

            public void setUpload_Person_ID(int Upload_Person_ID) {
                this.Upload_Person_ID = Upload_Person_ID;
            }

            public int getIssued_Person_ID() {
                return Issued_Person_ID;
            }

            public void setIssued_Person_ID(int Issued_Person_ID) {
                this.Issued_Person_ID = Issued_Person_ID;
            }

            public Object getIssuedTime() {
                return IssuedTime;
            }

            public void setIssuedTime(Object IssuedTime) {
                this.IssuedTime = IssuedTime;
            }

            public int getRequirementsComplete_Person_ID() {
                return RequirementsComplete_Person_ID;
            }

            public void setRequirementsComplete_Person_ID(int RequirementsComplete_Person_ID) {
                this.RequirementsComplete_Person_ID = RequirementsComplete_Person_ID;
            }

            public String getRequirementsCompleteTime() {
                return RequirementsCompleteTime.toString();
            }

            public void setRequirementsCompleteTime(Object RequirementsCompleteTime) {
                this.RequirementsCompleteTime = RequirementsCompleteTime;
            }

            public int getActualCompletion_Person_ID() {
                return ActualCompletion_Person_ID;
            }

            public void setActualCompletion_Person_ID(int ActualCompletion_Person_ID) {
                this.ActualCompletion_Person_ID = ActualCompletion_Person_ID;
            }

            public String getActualCompletionTime() {
                return ActualCompletionTime.toString();
            }

            public void setActualCompletionTime(Object ActualCompletionTime) {
                this.ActualCompletionTime = ActualCompletionTime;
            }

            public String getActualCompletionInfo() {
                return ActualCompletionInfo;
            }

            public void setActualCompletionInfo(String ActualCompletionInfo) {
                this.ActualCompletionInfo = ActualCompletionInfo;
            }

            public int getDepartment_ID() {
                return Department_ID;
            }

            public void setDepartment_ID(int Department_ID) {
                this.Department_ID = Department_ID;
            }

            public double getLongitude() {
                return Double.parseDouble(Longitude);

            }

            public void setLongitude(String Longitude) {
                this.Longitude = Longitude;
            }

            public double getLatitude() {
                return Double.parseDouble(Latitude);

            }

            public void setLatitude(String Latitude) {
                this.Latitude = Latitude;
            }

            public int getNode() {
                return node;
            }

            public void setNode(int node) {
                this.node = node;
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

            public List<?> getSubEntities() {
                return SubEntities;
            }

            public void setSubEntities(List<?> SubEntities) {
                this.SubEntities = SubEntities;
            }

            public String getSendPerson() {
                return sendPerson;
            }

            public void setSendPerson(String sendPerson) {
                this.sendPerson = sendPerson;
            }

            public String getRequestTime() {
                return requestTime;
            }

            public void setRequestTime(String requestTime) {
                this.requestTime = requestTime;
            }




            public boolean isCheck() {
                return isCheck;
            }

            public void setCheck(boolean check) {
                isCheck = check;
            }

            public boolean isShow() {
                return isShow;
            }

            public void setShow(boolean show) {
                isShow = show;
            }

            public int getLevel() {
                return Level;
            }

            public void setLevel(int level) {
                Level = level;
            }

            public String getPostedTime() {
                return postedTime.toString();
            }

            public void setPostedTime(Object postedTime) {
                this.postedTime = postedTime;
            }

            public String getDealedTime() {
                return DealedTime.toString();
            }

            public void setDealedTime(Object dealedTime) {
                DealedTime = dealedTime;
            }

            public String getDealType_Name() {
                return DealType_Name;
            }

            public void setDealType_Name(String dealType_Name) {
                DealType_Name = dealType_Name;
            }

            public int getFacilityType_Name() {
                return FacilityType_Name;
            }

            public void setFacilityType_Name(int facilityType_Name) {
                FacilityType_Name = facilityType_Name;
            }


            public String getDiseaseType_Name() {
                return DiseaseType_Name;
            }

            public void setDiseaseType_Name(String diseaseType_Name) {
                DiseaseType_Name = diseaseType_Name;
            }

            public String getFacilityName_Name() {
                return FacilityName_Name;
            }

            public void setFacilityName_Name(String facilityName_Name) {
                FacilityName_Name = facilityName_Name;
            }

            public String getFacilitySpecifications_Name() {
                return FacilitySpecifications_Name;
            }

            public void setFacilitySpecifications_Name(String facilitySpecifications_Name) {
                FacilitySpecifications_Name = facilitySpecifications_Name;
            }

            public String getStreetAddress_Name() {
                return StreetAddress_Name;
            }

            public void setStreetAddress_Name(String streetAddress_Name) {
                StreetAddress_Name = streetAddress_Name;
            }

            @Override
            public String toString() {
                return "ReviewRoadDetail{" +
                        "DI_ID=" + DI_ID +
                        ", Level=" + Level +
                        ", TaskNumber='" + TaskNumber + '\'' +
                        ", DealType_Name='" + DealType_Name + '\'' +
                        ", DisposalLevel_ID=" + DisposalLevel_ID +
                        ", FacilityType_Name=" + FacilityType_Name +
                        ", DiseaseType_Name='" + DiseaseType_Name + '\'' +
                        ", FacilityName_Name='" + FacilityName_Name + '\'' +
                        ", FacilitySpecifications_Name='" + FacilitySpecifications_Name + '\'' +
                        ", StreetAddress_Name='" + StreetAddress_Name + '\'' +
                        ", AddressDescription='" + AddressDescription + '\'' +
                        ", DiseaseDescription='" + DiseaseDescription + '\'' +
                        ", Channel=" + Channel +
                        ", PhaseIndication=" + PhaseIndication +
                        ", UploadTime='" + UploadTime + '\'' +
                        ", Upload_Person_ID=" + Upload_Person_ID +
                        ", Issued_Person_ID=" + Issued_Person_ID +
                        ", IssuedTime=" + IssuedTime +
                        ", RequirementsComplete_Person_ID=" + RequirementsComplete_Person_ID +
                        ", RequirementsCompleteTime=" + RequirementsCompleteTime +
                        ", ActualCompletion_Person_ID=" + ActualCompletion_Person_ID +
                        ", ActualCompletionTime=" + ActualCompletionTime +
                        ", ActualCompletionInfo='" + ActualCompletionInfo + '\'' +
                        ", Department_ID=" + Department_ID +
                        ", Longitude='" + Longitude + '\'' +
                        ", Latitude='" + Latitude + '\'' +
                        ", postedTime=" + postedTime +
                        ", DealedTime=" + DealedTime +
                        ", node=" + node +
                        ", Dirty=" + Dirty +
                        ", Key='" + Key + '\'' +
                        ", sendPerson='" + sendPerson + '\'' +
                        ", requestTime='" + requestTime + '\'' +
                        ", bitmapUrl='" + bitmapUrl + '\'' +
                        ", isCheck=" + isCheck +
                        ", isShow=" + isShow +
                        ", SubEntities=" + SubEntities +
                        '}';
            }
        }
    }

*/



}
