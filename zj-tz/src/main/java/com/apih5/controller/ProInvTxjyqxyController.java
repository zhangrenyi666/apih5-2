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
import com.apih5.mybatis.pojo.ProInvTxjyqxy;
import com.apih5.service.ProInvTxjyqxyService;

@RestController
public class ProInvTxjyqxyController {

    @Autowired(required = true)
    private ProInvTxjyqxyService proInvTxjyqxyService;

    @ApiOperation(value="查询项目管理", notes="查询项目管理")
    @ApiImplicitParam(name = "proInvTxjyqxy", value = "项目管理entity", dataType = "ProInvTxjyqxy")
    @RequireToken
    @PostMapping("/getProInvTxjyqxyList")
    public ResponseEntity getProInvTxjyqxyList(@RequestBody(required = false) ProInvTxjyqxy proInvTxjyqxy) {
        return proInvTxjyqxyService.getProInvTxjyqxyListByCondition(proInvTxjyqxy);
    }

    @ApiOperation(value="查询详情项目管理", notes="查询详情项目管理")
    @ApiImplicitParam(name = "proInvTxjyqxy", value = "项目管理entity", dataType = "ProInvTxjyqxy")
    @RequireToken
    @PostMapping("/getProInvTxjyqxyDetails")
    public ResponseEntity getProInvTxjyqxyDetails(@RequestBody(required = false) ProInvTxjyqxy proInvTxjyqxy) {
        return proInvTxjyqxyService.getProInvTxjyqxyDetails(proInvTxjyqxy);
    }

    @ApiOperation(value="新增项目管理", notes="新增项目管理")
    @ApiImplicitParam(name = "proInvTxjyqxy", value = "项目管理entity", dataType = "ProInvTxjyqxy")
    @RequireToken
    @PostMapping("/addProInvTxjyqxy")
    public ResponseEntity addProInvTxjyqxy(@RequestBody(required = false) ProInvTxjyqxy proInvTxjyqxy) {
        return proInvTxjyqxyService.saveProInvTxjyqxy(proInvTxjyqxy);
    }

    @ApiOperation(value="更新项目管理", notes="更新项目管理")
    @ApiImplicitParam(name = "proInvTxjyqxy", value = "项目管理entity", dataType = "ProInvTxjyqxy")
    @RequireToken
    @PostMapping("/updateProInvTxjyqxy")
    public ResponseEntity updateProInvTxjyqxy(@RequestBody(required = false) ProInvTxjyqxy proInvTxjyqxy) {
        return proInvTxjyqxyService.updateProInvTxjyqxy(proInvTxjyqxy);
    }

    @ApiOperation(value="删除项目管理", notes="删除项目管理")
    @ApiImplicitParam(name = "proInvTxjyqxyList", value = "项目管理List", dataType = "List<ProInvTxjyqxy>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateProInvTxjyqxy")
    public ResponseEntity batchDeleteUpdateProInvTxjyqxy(@RequestBody(required = false) List<ProInvTxjyqxy> proInvTxjyqxyList) {
        return proInvTxjyqxyService.batchDeleteUpdateProInvTxjyqxy(proInvTxjyqxyList);
    }

}
