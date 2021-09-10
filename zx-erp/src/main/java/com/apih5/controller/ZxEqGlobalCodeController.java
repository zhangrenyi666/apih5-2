package com.apih5.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import com.apih5.framework.annotation.RequireToken;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxEqGlobalCode;
import com.apih5.service.ZxEqGlobalCodeService;

@RestController
public class ZxEqGlobalCodeController {

    @Autowired(required = true)
    private ZxEqGlobalCodeService zxEqGlobalCodeService;

    @ApiOperation(value="查询设备基础数据", notes="查询设备基础数据")
    @ApiImplicitParam(name = "zxEqGlobalCode", value = "设备基础数据entity", dataType = "ZxEqGlobalCode")
    @RequireToken
    @PostMapping("/getZxEqGlobalCodeList")
    public ResponseEntity getZxEqGlobalCodeList(@RequestBody(required = false) ZxEqGlobalCode zxEqGlobalCode) {
        return zxEqGlobalCodeService.getZxEqGlobalCodeListByCondition(zxEqGlobalCode);
    }

    @ApiOperation(value="查询详情设备基础数据", notes="查询详情设备基础数据")
    @ApiImplicitParam(name = "zxEqGlobalCode", value = "设备基础数据entity", dataType = "ZxEqGlobalCode")
    @RequireToken
    @PostMapping("/getZxEqGlobalCodeDetails")
    public ResponseEntity getZxEqGlobalCodeDetails(@RequestBody(required = false) ZxEqGlobalCode zxEqGlobalCode) {
        return zxEqGlobalCodeService.getZxEqGlobalCodeDetails(zxEqGlobalCode);
    }

    @ApiOperation(value="新增设备基础数据", notes="新增设备基础数据")
    @ApiImplicitParam(name = "zxEqGlobalCode", value = "设备基础数据entity", dataType = "ZxEqGlobalCode")
    @RequireToken
    @PostMapping("/addZxEqGlobalCode")
    public ResponseEntity addZxEqGlobalCode(@RequestBody(required = false) ZxEqGlobalCode zxEqGlobalCode) {
        return zxEqGlobalCodeService.saveZxEqGlobalCode(zxEqGlobalCode);
    }

    @ApiOperation(value="更新设备基础数据", notes="更新设备基础数据")
    @ApiImplicitParam(name = "zxEqGlobalCode", value = "设备基础数据entity", dataType = "ZxEqGlobalCode")
    @RequireToken
    @PostMapping("/updateZxEqGlobalCode")
    public ResponseEntity updateZxEqGlobalCode(@RequestBody(required = false) ZxEqGlobalCode zxEqGlobalCode) {
        return zxEqGlobalCodeService.updateZxEqGlobalCode(zxEqGlobalCode);
    }

    @ApiOperation(value="删除设备基础数据", notes="删除设备基础数据")
    @ApiImplicitParam(name = "zxEqGlobalCodeList", value = "设备基础数据List", dataType = "List<ZxEqGlobalCode>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxEqGlobalCode")
    public ResponseEntity batchDeleteUpdateZxEqGlobalCode(@RequestBody(required = false) List<ZxEqGlobalCode> zxEqGlobalCodeList) {
        return zxEqGlobalCodeService.batchDeleteUpdateZxEqGlobalCode(zxEqGlobalCodeList);
    }

}
