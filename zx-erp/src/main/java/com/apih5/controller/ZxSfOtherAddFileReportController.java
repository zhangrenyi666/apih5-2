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
import com.apih5.mybatis.pojo.ZxSfOtherAddFileReport;
import com.apih5.service.ZxSfOtherAddFileReportService;

@RestController
public class ZxSfOtherAddFileReportController {

    @Autowired(required = true)
    private ZxSfOtherAddFileReportService zxSfOtherAddFileReportService;

    @ApiOperation(value="查询事故案例报告表", notes="查询事故案例报告表")
    @ApiImplicitParam(name = "zxSfOtherAddFileReport", value = "事故案例报告表entity", dataType = "ZxSfOtherAddFileReport")
    @RequireToken
    @PostMapping("/getZxSfOtherAddFileReportList")
    public ResponseEntity getZxSfOtherAddFileReportList(@RequestBody(required = false) ZxSfOtherAddFileReport zxSfOtherAddFileReport) {
        return zxSfOtherAddFileReportService.getZxSfOtherAddFileReportListByCondition(zxSfOtherAddFileReport);
    }

    @ApiOperation(value="查询详情事故案例报告表", notes="查询详情事故案例报告表")
    @ApiImplicitParam(name = "zxSfOtherAddFileReport", value = "事故案例报告表entity", dataType = "ZxSfOtherAddFileReport")
    @RequireToken
    @PostMapping("/getZxSfOtherAddFileReportDetail")
    public ResponseEntity getZxSfOtherAddFileReportDetail(@RequestBody(required = false) ZxSfOtherAddFileReport zxSfOtherAddFileReport) {
        return zxSfOtherAddFileReportService.getZxSfOtherAddFileReportDetail(zxSfOtherAddFileReport);
    }

    @ApiOperation(value="新增事故案例报告表", notes="新增事故案例报告表")
    @ApiImplicitParam(name = "zxSfOtherAddFileReport", value = "事故案例报告表entity", dataType = "ZxSfOtherAddFileReport")
    @RequireToken
    @PostMapping("/addZxSfOtherAddFileReport")
    public ResponseEntity addZxSfOtherAddFileReport(@RequestBody(required = false) ZxSfOtherAddFileReport zxSfOtherAddFileReport) {
        return zxSfOtherAddFileReportService.saveZxSfOtherAddFileReport(zxSfOtherAddFileReport);
    }

    @ApiOperation(value="更新事故案例报告表", notes="更新事故案例报告表")
    @ApiImplicitParam(name = "zxSfOtherAddFileReport", value = "事故案例报告表entity", dataType = "ZxSfOtherAddFileReport")
    @RequireToken
    @PostMapping("/updateZxSfOtherAddFileReport")
    public ResponseEntity updateZxSfOtherAddFileReport(@RequestBody(required = false) ZxSfOtherAddFileReport zxSfOtherAddFileReport) {
        return zxSfOtherAddFileReportService.updateZxSfOtherAddFileReport(zxSfOtherAddFileReport);
    }

    @ApiOperation(value="删除事故案例报告表", notes="删除事故案例报告表")
    @ApiImplicitParam(name = "zxSfOtherAddFileReportList", value = "事故案例报告表List", dataType = "List<ZxSfOtherAddFileReport>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxSfOtherAddFileReport")
    public ResponseEntity batchDeleteUpdateZxSfOtherAddFileReport(@RequestBody(required = false) List<ZxSfOtherAddFileReport> zxSfOtherAddFileReportList) {
        return zxSfOtherAddFileReportService.batchDeleteUpdateZxSfOtherAddFileReport(zxSfOtherAddFileReportList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
