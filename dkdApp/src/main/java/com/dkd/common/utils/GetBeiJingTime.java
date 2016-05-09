 /*****************************
 * Copyright (c) 2013 by ZbxSoft Co. Ltd.  All rights reserved.
 ****************************/
package com.dkd.common.utils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Calendar;
import java.util.Date;

 public class GetBeiJingTime {

     /**
      * @param args
      */
     public static String getTime() {
         // TODO Auto-generated method stub
         // TODO Auto-generated method stub
         Date date = null;
         try {
             URL url=new URL("http://www.bjtime.cn");//取得资源对象
             URLConnection uc=url.openConnection();//生成连接对象
             uc.connect(); //发出连接
             long ld=uc.getDate(); //取得网站日期时间
             date = DateUtils.fromLong(ld);
             //分别取得时间中的小时，分钟和秒，并输出
         } catch (MalformedURLException e) {
             // TODO Auto-generated catch block
             e.printStackTrace();
         } catch (IOException e) {
             // TODO Auto-generated catch block
             e.printStackTrace();
         }
         Calendar cd = Calendar.getInstance();
         cd.setTime(date);
         return cd.get(Calendar.YEAR)+"-"+(cd.get(Calendar.MONTH)+1)+"-"+cd.get(Calendar.DAY_OF_MONTH)+" "+cd.get(Calendar.HOUR_OF_DAY)+":"+cd.get(Calendar.MINUTE)+":"+cd.get(Calendar.SECOND);
     }

 }
