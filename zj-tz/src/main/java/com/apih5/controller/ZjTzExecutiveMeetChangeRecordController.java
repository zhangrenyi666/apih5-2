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
import com.apih5.mybatis.pojo.ZjTzExecutiveMeetChangeRecord;
import com.apih5.service.ZjTzExecutiveMeetChangeRecordService;

@RestController
public class ZjTzExecutiveMeetChangeRecordController {

    @Autowired(required = true)
    private ZjTzExecutiveMeetChangeRecordService zjTzExecutiveMeetChangeRecordService;

    @ApiOperation(value="查询董监高会变更记录", notes="查询董监高会变更记录")
    @ApiImplicitParam(name = "zjTzExecutiveMeetChangeRecord", value = "董监高会变更记录entity", dataType = "ZjTzExecutiveMeetChangeRecord")
    @RequireToken
    @PostMapping("/getZjTzExecutiveMeetChangeRecordList")
    public ResponseEntity getZjTzExecutiveMeetChangeRecordList(@RequestBody(required = false) ZjTzExecutiveMeetChangeRecord zjTzExecutiveMeetChangeRecord) {
        return zjTzExecutiveMeetChangeRecordService.getZjTzExecutiveMeetChangeRecordListByCondition(zjTzExecutiveMeetChangeRecord);
    }

    @ApiOperation(value="查询详情董监高会变更记录", notes="查询详情董监高会变更记录")
    @ApiImplicitParam(name = "zjTzExecutiveMeetChangeRecord", value = "董监高会变更记录entity", dataType = "ZjTzExecutiveMeetChangeRecord")
    @RequireToken
    @PostMapping("/getZjTzExecutiveMeetChangeRecordDetails")
    public ResponseEntity getZjTzExecutiveMeetChangeRecordDetails(@RequestBody(required = false) ZjTzExecutiveMeetChangeRecord zjTzExecutiveMeetChangeRecord) {
        return zjTzExecutiveMeetChangeRecordService.getZjTzExecutiveMeetChangeRecordDetails(zjTzExecutiveMeetChangeRecord);
    }

    @ApiOperation(value="新增董监高会变更记录", notes="新增董监高会变更记录")
    @ApiImplicitParam(name = "zjTzExecutiveMeetChangeRecord", value = "董监高会变更记录entity", dataType = "ZjTzExecutiveMeetChangeRecord")
    @RequireToken
    @PostMapping("/addZjTzExecutiveMeetChangeRecord")
    public ResponseEntity addZjTzExecutiveMeetChangeRecord(@RequestBody(required = false) ZjTzExecutiveMeetChangeRecord zjTzExecutiveMeetChangeRecord) {
        return zjTzExecutiveMeetChangeRecordService.saveZjTzExecutiveMeetChangeRecord(zjTzExecutiveMeetChangeRecord);
    }

    @ApiOperation(value="更新董监高会变更记录", notes="更新董监高会变更记录")
    @ApiImplicitParam(name = "zjTzExecutiveMeetChangeRecord", value = "董监高会变更记录entity", dataType = "ZjTzExecutiveMeetChangeRecord")
    @RequireToken
    @PostMapping("/updateZjTzExecutiveMeetChangeRecord")
    public ResponseEntity updateZjTzExecutiveMeetChangeRecord(@RequestBody(required = false) ZjTzExecutiveMeetChangeRecord zjTzExecutiveMeetChangeRecord) {
        return zjTzExecutiveMeetChangeRecordService.updateZjTzExecutiveMeetChangeRecord(zjTzExecutiveMeetChangeRecord);
    }

    @ApiOperation(value="删除董监高会变更记录", notes="删除董监高会变更记录")
    @ApiImplicitParam(name = "zjTzExecutiveMeetChangeRecordList", value = "董监高会变更记录List", dataType = "List<ZjTzExecutiveMeetChangeRecord>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjTzExecutiveMeetChangeRecord")
    public ResponseEntity batchDeleteUpdateZjTzExecutiveMeetChangeRecord(@RequestBody(required = false) List<ZjTzExecutiveMeetChangeRecord> zjTzExecutiveMeetChangeRecordList) {
        return zjTzExecutiveMeetChangeRecordService.batchDeleteUpdateZjTzExecutiveMeetChangeRecord(zjTzExecutiveMeetChangeRecordList);
    }

}