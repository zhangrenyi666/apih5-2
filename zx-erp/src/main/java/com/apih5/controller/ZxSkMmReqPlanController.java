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
import com.apih5.mybatis.pojo.ZxSkMmReqPlan;
import com.apih5.service.ZxSkMmReqPlanService;

@RestController
public class ZxSkMmReqPlanController {

    @Autowired(required = true)
    private ZxSkMmReqPlanService zxSkMmReqPlanService;

    @ApiOperation(value="查询月物资需用计划", notes="查询月物资需用计划")
    @ApiImplicitParam(name = "zxSkMmReqPlan", value = "月物资需用计划entity", dataType = "ZxSkMmReqPlan")
    @RequireToken
    @PostMapping("/getZxSkMmReqPlanList")
    public ResponseEntity getZxSkMmReqPlanList(@RequestBody(required = false) ZxSkMmReqPlan zxSkMmReqPlan) {
        return zxSkMmReqPlanService.getZxSkMmReqPlanListByCondition(zxSkMmReqPlan);
    }

    @ApiOperation(value="查询详情月物资需用计划", notes="查询详情月物资需用计划")
    @ApiImplicitParam(name = "zxSkMmReqPlan", value = "月物资需用计划entity", dataType = "ZxSkMmReqPlan")
    @RequireToken
    @PostMapping("/getZxSkMmReqPlanDetails")
    public ResponseEntity getZxSkMmReqPlanDetails(@RequestBody(required = false) ZxSkMmReqPlan zxSkMmReqPlan) {
        return zxSkMmReqPlanService.getZxSkMmReqPlanDetails(zxSkMmReqPlan);
    }

    @ApiOperation(value="新增月物资需用计划", notes="新增月物资需用计划")
    @ApiImplicitParam(name = "zxSkMmReqPlan", value = "月物资需用计划entity", dataType = "ZxSkMmReqPlan")
    @RequireToken
    @PostMapping("/addZxSkMmReqPlan")
    public ResponseEntity addZxSkMmReqPlan(@RequestBody(required = false) ZxSkMmReqPlan zxSkMmReqPlan) {
        return zxSkMmReqPlanService.saveZxSkMmReqPlan(zxSkMmReqPlan);
    }

    @ApiOperation(value="更新月物资需用计划", notes="更新月物资需用计划")
    @ApiImplicitParam(name = "zxSkMmReqPlan", value = "月物资需用计划entity", dataType = "ZxSkMmReqPlan")
    @RequireToken
    @PostMapping("/updateZxSkMmReqPlan")
    public ResponseEntity updateZxSkMmReqPlan(@RequestBody(required = false) ZxSkMmReqPlan zxSkMmReqPlan) {
        return zxSkMmReqPlanService.updateZxSkMmReqPlan(zxSkMmReqPlan);
    }

    @ApiOperation(value="删除月物资需用计划", notes="删除月物资需用计划")
    @ApiImplicitParam(name = "zxSkMmReqPlanList", value = "月物资需用计划List", dataType = "List<ZxSkMmReqPlan>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxSkMmReqPlan")
    public ResponseEntity batchDeleteUpdateZxSkMmReqPlan(@RequestBody(required = false) List<ZxSkMmReqPlan> zxSkMmReqPlanList) {
        return zxSkMmReqPlanService.batchDeleteUpdateZxSkMmReqPlan(zxSkMmReqPlanList);
    }




}
