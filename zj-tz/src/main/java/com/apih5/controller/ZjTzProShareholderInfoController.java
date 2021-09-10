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
import com.apih5.mybatis.pojo.ZjTzProShareholderInfo;
import com.apih5.service.ZjTzProShareholderInfoService;

@RestController
public class ZjTzProShareholderInfoController {

    @Autowired(required = true)
    private ZjTzProShareholderInfoService zjTzProShareholderInfoService;

    @ApiOperation(value="查询股东信息", notes="查询股东信息")
    @ApiImplicitParam(name = "zjTzProShareholderInfo", value = "股东信息entity", dataType = "ZjTzProShareholderInfo")
    @RequireToken
    @PostMapping("/getZjTzProShareholderInfoList")
    public ResponseEntity getZjTzProShareholderInfoList(@RequestBody(required = false) ZjTzProShareholderInfo zjTzProShareholderInfo) {
        return zjTzProShareholderInfoService.getZjTzProShareholderInfoListByCondition(zjTzProShareholderInfo);
    }

    @ApiOperation(value="查询详情股东信息", notes="查询详情股东信息")
    @ApiImplicitParam(name = "zjTzProShareholderInfo", value = "股东信息entity", dataType = "ZjTzProShareholderInfo")
    @RequireToken
    @PostMapping("/getZjTzProShareholderInfoDetails")
    public ResponseEntity getZjTzProShareholderInfoDetails(@RequestBody(required = false) ZjTzProShareholderInfo zjTzProShareholderInfo) {
        return zjTzProShareholderInfoService.getZjTzProShareholderInfoDetails(zjTzProShareholderInfo);
    }

    @ApiOperation(value="新增股东信息", notes="新增股东信息")
    @ApiImplicitParam(name = "zjTzProShareholderInfo", value = "股东信息entity", dataType = "ZjTzProShareholderInfo")
    @RequireToken
    @PostMapping("/addZjTzProShareholderInfo")
    public ResponseEntity addZjTzProShareholderInfo(@RequestBody(required = false) ZjTzProShareholderInfo zjTzProShareholderInfo) {
        return zjTzProShareholderInfoService.saveZjTzProShareholderInfo(zjTzProShareholderInfo);
    }

    @ApiOperation(value="更新股东信息", notes="更新股东信息")
    @ApiImplicitParam(name = "zjTzProShareholderInfo", value = "股东信息entity", dataType = "ZjTzProShareholderInfo")
    @RequireToken
    @PostMapping("/updateZjTzProShareholderInfo")
    public ResponseEntity updateZjTzProShareholderInfo(@RequestBody(required = false) ZjTzProShareholderInfo zjTzProShareholderInfo) {
        return zjTzProShareholderInfoService.updateZjTzProShareholderInfo(zjTzProShareholderInfo);
    }

    @ApiOperation(value="删除股东信息", notes="删除股东信息")
    @ApiImplicitParam(name = "zjTzProShareholderInfoList", value = "股东信息List", dataType = "List<ZjTzProShareholderInfo>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjTzProShareholderInfo")
    public ResponseEntity batchDeleteUpdateZjTzProShareholderInfo(@RequestBody(required = false) List<ZjTzProShareholderInfo> zjTzProShareholderInfoList) {
        return zjTzProShareholderInfoService.batchDeleteUpdateZjTzProShareholderInfo(zjTzProShareholderInfoList);
    }

}
