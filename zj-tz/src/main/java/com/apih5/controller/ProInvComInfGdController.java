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
import com.apih5.mybatis.pojo.ProInvComInfGd;
import com.apih5.service.ProInvComInfGdService;

@RestController
public class ProInvComInfGdController {

    @Autowired(required = true)
    private ProInvComInfGdService proInvComInfGdService;

    @ApiOperation(value="查询项目管理", notes="查询项目管理")
    @ApiImplicitParam(name = "proInvComInfGd", value = "项目管理entity", dataType = "ProInvComInfGd")
    @RequireToken
    @PostMapping("/getProInvComInfGdList")
    public ResponseEntity getProInvComInfGdList(@RequestBody(required = false) ProInvComInfGd proInvComInfGd) {
        return proInvComInfGdService.getProInvComInfGdListByCondition(proInvComInfGd);
    }

    @ApiOperation(value="查询详情项目管理", notes="查询详情项目管理")
    @ApiImplicitParam(name = "proInvComInfGd", value = "项目管理entity", dataType = "ProInvComInfGd")
    @RequireToken
    @PostMapping("/getProInvComInfGdDetails")
    public ResponseEntity getProInvComInfGdDetails(@RequestBody(required = false) ProInvComInfGd proInvComInfGd) {
        return proInvComInfGdService.getProInvComInfGdDetails(proInvComInfGd);
    }

    @ApiOperation(value="新增项目管理", notes="新增项目管理")
    @ApiImplicitParam(name = "proInvComInfGd", value = "项目管理entity", dataType = "ProInvComInfGd")
    @RequireToken
    @PostMapping("/addProInvComInfGd")
    public ResponseEntity addProInvComInfGd(@RequestBody(required = false) ProInvComInfGd proInvComInfGd) {
        return proInvComInfGdService.saveProInvComInfGd(proInvComInfGd);
    }

    @ApiOperation(value="更新项目管理", notes="更新项目管理")
    @ApiImplicitParam(name = "proInvComInfGd", value = "项目管理entity", dataType = "ProInvComInfGd")
    @RequireToken
    @PostMapping("/updateProInvComInfGd")
    public ResponseEntity updateProInvComInfGd(@RequestBody(required = false) ProInvComInfGd proInvComInfGd) {
        return proInvComInfGdService.updateProInvComInfGd(proInvComInfGd);
    }

    @ApiOperation(value="删除项目管理", notes="删除项目管理")
    @ApiImplicitParam(name = "proInvComInfGdList", value = "项目管理List", dataType = "List<ProInvComInfGd>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateProInvComInfGd")
    public ResponseEntity batchDeleteUpdateProInvComInfGd(@RequestBody(required = false) List<ProInvComInfGd> proInvComInfGdList) {
        return proInvComInfGdService.batchDeleteUpdateProInvComInfGd(proInvComInfGdList);
    }

}
