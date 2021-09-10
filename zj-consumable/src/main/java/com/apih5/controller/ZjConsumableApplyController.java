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
import com.apih5.mybatis.pojo.ZjConsumableApply;
import com.apih5.service.ZjConsumableApplyService;

@RestController
public class ZjConsumableApplyController {

    @Autowired(required = true)
    private ZjConsumableApplyService zjConsumableApplyService;

    @ApiOperation(value="查询耗材申请领用", notes="查询耗材申请领用")
    @ApiImplicitParam(name = "zjConsumableApply", value = "耗材申请领用entity", dataType = "ZjConsumableApply")
    @RequireToken
    @PostMapping("/getZjConsumableApplyList")
    public ResponseEntity getZjConsumableApplyList(@RequestBody(required = false) ZjConsumableApply zjConsumableApply) {
        return zjConsumableApplyService.getZjConsumableApplyListByCondition(zjConsumableApply);
    }

    @ApiOperation(value="查询详情耗材申请领用", notes="查询详情耗材申请领用")
    @ApiImplicitParam(name = "zjConsumableApply", value = "耗材申请领用entity", dataType = "ZjConsumableApply")
    @RequireToken
    @PostMapping("/getZjConsumableApplyDetails")
    public ResponseEntity getZjConsumableApplyDetails(@RequestBody(required = false) ZjConsumableApply zjConsumableApply) {
        return zjConsumableApplyService.getZjConsumableApplyDetails(zjConsumableApply);
    }

    @ApiOperation(value="新增耗材申请领用", notes="新增耗材申请领用")
    @ApiImplicitParam(name = "zjConsumableApply", value = "耗材申请领用entity", dataType = "ZjConsumableApply")
    @RequireToken
    @PostMapping("/addZjConsumableApply")
    public ResponseEntity addZjConsumableApply(@RequestBody(required = false) ZjConsumableApply zjConsumableApply) {
        return zjConsumableApplyService.saveZjConsumableApply(zjConsumableApply);
    }

    @ApiOperation(value="更新耗材申请领用", notes="更新耗材申请领用")
    @ApiImplicitParam(name = "zjConsumableApply", value = "耗材申请领用entity", dataType = "ZjConsumableApply")
    @RequireToken
    @PostMapping("/updateZjConsumableApply")
    public ResponseEntity updateZjConsumableApply(@RequestBody(required = false) ZjConsumableApply zjConsumableApply) {
        return zjConsumableApplyService.updateZjConsumableApply(zjConsumableApply);
    }

    @ApiOperation(value="删除耗材申请领用", notes="删除耗材申请领用")
    @ApiImplicitParam(name = "zjConsumableApplyList", value = "耗材申请领用List", dataType = "List<ZjConsumableApply>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjConsumableApply")
    public ResponseEntity batchDeleteUpdateZjConsumableApply(@RequestBody(required = false) List<ZjConsumableApply> zjConsumableApplyList) {
        return zjConsumableApplyService.batchDeleteUpdateZjConsumableApply(zjConsumableApplyList);
    }
    
    @ApiOperation(value="审批耗材申请领用", notes="审批耗材申请领用")
    @ApiImplicitParam(name = "zjConsumableApply", value = "耗材申请领用entity", dataType = "ZjConsumableApply")
    @RequireToken
    @PostMapping("/approveZjConsumableApply")
    public ResponseEntity approveZjConsumableApply(@RequestBody(required = false) ZjConsumableApply zjConsumableApply) {
        return zjConsumableApplyService.approveZjConsumableApply(zjConsumableApply);
    }
    
    @ApiOperation(value="领用耗材申请领用", notes="领用耗材申请领用")
    @ApiImplicitParam(name = "zjConsumableApply", value = "耗材申请领用entity", dataType = "ZjConsumableApply")
    @RequireToken
    @PostMapping("/useZjConsumableApply")
    public ResponseEntity useZjConsumableApply(@RequestBody(required = false) ZjConsumableApply zjConsumableApply) {
        return zjConsumableApplyService.useZjConsumableApply(zjConsumableApply);
    }

}
