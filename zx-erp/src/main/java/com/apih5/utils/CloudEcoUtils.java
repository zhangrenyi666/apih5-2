package com.apih5.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CloudEcoUtils {


	/**
	 * 
	 * @param url
	 * @param param
	 * @param outParam
	 * @return
	 */
	public static String sendPost4Business(String url, String param,String Authorization) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = null;
		try {
			URL realUrl = new URL(url);

			URLConnection conn = realUrl.openConnection();

			conn.setRequestProperty("Content-Type","application/json");

			conn.setRequestProperty("User-Agent", "User-Agent: Mozilla/5.0 (Windows NT 10.0; ...) Gecko/20100101 Firefox/61.0");
			conn.setRequestProperty("Authorization", Authorization);
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);

			out = new PrintWriter(conn.getOutputStream());

			out.print(param);

			out.flush();

			in = new BufferedReader(new InputStreamReader(
					conn.getInputStream(), "UTF-8"));
			String line;
			if ((line = in.readLine()) != null) {
				result = new String(line.getBytes(), "UTF-8");
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException e2) {
				throw new RuntimeException(e2);
			}
		}
		return result;
	}
}
