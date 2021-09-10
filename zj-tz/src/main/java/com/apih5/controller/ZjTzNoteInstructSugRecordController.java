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
import com.apih5.mybatis.pojo.ZjTzNoteInstructSugRecord;
import com.apih5.service.ZjTzNoteInstructSugRecordService;

@RestController
public class ZjTzNoteInstructSugRecordController {

    @Autowired(required = true)
    private ZjTzNoteInstructSugRecordService zjTzNoteInstructSugRecordService;

    @ApiOperation(value="查询公告、指令、建议推送记录", notes="查询公告、指令、建议推送记录")
    @ApiImplicitParam(name = "zjTzNoteInstructSugRecord", value = "公告、指令、建议推送记录entity", dataType = "ZjTzNoteInstructSugRecord")
    @RequireToken
    @PostMapping("/getZjTzNoteInstructSugRecordList")
    public ResponseEntity getZjTzNoteInstructSugRecordList(@RequestBody(required = false) ZjTzNoteInstructSugRecord zjTzNoteInstructSugRecord) {
        return zjTzNoteInstructSugRecordService.getZjTzNoteInstructSugRecordListByCondition(zjTzNoteInstructSugRecord);
    }

    @ApiOperation(value="查询详情公告、指令、建议推送记录", notes="查询详情公告、指令、建议推送记录")
    @ApiImplicitParam(name = "zjTzNoteInstructSugRecord", value = "公告、指令、建议推送记录entity", dataType = "ZjTzNoteInstructSugRecord")
    @RequireToken
    @PostMapping("/getZjTzNoteInstructSugRecordDetails")
    public ResponseEntity getZjTzNoteInstructSugRecordDetails(@RequestBody(required = false) ZjTzNoteInstructSugRecord zjTzNoteInstructSugRecord) {
        return zjTzNoteInstructSugRecordService.getZjTzNoteInstructSugRecordDetails(zjTzNoteInstructSugRecord);
    }

    @ApiOperation(value="定向推送新增公告、指令、建议推送记录", notes="新增公告、指令、建议推送记录")
    @ApiImplicitParam(name = "zjTzNoteInstructSugRecord", value = "公告、指令、建议推送记录entity", dataType = "ZjTzNoteInstructSugRecord")
    @RequireToken
    @PostMapping("/addZjTzNoteInstructSugRecord")
    public ResponseEntity addZjTzNoteInstructSugRecord(@RequestBody(required = false) ZjTzNoteInstructSugRecord zjTzNoteInstructSugRecord) {
        return zjTzNoteInstructSugRecordService.saveZjTzNoteInstructSugRecord(zjTzNoteInstructSugRecord);
    }

    @ApiOperation(value="更新公告、指令、建议推送记录", notes="更新公告、指令、建议推送记录")
    @ApiImplicitParam(name = "zjTzNoteInstructSugRecord", value = "公告、指令、建议推送记录entity", dataType = "ZjTzNoteInstructSugRecord")
    @RequireToken
    @PostMapping("/updateZjTzNoteInstructSugRecord")
    public ResponseEntity updateZjTzNoteInstructSugRecord(@RequestBody(required = false) ZjTzNoteInstructSugRecord zjTzNoteInstructSugRecord) {
        return zjTzNoteInstructSugRecordService.updateZjTzNoteInstructSugRecord(zjTzNoteInstructSugRecord);
    }

    @ApiOperation(value="删除公告、指令、建议推送记录", notes="删除公告、指令、建议推送记录")
    @ApiImplicitParam(name = "zjTzNoteInstructSugRecordList", value = "公告、指令、建议推送记录List", dataType = "List<ZjTzNoteInstructSugRecord>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjTzNoteInstructSugRecord")
    public ResponseEntity batchDeleteUpdateZjTzNoteInstructSugRecord(@RequestBody(required = false) List<ZjTzNoteInstructSugRecord> zjTzNoteInstructSugRecordList) {
        return zjTzNoteInstructSugRecordService.batchDeleteUpdateZjTzNoteInstructSugRecord(zjTzNoteInstructSugRecordList);
    }

}
