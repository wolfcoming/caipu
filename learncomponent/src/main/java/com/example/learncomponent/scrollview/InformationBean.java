package com.example.learncomponent.scrollview;

import java.io.Serializable;

/**
 * @author yangqing
 * @time 2019/6/5 11:19 AM
 * @describe 咨询bean
 */
public class InformationBean implements Serializable {
    private String content;
    private String date;
    private String from;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }
}
