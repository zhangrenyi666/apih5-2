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
import com.apih5.mybatis.pojo.ZjXmCqjxStaffAssistantDetailed;
import com.apih5.service.ZjXmCqjxStaffAssistantDetailedService;

@RestController
public class ZjXmCqjxStaffAssistantDetailedController {

    @Autowired(required = true)
    private ZjXmCqjxStaffAssistantDetailedService zjXmCqjxStaffAssistantDetailedService;

    @ApiOperation(value="查询员工季度考核明细", notes="查询员工季度考核明细")
    @ApiImplicitParam(name = "zjXmCqjxStaffAssistantDetailed", value = "员工季度考核明细entity", dataType = "ZjXmCqjxStaffAssistantDetailed")
    @RequireToken
    @PostMapping("/getZjXmCqjxStaffAssistantDetailedList")
    public ResponseEntity getZjXmCqjxStaffAssistantDetailedList(@RequestBody(required = false) ZjXmCqjxStaffAssistantDetailed zjXmCqjxStaffAssistantDetailed) {
        return zjXmCqjxStaffAssistantDetailedService.getZjXmCqjxStaffAssistantDetailedListByCondition(zjXmCqjxStaffAssistantDetailed);
    }

    @ApiOperation(value="查询详情员工季度考核明细", notes="查询详情员工季度考核明细")
    @ApiImplicitParam(name = "zjXmCqjxStaffAssistantDetailed", value = "员工季度考核明细entity", dataType = "ZjXmCqjxStaffAssistantDetailed")
    @RequireToken
    @PostMapping("/getZjXmCqjxStaffAssistantDetailedDetails")
    public ResponseEntity getZjXmCqjxStaffAssistantDetailedDetails(@RequestBody(required = false) ZjXmCqjxStaffAssistantDetailed zjXmCqjxStaffAssistantDetailed) {
        return zjXmCqjxStaffAssistantDetailedService.getZjXmCqjxStaffAssistantDetailedDetails(zjXmCqjxStaffAssistantDetailed);
    }

    @ApiOperation(value="新增员工季度考核明细", notes="新增员工季度考核明细")
    @ApiImplicitParam(name = "zjXmCqjxStaffAssistantDetailed", value = "员工季度考核明细entity", dataType = "ZjXmCqjxStaffAssistantDetailed")
    @RequireToken
    @PostMapping("/addZjXmCqjxStaffAssistantDetailed")
    public ResponseEntity addZjXmCqjxStaffAssistantDetailed(@RequestBody(required = false) ZjXmCqjxStaffAssistantDetailed zjXmCqjxStaffAssistantDetailed) {
        return zjXmCqjxStaffAssistantDetailedService.saveZjXmCqjxStaffAssistantDetailed(zjXmCqjxStaffAssistantDetailed);
    }

    @ApiOperation(value="更新员工季度考核明细", notes="更新员工季度考核明细")
    @ApiImplicitParam(name = "zjXmCqjxStaffAssistantDetailed", value = "员工季度考核明细entity", dataType = "ZjXmCqjxStaffAssistantDetailed")
    @RequireToken
    @PostMapping("/updateZjXmCqjxStaffAssistantDetailed")
    public ResponseEntity updateZjXmCqjxStaffAssistantDetailed(@RequestBody(required = false) ZjXmCqjxStaffAssistantDetailed zjXmCqjxStaffAssistantDetailed) {
        return zjXmCqjxStaffAssistantDetailedService.updateZjXmCqjxStaffAssistantDetailed(zjXmCqjxStaffAssistantDetailed);
    }

    @ApiOperation(value="删除员工季度考核明细", notes="删除员工季度考核明细")
    @ApiImplicitParam(name = "zjXmCqjxStaffAssistantDetailedList", value = "员工季度考核明细List", dataType = "List<ZjXmCqjxStaffAssistantDetailed>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjXmCqjxStaffAssistantDetailed")
    public ResponseEntity batchDeleteUpdateZjXmCqjxStaffAssistantDetailed(@RequestBody(required = false) List<ZjXmCqjxStaffAssistantDetailed> zjXmCqjxStaffAssistantDetailedList) {
        return zjXmCqjxStaffAssistantDetailedService.batchDeleteUpdateZjXmCqjxStaffAssistantDetailed(zjXmCqjxStaffAssistantDetailedList);
    }

}
