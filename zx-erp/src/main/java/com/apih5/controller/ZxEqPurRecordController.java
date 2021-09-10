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
import com.apih5.mybatis.pojo.ZxEqPurRecord;
import com.apih5.service.ZxEqPurRecordService;

@RestController
public class ZxEqPurRecordController {

    @Autowired(required = true)
    private ZxEqPurRecordService zxEqPurRecordService;

    @ApiOperation(value="查询设备购置记录", notes="查询设备购置记录")
    @ApiImplicitParam(name = "zxEqPurRecord", value = "设备购置记录entity", dataType = "ZxEqPurRecord")
    @RequireToken
    @PostMapping("/getZxEqPurRecordList")
    public ResponseEntity getZxEqPurRecordList(@RequestBody(required = false) ZxEqPurRecord zxEqPurRecord) {
        return zxEqPurRecordService.getZxEqPurRecordListByCondition(zxEqPurRecord);
    }

    @ApiOperation(value="查询详情设备购置记录", notes="查询详情设备购置记录")
    @ApiImplicitParam(name = "zxEqPurRecord", value = "设备购置记录entity", dataType = "ZxEqPurRecord")
    @RequireToken
    @PostMapping("/getZxEqPurRecordDetails")
    public ResponseEntity getZxEqPurRecordDetails(@RequestBody(required = false) ZxEqPurRecord zxEqPurRecord) {
        return zxEqPurRecordService.getZxEqPurRecordDetails(zxEqPurRecord);
    }

    @ApiOperation(value="新增设备购置记录", notes="新增设备购置记录")
    @ApiImplicitParam(name = "zxEqPurRecord", value = "设备购置记录entity", dataType = "ZxEqPurRecord")
    @RequireToken
    @PostMapping("/addZxEqPurRecord")
    public ResponseEntity addZxEqPurRecord(@RequestBody(required = false) ZxEqPurRecord zxEqPurRecord) {
        return zxEqPurRecordService.saveZxEqPurRecord(zxEqPurRecord);
    }

    @ApiOperation(value="更新设备购置记录", notes="更新设备购置记录")
    @ApiImplicitParam(name = "zxEqPurRecord", value = "设备购置记录entity", dataType = "ZxEqPurRecord")
    @RequireToken
    @PostMapping("/updateZxEqPurRecord")
    public ResponseEntity updateZxEqPurRecord(@RequestBody(required = false) ZxEqPurRecord zxEqPurRecord) {
        return zxEqPurRecordService.updateZxEqPurRecord(zxEqPurRecord);
    }

    @ApiOperation(value="删除设备购置记录", notes="删除设备购置记录")
    @ApiImplicitParam(name = "zxEqPurRecordList", value = "设备购置记录List", dataType = "List<ZxEqPurRecord>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxEqPurRecord")
    public ResponseEntity batchDeleteUpdateZxEqPurRecord(@RequestBody(required = false) List<ZxEqPurRecord> zxEqPurRecordList) {
        return zxEqPurRecordService.batchDeleteUpdateZxEqPurRecord(zxEqPurRecordList);
    }
    
    @ApiOperation(value="批量请求编号-设备购置记录", notes="批量请求编号-设备购置记录")
    @ApiImplicitParam(name = "zxEqPurRecord", value = "设备购置记录entity", dataType = "ZxEqPurRecord")
    @RequireToken
    @PostMapping("/requestNumberZxEqPurRecord")
    public ResponseEntity requestNumberZxEqPurRecord(@RequestBody(required = false) List<ZxEqPurRecord> zxEqPurRecordList) {
        return zxEqPurRecordService.requestNumberZxEqPurRecord(zxEqPurRecordList);
    }

    @ApiOperation(value="填写编号-设备购置记录", notes="填写编号-设备购置记录")
    @ApiImplicitParam(name = "zxEqPurRecord", value = "设备购置记录entity", dataType = "ZxEqPurRecord")
    @RequireToken
    @PostMapping("/writeNumberZxEqPurRecord")
    public ResponseEntity writeNumberZxEqPurRecord(@RequestBody(required = false) ZxEqPurRecord zxEqPurRecord) {
        return zxEqPurRecordService.writeNumberZxEqPurRecord(zxEqPurRecord);
    }
    
    @ApiOperation(value="退回设备购置记录", notes="退回设备购置记录")
    @ApiImplicitParam(name = "zxEqPurRecord", value = "设备购置记录entity", dataType = "ZxEqPurRecord")
    @RequireToken
    @PostMapping("/returnZxEqPurRecord")
    public ResponseEntity returnZxEqPurRecord(@RequestBody(required = false) ZxEqPurRecord zxEqPurRecord) {
        return zxEqPurRecordService.returnZxEqPurRecord(zxEqPurRecord);
    }
    
    @ApiOperation(value="反审核设备购置记录", notes="反审核设备购置记录")
    @ApiImplicitParam(name = "zxEqPurRecord", value = "设备购置记录entity", dataType = "ZxEqPurRecord")
    @RequireToken
    @PostMapping("/reverseAuditZxEqPurRecord")
    public ResponseEntity reverseAuditZxEqPurRecord(@RequestBody(required = false) ZxEqPurRecord zxEqPurRecord) {
        return zxEqPurRecordService.reverseAuditZxEqPurRecord(zxEqPurRecord);
    }
    
    @ApiOperation(value="报表导出设备购置记录", notes="报表导出设备购置记录")
    @ApiImplicitParam(name = "zxEqPurRecord", value = "设备购置记录entity", dataType = "ZxEqPurRecord")
    @PostMapping("/ureportZxEqPurRecordDetails")
    public ZxEqPurRecord ureportZxEqPurRecordDetails(@RequestBody(required = false) ZxEqPurRecord zxEqPurRecord) {
        return zxEqPurRecordService.ureportZxEqPurRecordDetails(zxEqPurRecord);
    }
    
    
}