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
import com.apih5.mybatis.pojo.ProInvXmndtzjh;
import com.apih5.service.ProInvXmndtzjhService;

@RestController
public class ProInvXmndtzjhController {

    @Autowired(required = true)
    private ProInvXmndtzjhService proInvXmndtzjhService;

    @ApiOperation(value="查询项目年度投资计划", notes="查询项目年度投资计划")
    @ApiImplicitParam(name = "proInvXmndtzjh", value = "项目年度投资计划entity", dataType = "ProInvXmndtzjh")
    @RequireToken
    @PostMapping("/getProInvXmndtzjhList")
    public ResponseEntity getProInvXmndtzjhList(@RequestBody(required = false) ProInvXmndtzjh proInvXmndtzjh) {
        return proInvXmndtzjhService.getProInvXmndtzjhListByCondition(proInvXmndtzjh);
    }

    @ApiOperation(value="查询详情项目年度投资计划", notes="查询详情项目年度投资计划")
    @ApiImplicitParam(name = "proInvXmndtzjh", value = "项目年度投资计划entity", dataType = "ProInvXmndtzjh")
    @RequireToken
    @PostMapping("/getProInvXmndtzjhDetails")
    public ResponseEntity getProInvXmndtzjhDetails(@RequestBody(required = false) ProInvXmndtzjh proInvXmndtzjh) {
        return proInvXmndtzjhService.getProInvXmndtzjhDetails(proInvXmndtzjh);
    }

    @ApiOperation(value="新增项目年度投资计划", notes="新增项目年度投资计划")
    @ApiImplicitParam(name = "proInvXmndtzjh", value = "项目年度投资计划entity", dataType = "ProInvXmndtzjh")
    @RequireToken
    @PostMapping("/addProInvXmndtzjh")
    public ResponseEntity addProInvXmndtzjh(@RequestBody(required = false) ProInvXmndtzjh proInvXmndtzjh) {
        return proInvXmndtzjhService.saveProInvXmndtzjh(proInvXmndtzjh);
    }

    @ApiOperation(value="更新项目年度投资计划", notes="更新项目年度投资计划")
    @ApiImplicitParam(name = "proInvXmndtzjh", value = "项目年度投资计划entity", dataType = "ProInvXmndtzjh")
    @RequireToken
    @PostMapping("/updateProInvXmndtzjh")
    public ResponseEntity updateProInvXmndtzjh(@RequestBody(required = false) ProInvXmndtzjh proInvXmndtzjh) {
        return proInvXmndtzjhService.updateProInvXmndtzjh(proInvXmndtzjh);
    }

    @ApiOperation(value="删除项目年度投资计划", notes="删除项目年度投资计划")
    @ApiImplicitParam(name = "proInvXmndtzjhList", value = "项目年度投资计划List", dataType = "List<ProInvXmndtzjh>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateProInvXmndtzjh")
    public ResponseEntity batchDeleteUpdateProInvXmndtzjh(@RequestBody(required = false) List<ProInvXmndtzjh> proInvXmndtzjhList) {
        return proInvXmndtzjhService.batchDeleteUpdateProInvXmndtzjh(proInvXmndtzjhList);
    }

}
