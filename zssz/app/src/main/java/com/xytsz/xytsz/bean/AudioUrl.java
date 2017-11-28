package com.xytsz.xytsz.bean;

import java.io.Serializable;

/**
 * Created by admin on 2017/11/28.
 *
 *
 */
public class AudioUrl implements Serializable{

    private String audioUrl;
    private String time;

    public String getAudioUrl() {
        return audioUrl;
    }

    public void setAudioUrl(String audioUrl) {
        this.audioUrl = audioUrl;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
