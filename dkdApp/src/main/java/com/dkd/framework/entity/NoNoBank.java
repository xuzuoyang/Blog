package com.dkd.framework.entity;

import java.io.Serializable;
import java.util.Date;

public class NoNoBank implements Serializable{
	private static final long serialVersionUID = 5252519174423871329L;
	private Integer id;
	private String projectNo;
	private String boExtno;
	private String proDesc;
	private String totalMoney;
	private String yearRate;
	private String duteTime;
	private String danBao;
	private String shoukuanfangshi;
	private Date loanStartTime;
	private Date loanEndTime;
	private String huankuanqingkuang;
	private String toubiaojindu;
	private String xinyongdengji;
	private String gongjijieru;
	private String jiekuancishu;
	private String zhengchanghuanqing;
	private String yuqihuanqing;
	private String yuqiweihuan;
	private String daihuanbenjin;
	private String userId;
	private String userName;
	private String shenfen;
	private String xingbie;
	private String huji;
	private String nianling;
	private String suozaidi;
	private String school;
	private String zhuanye;
	private String ruxueTime;
	private String shourulaiyuan;
	private String jiatingnianshouru;
	private String yuejunshouru;
	private String biddingCount;//当前标多少人投
	private String pcId;//暂时不知道什么含义
	private String mAvatarB;//暂时不知道什么含义 猜测是头像
	private String bo_all_repayed;//已还期数
	private String bo_is_finish;//暂时不知道什么含义是否完成
	
	private String hunyin;
	private String xueli;

	private Date createTime;

	private Date updateTime;

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	private String flag;

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getHunyin() {
		return hunyin;
	}

	public String getXueli() {
		return xueli;
	}

	public void setXueli(String xueli) {
		this.xueli = xueli;
	}

	public void setHunyin(String hunyin) {
		this.hunyin = hunyin;
	}

	public Date getLoanEndTime() {
		return loanEndTime;
	}

	public String getShoukuanfangshi() {
		return shoukuanfangshi;
	}

	public void setShoukuanfangshi(String shoukuanfangshi) {
		this.shoukuanfangshi = shoukuanfangshi;
	}

	public void setLoanEndTime(Date loanEndTime) {
		this.loanEndTime = loanEndTime;
	}

	public Date getLoanStartTime() {
		return loanStartTime;
	}

	public void setLoanStartTime(Date loanStartTime) {
		this.loanStartTime = loanStartTime;
	}

	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getBo_all_repayed() {
		return bo_all_repayed;
	}
	public void setBo_all_repayed(String boAllRepayed) {
		bo_all_repayed = boAllRepayed;
	}
	public String getBo_is_finish() {
		return bo_is_finish;
	}
	public void setBo_is_finish(String boIsFinish) {
		bo_is_finish = boIsFinish;
	}
	public String getmAvatarB() {
		return mAvatarB;
	}
	public void setmAvatarB(String mAvatarB) {
		this.mAvatarB = mAvatarB;
	}
	public String getPcId() {
		return pcId;
	}
	public void setPcId(String pcId) {
		this.pcId = pcId;
	}
	public String getBoExtno() {
		return boExtno;
	}
	public void setBoExtno(String boExtno) {
		this.boExtno = boExtno;
	}
	public String getBiddingCount() {
		return biddingCount;
	}
	public void setBiddingCount(String biddingCount) {
		this.biddingCount = biddingCount;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	public String getProjectNo() {
		return projectNo;
	}
	public void setProjectNo(String projectNo) {
		this.projectNo = projectNo;
	}
	
	public String getProDesc() {
		return proDesc;
	}
	public void setProDesc(String proDesc) {
		this.proDesc = proDesc;
	}
	public String getTotalMoney() {
		return totalMoney;
	}
	public void setTotalMoney(String totalMoney) {
		this.totalMoney = totalMoney;
	}
	public String getYearRate() {
		return yearRate;
	}
	public void setYearRate(String yearRate) {
		this.yearRate = yearRate;
	}
	public String getDuteTime() {
		return duteTime;
	}
	public void setDuteTime(String duteTime) {
		this.duteTime = duteTime;
	}
	public String getDanBao() {
		return danBao;
	}
	public void setDanBao(String danBao) {
		this.danBao = danBao;
	}
	public String getHuankuanqingkuang() {
		return huankuanqingkuang;
	}
	public void setHuankuanqingkuang(String huankuanqingkuang) {
		this.huankuanqingkuang = huankuanqingkuang;
	}
	public String getToubiaojindu() {
		return toubiaojindu;
	}
	public void setToubiaojindu(String toubiaojindu) {
		this.toubiaojindu = toubiaojindu;
	}
	public String getXinyongdengji() {
		return xinyongdengji;
	}
	public void setXinyongdengji(String xinyongdengji) {
		this.xinyongdengji = xinyongdengji;
	}
	public String getGongjijieru() {
		return gongjijieru;
	}
	public void setGongjijieru(String gongjijieru) {
		this.gongjijieru = gongjijieru;
	}
	public String getJiekuancishu() {
		return jiekuancishu;
	}
	public void setJiekuancishu(String jiekuancishu) {
		this.jiekuancishu = jiekuancishu;
	}
	public String getZhengchanghuanqing() {
		return zhengchanghuanqing;
	}
	public void setZhengchanghuanqing(String zhengchanghuanqing) {
		this.zhengchanghuanqing = zhengchanghuanqing;
	}
	public String getYuqihuanqing() {
		return yuqihuanqing;
	}
	public void setYuqihuanqing(String yuqihuanqing) {
		this.yuqihuanqing = yuqihuanqing;
	}
	public String getYuqiweihuan() {
		return yuqiweihuan;
	}
	public void setYuqiweihuan(String yuqiweihuan) {
		this.yuqiweihuan = yuqiweihuan;
	}
	public String getDaihuanbenjin() {
		return daihuanbenjin;
	}
	public void setDaihuanbenjin(String daihuanbenjin) {
		this.daihuanbenjin = daihuanbenjin;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getShenfen() {
		return shenfen;
	}
	public void setShenfen(String shenfen) {
		this.shenfen = shenfen;
	}
	public String getXingbie() {
		return xingbie;
	}
	public void setXingbie(String xingbie) {
		this.xingbie = xingbie;
	}
	public String getHuji() {
		return huji;
	}
	public void setHuji(String huji) {
		this.huji = huji;
	}
	public String getNianling() {
		return nianling;
	}
	public void setNianling(String nianling) {
		this.nianling = nianling;
	}
	public String getSuozaidi() {
		return suozaidi;
	}
	public void setSuozaidi(String suozaidi) {
		this.suozaidi = suozaidi;
	}
	public String getSchool() {
		return school;
	}
	public void setSchool(String school) {
		this.school = school;
	}
	public String getZhuanye() {
		return zhuanye;
	}
	public void setZhuanye(String zhuanye) {
		this.zhuanye = zhuanye;
	}
	public String getRuxueTime() {
		return ruxueTime;
	}
	public void setRuxueTime(String ruxueTime) {
		this.ruxueTime = ruxueTime;
	}
	public String getShourulaiyuan() {
		return shourulaiyuan;
	}
	public void setShourulaiyuan(String shourulaiyuan) {
		this.shourulaiyuan = shourulaiyuan;
	}
	public String getJiatingnianshouru() {
		return jiatingnianshouru;
	}
	public void setJiatingnianshouru(String jiatingnianshouru) {
		this.jiatingnianshouru = jiatingnianshouru;
	}
	public String getYuejunshouru() {
		return yuejunshouru;
	}
	public void setYuejunshouru(String yuejunshouru) {
		this.yuejunshouru = yuejunshouru;
	}



}
