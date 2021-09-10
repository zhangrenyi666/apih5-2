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
import com.apih5.mybatis.pojo.ZjTzLifeCycleChange;
import com.apih5.service.ZjTzLifeCycleChangeService;

@RestController
public class ZjTzLifeCycleChangeController {

    @Autowired(required = true)
    private ZjTzLifeCycleChangeService zjTzLifeCycleChangeService;

    @ApiOperation(value="查询全生命周期策划变更", notes="查询全生命周期策划变更")
    @ApiImplicitParam(name = "zjTzLifeCycleChange", value = "全生命周期策划变更entity", dataType = "ZjTzLifeCycleChange")
    @RequireToken
    @PostMapping("/getZjTzLifeCycleChangeList")
    public ResponseEntity getZjTzLifeCycleChangeList(@RequestBody(required = false) ZjTzLifeCycleChange zjTzLifeCycleChange) {
        return zjTzLifeCycleChangeService.getZjTzLifeCycleChangeListByCondition(zjTzLifeCycleChange);
    }

    @ApiOperation(value="查询详情全生命周期策划变更", notes="查询详情全生命周期策划变更")
    @ApiImplicitParam(name = "zjTzLifeCycleChange", value = "全生命周期策划变更entity", dataType = "ZjTzLifeCycleChange")
    @RequireToken
    @PostMapping("/getZjTzLifeCycleChangeDetails")
    public ResponseEntity getZjTzLifeCycleChangeDetails(@RequestBody(required = false) ZjTzLifeCycleChange zjTzLifeCycleChange) {
        return zjTzLifeCycleChangeService.getZjTzLifeCycleChangeDetails(zjTzLifeCycleChange);
    }

    @ApiOperation(value="新增全生命周期策划变更", notes="新增全生命周期策划变更")
    @ApiImplicitParam(name = "zjTzLifeCycleChange", value = "全生命周期策划变更entity", dataType = "ZjTzLifeCycleChange")
    @RequireToken
    @PostMapping("/addZjTzLifeCycleChange")
    public ResponseEntity addZjTzLifeCycleChange(@RequestBody(required = false) ZjTzLifeCycleChange zjTzLifeCycleChange) {
        return zjTzLifeCycleChangeService.saveZjTzLifeCycleChange(zjTzLifeCycleChange);
    }

    @ApiOperation(value="更新全生命周期策划变更", notes="更新全生命周期策划变更")
    @ApiImplicitParam(name = "zjTzLifeCycleChange", value = "全生命周期策划变更entity", dataType = "ZjTzLifeCycleChange")
    @RequireToken
    @PostMapping("/updateZjTzLifeCycleChange")
    public ResponseEntity updateZjTzLifeCycleChange(@RequestBody(required = false) ZjTzLifeCycleChange zjTzLifeCycleChange) {
        return zjTzLifeCycleChangeService.updateZjTzLifeCycleChange(zjTzLifeCycleChange);
    }

    @ApiOperation(value="删除全生命周期策划变更", notes="删除全生命周期策划变更")
    @ApiImplicitParam(name = "zjTzLifeCycleChangeList", value = "全生命周期策划变更List", dataType = "List<ZjTzLifeCycleChange>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjTzLifeCycleChange")
    public ResponseEntity batchDeleteUpdateZjTzLifeCycleChange(@RequestBody(required = false) List<ZjTzLifeCycleChange> zjTzLifeCycleChangeList) {
        return zjTzLifeCycleChangeService.batchDeleteUpdateZjTzLifeCycleChange(zjTzLifeCycleChangeList);
    }
    
    @ApiOperation(value="上报全生命周期策划变更", notes="上报全生命周期策划变更")
    @ApiImplicitParam(name = "zjTzLifeCycleChange", value = "全生命周期策划变更entity", dataType = "ZjTzLifeCycleChange")
    @RequireToken
    @PostMapping("/updateZjTzLifeCycleChangeForFlow")
    public ResponseEntity updateZjTzLifeCycleChangeForFlow(@RequestBody(required = false) ZjTzLifeCycleChange zjTzLifeCycleChange) {
        return zjTzLifeCycleChangeService.updateZjTzLifeCycleChangeForFlow(zjTzLifeCycleChange);
    }

    @ApiOperation(value="根据流程id获取流程标题", notes="根据流程id获取流程标题")
    @ApiImplicitParam(name = "zjTzLifeCycleChange", value = "全生命周期策划变更entity", dataType = "ZjTzLifeCycleChange")
    @RequireToken
    @PostMapping("/getZjTzFlowTitle")
    public ResponseEntity getZjTzFlowTitle(@RequestBody(required = false) ZjTzLifeCycleChange zjTzLifeCycleChange) {
        return zjTzLifeCycleChangeService.getZjTzFlowTitle(zjTzLifeCycleChange);
    }
}
