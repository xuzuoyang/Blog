package com.dkd.framework.service;

import com.alibaba.druid.util.StringUtils;
import com.dkd.common.httpClient.ConnectionUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class SpiderUtils {
    @Value("${reqTimeOut}")
    private  int reqTimeOut;
    @Value("${PHPSESSID}")
    private  String PHPSESSID;
    public    Long getSynData() {
        Long total = null ;
        ConnectionUtil instance = ConnectionUtil.getInstance();
        try {
            String dataResult = instance.doHTTPsGet("http://www.nonobank.com/Lend/GetLendList/0/0", "UTF-8");
            if(dataResult!=null && dataResult.length()>2){
                dataResult = dataResult.substring(dataResult.indexOf('{'),dataResult.lastIndexOf('}')+1);
                JSONObject jsonObject = JSONObject.fromObject(dataResult);
                if(jsonObject != null && jsonObject.has("total")){
                    total = Long.parseLong(jsonObject.optString("total"));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return total;
    }

    public  JSONArray getSynData(int pageNo,int pageSize) {
        JSONArray jsonArray = null ;
        ConnectionUtil instance = ConnectionUtil.getInstance();
        try {
             String dataResult = instance.doHTTPsGet("http://www.nonobank.com/Lend/GetLendList/" + pageSize + "/" + pageNo, "UTF-8");
            if(dataResult!=null && dataResult.length()>2){
                dataResult = dataResult.substring(dataResult.indexOf('{'),dataResult.lastIndexOf('}')+1);
                JSONObject jsonObject = JSONObject.fromObject(dataResult);
                if(jsonObject != null && jsonObject.has("members")){
                    String string = jsonObject.optString("members");
                    jsonArray = JSONArray.fromObject(string);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return jsonArray;
    }



    public   Document getDataByCookieLogin(String projectNo)  {
        Document document = null ;
        try {
            Connection con = Jsoup.connect("https://www.nonobank.com/Lend/View/" + projectNo);
            //设置访问形式（电脑访问，手机访问）：直接百度都参数设置
            con.header("User-Agent", "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1)");
            //把登录信息的cookies保存如map对象里面
            con = con.cookie("PHPSESSID", PHPSESSID);
            document = con.timeout(reqTimeOut).get();
        }catch (Exception e){
            if(!"Read timed out".equals(e.getMessage())){
                e.printStackTrace();
            }
        }
        return document;
    }


    public   JSONObject getJSONObject(int userId)  {
        JSONObject jsonObject = null ;
        try {
            ConnectionUtil connectionUtil = ConnectionUtil.getInstance();
            Map<String,String> reqMap = new HashMap<String,String>();
            reqMap.put("id", String.valueOf(userId));
            String doHTTPsPOST = connectionUtil.doHTTPsPOST("", reqMap);
            if(doHTTPsPOST!=null &&doHTTPsPOST.length()>5 ) {
                jsonObject= JSONObject.fromObject(doHTTPsPOST);
            }
        }catch (Exception e){
        }
        return jsonObject;
    }


    public   Document getKaoPuNiaoHtml(String webUrl)  {
        Document document = null ;
        try {
            Connection con = Jsoup.connect(webUrl);
            //设置访问形式（电脑访问，手机访问）：直接百度都参数设置
            con.header("User-Agent", "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1)");
            //把登录信息的cookies保存如map对象里面
            document = con.timeout(reqTimeOut).get();
        }catch (Exception e){
            if(!"Read timed out".equals(e.getMessage())){
                e.printStackTrace();
            }
        }
        return document;
    }

    public String getBirdList(int pageNo, int pageSize) {
        String resHtml = null ;
        ConnectionUtil connectionUtil = ConnectionUtil.getInstance();
        Map<String,String> reqMap = new HashMap<String,String>();
        reqMap.put("pageNumber", String.valueOf(pageNo));
        reqMap.put("pageSize", String.valueOf(pageSize));
        reqMap.put("sortColumns", "l.id desc");
        reqMap.put("search", "search");
        try {
             resHtml = connectionUtil.doHTTPsPOST("https://kaopuniao.com/loan/list/all", reqMap);
        }catch (Exception e){
            e.printStackTrace();
        }

        return  resHtml;
    }

    public JSONArray getBirdListByApi(int pageNo,int pageSize) {
        String resHtml = null ;
        JSONArray jsonArray = null;
        ConnectionUtil connectionUtil = ConnectionUtil.getInstance();
        Map<String,String> reqMap = new HashMap<String,String>();
        reqMap.put("pageNo", String.valueOf(pageNo));
        reqMap.put("pageSize", String.valueOf(pageSize));
        reqMap.put("category", "0");
        reqMap.put("version", "30");
        try {
            resHtml = connectionUtil.doHTTPsPOST("https://www.kaopuniao.com/interface/loan/list", reqMap);
        }catch (Exception e){
            e.printStackTrace();
        }

        if(!StringUtils.isEmpty(resHtml)){
            //解析json
            JSONObject jsonObject = JSONObject.fromObject(resHtml);
            if(jsonObject.has("data")){
                JSONObject jsonObjectData = JSONObject.fromObject(jsonObject.opt("data"));
                jsonArray = JSONArray.fromObject(jsonObjectData.optString("results"));
            }
        }
    return jsonArray;
    }
}
