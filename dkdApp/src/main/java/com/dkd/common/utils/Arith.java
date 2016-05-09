package com.dkd.common.utils;

import java.math.BigDecimal;

public class Arith {
	/**
	 * 浮点数精确加法运算
	 * 
	 * @param v1,v2
	 *            进行运算的参数
	 * @param scale
	 *            运算后返回值保留的小数位数
	 * 
	 * @return 两个参数按照精度进行加法运算后的结果
	 */
	public static double add(double v1, double v2, int scale) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return round(b1.add(b2).doubleValue(), scale);
	}

	/**
	 * 浮点数精确减法运算
	 * 
	 * @param v1,v2
	 *            进行运算的参数
	 * @param scale
	 *            运算后返回值保留的小数位数
	 * 
	 * @return 两个参数按照精度进行减法运算后的结果
	 */
	public static double sub(double v1, double v2, int scale) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return round(b1.subtract(b2).doubleValue(), scale);
	}

	/**
	 * 浮点数精确乘法运算
	 * 
	 * @param v1,v2
	 *            进行运算的参数
	 * @param scale
	 *            运算后返回值保留的小数位数
	 * 
	 * @return 两个参数按照精度进行乘法运算后的结果
	 */
	public static double mul(double v1, double v2, int scale) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return round(b1.multiply(b2).doubleValue(), scale);
	}

	/**
	 * 浮点数精确除法运算
	 * 
	 * @param v1,v2
	 *            进行运算的参数
	 * @param scale
	 *            运算后返回值保留的小数位数
	 * 
	 * @return 两个参数按照精度进行除法运算后的结果
	 */
	public static double div(double v1, double v2, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException(
					"The scale must be a positive integer or zero");
		}
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * 浮点数精确四舍五入
	 * 
	 * @param v
	 *            进行运算的参数
	 * @param scale
	 *            运算后返回值保留的小数位数
	 * 
	 * @return 按照精度进行四舍五入后的结果
	 */
	public static double round(double v, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException(
					"The scale must be a positive integer or zero");
		}
		BigDecimal b = new BigDecimal(Double.toString(v));
		BigDecimal one = new BigDecimal("1");
		return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * 按照精度比较两个浮点数是否相等
	 * 
	 * @param v
	 *            进行运算的参数
	 * @param scale
	 *            运算后返回值保留的小数位数
	 * 
	 * @return 按照精度进行四舍五入后的结果
	 */
	public static boolean equal(double v1, double v2, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException(
					"The scale must be a positive integer or zero");
		}
		BigDecimal V1 = new BigDecimal(Double.toString(v1)).divide(
				new BigDecimal(1), scale, BigDecimal.ROUND_HALF_UP);
		BigDecimal V2 = new BigDecimal(Double.toString(v2)).divide(
				new BigDecimal(1), scale, BigDecimal.ROUND_HALF_UP);

		return V1.equals(V2);
	}
}
