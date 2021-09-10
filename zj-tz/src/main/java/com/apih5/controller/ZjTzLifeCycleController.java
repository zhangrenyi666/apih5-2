package com.apih5.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import com.apih5.framework.annotation.RequireToken;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjTzLifeCycle;
import com.apih5.service.ZjTzLifeCycleService;

@RestController
public class ZjTzLifeCycleController {

    @Autowired(required = true)
    private ZjTzLifeCycleService zjTzLifeCycleService;

    @ApiOperation(value="查询全生命周期策划", notes="查询全生命周期策划")
    @ApiImplicitParam(name = "zjTzLifeCycle", value = "全生命周期策划entity", dataType = "ZjTzLifeCycle")
    @RequireToken
    @PostMapping("/getZjTzLifeCycleList")
    public ResponseEntity getZjTzLifeCycleList(@RequestBody(required = false) ZjTzLifeCycle zjTzLifeCycle) {
        return zjTzLifeCycleService.getZjTzLifeCycleListByCondition(zjTzLifeCycle);
    }

    @ApiOperation(value="查询详情全生命周期策划", notes="查询详情全生命周期策划")
    @ApiImplicitParam(name = "zjTzLifeCycle", value = "全生命周期策划entity", dataType = "ZjTzLifeCycle")
    @RequireToken
    @PostMapping("/getZjTzLifeCycleDetails")
    public ResponseEntity getZjTzLifeCycleDetails(@RequestBody(required = false) ZjTzLifeCycle zjTzLifeCycle) {
        return zjTzLifeCycleService.getZjTzLifeCycleDetails(zjTzLifeCycle);
    }

    @ApiOperation(value="新增全生命周期策划", notes="新增全生命周期策划")
    @ApiImplicitParam(name = "zjTzLifeCycle", value = "全生命周期策划entity", dataType = "ZjTzLifeCycle")
    @RequireToken
    @PostMapping("/addZjTzLifeCycle")
    public ResponseEntity addZjTzLifeCycle(@RequestBody(required = false) ZjTzLifeCycle zjTzLifeCycle) {
        return zjTzLifeCycleService.saveZjTzLifeCycle(zjTzLifeCycle);
    }

    @ApiOperation(value="更新全生命周期策划", notes="更新全生命周期策划")
    @ApiImplicitParam(name = "zjTzLifeCycle", value = "全生命周期策划entity", dataType = "ZjTzLifeCycle")
    @RequireToken
    @PostMapping("/updateZjTzLifeCycle")
    public ResponseEntity updateZjTzLifeCycle(@RequestBody(required = false) ZjTzLifeCycle zjTzLifeCycle) {
        return zjTzLifeCycleService.updateZjTzLifeCycle(zjTzLifeCycle);
    }

    @ApiOperation(value="删除全生命周期策划", notes="删除全生命周期策划")
    @ApiImplicitParam(name = "zjTzLifeCycleList", value = "全生命周期策划List", dataType = "List<ZjTzLifeCycle>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjTzLifeCycle")
    public ResponseEntity batchDeleteUpdateZjTzLifeCycle(@RequestBody(required = false) List<ZjTzLifeCycle> zjTzLifeCycleList) {
        return zjTzLifeCycleService.batchDeleteUpdateZjTzLifeCycle(zjTzLifeCycleList);
    }
    
    @ApiOperation(value="上报全生命周期策划", notes="上报全生命周期策划")
    @ApiImplicitParam(name = "zjTzLifeCycle", value = "全生命周期策划entity", dataType = "ZjTzLifeCycle")
    @RequireToken
    @PostMapping("/updateZjTzLifeCycleFroFlow")
    public ResponseEntity updateZjTzLifeCycleFroFlow(@RequestBody(required = false) ZjTzLifeCycle zjTzLifeCycle) {
        return zjTzLifeCycleService.updateZjTzLifeCycleFroFlow(zjTzLifeCycle);
    }
    
//    @ApiOperation(value="手机端消息提醒", notes="手机端消息提醒")
//    @ApiImplicitParam(name = "zjTzLifeCycle", value = "全生命周期策划entity", dataType = "ZjTzLifeCycle")
//    @GetMapping("/zjTzLifeCycleMesRemind")
//    public ResponseEntity zjTzLifeCycleMesRemind(@RequestBody(required = false) ZjTzLifeCycle zjTzLifeCycle) {
//    	return zjTzLifeCycleService.zjTzLifeCycleMesRemind(zjTzLifeCycle);
//    }
    
    @ApiOperation(value="上报全生命周期策划", notes="上报全生命周期策划")
    @ApiImplicitParam(name = "zjTzLifeCycle", value = "全生命周期策划entity", dataType = "ZjTzLifeCycle")
    @GetMapping("/sendZjtzMsg")
    public ResponseEntity sendZjtzMsg(@RequestBody(required = false) ZjTzLifeCycle zjTzLifeCycle) {
        return zjTzLifeCycleService.zjTzLifeCycleMesRemind(zjTzLifeCycle);
    }
    
}
