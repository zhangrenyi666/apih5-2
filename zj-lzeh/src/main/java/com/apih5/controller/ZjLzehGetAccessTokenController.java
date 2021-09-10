package com.apih5.controller;


import cn.hutool.json.JSONObject;
import com.apih5.framework.annotation.RequireToken;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjLzehInterfaceLink;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import okhttp3.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.*;
import java.util.ArrayList;


@RestController
public class ZjLzehGetAccessTokenController {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @ApiOperation(value="获取AccessToken", notes="获取AccessToken")
    @ApiImplicitParam(name = "ZjLzehAccessToken", value = "AccessTokenentity", dataType = "ZjLzehAccessToken")
    @RequireToken
    @PostMapping("/getZjLzehAccessToken")
    public ResponseEntity getZjProZcGetAccessToken() throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(mediaType, "appKey=b8eef51216fa46eab206dabc5aad1398&appSecret=4f2303908d35a7b35d85fec5cbd015bf");
        Request request = new Request.Builder()
                .url("https://open.ys7.com/api/lapp/token/get")
                .method("POST", body)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .build();
        Response response = client.newCall(request).execute();
        JSONObject jsonObject = new JSONObject(response.body().string());
        JSONObject data = new JSONObject(jsonObject.get("data").toString());
        String accessToken = data.get("accessToken").toString().replaceAll("\"","");
        ArrayList<ZjLzehInterfaceLink> zjProZcInterfaceLinks = new ArrayList<ZjLzehInterfaceLink>();
        String[] strs = new String[] {"1", "2", "5", "6"};
        
        for (int i = 0; i < strs.length ; i++) {
            ZjLzehInterfaceLink zjProZcInterfaceLink = new ZjLzehInterfaceLink();
            zjProZcInterfaceLink.setInterfaceLink("https://open.ys7.com/ezopen/h5/iframe?url=ezopen://open.ys7.com/F45398982/"+strs[i]+".live&autoplay=1&accessToken="+accessToken);
            zjProZcInterfaceLinks.add(zjProZcInterfaceLink);
        }
        return repEntity.ok(zjProZcInterfaceLinks);
    }




}
