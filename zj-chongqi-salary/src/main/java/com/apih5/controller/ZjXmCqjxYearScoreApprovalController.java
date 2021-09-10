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
import com.apih5.mybatis.pojo.ZjXmCqjxYearScoreApproval;
import com.apih5.service.ZjXmCqjxYearScoreApprovalService;

@RestController
public class ZjXmCqjxYearScoreApprovalController {

    @Autowired(required = true)
    private ZjXmCqjxYearScoreApprovalService zjXmCqjxYearScoreApprovalService;

    @ApiOperation(value="查询重庆绩效公司机关年度评分", notes="查询重庆绩效公司机关年度评分")
    @ApiImplicitParam(name = "zjXmCqjxYearScoreApproval", value = "重庆绩效公司机关年度评分entity", dataType = "ZjXmCqjxYearScoreApproval")
    @RequireToken
    @PostMapping("/getZjXmCqjxYearScoreApprovalList")
    public ResponseEntity getZjXmCqjxYearScoreApprovalList(@RequestBody(required = false) ZjXmCqjxYearScoreApproval zjXmCqjxYearScoreApproval) {
        return zjXmCqjxYearScoreApprovalService.getZjXmCqjxYearScoreApprovalListByCondition(zjXmCqjxYearScoreApproval);
    }

    @ApiOperation(value="查询详情重庆绩效公司机关年度评分", notes="查询详情重庆绩效公司机关年度评分")
    @ApiImplicitParam(name = "zjXmCqjxYearScoreApproval", value = "重庆绩效公司机关年度评分entity", dataType = "ZjXmCqjxYearScoreApproval")
    @RequireToken
    @PostMapping("/getZjXmCqjxYearScoreApprovalDetails")
    public ResponseEntity getZjXmCqjxYearScoreApprovalDetails(@RequestBody(required = false) ZjXmCqjxYearScoreApproval zjXmCqjxYearScoreApproval) {
        return zjXmCqjxYearScoreApprovalService.getZjXmCqjxYearScoreApprovalDetails(zjXmCqjxYearScoreApproval);
    }
    
    @ApiOperation(value="新增重庆绩效公司机关年度评分", notes="新增重庆绩效公司机关年度评分")
    @ApiImplicitParam(name = "zjXmCqjxYearScoreApproval", value = "重庆绩效公司机关年度评分entity", dataType = "ZjXmCqjxYearScoreApproval")
    @RequireToken
    @PostMapping("/addZjXmCqjxYearScoreApproval")
    public ResponseEntity addZjXmCqjxYearScoreApproval(@RequestBody(required = false) ZjXmCqjxYearScoreApproval zjXmCqjxYearScoreApproval) {
        return zjXmCqjxYearScoreApprovalService.saveZjXmCqjxYearScoreApproval(zjXmCqjxYearScoreApproval);
    }

    @ApiOperation(value="更新重庆绩效公司机关年度评分", notes="更新重庆绩效公司机关年度评分")
    @ApiImplicitParam(name = "zjXmCqjxYearScoreApproval", value = "重庆绩效公司机关年度评分entity", dataType = "ZjXmCqjxYearScoreApproval")
    @RequireToken
    @PostMapping("/updateZjXmCqjxYearScoreApproval")
    public ResponseEntity updateZjXmCqjxYearScoreApproval(@RequestBody(required = false) ZjXmCqjxYearScoreApproval zjXmCqjxYearScoreApproval) {
        return zjXmCqjxYearScoreApprovalService.updateZjXmCqjxYearScoreApproval(zjXmCqjxYearScoreApproval);
    }

    @ApiOperation(value="删除重庆绩效公司机关年度评分", notes="删除重庆绩效公司机关年度评分")
    @ApiImplicitParam(name = "zjXmCqjxYearScoreApprovalList", value = "重庆绩效公司机关年度评分List", dataType = "List<ZjXmCqjxYearScoreApproval>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjXmCqjxYearScoreApproval")
    public ResponseEntity batchDeleteUpdateZjXmCqjxYearScoreApproval(@RequestBody(required = false) List<ZjXmCqjxYearScoreApproval> zjXmCqjxYearScoreApprovalList) {
        return zjXmCqjxYearScoreApprovalService.batchDeleteUpdateZjXmCqjxYearScoreApproval(zjXmCqjxYearScoreApprovalList);
    }

}
