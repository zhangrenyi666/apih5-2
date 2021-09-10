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
import com.apih5.mybatis.pojo.ZjXmCqjxProjectSuperviseApproval;
import com.apih5.service.ZjXmCqjxProjectSuperviseApprovalService;

@RestController
public class ZjXmCqjxProjectSuperviseApprovalController {

    @Autowired(required = true)
    private ZjXmCqjxProjectSuperviseApprovalService zjXmCqjxProjectSuperviseApprovalService;

    @ApiOperation(value="查询重庆绩效项目督办工作评分", notes="查询重庆绩效项目督办工作评分")
    @ApiImplicitParam(name = "zjXmCqjxProjectSuperviseApproval", value = "重庆绩效项目督办工作评分entity", dataType = "ZjXmCqjxProjectSuperviseApproval")
    @RequireToken
    @PostMapping("/getZjXmCqjxProjectSuperviseApprovalList")
    public ResponseEntity getZjXmCqjxProjectSuperviseApprovalList(@RequestBody(required = false) ZjXmCqjxProjectSuperviseApproval zjXmCqjxProjectSuperviseApproval) {
        return zjXmCqjxProjectSuperviseApprovalService.getZjXmCqjxProjectSuperviseApprovalListByCondition(zjXmCqjxProjectSuperviseApproval);
    }

    @ApiOperation(value="查询详情重庆绩效项目督办工作评分", notes="查询详情重庆绩效项目督办工作评分")
    @ApiImplicitParam(name = "zjXmCqjxProjectSuperviseApproval", value = "重庆绩效项目督办工作评分entity", dataType = "ZjXmCqjxProjectSuperviseApproval")
    @RequireToken
    @PostMapping("/getZjXmCqjxProjectSuperviseApprovalDetails")
    public ResponseEntity getZjXmCqjxProjectSuperviseApprovalDetails(@RequestBody(required = false) ZjXmCqjxProjectSuperviseApproval zjXmCqjxProjectSuperviseApproval) {
        return zjXmCqjxProjectSuperviseApprovalService.getZjXmCqjxProjectSuperviseApprovalDetails(zjXmCqjxProjectSuperviseApproval);
    }

    @ApiOperation(value="新增重庆绩效项目督办工作评分", notes="新增重庆绩效项目督办工作评分")
    @ApiImplicitParam(name = "zjXmCqjxProjectSuperviseApproval", value = "重庆绩效项目督办工作评分entity", dataType = "ZjXmCqjxProjectSuperviseApproval")
    @RequireToken
    @PostMapping("/addZjXmCqjxProjectSuperviseApproval")
    public ResponseEntity addZjXmCqjxProjectSuperviseApproval(@RequestBody(required = false) ZjXmCqjxProjectSuperviseApproval zjXmCqjxProjectSuperviseApproval) {
        return zjXmCqjxProjectSuperviseApprovalService.saveZjXmCqjxProjectSuperviseApproval(zjXmCqjxProjectSuperviseApproval);
    }

    @ApiOperation(value="更新重庆绩效项目督办工作评分", notes="更新重庆绩效项目督办工作评分")
    @ApiImplicitParam(name = "zjXmCqjxProjectSuperviseApproval", value = "重庆绩效项目督办工作评分entity", dataType = "ZjXmCqjxProjectSuperviseApproval")
    @RequireToken
    @PostMapping("/updateZjXmCqjxProjectSuperviseApproval")
    public ResponseEntity updateZjXmCqjxProjectSuperviseApproval(@RequestBody(required = false) ZjXmCqjxProjectSuperviseApproval zjXmCqjxProjectSuperviseApproval) {
        return zjXmCqjxProjectSuperviseApprovalService.updateZjXmCqjxProjectSuperviseApproval(zjXmCqjxProjectSuperviseApproval);
    }

    @ApiOperation(value="删除重庆绩效项目督办工作评分", notes="删除重庆绩效项目督办工作评分")
    @ApiImplicitParam(name = "zjXmCqjxProjectSuperviseApprovalList", value = "重庆绩效项目督办工作评分List", dataType = "List<ZjXmCqjxProjectSuperviseApproval>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjXmCqjxProjectSuperviseApproval")
    public ResponseEntity batchDeleteUpdateZjXmCqjxProjectSuperviseApproval(@RequestBody(required = false) List<ZjXmCqjxProjectSuperviseApproval> zjXmCqjxProjectSuperviseApprovalList) {
        return zjXmCqjxProjectSuperviseApprovalService.batchDeleteUpdateZjXmCqjxProjectSuperviseApproval(zjXmCqjxProjectSuperviseApprovalList);
    }

}