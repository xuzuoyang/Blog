package com.dkd.framework.service.impl;

import com.dkd.framework.service.SpiderUtils;
import com.dkd.common.utils.DateUtils;
import com.dkd.framework.dao.NonoBankDao;
import com.dkd.framework.entity.NoNoBank;
import com.dkd.framework.service.NonoBankService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class NonoBankServiceImpl implements NonoBankService {

    private static Logger logger  = Logger.getLogger(NonoBankServiceImpl.class);
    @Autowired
    private NonoBankDao nonoBankDao;
    @Autowired
    private SpiderUtils spiderUtils;

    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,readOnly=true)
    public List<NoNoBank> select(Map<String, Object> reqMap) {
        return nonoBankDao.select(reqMap);
    }
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,readOnly=true)
    public Long selectCount(Map<String, Object> selectMap) {
        return nonoBankDao.selectCount(selectMap);
    }

    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,readOnly=false)
    public void saveSynData(JSONArray synData,int startPageNo) {
        int count = 0;
       // for(Object obj :synData){
        for(int i=0;i<synData.size();i++){
            JSONObject nonoJson = JSONObject.fromObject(synData.get(i));
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("projectNo", nonoJson.optString("bo_id"));
            NoNoBank noNoBank  = nonoBankDao.selectByProjectNo( map);
            try {
                if (noNoBank == null) {
                    count++;
                    noNoBank = new NoNoBank();
                    noNoBank.setProjectNo(nonoJson.optString("bo_id"));
                    noNoBank.setBoExtno(nonoJson.optString("bo_extno"));
                    noNoBank.setProDesc(nonoJson.optString("bo_title"));
                    noNoBank.setTotalMoney(nonoJson.optString("bo_price").replace(",", ""));
                    noNoBank.setYearRate(nonoJson.optString("bo_rate_lender"));
                    noNoBank.setDuteTime(nonoJson.optString("bo_expect"));
                    noNoBank.setDanBao(nonoJson.optString("bidding_cat"));
                    noNoBank.setShoukuanfangshi(nonoJson.optString("p_borrow_type"));
                    noNoBank.setToubiaojindu(nonoJson.optString("bo_finish_rate"));
                    noNoBank.setUserName(nonoJson.optString("m_username"));
                    noNoBank.setUserId(nonoJson.optString("m_id"));
                    noNoBank.setXingbie(nonoJson.optString("sex"));
                    noNoBank.setSuozaidi(nonoJson.optString("province") + nonoJson.optString("city"));
                    noNoBank.setBiddingCount(nonoJson.optString("bidding_count"));
                    noNoBank.setPcId(nonoJson.optString("pc_id"));
                    noNoBank.setBo_all_repayed(nonoJson.optString("bo_all_repayed"));
                    noNoBank.setmAvatarB(nonoJson.optString("m_avatar_b"));
                    noNoBank.setBo_is_finish(nonoJson.optString("bo_is_finish"));
                    noNoBank.setCreateTime(new Date());
                    noNoBank.setUpdateTime(new Date());
                    nonoBankDao.saveNonoBank(noNoBank);
                    logger.info("******"+startPageNo+"名校贷同步数据项目编号 "+i+":" + nonoJson.optString("bo_id"));
                }
            }catch (Exception e){
                e.printStackTrace();
                logger.error("******"+startPageNo+"名校贷同步失败的编号:" + nonoJson.optString("bo_id"), e);

            }
        }
        logger.info("同步时间:"+ DateUtils.getNowTime()+"******名校贷同步数据此次一共:"+count);

    }
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,readOnly=false)
    public Map<String, Object> saveSynDetailData(List<NoNoBank> select,int startPageNo) {
        Map<String,Object> resMap = new HashMap<String, Object>();
        int success = 0;
        int error = 0;
        for(NoNoBank nonoBank : select){
            try{
                Document objectDoc = spiderUtils.getDataByCookieLogin(nonoBank.getProjectNo());
                if(objectDoc!=null){
                    //担保方式
                    nonoBank.setDanBao(objectDoc.select("#lenddetail > div.product > div.product_left > div.list_01 > ul").first().children().eq(0).text().split("：")[1]);
                    //收款方式
                    String shoukuan = objectDoc.select("#lenddetail > div.product > div.product_left > div.list_01 > ul").first().children().eq(1).text();
                    if(!"".equals(shoukuan)&&null!=shoukuan&&shoukuan.split("：").length>1){
                        nonoBank.setShoukuanfangshi(shoukuan.split("：")[1]);
                    }
                    String startEndTime  = objectDoc.select("#lenddetail > div.product > div.product_left > div.list_01 > ul").first().children().eq(2).text().split("：")[1];
                    //借款开始时间
                    nonoBank.setLoanStartTime(DateUtils.strToDate(startEndTime.split("-")[0].replace("/", "-").trim(),"yyyy-MM-dd"));
                    //借款结束时间
                    nonoBank.setLoanEndTime(DateUtils.strToDate(startEndTime.split("-")[1].replace("/", "-").trim(), "yyyy-MM-dd"));
                    //还款情况
                    nonoBank.setHuankuanqingkuang(objectDoc.select("#lenddetail > div.product > div.product_left > div.list_01 > ul").last().children().eq(0).text().split("：")[1]);
                    //信用等级
                    nonoBank.setXinyongdengji(objectDoc.select(".levelbox").text());
                    //共计借入
                    nonoBank.setGongjijieru(objectDoc.select(".lend_left_data > dl > dd:eq(0) > span").text().replace("￥","").replace(",",""));
                    //借款次数
                    nonoBank.setJiekuancishu(objectDoc.select(".lend_left_data > dl > dd:eq(1) > span").text().replace("次", ""));
                    //正常还清
                    nonoBank.setZhengchanghuanqing(objectDoc.select(".lend_left_data > dl > dd:eq(2) > span").text().replace("次", ""));
                    //逾期还清
                    nonoBank.setYuqihuanqing(objectDoc.select(".lend_left_data > dl > dd:eq(3) > span").text().replace("次", ""));
                    //逾期未还
                    nonoBank.setYuqiweihuan(objectDoc.select(".lend_left_data > dl > dd:eq(4) > span").text().replace("次", ""));
                    //待还本金
                    nonoBank.setDaihuanbenjin(objectDoc.select(".lend_left_data > dl > dd:eq(5) > span").text().replace("￥", "").replace(",",""));
                    //身份
                    String shenfen  = objectDoc.select("#tabs-1 > div > div.basic_box > div.box_left > p").text();
                    if(!"".equals(shenfen)&&null!=shenfen){
                        nonoBank.setShenfen(shenfen);
                    }
                    //如果是微企业主
                    //如果是企业主
                    //如果是白领
                    if("白领".equals(shenfen)||"微企业主".equals(shenfen)||"企业主".equals(shenfen)){
                        //学历
                        String xueli = objectDoc.select("#tabs-1 > div > div.basic_box > div.box_right > div:eq(7)").text();
                        if(!"".equals(xueli)&&null!=xueli&&xueli.split("：").length>1){
                            nonoBank.setXueli(xueli.split("：")[1].trim());
                        }
                        //婚姻
                        String hunyin = objectDoc.select("#tabs-1 > div > div.basic_box > div.box_right > div:eq(5)").text();
                        if(!"".equals(hunyin)&&null!=hunyin&&hunyin.split("：").length>1){
                            nonoBank.setHunyin(hunyin.split("：")[1].trim());
                        }
                    }
                    //户籍
                    String huji = objectDoc.select(".box_right > div:eq(3)").text();
                    if(!"".equals(huji)&&null!=huji&&huji.split("：").length>1){
                        nonoBank.setHuji(huji.split("：")[1].trim());
                    }
                    //年龄
                    String age = objectDoc.select(".box_right > div:eq(4)").text();
                    if(!"".equals(age)&&null!=age&&age.split("：").length>1){

                        nonoBank.setNianling(age.split("：")[1].trim());
                    }
                    //学校名称#tabs-1 > div > div:nth-child(5) > table > tbody > tr > td:nth-child(1)
                    String school = objectDoc.select("#tabs-1 > div > div:eq(4) > table > tbody > tr>td:eq(0) ").text();
                    if(!"".equals(school)&&null!=school&&school.split("：").length>1){
                        nonoBank.setSchool(school.split("：")[1].trim());
                    }
                    //专业
                    String zhuanye = objectDoc.select("#tabs-1 > div > div:eq(4) > table > tbody > tr>td:eq(1)").text();
                    if(!"".equals(zhuanye)&&null!=zhuanye&&zhuanye.split("：").length>1){
                        nonoBank.setZhuanye(zhuanye.split("：")[1].trim());
                    }
                    //入学时间
                    String ruxue = objectDoc.select("#tabs-1 > div > div:eq(4) > table > tbody > tr>td:eq(2)").text();
                    if(!"".equals(ruxue)&&null!=ruxue&&ruxue.split("：").length>1){

                        nonoBank.setRuxueTime(ruxue.split("：")[1].trim());
                    }
                    //收入来源
                    String shouru = objectDoc.select("#tabs-1 > div > div:eq(7) > table > tbody > tr > td:eq(0)").text();
                    if(!"".equals(shouru)&&null!=shouru&&shouru.split("：").length>1){
                        nonoBank.setShourulaiyuan(shouru.split("：")[1].trim());
                    }
                    //家庭年收入
                    String jiating = objectDoc.select("#tabs-1 > div > div:eq(7) > table > tbody > tr > td:eq(1)").text();
                    if(!"".equals(jiating)&&null!=jiating&&jiating.split("：").length>1){

                        nonoBank.setJiatingnianshouru(jiating.split("：")[1].trim());
                    }
                    //月均工资
                    String yue =objectDoc.select("#tabs-1 > div > div:eq(7) > table > tbody > tr > td:eq(2)").text();
                    if(!"".equals(yue)&&null!=yue&&yue.split("：").length>1){
                        nonoBank.setYuejunshouru(yue.split("：")[1].trim());
                    }
                    nonoBank.setFlag("1");
                    nonoBank.setUpdateTime(new Date());
                    nonoBankDao.update(nonoBank);
                    logger.info("******"+startPageNo+"名校贷同步明细成功的编号:" + nonoBank.getProjectNo());
                    success++;
                }
            }catch(Exception e){
                error++;
                logger.error("******名校贷同步明细失败的编号:" + nonoBank.getProjectNo(), e);
                if(!e.getMessage().endsWith("Read timed out")){
                    e.printStackTrace();
                }
                nonoBank.setFlag(null);
                nonoBankDao.update(nonoBank);
            }
        }
        resMap.put("error",error);
        resMap.put("success",success);
        return resMap;
    }
}
