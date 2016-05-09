package com.dkd.common.utils;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * @{#} NumUtil.java Create on 2012-5-2
 * <p>
 * 	扩展处理数字类
 * </p>
 * @author hexin  
 * @version V1.0
 */
public class NumUtil {
	/**
	 * 获取格式化后的数字
	 * @param number 待转换的数字
	 * @param len 指定转换后小数位数
	 * @param isdf 是否带千分位分隔符
	 * @return
	 */
	public static String getDecimalFormatNum(double number, int len, boolean isdf) {
		String result = String.valueOf(number);
		if (number > 0) {
			String df = "###,###,##0";
			if (!isdf) {
				df = df.replaceAll(",", "");
			}
			if (len > 0) {
				df += ".";
				for (int i = 0; i < len; i++) {
					df += "0";
				}
			}
			DecimalFormat dfNew = new DecimalFormat(df);
			result = dfNew.format(number);
		}
		return result;
	}

	/**
	 * 分数转换为小数(四舍五入)
	 * @param fenshu 分数字符串，注意格式
	 * @param len 指定转换后小数位数
	 * @return
	 */
	public static double getDecimalFormatNum(String fenshu, int len) {
		double result = 0;
		if (StrUtil.isNotNull(fenshu)) {
			int f = fenshu.indexOf("/");
			if (f > 0) {
				double a = Double.parseDouble(fenshu.substring(0, f));
				double b = Double.parseDouble(fenshu.substring(f + 1));
				result = a / b;
			} else {
				result = Double.parseDouble(fenshu);
			}
		}
		result = Double.parseDouble(getDecimalFormatNum(result, len, false));
		return result;
	}

	/*
	 * 转化银行金钱格式
	 */
	public static String formateMoney(String money, String op) {
		String returnInfo = null;

		if (money == null || money.equals("")) {
			return "";
		}
		NumberFormat formater = null;
		if ("1".equals(op.trim())) {//将常用金额格式转为银行的12位格式
			formater = new DecimalFormat("000000000000");

			String info = formater.format(Double.valueOf(money) * 100);
			returnInfo = formater.format(Double.valueOf(info));

		} else if ("2".equals(op.trim())) {//将银行的12位格式转换为常用金额格式
			formater = new DecimalFormat("###.##");
			if ("000000000000".equals(money.trim())) {
				returnInfo = "0.00";
			} else {
				String info = formater.format(Double.valueOf(money));
				returnInfo = info.substring(0, info.length() - 2) + "."
						+ info.substring(info.length() - 2, info.length());
			}
		}
		return returnInfo;
	}

	public static String formateMoney(String money, int op) {
		String returnInfo = null;

		if (money == null || money.equals("")) {
			return "";
		}
		NumberFormat formater = null;
		if (op == 1) {//将常用金额格式转为银行的14位格式
			formater = new DecimalFormat("00000000000000");

			String info = formater.format(Double.valueOf(money) * 100);
			returnInfo = formater.format(Double.valueOf(info));

		} else if (op == 2) {//将银行的14位格式转换为常用金额格式
			formater = new DecimalFormat("###.##");
			if ("00000000000000".equals(money.trim())) {
				returnInfo = "0.00";
			} else {
				String info = formater.format(Double.valueOf(money));
				returnInfo = info.substring(0, info.length() - 2) + "."
						+ info.substring(info.length() - 2, info.length());
			}
		}

		return returnInfo;
	}

	/**
	 * 将银行的钱转成分
	 * description:  
	 * @param money
	 * @param op
	 * @return  
	 * @author qspu 
	 * @update 2014-9-15
	 */
	public static String formateMoneyB(String money, int op) {
		String returnInfo = null;

		if (money == null || money.equals("")) {
			return "";
		}
		NumberFormat formater = null;
		if (op == 1) {//将常用金额格式转为银行的14位格式
			formater = new DecimalFormat("00000000000000");

			String info = formater.format(Double.valueOf(money) * 100);
			returnInfo = formater.format(Double.valueOf(info));

		} else if (op == 2) {//将银行的14位格式转换为常用金额格式
			formater = new DecimalFormat("#####");
			if ("00000000000000".equals(money.trim())) {
				returnInfo = "0";
			} else {
				returnInfo = formater.format(Double.valueOf(money));
			}
		}

		return returnInfo;
	}

	/*
	 * 判断汉字个数
	 */
	public static int getGBKCount(String str) {
		int count = 0;
		String regEx = "[\\u4e00-\\u9fa5]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		while (m.find()) {
			for (int i = 0; i <= m.groupCount(); i++) {
				count = count + 1;
			}
		}
		return count;
	}

	public static String getStr(String info, int length) {
		int hzSize = NumUtil.getGBKCount(info);//汉字个数
		int size = hzSize + info.length();

		for (int i = 0; i < length - size; i++) {
			info += " ";
		}
		return info;
	}

	public static void main(String[] args) {

	}
}
