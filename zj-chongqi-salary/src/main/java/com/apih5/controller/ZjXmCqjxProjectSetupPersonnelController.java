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
import com.apih5.mybatis.pojo.ZjXmCqjxProjectSetupPersonnel;
import com.apih5.service.ZjXmCqjxProjectSetupPersonnelService;

@RestController
public class ZjXmCqjxProjectSetupPersonnelController {

    @Autowired(required = true)
    private ZjXmCqjxProjectSetupPersonnelService zjXmCqjxProjectSetupPersonnelService;

    @ApiOperation(value="查询重庆绩效项目人事设置", notes="查询重庆绩效项目人事设置")
    @ApiImplicitParam(name = "zjXmCqjxProjectSetupPersonnel", value = "重庆绩效项目人事设置entity", dataType = "ZjXmCqjxProjectSetupPersonnel")
    @RequireToken
    @PostMapping("/getZjXmCqjxProjectSetupPersonnelList")
    public ResponseEntity getZjXmCqjxProjectSetupPersonnelList(@RequestBody(required = false) ZjXmCqjxProjectSetupPersonnel zjXmCqjxProjectSetupPersonnel) {
        return zjXmCqjxProjectSetupPersonnelService.getZjXmCqjxProjectSetupPersonnelListByCondition(zjXmCqjxProjectSetupPersonnel);
    }

    @ApiOperation(value="查询详情重庆绩效项目人事设置", notes="查询详情重庆绩效项目人事设置")
    @ApiImplicitParam(name = "zjXmCqjxProjectSetupPersonnel", value = "重庆绩效项目人事设置entity", dataType = "ZjXmCqjxProjectSetupPersonnel")
    @RequireToken
    @PostMapping("/getZjXmCqjxProjectSetupPersonnelDetails")
    public ResponseEntity getZjXmCqjxProjectSetupPersonnelDetails(@RequestBody(required = false) ZjXmCqjxProjectSetupPersonnel zjXmCqjxProjectSetupPersonnel) {
        return zjXmCqjxProjectSetupPersonnelService.getZjXmCqjxProjectSetupPersonnelDetails(zjXmCqjxProjectSetupPersonnel);
    }

    @ApiOperation(value="新增重庆绩效项目人事设置", notes="新增重庆绩效项目人事设置")
    @ApiImplicitParam(name = "zjXmCqjxProjectSetupPersonnel", value = "重庆绩效项目人事设置entity", dataType = "ZjXmCqjxProjectSetupPersonnel")
    @RequireToken
    @PostMapping("/addZjXmCqjxProjectSetupPersonnel")
    public ResponseEntity addZjXmCqjxProjectSetupPersonnel(@RequestBody(required = false) ZjXmCqjxProjectSetupPersonnel zjXmCqjxProjectSetupPersonnel) {
        return zjXmCqjxProjectSetupPersonnelService.saveZjXmCqjxProjectSetupPersonnel(zjXmCqjxProjectSetupPersonnel);
    }

    @ApiOperation(value="更新重庆绩效项目人事设置", notes="更新重庆绩效项目人事设置")
    @ApiImplicitParam(name = "zjXmCqjxProjectSetupPersonnel", value = "重庆绩效项目人事设置entity", dataType = "ZjXmCqjxProjectSetupPersonnel")
    @RequireToken
    @PostMapping("/updateZjXmCqjxProjectSetupPersonnel")
    public ResponseEntity updateZjXmCqjxProjectSetupPersonnel(@RequestBody(required = false) ZjXmCqjxProjectSetupPersonnel zjXmCqjxProjectSetupPersonnel) {
        return zjXmCqjxProjectSetupPersonnelService.updateZjXmCqjxProjectSetupPersonnel(zjXmCqjxProjectSetupPersonnel);
    }

    @ApiOperation(value="删除重庆绩效项目人事设置", notes="删除重庆绩效项目人事设置")
    @ApiImplicitParam(name = "zjXmCqjxProjectSetupPersonnelList", value = "重庆绩效项目人事设置List", dataType = "List<ZjXmCqjxProjectSetupPersonnel>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjXmCqjxProjectSetupPersonnel")
    public ResponseEntity batchDeleteUpdateZjXmCqjxProjectSetupPersonnel(@RequestBody(required = false) List<ZjXmCqjxProjectSetupPersonnel> zjXmCqjxProjectSetupPersonnelList) {
        return zjXmCqjxProjectSetupPersonnelService.batchDeleteUpdateZjXmCqjxProjectSetupPersonnel(zjXmCqjxProjectSetupPersonnelList);
    }

}