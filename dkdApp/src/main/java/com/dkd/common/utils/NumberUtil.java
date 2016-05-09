package com.dkd.common.utils;

import java.math.BigDecimal;
import java.util.Date;

public class NumberUtil {

	public static int doNumberNull(Integer n) {
		int number = 0;
		if (n != null) {
			return n;
		} else {
			return number;
		}
	}

	public static Long doNumberNull(Long n) {
		Long number = 0L;
		if (n != null) {
			return n;
		} else {
			return number;
		}
	}

	public static Double doNumberNull(Double n) {
		Double number = 0d;
		if (n != null) {
			return n;
		} else {
			return number;
		}
	}

	/**
	 * 返回一个当天日期加四位随机数
	 * @return
	 */
	public static String getRandomNum() {
		int rand = (int) ((Math.random() * 9 + 1) * 1000);
		String str = DateUtils.format(new Date(), "yyyyMMdd");
		return str + rand;
	}

	/**
	 * 返回一个当天日期加两位位随机数
	 * @return
	 */
	public static String getRandomNum1() {
		int rand = (int) ((Math.random() * 9 + 1) * 10);
		String str = DateUtils.format(new Date(), "yyMMdd");
		return str + rand;
	}

	/**
	 * 把数字金额转成大写
	 */
	public static String toBigMode(double value) {
		final char[] NUMBER_CHAR = "零壹贰叁肆伍陆柒捌玖".toCharArray(); // 大写数字  
		final String[] IN_UNIT_CHAR = { "", "拾", "佰", "仟" }; // 段内字符  
		final String[] UNIT_NAME = { "", "万", "亿", "万亿" }; // 段名  

		long longValue = (long) (value * 100); // 转换成整数  
		// System.out.println(longValue);  
		String valStr = new BigDecimal(Math.ceil(longValue)).toString(); // 转换成字符串  

		StringBuilder prefix = new StringBuilder(); // 整数部分转化的结果  
		StringBuilder suffix = new StringBuilder(); // 小数部分转化的结果  

		if (valStr.length() <= 2) // 只有小数部分  
		{
			prefix.append("零元");
			if (valStr.equals("0")) {
				suffix.append("零角零分");
			} else if (valStr.length() == 1) {
				suffix.append(NUMBER_CHAR[valStr.charAt(0) - '0']).append("分");
			} else {
				suffix.append(NUMBER_CHAR[valStr.charAt(0) - '0']).append("角");
				suffix.append(NUMBER_CHAR[valStr.charAt(1) - '0']).append("分");
			}
		} else {
			int flag = valStr.length() - 2;
			String head = valStr.substring(0, flag); // 取整数部分  
			String rail = valStr.substring(flag); // 取小数部分  

			if (head.length() > 13) {
				return "数值太大(最大支持13位整数)，无法处理。";
			}

			// 处理整数位  
			char[] ch = head.toCharArray();
			int zeroNum = 0; // 连续零的个数  
			for (int i = 0; i < ch.length; i++) {
				int index = (ch.length - i - 1) % 4; // 取段内位置，介于 3 2 1 0  
				int indexLoc = (ch.length - i - 1) / 4; // 取段位置，介于 3 2 1 0  

				if (ch[i] == '0') {
					zeroNum++;
				} else {
					if (zeroNum != 0) {
						if (index != 3) {
							prefix.append("零");
						}
						zeroNum = 0;
					}
					prefix.append(NUMBER_CHAR[ch[i] - '0']); // 转换该位置的数  

					prefix.append(IN_UNIT_CHAR[index]); // 添加段内标识  
				}

				if (index == 0 && zeroNum < 4) // 添加段名  
				{
					prefix.append(UNIT_NAME[indexLoc]);
				}
			}
			prefix.append("元");

			// 处理小数位  
			if (rail.equals("00")) {
				suffix.append("整");
			} else if (rail.startsWith("0")) {
				suffix.append(NUMBER_CHAR[rail.charAt(1) - '0']).append("分");
			} else {
				suffix.append(NUMBER_CHAR[rail.charAt(0) - '0']).append("角");
				suffix.append(NUMBER_CHAR[rail.charAt(1) - '0']).append("分");
			}
		}

		return prefix.append(suffix).toString();
	}
}
