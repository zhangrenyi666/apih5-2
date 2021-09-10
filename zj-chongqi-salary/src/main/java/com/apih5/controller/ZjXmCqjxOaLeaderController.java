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
import com.apih5.mybatis.pojo.ZjXmCqjxOaLeader;
import com.apih5.service.ZjXmCqjxOaLeaderService;

@RestController
public class ZjXmCqjxOaLeaderController {

    @Autowired(required = true)
    private ZjXmCqjxOaLeaderService zjXmCqjxOaLeaderService;

    @ApiOperation(value="查询重庆绩效oa领导", notes="查询重庆绩效oa领导")
    @ApiImplicitParam(name = "zjXmCqjxOaLeader", value = "重庆绩效oa领导entity", dataType = "ZjXmCqjxOaLeader")
    @RequireToken
    @PostMapping("/getZjXmCqjxOaLeaderList")
    public ResponseEntity getZjXmCqjxOaLeaderList(@RequestBody(required = false) ZjXmCqjxOaLeader zjXmCqjxOaLeader) {
        return zjXmCqjxOaLeaderService.getZjXmCqjxOaLeaderListByCondition(zjXmCqjxOaLeader);
    }

    @ApiOperation(value="查询详情重庆绩效oa领导", notes="查询详情重庆绩效oa领导")
    @ApiImplicitParam(name = "zjXmCqjxOaLeader", value = "重庆绩效oa领导entity", dataType = "ZjXmCqjxOaLeader")
    @RequireToken
    @PostMapping("/getZjXmCqjxOaLeaderDetails")
    public ResponseEntity getZjXmCqjxOaLeaderDetails(@RequestBody(required = false) ZjXmCqjxOaLeader zjXmCqjxOaLeader) {
        return zjXmCqjxOaLeaderService.getZjXmCqjxOaLeaderDetails(zjXmCqjxOaLeader);
    }

    @ApiOperation(value="新增重庆绩效oa领导", notes="新增重庆绩效oa领导")
    @ApiImplicitParam(name = "zjXmCqjxOaLeader", value = "重庆绩效oa领导entity", dataType = "ZjXmCqjxOaLeader")
    @RequireToken
    @PostMapping("/addZjXmCqjxOaLeader")
    public ResponseEntity addZjXmCqjxOaLeader(@RequestBody(required = false) ZjXmCqjxOaLeader zjXmCqjxOaLeader) {
        return zjXmCqjxOaLeaderService.saveZjXmCqjxOaLeader(zjXmCqjxOaLeader);
    }

    @ApiOperation(value="更新重庆绩效oa领导", notes="更新重庆绩效oa领导")
    @ApiImplicitParam(name = "zjXmCqjxOaLeader", value = "重庆绩效oa领导entity", dataType = "ZjXmCqjxOaLeader")
    @RequireToken
    @PostMapping("/updateZjXmCqjxOaLeader")
    public ResponseEntity updateZjXmCqjxOaLeader(@RequestBody(required = false) ZjXmCqjxOaLeader zjXmCqjxOaLeader) {
        return zjXmCqjxOaLeaderService.updateZjXmCqjxOaLeader(zjXmCqjxOaLeader);
    }

    @ApiOperation(value="删除重庆绩效oa领导", notes="删除重庆绩效oa领导")
    @ApiImplicitParam(name = "zjXmCqjxOaLeaderList", value = "重庆绩效oa领导List", dataType = "List<ZjXmCqjxOaLeader>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjXmCqjxOaLeader")
    public ResponseEntity batchDeleteUpdateZjXmCqjxOaLeader(@RequestBody(required = false) List<ZjXmCqjxOaLeader> zjXmCqjxOaLeaderList) {
        return zjXmCqjxOaLeaderService.batchDeleteUpdateZjXmCqjxOaLeader(zjXmCqjxOaLeaderList);
    }

}
