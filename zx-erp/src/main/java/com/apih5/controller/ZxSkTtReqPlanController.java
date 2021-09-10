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
import com.apih5.mybatis.pojo.ZxSkTtReqPlan;
import com.apih5.service.ZxSkTtReqPlanService;

@RestController
public class ZxSkTtReqPlanController {

    @Autowired(required = true)
    private ZxSkTtReqPlanService zxSkTtReqPlanService;

    @ApiOperation(value="查询物资总需用计划", notes="查询物资总需用计划")
    @ApiImplicitParam(name = "zxSkTtReqPlan", value = "物资总需用计划entity", dataType = "ZxSkTtReqPlan")
    @RequireToken
    @PostMapping("/getZxSkTtReqPlanList")
    public ResponseEntity getZxSkTtReqPlanList(@RequestBody(required = false) ZxSkTtReqPlan zxSkTtReqPlan) {
        return zxSkTtReqPlanService.getZxSkTtReqPlanListByCondition(zxSkTtReqPlan);
    }

    @ApiOperation(value="查询详情物资总需用计划", notes="查询详情物资总需用计划")
    @ApiImplicitParam(name = "zxSkTtReqPlan", value = "物资总需用计划entity", dataType = "ZxSkTtReqPlan")
    @RequireToken
    @PostMapping("/getZxSkTtReqPlanDetails")
    public ResponseEntity getZxSkTtReqPlanDetails(@RequestBody(required = false) ZxSkTtReqPlan zxSkTtReqPlan) {
        return zxSkTtReqPlanService.getZxSkTtReqPlanDetails(zxSkTtReqPlan);
    }

    @ApiOperation(value="新增物资总需用计划", notes="新增物资总需用计划")
    @ApiImplicitParam(name = "zxSkTtReqPlan", value = "物资总需用计划entity", dataType = "ZxSkTtReqPlan")
    @RequireToken
    @PostMapping("/addZxSkTtReqPlan")
    public ResponseEntity addZxSkTtReqPlan(@RequestBody(required = false) ZxSkTtReqPlan zxSkTtReqPlan) {
        return zxSkTtReqPlanService.saveZxSkTtReqPlan(zxSkTtReqPlan);
    }

    @ApiOperation(value="更新物资总需用计划", notes="更新物资总需用计划")
    @ApiImplicitParam(name = "zxSkTtReqPlan", value = "物资总需用计划entity", dataType = "ZxSkTtReqPlan")
    @RequireToken
    @PostMapping("/updateZxSkTtReqPlan")
    public ResponseEntity updateZxSkTtReqPlan(@RequestBody(required = false) ZxSkTtReqPlan zxSkTtReqPlan) {
        return zxSkTtReqPlanService.updateZxSkTtReqPlan(zxSkTtReqPlan);
    }

    @ApiOperation(value="删除物资总需用计划", notes="删除物资总需用计划")
    @ApiImplicitParam(name = "zxSkTtReqPlanList", value = "物资总需用计划List", dataType = "List<ZxSkTtReqPlan>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxSkTtReqPlan")
    public ResponseEntity batchDeleteUpdateZxSkTtReqPlan(@RequestBody(required = false) List<ZxSkTtReqPlan> zxSkTtReqPlanList) {
        return zxSkTtReqPlanService.batchDeleteUpdateZxSkTtReqPlan(zxSkTtReqPlanList);
    }

    @ApiOperation(value="审核物资总需用计划", notes="批量审核物资总需用计划")
    @ApiImplicitParam(name = "zxSkTtReqPlanList", value = "批量审核物资总需用计划List", dataType = "List<ZxSkTtReqPlan>")
    @RequireToken
    @PostMapping("/checkZxSkTtReqPlanList")
    public ResponseEntity checkZxSkTtReqPlanList(@RequestBody(required = false) ZxSkTtReqPlan zxSkTtReqPlan) {
        return zxSkTtReqPlanService.checkZxSkTtReqPlanList(zxSkTtReqPlan);
    }

    @ApiOperation(value="物资总需用计划变更", notes="物资总需用计划变更")
    @ApiImplicitParam(name = "zxSkTtReqPlanList", value = "批量审核物资总需用计划List", dataType = "List<ZxSkTtReqPlan>")
    @RequireToken
    @PostMapping("/updateZxSkTtReqPlanCheckOver")
    public ResponseEntity updateZxSkTtReqPlanCheckOver(@RequestBody(required = false) ZxSkTtReqPlan zxSkTtReqPlan) {
        return zxSkTtReqPlanService.updateZxSkTtReqPlanCheckOver(zxSkTtReqPlan);
    }



}
