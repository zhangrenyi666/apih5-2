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
import com.apih5.mybatis.pojo.ZxSkMonthPur;
import com.apih5.service.ZxSkMonthPurService;

@RestController
public class ZxSkMonthPurController {

    @Autowired(required = true)
    private ZxSkMonthPurService zxSkMonthPurService;

    @ApiOperation(value="查询月采购计划", notes="查询月采购计划")
    @ApiImplicitParam(name = "zxSkMonthPur", value = "月采购计划entity", dataType = "ZxSkMonthPur")
    @RequireToken
    @PostMapping("/getZxSkMonthPurList")
    public ResponseEntity getZxSkMonthPurList(@RequestBody(required = false) ZxSkMonthPur zxSkMonthPur) {
        return zxSkMonthPurService.getZxSkMonthPurListByCondition(zxSkMonthPur);
    }

    @ApiOperation(value="查询详情月采购计划", notes="查询详情月采购计划")
    @ApiImplicitParam(name = "zxSkMonthPur", value = "月采购计划entity", dataType = "ZxSkMonthPur")
    @RequireToken
    @PostMapping("/getZxSkMonthPurDetails")
    public ResponseEntity getZxSkMonthPurDetails(@RequestBody(required = false) ZxSkMonthPur zxSkMonthPur) {
        return zxSkMonthPurService.getZxSkMonthPurDetails(zxSkMonthPur);
    }

    @ApiOperation(value="新增月采购计划", notes="新增月采购计划")
    @ApiImplicitParam(name = "zxSkMonthPur", value = "月采购计划entity", dataType = "ZxSkMonthPur")
    @RequireToken
    @PostMapping("/addZxSkMonthPur")
    public ResponseEntity addZxSkMonthPur(@RequestBody(required = false) ZxSkMonthPur zxSkMonthPur) {
        return zxSkMonthPurService.saveZxSkMonthPur(zxSkMonthPur);
    }

    @ApiOperation(value="更新月采购计划", notes="更新月采购计划")
    @ApiImplicitParam(name = "zxSkMonthPur", value = "月采购计划entity", dataType = "ZxSkMonthPur")
    @RequireToken
    @PostMapping("/updateZxSkMonthPur")
    public ResponseEntity updateZxSkMonthPur(@RequestBody(required = false) ZxSkMonthPur zxSkMonthPur) {
        return zxSkMonthPurService.updateZxSkMonthPur(zxSkMonthPur);
    }

    @ApiOperation(value="删除月采购计划", notes="删除月采购计划")
    @ApiImplicitParam(name = "zxSkMonthPurList", value = "月采购计划List", dataType = "List<ZxSkMonthPur>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxSkMonthPur")
    public ResponseEntity batchDeleteUpdateZxSkMonthPur(@RequestBody(required = false) List<ZxSkMonthPur> zxSkMonthPurList) {
        return zxSkMonthPurService.batchDeleteUpdateZxSkMonthPur(zxSkMonthPurList);
    }




}
