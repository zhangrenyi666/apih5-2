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
import com.apih5.mybatis.pojo.ZjTzSizeControlRecord;
import com.apih5.service.ZjTzSizeControlRecordService;

@RestController
public class ZjTzSizeControlRecordController {

    @Autowired(required = true)
    private ZjTzSizeControlRecordService zjTzSizeControlRecordService;

    @ApiOperation(value="查询投资规模记录", notes="查询投资规模记录")
    @ApiImplicitParam(name = "zjTzSizeControlRecord", value = "投资规模记录entity", dataType = "ZjTzSizeControlRecord")
    @RequireToken
    @PostMapping("/getZjTzSizeControlRecordList")
    public ResponseEntity getZjTzSizeControlRecordList(@RequestBody(required = false) ZjTzSizeControlRecord zjTzSizeControlRecord) {
        return zjTzSizeControlRecordService.getZjTzSizeControlRecordListByCondition(zjTzSizeControlRecord);
    }

    @ApiOperation(value="查询详情投资规模记录", notes="查询详情投资规模记录")
    @ApiImplicitParam(name = "zjTzSizeControlRecord", value = "投资规模记录entity", dataType = "ZjTzSizeControlRecord")
    @RequireToken
    @PostMapping("/getZjTzSizeControlRecordDetails")
    public ResponseEntity getZjTzSizeControlRecordDetails(@RequestBody(required = false) ZjTzSizeControlRecord zjTzSizeControlRecord) {
        return zjTzSizeControlRecordService.getZjTzSizeControlRecordDetails(zjTzSizeControlRecord);
    }

    @ApiOperation(value="新增投资规模记录", notes="新增投资规模记录")
    @ApiImplicitParam(name = "zjTzSizeControlRecord", value = "投资规模记录entity", dataType = "ZjTzSizeControlRecord")
    @RequireToken
    @PostMapping("/addZjTzSizeControlRecord")
    public ResponseEntity addZjTzSizeControlRecord(@RequestBody(required = false) ZjTzSizeControlRecord zjTzSizeControlRecord) {
        return zjTzSizeControlRecordService.saveZjTzSizeControlRecord(zjTzSizeControlRecord);
    }

    @ApiOperation(value="更新投资规模记录", notes="更新投资规模记录")
    @ApiImplicitParam(name = "zjTzSizeControlRecord", value = "投资规模记录entity", dataType = "ZjTzSizeControlRecord")
    @RequireToken
    @PostMapping("/updateZjTzSizeControlRecord")
    public ResponseEntity updateZjTzSizeControlRecord(@RequestBody(required = false) ZjTzSizeControlRecord zjTzSizeControlRecord) {
        return zjTzSizeControlRecordService.updateZjTzSizeControlRecord(zjTzSizeControlRecord);
    }

    @ApiOperation(value="删除投资规模记录", notes="删除投资规模记录")
    @ApiImplicitParam(name = "zjTzSizeControlRecordList", value = "投资规模记录List", dataType = "List<ZjTzSizeControlRecord>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjTzSizeControlRecord")
    public ResponseEntity batchDeleteUpdateZjTzSizeControlRecord(@RequestBody(required = false) List<ZjTzSizeControlRecord> zjTzSizeControlRecordList) {
        return zjTzSizeControlRecordService.batchDeleteUpdateZjTzSizeControlRecord(zjTzSizeControlRecordList);
    }
    
    @ApiOperation(value="单条上报投资规模记录", notes="单条上报投资规模记录")
    @ApiImplicitParam(name = "zjTzSizeControlRecordList", value = "投资规模记录List", dataType = "List<ZjTzSizeControlRecord>")
    @RequireToken
    @PostMapping("/singleReleaseZjTzSizeControlRecord")
    public ResponseEntity singleReleaseZjTzSizeControlRecord(@RequestBody(required = false) ZjTzSizeControlRecord zjTzSizeControlRecord) {
        return zjTzSizeControlRecordService.singleReleaseZjTzSizeControlRecord(zjTzSizeControlRecord);
    }
    
    @ApiOperation(value="单条撤回投资规模记录", notes="单条撤回投资规模记录")
    @ApiImplicitParam(name = "zjTzSizeControlRecordList", value = "投资规模记录List", dataType = "List<ZjTzSizeControlRecord>")
    @RequireToken
    @PostMapping("/singleRecallZjTzSizeControlRecord")
    public ResponseEntity singleRecallZjTzSizeControlRecord(@RequestBody(required = false) ZjTzSizeControlRecord zjTzSizeControlRecord) {
        return zjTzSizeControlRecordService.singleRecallZjTzSizeControlRecord(zjTzSizeControlRecord);
    }
    
    @ApiOperation(value="审查完成", notes="审查完成")
    @ApiImplicitParam(name = "zjTzSizeControlRecordList", value = "投资规模记录List", dataType = "List<ZjTzSizeControlRecord>")
    @RequireToken
    @PostMapping("/checkAndFinishZjTzSizeControlRecord")
    public ResponseEntity checkAndFinishZjTzSizeControlRecord(@RequestBody(required = false) ZjTzSizeControlRecord zjTzSizeControlRecord) {
        return zjTzSizeControlRecordService.checkAndFinishZjTzSizeControlRecord(zjTzSizeControlRecord);
    }

}
