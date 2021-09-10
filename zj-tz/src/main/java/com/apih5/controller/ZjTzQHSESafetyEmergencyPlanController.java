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
import com.apih5.mybatis.pojo.ZjTzSafetyEmergencyPlan;
import com.apih5.service.ZjTzSafetyEmergencyPlanService;

@RestController
public class ZjTzQHSESafetyEmergencyPlanController {

    @Autowired(required = true)
    private ZjTzSafetyEmergencyPlanService zjTzSafetyEmergencyPlanService;

    @ApiOperation(value="查询安全应急预案", notes="查询安全应急预案")
    @ApiImplicitParam(name = "zjTzSafetyEmergencyPlan", value = "安全应急预案entity", dataType = "ZjTzSafetyEmergencyPlan")
    @RequireToken
    @PostMapping("/getZjTzSafetyEmergencyPlanList")
    public ResponseEntity getZjTzSafetyEmergencyPlanList(@RequestBody(required = false) ZjTzSafetyEmergencyPlan zjTzSafetyEmergencyPlan) {
        return zjTzSafetyEmergencyPlanService.getZjTzSafetyEmergencyPlanListByCondition(zjTzSafetyEmergencyPlan);
    }

    @ApiOperation(value="查询详情安全应急预案", notes="查询详情安全应急预案")
    @ApiImplicitParam(name = "zjTzSafetyEmergencyPlan", value = "安全应急预案entity", dataType = "ZjTzSafetyEmergencyPlan")
    @RequireToken
    @PostMapping("/getZjTzSafetyEmergencyPlanDetails")
    public ResponseEntity getZjTzSafetyEmergencyPlanDetails(@RequestBody(required = false) ZjTzSafetyEmergencyPlan zjTzSafetyEmergencyPlan) {
        return zjTzSafetyEmergencyPlanService.getZjTzSafetyEmergencyPlanDetails(zjTzSafetyEmergencyPlan);
    }

    @ApiOperation(value="新增安全应急预案", notes="新增安全应急预案")
    @ApiImplicitParam(name = "zjTzSafetyEmergencyPlan", value = "安全应急预案entity", dataType = "ZjTzSafetyEmergencyPlan")
    @RequireToken
    @PostMapping("/addZjTzSafetyEmergencyPlan")
    public ResponseEntity addZjTzSafetyEmergencyPlan(@RequestBody(required = false) ZjTzSafetyEmergencyPlan zjTzSafetyEmergencyPlan) {
        return zjTzSafetyEmergencyPlanService.saveZjTzSafetyEmergencyPlan(zjTzSafetyEmergencyPlan);
    }

    @ApiOperation(value="更新安全应急预案", notes="更新安全应急预案")
    @ApiImplicitParam(name = "zjTzSafetyEmergencyPlan", value = "安全应急预案entity", dataType = "ZjTzSafetyEmergencyPlan")
    @RequireToken
    @PostMapping("/updateZjTzSafetyEmergencyPlan")
    public ResponseEntity updateZjTzSafetyEmergencyPlan(@RequestBody(required = false) ZjTzSafetyEmergencyPlan zjTzSafetyEmergencyPlan) {
        return zjTzSafetyEmergencyPlanService.updateZjTzSafetyEmergencyPlan(zjTzSafetyEmergencyPlan);
    }
    
    @ApiOperation(value="新增安全应急预案（增加附件）", notes="新增安全应急预案（增加附件）")
    @ApiImplicitParam(name = "zjTzSafetyEmergencyPlan", value = "安全应急预案entity", dataType = "ZjTzSafetyEmergencyPlan")
    @RequireToken
    @PostMapping("/addZjTzSafetyEmergencyPlanAddFile")
    public ResponseEntity addZjTzSafetyEmergencyPlanAddFile(@RequestBody(required = false) ZjTzSafetyEmergencyPlan zjTzSafetyEmergencyPlan) {
    	return zjTzSafetyEmergencyPlanService.saveZjTzSafetyEmergencyPlanAddFile(zjTzSafetyEmergencyPlan);
    }
    
    @ApiOperation(value="更新安全应急预案（增加附件）", notes="更新安全应急预案（增加附件）")
    @ApiImplicitParam(name = "zjTzSafetyEmergencyPlan", value = "安全应急预案entity", dataType = "ZjTzSafetyEmergencyPlan")
    @RequireToken
    @PostMapping("/updateZjTzSafetyEmergencyPlanAddFile")
    public ResponseEntity updateZjTzSafetyEmergencyPlanAddFile(@RequestBody(required = false) ZjTzSafetyEmergencyPlan zjTzSafetyEmergencyPlan) {
    	return zjTzSafetyEmergencyPlanService.updateZjTzSafetyEmergencyPlanAddFile(zjTzSafetyEmergencyPlan);
    }

    @ApiOperation(value="删除安全应急预案", notes="删除安全应急预案")
    @ApiImplicitParam(name = "zjTzSafetyEmergencyPlanList", value = "安全应急预案List", dataType = "List<ZjTzSafetyEmergencyPlan>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjTzSafetyEmergencyPlan")
    public ResponseEntity batchDeleteUpdateZjTzSafetyEmergencyPlan(@RequestBody(required = false) List<ZjTzSafetyEmergencyPlan> zjTzSafetyEmergencyPlanList) {
        return zjTzSafetyEmergencyPlanService.batchDeleteUpdateZjTzSafetyEmergencyPlan(zjTzSafetyEmergencyPlanList);
    }

}
