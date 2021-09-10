package com.apih5.controller;

import com.apih5.annotation.CheckOnlyPoint;
import com.apih5.framework.annotation.RequireToken;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxSfPlanTarget;
import com.apih5.service.UreportService;
import com.apih5.service.ZxSfPlanTargetService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class UreportController {

    @Autowired(required = true)
    private UreportService ureportService;

    @ApiOperation(value="报表通用接口返回列表", notes="报表通用接口返回列表")
    @ApiImplicitParam(name = "ureportCond", value = "条件Map", dataType = "Map")
    @PostMapping("/ureportList")
    public List ureportList(@RequestBody(required = false) Map paramMap) {
        return ureportService.ureportList(paramMap);
    }

    @ApiOperation(value="报表通用接口返回对象", notes="报表通用接口返回对象")
    @ApiImplicitParam(name = "ureportCond", value = "条件Map", dataType = "Map")
    @PostMapping("/ureportObject")
    public Object ureportObject(@RequestBody(required = false) Map paramMap) {
        return ureportService.ureportObject(paramMap);
    }
}
