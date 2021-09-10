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
import com.apih5.mybatis.pojo.ZjXmCqjxProjectSecretaryrAssistantDetailed;
import com.apih5.service.ZjXmCqjxProjectSecretaryrAssistantDetailedService;

@RestController
public class ZjXmCqjxProjectSecretaryrAssistantDetailedController {

    @Autowired(required = true)
    private ZjXmCqjxProjectSecretaryrAssistantDetailedService zjXmCqjxProjectSecretaryrAssistantDetailedService;

    @ApiOperation(value="查询项目书记考核明细", notes="查询项目书记考核明细")
    @ApiImplicitParam(name = "zjXmCqjxProjectSecretaryrAssistantDetailed", value = "项目书记考核明细entity", dataType = "ZjXmCqjxProjectSecretaryrAssistantDetailed")
    @RequireToken
    @PostMapping("/getZjXmCqjxProjectSecretaryrAssistantDetailedList")
    public ResponseEntity getZjXmCqjxProjectSecretaryrAssistantDetailedList(@RequestBody(required = false) ZjXmCqjxProjectSecretaryrAssistantDetailed zjXmCqjxProjectSecretaryrAssistantDetailed) {
        return zjXmCqjxProjectSecretaryrAssistantDetailedService.getZjXmCqjxProjectSecretaryrAssistantDetailedListByCondition(zjXmCqjxProjectSecretaryrAssistantDetailed);
    }

    @ApiOperation(value="查询详情项目书记考核明细", notes="查询详情项目书记考核明细")
    @ApiImplicitParam(name = "zjXmCqjxProjectSecretaryrAssistantDetailed", value = "项目书记考核明细entity", dataType = "ZjXmCqjxProjectSecretaryrAssistantDetailed")
    @RequireToken
    @PostMapping("/getZjXmCqjxProjectSecretaryrAssistantDetailedDetails")
    public ResponseEntity getZjXmCqjxProjectSecretaryrAssistantDetailedDetails(@RequestBody(required = false) ZjXmCqjxProjectSecretaryrAssistantDetailed zjXmCqjxProjectSecretaryrAssistantDetailed) {
        return zjXmCqjxProjectSecretaryrAssistantDetailedService.getZjXmCqjxProjectSecretaryrAssistantDetailedDetails(zjXmCqjxProjectSecretaryrAssistantDetailed);
    }

    @ApiOperation(value="新增项目书记考核明细", notes="新增项目书记考核明细")
    @ApiImplicitParam(name = "zjXmCqjxProjectSecretaryrAssistantDetailed", value = "项目书记考核明细entity", dataType = "ZjXmCqjxProjectSecretaryrAssistantDetailed")
    @RequireToken
    @PostMapping("/addZjXmCqjxProjectSecretaryrAssistantDetailed")
    public ResponseEntity addZjXmCqjxProjectSecretaryrAssistantDetailed(@RequestBody(required = false) ZjXmCqjxProjectSecretaryrAssistantDetailed zjXmCqjxProjectSecretaryrAssistantDetailed) {
        return zjXmCqjxProjectSecretaryrAssistantDetailedService.saveZjXmCqjxProjectSecretaryrAssistantDetailed(zjXmCqjxProjectSecretaryrAssistantDetailed);
    }

    @ApiOperation(value="更新项目书记考核明细", notes="更新项目书记考核明细")
    @ApiImplicitParam(name = "zjXmCqjxProjectSecretaryrAssistantDetailed", value = "项目书记考核明细entity", dataType = "ZjXmCqjxProjectSecretaryrAssistantDetailed")
    @RequireToken
    @PostMapping("/updateZjXmCqjxProjectSecretaryrAssistantDetailed")
    public ResponseEntity updateZjXmCqjxProjectSecretaryrAssistantDetailed(@RequestBody(required = false) ZjXmCqjxProjectSecretaryrAssistantDetailed zjXmCqjxProjectSecretaryrAssistantDetailed) {
        return zjXmCqjxProjectSecretaryrAssistantDetailedService.updateZjXmCqjxProjectSecretaryrAssistantDetailed(zjXmCqjxProjectSecretaryrAssistantDetailed);
    }

    @ApiOperation(value="删除项目书记考核明细", notes="删除项目书记考核明细")
    @ApiImplicitParam(name = "zjXmCqjxProjectSecretaryrAssistantDetailedList", value = "项目书记考核明细List", dataType = "List<ZjXmCqjxProjectSecretaryrAssistantDetailed>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjXmCqjxProjectSecretaryrAssistantDetailed")
    public ResponseEntity batchDeleteUpdateZjXmCqjxProjectSecretaryrAssistantDetailed(@RequestBody(required = false) List<ZjXmCqjxProjectSecretaryrAssistantDetailed> zjXmCqjxProjectSecretaryrAssistantDetailedList) {
        return zjXmCqjxProjectSecretaryrAssistantDetailedService.batchDeleteUpdateZjXmCqjxProjectSecretaryrAssistantDetailed(zjXmCqjxProjectSecretaryrAssistantDetailedList);
    }

}