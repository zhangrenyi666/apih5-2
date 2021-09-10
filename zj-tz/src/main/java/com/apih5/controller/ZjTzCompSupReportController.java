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
import com.apih5.mybatis.pojo.ZjTzCompSupReport;
import com.apih5.service.ZjTzCompSupReportService;

@RestController
public class ZjTzCompSupReportController {

    @Autowired(required = true)
    private ZjTzCompSupReportService zjTzCompSupReportService;

    @ApiOperation(value="查询综合督查报告", notes="查询综合督查报告")
    @ApiImplicitParam(name = "zjTzCompSupReport", value = "综合督查报告entity", dataType = "ZjTzCompSupReport")
    @RequireToken
    @PostMapping("/getZjTzCompSupReportList")
    public ResponseEntity getZjTzCompSupReportList(@RequestBody(required = false) ZjTzCompSupReport zjTzCompSupReport) {
        return zjTzCompSupReportService.getZjTzCompSupReportListByCondition(zjTzCompSupReport);
    }

    @ApiOperation(value="查询详情综合督查报告", notes="查询详情综合督查报告")
    @ApiImplicitParam(name = "zjTzCompSupReport", value = "综合督查报告entity", dataType = "ZjTzCompSupReport")
    @RequireToken
    @PostMapping("/getZjTzCompSupReportDetails")
    public ResponseEntity getZjTzCompSupReportDetails(@RequestBody(required = false) ZjTzCompSupReport zjTzCompSupReport) {
        return zjTzCompSupReportService.getZjTzCompSupReportDetails(zjTzCompSupReport);
    }

    @ApiOperation(value="新增综合督查报告", notes="新增综合督查报告")
    @ApiImplicitParam(name = "zjTzCompSupReport", value = "综合督查报告entity", dataType = "ZjTzCompSupReport")
    @RequireToken
    @PostMapping("/addZjTzCompSupReport")
    public ResponseEntity addZjTzCompSupReport(@RequestBody(required = false) ZjTzCompSupReport zjTzCompSupReport) {
        return zjTzCompSupReportService.saveZjTzCompSupReport(zjTzCompSupReport);
    }

    @ApiOperation(value="更新综合督查报告", notes="更新综合督查报告")
    @ApiImplicitParam(name = "zjTzCompSupReport", value = "综合督查报告entity", dataType = "ZjTzCompSupReport")
    @RequireToken
    @PostMapping("/updateZjTzCompSupReport")
    public ResponseEntity updateZjTzCompSupReport(@RequestBody(required = false) ZjTzCompSupReport zjTzCompSupReport) {
        return zjTzCompSupReportService.updateZjTzCompSupReport(zjTzCompSupReport);
    }

    @ApiOperation(value="删除综合督查报告", notes="删除综合督查报告")
    @ApiImplicitParam(name = "zjTzCompSupReportList", value = "综合督查报告List", dataType = "List<ZjTzCompSupReport>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjTzCompSupReport")
    public ResponseEntity batchDeleteUpdateZjTzCompSupReport(@RequestBody(required = false) List<ZjTzCompSupReport> zjTzCompSupReportList) {
        return zjTzCompSupReportService.batchDeleteUpdateZjTzCompSupReport(zjTzCompSupReportList);
    }
    
    @ApiOperation(value="批量撤回综合督查报告", notes="批量撤回综合督查报告")
    @ApiImplicitParam(name = "zjTzCompSupReportList", value = "综合督查报告List", dataType = "List<ZjTzCompSupReport>")
    @RequireToken
    @PostMapping("/batchRecallZjTzCompSupReport")
    public ResponseEntity batchRecallZjTzCompSupReport(@RequestBody(required = false) List<ZjTzCompSupReport> zjTzCompSupReportList) {
        return zjTzCompSupReportService.batchRecallZjTzCompSupReport(zjTzCompSupReportList);
    }
    
    @ApiOperation(value="整改下达综合督查报告", notes="整改下达综合督查报告")
    @ApiImplicitParam(name = "zjTzCompSupReport", value = "综合督查报告entity", dataType = "ZjTzCompSupReport")
    @RequireToken
    @PostMapping("/correctiveZjTzCompSupReport")
    public ResponseEntity correctiveZjTzCompSupReport(@RequestBody(required = false) ZjTzCompSupReport zjTzCompSupReport) {
        return zjTzCompSupReportService.correctiveZjTzCompSupReport(zjTzCompSupReport);
    }
    
    @ApiOperation(value="查询综合督查报告回复情况列表", notes="查询综合督查报告回复情况列表")
    @ApiImplicitParam(name = "zjTzCompSupReport", value = "综合督查报告entity", dataType = "ZjTzCompSupReport")
    @RequireToken
    @PostMapping("/getZjTzCompSupReportReplyList")
    public ResponseEntity getZjTzCompSupReportReplyList(@RequestBody(required = false) ZjTzCompSupReport zjTzCompSupReport) {
        return zjTzCompSupReportService.getZjTzCompSupReportReplyList(zjTzCompSupReport);
    }
    
    @ApiOperation(value="批量导出综合督查报告附件", notes="批量导出综合督查报告附件")
    @ApiImplicitParam(name = "zjTzCompSupReportList", value = "综合督查报告List", dataType = "List<ZjTzCompSupReport>")
    @RequireToken
    @PostMapping("/batchExportZjTzCompSupReportFile")
    public ResponseEntity batchExportZjTzCompSupReportFile(@RequestBody(required = false) List<ZjTzCompSupReport> zjTzCompSupReportList) {
        return zjTzCompSupReportService.batchExportZjTzCompSupReportFile(zjTzCompSupReportList);
    }
    
    @ApiOperation(value="编辑回复内容综合督查报告", notes="编辑回复内容综合督查报告")
    @ApiImplicitParam(name = "zjTzCompSupReport", value = "综合督查报告entity", dataType = "ZjTzCompSupReport")
    @RequireToken
    @PostMapping("/replyZjTzCompSupReport")
    public ResponseEntity replyZjTzCompSupReport(@RequestBody(required = false) ZjTzCompSupReport zjTzCompSupReport) {
        return zjTzCompSupReportService.replyZjTzCompSupReport(zjTzCompSupReport);
    }
    
    @ApiOperation(value="上报综合督查报告", notes="上报综合督查报告")
    @ApiImplicitParam(name = "zjTzCompSupReport", value = "综合督查报告entity", dataType = "ZjTzCompSupReport")
    @RequireToken
    @PostMapping("/reportZjTzCompSupReport")
    public ResponseEntity reportZjTzCompSupReport(@RequestBody(required = false) ZjTzCompSupReport zjTzCompSupReport) {
        return zjTzCompSupReportService.reportZjTzCompSupReport(zjTzCompSupReport);
    }


}
