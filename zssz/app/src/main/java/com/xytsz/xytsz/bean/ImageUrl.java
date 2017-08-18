package com.xytsz.xytsz.bean;


import java.io.Serializable;

/**
 * Created by admin on 2017/3/27.
 *
 *
 */
public class ImageUrl implements Serializable {


    /**
     * urlId : url1
     * Imgurl : http://110.173.0.231:10003/UploadMedia/my/666.jpg
     */

    private String urlId;
    private String Imgurl;
    private String taskNumber;

    public String getUrlId() {
        return urlId;
    }

    public void setUrlId(String urlId) {
        this.urlId = urlId;
    }

    public String getImgurl() {
        return Imgurl;
    }

    public void setImgurl(String Imgurl) {
        this.Imgurl = Imgurl;
    }

    public String getTaskNumber() {
        return taskNumber;
    }

    public void setTaskNumber(String taskNumber) {
        this.taskNumber = taskNumber;
    }
}
