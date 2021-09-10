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
import com.apih5.mybatis.pojo.ZjXmCqjxProjectEvaluationApproval;
import com.apih5.service.ZjXmCqjxProjectEvaluationApprovalService;

@RestController
public class ZjXmCqjxProjectEvaluationApprovalController {

    @Autowired(required = true)
    private ZjXmCqjxProjectEvaluationApprovalService zjXmCqjxProjectEvaluationApprovalService;

    @ApiOperation(value="查询重庆绩效项目总体评价评分", notes="查询重庆绩效项目总体评价评分")
    @ApiImplicitParam(name = "zjXmCqjxProjectEvaluationApproval", value = "重庆绩效项目总体评价评分entity", dataType = "ZjXmCqjxProjectEvaluationApproval")
    @RequireToken
    @PostMapping("/getZjXmCqjxProjectEvaluationApprovalList")
    public ResponseEntity getZjXmCqjxProjectEvaluationApprovalList(@RequestBody(required = false) ZjXmCqjxProjectEvaluationApproval zjXmCqjxProjectEvaluationApproval) {
        return zjXmCqjxProjectEvaluationApprovalService.getZjXmCqjxProjectEvaluationApprovalListByCondition(zjXmCqjxProjectEvaluationApproval);
    }

    @ApiOperation(value="查询详情重庆绩效项目总体评价评分", notes="查询详情重庆绩效项目总体评价评分")
    @ApiImplicitParam(name = "zjXmCqjxProjectEvaluationApproval", value = "重庆绩效项目总体评价评分entity", dataType = "ZjXmCqjxProjectEvaluationApproval")
    @RequireToken
    @PostMapping("/getZjXmCqjxProjectEvaluationApprovalDetails")
    public ResponseEntity getZjXmCqjxProjectEvaluationApprovalDetails(@RequestBody(required = false) ZjXmCqjxProjectEvaluationApproval zjXmCqjxProjectEvaluationApproval) {
        return zjXmCqjxProjectEvaluationApprovalService.getZjXmCqjxProjectEvaluationApprovalDetails(zjXmCqjxProjectEvaluationApproval);
    }

    @ApiOperation(value="新增重庆绩效项目总体评价评分", notes="新增重庆绩效项目总体评价评分")
    @ApiImplicitParam(name = "zjXmCqjxProjectEvaluationApproval", value = "重庆绩效项目总体评价评分entity", dataType = "ZjXmCqjxProjectEvaluationApproval")
    @RequireToken
    @PostMapping("/addZjXmCqjxProjectEvaluationApproval")
    public ResponseEntity addZjXmCqjxProjectEvaluationApproval(@RequestBody(required = false) ZjXmCqjxProjectEvaluationApproval zjXmCqjxProjectEvaluationApproval) {
        return zjXmCqjxProjectEvaluationApprovalService.saveZjXmCqjxProjectEvaluationApproval(zjXmCqjxProjectEvaluationApproval);
    }

    @ApiOperation(value="更新重庆绩效项目总体评价评分", notes="更新重庆绩效项目总体评价评分")
    @ApiImplicitParam(name = "zjXmCqjxProjectEvaluationApproval", value = "重庆绩效项目总体评价评分entity", dataType = "ZjXmCqjxProjectEvaluationApproval")
    @RequireToken
    @PostMapping("/updateZjXmCqjxProjectEvaluationApproval")
    public ResponseEntity updateZjXmCqjxProjectEvaluationApproval(@RequestBody(required = false) ZjXmCqjxProjectEvaluationApproval zjXmCqjxProjectEvaluationApproval) {
        return zjXmCqjxProjectEvaluationApprovalService.updateZjXmCqjxProjectEvaluationApproval(zjXmCqjxProjectEvaluationApproval);
    }

    @ApiOperation(value="删除重庆绩效项目总体评价评分", notes="删除重庆绩效项目总体评价评分")
    @ApiImplicitParam(name = "zjXmCqjxProjectEvaluationApprovalList", value = "重庆绩效项目总体评价评分List", dataType = "List<ZjXmCqjxProjectEvaluationApproval>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjXmCqjxProjectEvaluationApproval")
    public ResponseEntity batchDeleteUpdateZjXmCqjxProjectEvaluationApproval(@RequestBody(required = false) List<ZjXmCqjxProjectEvaluationApproval> zjXmCqjxProjectEvaluationApprovalList) {
        return zjXmCqjxProjectEvaluationApprovalService.batchDeleteUpdateZjXmCqjxProjectEvaluationApproval(zjXmCqjxProjectEvaluationApprovalList);
    }

}
