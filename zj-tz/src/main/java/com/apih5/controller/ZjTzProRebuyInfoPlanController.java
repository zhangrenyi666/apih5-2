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
import com.apih5.mybatis.pojo.ZjTzProRebuyInfoPlan;
import com.apih5.service.ZjTzProRebuyInfoPlanService;

@RestController
public class ZjTzProRebuyInfoPlanController {

    @Autowired(required = true)
    private ZjTzProRebuyInfoPlanService zjTzProRebuyInfoPlanService;

    @ApiOperation(value="查询运营/回购明细", notes="查询运营/回购明细")
    @ApiImplicitParam(name = "zjTzProRebuyInfoPlan", value = "运营/回购明细entity", dataType = "ZjTzProRebuyInfoPlan")
    @RequireToken
    @PostMapping("/getZjTzProRebuyInfoPlanList")
    public ResponseEntity getZjTzProRebuyInfoPlanList(@RequestBody(required = false) ZjTzProRebuyInfoPlan zjTzProRebuyInfoPlan) {
        return zjTzProRebuyInfoPlanService.getZjTzProRebuyInfoPlanListByCondition(zjTzProRebuyInfoPlan);
    }

    @ApiOperation(value="查询详情运营/回购明细", notes="查询详情运营/回购明细")
    @ApiImplicitParam(name = "zjTzProRebuyInfoPlan", value = "运营/回购明细entity", dataType = "ZjTzProRebuyInfoPlan")
    @RequireToken
    @PostMapping("/getZjTzProRebuyInfoPlanDetails")
    public ResponseEntity getZjTzProRebuyInfoPlanDetails(@RequestBody(required = false) ZjTzProRebuyInfoPlan zjTzProRebuyInfoPlan) {
        return zjTzProRebuyInfoPlanService.getZjTzProRebuyInfoPlanDetails(zjTzProRebuyInfoPlan);
    }

    @ApiOperation(value="新增运营/回购明细", notes="新增运营/回购明细")
    @ApiImplicitParam(name = "zjTzProRebuyInfoPlan", value = "运营/回购明细entity", dataType = "ZjTzProRebuyInfoPlan")
    @RequireToken
    @PostMapping("/addZjTzProRebuyInfoPlan")
    public ResponseEntity addZjTzProRebuyInfoPlan(@RequestBody(required = false) ZjTzProRebuyInfoPlan zjTzProRebuyInfoPlan) {
        return zjTzProRebuyInfoPlanService.saveZjTzProRebuyInfoPlan(zjTzProRebuyInfoPlan);
    }

    @ApiOperation(value="更新运营/回购明细", notes="更新运营/回购明细")
    @ApiImplicitParam(name = "zjTzProRebuyInfoPlan", value = "运营/回购明细entity", dataType = "ZjTzProRebuyInfoPlan")
    @RequireToken
    @PostMapping("/updateZjTzProRebuyInfoPlan")
    public ResponseEntity updateZjTzProRebuyInfoPlan(@RequestBody(required = false) ZjTzProRebuyInfoPlan zjTzProRebuyInfoPlan) {
        return zjTzProRebuyInfoPlanService.updateZjTzProRebuyInfoPlan(zjTzProRebuyInfoPlan);
    }

    @ApiOperation(value="删除运营/回购明细", notes="删除运营/回购明细")
    @ApiImplicitParam(name = "zjTzProRebuyInfoPlanList", value = "运营/回购明细List", dataType = "List<ZjTzProRebuyInfoPlan>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjTzProRebuyInfoPlan")
    public ResponseEntity batchDeleteUpdateZjTzProRebuyInfoPlan(@RequestBody(required = false) List<ZjTzProRebuyInfoPlan> zjTzProRebuyInfoPlanList) {
        return zjTzProRebuyInfoPlanService.batchDeleteUpdateZjTzProRebuyInfoPlan(zjTzProRebuyInfoPlanList);
    }

}
