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
import com.apih5.mybatis.pojo.ProInvBgbcxy;
import com.apih5.service.ProInvBgbcxyService;

@RestController
public class ProInvBgbcxyController {

    @Autowired(required = true)
    private ProInvBgbcxyService proInvBgbcxyService;

    @ApiOperation(value="查询投资项目_投资合同变更补充", notes="查询投资项目_投资合同变更补充")
    @ApiImplicitParam(name = "proInvBgbcxy", value = "投资项目_投资合同变更补充entity", dataType = "ProInvBgbcxy")
    @RequireToken
    @PostMapping("/getProInvBgbcxyList")
    public ResponseEntity getProInvBgbcxyList(@RequestBody(required = false) ProInvBgbcxy proInvBgbcxy) {
        return proInvBgbcxyService.getProInvBgbcxyListByCondition(proInvBgbcxy);
    }

    @ApiOperation(value="查询详情投资项目_投资合同变更补充", notes="查询详情投资项目_投资合同变更补充")
    @ApiImplicitParam(name = "proInvBgbcxy", value = "投资项目_投资合同变更补充entity", dataType = "ProInvBgbcxy")
    @RequireToken
    @PostMapping("/getProInvBgbcxyDetails")
    public ResponseEntity getProInvBgbcxyDetails(@RequestBody(required = false) ProInvBgbcxy proInvBgbcxy) {
        return proInvBgbcxyService.getProInvBgbcxyDetails(proInvBgbcxy);
    }

    @ApiOperation(value="新增投资项目_投资合同变更补充", notes="新增投资项目_投资合同变更补充")
    @ApiImplicitParam(name = "proInvBgbcxy", value = "投资项目_投资合同变更补充entity", dataType = "ProInvBgbcxy")
    @RequireToken
    @PostMapping("/addProInvBgbcxy")
    public ResponseEntity addProInvBgbcxy(@RequestBody(required = false) ProInvBgbcxy proInvBgbcxy) {
        return proInvBgbcxyService.saveProInvBgbcxy(proInvBgbcxy);
    }

    @ApiOperation(value="更新投资项目_投资合同变更补充", notes="更新投资项目_投资合同变更补充")
    @ApiImplicitParam(name = "proInvBgbcxy", value = "投资项目_投资合同变更补充entity", dataType = "ProInvBgbcxy")
    @RequireToken
    @PostMapping("/updateProInvBgbcxy")
    public ResponseEntity updateProInvBgbcxy(@RequestBody(required = false) ProInvBgbcxy proInvBgbcxy) {
        return proInvBgbcxyService.updateProInvBgbcxy(proInvBgbcxy);
    }

    @ApiOperation(value="删除投资项目_投资合同变更补充", notes="删除投资项目_投资合同变更补充")
    @ApiImplicitParam(name = "proInvBgbcxyList", value = "投资项目_投资合同变更补充List", dataType = "List<ProInvBgbcxy>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateProInvBgbcxy")
    public ResponseEntity batchDeleteUpdateProInvBgbcxy(@RequestBody(required = false) List<ProInvBgbcxy> proInvBgbcxyList) {
        return proInvBgbcxyService.batchDeleteUpdateProInvBgbcxy(proInvBgbcxyList);
    }

}
