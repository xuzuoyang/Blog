package com.dkd.common.utils;

import java.util.*;

/**
 * @description 静态字典表.定义一些可选的常量参数，可以用来实现页面上的单选、多选、下拉选项.
 * @description 特别注意：为了不影响到其他同事，提交时请先更新再提交.
 * @description 大家可按需要增加字典项.使用方法：List<Dic> list =
 *              CHTStaticDictionary.getUnitList(CHTStaticDictionary.Dic_1573_UNIT);
 * @author LiuGang
 * @email gliu@ice.com
 * @version v1.0,2012-02
 * @time 6:07:18 PM, Feb 6, 2012
 */
public class CHTStaticDictionary {
	// boolean
	public static String DIC_Boolean = "Boolean";

	/**
	 * 酒坛数量单位.
	 */
	public static String DIC_1573_UNIT = "1573";

	/**
	 * 人参数量单位
	 */
	public static String DIC_RENSHEN_UNIT = "renshen";

	/**
	 * 提货卡使用情况.
	 */
	public static String DIC_CARD_USED = "Card_Used";

	/**
	 * 提货卡状态.
	 */
	public static String DIC_CARD_STATUS = "Card_Status";

	/**
	 * 开卡状态.
	 */
	public static String DIC_CARDOPEN_STATUS = "Cardopen_Status";

	/**
	 * 开卡操作类型.
	 */
	public static String DIC_CARDOPEN_TYPE = "Cardopen_Type";

	/**
	 * 提货卡发放状态
	 */
	public static String DIC_CARD_SENT = "Card_Sent";

	/**
	 * 提货状态
	 */
	public static String DIC_GOOD_DETAIL_STATUS = "Good_Detail_Status";

	/**
	 * 提货详细信息付款状态
	 */
	public static String DIC_GOOD_DETAIL_FEE_STATUS = "Good_Detail_Fee_Status";

	private static Map<Object, Map<String, String>> dictionary = new HashMap<Object, Map<String, String>>();
	static {
		init();
	}

	private static void init() {
		Map<String, String> unit = null;
		// 公共字典选项
		unit = new LinkedHashMap<String, String>();
		unit.put("是", "1");
		unit.put("否", "0");
		dictionary.put(CHTStaticDictionary.DIC_Boolean, unit);

		// 定义酒坛数量单位
		unit = new LinkedHashMap<String, String>();
		// 为保证数据的准确性，value值可以由常量导入，如com.zbxsoft.chtrans.common.ConstantData.AAA
		unit.put("1坛", ConstantData.UNIT_1573_1);
		unit.put("5坛", ConstantData.UNIT_1573_5);
		unit.put("10坛", ConstantData.UNIT_1573_10);
		unit.put("50坛", ConstantData.UNIT_1573_50);
		unit.put("100坛", ConstantData.UNIT_1573_100);
		dictionary.put(CHTStaticDictionary.DIC_1573_UNIT, unit);

		// 定义人参数量单位
		unit = new LinkedHashMap<String, String>();
		unit.put("1kg", "1");
		unit.put("5kg", "5");
		unit.put("10kg", "10");
		unit.put("50kg", "50");
		unit.put("100kg", "100");
		dictionary.put(CHTStaticDictionary.DIC_RENSHEN_UNIT, unit);

		// 提货卡状态字典
		unit = new LinkedHashMap<String, String>();
		unit.put("未使用", ConstantData.CARD_STATUS_NOUSER);
		unit.put("待发出", ConstantData.CARD_STATUS_APPLYED);
		unit.put("未激活", ConstantData.CARD_STATUS_UNACTIVITY);
		unit.put("已激活", ConstantData.CARD_STATUS_ACTIVITY);
		unit.put("已开卡", ConstantData.CARD_STATUS_OPEN);
		unit.put("无效卡", ConstantData.CARD_STATUS_INVALID);
		dictionary.put(CHTStaticDictionary.DIC_CARD_STATUS, unit);

		// 开卡操作类型字典
		unit = new LinkedHashMap<String, String>();
		unit.put("提货", ConstantData.CARDOPEN_TYPE_PICKUP);
		unit.put("委托再交易", ConstantData.CARDOPEN_TYPE_COMMTRADE);
		unit.put("挂失", ConstantData.CARDOPEN_TYPE_LOSE);
		dictionary.put(CHTStaticDictionary.DIC_CARDOPEN_TYPE, unit);

		// 开卡状态类型字典
		unit = new LinkedHashMap<String, String>();
		unit.put("正常", ConstantData.CARDOPEN_STATUS_NORMAL);
		unit.put("已处理", ConstantData.CARDOPEN_STATUS_DEAL);
		unit.put("异常", ConstantData.CARDOPEN_STATUS_ABNORMAL);
		dictionary.put(CHTStaticDictionary.DIC_CARDOPEN_STATUS, unit);

		// 提货卡发放状态字典
		unit = new LinkedHashMap<String, String>();
		unit.put("处理中", ConstantData.CARD_SENT_NO);
		unit.put("已发放", ConstantData.CARD_SENT_YES);
		dictionary.put(CHTStaticDictionary.DIC_CARD_SENT, unit);

		// 提货状态
		unit = new LinkedHashMap<String, String>();
		unit.put("申请提货", ConstantData.GOODDETAIL_INIT);
		unit.put("已填写详细信息", ConstantData.GOODDETAIL_APPLY);
		unit.put("已确认提货信息", ConstantData.GOODDETAIL_CONFIRM);
		unit.put("已确定提货信息", ConstantData.GOODDETAIL_DELIVERY);// 已确定提货信息 // 已发货
		unit.put("已收货", ConstantData.GOODDETAIL_ACCEPT);
		unit.put("已撤销", ConstantData.GOODDETAIL_CANCEL);
		dictionary.put(CHTStaticDictionary.DIC_GOOD_DETAIL_STATUS, unit);

		// 付款状态
		unit = new LinkedHashMap<String, String>();
		unit.put("未付款", ConstantData.GOODDETAIL_FEESTATUS_NO);
		unit.put("已付款", ConstantData.GOODDETAIL_FEESTATUS_YES);
		dictionary.put(CHTStaticDictionary.DIC_GOOD_DETAIL_FEE_STATUS, unit);
	}

	public static Map<String, String> getUnitMap(String unitName) {
		Map<String, String> unit = dictionary.get(unitName);
		if (unit == null) {
			unit = new LinkedHashMap<String, String>();
		}
		return unit;
	}

	/**
	 * 获取字典项指定项的列表.例子见main方法.
	 * 
	 * @param unitName
	 *            字典项的名字 CHTStaticDictionary.Dic_Boolean
	 * @return
	 */
	public static List<Dic> getUnitList(String unitName) {
		List<Dic> unitList = new ArrayList<Dic>();
		Map<String, String> unit = dictionary.get(unitName);
		if (unit == null) {
			unit = new LinkedHashMap<String, String>();
		}
		Set<String> set = unit.keySet();
		String key = "";
		if (!set.isEmpty()) {
			Iterator<String> it = set.iterator();
			while (it.hasNext()) {
				key = it.next();
				String value = unit.get(key);
				unitList.add(new Dic(key, value));
			}
		}
		return unitList;
	}

	public static void main(String args[]) {
		// 获得1573酒的数量单位
		List<Dic> list = CHTStaticDictionary.getUnitList(CHTStaticDictionary.DIC_1573_UNIT);
		for (Dic d : list) {
			System.out.println(d.getTitle() + "," + d.getValue());
		}
	}
}
