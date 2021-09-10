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
import com.apih5.mybatis.pojo.ZjLzehMonthPlanProgress;
import com.apih5.service.ZjLzehMonthPlanProgressService;

@RestController
public class ZjLzehMonthPlanProgressController {

    @Autowired(required = true)
    private ZjLzehMonthPlanProgressService zjLzehMonthPlanProgressService;

    @ApiOperation(value="查询月计划进度表", notes="查询月计划进度表")
    @ApiImplicitParam(name = "zjLzehMonthPlanProgress", value = "月计划进度表entity", dataType = "ZjLzehMonthPlanProgress")
    @RequireToken
    @PostMapping("/getZjLzehMonthPlanProgressList")
    public ResponseEntity getZjLzehMonthPlanProgressList(@RequestBody(required = false) ZjLzehMonthPlanProgress zjLzehMonthPlanProgress) {
        return zjLzehMonthPlanProgressService.getZjLzehMonthPlanProgressListByCondition(zjLzehMonthPlanProgress);
    }

    @ApiOperation(value="查询详情月计划进度表", notes="查询详情月计划进度表")
    @ApiImplicitParam(name = "zjLzehMonthPlanProgress", value = "月计划进度表entity", dataType = "ZjLzehMonthPlanProgress")
    @RequireToken
    @PostMapping("/getZjLzehMonthPlanProgressDetail")
    public ResponseEntity getZjLzehMonthPlanProgressDetail(@RequestBody(required = false) ZjLzehMonthPlanProgress zjLzehMonthPlanProgress) {
        return zjLzehMonthPlanProgressService.getZjLzehMonthPlanProgressDetail(zjLzehMonthPlanProgress);
    }

    @ApiOperation(value="新增月计划进度表", notes="新增月计划进度表")
    @ApiImplicitParam(name = "zjLzehMonthPlanProgress", value = "月计划进度表entity", dataType = "ZjLzehMonthPlanProgress")
    @RequireToken
    @PostMapping("/addZjLzehMonthPlanProgress")
    public ResponseEntity addZjLzehMonthPlanProgress(@RequestBody(required = false) ZjLzehMonthPlanProgress zjLzehMonthPlanProgress) {
        return zjLzehMonthPlanProgressService.saveZjLzehMonthPlanProgress(zjLzehMonthPlanProgress);
    }

    @ApiOperation(value="更新月计划进度表", notes="更新月计划进度表")
    @ApiImplicitParam(name = "zjLzehMonthPlanProgress", value = "月计划进度表entity", dataType = "ZjLzehMonthPlanProgress")
    @RequireToken
    @PostMapping("/updateZjLzehMonthPlanProgress")
    public ResponseEntity updateZjLzehMonthPlanProgress(@RequestBody(required = false) ZjLzehMonthPlanProgress zjLzehMonthPlanProgress) {
        return zjLzehMonthPlanProgressService.updateZjLzehMonthPlanProgress(zjLzehMonthPlanProgress);
    }

    @ApiOperation(value="删除月计划进度表", notes="删除月计划进度表")
    @ApiImplicitParam(name = "zjLzehMonthPlanProgressList", value = "月计划进度表List", dataType = "List<ZjLzehMonthPlanProgress>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjLzehMonthPlanProgress")
    public ResponseEntity batchDeleteUpdateZjLzehMonthPlanProgress(@RequestBody(required = false) List<ZjLzehMonthPlanProgress> zjLzehMonthPlanProgressList) {
        return zjLzehMonthPlanProgressService.batchDeleteUpdateZjLzehMonthPlanProgress(zjLzehMonthPlanProgressList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
