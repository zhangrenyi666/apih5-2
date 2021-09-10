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
import com.apih5.mybatis.pojo.ZjTzRiskAnalysis;
import com.apih5.service.ZjTzRiskAnalysisService;

@RestController
public class ZjTzRiskAnalysisController {

    @Autowired(required = true)
    private ZjTzRiskAnalysisService zjTzRiskAnalysisService;

    @ApiOperation(value="查询风险管理分析报告", notes="查询风险管理分析报告")
    @ApiImplicitParam(name = "zjTzRiskAnalysis", value = "风险管理分析报告entity", dataType = "ZjTzRiskAnalysis")
    @RequireToken
    @PostMapping("/getZjTzRiskAnalysisList")
    public ResponseEntity getZjTzRiskAnalysisList(@RequestBody(required = false) ZjTzRiskAnalysis zjTzRiskAnalysis) {
        return zjTzRiskAnalysisService.getZjTzRiskAnalysisListByCondition(zjTzRiskAnalysis);
    }

    @ApiOperation(value="查询详情风险管理分析报告", notes="查询详情风险管理分析报告")
    @ApiImplicitParam(name = "zjTzRiskAnalysis", value = "风险管理分析报告entity", dataType = "ZjTzRiskAnalysis")
    @RequireToken
    @PostMapping("/getZjTzRiskAnalysisDetails")
    public ResponseEntity getZjTzRiskAnalysisDetails(@RequestBody(required = false) ZjTzRiskAnalysis zjTzRiskAnalysis) {
        return zjTzRiskAnalysisService.getZjTzRiskAnalysisDetails(zjTzRiskAnalysis);
    }

    @ApiOperation(value="新增风险管理分析报告", notes="新增风险管理分析报告")
    @ApiImplicitParam(name = "zjTzRiskAnalysis", value = "风险管理分析报告entity", dataType = "ZjTzRiskAnalysis")
    @RequireToken
    @PostMapping("/addZjTzRiskAnalysis")
    public ResponseEntity addZjTzRiskAnalysis(@RequestBody(required = false) ZjTzRiskAnalysis zjTzRiskAnalysis) {
        return zjTzRiskAnalysisService.saveZjTzRiskAnalysis(zjTzRiskAnalysis);
    }

    @ApiOperation(value="更新风险管理分析报告", notes="更新风险管理分析报告")
    @ApiImplicitParam(name = "zjTzRiskAnalysis", value = "风险管理分析报告entity", dataType = "ZjTzRiskAnalysis")
    @RequireToken
    @PostMapping("/updateZjTzRiskAnalysis")
    public ResponseEntity updateZjTzRiskAnalysis(@RequestBody(required = false) ZjTzRiskAnalysis zjTzRiskAnalysis) {
        return zjTzRiskAnalysisService.updateZjTzRiskAnalysis(zjTzRiskAnalysis);
    }

    @ApiOperation(value="删除风险管理分析报告", notes="删除风险管理分析报告")
    @ApiImplicitParam(name = "zjTzRiskAnalysisList", value = "风险管理分析报告List", dataType = "List<ZjTzRiskAnalysis>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjTzRiskAnalysis")
    public ResponseEntity batchDeleteUpdateZjTzRiskAnalysis(@RequestBody(required = false) List<ZjTzRiskAnalysis> zjTzRiskAnalysisList) {
        return zjTzRiskAnalysisService.batchDeleteUpdateZjTzRiskAnalysis(zjTzRiskAnalysisList);
    }
    
    @ApiOperation(value="批量上报风险管理分析报告", notes="批量上报风险管理分析报告")
    @ApiImplicitParam(name = "zjTzRiskAnalysisList", value = "风险管理分析报告List", dataType = "List<ZjTzRiskAnalysis>")
    @RequireToken
    @PostMapping("/batchReleaseZjTzRiskAnalysis")
    public ResponseEntity batchReleaseZjTzRiskAnalysis(@RequestBody(required = false) List<ZjTzRiskAnalysis> zjTzRiskAnalysisList) {
        return zjTzRiskAnalysisService.batchReleaseZjTzRiskAnalysis(zjTzRiskAnalysisList);
    }

    @ApiOperation(value="批量退回风险管理分析报告", notes="批量退回风险管理分析报告")
    @ApiImplicitParam(name = "zjTzRiskAnalysisList", value = "风险管理分析报告List", dataType = "List<ZjTzRiskAnalysis>")
    @RequireToken
    @PostMapping("/batchRecallZjTzRiskAnalysis")
    public ResponseEntity batchRecallZjTzRiskAnalysis(@RequestBody(required = false) List<ZjTzRiskAnalysis> zjTzRiskAnalysisList) {
        return zjTzRiskAnalysisService.batchRecallZjTzRiskAnalysis(zjTzRiskAnalysisList);
    }
    
    @ApiOperation(value="批量导出风险管理分析报告附件", notes="批量导出风险管理分析报告附件")
    @ApiImplicitParam(name = "zjTzRiskAnalysisList", value = "风险管理分析报告List", dataType = "List<ZjTzRiskAnalysis>")
    @RequireToken
    @PostMapping("/batchExportZjTzRiskAnalysisFile")
    public ResponseEntity batchExportZjTzRiskAnalysisFile(@RequestBody(required = false) List<ZjTzRiskAnalysis> zjTzRiskAnalysisList) {
        return zjTzRiskAnalysisService.batchExportZjTzRiskAnalysisFile(zjTzRiskAnalysisList);
    }
}
