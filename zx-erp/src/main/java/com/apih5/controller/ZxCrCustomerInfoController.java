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
import com.apih5.mybatis.pojo.ZxCrCustomerInfo;
import com.apih5.service.ZxCrCustomerInfoService;

@RestController
public class ZxCrCustomerInfoController {

    @Autowired(required = true)
    private ZxCrCustomerInfoService zxCrCustomerInfoService;

    @ApiOperation(value="查询协作单位基础信息登记", notes="查询协作单位基础信息登记")
    @ApiImplicitParam(name = "zxCrCustomerInfo", value = "协作单位基础信息登记entity", dataType = "ZxCrCustomerInfo")
    @RequireToken
    @PostMapping("/getZxCrCustomerInfoList")
    public ResponseEntity getZxCrCustomerInfoList(@RequestBody(required = false) ZxCrCustomerInfo zxCrCustomerInfo) {
        return zxCrCustomerInfoService.getZxCrCustomerInfoListByCondition(zxCrCustomerInfo);
    }

    @ApiOperation(value="查询详情协作单位基础信息登记", notes="查询详情协作单位基础信息登记")
    @ApiImplicitParam(name = "zxCrCustomerInfo", value = "协作单位基础信息登记entity", dataType = "ZxCrCustomerInfo")
    @RequireToken
    @PostMapping("/getZxCrCustomerInfoDetail")
    public ResponseEntity getZxCrCustomerInfoDetail(@RequestBody(required = false) ZxCrCustomerInfo zxCrCustomerInfo) {
        return zxCrCustomerInfoService.getZxCrCustomerInfoDetail(zxCrCustomerInfo);
    }

    @ApiOperation(value="新增协作单位基础信息登记", notes="新增协作单位基础信息登记")
    @ApiImplicitParam(name = "zxCrCustomerInfo", value = "协作单位基础信息登记entity", dataType = "ZxCrCustomerInfo")
    @RequireToken
    @PostMapping("/addZxCrCustomerInfo")
    public ResponseEntity addZxCrCustomerInfo(@RequestBody(required = false) ZxCrCustomerInfo zxCrCustomerInfo) {
        return zxCrCustomerInfoService.saveZxCrCustomerInfo(zxCrCustomerInfo);
    }

    @ApiOperation(value="更新协作单位基础信息登记", notes="更新协作单位基础信息登记")
    @ApiImplicitParam(name = "zxCrCustomerInfo", value = "协作单位基础信息登记entity", dataType = "ZxCrCustomerInfo")
    @RequireToken
    @PostMapping("/updateZxCrCustomerInfo")
    public ResponseEntity updateZxCrCustomerInfo(@RequestBody(required = false) ZxCrCustomerInfo zxCrCustomerInfo) {
        return zxCrCustomerInfoService.updateZxCrCustomerInfo(zxCrCustomerInfo);
    }

    @ApiOperation(value="删除协作单位基础信息登记", notes="删除协作单位基础信息登记")
    @ApiImplicitParam(name = "zxCrCustomerInfoList", value = "协作单位基础信息登记List", dataType = "List<ZxCrCustomerInfo>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxCrCustomerInfo")
    public ResponseEntity batchDeleteUpdateZxCrCustomerInfo(@RequestBody(required = false) List<ZxCrCustomerInfo> zxCrCustomerInfoList) {
        return zxCrCustomerInfoService.batchDeleteUpdateZxCrCustomerInfo(zxCrCustomerInfoList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    
    @ApiOperation(value="查询协作单位基础信息登记", notes="查询协作单位基础信息登记")
    @ApiImplicitParam(name = "zxCrCustomerInfo", value = "协作单位基础信息登记entity", dataType = "ZxCrCustomerInfo")
    @RequireToken
    @PostMapping("/getZxCrCustomerInfoListOne")
    public ResponseEntity getZxCrCustomerInfoListOne(@RequestBody(required = false) ZxCrCustomerInfo zxCrCustomerInfo) {
        return zxCrCustomerInfoService.getZxCrCustomerInfoListOne(zxCrCustomerInfo);
    }
}
