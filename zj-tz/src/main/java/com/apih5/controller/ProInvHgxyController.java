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
import com.apih5.mybatis.pojo.ProInvHgxy;
import com.apih5.service.ProInvHgxyService;

@RestController
public class ProInvHgxyController {

    @Autowired(required = true)
    private ProInvHgxyService proInvHgxyService;

    @ApiOperation(value="查询项目管理", notes="查询项目管理")
    @ApiImplicitParam(name = "proInvHgxy", value = "项目管理entity", dataType = "ProInvHgxy")
    @RequireToken
    @PostMapping("/getProInvHgxyList")
    public ResponseEntity getProInvHgxyList(@RequestBody(required = false) ProInvHgxy proInvHgxy) {
        return proInvHgxyService.getProInvHgxyListByCondition(proInvHgxy);
    }

    @ApiOperation(value="查询详情项目管理", notes="查询详情项目管理")
    @ApiImplicitParam(name = "proInvHgxy", value = "项目管理entity", dataType = "ProInvHgxy")
    @RequireToken
    @PostMapping("/getProInvHgxyDetails")
    public ResponseEntity getProInvHgxyDetails(@RequestBody(required = false) ProInvHgxy proInvHgxy) {
        return proInvHgxyService.getProInvHgxyDetails(proInvHgxy);
    }

    @ApiOperation(value="新增项目管理", notes="新增项目管理")
    @ApiImplicitParam(name = "proInvHgxy", value = "项目管理entity", dataType = "ProInvHgxy")
    @RequireToken
    @PostMapping("/addProInvHgxy")
    public ResponseEntity addProInvHgxy(@RequestBody(required = false) ProInvHgxy proInvHgxy) {
        return proInvHgxyService.saveProInvHgxy(proInvHgxy);
    }

    @ApiOperation(value="更新项目管理", notes="更新项目管理")
    @ApiImplicitParam(name = "proInvHgxy", value = "项目管理entity", dataType = "ProInvHgxy")
    @RequireToken
    @PostMapping("/updateProInvHgxy")
    public ResponseEntity updateProInvHgxy(@RequestBody(required = false) ProInvHgxy proInvHgxy) {
        return proInvHgxyService.updateProInvHgxy(proInvHgxy);
    }

    @ApiOperation(value="删除项目管理", notes="删除项目管理")
    @ApiImplicitParam(name = "proInvHgxyList", value = "项目管理List", dataType = "List<ProInvHgxy>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateProInvHgxy")
    public ResponseEntity batchDeleteUpdateProInvHgxy(@RequestBody(required = false) List<ProInvHgxy> proInvHgxyList) {
        return proInvHgxyService.batchDeleteUpdateProInvHgxy(proInvHgxyList);
    }

}
