package com.dkd.framework.service.impl;

import com.dkd.framework.dao.NonoUserDao;
import com.dkd.framework.entity.NonoUser;
import com.dkd.framework.service.NonoUserService;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class NonoUserServiceImpl implements NonoUserService {

    private static Logger logger  = Logger.getLogger(NonoUserServiceImpl.class);
    @Autowired
    private NonoUserDao nonoUserDao;

    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,readOnly=true)
    public List<NonoUser> select(Map<String, Object> reqMap) {
        return nonoUserDao.select(reqMap);
    }
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,readOnly=false)
    public void save(JSONObject jsonObject) {
        if(jsonObject!=null && jsonObject.has("id")){
            NonoUser nonoUser = nonoUserDao.selectByUserId(String.valueOf(jsonObject.optString("id")));
            if(nonoUser == null ) {
                nonoUser = new NonoUser();
                nonoUser.setUserName(jsonObject.has("user_name")&&jsonObject.optString("user_name").length()>0?jsonObject.optString("user_name"):null);
                nonoUser.setRealName(jsonObject.has("real_name")&&jsonObject.optString("real_name").length()>0?jsonObject.optString("real_name"):null);
                nonoUser.setMobile(jsonObject.has("mobile_num")&&jsonObject.optString("mobile_num").length()>0?jsonObject.optString("mobile_num"):null);
                nonoUser.setUserId(jsonObject.optString("id"));
                nonoUser.setIdCard(jsonObject.has("id_num")&&jsonObject.optString("id_num").length()>0?jsonObject.optString("id_num"):null);
                if ("3".equals(jsonObject.optString("user_type").trim())) {
                    nonoUser.setUserType("白领");
                }
                else if ("4".equals(jsonObject.optString("user_type").trim())) {
                    nonoUser.setUserType("微企业主");
                }
                else if ("5".equals(jsonObject.optString("user_type").trim())) {
                    nonoUser.setUserType("学生");
                }
                else if ("6".equals(jsonObject.optString("user_type").trim())) {
                    nonoUser.setUserType("企业主");
                } else {
                    nonoUser.setUserType(jsonObject.has("user_type")&&jsonObject.optString("user_type").length()>0?jsonObject.optString("user_type"):null);
                }
                nonoUser.setCreateTime(new Date());
                nonoUserDao.save(nonoUser);
            }
        }

    }

    public NonoUser selectByUserId(int userId) {
        return nonoUserDao.selectByUserId(String.valueOf(userId));
    }

    public int selectMaxUserId() {
        return nonoUserDao.selectMaxUserId();
    }

}
