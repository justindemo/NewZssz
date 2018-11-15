package com.xytsz.xytsz.net;

/**
 * Created by admin on 2017/2/13.
 *
 * 网络层
 */
public class NetUrl {



    public static final String SERVERURL = "http://sz.xytgps.com/SZWEBSERVICE/newxytszServices.asmx";
    //public static final String SERVERURL = "http://sdgr.xytgps.com/SZWEBSERVICE/newxytszServices.asmx";

    /**
     *命名空间
     */
    public static final String nameSpace = "http://xytsz.org/";
    /**
     * 方法名
     */
    public static final String dealtypemethodName = "getAllSZDealType";
    public static final String gradesmethodName = "getAllSZDisposalLeve";

    public static final String departmethodName = "getAllSZDepatment";
    public static final String fatypemethodName = "getAllSZFacilityType";
    public static final String problemmethodName = "getAllSZDiseaseType";
    public static final String faNamemethodName = "getAllSZFacilityName";
    public static final String faSizemethodName = "getAllSZFacilitySpecifications";
    public static final String loginmethodName = "toLogin";
    public static final String registmethodName = "toRegister";
    public static final String noticeMethodName = "getAllSZNotice";
    public static final String streetmethodName = "getAllSZStreet";
    public static final String photomethodName = "toReportImg";
    public static final String reportmethodName = "toReport";
    public static final String getTaskList = "GetTaskList";
    //获取当前人员要处置的信息
    public static final String getManagementList = "GetManagementList";
    /*
    * 去派工*/
    public static final String sendmethodName = "ToDispatching";
    /**
     * 养护驳回
     */
    public static final String sendbackmethodName = "ToDispatching";

    /*
    * 审核*/
    public static final String reviewmethodName = "ToExamine";

    /*
    * 报验*/
    public static final String postmethodName = "ToManagement";
    /*
    * 验收*/
    public static final String checkmethodName = "ToInspection";
    /*
    * 上传位置信息到服务器*/
    public static final String uploadLocationmethodName = "toUploadLocation";

    /*
    * 获取人员位置信息*/
    public static final String getpersonLocation = "getAllPersonLocation";

    /**
     * 我的界面的
     */
    public static final String getTaskCountOfReport ="GetTaskCountOfReport";
    public static final String getTaskCountOfDeal ="GetTaskCountOfDeal";
    public static final String getTaskCountOfReview ="GetTaskCountOfEx";
    public static final String getTaskCountOfSend ="GetTaskCountOfIssued";


    /**
     * 获取所有病害列表 用于病害定位
     */
    public static final String getAllTaskMethodName = "GetAllTask";
    /**
     * 获取所有病害的图片Url
     */
    public static final String getAllImageURLmethodName = "getAllImgUrl";
    /**
     * 获取报验阶段的三类图片
     */
    public static final String getPostImageURLmethodName = "getPostImgUrl";
    public static final String getPreImageURLmethodName = "getPreImgUrl";
    public static final String getRngImageURLmethodName = "getRngImgUrl";
    /**
     * 获取当前版本
     */
    public static final String getVersionInfoMethodName = "getVersionInfo";
    /**
     * 获取人员列表的方法
     */
    public static final String getPersonList ="getPersonList";
    public static final String getallPersonList ="getPersonByYH";
    /**
     * 获取自己上报处置的方法
     */
    public static final String getALlReportByPersonID = "getALlReportByPersonID";
    public static final String getALlDealByPersonID = "getAllDealByPersonID";
    public static final String getAllSendByPersonID = "GetAllIssuedByPersonID";
    public static final String getALlReviewByPersonID = "getAllExamineByPersonID";
    /**
     * 上传头像
     */
    public static final String uploadHeadImg = "uploadHeadImg";

    /*
    * 方法*/
    public static final  String grade_SOAP_ACTION = "http://xytsz.org/getAllSZDisposalLeve";
    public static final  String depart_SOAP_ACTION = "http://xytsz.org/getAllSZDepatment";
    public static final  String fatype_SOAP_ACTION = "http://xytsz.org/getAllSZFacilityType";
    public static final  String pbtype_SOAP_ACTION = "http://xytsz.org/getAllSZDiseaseType";
    public static final  String faname_SOAP_ACTION = "http://xytsz.org/getAllSZFacilityName";
    public static final  String fasize_SOAP_ACTION = "http://xytsz.org/getAllSZFacilitySpecifications";
    public static final  String login_SOAP_ACTION = "http://xytsz.org/toLogin";
    public static final  String notice_SOAP_ACTION = "http://xytsz.org/getAllSZNotice";
    public static final  String street_SOAP_ACTION = "http://xytsz.org/getAllSZStreet";
    public static final  String photo_SOAP_ACTION = "http://xytsz.org/toReportImg";
    public static final  String report_SOAP_ACTION = "http://xytsz.org/toReport";
    public static final  String getTasklist_SOAP_ACTION = "http://xytsz.org/GetTaskList";
    public static final  String toExamine_SOAP_ACTION = "http://xytsz.org/ToExamine";
    public static final  String toDispatching_SOAP_ACTION = "http://xytsz.org/ToDispatching";
    public static final  String toManagement_SOAP_ACTION = "http://xytsz.org/ToManagement";
    public static final  String toInspection_SOAP_ACTION = "http://xytsz.org/ToInspection";
    public static final  String getPersonlocation_SOAP_ACTION = "http://xytsz.org/getAllPersonLocation";
    public static final  String toUploadlocation_SOAP_ACTION = "http://xytsz.org/toUploadLocation";


    public static final String getManagementList_SOAP_ACTION = "http://xytsz.org/GetManagementList";

    public static final String getTaskCountOfReport_SOAP_ACTION = "http://xytsz.org/GetTaskCountOfReport";
    public static final String getTaskCountOfDeal_SOAP_ACTION = "http://xytsz.org/GetTaskCountOfDeal";
    public static final String getTaskCountOfReview_SOAP_ACTION = "http://xytsz.org/GetTaskCountOfEx";

    public static final String getAllImageURL_SOAP_ACTION = "http://xytsz.org/getAllImgUrl";

    public static final String getAllTask_SOAP_ACTION = "http://xytsz.org/GetAllTask";

    public static final String getPostImageURL_SOAP_ACTION ="http://xytsz.org/getPostImgUrl" ;

    public static final String getPreImageURLSoap_Action = "http://xytsz.org/getPreImgUrl";
    public static final String getRngImageURLSoap_Action = "http://xytsz.org/getRngImgUrl";


    public static final String getPersonList_SOAP_ACTION ="http://xytsz.org/getPersonList";

    public static final String getVersionInfo_SOAP_ACTION = "http://xytsz.org/getVersionInfo";
    public static final String dealtype_SOAP_ACTION = "http://xytsz.org/getAllSZDealType";

    public static final String getALlReportByPersonID_SOAP_ACTION = "http://xytsz.org/getALlReportByPersonID";
    public static final String getALlDealByPersonID_SOAP_ACTION = "http://xytsz.org/getAllDealByPersonID";

    public static final String uploadHeadImg_SOAP_ACTION = "http://xytsz.org/uploadHeadImg";
    //修改
    public static final String sendBack_SOAP_ACTION = "http://xytsz.org/uploadHeadImg";



    public static final String getALlReviewByPersonID_SOAP_ACTION = "http://xytsz.org/getAllExamineByPersonID";
    public static final String getenlist = "GetEnList";
    public static final String getenlist_SOAP_ACTION = "http://xytsz.org/GetEnList";
    public static final String citypersonreportemethodName = "ToSoReporting";
    public static final String getAllUserCountMethodName = "GetALLUserCount";
    public static final String getAllUserCount_SOAP_ACITION = "http://xytsz.org/GetALLUserCount";
    public static final String getszUserCountInfomethod = "GetSZDisCountInfo";
    public static final String getszUserCountInfo_SOAP_ACTION = "http://xytsz.org/GetSZDisCountInfo";
    public static final String getSZPeoplemethodName = "GetSZPeople";
    public static final String getSZPeople_SOAP_ACTION = "http://xytsz.org/GetSZPeople";
    public static final String isSignMethodname = "ToSign";
    public static final String isSign_SOAP_ACITON = "http://xytsz.org/ToSign";
    public static final String updatePesronmethodName ="ToUpdateSZPeople";
    public static final String updatePesron_SOAP_ACTION ="http://xytsz.org/ToUpdateSZPeople";

    public static final String getHistoryMethodname = "GetSoReport";
    public static final String getHistory_SOAP_ACTION = "http://xytsz.org/GetSoReport";
    public static final String getScoreDetailMethodname ="GetIntegralInfo";
    public static final String getScoreDetail_SOAP_ACTION ="http://xytsz.org/GetIntegralInfo";
    public static final String getScoreMethodname ="GetIntegral";
    public static final String getScore_SOAP_ACTION ="http://xytsz.org/GetIntegral";
    public static final String audiomethodName = "toReportAudio";

    public static final String getAudioMethodName ="GetAudioByTNum";
    public static final String getVideoMethodName ="GetMp4ByTNum";
    public static final String getAudio_SOAP_ACTION ="http://xytsz.org/GetAudioByTNum";
    public static final String appraisemethodName = "ToFeedBack";
    public static final String appraise_SOAP_ACTION = "http://xytsz.org/ToFeedBack";
    public static final String videomethodName ="toReportMp4";
    public static final String modificaitonmethodName = "toChangePwd";
    public static final String replacePerson ="ToChangeRe_Person_ID";

}
