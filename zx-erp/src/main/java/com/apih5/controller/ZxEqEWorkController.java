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
import com.apih5.mybatis.pojo.ZxEqEWork;
import com.apih5.service.ZxEqEWorkService;

@RestController
public class ZxEqEWorkController {

    @Autowired(required = true)
    private ZxEqEWorkService zxEqEWorkService;

    @ApiOperation(value="查询项目设备管理-机械设备运转记录", notes="查询项目设备管理-机械设备运转记录")
    @ApiImplicitParam(name = "zxEqEWork", value = "项目设备管理-机械设备运转记录entity", dataType = "ZxEqEWork")
    @RequireToken
    @PostMapping("/getZxEqEWorkList")
    public ResponseEntity getZxEqEWorkList(@RequestBody(required = false) ZxEqEWork zxEqEWork) {
        return zxEqEWorkService.getZxEqEWorkListByCondition(zxEqEWork);
    }

    @ApiOperation(value="查询详情项目设备管理-机械设备运转记录", notes="查询详情项目设备管理-机械设备运转记录")
    @ApiImplicitParam(name = "zxEqEWork", value = "项目设备管理-机械设备运转记录entity", dataType = "ZxEqEWork")
    @RequireToken
    @PostMapping("/getZxEqEWorkDetails")
    public ResponseEntity getZxEqEWorkDetails(@RequestBody(required = false) ZxEqEWork zxEqEWork) {
        return zxEqEWorkService.getZxEqEWorkDetails(zxEqEWork);
    }

    @ApiOperation(value="新增项目设备管理-机械设备运转记录", notes="新增项目设备管理-机械设备运转记录")
    @ApiImplicitParam(name = "zxEqEWork", value = "项目设备管理-机械设备运转记录entity", dataType = "ZxEqEWork")
    @RequireToken
    @PostMapping("/addZxEqEWork")
    public ResponseEntity addZxEqEWork(@RequestBody(required = false) ZxEqEWork zxEqEWork) {
        return zxEqEWorkService.saveZxEqEWork(zxEqEWork);
    }

    @ApiOperation(value="更新项目设备管理-机械设备运转记录", notes="更新项目设备管理-机械设备运转记录")
    @ApiImplicitParam(name = "zxEqEWork", value = "项目设备管理-机械设备运转记录entity", dataType = "ZxEqEWork")
    @RequireToken
    @PostMapping("/updateZxEqEWork")
    public ResponseEntity updateZxEqEWork(@RequestBody(required = false) ZxEqEWork zxEqEWork) {
        return zxEqEWorkService.updateZxEqEWork(zxEqEWork);
    }

    @ApiOperation(value="删除项目设备管理-机械设备运转记录", notes="删除项目设备管理-机械设备运转记录")
    @ApiImplicitParam(name = "zxEqEWorkList", value = "项目设备管理-机械设备运转记录List", dataType = "List<ZxEqEWork>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxEqEWork")
    public ResponseEntity batchDeleteUpdateZxEqEWork(@RequestBody(required = false) List<ZxEqEWork> zxEqEWorkList) {
        return zxEqEWorkService.batchDeleteUpdateZxEqEWork(zxEqEWorkList);
    }

    //报表
    @ApiOperation(value="查询机械车辆使用情况报表", notes="查询机械车辆使用情况报表")
    @ApiImplicitParam(name = "zxEqEWork", value = "项目设备管理-机械设备运转记录entity", dataType = "ZxEqEWork")
    @RequireToken
    @PostMapping("/getZxEqEWorkListForReport")
    public ResponseEntity getZxEqEWorkListForReport(@RequestBody(required = false) ZxEqEWork zxEqEWork) {
        return zxEqEWorkService.getZxEqEWorkListForReport(zxEqEWork);
    }
    @ApiOperation(value="导出机械车辆使用情况报表", notes="导出机械车辆使用情况报表")
    @ApiImplicitParam(name = "zxEqEWork", value = "项目设备管理-机械设备运转记录entity", dataType = "ZxEqEWork")
    @PostMapping("/ureportZxEqEWorkListForReport")
    public List<ZxEqEWork> ureportZxEqEWorkListForReport(@RequestBody(required = false) ZxEqEWork zxEqEWork) {
        return zxEqEWorkService.ureportZxEqEWorkListForReport(zxEqEWork);
    }
    
}
