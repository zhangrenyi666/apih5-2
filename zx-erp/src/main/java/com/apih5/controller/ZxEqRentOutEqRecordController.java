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
import com.apih5.mybatis.pojo.ZxEqRentOutEqRecord;
import com.apih5.service.ZxEqRentOutEqRecordService;

@RestController
public class ZxEqRentOutEqRecordController {

    @Autowired(required = true)
    private ZxEqRentOutEqRecordService zxEqRentOutEqRecordService;

    @ApiOperation(value="查询租赁设备管理-外租机械设备运转记录", notes="查询租赁设备管理-外租机械设备运转记录")
    @ApiImplicitParam(name = "zxEqRentOutEqRecord", value = "租赁设备管理-外租机械设备运转记录entity", dataType = "ZxEqRentOutEqRecord")
    @RequireToken
    @PostMapping("/getZxEqRentOutEqRecordList")
    public ResponseEntity getZxEqRentOutEqRecordList(@RequestBody(required = false) ZxEqRentOutEqRecord zxEqRentOutEqRecord) {
        return zxEqRentOutEqRecordService.getZxEqRentOutEqRecordListByCondition(zxEqRentOutEqRecord);
    }

    @ApiOperation(value="查询详情租赁设备管理-外租机械设备运转记录", notes="查询详情租赁设备管理-外租机械设备运转记录")
    @ApiImplicitParam(name = "zxEqRentOutEqRecord", value = "租赁设备管理-外租机械设备运转记录entity", dataType = "ZxEqRentOutEqRecord")
    @RequireToken
    @PostMapping("/getZxEqRentOutEqRecordDetails")
    public ResponseEntity getZxEqRentOutEqRecordDetails(@RequestBody(required = false) ZxEqRentOutEqRecord zxEqRentOutEqRecord) {
        return zxEqRentOutEqRecordService.getZxEqRentOutEqRecordDetails(zxEqRentOutEqRecord);
    }

    @ApiOperation(value="新增租赁设备管理-外租机械设备运转记录", notes="新增租赁设备管理-外租机械设备运转记录")
    @ApiImplicitParam(name = "zxEqRentOutEqRecord", value = "租赁设备管理-外租机械设备运转记录entity", dataType = "ZxEqRentOutEqRecord")
    @RequireToken
    @PostMapping("/addZxEqRentOutEqRecord")
    public ResponseEntity addZxEqRentOutEqRecord(@RequestBody(required = false) ZxEqRentOutEqRecord zxEqRentOutEqRecord) {
        return zxEqRentOutEqRecordService.saveZxEqRentOutEqRecord(zxEqRentOutEqRecord);
    }

    @ApiOperation(value="更新租赁设备管理-外租机械设备运转记录", notes="更新租赁设备管理-外租机械设备运转记录")
    @ApiImplicitParam(name = "zxEqRentOutEqRecord", value = "租赁设备管理-外租机械设备运转记录entity", dataType = "ZxEqRentOutEqRecord")
    @RequireToken
    @PostMapping("/updateZxEqRentOutEqRecord")
    public ResponseEntity updateZxEqRentOutEqRecord(@RequestBody(required = false) ZxEqRentOutEqRecord zxEqRentOutEqRecord) {
        return zxEqRentOutEqRecordService.updateZxEqRentOutEqRecord(zxEqRentOutEqRecord);
    }

    @ApiOperation(value="删除租赁设备管理-外租机械设备运转记录", notes="删除租赁设备管理-外租机械设备运转记录")
    @ApiImplicitParam(name = "zxEqRentOutEqRecordList", value = "租赁设备管理-外租机械设备运转记录List", dataType = "List<ZxEqRentOutEqRecord>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxEqRentOutEqRecord")
    public ResponseEntity batchDeleteUpdateZxEqRentOutEqRecord(@RequestBody(required = false) List<ZxEqRentOutEqRecord> zxEqRentOutEqRecordList) {
        return zxEqRentOutEqRecordService.batchDeleteUpdateZxEqRentOutEqRecord(zxEqRentOutEqRecordList);
    }
    
    @ApiOperation(value="审核租赁设备管理-外租机械设备运转记录", notes="审核租赁设备管理-外租机械设备运转记录")
    @ApiImplicitParam(name = "zxEqRentOutEqRecord", value = "租赁设备管理-外租机械设备运转记录entity", dataType = "ZxEqRentOutEqRecord")
    @RequireToken
    @PostMapping("/auditZxEqRentOutEqRecord")
    public ResponseEntity auditZxEqRentOutEqRecord(@RequestBody(required = false) ZxEqRentOutEqRecord zxEqRentOutEqRecord) {
        return zxEqRentOutEqRecordService.auditZxEqRentOutEqRecord(zxEqRentOutEqRecord);
    }
    
    @ApiOperation(value="导入租赁设备管理-外租机械设备运转记录", notes="导入租赁设备管理-外租机械设备运转记录")
    @ApiImplicitParam(name = "zxEqRentOutEqRecord", value = "租赁设备管理-外租机械设备运转记录entity", dataType = "ZxEqRentOutEqRecord")
    @RequireToken
    @PostMapping("/importZxEqRentOutEqRecordList")
    public ResponseEntity importZxEqRentOutEqRecordList(@RequestBody(required = false) ZxEqRentOutEqRecord zxEqRentOutEqRecord) {
        return zxEqRentOutEqRecordService.importZxEqRentOutEqRecordList(zxEqRentOutEqRecord);
    }


    
}
