package com.apih5.controller;

import com.apih5.framework.annotation.RequireToken;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjTzProManage;
import com.apih5.service.ZjTzProManageService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
public class ZjTzProManageController {

    @Autowired(required = true)
    private ZjTzProManageService zjTzProManageService;

    @ApiOperation(value="查询项目管理", notes="查询项目管理")
    @ApiImplicitParam(name = "zjTzProManage", value = "项目管理entity", dataType = "ZjTzProManage")
    @RequireToken
    @PostMapping("/getZjTzProManageList")
    public ResponseEntity getZjTzProManageList(@RequestBody(required = false) ZjTzProManage zjTzProManage) {
        return zjTzProManageService.getZjTzProManageListByCondition(zjTzProManage);
    }

    @ApiOperation(value="查询详情项目管理", notes="查询详情项目管理")
    @ApiImplicitParam(name = "zjTzProManage", value = "项目管理entity", dataType = "ZjTzProManage")
    @RequireToken
    @PostMapping("/getZjTzProManageDetails")
    public ResponseEntity getZjTzProManageDetails(@RequestBody(required = false) ZjTzProManage zjTzProManage) {
        return zjTzProManageService.getZjTzProManageDetails(zjTzProManage);
    }

    @ApiOperation(value="新增项目管理", notes="新增项目管理")
    @ApiImplicitParam(name = "zjTzProManage", value = "项目管理entity", dataType = "ZjTzProManage")
    @RequireToken
    @PostMapping("/addZjTzProManage")
    public ResponseEntity addZjTzProManage(@RequestBody(required = false) ZjTzProManage zjTzProManage) {
        return zjTzProManageService.saveZjTzProManage(zjTzProManage);
    }

    @ApiOperation(value="更新项目管理", notes="更新项目管理")
    @ApiImplicitParam(name = "zjTzProManage", value = "项目管理entity", dataType = "ZjTzProManage")
    @RequireToken
    @PostMapping("/updateZjTzProManage")
    public ResponseEntity updateZjTzProManage(@RequestBody(required = false) ZjTzProManage zjTzProManage) {
        return zjTzProManageService.updateZjTzProManage(zjTzProManage);
    }

    @ApiOperation(value="删除项目管理", notes="删除项目管理")
    @ApiImplicitParam(name = "zjTzProManageList", value = "项目管理List", dataType = "List<ZjTzProManage>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjTzProManage")
    public ResponseEntity batchDeleteUpdateZjTzProManage(@RequestBody(required = false) List<ZjTzProManage> zjTzProManageList) {
        return zjTzProManageService.batchDeleteUpdateZjTzProManage(zjTzProManageList);
    }
    
    @ApiOperation(value="查询项目管理", notes="查询项目管理")
    @ApiImplicitParam(name = "zjTzProManage", value = "项目管理entity", dataType = "ZjTzProManage")
    @RequireToken
    @PostMapping("/getZjTzProManageListForHard")
    public ResponseEntity getZjTzProManageListForHard(@RequestBody(required = false) ZjTzProManage zjTzProManage) {
        return zjTzProManageService.getZjTzProManageListForHard(zjTzProManage);
    }
    
    @ApiOperation(value="移动至已签约项目管理", notes="移动至已签约项目管理")
    @ApiImplicitParam(name = "zjTzProManage", value = "项目管理entity", dataType = "ZjTzProManage")
    @RequireToken
    @PostMapping("/changeZjTzProManageToSign")
    public ResponseEntity changeZjTzProManageToSign(@RequestBody(required = false) ZjTzProManage zjTzProManage) {
        return zjTzProManageService.changeZjTzProManageToSign(zjTzProManage);
    }
    
    @ApiOperation(value="中间库同步已签约项目信息接口", notes="中间库同步已签约项目信息接口")
    @ApiImplicitParam(name = "zjTzProManage", value = "项目管理entity", dataType = "ZjTzProManage")
    @GetMapping("/synZjTzProManage")
    public ResponseEntity synZjTzProManage(@RequestBody(required = false) ZjTzProManage zjTzProManage) {
        return zjTzProManageService.synZjTzProManage();
    }
    
    //其他人接口
    @ApiOperation(value="查询首页地域总览", notes="查询首页地域总览")
    @ApiImplicitParam(name = "zjTzProManage", value = "项目管理entity", dataType = "ZjTzProManage")
    @RequireToken
    @PostMapping("/getHomeRegionalOverviewProcessAndType")
    public ResponseEntity getHomeRegionalOverviewProcessAndType(@RequestBody(required = false) ZjTzProManage zjTzProManage) {
        return zjTzProManageService.getHomeRegionalOverviewProcessAndType(zjTzProManage);
    }
    
    @ApiOperation(value="首页地域总览项目列表", notes="首页地域总览项目列表")
    @ApiImplicitParam(name = "ZjTzProManage", value = "投资项目_项目投资情况entity", dataType = "ZjTzProManage")
    @RequireToken
    @PostMapping("/getHomeRegionalOverviewProList")
    public ResponseEntity getHomeRegionalOverviewProList(@RequestBody(required = false) ZjTzProManage zjTzProManage) {
    	return zjTzProManageService.getHomeRegionalOverviewProList(zjTzProManage);
    }
    
    @ApiOperation(value="首页地域总览项目列表导出", notes="首页地域总览项目列表导出")
    @ApiImplicitParam(name = "ZjTzProManage", value = "投资项目_项目投资情况entity", dataType = "ZjTzProManage")
//    @RequireToken
    @PostMapping("/exportHomeRegionalOverviewProList")
    public ResponseEntity exportHomeRegionalOverviewProList(@RequestBody(required = false) ZjTzProManage zjTzProManage, HttpServletResponse response) {
    	return zjTzProManageService.exportHomeRegionalOverviewProList(zjTzProManage,response);
    }
    
    @ApiOperation(value="所有项目工期预警", notes="所有项目工期预警")
    @ApiImplicitParam(name = "ZjTzProManage", value = "投资项目_项目投资情况entity", dataType = "ZjTzProManage")
    @RequireToken
    @PostMapping("/getHomeProjectStatusAllProject")
    public ResponseEntity getHomeProjectStatusAllProject(@RequestBody(required = false) ZjTzProManage zjTzProManage) {
    	return zjTzProManageService.getHomeProjectStatusAllProject(zjTzProManage);
    }
    
    @ApiOperation(value="所有项目工期状态饼图各部分弹出页面", notes="所有项目工期状态饼图各部分弹出页面")
    @ApiImplicitParam(name = "ZjTzProManage", value = "投资项目_项目投资情况entity", dataType = "ZjTzProManage")
    @RequireToken
    @PostMapping("/getHomeProjectStatusAllProjectAlertPage")
    public ResponseEntity getHomeProjectStatusAllProjectAlertPage(@RequestBody(required = false) ZjTzProManage zjTzProManage) {
    	return zjTzProManageService.getHomeProjectStatusAllProjectAlertPage(zjTzProManage);
    }
    
    @ApiOperation(value="项目页工期状态", notes="项目页工期状态")
    @ApiImplicitParam(name = "ZjTzProManage", value = "投资项目_项目投资情况entity", dataType = "ZjTzProManage")
    @RequireToken
    @PostMapping("/getHomeProjectStatus")
    public ResponseEntity getHomeProjectStatus(@RequestBody(required = false) ZjTzProManage zjTzProManage) {
    	return zjTzProManageService.getHomeProjectStatusByProjectId(zjTzProManage);
    }
    
    @ApiOperation(value="导出首页工期状态", notes="导出首页工期状态")
    @ApiImplicitParam(name = "ZjTzProManage", value = "投资项目_项目投资情况entity", dataType = "ZjTzProManage")
//    @RequireToken
    @PostMapping("/exportHomeProjectStatus")
    public ResponseEntity exportHomeProjectStatus(@RequestBody(required = false) ZjTzProManage zjTzProManage, HttpServletResponse response) {
    	return zjTzProManageService.exportHomeProjectStatus(zjTzProManage, response);
    }
    @ApiOperation(value="首页计划进度-管理单位下拉", notes="首页计划进度-管理单位下拉")
    @ApiImplicitParam(name = "ZjTzProManage", value = "投资项目_项目投资情况entity", dataType = "ZjTzProManage")
    @RequireToken
    @PostMapping("/getHomeProgressPlaningComname")
    public ResponseEntity getHomeProgressPlaningComname(@RequestBody(required = false) ZjTzProManage zjTzProManage) {
    	return zjTzProManageService.getHomeProgressPlaningComname(zjTzProManage);
    }
    
    @ApiOperation(value="集采平台", notes="集采平台")
    @ApiImplicitParam(name = "zjTzProManage", value = "项目管理entity", dataType = "ZjTzProManage")
    @RequireToken
    @PostMapping("/getZjTzProManageListNotUpdated")
    public ResponseEntity getZjTzProManageListNotUpdated(@RequestBody(required = false) ZjTzProManage zjTzProManage) {
        return zjTzProManageService.getZjTzProManageListNotUpdated(zjTzProManage);
    }
}
