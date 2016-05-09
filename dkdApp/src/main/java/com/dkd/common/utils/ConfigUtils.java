package com.dkd.common.utils;

import java.io.IOException;
import java.util.Properties;

/**
 * @description 系统的配置文件. 定义一些系统本身使用、非业务规则范围的经常更改的常量值.也可以定义抛出异常的文字提示.
 * @email 
 * @time 12:15:32 PM, Feb 16, 2012
 */
public class ConfigUtils {
	private static Properties propertiesJdbc = new Properties();
	private static Properties propertiesMemcache = new Properties();
	private static Properties propertiesConfig = new Properties();
	static {
		try {
			propertiesJdbc.load(ConfigUtils.class.getClassLoader().getResourceAsStream("/conf/jdbc.properties"));
			propertiesMemcache.load(ConfigUtils.class.getClassLoader().getResourceAsStream("memcached.properties"));
			propertiesConfig.load(ConfigUtils.class.getClassLoader().getResourceAsStream("/conf/config.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 字符类型
	 * 
	 * @param key
	 * @return
	 */
	public static String readMemcacheString(String key) {
		return (String) propertiesMemcache.get(key);
	}
	public static String readConfigString(String key) {
		return (String) propertiesConfig.get(key);
	}
	public static String readJdbcString(String key) {
		return (String) propertiesJdbc.get(key);
	}

	/**
	 * 数字类型
	 * 
	 * @param key
	 * @return
	 */
	public static int readJdbcInt(String key) {
		try {
			String i = (String) propertiesJdbc.get(key);
			return Integer.parseInt(i);
		} catch (Exception e) {
			return 0;
		}
	}
	public static int readConfigInt(String key) {
		try {
			String i = (String) propertiesConfig.get(key);
			return Integer.parseInt(i);
		} catch (Exception e) {
			return 0;
		}
	}
	
}
