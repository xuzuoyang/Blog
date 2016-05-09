package com.dkd.framework.service;

import com.dkd.framework.entity.NoNoBank;
import com.dkd.framework.entity.NonoUser;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.List;
import java.util.Map;

public interface NonoUserService {
   public List<NonoUser> select(Map<String, Object> reqMap);

   public void save(JSONObject jsonObject);

   public NonoUser selectByUserId(int userId);

   int selectMaxUserId();
}
