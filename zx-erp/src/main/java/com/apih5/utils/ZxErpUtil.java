package com.apih5.utils;

import java.util.Map;

import com.apih5.framework.utils.HttpUtil;
import com.google.common.collect.Maps;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;

public class ZxErpUtil {
    
    private static String GETEWAY_URL = "http://192.168.1.13:18090/";
    // 修改为自己的服务名，别忘记后面的斜线
    private static String SERVER_NAME = "erp-server-1111/"; 
    
//    // 报表使用，修改ip为自己的
//    private static String BIZ_URL = "http://192.168.1.145:18081/"; 
    
    
    /**
     * 
     * @param url
     * @param param
     * @param outParam
     * @return
     */
    public static String getNewToken(String userId) {
        String url = GETEWAY_URL + "auth-server/oauth/token?grant_type=password&client_id=crcc-laowu&client_secret=1234567&username=USER_NAME&password=123456";
        url = url.replace("USER_NAME", userId);
        String result =  HttpUtil.sendPostJson(url, "");
        JSONObject jsonObject = new JSONObject(result);
        JSONObject dataJSONObject = jsonObject.getJSONObject("data");
        return "Bearer " + dataJSONObject.getStr("access_token");
    }
    
    /**
     * 网关服务
     * 
     * @param userId
     * @param apiName
     * @param parJSON
     * @return
     */
    public static Object getApiContent(String userId, String apiName, JSONObject parJSON) {
        userId = StrUtil.isNotEmpty(userId) ? userId : "admin";
        String url = GETEWAY_URL + SERVER_NAME + apiName;
        url = url.replace("USER_NAME", userId);
        Map<String, String> headerMap = Maps.newHashMap();
        headerMap.put("Authorization", getNewToken(userId));
        String result =  HttpUtil.sendPostHeaderMap(url, parJSON.toString(), headerMap);
        Object object = (Object)result;
        return object;
    }
//
//    /**
//     * 业务服务器
//     * 
//     * @param userId
//     * @param apiName
//     * @param parJSON
//     * @return
//     */
//    public static Object getApiContent(String userId, String apiName, JSONObject parJSON) {
////        userId = StrUtil.isNotEmpty(userId) ? userId : "admin";
//        String url = BIZ_URL + apiName;
////        url = url.replace("USER_NAME", userId);
//        Map<String, String> headerMap = Maps.newHashMap();
////        headerMap.put("Authorization", getNewToken(userId));
//        String result =  HttpUtil.sendPostHeaderMap(url, parJSON.toString(), headerMap);
//        Object object = (Object)result;
//        return object;
//    }
    

}
