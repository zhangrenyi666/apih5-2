package com.apih5.service.impl;

import java.awt.Color;
import java.awt.Font;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.cache.RedisCacheService;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.service.SysCaptchaService;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;

@Service("sysCaptchaService")
public class SysCaptchaServiceImpl implements SysCaptchaService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RedisCacheService redisCacheService;
    
	@Override
	public ResponseEntity getCaptchaCode() {
		//定义图形验证码的长、宽、验证码字符数、干扰元素个数
		LineCaptcha captcha = CaptchaUtil.createLineCaptcha(400, 100, 4, 20);
//		captcha.setBackground(new Color(100, 149, 237));
		Font font = new Font("黑体", Font.BOLD, 100);
        captcha.setFont(font);
        JSONObject jsonObject = new JSONObject();
//        jsonObject.set("captchaId", captcha.getCode());
        jsonObject.set("imgData", captcha.getImageBase64());
        redisCacheService.putCaptchaCodeRedis(captcha.getCode(), captcha.getImageBase64());
		return repEntity.ok(jsonObject);
	}
	
	@Override
	public ResponseEntity checkSysCaptchaCode(String captchaId, String captchaCode) {
	    captchaId = captchaCode;
	    if(!StrUtil.equals(captchaId, captchaCode)) {
	        redisCacheService.removeCaptchaCodeRedis(captchaCode);
	        return repEntity.layerMessage("no", "验证码错误，请重新输入！");
	    }
		String str = redisCacheService.getCaptchaCodeRedis(captchaCode);
		redisCacheService.removeCaptchaCodeRedis(captchaCode);
        if (StrUtil.isEmpty(str)) {
            return repEntity.layerMessage("no", "验证码错误，请重新输入！");
        }
		return repEntity.ok(true);
	}
}
