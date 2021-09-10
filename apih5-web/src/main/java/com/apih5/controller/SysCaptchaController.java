package com.apih5.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apih5.framework.entity.ResponseEntity;
import com.apih5.service.SysCaptchaService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@RestController
public class SysCaptchaController {

    @Autowired(required = true)
    private SysCaptchaService sysCaptchaService;
    
    @ApiOperation(value="查询详情图片验证码", notes="查询详情图片验证码")
    @ApiImplicitParam(name = "sysCaptcha", value = "图片验证码entity", dataType = "SysCaptcha")
    @PostMapping("/getCaptchaCode")
    public ResponseEntity getCaptchaCode() {
        return sysCaptchaService.getCaptchaCode();
    }

}
