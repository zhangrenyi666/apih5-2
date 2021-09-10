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
import com.apih5.mybatis.pojo.ZjLzehManageTaskPlan;
import com.apih5.service.ZjLzehManageTaskPlanService;

@RestController
public class ZjLzehManageTaskPlanController {

    @Autowired(required = true)
    private ZjLzehManageTaskPlanService zjLzehManageTaskPlanService;

    @ApiOperation(value="查询经营任务计划", notes="查询经营任务计划")
    @ApiImplicitParam(name = "zjLzehManageTaskPlan", value = "经营任务计划entity", dataType = "ZjLzehManageTaskPlan")
    @RequireToken
    @PostMapping("/getZjLzehManageTaskPlanList")
    public ResponseEntity getZjLzehManageTaskPlanList(@RequestBody(required = false) ZjLzehManageTaskPlan zjLzehManageTaskPlan) {
        return zjLzehManageTaskPlanService.getZjLzehManageTaskPlanListByCondition(zjLzehManageTaskPlan);
    }

    @ApiOperation(value="查询详情经营任务计划", notes="查询详情经营任务计划")
    @ApiImplicitParam(name = "zjLzehManageTaskPlan", value = "经营任务计划entity", dataType = "ZjLzehManageTaskPlan")
    @RequireToken
    @PostMapping("/getZjLzehManageTaskPlanDetail")
    public ResponseEntity getZjLzehManageTaskPlanDetail(@RequestBody(required = false) ZjLzehManageTaskPlan zjLzehManageTaskPlan) {
        return zjLzehManageTaskPlanService.getZjLzehManageTaskPlanDetail(zjLzehManageTaskPlan);
    }

    @ApiOperation(value="新增经营任务计划", notes="新增经营任务计划")
    @ApiImplicitParam(name = "zjLzehManageTaskPlan", value = "经营任务计划entity", dataType = "ZjLzehManageTaskPlan")
    @RequireToken
    @PostMapping("/addZjLzehManageTaskPlan")
    public ResponseEntity addZjLzehManageTaskPlan(@RequestBody(required = false) ZjLzehManageTaskPlan zjLzehManageTaskPlan) {
        return zjLzehManageTaskPlanService.saveZjLzehManageTaskPlan(zjLzehManageTaskPlan);
    }

    @ApiOperation(value="更新经营任务计划", notes="更新经营任务计划")
    @ApiImplicitParam(name = "zjLzehManageTaskPlan", value = "经营任务计划entity", dataType = "ZjLzehManageTaskPlan")
    @RequireToken
    @PostMapping("/updateZjLzehManageTaskPlan")
    public ResponseEntity updateZjLzehManageTaskPlan(@RequestBody(required = false) ZjLzehManageTaskPlan zjLzehManageTaskPlan) {
        return zjLzehManageTaskPlanService.updateZjLzehManageTaskPlan(zjLzehManageTaskPlan);
    }

    @ApiOperation(value="删除经营任务计划", notes="删除经营任务计划")
    @ApiImplicitParam(name = "zjLzehManageTaskPlanList", value = "经营任务计划List", dataType = "List<ZjLzehManageTaskPlan>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjLzehManageTaskPlan")
    public ResponseEntity batchDeleteUpdateZjLzehManageTaskPlan(@RequestBody(required = false) List<ZjLzehManageTaskPlan> zjLzehManageTaskPlanList) {
        return zjLzehManageTaskPlanService.batchDeleteUpdateZjLzehManageTaskPlan(zjLzehManageTaskPlanList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    
    @ApiOperation(value="查询任务创建收、任务接数量", notes="查询任务创建收、任务接数量")
    @ApiImplicitParam(name = "zjLzehManageTaskPlan", value = "经营任务计划entity", dataType = "ZjLzehManageTaskPlan")
    @RequireToken
    @PostMapping("/getZjLzehTaskNum")
    public ResponseEntity getZjLzehTaskNum(@RequestBody(required = false) ZjLzehManageTaskPlan zjLzehManageTaskPlan) {
        return zjLzehManageTaskPlanService.getZjLzehTaskNum(zjLzehManageTaskPlan);
    }
}
