package com.dkd.common.utils;

/**
 * @description 字典项,定义选项的类型,key:value.
 * 
 * @author LiuGang
 * @email gliu@ice.com
 * @version v1.0,2012-02
 * @time 6:47:39 PM, Feb 6, 2012
 */

public class Dic {
	private String title; // key
	// 数据库中的status为String类型，因此Integer更改为String类型
	private String value; // value

	public Dic(String title, String value) {
		super();
		this.title = title;
		this.value = value;
	}

	public String getTitle() {
		return title;
	}

	public String getValue() {
		return value;
	}
}
