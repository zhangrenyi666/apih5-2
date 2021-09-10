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
import com.apih5.mybatis.pojo.ZxEqMachineJobs;
import com.apih5.service.ZxEqMachineJobsService;

@RestController
public class ZxEqMachineJobsController {

    @Autowired(required = true)
    private ZxEqMachineJobsService zxEqMachineJobsService;

    @ApiOperation(value="查询机械人员岗位设置", notes="查询机械人员岗位设置")
    @ApiImplicitParam(name = "zxEqMachineJobs", value = "机械人员岗位设置entity", dataType = "ZxEqMachineJobs")
    @RequireToken
    @PostMapping("/getZxEqMachineJobsList")
    public ResponseEntity getZxEqMachineJobsList(@RequestBody(required = false) ZxEqMachineJobs zxEqMachineJobs) {
        return zxEqMachineJobsService.getZxEqMachineJobsListByCondition(zxEqMachineJobs);
    }

    @ApiOperation(value="查询详情机械人员岗位设置", notes="查询详情机械人员岗位设置")
    @ApiImplicitParam(name = "zxEqMachineJobs", value = "机械人员岗位设置entity", dataType = "ZxEqMachineJobs")
    @RequireToken
    @PostMapping("/getZxEqMachineJobsDetails")
    public ResponseEntity getZxEqMachineJobsDetails(@RequestBody(required = false) ZxEqMachineJobs zxEqMachineJobs) {
        return zxEqMachineJobsService.getZxEqMachineJobsDetails(zxEqMachineJobs);
    }

    @ApiOperation(value="新增机械人员岗位设置", notes="新增机械人员岗位设置")
    @ApiImplicitParam(name = "zxEqMachineJobs", value = "机械人员岗位设置entity", dataType = "ZxEqMachineJobs")
    @RequireToken
    @PostMapping("/addZxEqMachineJobs")
    public ResponseEntity addZxEqMachineJobs(@RequestBody(required = false) ZxEqMachineJobs zxEqMachineJobs) {
        return zxEqMachineJobsService.saveZxEqMachineJobs(zxEqMachineJobs);
    }

    @ApiOperation(value="更新机械人员岗位设置", notes="更新机械人员岗位设置")
    @ApiImplicitParam(name = "zxEqMachineJobs", value = "机械人员岗位设置entity", dataType = "ZxEqMachineJobs")
    @RequireToken
    @PostMapping("/updateZxEqMachineJobs")
    public ResponseEntity updateZxEqMachineJobs(@RequestBody(required = false) ZxEqMachineJobs zxEqMachineJobs) {
        return zxEqMachineJobsService.updateZxEqMachineJobs(zxEqMachineJobs);
    }

    @ApiOperation(value="删除机械人员岗位设置", notes="删除机械人员岗位设置")
    @ApiImplicitParam(name = "zxEqMachineJobsList", value = "机械人员岗位设置List", dataType = "List<ZxEqMachineJobs>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxEqMachineJobs")
    public ResponseEntity batchDeleteUpdateZxEqMachineJobs(@RequestBody(required = false) List<ZxEqMachineJobs> zxEqMachineJobsList) {
        return zxEqMachineJobsService.batchDeleteUpdateZxEqMachineJobs(zxEqMachineJobsList);
    }

}
