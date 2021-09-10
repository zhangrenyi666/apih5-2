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
import com.apih5.mybatis.pojo.ProInvBasic;
import com.apih5.service.ProInvBasicService;

@RestController
public class ProInvBasicController {

    @Autowired(required = true)
    private ProInvBasicService proInvBasicService;

    @ApiOperation(value="查询项目管理", notes="查询项目管理")
    @ApiImplicitParam(name = "proInvBasic", value = "项目管理entity", dataType = "ProInvBasic")
    @RequireToken
    @PostMapping("/getProInvBasicList")
    public ResponseEntity getProInvBasicList(@RequestBody(required = false) ProInvBasic proInvBasic) {
        return proInvBasicService.getProInvBasicListByCondition(proInvBasic);
    }

    @ApiOperation(value="查询详情项目管理", notes="查询详情项目管理")
    @ApiImplicitParam(name = "proInvBasic", value = "项目管理entity", dataType = "ProInvBasic")
    @RequireToken
    @PostMapping("/getProInvBasicDetails")
    public ResponseEntity getProInvBasicDetails(@RequestBody(required = false) ProInvBasic proInvBasic) {
        return proInvBasicService.getProInvBasicDetails(proInvBasic);
    }

    @ApiOperation(value="新增项目管理", notes="新增项目管理")
    @ApiImplicitParam(name = "proInvBasic", value = "项目管理entity", dataType = "ProInvBasic")
    @RequireToken
    @PostMapping("/addProInvBasic")
    public ResponseEntity addProInvBasic(@RequestBody(required = false) ProInvBasic proInvBasic) {
        return proInvBasicService.saveProInvBasic(proInvBasic);
    }

    @ApiOperation(value="更新项目管理", notes="更新项目管理")
    @ApiImplicitParam(name = "proInvBasic", value = "项目管理entity", dataType = "ProInvBasic")
    @RequireToken
    @PostMapping("/updateProInvBasic")
    public ResponseEntity updateProInvBasic(@RequestBody(required = false) ProInvBasic proInvBasic) {
        return proInvBasicService.updateProInvBasic(proInvBasic);
    }

    @ApiOperation(value="删除项目管理", notes="删除项目管理")
    @ApiImplicitParam(name = "proInvBasicList", value = "项目管理List", dataType = "List<ProInvBasic>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateProInvBasic")
    public ResponseEntity batchDeleteUpdateProInvBasic(@RequestBody(required = false) List<ProInvBasic> proInvBasicList) {
        return proInvBasicService.batchDeleteUpdateProInvBasic(proInvBasicList);
    }
    
}
