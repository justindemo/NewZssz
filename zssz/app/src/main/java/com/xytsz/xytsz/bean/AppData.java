package com.xytsz.xytsz.bean;

import java.util.List;

/**
 * Created by admin on 2017/1/17.
 *
 *
 */
public class AppData {


    private List<ReviewRoadListBean> reviewRoadList;

    public List<ReviewRoadListBean> getReviewRoadList() {
        return reviewRoadList;
    }

    public void setReviewRoadList(List<ReviewRoadListBean> reviewRoadList) {
        this.reviewRoadList = reviewRoadList;
    }

    public static class ReviewRoadListBean {
        /**
         * streetId : 5
         * list : [{"DI_ID":2,"Level":1,"TaskNumber":"20170401164312","DisposalLevel_ID":1,"FacilityType_ID":1,"DiseaseType_ID":1,"FacilityName_ID":1,"FacilitySpecifications_ID":1,"StreetAddress_ID":5,"AddressDescription":"","DiseaseDescription":"","Channel":0,"PhaseIndication":0,"UploadTime":"2017/4/1 16:43:21","Upload_Person_ID":4,"reviewed_Person_ID":null,"reviewedTime":null,"Issued_Person_ID":0,"IssuedTime":null,"RequirementsComplete_Person_ID":0,"RequirementsCompleteTime":null,"postedTime":null,"DealedTime":null,"ActualCompletion_Person_ID":0,"ActualCompletionTime":null,"ActualCompletionInfo":"","Checked_Person_ID":null,"CheckedTime":null,"Department_ID":1,"Longitude":"116.356021","Latitude":"39.768593","node":null,"Dirty":true,"Key":"","SubEntities":[]}]
         */

        private int streetId;
        private List<ListBean> list;

        public int getStreetId() {
            return streetId;
        }

        public void setStreetId(int streetId) {
            this.streetId = streetId;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * DI_ID : 2
             * Level : 1
             * TaskNumber : 20170401164312
             * DisposalLevel_ID : 1
             * FacilityType_ID : 1
             * DiseaseType_ID : 1
             * FacilityName_ID : 1
             * FacilitySpecifications_ID : 1
             * StreetAddress_ID : 5
             * AddressDescription :
             * DiseaseDescription :
             * Channel : 0
             * PhaseIndication : 0
             * UploadTime : 2017/4/1 16:43:21
             * Upload_Person_ID : 4
             * reviewed_Person_ID : null
             * reviewedTime : null
             * Issued_Person_ID : 0
             * IssuedTime : null
             * RequirementsComplete_Person_ID : 0
             * RequirementsCompleteTime : null
             * postedTime : null
             * DealedTime : null
             * ActualCompletion_Person_ID : 0
             * ActualCompletionTime : null
             * ActualCompletionInfo :
             * Checked_Person_ID : null
             * CheckedTime : null
             * Department_ID : 1
             * Longitude : 116.356021
             * Latitude : 39.768593
             * node : null
             * Dirty : true
             * Key :
             * SubEntities : []
             */

            private int DI_ID;
            private int Level;
            private String TaskNumber;
            private int DisposalLevel_ID;
            private int FacilityType_ID;
            private int DiseaseType_ID;
            private int FacilityName_ID;
            private int FacilitySpecifications_ID;
            private int StreetAddress_ID;
            private String AddressDescription;
            private String DiseaseDescription;
            private int Channel;
            private int PhaseIndication;
            private String UploadTime;
            private int Upload_Person_ID;
            private Object reviewed_Person_ID;
            private Object reviewedTime;
            private int Issued_Person_ID;
            private Object IssuedTime;
            private int RequirementsComplete_Person_ID;
            private Object RequirementsCompleteTime;
            private Object postedTime;
            private Object DealedTime;
            private int ActualCompletion_Person_ID;
            private Object ActualCompletionTime;
            private String ActualCompletionInfo;
            private Object Checked_Person_ID;
            private Object CheckedTime;
            private int Department_ID;
            private String Longitude;
            private String Latitude;
            private Object node;
            private boolean Dirty;
            private String Key;
            private List<?> SubEntities;

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

            public int getFacilityType_ID() {
                return FacilityType_ID;
            }

            public void setFacilityType_ID(int FacilityType_ID) {
                this.FacilityType_ID = FacilityType_ID;
            }

            public int getDiseaseType_ID() {
                return DiseaseType_ID;
            }

            public void setDiseaseType_ID(int DiseaseType_ID) {
                this.DiseaseType_ID = DiseaseType_ID;
            }

            public int getFacilityName_ID() {
                return FacilityName_ID;
            }

            public void setFacilityName_ID(int FacilityName_ID) {
                this.FacilityName_ID = FacilityName_ID;
            }

            public int getFacilitySpecifications_ID() {
                return FacilitySpecifications_ID;
            }

            public void setFacilitySpecifications_ID(int FacilitySpecifications_ID) {
                this.FacilitySpecifications_ID = FacilitySpecifications_ID;
            }

            public int getStreetAddress_ID() {
                return StreetAddress_ID;
            }

            public void setStreetAddress_ID(int StreetAddress_ID) {
                this.StreetAddress_ID = StreetAddress_ID;
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

            public Object getReviewed_Person_ID() {
                return reviewed_Person_ID;
            }

            public void setReviewed_Person_ID(Object reviewed_Person_ID) {
                this.reviewed_Person_ID = reviewed_Person_ID;
            }

            public Object getReviewedTime() {
                return reviewedTime;
            }

            public void setReviewedTime(Object reviewedTime) {
                this.reviewedTime = reviewedTime;
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

            public Object getRequirementsCompleteTime() {
                return RequirementsCompleteTime;
            }

            public void setRequirementsCompleteTime(Object RequirementsCompleteTime) {
                this.RequirementsCompleteTime = RequirementsCompleteTime;
            }

            public Object getPostedTime() {
                return postedTime;
            }

            public void setPostedTime(Object postedTime) {
                this.postedTime = postedTime;
            }

            public Object getDealedTime() {
                return DealedTime;
            }

            public void setDealedTime(Object DealedTime) {
                this.DealedTime = DealedTime;
            }

            public int getActualCompletion_Person_ID() {
                return ActualCompletion_Person_ID;
            }

            public void setActualCompletion_Person_ID(int ActualCompletion_Person_ID) {
                this.ActualCompletion_Person_ID = ActualCompletion_Person_ID;
            }

            public Object getActualCompletionTime() {
                return ActualCompletionTime;
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

            public String getLongitude() {
                return Longitude;
            }

            public void setLongitude(String Longitude) {
                this.Longitude = Longitude;
            }

            public String getLatitude() {
                return Latitude;
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
        }
    }
}
