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
import com.apih5.mybatis.pojo.ZjXmCqjxProjectSetupAdmin;
import com.apih5.service.ZjXmCqjxProjectSetupAdminService;

@RestController
public class ZjXmCqjxProjectSetupAdminController {

    @Autowired(required = true)
    private ZjXmCqjxProjectSetupAdminService zjXmCqjxProjectSetupAdminService;

    @ApiOperation(value="查询重庆绩效项目负责人设置", notes="查询重庆绩效项目负责人设置")
    @ApiImplicitParam(name = "zjXmCqjxProjectSetupAdmin", value = "重庆绩效项目负责人设置entity", dataType = "ZjXmCqjxProjectSetupAdmin")
    @RequireToken
    @PostMapping("/getZjXmCqjxProjectSetupAdminList")
    public ResponseEntity getZjXmCqjxProjectSetupAdminList(@RequestBody(required = false) ZjXmCqjxProjectSetupAdmin zjXmCqjxProjectSetupAdmin) {
        return zjXmCqjxProjectSetupAdminService.getZjXmCqjxProjectSetupAdminListByCondition(zjXmCqjxProjectSetupAdmin);
    }

    @ApiOperation(value="查询详情重庆绩效项目负责人设置", notes="查询详情重庆绩效项目负责人设置")
    @ApiImplicitParam(name = "zjXmCqjxProjectSetupAdmin", value = "重庆绩效项目负责人设置entity", dataType = "ZjXmCqjxProjectSetupAdmin")
    @RequireToken
    @PostMapping("/getZjXmCqjxProjectSetupAdminDetails")
    public ResponseEntity getZjXmCqjxProjectSetupAdminDetails(@RequestBody(required = false) ZjXmCqjxProjectSetupAdmin zjXmCqjxProjectSetupAdmin) {
        return zjXmCqjxProjectSetupAdminService.getZjXmCqjxProjectSetupAdminDetails(zjXmCqjxProjectSetupAdmin);
    }

    @ApiOperation(value="新增重庆绩效项目负责人设置", notes="新增重庆绩效项目负责人设置")
    @ApiImplicitParam(name = "zjXmCqjxProjectSetupAdmin", value = "重庆绩效项目负责人设置entity", dataType = "ZjXmCqjxProjectSetupAdmin")
    @RequireToken
    @PostMapping("/addZjXmCqjxProjectSetupAdmin")
    public ResponseEntity addZjXmCqjxProjectSetupAdmin(@RequestBody(required = false) ZjXmCqjxProjectSetupAdmin zjXmCqjxProjectSetupAdmin) {
        return zjXmCqjxProjectSetupAdminService.saveZjXmCqjxProjectSetupAdmin(zjXmCqjxProjectSetupAdmin);
    }

    @ApiOperation(value="更新重庆绩效项目负责人设置", notes="更新重庆绩效项目负责人设置")
    @ApiImplicitParam(name = "zjXmCqjxProjectSetupAdmin", value = "重庆绩效项目负责人设置entity", dataType = "ZjXmCqjxProjectSetupAdmin")
    @RequireToken
    @PostMapping("/updateZjXmCqjxProjectSetupAdmin")
    public ResponseEntity updateZjXmCqjxProjectSetupAdmin(@RequestBody(required = false) ZjXmCqjxProjectSetupAdmin zjXmCqjxProjectSetupAdmin) {
        return zjXmCqjxProjectSetupAdminService.updateZjXmCqjxProjectSetupAdmin(zjXmCqjxProjectSetupAdmin);
    }

    @ApiOperation(value="删除重庆绩效项目负责人设置", notes="删除重庆绩效项目负责人设置")
    @ApiImplicitParam(name = "zjXmCqjxProjectSetupAdminList", value = "重庆绩效项目负责人设置List", dataType = "List<ZjXmCqjxProjectSetupAdmin>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjXmCqjxProjectSetupAdmin")
    public ResponseEntity batchDeleteUpdateZjXmCqjxProjectSetupAdmin(@RequestBody(required = false) List<ZjXmCqjxProjectSetupAdmin> zjXmCqjxProjectSetupAdminList) {
        return zjXmCqjxProjectSetupAdminService.batchDeleteUpdateZjXmCqjxProjectSetupAdmin(zjXmCqjxProjectSetupAdminList);
    }

}