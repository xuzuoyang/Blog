package com.dkd.framework.entity;

import java.io.Serializable;
import java.util.Date;

public class BirdProject implements Serializable{
	private Integer id;
	private String projectNo;
	private String title;
	private String kaopudengji;
	private String totalMoney;
	private String yearRate;
	private String duteTime;
	private String huankuanfangshi;
	private String jiekuanyongtu;
	private String jiekuanmiaoshu;
	private String huankuanlaiyuan;
	private String toubiaojindu;
	private String shengyutianshu;
	private String ketouMoney;
	private String status;

	private String userId;

	private Date createTime;

	public BirdProject(String projectNo) {
		this.projectNo = projectNo;
	}
	public BirdProject() {
	}

	private Date updateTime;





	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getDuteTime() {
		return duteTime;
	}

	public void setDuteTime(String duteTime) {
		this.duteTime = duteTime;
	}

	public String getHuankuanfangshi() {
		return huankuanfangshi;
	}

	public void setHuankuanfangshi(String huankuanfangshi) {
		this.huankuanfangshi = huankuanfangshi;
	}

	public String getHuankuanlaiyuan() {
		return huankuanlaiyuan;
	}

	public void setHuankuanlaiyuan(String huankuanlaiyuan) {
		this.huankuanlaiyuan = huankuanlaiyuan;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getJiekuanmiaoshu() {
		return jiekuanmiaoshu;
	}

	public void setJiekuanmiaoshu(String jiekuanmiaoshu) {
		this.jiekuanmiaoshu = jiekuanmiaoshu;
	}

	public String getJiekuanyongtu() {
		return jiekuanyongtu;
	}

	public void setJiekuanyongtu(String jiekuanyongtu) {
		this.jiekuanyongtu = jiekuanyongtu;
	}

	public String getKaopudengji() {
		return kaopudengji;
	}

	public void setKaopudengji(String kaopudengji) {
		this.kaopudengji = kaopudengji;
	}

	public String getKetouMoney() {
		return ketouMoney;
	}

	public void setKetouMoney(String ketouMoney) {
		this.ketouMoney = ketouMoney;
	}

	public String getProjectNo() {
		return projectNo;
	}

	public void setProjectNo(String projectNo) {
		this.projectNo = projectNo;
	}

	public String getShengyutianshu() {
		return shengyutianshu;
	}

	public void setShengyutianshu(String shengyutianshu) {
		this.shengyutianshu = shengyutianshu;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public String getToubiaojindu() {
		return toubiaojindu;
	}

	public void setToubiaojindu(String toubiaojindu) {
		this.toubiaojindu = toubiaojindu;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getYearRate() {
		return yearRate;
	}

	public void setYearRate(String yearRate) {
		this.yearRate = yearRate;
	}
}
