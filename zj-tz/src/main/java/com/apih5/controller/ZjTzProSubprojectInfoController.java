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
import com.apih5.mybatis.pojo.ZjTzProSubprojectInfo;
import com.apih5.service.ZjTzProSubprojectInfoService;

@RestController
public class ZjTzProSubprojectInfoController {

    @Autowired(required = true)
    private ZjTzProSubprojectInfoService zjTzProSubprojectInfoService;

    @ApiOperation(value="查询子项目信息", notes="查询子项目信息")
    @ApiImplicitParam(name = "zjTzProSubprojectInfo", value = "子项目信息entity", dataType = "ZjTzProSubprojectInfo")
    @RequireToken
    @PostMapping("/getZjTzProSubprojectInfoList")
    public ResponseEntity getZjTzProSubprojectInfoList(@RequestBody(required = false) ZjTzProSubprojectInfo zjTzProSubprojectInfo) {
        return zjTzProSubprojectInfoService.getZjTzProSubprojectInfoListByCondition(zjTzProSubprojectInfo);
    }

    @ApiOperation(value="查询详情子项目信息", notes="查询详情子项目信息")
    @ApiImplicitParam(name = "zjTzProSubprojectInfo", value = "子项目信息entity", dataType = "ZjTzProSubprojectInfo")
    @RequireToken
    @PostMapping("/getZjTzProSubprojectInfoDetails")
    public ResponseEntity getZjTzProSubprojectInfoDetails(@RequestBody(required = false) ZjTzProSubprojectInfo zjTzProSubprojectInfo) {
        return zjTzProSubprojectInfoService.getZjTzProSubprojectInfoDetails(zjTzProSubprojectInfo);
    }

    @ApiOperation(value="新增子项目信息", notes="新增子项目信息")
    @ApiImplicitParam(name = "zjTzProSubprojectInfo", value = "子项目信息entity", dataType = "ZjTzProSubprojectInfo")
    @RequireToken
    @PostMapping("/addZjTzProSubprojectInfo")
    public ResponseEntity addZjTzProSubprojectInfo(@RequestBody(required = false) ZjTzProSubprojectInfo zjTzProSubprojectInfo) {
        return zjTzProSubprojectInfoService.saveZjTzProSubprojectInfo(zjTzProSubprojectInfo);
    }

    @ApiOperation(value="更新子项目信息", notes="更新子项目信息")
    @ApiImplicitParam(name = "zjTzProSubprojectInfo", value = "子项目信息entity", dataType = "ZjTzProSubprojectInfo")
    @RequireToken
    @PostMapping("/updateZjTzProSubprojectInfo")
    public ResponseEntity updateZjTzProSubprojectInfo(@RequestBody(required = false) ZjTzProSubprojectInfo zjTzProSubprojectInfo) {
        return zjTzProSubprojectInfoService.updateZjTzProSubprojectInfo(zjTzProSubprojectInfo);
    }

    @ApiOperation(value="删除子项目信息", notes="删除子项目信息")
    @ApiImplicitParam(name = "zjTzProSubprojectInfoList", value = "子项目信息List", dataType = "List<ZjTzProSubprojectInfo>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjTzProSubprojectInfo")
    public ResponseEntity batchDeleteUpdateZjTzProSubprojectInfo(@RequestBody(required = false) List<ZjTzProSubprojectInfo> zjTzProSubprojectInfoList) {
        return zjTzProSubprojectInfoService.batchDeleteUpdateZjTzProSubprojectInfo(zjTzProSubprojectInfoList);
    }

}
