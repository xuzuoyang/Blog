package com.dkd.framework.entity;

import java.io.Serializable;

public class BirdHistoryLoan implements Serializable{
    private String id;
    private String userId;
    private String title;
    private String totalMoney;
    private String duteTime;
    private String yueRate;
    private String jiekuanTime;
    private String jieqingTime;

    public String getDuteTime() {
        return duteTime;
    }

    public void setDuteTime(String duteTime) {
        this.duteTime = duteTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJiekuanTime() {
        return jiekuanTime;
    }

    public void setJiekuanTime(String jiekuanTime) {
        this.jiekuanTime = jiekuanTime;
    }

    public String getJieqingTime() {
        return jieqingTime;
    }

    public void setJieqingTime(String jieqingTime) {
        this.jieqingTime = jieqingTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(String totalMoney) {
        this.totalMoney = totalMoney;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getYueRate() {
        return yueRate;
    }

    public void setYueRate(String yueRate) {
        this.yueRate = yueRate;
    }
}
