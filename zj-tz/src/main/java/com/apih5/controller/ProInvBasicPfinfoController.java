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
import com.apih5.mybatis.pojo.ProInvBasicPfinfo;
import com.apih5.service.ProInvBasicPfinfoService;

@RestController
public class ProInvBasicPfinfoController {

    @Autowired(required = true)
    private ProInvBasicPfinfoService proInvBasicPfinfoService;

    @ApiOperation(value="查询项目管理", notes="查询项目管理")
    @ApiImplicitParam(name = "proInvBasicPfinfo", value = "项目管理entity", dataType = "ProInvBasicPfinfo")
    @RequireToken
    @PostMapping("/getProInvBasicPfinfoList")
    public ResponseEntity getProInvBasicPfinfoList(@RequestBody(required = false) ProInvBasicPfinfo proInvBasicPfinfo) {
        return proInvBasicPfinfoService.getProInvBasicPfinfoListByCondition(proInvBasicPfinfo);
    }

    @ApiOperation(value="查询详情项目管理", notes="查询详情项目管理")
    @ApiImplicitParam(name = "proInvBasicPfinfo", value = "项目管理entity", dataType = "ProInvBasicPfinfo")
    @RequireToken
    @PostMapping("/getProInvBasicPfinfoDetails")
    public ResponseEntity getProInvBasicPfinfoDetails(@RequestBody(required = false) ProInvBasicPfinfo proInvBasicPfinfo) {
        return proInvBasicPfinfoService.getProInvBasicPfinfoDetails(proInvBasicPfinfo);
    }

    @ApiOperation(value="新增项目管理", notes="新增项目管理")
    @ApiImplicitParam(name = "proInvBasicPfinfo", value = "项目管理entity", dataType = "ProInvBasicPfinfo")
    @RequireToken
    @PostMapping("/addProInvBasicPfinfo")
    public ResponseEntity addProInvBasicPfinfo(@RequestBody(required = false) ProInvBasicPfinfo proInvBasicPfinfo) {
        return proInvBasicPfinfoService.saveProInvBasicPfinfo(proInvBasicPfinfo);
    }

    @ApiOperation(value="更新项目管理", notes="更新项目管理")
    @ApiImplicitParam(name = "proInvBasicPfinfo", value = "项目管理entity", dataType = "ProInvBasicPfinfo")
    @RequireToken
    @PostMapping("/updateProInvBasicPfinfo")
    public ResponseEntity updateProInvBasicPfinfo(@RequestBody(required = false) ProInvBasicPfinfo proInvBasicPfinfo) {
        return proInvBasicPfinfoService.updateProInvBasicPfinfo(proInvBasicPfinfo);
    }

    @ApiOperation(value="删除项目管理", notes="删除项目管理")
    @ApiImplicitParam(name = "proInvBasicPfinfoList", value = "项目管理List", dataType = "List<ProInvBasicPfinfo>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateProInvBasicPfinfo")
    public ResponseEntity batchDeleteUpdateProInvBasicPfinfo(@RequestBody(required = false) List<ProInvBasicPfinfo> proInvBasicPfinfoList) {
        return proInvBasicPfinfoService.batchDeleteUpdateProInvBasicPfinfo(proInvBasicPfinfoList);
    }

}
