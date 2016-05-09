package com.dkd.common.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class CheckPassword {

	private Socket socket;
	private BufferedReader in;
	private PrintWriter out;

	public boolean involve(String str) {
		try {
			String result = null;
			String ip = ConfigUtils.readJdbcString("check_xiaoma_pwd");
			socket = new Socket(ip, 9527);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream(), true);
			out.println(str);
			// 如果得到服务端响应 新关闭与服务端的连接
			boolean flag = true;
			while (flag) {
				if (in.ready()) {
					result = in.readLine();
					break;
				}
			}
			if (result != null) {
				if (result.equals("1")) {
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}

		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
				if (socket != null) {
					socket.close();
				}
			} catch (Exception e) {

			}
		}
	}

	/**
	 * @param args
	 */
//	public static void main(String[] args) {
//
//		System.out.println(new CheckPassword().involve("sp87654321,12345678"));
//		//System.out.println(new CheckPassword().involve("gl123#12345678"));
//
//	}

}
