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
import com.apih5.mybatis.pojo.ZxSaProjectSet;
import com.apih5.service.ZxSaProjectSetService;

@RestController
public class ZxSaProjectSetController {

    @Autowired(required = true)
    private ZxSaProjectSetService zxSaProjectSetService;

    @ApiOperation(value="查询结算支付信息", notes="查询结算支付信息")
    @ApiImplicitParam(name = "zxSaProjectSet", value = "结算支付信息entity", dataType = "ZxSaProjectSet")
    @RequireToken
    @PostMapping("/getZxSaProjectSetList")
    public ResponseEntity getZxSaProjectSetList(@RequestBody(required = false) ZxSaProjectSet zxSaProjectSet) {
        return zxSaProjectSetService.getZxSaProjectSetListByCondition(zxSaProjectSet);
    }

    @ApiOperation(value="查询详情结算支付信息", notes="查询详情结算支付信息")
    @ApiImplicitParam(name = "zxSaProjectSet", value = "结算支付信息entity", dataType = "ZxSaProjectSet")
    @RequireToken
    @PostMapping("/getZxSaProjectSetDetails")
    public ResponseEntity getZxSaProjectSetDetails(@RequestBody(required = false) ZxSaProjectSet zxSaProjectSet) {
        return zxSaProjectSetService.getZxSaProjectSetDetails(zxSaProjectSet);
    }

    @ApiOperation(value="新增结算支付信息", notes="新增结算支付信息")
    @ApiImplicitParam(name = "zxSaProjectSet", value = "结算支付信息entity", dataType = "ZxSaProjectSet")
    @RequireToken
    @PostMapping("/addZxSaProjectSet")
    public ResponseEntity addZxSaProjectSet(@RequestBody(required = false) ZxSaProjectSet zxSaProjectSet) {
        return zxSaProjectSetService.saveZxSaProjectSet(zxSaProjectSet);
    }

    @ApiOperation(value="更新结算支付信息", notes="更新结算支付信息")
    @ApiImplicitParam(name = "zxSaProjectSet", value = "结算支付信息entity", dataType = "ZxSaProjectSet")
    @RequireToken
    @PostMapping("/updateZxSaProjectSet")
    public ResponseEntity updateZxSaProjectSet(@RequestBody(required = false) ZxSaProjectSet zxSaProjectSet) {
        return zxSaProjectSetService.updateZxSaProjectSet(zxSaProjectSet);
    }

    @ApiOperation(value="删除结算支付信息", notes="删除结算支付信息")
    @ApiImplicitParam(name = "zxSaProjectSetList", value = "结算支付信息List", dataType = "List<ZxSaProjectSet>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxSaProjectSet")
    public ResponseEntity batchDeleteUpdateZxSaProjectSet(@RequestBody(required = false) List<ZxSaProjectSet> zxSaProjectSetList) {
        return zxSaProjectSetService.batchDeleteUpdateZxSaProjectSet(zxSaProjectSetList);
    }

}
