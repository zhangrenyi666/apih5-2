package com.apih5.controller;

import cn.hutool.json.JSONObject;
import com.apih5.framework.annotation.RequireToken;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjLzehVideo;
import com.apih5.mybatis.pojo.ZjLzehWeather;
import com.apih5.service.ZjLzehVideoService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@RestController
public class ZjLzehWeatherController {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @ApiOperation(value = "获取天气", notes = "获取天气")
    @ApiImplicitParam(name = "ZjLzehWeather", value = "ZjLzehWeatherEntity", dataType = "ZjLzehWeather")
    @RequireToken
    @PostMapping("/getZjLzehWeather")
    public ResponseEntity getZjProZcGetAccessToken() throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        okhttp3.RequestBody body = okhttp3.RequestBody.create(mediaType, "");
        Request request = new Request.Builder()
                .url("https://www.tianqiapi.com/api?version=v6&appid=81191113&appsecret=oY6DOs3h&cityid=101271001")
                .method("POST", body)
                .build();
        Response response = client.newCall(request).execute();
        JSONObject jsonObject = new JSONObject(response.body().string());
        System.out.println(jsonObject);
        ZjLzehWeather zjLzehWeather = new ZjLzehWeather();
        //气温
        zjLzehWeather.setAirTemperature(new BigDecimal(jsonObject.get("tem").toString()));
        //湿度
        zjLzehWeather.setHumidity(new BigDecimal(jsonObject.get("humidity").toString().replaceAll("%", "")));
        //风向
        zjLzehWeather.setWindDirection(jsonObject.get("win").toString());
        //pm2.5
        zjLzehWeather.setPm25(new BigDecimal(jsonObject.get("air_pm25").toString()));
        return repEntity.ok(zjLzehWeather);
    }


}
