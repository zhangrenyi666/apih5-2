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
import com.apih5.mybatis.pojo.ZjXmCqjxProjectStaffAssistantDetailed;
import com.apih5.service.ZjXmCqjxProjectStaffAssistantDetailedService;

@RestController
public class ZjXmCqjxProjectStaffAssistantDetailedController {

    @Autowired(required = true)
    private ZjXmCqjxProjectStaffAssistantDetailedService zjXmCqjxProjectStaffAssistantDetailedService;

    @ApiOperation(value="查询项目员工月度考核明细", notes="查询项目员工月度考核明细")
    @ApiImplicitParam(name = "zjXmCqjxProjectStaffAssistantDetailed", value = "项目员工月度考核明细entity", dataType = "ZjXmCqjxProjectStaffAssistantDetailed")
    @RequireToken
    @PostMapping("/getZjXmCqjxProjectStaffAssistantDetailedList")
    public ResponseEntity getZjXmCqjxProjectStaffAssistantDetailedList(@RequestBody(required = false) ZjXmCqjxProjectStaffAssistantDetailed zjXmCqjxProjectStaffAssistantDetailed) {
        return zjXmCqjxProjectStaffAssistantDetailedService.getZjXmCqjxProjectStaffAssistantDetailedListByCondition(zjXmCqjxProjectStaffAssistantDetailed);
    }

    @ApiOperation(value="查询详情项目员工月度考核明细", notes="查询详情项目员工月度考核明细")
    @ApiImplicitParam(name = "zjXmCqjxProjectStaffAssistantDetailed", value = "项目员工月度考核明细entity", dataType = "ZjXmCqjxProjectStaffAssistantDetailed")
    @RequireToken
    @PostMapping("/getZjXmCqjxProjectStaffAssistantDetailedDetails")
    public ResponseEntity getZjXmCqjxProjectStaffAssistantDetailedDetails(@RequestBody(required = false) ZjXmCqjxProjectStaffAssistantDetailed zjXmCqjxProjectStaffAssistantDetailed) {
        return zjXmCqjxProjectStaffAssistantDetailedService.getZjXmCqjxProjectStaffAssistantDetailedDetails(zjXmCqjxProjectStaffAssistantDetailed);
    }

    @ApiOperation(value="新增项目员工月度考核明细", notes="新增项目员工月度考核明细")
    @ApiImplicitParam(name = "zjXmCqjxProjectStaffAssistantDetailed", value = "项目员工月度考核明细entity", dataType = "ZjXmCqjxProjectStaffAssistantDetailed")
    @RequireToken
    @PostMapping("/addZjXmCqjxProjectStaffAssistantDetailed")
    public ResponseEntity addZjXmCqjxProjectStaffAssistantDetailed(@RequestBody(required = false) ZjXmCqjxProjectStaffAssistantDetailed zjXmCqjxProjectStaffAssistantDetailed) {
        return zjXmCqjxProjectStaffAssistantDetailedService.saveZjXmCqjxProjectStaffAssistantDetailed(zjXmCqjxProjectStaffAssistantDetailed);
    }

    @ApiOperation(value="更新项目员工月度考核明细", notes="更新项目员工月度考核明细")
    @ApiImplicitParam(name = "zjXmCqjxProjectStaffAssistantDetailed", value = "项目员工月度考核明细entity", dataType = "ZjXmCqjxProjectStaffAssistantDetailed")
    @RequireToken
    @PostMapping("/updateZjXmCqjxProjectStaffAssistantDetailed")
    public ResponseEntity updateZjXmCqjxProjectStaffAssistantDetailed(@RequestBody(required = false) ZjXmCqjxProjectStaffAssistantDetailed zjXmCqjxProjectStaffAssistantDetailed) {
        return zjXmCqjxProjectStaffAssistantDetailedService.updateZjXmCqjxProjectStaffAssistantDetailed(zjXmCqjxProjectStaffAssistantDetailed);
    }

    @ApiOperation(value="删除项目员工月度考核明细", notes="删除项目员工月度考核明细")
    @ApiImplicitParam(name = "zjXmCqjxProjectStaffAssistantDetailedList", value = "项目员工月度考核明细List", dataType = "List<ZjXmCqjxProjectStaffAssistantDetailed>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjXmCqjxProjectStaffAssistantDetailed")
    public ResponseEntity batchDeleteUpdateZjXmCqjxProjectStaffAssistantDetailed(@RequestBody(required = false) List<ZjXmCqjxProjectStaffAssistantDetailed> zjXmCqjxProjectStaffAssistantDetailedList) {
        return zjXmCqjxProjectStaffAssistantDetailedService.batchDeleteUpdateZjXmCqjxProjectStaffAssistantDetailed(zjXmCqjxProjectStaffAssistantDetailedList);
    }

}