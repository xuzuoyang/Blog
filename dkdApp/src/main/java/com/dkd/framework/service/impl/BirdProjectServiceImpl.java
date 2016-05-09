package com.dkd.framework.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.dkd.common.utils.DateUtils;
import com.dkd.framework.dao.BirdProjectDao;
import com.dkd.framework.dao.BirdUserDao;
import com.dkd.framework.dao.NonoBankDao;
import com.dkd.framework.entity.BirdProject;
import com.dkd.framework.entity.BirdUser;
import com.dkd.framework.entity.NoNoBank;
import com.dkd.framework.service.BirdProjectService;
import com.dkd.framework.service.NonoBankService;
import com.dkd.framework.service.SpiderUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class BirdProjectServiceImpl implements BirdProjectService {

    private static Logger logger  = Logger.getLogger(BirdProjectServiceImpl.class);
    @Autowired
    private BirdProjectDao birdProjectDao;
    @Autowired
    private SpiderUtils spiderUtils;
    @Autowired
    private BirdUserDao birdUserDao;

    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,readOnly=false)
    public void saveBirdproject(BirdProject birdProject) {
       BirdProject birdProjectQuery =  birdProjectDao.getBirdProjectByProjectNo(birdProject);
        if(birdProjectQuery == null ){
            birdProjectDao.saveBirdProject(birdProject);
            logger.info("******靠谱鸟同步列表编号:" + birdProject.getProjectNo());
        }
    }
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,readOnly=false)
    public void synBirdDetail() {
        Map<String,Object> reqMap  = new HashMap<String, Object>();
        reqMap.put("pageIndex",0);
        reqMap.put("pageSize",200);
        List<BirdProject> list = birdProjectDao.getList(reqMap);
        for (BirdProject birdProject : list) {
            try {
                //根据项目编号查询详细信息
                Document document = spiderUtils.getKaoPuNiaoHtml("https://kaopuniao.com/invest/detail/" + birdProject.getProjectNo());
                if (document != null) {
                    birdProject.setTitle(document.select("body > div.main > div.tb_infom > div.tb_info > div.tb_infol > div.tbjk_tit > div.tbjk_titinfo > div").text());
                    birdProject.setKaopudengji(document.select("body > div.main > div.tb_infom > div.tb_info > div.tb_infol > div.tbjk_tit > div.tbjk_titinfo > p > i").text());
                    String totalMoney = document.select("body > div.main > div.tb_infom > div.tb_info > div.tb_infol > table > tbody > tr:eq(1) > td:eq(0)").text();
                    if (!StringUtils.isEmpty(totalMoney)) {
                        if (totalMoney.contains("千元")) {
                            totalMoney = new BigDecimal(totalMoney.replace("千元", "")).multiply(new BigDecimal("1000")).toString();
                        }
                        birdProject.setTotalMoney(totalMoney);
                    }
                    birdProject.setYearRate(document.select("body > div.main > div.tb_infom > div.tb_info > div.tb_infol > table > tbody > tr:eq(1) > td:eq(1) > span").text().replace("%", ""));
                    String duteTime = document.select("body > div.main > div.tb_infom > div.tb_info > div.tb_infol > table > tbody > tr:eq(1) > td:eq(2)").text();
                    if(!StringUtils.isEmpty(duteTime)){

                        birdProject.setDuteTime(duteTime.contains("个月")?duteTime.replace("个月", ""):duteTime);
                    }

                    birdProject.setHuankuanfangshi(document.select("body > div.main > div.tb_infom > div.tb_info > div.tb_infol > table > tbody > tr:eq(1) > td:eq(3)").text());
                    birdProject.setJiekuanyongtu(document.select("body > div.main > div.tb_infom > div.tb_info > div.tb_infol > div.tb_infol_yt > div:eq(0) > p").text());
                    birdProject.setJiekuanmiaoshu(document.select("body > div.main > div.tb_infom > div.tb_info > div.tb_infol > div.tb_infol_yt > div:eq(1) > p").text());
                    birdProject.setHuankuanlaiyuan(document.select("body > div.main > div.tb_infom > div.tb_info > div.tb_infol > div.tb_infol_yt > div:eq(2) > p").text());
                    String status = document.select(".tb_info_state").text();
                    if(StringUtils.isEmpty(status)){
                        status = document.select(".tb_info_state2").text();
                    }
                    birdProject.setStatus(status);
                    String ketou = document.select("body > div.main > div.tb_infom > div.tb_info > div.tb_infor > div > div > div:eq(1) > span:eq(1)").text();
                    if (!StringUtils.isEmpty(ketou)) {
                        birdProject.setKetouMoney(ketou.replace("￥", "").replace(",", "").trim());
                    }else{
                        birdProject.setKetouMoney("0");
                    }
                    String toubiaojindu = document.select("body > div.main > div.tb_infom > div.tb_info > div.tb_infor > div > div > div.tb_info_jindu > span:eq(3)").text();
                    if (!StringUtils.isEmpty(toubiaojindu)) {
                        birdProject.setToubiaojindu(toubiaojindu.replace("%", "").trim());
                    }else{
                        birdProject.setToubiaojindu("100");
                    }
                    String shengyuTime = document.select("body > div.main > div.tb_infom > div.tb_info > div.tb_infor > div > div > div.tb_info_jindu > div:eq(4) > span").text();
                    if (!StringUtils.isEmpty(shengyuTime)) {
                        birdProject.setShengyutianshu(shengyuTime.trim());
                    }else{
                        birdProject.setShengyutianshu("0");
                    }
                    String userId = document.select("body > div.main > div.three_tb_infom > div > iframe").attr("src");
                    if (!StringUtils.isEmpty(userId)) {
                        userId = userId.split("userId=")[1].replace("&", "").trim();
                        birdProject.setUserId(userId);
                    }
                    birdProject.setUpdateTime(new Date());
                   birdProjectDao.updateBirdProject(birdProject);

                    BirdUser birdUser = birdUserDao.getBirdUserByUserId(new BirdUser(userId));
                    if(birdUser == null ){
                        birdUser = new BirdUser();
                        birdUser.setSex(document.select(".two_tb_ul > li:eq(0)").attr("title"));
                        birdUser.setNianling(document.select(".two_tb_ul > li:eq(1)").attr("title"));
                        birdUser.setUserType(document.select(".two_tb_ul > li:eq(2)").attr("title"));
                        birdUser.setSchool(document.select(".two_tb_ul > li:eq(3)").attr("title"));
                        birdUser.setXueyuan(document.select(".two_tb_ul > li:eq(4)").attr("title"));
                        birdUser.setProvince(document.select(".two_tb_ul > li:eq(5)").attr("title"));
                        birdUser.setCity(document.select(".two_tb_ul > li:eq(6)").attr("title"));

                        String signImg = "";
                        for (Element img : document.select(".pic_befor > div").select("img")) {
                            signImg+=img.attr("src")+";";
                        }
                        birdUser.setUserId(userId);
                        birdUser.setSignImg(signImg);
                        birdUser.setCreateTime(new Date());
                        birdUser.setUpdateTime(new Date());
                        birdUserDao.saveBirdUser(birdUser);
                    }
                    logger.info("******靠谱鸟同步标的详情编号:" + birdProject.getProjectNo());



                }
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }

    public void synBirdUserDetail() {

    }

    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,readOnly=false)
    public void saveBirdproject(JSONArray birdListByApi) {
        for (int i = 0; i <birdListByApi.size() ; i++) {
            //先取编号判断是否存在
            JSONObject birdJson = JSONObject.fromObject(birdListByApi.get(i));

            if(birdJson.has("contractNumber")){
                BirdProject birdProject = birdProjectDao.getBirdProjectByProjectNo(new BirdProject(birdJson.optString("contractNumber")));
                if(birdProject == null){
                    birdProject = new BirdProject();
                    birdProject.setProjectNo(birdJson.optString("contractNumber"));
                    birdProject.setCreateTime(new Date());
                    birdProject.setUpdateTime(new Date());
                    birdProjectDao.saveBirdProject(birdProject);
                    logger.info("******靠谱鸟同步列表编号:" + birdProject.getProjectNo());
                }

            }
        }

    }

}
