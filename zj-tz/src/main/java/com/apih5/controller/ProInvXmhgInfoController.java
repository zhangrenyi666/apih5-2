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
import com.apih5.mybatis.pojo.ProInvXmhgInfo;
import com.apih5.service.ProInvXmhgInfoService;

@RestController
public class ProInvXmhgInfoController {

    @Autowired(required = true)
    private ProInvXmhgInfoService proInvXmhgInfoService;

    @ApiOperation(value="查询项目管理", notes="查询项目管理")
    @ApiImplicitParam(name = "proInvXmhgInfo", value = "项目管理entity", dataType = "ProInvXmhgInfo")
    @RequireToken
    @PostMapping("/getProInvXmhgInfoList")
    public ResponseEntity getProInvXmhgInfoList(@RequestBody(required = false) ProInvXmhgInfo proInvXmhgInfo) {
        return proInvXmhgInfoService.getProInvXmhgInfoListByCondition(proInvXmhgInfo);
    }

    @ApiOperation(value="查询详情项目管理", notes="查询详情项目管理")
    @ApiImplicitParam(name = "proInvXmhgInfo", value = "项目管理entity", dataType = "ProInvXmhgInfo")
    @RequireToken
    @PostMapping("/getProInvXmhgInfoDetails")
    public ResponseEntity getProInvXmhgInfoDetails(@RequestBody(required = false) ProInvXmhgInfo proInvXmhgInfo) {
        return proInvXmhgInfoService.getProInvXmhgInfoDetails(proInvXmhgInfo);
    }

    @ApiOperation(value="新增项目管理", notes="新增项目管理")
    @ApiImplicitParam(name = "proInvXmhgInfo", value = "项目管理entity", dataType = "ProInvXmhgInfo")
    @RequireToken
    @PostMapping("/addProInvXmhgInfo")
    public ResponseEntity addProInvXmhgInfo(@RequestBody(required = false) ProInvXmhgInfo proInvXmhgInfo) {
        return proInvXmhgInfoService.saveProInvXmhgInfo(proInvXmhgInfo);
    }

    @ApiOperation(value="更新项目管理", notes="更新项目管理")
    @ApiImplicitParam(name = "proInvXmhgInfo", value = "项目管理entity", dataType = "ProInvXmhgInfo")
    @RequireToken
    @PostMapping("/updateProInvXmhgInfo")
    public ResponseEntity updateProInvXmhgInfo(@RequestBody(required = false) ProInvXmhgInfo proInvXmhgInfo) {
        return proInvXmhgInfoService.updateProInvXmhgInfo(proInvXmhgInfo);
    }

    @ApiOperation(value="删除项目管理", notes="删除项目管理")
    @ApiImplicitParam(name = "proInvXmhgInfoList", value = "项目管理List", dataType = "List<ProInvXmhgInfo>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateProInvXmhgInfo")
    public ResponseEntity batchDeleteUpdateProInvXmhgInfo(@RequestBody(required = false) List<ProInvXmhgInfo> proInvXmhgInfoList) {
        return proInvXmhgInfoService.batchDeleteUpdateProInvXmhgInfo(proInvXmhgInfoList);
    }

}
