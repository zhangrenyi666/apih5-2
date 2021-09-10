package com.apih5.bean;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

import org.springframework.stereotype.Component;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;

@Component
public class ReportBean {
//	/**
//	 * 方法必须包含三个参数：String，String，Map
//	 *
//	 * @return 集合类型 ，包含字段：id,name,salary
//	 */
//	public Object loadReportDataObject(String dsName, String datasetName, Map<String, Object> parameters) {
//		String url = (String) parameters.get("url") + datasetName;
//		JSONObject json = new JSONObject(parameters);
////		String result = HttpRequest.post(url).header("Content-Type", "application/json; charset=utf-8")// 头信息，多个头信息多次调用此方法即可
////				.body(json).timeout(20000)// 超时，毫秒
////				.execute().body();
//		String result = sendPostToken(url, json.toString(), "");
//		JSONObject jsonObject = new JSONObject(result);
//		
//		JSONArray jsonArray = new JSONArray();
//		jsonArray.add(jsonObject);
//		return jsonArray;
//	}
//
//	public Object loadReportDataObjectToken(String dsName, String datasetName, Map<String, Object> parameters) {
//		String url = (String) parameters.get("url") + datasetName;
//		String token = (String) parameters.get("token");
//		JSONObject json = new JSONObject(parameters);
//		String result = sendPostToken(url, json.toString(), token);
//		JSONObject jsonObject = new JSONObject(result);
//		JSONArray jsonArray = new JSONArray();
//		jsonArray.add(jsonObject);
//		return jsonArray;
//	}
//
//	/**
//	 * 方法必须包含三个参数：String，String，Map
//	 *
//	 * @return 集合类型 ，包含字段：id,name,salary
//	 */
//	public Object loadReportDataList(String dsName, String datasetName, Map<String, Object> parameters) {
//		String url = (String) parameters.get("url") + datasetName;
//		JSONObject json = new JSONObject(parameters);
////		String result = HttpRequest.post(url).header("Content-Type", "application/json; charset=utf-8")// 头信息，多个头信息多次调用此方法即可
////				.body(json).timeout(20000)// 超时，毫秒
////				.execute().body();
//		String result = sendPostToken(url, json.toString(), "");
//		JSONArray jsonArray = new JSONArray(result);
//		return jsonArray;
//	}
//	
//	/**
//	 * 方法必须包含三个参数：String，String，Map
//	 *
//	 * @return 集合类型 ，包含字段：id,name,salary
//	 */
//	public Object loadReportDataListToken(String dsName, String datasetName, Map<String, Object> parameters) {
//		String url = (String) parameters.get("url") + datasetName;
//		String token = (String) parameters.get("token");
//		JSONObject json = new JSONObject(parameters);
//		String result = sendPostToken(url, json.toString(), token);
//		JSONArray jsonArray = new JSONArray(result);
//		return jsonArray;
//	}
	
    /**
     * 方法必须包含三个参数：String，String，Map
     * 注意：对象请求后，最后必须以list形式返回
     *
     * @return 集合类型 ，包含字段：id,name,salary
     */
    public Object loadObjToken(String dsName, String datasetName, Map<String, Object> parameters) {
        String url = (String) parameters.get("url") + datasetName;
        String token = (String) parameters.get("token");
        JSONObject json = new JSONObject(parameters);
        json.remove("url");
        json.remove("token");
        String jsonStr = json.toString();
        if(StrUtil.isNotEmpty(jsonStr)) {
            jsonStr = jsonStr.replaceAll("null", "");
            jsonStr = jsonStr.replaceAll("NULL", "");
        }
        String result = sendPostToken(url, jsonStr, token);
        JSONObject jsonObject = new JSONObject(result);
        JSONArray jsonArray = new JSONArray(result);
        try {
            jsonArray.add(jsonObject.getObj("data"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonArray;
    }
    
    /**
     * 方法必须包含三个参数：String，String，Map
     *
     * @return 集合类型 ，包含字段：id,name,salary
     */
    public Object loadListToken(String dsName, String datasetName, Map<String, Object> parameters) {
        String url = (String) parameters.get("url") + datasetName;
        String token = (String) parameters.get("token");
        JSONObject json = new JSONObject(parameters);
        json.remove("url");
        json.remove("token");
        String jsonStr = json.toString();
        if(StrUtil.isNotEmpty(jsonStr)) {
            jsonStr = jsonStr.replaceAll("null", "");
            jsonStr = jsonStr.replaceAll("NULL", "");
        }
        String result = sendPostToken(url, jsonStr, token);
        JSONObject jsonObject = new JSONObject(result);
        Object obj = new Object();
        try {
            obj = jsonObject.getObj("data");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }
    
    /**
     * 带token的服务端请求
     * 
     * @param url
     * @param param
     * @param token
     * @return
     */
    public static String sendPostToken(String url, String param, String token) {
        PrintWriter out = null;
        BufferedReader in = null;
        StringBuffer message = new StringBuffer();
        try {
            System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
            System.setProperty("sun.net.client.defaultReadTimeout", "10000");
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            conn.setRequestProperty("User-Agent", "User-Agent: Mozilla/5.0 (Windows NT 10.0; …) Gecko/20100101 Firefox/61.0");
//          conn.setRequestProperty("token", token);
            if(StrUtil.isNotEmpty(token)) {
                conn.setRequestProperty("Authorization", "Bearer "+token);
            }
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                // result = new String(line.getBytes(), "UTF-8");
                // result += line;
                message.append(new String(line.getBytes(), "UTF-8"));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        // 使用finally块来关闭输出流、输入流
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
        return message.toString();
    }
}
