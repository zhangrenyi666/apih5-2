package com.apih5.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import com.apih5.framework.annotation.RequireToken;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjTzComplianceDetail;
import com.apih5.service.ZjTzComplianceDetailService;

@RestController
public class ZjTzComplianceDetailController {

    @Autowired(required = true)
    private ZjTzComplianceDetailService zjTzComplianceDetailService;

    @ApiOperation(value="查询合规明细", notes="查询合规明细")
    @ApiImplicitParam(name = "zjTzComplianceDetail", value = "合规明细entity", dataType = "ZjTzComplianceDetail")
    @RequireToken
    @PostMapping("/getZjTzComplianceDetailList")
    public ResponseEntity getZjTzComplianceDetailList(@RequestBody(required = false) ZjTzComplianceDetail zjTzComplianceDetail) {
        return zjTzComplianceDetailService.getZjTzComplianceDetailListByCondition(zjTzComplianceDetail);
    }

    @ApiOperation(value="查询详情合规明细", notes="查询详情合规明细")
    @ApiImplicitParam(name = "zjTzComplianceDetail", value = "合规明细entity", dataType = "ZjTzComplianceDetail")
    @RequireToken
    @PostMapping("/getZjTzComplianceDetailDetails")
    public ResponseEntity getZjTzComplianceDetailDetails(@RequestBody(required = false) ZjTzComplianceDetail zjTzComplianceDetail) {
        return zjTzComplianceDetailService.getZjTzComplianceDetailDetails(zjTzComplianceDetail);
    }

    @ApiOperation(value="新增合规明细", notes="新增合规明细")
    @ApiImplicitParam(name = "zjTzComplianceDetail", value = "合规明细entity", dataType = "ZjTzComplianceDetail")
    @RequireToken
    @PostMapping("/addZjTzComplianceDetail")
    public ResponseEntity addZjTzComplianceDetail(@RequestBody(required = false) ZjTzComplianceDetail zjTzComplianceDetail) {
        return zjTzComplianceDetailService.saveZjTzComplianceDetail(zjTzComplianceDetail);
    }

    @ApiOperation(value="更新合规明细", notes="更新合规明细")
    @ApiImplicitParam(name = "zjTzComplianceDetail", value = "合规明细entity", dataType = "ZjTzComplianceDetail")
    @RequireToken
    @PostMapping("/updateZjTzComplianceDetail")
    public ResponseEntity updateZjTzComplianceDetail(@RequestBody(required = false) ZjTzComplianceDetail zjTzComplianceDetail) {
        return zjTzComplianceDetailService.updateZjTzComplianceDetail(zjTzComplianceDetail);
    }

    @ApiOperation(value="删除合规明细", notes="删除合规明细")
    @ApiImplicitParam(name = "zjTzComplianceDetailList", value = "合规明细List", dataType = "List<ZjTzComplianceDetail>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjTzComplianceDetail")
    public ResponseEntity batchDeleteUpdateZjTzComplianceDetail(@RequestBody(required = false) List<ZjTzComplianceDetail> zjTzComplianceDetailList) {
        return zjTzComplianceDetailService.batchDeleteUpdateZjTzComplianceDetail(zjTzComplianceDetailList);
    }

    
    @ApiOperation(value="查询合规明细", notes="查询合规明细")
    @ApiImplicitParam(name = "zjTzComplianceDetail", value = "合规明细entity", dataType = "ZjTzComplianceDetail")
    @RequireToken
    @PostMapping("/getZjTzComplianceDetailListForReport")
    public ResponseEntity getZjTzComplianceDetailListForReport(@RequestBody(required = false) ZjTzComplianceDetail zjTzComplianceDetail) {
        return zjTzComplianceDetailService.getZjTzComplianceDetailListForReport(zjTzComplianceDetail);
    }
    
    @ApiOperation(value="报表导出合规明细列表", notes="报表导出合规明细列表")
    @ApiImplicitParam(name = "zjTzComplianceDetail", value = "合规明细entity", dataType = "ZjTzComplianceDetail")
    @PostMapping("/uReportZjTzComplianceDetailList")
    public List<ZjTzComplianceDetail> uReportZjTzComplianceDetailList(@RequestBody(required = false) ZjTzComplianceDetail zjTzComplianceDetail) {
        return zjTzComplianceDetailService.uReportZjTzComplianceDetailList(zjTzComplianceDetail);
    }
    
    @ApiOperation(value="导出合规明细", notes="导出合规明细")
    @ApiImplicitParam(name = "zjTzComplianceDetail", value = "合规明细entity", dataType = "ZjTzComplianceDetail")
//    @RequireToken
    @PostMapping("/exportZjTzComplianceDetailList")
    public ResponseEntity exportZjTzComplianceDetailList(@RequestBody(required = false) ZjTzComplianceDetail zjTzComplianceDetail, HttpServletResponse response) {
        return zjTzComplianceDetailService.exportZjTzComplianceDetailList(zjTzComplianceDetail, response);
    }
}