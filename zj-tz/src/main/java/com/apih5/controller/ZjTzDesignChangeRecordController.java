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
import com.apih5.mybatis.pojo.ZjTzDesignChangeRecord;
import com.apih5.service.ZjTzDesignChangeRecordService;

@RestController
public class ZjTzDesignChangeRecordController {

    @Autowired(required = true)
    private ZjTzDesignChangeRecordService zjTzDesignChangeRecordService;

    @ApiOperation(value="查询设计变更管理记录", notes="查询设计变更管理记录")
    @ApiImplicitParam(name = "zjTzDesignChangeRecord", value = "设计变更管理记录entity", dataType = "ZjTzDesignChangeRecord")
    @RequireToken
    @PostMapping("/getZjTzDesignChangeRecordList")
    public ResponseEntity getZjTzDesignChangeRecordList(@RequestBody(required = false) ZjTzDesignChangeRecord zjTzDesignChangeRecord) {
        return zjTzDesignChangeRecordService.getZjTzDesignChangeRecordListByCondition(zjTzDesignChangeRecord);
    }

    @ApiOperation(value="查询详情设计变更管理记录", notes="查询详情设计变更管理记录")
    @ApiImplicitParam(name = "zjTzDesignChangeRecord", value = "设计变更管理记录entity", dataType = "ZjTzDesignChangeRecord")
    @RequireToken
    @PostMapping("/getZjTzDesignChangeRecordDetails")
    public ResponseEntity getZjTzDesignChangeRecordDetails(@RequestBody(required = false) ZjTzDesignChangeRecord zjTzDesignChangeRecord) {
        return zjTzDesignChangeRecordService.getZjTzDesignChangeRecordDetails(zjTzDesignChangeRecord);
    }

    @ApiOperation(value="新增设计变更管理记录", notes="新增设计变更管理记录")
    @ApiImplicitParam(name = "zjTzDesignChangeRecord", value = "设计变更管理记录entity", dataType = "ZjTzDesignChangeRecord")
    @RequireToken
    @PostMapping("/addZjTzDesignChangeRecord")
    public ResponseEntity addZjTzDesignChangeRecord(@RequestBody(required = false) ZjTzDesignChangeRecord zjTzDesignChangeRecord) {
        return zjTzDesignChangeRecordService.saveZjTzDesignChangeRecord(zjTzDesignChangeRecord);
    }

    @ApiOperation(value="更新设计变更管理记录", notes="更新设计变更管理记录")
    @ApiImplicitParam(name = "zjTzDesignChangeRecord", value = "设计变更管理记录entity", dataType = "ZjTzDesignChangeRecord")
    @RequireToken
    @PostMapping("/updateZjTzDesignChangeRecord")
    public ResponseEntity updateZjTzDesignChangeRecord(@RequestBody(required = false) ZjTzDesignChangeRecord zjTzDesignChangeRecord) {
        return zjTzDesignChangeRecordService.updateZjTzDesignChangeRecord(zjTzDesignChangeRecord);
    }

    @ApiOperation(value="删除设计变更管理记录", notes="删除设计变更管理记录")
    @ApiImplicitParam(name = "zjTzDesignChangeRecordList", value = "设计变更管理记录List", dataType = "List<ZjTzDesignChangeRecord>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjTzDesignChangeRecord")
    public ResponseEntity batchDeleteUpdateZjTzDesignChangeRecord(@RequestBody(required = false) List<ZjTzDesignChangeRecord> zjTzDesignChangeRecordList) {
        return zjTzDesignChangeRecordService.batchDeleteUpdateZjTzDesignChangeRecord(zjTzDesignChangeRecordList);
    }
    
    @ApiOperation(value="批量上报设计变更管理记录", notes="批量上报设计变更管理记录")
    @ApiImplicitParam(name = "zjTzDesignChangeRecordList", value = "设计变更管理记录List", dataType = "List<ZjTzDesignChangeRecord>")
    @RequireToken
    @PostMapping("/batchReleaseZjTzDesignChangeRecord")
    public ResponseEntity batchReleaseZjTzDesignChangeRecord(@RequestBody(required = false) List<ZjTzDesignChangeRecord> zjTzDesignChangeRecordList) {
        return zjTzDesignChangeRecordService.batchReleaseZjTzDesignChangeRecord(zjTzDesignChangeRecordList);
    }
    
    @ApiOperation(value="批量退回设计变更管理记录", notes="批量退回设计变更管理记录")
    @ApiImplicitParam(name = "zjTzDesignChangeRecordList", value = "设计变更管理记录List", dataType = "List<ZjTzDesignChangeRecord>")
    @RequireToken
    @PostMapping("/batchRecallZjTzDesignChangeRecord")
    public ResponseEntity batchRecallZjTzDesignChangeRecord(@RequestBody(required = false) List<ZjTzDesignChangeRecord> zjTzDesignChangeRecordList) {
        return zjTzDesignChangeRecordService.batchRecallZjTzDesignChangeRecord(zjTzDesignChangeRecordList);
    }

    @ApiOperation(value="审查完成设计变更管理记录", notes="审查完成设计变更管理记录")
    @ApiImplicitParam(name = "zjTzDesignChangeRecordList", value = "设计变更管理记录List", dataType = "List<ZjTzDesignChangeRecord>")
    @RequireToken
    @PostMapping("/checkAndFinishZjTzDesignChangeRecord")
    public ResponseEntity checkAndFinishZjTzDesignChangeRecord(@RequestBody(required = false) ZjTzDesignChangeRecord zjTzDesignChangeRecord) {
        return zjTzDesignChangeRecordService.checkAndFinishZjTzDesignChangeRecord(zjTzDesignChangeRecord);
    }
}
