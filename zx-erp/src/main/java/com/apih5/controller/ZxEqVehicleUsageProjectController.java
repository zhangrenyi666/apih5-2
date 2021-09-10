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
import com.apih5.mybatis.pojo.ZxEqExternalEquipmentUsage;
import com.apih5.mybatis.pojo.ZxEqVehicleUsageProject;
import com.apih5.service.ZxEqVehicleUsageProjectService;

@RestController
public class ZxEqVehicleUsageProjectController {

    @Autowired(required = true)
    private ZxEqVehicleUsageProjectService zxEqVehicleUsageProjectService;

    @ApiOperation(value="查询机械车辆使用情况报表（项目）", notes="查询机械车辆使用情况报表（项目）")
    @ApiImplicitParam(name = "zxEqVehicleUsageProject", value = "机械车辆使用情况报表（项目）entity", dataType = "ZxEqVehicleUsageProject")
    @RequireToken
    @PostMapping("/getZxEqVehicleUsageProjectList")
    public ResponseEntity getZxEqVehicleUsageProjectList(@RequestBody(required = false) ZxEqVehicleUsageProject zxEqVehicleUsageProject) {
        return zxEqVehicleUsageProjectService.getZxEqVehicleUsageProjectListByCondition(zxEqVehicleUsageProject);
    }

    @ApiOperation(value="查询详情机械车辆使用情况报表（项目）", notes="查询详情机械车辆使用情况报表（项目）")
    @ApiImplicitParam(name = "zxEqVehicleUsageProject", value = "机械车辆使用情况报表（项目）entity", dataType = "ZxEqVehicleUsageProject")
    @RequireToken
    @PostMapping("/getZxEqVehicleUsageProjectDetail")
    public ResponseEntity getZxEqVehicleUsageProjectDetail(@RequestBody(required = false) ZxEqVehicleUsageProject zxEqVehicleUsageProject) {
        return zxEqVehicleUsageProjectService.getZxEqVehicleUsageProjectDetail(zxEqVehicleUsageProject);
    }

    @ApiOperation(value="新增机械车辆使用情况报表（项目）", notes="新增机械车辆使用情况报表（项目）")
    @ApiImplicitParam(name = "zxEqVehicleUsageProject", value = "机械车辆使用情况报表（项目）entity", dataType = "ZxEqVehicleUsageProject")
    @RequireToken
    @PostMapping("/addZxEqVehicleUsageProject")
    public ResponseEntity addZxEqVehicleUsageProject(@RequestBody(required = false) ZxEqVehicleUsageProject zxEqVehicleUsageProject) {
        return zxEqVehicleUsageProjectService.saveZxEqVehicleUsageProject(zxEqVehicleUsageProject);
    }

    @ApiOperation(value="更新机械车辆使用情况报表（项目）", notes="更新机械车辆使用情况报表（项目）")
    @ApiImplicitParam(name = "zxEqVehicleUsageProject", value = "机械车辆使用情况报表（项目）entity", dataType = "ZxEqVehicleUsageProject")
    @RequireToken
    @PostMapping("/updateZxEqVehicleUsageProject")
    public ResponseEntity updateZxEqVehicleUsageProject(@RequestBody(required = false) ZxEqVehicleUsageProject zxEqVehicleUsageProject) {
        return zxEqVehicleUsageProjectService.updateZxEqVehicleUsageProject(zxEqVehicleUsageProject);
    }

    @ApiOperation(value="删除机械车辆使用情况报表（项目）", notes="删除机械车辆使用情况报表（项目）")
    @ApiImplicitParam(name = "zxEqVehicleUsageProjectList", value = "机械车辆使用情况报表（项目）List", dataType = "List<ZxEqVehicleUsageProject>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxEqVehicleUsageProject")
    public ResponseEntity batchDeleteUpdateZxEqVehicleUsageProject(@RequestBody(required = false) List<ZxEqVehicleUsageProject> zxEqVehicleUsageProjectList) {
        return zxEqVehicleUsageProjectService.batchDeleteUpdateZxEqVehicleUsageProject(zxEqVehicleUsageProjectList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    
    @ApiOperation(value="报表导出机械车辆使用情况报表（项目）", notes="报表导出机械车辆使用情况报表（项目）")
    @ApiImplicitParam(name = "zxEqVehicleUsageProject", value = "设备台账entity", dataType = "ZxEqVehicleUsageProject")
    @PostMapping("/ureportZxEqVehicleUsageProject")
    public List<ZxEqVehicleUsageProject> ureportZxEqVehicleUsageProject(@RequestBody(required = false) ZxEqVehicleUsageProject zxEqVehicleUsageProject) {
        return zxEqVehicleUsageProjectService.ureportZxEqVehicleUsageProject(zxEqVehicleUsageProject);
    }
    
    @ApiOperation(value="报表导出机械车辆使用情况报表（项目）", notes="报表导出机械车辆使用情况报表（项目）")
    @ApiImplicitParam(name = "zxEqVehicleUsageProject", value = "设备台账entity", dataType = "ZxEqVehicleUsageProject")
    @RequireToken
    @PostMapping("/ureportZxEqVehicleUsageProjectIdle")
    public ResponseEntity ureportZxEqVehicleUsageProjectIdle(@RequestBody(required = false) ZxEqVehicleUsageProject zxEqVehicleUsageProject) {
        return zxEqVehicleUsageProjectService.ureportZxEqVehicleUsageProjectIdle(zxEqVehicleUsageProject);
    }
}
