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
import com.apih5.mybatis.pojo.ZjTzPolicyLocalReplyRecord;
import com.apih5.service.ZjTzPolicyLocalReplyRecordService;

@RestController
public class ZjTzPolicyLocalReplyRecordController {

    @Autowired(required = true)
    private ZjTzPolicyLocalReplyRecordService zjTzPolicyLocalReplyRecordService;

    @ApiOperation(value="查询宏观政策地方回复记录", notes="查询宏观政策地方回复记录")
    @ApiImplicitParam(name = "zjTzPolicyLocalReplyRecord", value = "宏观政策地方回复记录entity", dataType = "ZjTzPolicyLocalReplyRecord")
    @RequireToken
    @PostMapping("/getZjTzPolicyLocalReplyRecordList")
    public ResponseEntity getZjTzPolicyLocalReplyRecordList(@RequestBody(required = false) ZjTzPolicyLocalReplyRecord zjTzPolicyLocalReplyRecord) {
        return zjTzPolicyLocalReplyRecordService.getZjTzPolicyLocalReplyRecordListByCondition(zjTzPolicyLocalReplyRecord);
    }

    @ApiOperation(value="查询详情宏观政策地方回复记录", notes="查询详情宏观政策地方回复记录")
    @ApiImplicitParam(name = "zjTzPolicyLocalReplyRecord", value = "宏观政策地方回复记录entity", dataType = "ZjTzPolicyLocalReplyRecord")
    @RequireToken
    @PostMapping("/getZjTzPolicyLocalReplyRecordDetails")
    public ResponseEntity getZjTzPolicyLocalReplyRecordDetails(@RequestBody(required = false) ZjTzPolicyLocalReplyRecord zjTzPolicyLocalReplyRecord) {
        return zjTzPolicyLocalReplyRecordService.getZjTzPolicyLocalReplyRecordDetails(zjTzPolicyLocalReplyRecord);
    }

    @ApiOperation(value="新增宏观政策地方回复记录", notes="新增宏观政策地方回复记录")
    @ApiImplicitParam(name = "zjTzPolicyLocalReplyRecord", value = "宏观政策地方回复记录entity", dataType = "ZjTzPolicyLocalReplyRecord")
    @RequireToken
    @PostMapping("/addZjTzPolicyLocalReplyRecord")
    public ResponseEntity addZjTzPolicyLocalReplyRecord(@RequestBody(required = false) ZjTzPolicyLocalReplyRecord zjTzPolicyLocalReplyRecord) {
        return zjTzPolicyLocalReplyRecordService.saveZjTzPolicyLocalReplyRecord(zjTzPolicyLocalReplyRecord);
    }

    @ApiOperation(value="更新宏观政策地方回复记录", notes="更新宏观政策地方回复记录")
    @ApiImplicitParam(name = "zjTzPolicyLocalReplyRecord", value = "宏观政策地方回复记录entity", dataType = "ZjTzPolicyLocalReplyRecord")
    @RequireToken
    @PostMapping("/updateZjTzPolicyLocalReplyRecord")
    public ResponseEntity updateZjTzPolicyLocalReplyRecord(@RequestBody(required = false) ZjTzPolicyLocalReplyRecord zjTzPolicyLocalReplyRecord) {
        return zjTzPolicyLocalReplyRecordService.updateZjTzPolicyLocalReplyRecord(zjTzPolicyLocalReplyRecord);
    }

    @ApiOperation(value="删除宏观政策地方回复记录", notes="删除宏观政策地方回复记录")
    @ApiImplicitParam(name = "zjTzPolicyLocalReplyRecordList", value = "宏观政策地方回复记录List", dataType = "List<ZjTzPolicyLocalReplyRecord>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjTzPolicyLocalReplyRecord")
    public ResponseEntity batchDeleteUpdateZjTzPolicyLocalReplyRecord(@RequestBody(required = false) List<ZjTzPolicyLocalReplyRecord> zjTzPolicyLocalReplyRecordList) {
        return zjTzPolicyLocalReplyRecordService.batchDeleteUpdateZjTzPolicyLocalReplyRecord(zjTzPolicyLocalReplyRecordList);
    }

}
