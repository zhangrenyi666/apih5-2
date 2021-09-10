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
import com.apih5.mybatis.pojo.ZjTzDesignFlow;
import com.apih5.service.ZjTzDesignFlowService;

@RestController
public class ZjTzDesignFlowController {

    @Autowired(required = true)
    private ZjTzDesignFlowService zjTzDesignFlowService;

    @ApiOperation(value="查询设计流程管理", notes="查询设计流程管理")
    @ApiImplicitParam(name = "zjTzDesignFlow", value = "设计流程管理entity", dataType = "ZjTzDesignFlow")
    @RequireToken
    @PostMapping("/getZjTzDesignFlowList")
    public ResponseEntity getZjTzDesignFlowList(@RequestBody(required = false) ZjTzDesignFlow zjTzDesignFlow) {
        return zjTzDesignFlowService.getZjTzDesignFlowListByCondition(zjTzDesignFlow);
    }

    @ApiOperation(value="查询详情设计流程管理", notes="查询详情设计流程管理")
    @ApiImplicitParam(name = "zjTzDesignFlow", value = "设计流程管理entity", dataType = "ZjTzDesignFlow")
    @RequireToken
    @PostMapping("/getZjTzDesignFlowDetails")
    public ResponseEntity getZjTzDesignFlowDetails(@RequestBody(required = false) ZjTzDesignFlow zjTzDesignFlow) {
        return zjTzDesignFlowService.getZjTzDesignFlowDetails(zjTzDesignFlow);
    }

    @ApiOperation(value="新增设计流程管理", notes="新增设计流程管理")
    @ApiImplicitParam(name = "zjTzDesignFlow", value = "设计流程管理entity", dataType = "ZjTzDesignFlow")
    @RequireToken
    @PostMapping("/addZjTzDesignFlow")
    public ResponseEntity addZjTzDesignFlow(@RequestBody(required = false) ZjTzDesignFlow zjTzDesignFlow) {
        return zjTzDesignFlowService.saveZjTzDesignFlow(zjTzDesignFlow);
    }

    @ApiOperation(value="更新设计流程管理", notes="更新设计流程管理")
    @ApiImplicitParam(name = "zjTzDesignFlow", value = "设计流程管理entity", dataType = "ZjTzDesignFlow")
    @RequireToken
    @PostMapping("/updateZjTzDesignFlow")
    public ResponseEntity updateZjTzDesignFlow(@RequestBody(required = false) ZjTzDesignFlow zjTzDesignFlow) {
        return zjTzDesignFlowService.updateZjTzDesignFlow(zjTzDesignFlow);
    }

    @ApiOperation(value="删除设计流程管理", notes="删除设计流程管理")
    @ApiImplicitParam(name = "zjTzDesignFlowList", value = "设计流程管理List", dataType = "List<ZjTzDesignFlow>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjTzDesignFlow")
    public ResponseEntity batchDeleteUpdateZjTzDesignFlow(@RequestBody(required = false) List<ZjTzDesignFlow> zjTzDesignFlowList) {
        return zjTzDesignFlowService.batchDeleteUpdateZjTzDesignFlow(zjTzDesignFlowList);
    }
    
    @ApiOperation(value="批量上报设计流程管理", notes="批量上报设计流程管理")
    @ApiImplicitParam(name = "zjTzDesignFlowList", value = "设计流程管理List", dataType = "List<ZjTzDesignFlow>")
    @RequireToken
    @PostMapping("/batchReleaseZjTzDesignFlow")
    public ResponseEntity batchReleaseZjTzDesignFlow(@RequestBody(required = false) List<ZjTzDesignFlow> zjTzDesignFlowList) {
        return zjTzDesignFlowService.batchReleaseZjTzDesignFlow(zjTzDesignFlowList);
    }
    
    @ApiOperation(value="批量退回设计流程管理", notes="批量退回设计流程管理")
    @ApiImplicitParam(name = "zjTzDesignFlowList", value = "设计流程管理List", dataType = "List<ZjTzDesignFlow>")
    @RequireToken
    @PostMapping("/batchRecallZjTzDesignFlow")
    public ResponseEntity batchRecallZjTzDesignFlow(@RequestBody(required = false) List<ZjTzDesignFlow> zjTzDesignFlowList) {
        return zjTzDesignFlowService.batchRecallZjTzDesignFlow(zjTzDesignFlowList);
    }
    
    @ApiOperation(value="保存所有环节管理接口", notes="保存所有环节管理接口")
    @ApiImplicitParam(name = "zjTzDesignFlow", value = "设计流程管理entity", dataType = "ZjTzDesignFlow")
    @RequireToken
    @PostMapping("/saveZjTzPartManageAllList")
    public ResponseEntity saveZjTzPartManageAllList(@RequestBody(required = false) ZjTzDesignFlow zjTzDesignFlow) {
        return zjTzDesignFlowService.saveZjTzPartManageAllList(zjTzDesignFlow);
    }
    
    @ApiOperation(value="审查完成设计流程管理", notes="审查完成设计流程管理")
    @ApiImplicitParam(name = "zjTzDesignFlowList", value = "设计流程管理List", dataType = "List<ZjTzDesignFlow>")
    @RequireToken
    @PostMapping("/checkAndFinishZjTzDesignFlow")
    public ResponseEntity checkAndFinishZjTzDesignFlow(@RequestBody(required = false) ZjTzDesignFlow zjTzDesignFlow) {
        return zjTzDesignFlowService.checkAndFinishZjTzDesignFlow(zjTzDesignFlow);
    }
}
