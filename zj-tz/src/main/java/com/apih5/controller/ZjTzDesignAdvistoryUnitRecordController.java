package com.apih5.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import com.apih5.framework.annotation.RequireToken;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjTzDesignAdvistoryUnitRecord;
import com.apih5.service.ZjTzDesignAdvistoryUnitRecordService;

@RestController
public class ZjTzDesignAdvistoryUnitRecordController {

    @Autowired(required = true)
    private ZjTzDesignAdvistoryUnitRecordService zjTzDesignAdvistoryUnitRecordService;

    @ApiOperation(value="查询设计、咨询单位管理记录", notes="查询设计、咨询单位管理记录")
    @ApiImplicitParam(name = "zjTzDesignAdvistoryUnitRecord", value = "设计、咨询单位管理记录entity", dataType = "ZjTzDesignAdvistoryUnitRecord")
    @RequireToken
    @PostMapping("/getZjTzDesignAdvistoryUnitRecordList")
    public ResponseEntity getZjTzDesignAdvistoryUnitRecordList(@RequestBody(required = false) ZjTzDesignAdvistoryUnitRecord zjTzDesignAdvistoryUnitRecord) {
        return zjTzDesignAdvistoryUnitRecordService.getZjTzDesignAdvistoryUnitRecordListByCondition(zjTzDesignAdvistoryUnitRecord);
    }

    @ApiOperation(value="查询详情设计、咨询单位管理记录", notes="查询详情设计、咨询单位管理记录")
    @ApiImplicitParam(name = "zjTzDesignAdvistoryUnitRecord", value = "设计、咨询单位管理记录entity", dataType = "ZjTzDesignAdvistoryUnitRecord")
    @RequireToken
    @PostMapping("/getZjTzDesignAdvistoryUnitRecordDetails")
    public ResponseEntity getZjTzDesignAdvistoryUnitRecordDetails(@RequestBody(required = false) ZjTzDesignAdvistoryUnitRecord zjTzDesignAdvistoryUnitRecord) {
        return zjTzDesignAdvistoryUnitRecordService.getZjTzDesignAdvistoryUnitRecordDetails(zjTzDesignAdvistoryUnitRecord);
    }

    @ApiOperation(value="新增设计、咨询单位管理记录", notes="新增设计、咨询单位管理记录")
    @ApiImplicitParam(name = "zjTzDesignAdvistoryUnitRecord", value = "设计、咨询单位管理记录entity", dataType = "ZjTzDesignAdvistoryUnitRecord")
    @RequireToken
    @PostMapping("/addZjTzDesignAdvistoryUnitRecord")
    public ResponseEntity addZjTzDesignAdvistoryUnitRecord(@RequestBody(required = false) ZjTzDesignAdvistoryUnitRecord zjTzDesignAdvistoryUnitRecord) {
        return zjTzDesignAdvistoryUnitRecordService.saveZjTzDesignAdvistoryUnitRecord(zjTzDesignAdvistoryUnitRecord);
    }

    @ApiOperation(value="更新设计、咨询单位管理记录", notes="更新设计、咨询单位管理记录")
    @ApiImplicitParam(name = "zjTzDesignAdvistoryUnitRecord", value = "设计、咨询单位管理记录entity", dataType = "ZjTzDesignAdvistoryUnitRecord")
    @RequireToken
    @PostMapping("/updateZjTzDesignAdvistoryUnitRecord")
    public ResponseEntity updateZjTzDesignAdvistoryUnitRecord(@RequestBody(required = false) ZjTzDesignAdvistoryUnitRecord zjTzDesignAdvistoryUnitRecord) {
        return zjTzDesignAdvistoryUnitRecordService.updateZjTzDesignAdvistoryUnitRecord(zjTzDesignAdvistoryUnitRecord);
    }

    @ApiOperation(value="删除设计、咨询单位管理记录", notes="删除设计、咨询单位管理记录")
    @ApiImplicitParam(name = "zjTzDesignAdvistoryUnitRecordList", value = "设计、咨询单位管理记录List", dataType = "List<ZjTzDesignAdvistoryUnitRecord>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZjTzDesignAdvistoryUnitRecord")
    public ResponseEntity batchDeleteUpdateZjTzDesignAdvistoryUnitRecord(@RequestBody(required = false) List<ZjTzDesignAdvistoryUnitRecord> zjTzDesignAdvistoryUnitRecordList) {
        return zjTzDesignAdvistoryUnitRecordService.batchDeleteUpdateZjTzDesignAdvistoryUnitRecord(zjTzDesignAdvistoryUnitRecordList);
    }
    
    @ApiOperation(value="批量上报设计、咨询单位管理记录", notes="批量上报设计、咨询单位管理记录")
    @ApiImplicitParam(name = "zjTzDesignAdvistoryUnitRecordList", value = "设计、咨询单位管理记录List", dataType = "List<ZjTzDesignAdvistoryUnitRecord>")
    @RequireToken
    @PostMapping("/batchReleaseZjTzDesignAdvistoryUnitRecord")
    public ResponseEntity batchReleaseZjTzDesignAdvistoryUnitRecord(@RequestBody(required = false) List<ZjTzDesignAdvistoryUnitRecord> zjTzDesignAdvistoryUnitRecordList) {
        return zjTzDesignAdvistoryUnitRecordService.batchReleaseZjTzDesignAdvistoryUnitRecord(zjTzDesignAdvistoryUnitRecordList);
    }
    
    @ApiOperation(value="批量退回设计、咨询单位管理记录", notes="批量退回设计、咨询单位管理记录")
    @ApiImplicitParam(name = "zjTzDesignAdvistoryUnitRecordList", value = "设计、咨询单位管理记录List", dataType = "List<ZjTzDesignAdvistoryUnitRecord>")
    @RequireToken
    @PostMapping("/batchRecallZjTzDesignAdvistoryUnitRecord")
    public ResponseEntity batchRecallZjTzDesignAdvistoryUnitRecord(@RequestBody(required = false) List<ZjTzDesignAdvistoryUnitRecord> zjTzDesignAdvistoryUnitRecordList) {
        return zjTzDesignAdvistoryUnitRecordService.batchRecallZjTzDesignAdvistoryUnitRecord(zjTzDesignAdvistoryUnitRecordList);
    }
    
    @ApiOperation(value="标准化设计库查看所有单位管理记录的列表接口", notes="标准化设计库查看所有单位管理记录的列表接口")
    @ApiImplicitParam(name = "zjTzDesignAdvistoryUnitRecord", value = "设计、咨询单位管理记录entity", dataType = "ZjTzDesignAdvistoryUnitRecord")
    @RequireToken
    @PostMapping("/getZjTzDesignAdvistoryUnitRecordAllList")
    public ResponseEntity getZjTzDesignAdvistoryUnitRecordAllList(@RequestBody(required = false) ZjTzDesignAdvistoryUnitRecord zjTzDesignAdvistoryUnitRecord) {
        return zjTzDesignAdvistoryUnitRecordService.getZjTzDesignAdvistoryUnitRecordAllList(zjTzDesignAdvistoryUnitRecord);
    }
    
    @ApiOperation(value="评价设计、咨询单位管理记录", notes="评价设计、咨询单位管理记录")
    @ApiImplicitParam(name = "zjTzDesignAdvistoryUnitRecord", value = "设计、咨询单位管理记录entity", dataType = "ZjTzDesignAdvistoryUnitRecord")
    @RequireToken
    @PostMapping("/evaluateZjTzDesignAdvistoryUnitRecord")
    public ResponseEntity evaluateZjTzDesignAdvistoryUnitRecord(@RequestBody(required = false) ZjTzDesignAdvistoryUnitRecord zjTzDesignAdvistoryUnitRecord) {
        return zjTzDesignAdvistoryUnitRecordService.evaluateZjTzDesignAdvistoryUnitRecord(zjTzDesignAdvistoryUnitRecord);
    }

    @ApiOperation(value="查询设计、咨询单位管理记录统计列表", notes="查询设计、咨询单位管理记录统计列表")
    @ApiImplicitParam(name = "zjTzDesignAdvistoryUnitRecord", value = "设计、咨询单位管理记录entity", dataType = "ZjTzDesignAdvistoryUnitRecord")
    @RequireToken
    @PostMapping("/getZjTzDesignAdvistoryUnitRecordTotalList")
    public ResponseEntity getZjTzDesignAdvistoryUnitRecordTotalList(@RequestBody(required = false) ZjTzDesignAdvistoryUnitRecord zjTzDesignAdvistoryUnitRecord) {
        return zjTzDesignAdvistoryUnitRecordService.getZjTzDesignAdvistoryUnitRecordTotalList(zjTzDesignAdvistoryUnitRecord);
    }
    
//    @ApiOperation(value="报表导出查询设计、咨询单位管理记录统计列表", notes="报表导出查询设计、咨询单位管理记录统计列表")
//    @ApiImplicitParam(name = "zjTzDesignAdvistoryUnitRecord", value = "设计、咨询单位管理记录entity", dataType = "ZjTzDesignAdvistoryUnitRecord")
//    @PostMapping("/reportZjTzDesignAdvistoryUnitRecordTotalList")
//    public List<ZjTzDesignAdvistoryUnitRecord> reportZjTzDesignAdvistoryUnitRecordTotalList(@RequestBody(required = false) ZjTzDesignAdvistoryUnitRecord zjTzDesignAdvistoryUnitRecord) {
//        return zjTzDesignAdvistoryUnitRecordService.reportZjTzDesignAdvistoryUnitRecordTotalList(zjTzDesignAdvistoryUnitRecord);
//    }
    
    @ApiOperation(value="报表导出查询设计、咨询单位管理记录统计列表", notes="报表导出查询设计、咨询单位管理记录统计列表")
    @ApiImplicitParam(name = "zjTzDesignAdvistoryUnitRecord", value = "设计、咨询单位管理记录entity", dataType = "ZjTzDesignAdvistoryUnitRecord")
    @PostMapping("/reportZjTzDesignAdvistoryUnitRecordTotalList")
    public ResponseEntity reportZjTzDesignAdvistoryUnitRecordTotalList(@RequestBody(required = false) ZjTzDesignAdvistoryUnitRecord zjTzDesignAdvistoryUnitRecord, HttpServletResponse response) {
        return zjTzDesignAdvistoryUnitRecordService.reportZjTzDesignAdvistoryUnitRecordTotalList(zjTzDesignAdvistoryUnitRecord, response);
    }
    
    @ApiOperation(value="审查完成设计、咨询单位管理记录", notes="审查完成设计、咨询单位管理记录")
    @ApiImplicitParam(name = "zjTzDesignAdvistoryUnitRecordList", value = "设计、咨询单位管理记录List", dataType = "List<ZjTzDesignAdvistoryUnitRecord>")
    @RequireToken
    @PostMapping("/checkAndFinishZjTzDesignAdvistoryUnitRecord")
    public ResponseEntity checkAndFinishZjTzDesignAdvistoryUnitRecord(@RequestBody(required = false) ZjTzDesignAdvistoryUnitRecord zjTzDesignAdvistoryUnitRecord) {
        return zjTzDesignAdvistoryUnitRecordService.checkAndFinishZjTzDesignAdvistoryUnitRecord(zjTzDesignAdvistoryUnitRecord);
    }
}
