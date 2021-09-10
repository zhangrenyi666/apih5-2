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
import com.apih5.mybatis.pojo.ZxEqIecrCustomerNew;
import com.apih5.service.ZxEqIecrCustomerNewService;

@RestController
public class ZxEqIecrCustomerNewController {

    @Autowired(required = true)
    private ZxEqIecrCustomerNewService zxEqIecrCustomerNewService;

    @ApiOperation(value="查询供应商", notes="查询供应商")
    @ApiImplicitParam(name = "zxEqIecrCustomerNew", value = "供应商entity", dataType = "ZxEqIecrCustomerNew")
    @RequireToken
    @PostMapping("/getZxEqIecrCustomerNewList")
    public ResponseEntity getZxEqIecrCustomerNewList(@RequestBody(required = false) ZxEqIecrCustomerNew zxEqIecrCustomerNew) {
        return zxEqIecrCustomerNewService.getZxEqIecrCustomerNewListByCondition(zxEqIecrCustomerNew);
    }

    @ApiOperation(value="查询详情供应商", notes="查询详情供应商")
    @ApiImplicitParam(name = "zxEqIecrCustomerNew", value = "供应商entity", dataType = "ZxEqIecrCustomerNew")
    @RequireToken
    @PostMapping("/getZxEqIecrCustomerNewDetails")
    public ResponseEntity getZxEqIecrCustomerNewDetails(@RequestBody(required = false) ZxEqIecrCustomerNew zxEqIecrCustomerNew) {
        return zxEqIecrCustomerNewService.getZxEqIecrCustomerNewDetails(zxEqIecrCustomerNew);
    }

    @ApiOperation(value="新增供应商", notes="新增供应商")
    @ApiImplicitParam(name = "zxEqIecrCustomerNew", value = "供应商entity", dataType = "ZxEqIecrCustomerNew")
    @RequireToken
    @PostMapping("/addZxEqIecrCustomerNew")
    public ResponseEntity addZxEqIecrCustomerNew(@RequestBody(required = false) ZxEqIecrCustomerNew zxEqIecrCustomerNew) {
        return zxEqIecrCustomerNewService.saveZxEqIecrCustomerNew(zxEqIecrCustomerNew);
    }

    @ApiOperation(value="更新供应商", notes="更新供应商")
    @ApiImplicitParam(name = "zxEqIecrCustomerNew", value = "供应商entity", dataType = "ZxEqIecrCustomerNew")
    @RequireToken
    @PostMapping("/updateZxEqIecrCustomerNew")
    public ResponseEntity updateZxEqIecrCustomerNew(@RequestBody(required = false) ZxEqIecrCustomerNew zxEqIecrCustomerNew) {
        return zxEqIecrCustomerNewService.updateZxEqIecrCustomerNew(zxEqIecrCustomerNew);
    }

    @ApiOperation(value="删除供应商", notes="删除供应商")
    @ApiImplicitParam(name = "zxEqIecrCustomerNewList", value = "供应商List", dataType = "List<ZxEqIecrCustomerNew>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxEqIecrCustomerNew")
    public ResponseEntity batchDeleteUpdateZxEqIecrCustomerNew(@RequestBody(required = false) List<ZxEqIecrCustomerNew> zxEqIecrCustomerNewList) {
        return zxEqIecrCustomerNewService.batchDeleteUpdateZxEqIecrCustomerNew(zxEqIecrCustomerNewList);
    }

}
