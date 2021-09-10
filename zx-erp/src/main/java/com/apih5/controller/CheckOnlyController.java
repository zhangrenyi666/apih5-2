package com.apih5.controller;

import com.apih5.framework.annotation.RequireToken;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.CheckOnly;
import com.apih5.mybatis.pojo.ZxBuProjectTypeCheck;
import com.apih5.service.CheckOnlyService;
import com.apih5.service.ZxBuProjectTypeCheckService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CheckOnlyController {

    @Autowired(required = true)
    private CheckOnlyService checkOnlyService;

    @ApiOperation(value="唯一性检验", notes="唯一性检验")
    @ApiImplicitParam(name = "checkOnlyByObj", value = "唯一性检验", dataType = "checkOnly")
    //@RequireToken
    @PostMapping("/checkOnlyByObj")
    public ResponseEntity checkOnlyByObj(@RequestBody(required = false) CheckOnly checkOnly) {
        return checkOnlyService.checkOnlyByObj(checkOnly);
    }

}
