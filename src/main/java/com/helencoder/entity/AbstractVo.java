package com.helencoder.entity;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by zhenghailun on 2018/5/14.
 */
public class AbstractVo {
    private String id;
    private String content;
    private String abs;
    private String result;
    private String similarity;
    private String flag;
    private String createTime;
    private String updateTime;
//    private Timestamp createTime;
//    private Timestamp updateTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAbs() {
        return abs;
    }

    public void setAbs(String abs) {
        this.abs = abs;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getSimilarity() {
        return similarity;
    }

    public void setSimilarity(String similarity) {
        this.similarity = similarity;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    //    public Timestamp getCreateTime() {
//        return createTime;
//    }
//
//    public void setCreateTime() {
//        Timestamp timeStamp = new Timestamp(new Date().getTime());
//        this.createTime = timeStamp;
//    }
//
//    public void setCreateTime(Timestamp timestamp) {
//        this.createTime = timestamp;
//    }
//
//    public void setCreateTime(Date date) {
//        Timestamp timeStamp = new Timestamp(date.getTime());
//        this.createTime = timeStamp;
//    }
//
//    public Timestamp getUpdateTime() {
//        return updateTime;
//    }
//
//    public void setUpdateTime() {
//        Timestamp timeStamp = new Timestamp(new Date().getTime());
//        this.updateTime = timeStamp;
//    }
//
//    public void setUpdateTime(Timestamp timestamp) {
//        this.updateTime = timestamp;
//    }
//
//    public void setUpdateTime(Date date) {
//        Timestamp timeStamp = new Timestamp(date.getTime());
//        this.updateTime = timeStamp;
//    }
}
