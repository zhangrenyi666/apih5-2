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
import com.apih5.mybatis.pojo.ZjTzSupReport;
import com.apih5.service.ZjTzSupReportService;

@RestController
public class ZjTzSupReportController {

    @Autowired(required = true)
    private ZjTzSupReportService zjTzSupReportService;

    @ApiOperation(value="查询上报人员", notes="查询上报人员")
    @ApiImplicitParam(name = "zjTzSupReport", value = "上报人员entity", dataType = "ZjTzSupReport")
    @RequireToken
    @PostMapping("/getZjTzSupReportList")
    public ResponseEntity getZjTzSupReportList(@RequestBody(required = false) ZjTzSupReport zjTzSupReport) {
        return zjTzSupReportService.getZjTzSupReportListByCondition(zjTzSupReport);
    }

    @ApiOperation(value="查询详情上报人员", notes="查询详情上报人员")
    @ApiImplicitParam(name = "zjTzSupReport", value = "上报人员entity", dataType = "ZjTzSupReport")
    @RequireToken
    @PostMapping("/getZjTzSupReportDetails")
    public ResponseEntity getZjTzSupReportDetails(@RequestBody(required = false) ZjTzSupReport zjTzSupReport) {
        return zjTzSupReportService.getZjTzSupReportDetails(zjTzSupReport);
    }

    @ApiOperation(value="新增上报人员", notes="新增上报人员")
    @ApiImplicitParam(name = "zjTzSupReport", value = "上报人员entity", dataType = "ZjTzSupReport")
    @RequireToken
    @PostMapping("/addZjTzSupReport")
    public ResponseEntity addZjTzSupReport(@RequestBody(required = false) ZjTzSupReport zjTzSupReport) {
        return zjTzSupReportService.saveZjTzSupReport(zjTzSupReport);
    }

    @ApiOperation(value="更新上报人员", notes="更新上报人员")
    @ApiImplicitParam(name = "zjTzSupReport", value = "上报人员entity", dataType = "ZjTzSupReport")
    @RequireToken
    @PostMapping("/updateZjTzSupReport")
    public ResponseEntity updateZjTzSupReport(@RequestBody(required = false) ZjTzSupReport zjTzSupReport) {
        return zjTzSupReportService.updateZjTzSupReport(zjTzSupReport);
    }

    @ApiOperation(value="删除上报人员", notes="删除上报人员")
    @ApiImplicitParam(name = "zjTzSupReportList", value = "上报人员List", dataType = "List<ZjTzSupReport>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjTzSupReport")
    public ResponseEntity batchDeleteUpdateZjTzSupReport(@RequestBody(required = false) List<ZjTzSupReport> zjTzSupReportList) {
        return zjTzSupReportService.batchDeleteUpdateZjTzSupReport(zjTzSupReportList);
    }

}
