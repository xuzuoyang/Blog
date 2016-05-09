package com.dkd.framework.entity;

import java.io.Serializable;

public class BirdBackInfo implements Serializable {
    private String id ;
    private String heyueDate ;
    private String status ;
    private String yinghunanbenxi ;
    private String yingfufaxi ;
    private String shijiDate ;
    private String projectNo ;
    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getProjectNo() {
        return projectNo;
    }

    public void setProjectNo(String projectNo) {
        this.projectNo = projectNo;
    }

    public String getHeyueDate() {
        return heyueDate;
    }

    public void setHeyueDate(String heyueDate) {
        this.heyueDate = heyueDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getShijiDate() {
        return shijiDate;
    }

    public void setShijiDate(String shijiDate) {
        this.shijiDate = shijiDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getYingfufaxi() {
        return yingfufaxi;
    }

    public void setYingfufaxi(String yingfufaxi) {
        this.yingfufaxi = yingfufaxi;
    }

    public String getYinghunanbenxi() {
        return yinghunanbenxi;
    }

    public void setYinghunanbenxi(String yinghunanbenxi) {
        this.yinghunanbenxi = yinghunanbenxi;
    }
}
