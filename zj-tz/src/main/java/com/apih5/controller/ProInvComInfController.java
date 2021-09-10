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
import com.apih5.mybatis.pojo.ProInvComInf;
import com.apih5.service.ProInvComInfService;

@RestController
public class ProInvComInfController {

    @Autowired(required = true)
    private ProInvComInfService proInvComInfService;

    @ApiOperation(value="查询项目管理", notes="查询项目管理")
    @ApiImplicitParam(name = "proInvComInf", value = "项目管理entity", dataType = "ProInvComInf")
    @RequireToken
    @PostMapping("/getProInvComInfList")
    public ResponseEntity getProInvComInfList(@RequestBody(required = false) ProInvComInf proInvComInf) {
        return proInvComInfService.getProInvComInfListByCondition(proInvComInf);
    }

    @ApiOperation(value="查询详情项目管理", notes="查询详情项目管理")
    @ApiImplicitParam(name = "proInvComInf", value = "项目管理entity", dataType = "ProInvComInf")
    @RequireToken
    @PostMapping("/getProInvComInfDetails")
    public ResponseEntity getProInvComInfDetails(@RequestBody(required = false) ProInvComInf proInvComInf) {
        return proInvComInfService.getProInvComInfDetails(proInvComInf);
    }

    @ApiOperation(value="新增项目管理", notes="新增项目管理")
    @ApiImplicitParam(name = "proInvComInf", value = "项目管理entity", dataType = "ProInvComInf")
    @RequireToken
    @PostMapping("/addProInvComInf")
    public ResponseEntity addProInvComInf(@RequestBody(required = false) ProInvComInf proInvComInf) {
        return proInvComInfService.saveProInvComInf(proInvComInf);
    }

    @ApiOperation(value="更新项目管理", notes="更新项目管理")
    @ApiImplicitParam(name = "proInvComInf", value = "项目管理entity", dataType = "ProInvComInf")
    @RequireToken
    @PostMapping("/updateProInvComInf")
    public ResponseEntity updateProInvComInf(@RequestBody(required = false) ProInvComInf proInvComInf) {
        return proInvComInfService.updateProInvComInf(proInvComInf);
    }

    @ApiOperation(value="删除项目管理", notes="删除项目管理")
    @ApiImplicitParam(name = "proInvComInfList", value = "项目管理List", dataType = "List<ProInvComInf>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateProInvComInf")
    public ResponseEntity batchDeleteUpdateProInvComInf(@RequestBody(required = false) List<ProInvComInf> proInvComInfList) {
        return proInvComInfService.batchDeleteUpdateProInvComInf(proInvComInfList);
    }

}
