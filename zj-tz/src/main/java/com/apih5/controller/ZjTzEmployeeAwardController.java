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
import com.apih5.mybatis.pojo.ZjTzEmployeeAward;
import com.apih5.service.ZjTzEmployeeAwardService;

@RestController
public class ZjTzEmployeeAwardController {

    @Autowired(required = true)
    private ZjTzEmployeeAwardService zjTzEmployeeAwardService;

    @ApiOperation(value="查询人员库-奖励情况", notes="查询人员库-奖励情况")
    @ApiImplicitParam(name = "zjTzEmployeeAward", value = "人员库-奖励情况entity", dataType = "ZjTzEmployeeAward")
    @RequireToken
    @PostMapping("/getZjTzEmployeeAwardList")
    public ResponseEntity getZjTzEmployeeAwardList(@RequestBody(required = false) ZjTzEmployeeAward zjTzEmployeeAward) {
        return zjTzEmployeeAwardService.getZjTzEmployeeAwardListByCondition(zjTzEmployeeAward);
    }

    @ApiOperation(value="查询详情人员库-奖励情况", notes="查询详情人员库-奖励情况")
    @ApiImplicitParam(name = "zjTzEmployeeAward", value = "人员库-奖励情况entity", dataType = "ZjTzEmployeeAward")
    @RequireToken
    @PostMapping("/getZjTzEmployeeAwardDetails")
    public ResponseEntity getZjTzEmployeeAwardDetails(@RequestBody(required = false) ZjTzEmployeeAward zjTzEmployeeAward) {
        return zjTzEmployeeAwardService.getZjTzEmployeeAwardDetails(zjTzEmployeeAward);
    }

    @ApiOperation(value="新增人员库-奖励情况", notes="新增人员库-奖励情况")
    @ApiImplicitParam(name = "zjTzEmployeeAward", value = "人员库-奖励情况entity", dataType = "ZjTzEmployeeAward")
    @RequireToken
    @PostMapping("/addZjTzEmployeeAward")
    public ResponseEntity addZjTzEmployeeAward(@RequestBody(required = false) ZjTzEmployeeAward zjTzEmployeeAward) {
        return zjTzEmployeeAwardService.saveZjTzEmployeeAward(zjTzEmployeeAward);
    }

    @ApiOperation(value="更新人员库-奖励情况", notes="更新人员库-奖励情况")
    @ApiImplicitParam(name = "zjTzEmployeeAward", value = "人员库-奖励情况entity", dataType = "ZjTzEmployeeAward")
    @RequireToken
    @PostMapping("/updateZjTzEmployeeAward")
    public ResponseEntity updateZjTzEmployeeAward(@RequestBody(required = false) ZjTzEmployeeAward zjTzEmployeeAward) {
        return zjTzEmployeeAwardService.updateZjTzEmployeeAward(zjTzEmployeeAward);
    }

    @ApiOperation(value="删除人员库-奖励情况", notes="删除人员库-奖励情况")
    @ApiImplicitParam(name = "zjTzEmployeeAwardList", value = "人员库-奖励情况List", dataType = "List<ZjTzEmployeeAward>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjTzEmployeeAward")
    public ResponseEntity batchDeleteUpdateZjTzEmployeeAward(@RequestBody(required = false) List<ZjTzEmployeeAward> zjTzEmployeeAwardList) {
        return zjTzEmployeeAwardService.batchDeleteUpdateZjTzEmployeeAward(zjTzEmployeeAwardList);
    }

}
