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
import com.apih5.mybatis.pojo.ZjTzContractConditionRecord;
import com.apih5.service.ZjTzContractConditionRecordService;

@RestController
public class ZjTzContractConditionRecordController {

    @Autowired(required = true)
    private ZjTzContractConditionRecordService zjTzContractConditionRecordService;

    @ApiOperation(value="查询合同条件记录", notes="查询合同条件记录")
    @ApiImplicitParam(name = "zjTzContractConditionRecord", value = "合同条件记录entity", dataType = "ZjTzContractConditionRecord")
    @RequireToken
    @PostMapping("/getZjTzContractConditionRecordList")
    public ResponseEntity getZjTzContractConditionRecordList(@RequestBody(required = false) ZjTzContractConditionRecord zjTzContractConditionRecord) {
        return zjTzContractConditionRecordService.getZjTzContractConditionRecordListByCondition(zjTzContractConditionRecord);
    }

    @ApiOperation(value="查询详情合同条件记录", notes="查询详情合同条件记录")
    @ApiImplicitParam(name = "zjTzContractConditionRecord", value = "合同条件记录entity", dataType = "ZjTzContractConditionRecord")
    @RequireToken
    @PostMapping("/getZjTzContractConditionRecordDetails")
    public ResponseEntity getZjTzContractConditionRecordDetails(@RequestBody(required = false) ZjTzContractConditionRecord zjTzContractConditionRecord) {
        return zjTzContractConditionRecordService.getZjTzContractConditionRecordDetails(zjTzContractConditionRecord);
    }

    @ApiOperation(value="新增合同条件记录", notes="新增合同条件记录")
    @ApiImplicitParam(name = "zjTzContractConditionRecord", value = "合同条件记录entity", dataType = "ZjTzContractConditionRecord")
    @RequireToken
    @PostMapping("/addZjTzContractConditionRecord")
    public ResponseEntity addZjTzContractConditionRecord(@RequestBody(required = false) ZjTzContractConditionRecord zjTzContractConditionRecord) {
        return zjTzContractConditionRecordService.saveZjTzContractConditionRecord(zjTzContractConditionRecord);
    }

    @ApiOperation(value="更新合同条件记录", notes="更新合同条件记录")
    @ApiImplicitParam(name = "zjTzContractConditionRecord", value = "合同条件记录entity", dataType = "ZjTzContractConditionRecord")
    @RequireToken
    @PostMapping("/updateZjTzContractConditionRecord")
    public ResponseEntity updateZjTzContractConditionRecord(@RequestBody(required = false) ZjTzContractConditionRecord zjTzContractConditionRecord) {
        return zjTzContractConditionRecordService.updateZjTzContractConditionRecord(zjTzContractConditionRecord);
    }

    @ApiOperation(value="删除合同条件记录", notes="删除合同条件记录")
    @ApiImplicitParam(name = "zjTzContractConditionRecordList", value = "合同条件记录List", dataType = "List<ZjTzContractConditionRecord>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjTzContractConditionRecord")
    public ResponseEntity batchDeleteUpdateZjTzContractConditionRecord(@RequestBody(required = false) List<ZjTzContractConditionRecord> zjTzContractConditionRecordList) {
        return zjTzContractConditionRecordService.batchDeleteUpdateZjTzContractConditionRecord(zjTzContractConditionRecordList);
    }

}
