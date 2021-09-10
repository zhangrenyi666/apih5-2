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
import com.apih5.mybatis.pojo.ZxCtGlobalCode;
import com.apih5.service.ZxCtGlobalCodeService;

@RestController
public class ZxCtGlobalCodeController {

    @Autowired(required = true)
    private ZxCtGlobalCodeService zxCtGlobalCodeService;

    @ApiOperation(value="查询合同属性", notes="查询合同属性")
    @ApiImplicitParam(name = "zxCtGlobalCode", value = "合同属性entity", dataType = "ZxCtGlobalCode")
    @RequireToken
    @PostMapping("/getZxCtGlobalCodeList")
    public ResponseEntity getZxCtGlobalCodeList(@RequestBody(required = false) ZxCtGlobalCode zxCtGlobalCode) {
        return zxCtGlobalCodeService.getZxCtGlobalCodeListByCondition(zxCtGlobalCode);
    }

    @ApiOperation(value="查询详情合同属性", notes="查询详情合同属性")
    @ApiImplicitParam(name = "zxCtGlobalCode", value = "合同属性entity", dataType = "ZxCtGlobalCode")
    @RequireToken
    @PostMapping("/getZxCtGlobalCodeDetails")
    public ResponseEntity getZxCtGlobalCodeDetails(@RequestBody(required = false) ZxCtGlobalCode zxCtGlobalCode) {
        return zxCtGlobalCodeService.getZxCtGlobalCodeDetails(zxCtGlobalCode);
    }

    @ApiOperation(value="新增合同属性", notes="新增合同属性")
    @ApiImplicitParam(name = "zxCtGlobalCode", value = "合同属性entity", dataType = "ZxCtGlobalCode")
    @RequireToken
    @PostMapping("/addZxCtGlobalCode")
    public ResponseEntity addZxCtGlobalCode(@RequestBody(required = false) ZxCtGlobalCode zxCtGlobalCode) {
        return zxCtGlobalCodeService.saveZxCtGlobalCode(zxCtGlobalCode);
    }

    @ApiOperation(value="更新合同属性", notes="更新合同属性")
    @ApiImplicitParam(name = "zxCtGlobalCode", value = "合同属性entity", dataType = "ZxCtGlobalCode")
    @RequireToken
    @PostMapping("/updateZxCtGlobalCode")
    public ResponseEntity updateZxCtGlobalCode(@RequestBody(required = false) ZxCtGlobalCode zxCtGlobalCode) {
        return zxCtGlobalCodeService.updateZxCtGlobalCode(zxCtGlobalCode);
    }

    @ApiOperation(value="删除合同属性", notes="删除合同属性")
    @ApiImplicitParam(name = "zxCtGlobalCodeList", value = "合同属性List", dataType = "List<ZxCtGlobalCode>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxCtGlobalCode")
    public ResponseEntity batchDeleteUpdateZxCtGlobalCode(@RequestBody(required = false) List<ZxCtGlobalCode> zxCtGlobalCodeList) {
        return zxCtGlobalCodeService.batchDeleteUpdateZxCtGlobalCode(zxCtGlobalCodeList);
    }

}
