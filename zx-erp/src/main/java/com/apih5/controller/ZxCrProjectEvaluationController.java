package com.apih5.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.apih5.framework.annotation.RequireToken;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxCrProjectEvaluation;
import com.apih5.service.ZxCrProjectEvaluationService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@RestController
public class ZxCrProjectEvaluationController {

    @Autowired(required = true)
    private ZxCrProjectEvaluationService zxCrProjectEvaluationService;

    @ApiOperation(value="查询项目信用评价", notes="查询项目信用评价")
    @ApiImplicitParam(name = "zxCrProjectEvaluation", value = "项目信用评价entity", dataType = "ZxCrProjectEvaluation")
    @RequireToken
    @PostMapping("/getZxCrProjectEvaluationList")
    public ResponseEntity getZxCrProjectEvaluationList(@RequestBody(required = false) ZxCrProjectEvaluation zxCrProjectEvaluation) {
        return zxCrProjectEvaluationService.getZxCrProjectEvaluationListByCondition(zxCrProjectEvaluation);
    }

    @ApiOperation(value="查询详情项目信用评价", notes="查询详情项目信用评价")
    @ApiImplicitParam(name = "zxCrProjectEvaluation", value = "项目信用评价entity", dataType = "ZxCrProjectEvaluation")
    @RequireToken
    @PostMapping("/getZxCrProjectEvaluationDetail")
    public ResponseEntity getZxCrProjectEvaluationDetail(@RequestBody(required = false) ZxCrProjectEvaluation zxCrProjectEvaluation) {
        return zxCrProjectEvaluationService.getZxCrProjectEvaluationDetail(zxCrProjectEvaluation);
    }

    @ApiOperation(value="新增项目信用评价", notes="新增项目信用评价")
    @ApiImplicitParam(name = "zxCrProjectEvaluation", value = "项目信用评价entity", dataType = "ZxCrProjectEvaluation")
    @RequireToken
    @PostMapping("/addZxCrProjectEvaluation")
    public ResponseEntity addZxCrProjectEvaluation(@RequestBody(required = false) ZxCrProjectEvaluation zxCrProjectEvaluation) {
        return zxCrProjectEvaluationService.saveZxCrProjectEvaluation(zxCrProjectEvaluation);
    }

    @ApiOperation(value="更新项目信用评价", notes="更新项目信用评价")
    @ApiImplicitParam(name = "zxCrProjectEvaluation", value = "项目信用评价entity", dataType = "ZxCrProjectEvaluation")
    @RequireToken
    @PostMapping("/updateZxCrProjectEvaluation")
    public ResponseEntity updateZxCrProjectEvaluation(@RequestBody(required = false) ZxCrProjectEvaluation zxCrProjectEvaluation) {
        return zxCrProjectEvaluationService.updateZxCrProjectEvaluation(zxCrProjectEvaluation);
    }

    @ApiOperation(value="删除项目信用评价", notes="删除项目信用评价")
    @ApiImplicitParam(name = "zxCrProjectEvaluationList", value = "项目信用评价List", dataType = "List<ZxCrProjectEvaluation>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxCrProjectEvaluation")
    public ResponseEntity batchDeleteUpdateZxCrProjectEvaluation(@RequestBody(required = false) List<ZxCrProjectEvaluation> zxCrProjectEvaluationList) {
        return zxCrProjectEvaluationService.batchDeleteUpdateZxCrProjectEvaluation(zxCrProjectEvaluationList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓CustomerInfo
    
    //检索协作单位信息
    @ApiOperation(value="检索协作单位信息", notes="查询协作单位信息")
    @ApiImplicitParam(name = "zxCrCustomerInfo", value = "协作单位基础信息登记entity", dataType = "ZxCrCustomerInfo")
    @RequireToken
    @PostMapping("/getZxCrCustomerInfoListAll")
    public ResponseEntity getZxCrCustomerInfoList() {
        return zxCrProjectEvaluationService.getZxCrCustomerInfoList();
    }
    
    @ApiOperation(value="审核项目信用评价", notes="审核项目信用评价")
    @ApiImplicitParam(name = "zxCrProjectEvaluation", value = "项目信用评价entity", dataType = "ZxCrProjectEvaluation")
    @RequireToken
    @PostMapping("/updateAuditStatus")
    public ResponseEntity updateAuditStatus(@RequestBody(required = false) ZxCrProjectEvaluation zxCrProjectEvaluation) {
        return zxCrProjectEvaluationService.updateAuditStatusOne(zxCrProjectEvaluation);
    }
    
    @ApiOperation(value="反审核项目信用评价", notes="反审核项目信用评价")
    @ApiImplicitParam(name = "zxCrProjectEvaluation", value = "项目信用评价entity", dataType = "ZxCrProjectEvaluation")
    @RequireToken
    @PostMapping("/updateAuditStatusOut")
    public ResponseEntity updateAuditStatusOut(@RequestBody(required = false) ZxCrProjectEvaluation zxCrProjectEvaluation) {
        return zxCrProjectEvaluationService.updateAuditStatusOut(zxCrProjectEvaluation);
    }
    
    @ApiOperation(value="查询项目信用评价专业类别", notes="查询项目信用评价专业类别")
    @ApiImplicitParam(name = "zxCrProjectEvaluation", value = "项目信用评价entity", dataType = "ZxCrProjectEvaluation")
    @RequireToken
    @PostMapping("/getZxCrProjectEvaluationListCatName")
    public ResponseEntity getZxCrProjectEvaluationListCatName() {
        return zxCrProjectEvaluationService.getZxCrProjectEvaluationListByCatName();
    }
    
    @ApiOperation(value="查询项目信用评价分类", notes="查询项目信用评价分类")
    @ApiImplicitParam(name = "zxCrProjectEvaluation", value = "项目信用评价entity", dataType = "ZxCrProjectEvaluation")
    @RequireToken
    @PostMapping("/getZxCrProjectEvaluationListResName")
    public ResponseEntity getZxCrProjectEvaluationListResName(@RequestBody(required = false) ZxCrProjectEvaluation zxCrProjectEvaluation) {
        return zxCrProjectEvaluationService.getZxCrProjectEvaluationListByResName(zxCrProjectEvaluation);
    }
    
}
