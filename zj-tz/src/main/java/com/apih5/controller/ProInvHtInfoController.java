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
import com.apih5.mybatis.pojo.ProInvHtInfo;
import com.apih5.service.ProInvHtInfoService;

@RestController
public class ProInvHtInfoController {

    @Autowired(required = true)
    private ProInvHtInfoService proInvHtInfoService;

    @ApiOperation(value="查询项目管理", notes="查询项目管理")
    @ApiImplicitParam(name = "proInvHtInfo", value = "项目管理entity", dataType = "ProInvHtInfo")
    @RequireToken
    @PostMapping("/getProInvHtInfoList")
    public ResponseEntity getProInvHtInfoList(@RequestBody(required = false) ProInvHtInfo proInvHtInfo) {
        return proInvHtInfoService.getProInvHtInfoListByCondition(proInvHtInfo);
    }

    @ApiOperation(value="查询详情项目管理", notes="查询详情项目管理")
    @ApiImplicitParam(name = "proInvHtInfo", value = "项目管理entity", dataType = "ProInvHtInfo")
    @RequireToken
    @PostMapping("/getProInvHtInfoDetails")
    public ResponseEntity getProInvHtInfoDetails(@RequestBody(required = false) ProInvHtInfo proInvHtInfo) {
        return proInvHtInfoService.getProInvHtInfoDetails(proInvHtInfo);
    }

    @ApiOperation(value="新增项目管理", notes="新增项目管理")
    @ApiImplicitParam(name = "proInvHtInfo", value = "项目管理entity", dataType = "ProInvHtInfo")
    @RequireToken
    @PostMapping("/addProInvHtInfo")
    public ResponseEntity addProInvHtInfo(@RequestBody(required = false) ProInvHtInfo proInvHtInfo) {
        return proInvHtInfoService.saveProInvHtInfo(proInvHtInfo);
    }

    @ApiOperation(value="更新项目管理", notes="更新项目管理")
    @ApiImplicitParam(name = "proInvHtInfo", value = "项目管理entity", dataType = "ProInvHtInfo")
    @RequireToken
    @PostMapping("/updateProInvHtInfo")
    public ResponseEntity updateProInvHtInfo(@RequestBody(required = false) ProInvHtInfo proInvHtInfo) {
        return proInvHtInfoService.updateProInvHtInfo(proInvHtInfo);
    }

    @ApiOperation(value="删除项目管理", notes="删除项目管理")
    @ApiImplicitParam(name = "proInvHtInfoList", value = "项目管理List", dataType = "List<ProInvHtInfo>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateProInvHtInfo")
    public ResponseEntity batchDeleteUpdateProInvHtInfo(@RequestBody(required = false) List<ProInvHtInfo> proInvHtInfoList) {
        return proInvHtInfoService.batchDeleteUpdateProInvHtInfo(proInvHtInfoList);
    }

}
