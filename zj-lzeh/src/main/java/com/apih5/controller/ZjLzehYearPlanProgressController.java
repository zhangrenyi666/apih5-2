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
import com.apih5.mybatis.pojo.ZjLzehYearPlanProgress;
import com.apih5.service.ZjLzehYearPlanProgressService;

@RestController
public class ZjLzehYearPlanProgressController {

    @Autowired(required = true)
    private ZjLzehYearPlanProgressService zjLzehYearPlanProgressService;

    @ApiOperation(value="查询年计划进度表", notes="查询年计划进度表")
    @ApiImplicitParam(name = "zjLzehYearPlanProgress", value = "年计划进度表entity", dataType = "ZjLzehYearPlanProgress")
    @RequireToken
    @PostMapping("/getZjLzehYearPlanProgressList")
    public ResponseEntity getZjLzehYearPlanProgressList(@RequestBody(required = false) ZjLzehYearPlanProgress zjLzehYearPlanProgress) {
        return zjLzehYearPlanProgressService.getZjLzehYearPlanProgressListByCondition(zjLzehYearPlanProgress);
    }

    @ApiOperation(value="查询详情年计划进度表", notes="查询详情年计划进度表")
    @ApiImplicitParam(name = "zjLzehYearPlanProgress", value = "年计划进度表entity", dataType = "ZjLzehYearPlanProgress")
    @RequireToken
    @PostMapping("/getZjLzehYearPlanProgressDetail")
    public ResponseEntity getZjLzehYearPlanProgressDetail(@RequestBody(required = false) ZjLzehYearPlanProgress zjLzehYearPlanProgress) {
        return zjLzehYearPlanProgressService.getZjLzehYearPlanProgressDetail(zjLzehYearPlanProgress);
    }

    @ApiOperation(value="新增年计划进度表", notes="新增年计划进度表")
    @ApiImplicitParam(name = "zjLzehYearPlanProgress", value = "年计划进度表entity", dataType = "ZjLzehYearPlanProgress")
    @RequireToken
    @PostMapping("/addZjLzehYearPlanProgress")
    public ResponseEntity addZjLzehYearPlanProgress(@RequestBody(required = false) ZjLzehYearPlanProgress zjLzehYearPlanProgress) {
        return zjLzehYearPlanProgressService.saveZjLzehYearPlanProgress(zjLzehYearPlanProgress);
    }

    @ApiOperation(value="更新年计划进度表", notes="更新年计划进度表")
    @ApiImplicitParam(name = "zjLzehYearPlanProgress", value = "年计划进度表entity", dataType = "ZjLzehYearPlanProgress")
    @RequireToken
    @PostMapping("/updateZjLzehYearPlanProgress")
    public ResponseEntity updateZjLzehYearPlanProgress(@RequestBody(required = false) ZjLzehYearPlanProgress zjLzehYearPlanProgress) {
        return zjLzehYearPlanProgressService.updateZjLzehYearPlanProgress(zjLzehYearPlanProgress);
    }

    @ApiOperation(value="删除年计划进度表", notes="删除年计划进度表")
    @ApiImplicitParam(name = "zjLzehYearPlanProgressList", value = "年计划进度表List", dataType = "List<ZjLzehYearPlanProgress>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjLzehYearPlanProgress")
    public ResponseEntity batchDeleteUpdateZjLzehYearPlanProgress(@RequestBody(required = false) List<ZjLzehYearPlanProgress> zjLzehYearPlanProgressList) {
        return zjLzehYearPlanProgressService.batchDeleteUpdateZjLzehYearPlanProgress(zjLzehYearPlanProgressList);
    }
    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
