package com.apih5.utils;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;

public class ZjLzehUtil {
    public static String OTHER_URL = "http://114.115.200.165:9052/";
    public static String PROJECT_ID = "fbfe1d928e2544d6b6e9bcb72341fa36";
    public static String BUSI_Id = "69d5dfcde9694c9690b6e19b5462967b";
    public static String BUS_DATE = "2020-10-27";
    
    public JSONObject getLzehOtherSessionId() {
        String mobile = "18612220503";
        JSONObject jsonObject = new JSONObject();
        String url = OTHER_URL + "a/loginForMobile";
        try {
            ZjLzehDesUtil desUtil = new ZjLzehDesUtil("qfkj!@#$%");
            url += "?encryptMobileStr=" + desUtil.encrypt(mobile);
            String reuslt = HttpUtil.get(url);
            
            JSONObject obj = new JSONObject(reuslt);
            
            if (obj.containsKey("rtnFlag") && StrUtil.equals("0", obj.getStr("rtnFlag"))) {
                JSONObject returnObj = new JSONObject(obj.getStr("rtnObj"));
                String sessionId = returnObj.getStr("sessionId");
                jsonObject.set("success", true);
                jsonObject.set("data", sessionId);
                return jsonObject;
            } else {
                jsonObject.set("success", false);
                jsonObject.set("data", obj.getStr("rtnMessage"));
                return jsonObject;
            }
        } catch (Exception e) {
            jsonObject.set("success", false);
            jsonObject.set("data", "加密异常！");
            return jsonObject;
        }
    }
}
