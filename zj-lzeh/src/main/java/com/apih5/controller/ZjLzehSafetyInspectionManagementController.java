package com.apih5.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.apih5.framework.annotation.RequireToken;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjLzehSafetyInspectionManagement;
import com.apih5.service.ZjLzehSafetyInspectionManagementService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@RestController
public class ZjLzehSafetyInspectionManagementController {

    @Autowired(required = true)
    private ZjLzehSafetyInspectionManagementService zjLzehSafetyInspectionManagementService;

    @ApiOperation(value="查询安全检查管理", notes="查询安全检查管理")
    @ApiImplicitParam(name = "zjLzehSafetyInspectionManagement", value = "安全检查管理entity", dataType = "ZjLzehSafetyInspectionManagement")
    @RequireToken
    @PostMapping("/getZjLzehSafetyInspectionManagementList")
    public ResponseEntity getZjLzehSafetyInspectionManagementList(@RequestBody(required = false) ZjLzehSafetyInspectionManagement zjLzehSafetyInspectionManagement) {
        return zjLzehSafetyInspectionManagementService.getZjLzehSafetyInspectionManagementListByCondition(zjLzehSafetyInspectionManagement);
    }

    @ApiOperation(value="查询详情安全检查管理", notes="查询详情安全检查管理")
    @ApiImplicitParam(name = "zjLzehSafetyInspectionManagement", value = "安全检查管理entity", dataType = "ZjLzehSafetyInspectionManagement")
    @RequireToken
    @PostMapping("/getZjLzehSafetyInspectionManagementDetails")
    public ResponseEntity getZjLzehSafetyInspectionManagementDetails(@RequestBody(required = false) ZjLzehSafetyInspectionManagement zjLzehSafetyInspectionManagement) {
        return zjLzehSafetyInspectionManagementService.getZjLzehSafetyInspectionManagementDetails(zjLzehSafetyInspectionManagement);
    }

    @ApiOperation(value="新增安全检查管理", notes="新增安全检查管理")
    @ApiImplicitParam(name = "zjLzehSafetyInspectionManagement", value = "安全检查管理entity", dataType = "ZjLzehSafetyInspectionManagement")
    @RequireToken
    @PostMapping("/addZjLzehSafetyInspectionManagement")
    public ResponseEntity addZjLzehSafetyInspectionManagement(@RequestBody(required = false) ZjLzehSafetyInspectionManagement zjLzehSafetyInspectionManagement) {
        return zjLzehSafetyInspectionManagementService.saveZjLzehSafetyInspectionManagement(zjLzehSafetyInspectionManagement);
    }

    @ApiOperation(value="更新安全检查管理", notes="更新安全检查管理")
    @ApiImplicitParam(name = "zjLzehSafetyInspectionManagement", value = "安全检查管理entity", dataType = "ZjLzehSafetyInspectionManagement")
    @RequireToken
    @PostMapping("/updateZjLzehSafetyInspectionManagement")
    public ResponseEntity updateZjLzehSafetyInspectionManagement(@RequestBody(required = false) ZjLzehSafetyInspectionManagement zjLzehSafetyInspectionManagement) {
        return zjLzehSafetyInspectionManagementService.updateZjLzehSafetyInspectionManagement(zjLzehSafetyInspectionManagement);
    }

    @ApiOperation(value="删除安全检查管理", notes="删除安全检查管理")
    @ApiImplicitParam(name = "zjLzehSafetyInspectionManagementList", value = "安全检查管理List", dataType = "List<ZjLzehSafetyInspectionManagement>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjLzehSafetyInspectionManagement")
    public ResponseEntity batchDeleteUpdateZjLzehSafetyInspectionManagement(@RequestBody(required = false) List<ZjLzehSafetyInspectionManagement> zjLzehSafetyInspectionManagementList) {
        return zjLzehSafetyInspectionManagementService.batchDeleteUpdateZjLzehSafetyInspectionManagement(zjLzehSafetyInspectionManagementList);
    }
    
    @ApiOperation(value="查询安全检查管理完成数及总数", notes="查询安全检查管理完成数及总数")
    @ApiImplicitParam(name = "zjLzehSafetyInspectionManagement", value = "安全检查管理", dataType = "ZjLzehSafetyInspectionManagement")
    @RequireToken
    @PostMapping("/getZjLzehSafetyInspectionManagementBySumDangerType")
    public ResponseEntity getZjLzehSafetyInspectionManagementBySumDangerType(@RequestBody(required = false) ZjLzehSafetyInspectionManagement zjLzehSafetyInspectionManagement) {
    	return zjLzehSafetyInspectionManagementService.getZjLzehSafetyInspectionManagementBySumDangerType(zjLzehSafetyInspectionManagement);
    }
   
    @ApiOperation(value="查询安全检查统计已解决数及未解决数", notes="查询安全检查统计已解决数及未解决数")
    @ApiImplicitParam(name = "zjLzehSafetyInspectionManagement", value = "安全检查统计", dataType = "ZjLzehSafetyInspectionManagement")
    @RequireToken
    @PostMapping("/getZjLzehSafetyInspectionManagementBySumDangerLevel")
    public ResponseEntity getZjLzehSafetyInspectionManagementBySumDangerLevel(@RequestBody(required = false) ZjLzehSafetyInspectionManagement zjLzehSafetyInspectionManagement) {
    	return zjLzehSafetyInspectionManagementService.getZjLzehSafetyInspectionManagementBySumDangerLevel(zjLzehSafetyInspectionManagement);
    }

    @ApiOperation(value="查询质量检查数据已解决数及未解决数", notes="查询质量检查数据已解决数及未解决数")
    @ApiImplicitParam(name = "zjLzehSafetyInspectionManagement", value = "质量检查数据", dataType = "ZjLzehSafetyInspectionManagement")
    @RequireToken
    @PostMapping("/getZjLzehSafetyInspectionManagementBySumTroubleLevel")
    public ResponseEntity getZjLzehSafetyInspectionManagementBySumTroubleLevel(@RequestBody(required = false) ZjLzehSafetyInspectionManagement zjLzehSafetyInspectionManagement) {
    	return zjLzehSafetyInspectionManagementService.getZjLzehSafetyInspectionManagementBySumTroubleLevel(zjLzehSafetyInspectionManagement);
    }
    @ApiOperation(value="数据中心（大屏）动态信息包含质量、安全、进度", notes="数据中心（大屏）动态信息包含质量、安全、进度")
    @ApiImplicitParam(name = "zjLzehSafetyInspectionManagementList", value = "安全检查管理List", dataType = "List<ZjLzehSafetyInspectionManagement>")
//    @RequireToken
    @PostMapping("/getZjLzehDataCenterDynamicInfo")
    public ResponseEntity getZjLzehDataCenterDynamicInfo(@RequestBody(required = false) ZjLzehSafetyInspectionManagement zjLzehSafetyInspectionManagement) {
    	return zjLzehSafetyInspectionManagementService.getZjLzehDataCenterDynamicInfo(zjLzehSafetyInspectionManagement);
    }

    @ApiOperation(value="导出质量整改回复", notes="导出质量整改回复")
    @ApiImplicitParam(name = "zjLzehSafetyInspectionManagement", value = "质量检查数据", dataType = "ZjLzehSafetyInspectionManagement")
    @RequireToken
    @PostMapping("/exportZjLzehQualityRectificatReply")
    public ResponseEntity exportZjLzehQualityRectificatReply(@RequestBody(required = false) ZjLzehSafetyInspectionManagement zjLzehSafetyInspectionManagement) {
    	return zjLzehSafetyInspectionManagementService.exportZjLzehQualityRectificatReply(zjLzehSafetyInspectionManagement);
    }
    
    @ApiOperation(value="导出质量整改单", notes="导出质量整改单")
    @ApiImplicitParam(name = "zjLzehSafetyInspectionManagement", value = "质量检查数据", dataType = "ZjLzehSafetyInspectionManagement")
    @RequireToken
    @PostMapping("/exportZjLzehQualityRectificatSheet")
    public ResponseEntity exportZjLzehQualityRectificatSheet(@RequestBody(required = false) ZjLzehSafetyInspectionManagement zjLzehSafetyInspectionManagement) {
    	return zjLzehSafetyInspectionManagementService.exportZjLzehQualityRectificatSheet(zjLzehSafetyInspectionManagement);
    }
    
    @ApiOperation(value="导出隐患整改回复单", notes="导出隐患整改回复单")
    @ApiImplicitParam(name = "zjLzehSafetyInspectionManagement", value = "质量检查数据", dataType = "ZjLzehSafetyInspectionManagement")
    @RequireToken
    @PostMapping("/exportZjLzehHiddenDangerRectificatReply")
    public ResponseEntity exportZjLzehHiddenDangerRectificatReply(@RequestBody(required = false) ZjLzehSafetyInspectionManagement zjLzehSafetyInspectionManagement) {
    	return zjLzehSafetyInspectionManagementService.exportZjLzehHiddenDangerRectificatReply(zjLzehSafetyInspectionManagement);
    }
    
    @ApiOperation(value="导出隐患整改指令书", notes="导出隐患整改指令书")
    @ApiImplicitParam(name = "zjLzehSafetyInspectionManagement", value = "质量检查数据", dataType = "ZjLzehSafetyInspectionManagement")
    @RequireToken
    @PostMapping("/exportZjLzehHiddenDangerRectificatInstructBook")
    public ResponseEntity exportZjLzehHiddenDangerRectificatInstructBook(@RequestBody(required = false) ZjLzehSafetyInspectionManagement zjLzehSafetyInspectionManagement) {
    	return zjLzehSafetyInspectionManagementService.exportZjLzehHiddenDangerRectificatInstructBook(zjLzehSafetyInspectionManagement);
    }
}
