/**
 * 
 */
package com.dkd.common.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 用于操作txt文件的帮助类
 * @author lvcai
 *
 */
public class txtFileUtils {

	/*
	 * 将txt文件内容读取完后一起返回
	 */
	public static String readFileByLines(String fileName) {
		String returnInfo = "";
		File file = new File(fileName);
		BufferedReader reader = null;
		try {
			String tempString = null;
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));

			// 一次读入一行，直到读入null为文件结束
			while ((tempString = reader.readLine()) != null) {
				returnInfo += tempString + "\n";
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}
		return returnInfo;
	}

	/*
	 * 将txt文件内容按行分割，保存到集合中一起返回
	 */
	public static List<String> readFileByLinesToListGB2312(String fileName) {
		List<String> str_list = new ArrayList<String>();
		File file = new File(fileName);
		BufferedReader reader = null;
		try {
			String tempString = null;
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "gb2312"));

			// 一次读入一行，直到读入null为文件结束
			while ((tempString = reader.readLine()) != null) {
				if (StrUtil.isNotEmpty(tempString.trim())) {
					str_list.add(tempString.trim());
				}
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}
		return str_list;
	}

	/*
	 * 将txt文件内容按行分割，保存到集合中一起返回
	 */
	public static List<String> readFileByLinesToList(String fileName) {
		List<String> str_list = new ArrayList<String>();
		File file = new File(fileName);
		BufferedReader reader = null;
		try {
			String tempString = null;
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));

			// 一次读入一行，直到读入null为文件结束
			while ((tempString = reader.readLine()) != null) {
				if (StrUtil.isNotEmpty(tempString.trim())) {
					str_list.add(tempString.trim());
				}
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}
		return str_list;
	}

	/**
	 * 获取文本文件的行数
	 * description:  
	 * @param path  
	 * @author qspu 
	 * @update 2014-11-26
	 */
	public static int getLines(String path) {
		int count = 0;
		File f = new File(path);
		InputStream input = null;
		try {
			input = new FileInputStream(f);
		} catch (FileNotFoundException e) {

			e.printStackTrace();

		}

		BufferedReader b = new BufferedReader(new InputStreamReader(input));

		String value = null;
		try {
			value = b.readLine();
		} catch (IOException e) {

			e.printStackTrace();

		}
		if (value != null)

			while (value != null) {
				count++;
				try {
					value = b.readLine();
				} catch (IOException e) {

					e.printStackTrace();

				}
			}
		return count;
	}

	/**
	 * 从指定行开始读取
	 * description:  
	 * @param path
	 * @param lineNum
	 * @return  
	 * @author qspu 
	 * @throws IOException 
	 * @update 2014-11-26
	 */
	public static List<String> readLineByNum(String path, int lineNum) {
		List<String> str_list = new ArrayList<String>();
		FileReader in = null;
		try {
			in = new FileReader(path);
		} catch (FileNotFoundException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		LineNumberReader reader = new LineNumberReader(in);
		int linesNUM = getLines(path);
		String s = "";
		if (lineNum <= 0 || lineNum > linesNUM) {
			System.out.println("不在文件的行数范围(1至总行数)之内。");
		}
		int lines = 0;
		while (s != null) {
			lines++;
			try {
				s = reader.readLine();
			} catch (IOException e) {

				// TODO Auto-generated catch block
				e.printStackTrace();

			}
			if ((lines - lineNum) >= 0 && s != null) {
				str_list.add(s);
			}
		}
		try {
			reader.close();
		} catch (IOException e1) {

			e1.printStackTrace();

		}
		try {
			in.close();
		} catch (IOException e) {

			e.printStackTrace();

		}
		return str_list;
	}

	public static void main(String[] args) {
		txtFileUtils.readFileByLines("F:\testBank.txt");
	}
}
