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
import com.apih5.mybatis.pojo.ZjTzContractCondition;
import com.apih5.service.ZjTzContractConditionService;

@RestController
public class ZjTzContractConditionController {

    @Autowired(required = true)
    private ZjTzContractConditionService zjTzContractConditionService;

    @ApiOperation(value="查询合同条件", notes="查询合同条件")
    @ApiImplicitParam(name = "zjTzContractCondition", value = "合同条件entity", dataType = "ZjTzContractCondition")
    @RequireToken
    @PostMapping("/getZjTzContractConditionList")
    public ResponseEntity getZjTzContractConditionList(@RequestBody(required = false) ZjTzContractCondition zjTzContractCondition) {
        return zjTzContractConditionService.getZjTzContractConditionListByCondition(zjTzContractCondition);
    }

    @ApiOperation(value="查询详情合同条件", notes="查询详情合同条件")
    @ApiImplicitParam(name = "zjTzContractCondition", value = "合同条件entity", dataType = "ZjTzContractCondition")
    @RequireToken
    @PostMapping("/getZjTzContractConditionDetails")
    public ResponseEntity getZjTzContractConditionDetails(@RequestBody(required = false) ZjTzContractCondition zjTzContractCondition) {
        return zjTzContractConditionService.getZjTzContractConditionDetails(zjTzContractCondition);
    }

    @ApiOperation(value="新增合同条件", notes="新增合同条件")
    @ApiImplicitParam(name = "zjTzContractCondition", value = "合同条件entity", dataType = "ZjTzContractCondition")
    @RequireToken
    @PostMapping("/addZjTzContractCondition")
    public ResponseEntity addZjTzContractCondition(@RequestBody(required = false) ZjTzContractCondition zjTzContractCondition) {
        return zjTzContractConditionService.saveZjTzContractCondition(zjTzContractCondition);
    }

    @ApiOperation(value="更新合同条件", notes="更新合同条件")
    @ApiImplicitParam(name = "zjTzContractCondition", value = "合同条件entity", dataType = "ZjTzContractCondition")
    @RequireToken
    @PostMapping("/updateZjTzContractCondition")
    public ResponseEntity updateZjTzContractCondition(@RequestBody(required = false) ZjTzContractCondition zjTzContractCondition) {
        return zjTzContractConditionService.updateZjTzContractCondition(zjTzContractCondition);
    }

    @ApiOperation(value="删除合同条件", notes="删除合同条件")
    @ApiImplicitParam(name = "zjTzContractConditionList", value = "合同条件List", dataType = "List<ZjTzContractCondition>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjTzContractCondition")
    public ResponseEntity batchDeleteUpdateZjTzContractCondition(@RequestBody(required = false) List<ZjTzContractCondition> zjTzContractConditionList) {
        return zjTzContractConditionService.batchDeleteUpdateZjTzContractCondition(zjTzContractConditionList);
    }

}
