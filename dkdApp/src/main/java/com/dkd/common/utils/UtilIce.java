package com.dkd.common.utils;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Date;

public class UtilIce {
	public static Object dealNull(Object obj) {

		Object object = obj;
		Field[] fields = object.getClass().getDeclaredFields();

		for (int i = 0; i < fields.length; i++) {
			fields[i].setAccessible(true);
			Object result = null;
			try {
				result = fields[i].get(object);
				if (result == null) {
					String typeName = fields[i].getType().getName();
					if (typeName.equals("java.lang.Long")) {
						fields[i].set(object, 1L);
					} else if (typeName.equals("java.lang.String")) {
						fields[i].set(object, "");
					} else if (typeName.equals("java.math.BigDecimal")) {
						BigDecimal bd = new BigDecimal(0);
						fields[i].set(object, bd);
					} else if (typeName.equals("java.lang.Integer")) {
						fields[i].set(object, 0);
					} else if (typeName.equals("java.util.Date")) {
						fields[i].set(object, new Date());
					} else {
						fields[i].set(object, "");
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return object;
	}

	/**
	 * 返回客戶端IP
	 * @param request
	 * @return
	 */
	public static String getRomteIp(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
}
