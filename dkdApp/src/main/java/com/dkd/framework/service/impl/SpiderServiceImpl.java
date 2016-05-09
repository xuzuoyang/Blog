package com.dkd.framework.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.dkd.framework.entity.BirdProject;
import com.dkd.framework.service.*;
import com.dkd.framework.entity.NoNoBank;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class SpiderServiceImpl implements SpiderService {
    private static Logger logger = Logger.getLogger(SpiderServiceImpl.class);

    @Autowired
    private NonoBankService nonoBankService;
    @Autowired
    private NonoUserService nonoUserService;
    @Autowired
    private SpiderUtils spiderUtils;
    @Autowired
    private BirdProjectService birdProjectService;

    /**
     * 同步名校贷数据
     */
    public void getSynNonoBankData(int startPageNo, int endPageNo, int pageSize) {
        logger.info("******"+startPageNo+"名校贷同步数据开始******");
        //获取名校贷散投总数
        Long synDataCount = spiderUtils.getSynData();
        Map<String, Object> selectMap = new HashMap<String, Object>();
        //获取数据库中存的总数
        Long nonoCount = nonoBankService.selectCount(selectMap);
        //如果大于开始同步数据
        if (synDataCount > nonoCount) {
            // if (synDataCount - nonoCount > 1000) {
            while (true && startPageNo <= endPageNo) {
                startPageNo++;
                JSONArray synData = spiderUtils.getSynData(startPageNo, pageSize);
                nonoBankService.saveSynData(synData,startPageNo);
            }
            /*} else {
                JSONArray synData = spiderUtils.getSynData(0, (int) (synDataCount - nonoCount));
                nonoBankService.saveSynData(synData);
            }*/
        }
        logger.info("******名校贷同步数据结束******" + "名校贷数据:" + synDataCount + "  本地数据：" + nonoCount + "  目前相差：" + (synDataCount - nonoCount));
    }

    public void updateNonoBankDetail(int startPageNo, int endPageNo, int pageSize) {
        logger.info("******名校贷同步标的明细数据开始******");
        int success = 0;
        int error = 0;
        while (true && startPageNo <= endPageNo) {
            startPageNo++;
            Map<String, Object> selectMap = new HashMap<String, Object>();
            selectMap.put("pageIndex", startPageNo);
            selectMap.put("pageSize", pageSize);
            List<NoNoBank> select = nonoBankService.select(selectMap);
            Map<String, Object> resMap = nonoBankService.saveSynDetailData(select,startPageNo);
            success = success + Integer.parseInt(String.valueOf(resMap.get("success")));
            error = error + Integer.parseInt(String.valueOf(resMap.get("error")));
        }
        logger.info("******名校贷同步标的明细数据结束****** 成功：" + success + "   失败：" + error);

    }

    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, readOnly = false)
    public void synUser() {
        int maxUserId = nonoUserService.selectMaxUserId();
        nonoUserService.save(spiderUtils.getJSONObject(maxUserId++));
    }

    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, readOnly = false)
    public void getBirdList() {
        logger.info("******靠谱鸟同步数据开始******");
        int pageNo = 0;
        while (true && pageNo <= 10) {
            String birdList = spiderUtils.getBirdList(pageNo++, 12);
            if (!StringUtils.isEmpty(birdList)) {
                Document parse = Jsoup.parse(birdList);
                Elements select = parse.select(".loanContractNumber");
                if (select != null && select.size() > 0) {
                    Iterator<Element> iterator = select.iterator();
                    while (iterator.hasNext()) {
                        String projectNo = iterator.next().val();
                        //先抓取编号
                        if (!StringUtils.isEmpty(projectNo)) {
                            BirdProject birdProject = new BirdProject();
                            birdProject.setProjectNo(projectNo);
                            birdProject.setCreateTime(new Date());
                            birdProject.setUpdateTime(new Date());
                            birdProjectService.saveBirdproject(birdProject);
                        }
                    }
                }
            }
        }
        logger.info("******靠谱鸟同步数据结束****** pageNo :" + pageNo);
    }

    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, readOnly = false)
    public void synBirdDetail() {
        birdProjectService.synBirdDetail();
    }

    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, readOnly = false)
    public void synBirdUserDetail() {
        birdProjectService.synBirdUserDetail();
    }

    public void synBird() {

    }

    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, readOnly = false)
    public void getBirdListByApi() {
        int no = 0;
        //while (no<=120) {
        no++;
        JSONArray birdListByApi = spiderUtils.getBirdListByApi(no, 20);
        if (birdListByApi != null && birdListByApi.size() > 0) {
            birdProjectService.saveBirdproject(birdListByApi);
        }
        // }
    }
}
