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
import com.apih5.mybatis.pojo.ZjTzPolicyCountryReplyRecord;
import com.apih5.service.ZjTzPolicyCountryReplyRecordService;

@RestController
public class ZjTzPolicyCountryReplyRecordController {

    @Autowired(required = true)
    private ZjTzPolicyCountryReplyRecordService zjTzPolicyCountryReplyRecordService;

    @ApiOperation(value="查询宏观政策国家回复记录", notes="查询宏观政策国家回复记录")
    @ApiImplicitParam(name = "zjTzPolicyCountryReplyRecord", value = "宏观政策国家回复记录entity", dataType = "ZjTzPolicyCountryReplyRecord")
    @RequireToken
    @PostMapping("/getZjTzPolicyCountryReplyRecordList")
    public ResponseEntity getZjTzPolicyCountryReplyRecordList(@RequestBody(required = false) ZjTzPolicyCountryReplyRecord zjTzPolicyCountryReplyRecord) {
        return zjTzPolicyCountryReplyRecordService.getZjTzPolicyCountryReplyRecordListByCondition(zjTzPolicyCountryReplyRecord);
    }

    @ApiOperation(value="查询详情宏观政策国家回复记录", notes="查询详情宏观政策国家回复记录")
    @ApiImplicitParam(name = "zjTzPolicyCountryReplyRecord", value = "宏观政策国家回复记录entity", dataType = "ZjTzPolicyCountryReplyRecord")
    @RequireToken
    @PostMapping("/getZjTzPolicyCountryReplyRecordDetails")
    public ResponseEntity getZjTzPolicyCountryReplyRecordDetails(@RequestBody(required = false) ZjTzPolicyCountryReplyRecord zjTzPolicyCountryReplyRecord) {
        return zjTzPolicyCountryReplyRecordService.getZjTzPolicyCountryReplyRecordDetails(zjTzPolicyCountryReplyRecord);
    }

    @ApiOperation(value="新增宏观政策国家回复记录", notes="新增宏观政策国家回复记录")
    @ApiImplicitParam(name = "zjTzPolicyCountryReplyRecord", value = "宏观政策国家回复记录entity", dataType = "ZjTzPolicyCountryReplyRecord")
    @RequireToken
    @PostMapping("/addZjTzPolicyCountryReplyRecord")
    public ResponseEntity addZjTzPolicyCountryReplyRecord(@RequestBody(required = false) ZjTzPolicyCountryReplyRecord zjTzPolicyCountryReplyRecord) {
        return zjTzPolicyCountryReplyRecordService.saveZjTzPolicyCountryReplyRecord(zjTzPolicyCountryReplyRecord);
    }

    @ApiOperation(value="更新宏观政策国家回复记录", notes="更新宏观政策国家回复记录")
    @ApiImplicitParam(name = "zjTzPolicyCountryReplyRecord", value = "宏观政策国家回复记录entity", dataType = "ZjTzPolicyCountryReplyRecord")
    @RequireToken
    @PostMapping("/updateZjTzPolicyCountryReplyRecord")
    public ResponseEntity updateZjTzPolicyCountryReplyRecord(@RequestBody(required = false) ZjTzPolicyCountryReplyRecord zjTzPolicyCountryReplyRecord) {
        return zjTzPolicyCountryReplyRecordService.updateZjTzPolicyCountryReplyRecord(zjTzPolicyCountryReplyRecord);
    }

    @ApiOperation(value="删除宏观政策国家回复记录", notes="删除宏观政策国家回复记录")
    @ApiImplicitParam(name = "zjTzPolicyCountryReplyRecordList", value = "宏观政策国家回复记录List", dataType = "List<ZjTzPolicyCountryReplyRecord>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjTzPolicyCountryReplyRecord")
    public ResponseEntity batchDeleteUpdateZjTzPolicyCountryReplyRecord(@RequestBody(required = false) List<ZjTzPolicyCountryReplyRecord> zjTzPolicyCountryReplyRecordList) {
        return zjTzPolicyCountryReplyRecordService.batchDeleteUpdateZjTzPolicyCountryReplyRecord(zjTzPolicyCountryReplyRecordList);
    }

}
