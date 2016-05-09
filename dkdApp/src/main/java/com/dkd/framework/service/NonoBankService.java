package com.dkd.framework.service;

import com.dkd.framework.entity.NoNoBank;
import net.sf.json.JSONArray;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface NonoBankService {
   public List<NoNoBank> select(Map<String, Object> reqMap);

   public Long selectCount(Map<String, Object> selectMap);

   public void saveSynData(JSONArray synData,int startPageNo);

   public Map<String, Object> saveSynDetailData(List<NoNoBank> select,int startPageNo);
}
