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
import com.apih5.mybatis.pojo.ZjLzehProduceTaskPlan;
import com.apih5.service.ZjLzehProduceTaskPlanService;

@RestController
public class ZjLzehProduceTaskPlanController {

    @Autowired(required = true)
    private ZjLzehProduceTaskPlanService zjLzehProduceTaskPlanService;

    @ApiOperation(value="查询生产目标任务计划", notes="查询生产目标任务计划")
    @ApiImplicitParam(name = "zjLzehProduceTaskPlan", value = "生产目标任务计划entity", dataType = "ZjLzehProduceTaskPlan")
    @RequireToken
    @PostMapping("/getZjLzehProduceTaskPlanList")
    public ResponseEntity getZjLzehProduceTaskPlanList(@RequestBody(required = false) ZjLzehProduceTaskPlan zjLzehProduceTaskPlan) {
        return zjLzehProduceTaskPlanService.getZjLzehProduceTaskPlanListByCondition(zjLzehProduceTaskPlan);
    }

    @ApiOperation(value="查询详情生产目标任务计划", notes="查询详情生产目标任务计划")
    @ApiImplicitParam(name = "zjLzehProduceTaskPlan", value = "生产目标任务计划entity", dataType = "ZjLzehProduceTaskPlan")
    @RequireToken
    @PostMapping("/getZjLzehProduceTaskPlanDetail")
    public ResponseEntity getZjLzehProduceTaskPlanDetail(@RequestBody(required = false) ZjLzehProduceTaskPlan zjLzehProduceTaskPlan) {
        return zjLzehProduceTaskPlanService.getZjLzehProduceTaskPlanDetail(zjLzehProduceTaskPlan);
    }

    @ApiOperation(value="新增生产目标任务计划", notes="新增生产目标任务计划")
    @ApiImplicitParam(name = "zjLzehProduceTaskPlan", value = "生产目标任务计划entity", dataType = "ZjLzehProduceTaskPlan")
    @RequireToken
    @PostMapping("/addZjLzehProduceTaskPlan")
    public ResponseEntity addZjLzehProduceTaskPlan(@RequestBody(required = false) ZjLzehProduceTaskPlan zjLzehProduceTaskPlan) {
        return zjLzehProduceTaskPlanService.saveZjLzehProduceTaskPlan(zjLzehProduceTaskPlan);
    }

    @ApiOperation(value="更新生产目标任务计划", notes="更新生产目标任务计划")
    @ApiImplicitParam(name = "zjLzehProduceTaskPlan", value = "生产目标任务计划entity", dataType = "ZjLzehProduceTaskPlan")
    @RequireToken
    @PostMapping("/updateZjLzehProduceTaskPlan")
    public ResponseEntity updateZjLzehProduceTaskPlan(@RequestBody(required = false) ZjLzehProduceTaskPlan zjLzehProduceTaskPlan) {
        return zjLzehProduceTaskPlanService.updateZjLzehProduceTaskPlan(zjLzehProduceTaskPlan);
    }

    @ApiOperation(value="删除生产目标任务计划", notes="删除生产目标任务计划")
    @ApiImplicitParam(name = "zjLzehProduceTaskPlanList", value = "生产目标任务计划List", dataType = "List<ZjLzehProduceTaskPlan>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjLzehProduceTaskPlan")
    public ResponseEntity batchDeleteUpdateZjLzehProduceTaskPlan(@RequestBody(required = false) List<ZjLzehProduceTaskPlan> zjLzehProduceTaskPlanList) {
        return zjLzehProduceTaskPlanService.batchDeleteUpdateZjLzehProduceTaskPlan(zjLzehProduceTaskPlanList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
