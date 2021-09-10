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
import com.apih5.mybatis.pojo.ZjXmCqjxDepartmentHead;
import com.apih5.service.ZjXmCqjxDepartmentHeadService;

@RestController
public class ZjXmCqjxDepartmentHeadController {

    @Autowired(required = true)
    private ZjXmCqjxDepartmentHeadService zjXmCqjxDepartmentHeadService;

    @ApiOperation(value="查询重庆绩效部门负责人", notes="查询重庆绩效部门负责人")
    @ApiImplicitParam(name = "zjXmCqjxDepartmentHead", value = "重庆绩效部门负责人entity", dataType = "ZjXmCqjxDepartmentHead")
    @RequireToken
    @PostMapping("/getZjXmCqjxDepartmentHeadList")
    public ResponseEntity getZjXmCqjxDepartmentHeadList(@RequestBody(required = false) ZjXmCqjxDepartmentHead zjXmCqjxDepartmentHead) {
        return zjXmCqjxDepartmentHeadService.getZjXmCqjxDepartmentHeadListByCondition(zjXmCqjxDepartmentHead);
    }

    @ApiOperation(value="查询详情重庆绩效部门负责人", notes="查询详情重庆绩效部门负责人")
    @ApiImplicitParam(name = "zjXmCqjxDepartmentHead", value = "重庆绩效部门负责人entity", dataType = "ZjXmCqjxDepartmentHead")
    @RequireToken
    @PostMapping("/getZjXmCqjxDepartmentHeadDetails")
    public ResponseEntity getZjXmCqjxDepartmentHeadDetails(@RequestBody(required = false) ZjXmCqjxDepartmentHead zjXmCqjxDepartmentHead) {
        return zjXmCqjxDepartmentHeadService.getZjXmCqjxDepartmentHeadDetails(zjXmCqjxDepartmentHead);
    }

    @ApiOperation(value="新增重庆绩效部门负责人", notes="新增重庆绩效部门负责人")
    @ApiImplicitParam(name = "zjXmCqjxDepartmentHead", value = "重庆绩效部门负责人entity", dataType = "ZjXmCqjxDepartmentHead")
    @RequireToken
    @PostMapping("/addZjXmCqjxDepartmentHead")
    public ResponseEntity addZjXmCqjxDepartmentHead(@RequestBody(required = false) ZjXmCqjxDepartmentHead zjXmCqjxDepartmentHead) {
        return zjXmCqjxDepartmentHeadService.saveZjXmCqjxDepartmentHead(zjXmCqjxDepartmentHead);
    }

    @ApiOperation(value="更新重庆绩效部门负责人", notes="更新重庆绩效部门负责人")
    @ApiImplicitParam(name = "zjXmCqjxDepartmentHead", value = "重庆绩效部门负责人entity", dataType = "ZjXmCqjxDepartmentHead")
    @RequireToken
    @PostMapping("/updateZjXmCqjxDepartmentHead")
    public ResponseEntity updateZjXmCqjxDepartmentHead(@RequestBody(required = false) ZjXmCqjxDepartmentHead zjXmCqjxDepartmentHead) {
        return zjXmCqjxDepartmentHeadService.updateZjXmCqjxDepartmentHead(zjXmCqjxDepartmentHead);
    }

    @ApiOperation(value="删除重庆绩效部门负责人", notes="删除重庆绩效部门负责人")
    @ApiImplicitParam(name = "zjXmCqjxDepartmentHeadList", value = "重庆绩效部门负责人List", dataType = "List<ZjXmCqjxDepartmentHead>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjXmCqjxDepartmentHead")
    public ResponseEntity batchDeleteUpdateZjXmCqjxDepartmentHead(@RequestBody(required = false) List<ZjXmCqjxDepartmentHead> zjXmCqjxDepartmentHeadList) {
        return zjXmCqjxDepartmentHeadService.batchDeleteUpdateZjXmCqjxDepartmentHead(zjXmCqjxDepartmentHeadList);
    }

}
