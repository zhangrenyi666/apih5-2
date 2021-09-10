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
import com.apih5.mybatis.pojo.ZjTzEmployeeWork;
import com.apih5.service.ZjTzEmployeeWorkService;

@RestController
public class ZjTzEmployeeWorkController {

    @Autowired(required = true)
    private ZjTzEmployeeWorkService zjTzEmployeeWorkService;

    @ApiOperation(value="查询人员库-工作经历", notes="查询人员库-工作经历")
    @ApiImplicitParam(name = "zjTzEmployeeWork", value = "人员库-工作经历entity", dataType = "ZjTzEmployeeWork")
    @RequireToken
    @PostMapping("/getZjTzEmployeeWorkList")
    public ResponseEntity getZjTzEmployeeWorkList(@RequestBody(required = false) ZjTzEmployeeWork zjTzEmployeeWork) {
        return zjTzEmployeeWorkService.getZjTzEmployeeWorkListByCondition(zjTzEmployeeWork);
    }

    @ApiOperation(value="查询详情人员库-工作经历", notes="查询详情人员库-工作经历")
    @ApiImplicitParam(name = "zjTzEmployeeWork", value = "人员库-工作经历entity", dataType = "ZjTzEmployeeWork")
    @RequireToken
    @PostMapping("/getZjTzEmployeeWorkDetails")
    public ResponseEntity getZjTzEmployeeWorkDetails(@RequestBody(required = false) ZjTzEmployeeWork zjTzEmployeeWork) {
        return zjTzEmployeeWorkService.getZjTzEmployeeWorkDetails(zjTzEmployeeWork);
    }

    @ApiOperation(value="新增人员库-工作经历", notes="新增人员库-工作经历")
    @ApiImplicitParam(name = "zjTzEmployeeWork", value = "人员库-工作经历entity", dataType = "ZjTzEmployeeWork")
    @RequireToken
    @PostMapping("/addZjTzEmployeeWork")
    public ResponseEntity addZjTzEmployeeWork(@RequestBody(required = false) ZjTzEmployeeWork zjTzEmployeeWork) {
        return zjTzEmployeeWorkService.saveZjTzEmployeeWork(zjTzEmployeeWork);
    }

    @ApiOperation(value="更新人员库-工作经历", notes="更新人员库-工作经历")
    @ApiImplicitParam(name = "zjTzEmployeeWork", value = "人员库-工作经历entity", dataType = "ZjTzEmployeeWork")
    @RequireToken
    @PostMapping("/updateZjTzEmployeeWork")
    public ResponseEntity updateZjTzEmployeeWork(@RequestBody(required = false) ZjTzEmployeeWork zjTzEmployeeWork) {
        return zjTzEmployeeWorkService.updateZjTzEmployeeWork(zjTzEmployeeWork);
    }

    @ApiOperation(value="删除人员库-工作经历", notes="删除人员库-工作经历")
    @ApiImplicitParam(name = "zjTzEmployeeWorkList", value = "人员库-工作经历List", dataType = "List<ZjTzEmployeeWork>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjTzEmployeeWork")
    public ResponseEntity batchDeleteUpdateZjTzEmployeeWork(@RequestBody(required = false) List<ZjTzEmployeeWork> zjTzEmployeeWorkList) {
        return zjTzEmployeeWorkService.batchDeleteUpdateZjTzEmployeeWork(zjTzEmployeeWorkList);
    }

    @ApiOperation(value="导出人员库-工作经历", notes="导出人员库-工作经历")
    @ApiImplicitParam(name = "zjTzEmployeeWorkList", value = "人员库-工作经历List", dataType = "zjTzEmployeeWork")
    @PostMapping("/printZjTzEmployeeWork")
    public ZjTzEmployeeWork printZjTzEmployeeWork(@RequestBody(required = false) ZjTzEmployeeWork zjTzEmployeeWork) {
        return zjTzEmployeeWorkService.printZjTzEmployeeWork(zjTzEmployeeWork);
    }

}