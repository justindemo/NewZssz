package com.xytsz.xytsz.bean;

import java.util.List;

/**
 * Created by admin on 2017/3/27.
 *
 */
public class ImageUpLoad {

    private List<String> fileName;
    private List<String> imageBase64String;

    public List<String> getFileName() {
        return fileName;
    }

    public void setFileName(List<String> fileName) {
        this.fileName = fileName;
    }

    public List<String> getImageBase64String() {
        return imageBase64String;
    }

    public void setImageBase64String(List<String> imageBase64String) {
        this.imageBase64String = imageBase64String;
    }
}
