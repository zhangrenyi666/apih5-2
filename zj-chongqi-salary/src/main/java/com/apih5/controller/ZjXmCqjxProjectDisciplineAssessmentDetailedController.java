package com.apih5.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.apih5.framework.annotation.RequireToken;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjXmCqjxProjectDisciplineAssessmentDetailed;
import com.apih5.service.ZjXmCqjxProjectDisciplineAssessmentDetailedService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@RestController
public class ZjXmCqjxProjectDisciplineAssessmentDetailedController {

    @Autowired(required = true)
    private ZjXmCqjxProjectDisciplineAssessmentDetailedService zjXmCqjxProjectDisciplineAssessmentDetailedService;

    @ApiOperation(value="查询项目员工纪律性考核明细", notes="查询项目员工纪律性考核明细")
    @ApiImplicitParam(name = "zjXmCqjxProjectDisciplineAssessmentDetailed", value = "项目员工纪律性考核明细entity", dataType = "ZjXmCqjxProjectDisciplineAssessmentDetailed")
    @RequireToken
    @PostMapping("/getZjXmCqjxProjectDisciplineAssessmentDetailedList")
    public ResponseEntity getZjXmCqjxProjectDisciplineAssessmentDetailedList(@RequestBody(required = false) ZjXmCqjxProjectDisciplineAssessmentDetailed zjXmCqjxProjectDisciplineAssessmentDetailed) {
        return zjXmCqjxProjectDisciplineAssessmentDetailedService.getZjXmCqjxProjectDisciplineAssessmentDetailedListByCondition(zjXmCqjxProjectDisciplineAssessmentDetailed);
    }

    @ApiOperation(value="查询详情项目员工纪律性考核明细", notes="查询详情项目员工纪律性考核明细")
    @ApiImplicitParam(name = "zjXmCqjxProjectDisciplineAssessmentDetailed", value = "项目员工纪律性考核明细entity", dataType = "ZjXmCqjxProjectDisciplineAssessmentDetailed")
    @RequireToken
    @PostMapping("/getZjXmCqjxProjectDisciplineAssessmentDetailedDetails")
    public ResponseEntity getZjXmCqjxProjectDisciplineAssessmentDetailedDetails(@RequestBody(required = false) ZjXmCqjxProjectDisciplineAssessmentDetailed zjXmCqjxProjectDisciplineAssessmentDetailed) {
        return zjXmCqjxProjectDisciplineAssessmentDetailedService.getZjXmCqjxProjectDisciplineAssessmentDetailedDetails(zjXmCqjxProjectDisciplineAssessmentDetailed);
    }

    @ApiOperation(value="新增项目员工纪律性考核明细", notes="新增项目员工纪律性考核明细")
    @ApiImplicitParam(name = "zjXmCqjxProjectDisciplineAssessmentDetailed", value = "项目员工纪律性考核明细entity", dataType = "ZjXmCqjxProjectDisciplineAssessmentDetailed")
    @RequireToken
    @PostMapping("/addZjXmCqjxProjectDisciplineAssessmentDetailed")
    public ResponseEntity addZjXmCqjxProjectDisciplineAssessmentDetailed(@RequestBody(required = false) ZjXmCqjxProjectDisciplineAssessmentDetailed zjXmCqjxProjectDisciplineAssessmentDetailed) {
        return zjXmCqjxProjectDisciplineAssessmentDetailedService.saveZjXmCqjxProjectDisciplineAssessmentDetailed(zjXmCqjxProjectDisciplineAssessmentDetailed);
    }

    @ApiOperation(value="更新项目员工纪律性考核明细", notes="更新项目员工纪律性考核明细")
    @ApiImplicitParam(name = "zjXmCqjxProjectDisciplineAssessmentDetailed", value = "项目员工纪律性考核明细entity", dataType = "ZjXmCqjxProjectDisciplineAssessmentDetailed")
    @RequireToken
    @PostMapping("/updateZjXmCqjxProjectDisciplineAssessmentDetailed")
    public ResponseEntity updateZjXmCqjxProjectDisciplineAssessmentDetailed(@RequestBody(required = false) ZjXmCqjxProjectDisciplineAssessmentDetailed zjXmCqjxProjectDisciplineAssessmentDetailed) {
        return zjXmCqjxProjectDisciplineAssessmentDetailedService.updateZjXmCqjxProjectDisciplineAssessmentDetailed(zjXmCqjxProjectDisciplineAssessmentDetailed);
    }

    @ApiOperation(value="删除项目员工纪律性考核明细", notes="删除项目员工纪律性考核明细")
    @ApiImplicitParam(name = "zjXmCqjxProjectDisciplineAssessmentDetailedList", value = "项目员工纪律性考核明细List", dataType = "List<ZjXmCqjxProjectDisciplineAssessmentDetailed>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjXmCqjxProjectDisciplineAssessmentDetailed")
    public ResponseEntity batchDeleteUpdateZjXmCqjxProjectDisciplineAssessmentDetailed(@RequestBody(required = false) List<ZjXmCqjxProjectDisciplineAssessmentDetailed> zjXmCqjxProjectDisciplineAssessmentDetailedList) {
        return zjXmCqjxProjectDisciplineAssessmentDetailedService.batchDeleteUpdateZjXmCqjxProjectDisciplineAssessmentDetailed(zjXmCqjxProjectDisciplineAssessmentDetailedList);
    }
}