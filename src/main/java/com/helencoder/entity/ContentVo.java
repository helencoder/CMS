package com.helencoder.entity;

import java.sql.Timestamp;
import java.util.Date;

/**
 * 内容实体
 *
 * Created by zhenghailun on 2018/5/4.
 */
public class ContentVo {
    private String id;
    private String data;
    private String flag;
    private String label;
    private String remark;
    private Timestamp createTime;
    private Timestamp updateTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime() {
        Timestamp timeStamp = new Timestamp(new Date().getTime());
        this.createTime = timeStamp;
    }

    public void setCreateTime(Timestamp timestamp) {
        this.createTime = timestamp;
    }

    public void setCreateTime(Date date) {
        Timestamp timeStamp = new Timestamp(date.getTime());
        this.createTime = timeStamp;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime() {
        Timestamp timeStamp = new Timestamp(new Date().getTime());
        this.updateTime = timeStamp;
    }

    public void setUpdateTime(Timestamp timestamp) {
        this.updateTime = timestamp;
    }

    public void setUpdateTime(Date date) {
        Timestamp timeStamp = new Timestamp(date.getTime());
        this.updateTime = timeStamp;
    }
}
