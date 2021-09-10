package com.apih5.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.apih5.framework.annotation.RequireToken;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxSkStockTransferRpt;
import com.apih5.service.ZxSkStockTransferRptService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@RestController
public class ZxSkStockTransferRptController {

    @Autowired(required = true)
    private ZxSkStockTransferRptService zxSkStockTransferRptService;

    @ApiOperation(value="查询物资收发存季度报表", notes="查询物资收发存季度报表")
    @ApiImplicitParam(name = "zxSkStockTransferRpt", value = "物资收发存季度报表entity", dataType = "ZxSkStockTransferRpt")
    @RequireToken
    @PostMapping("/getZxSkStockTransferRptList")
    public ResponseEntity getZxSkStockTransferRptList(@RequestBody(required = false) ZxSkStockTransferRpt zxSkStockTransferRpt) {
        return zxSkStockTransferRptService.getZxSkStockTransferRptListByCondition(zxSkStockTransferRpt);
    }

    @ApiOperation(value="查询详情物资收发存季度报表", notes="查询详情物资收发存季度报表")
    @ApiImplicitParam(name = "zxSkStockTransferRpt", value = "物资收发存季度报表entity", dataType = "ZxSkStockTransferRpt")
    @RequireToken
    @PostMapping("/getZxSkStockTransferRptDetail")
    public ResponseEntity getZxSkStockTransferRptDetail(@RequestBody(required = false) ZxSkStockTransferRpt zxSkStockTransferRpt) {
        return zxSkStockTransferRptService.getZxSkStockTransferRptDetail(zxSkStockTransferRpt);
    }

    @ApiOperation(value="新增物资收发存季度报表", notes="新增物资收发存季度报表")
    @ApiImplicitParam(name = "zxSkStockTransferRpt", value = "物资收发存季度报表entity", dataType = "ZxSkStockTransferRpt")
    @RequireToken
    @PostMapping("/addZxSkStockTransferRpt")
    public ResponseEntity addZxSkStockTransferRpt(@RequestBody(required = false) ZxSkStockTransferRpt zxSkStockTransferRpt) {
        return zxSkStockTransferRptService.saveZxSkStockTransferRpt(zxSkStockTransferRpt);
    }

    @ApiOperation(value="更新物资收发存季度报表", notes="更新物资收发存季度报表")
    @ApiImplicitParam(name = "zxSkStockTransferRpt", value = "物资收发存季度报表entity", dataType = "ZxSkStockTransferRpt")
    @RequireToken
    @PostMapping("/updateZxSkStockTransferRpt")
    public ResponseEntity updateZxSkStockTransferRpt(@RequestBody(required = false) ZxSkStockTransferRpt zxSkStockTransferRpt) {
        return zxSkStockTransferRptService.updateZxSkStockTransferRpt(zxSkStockTransferRpt);
    }

    @ApiOperation(value="删除物资收发存季度报表", notes="删除物资收发存季度报表")
    @ApiImplicitParam(name = "zxSkStockTransferRptList", value = "物资收发存季度报表List", dataType = "List<ZxSkStockTransferRpt>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxSkStockTransferRpt")
    public ResponseEntity batchDeleteUpdateZxSkStockTransferRpt(@RequestBody(required = false) List<ZxSkStockTransferRpt> zxSkStockTransferRptList) {
        return zxSkStockTransferRptService.batchDeleteUpdateZxSkStockTransferRpt(zxSkStockTransferRptList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    
    @ApiOperation(value="报表导出物资收发存季度报表", notes="报表导出物资收发存季度报表")
    @ApiImplicitParam(name = "zxSkStockTransferRpt", value = "设备台账entity", dataType = "ZxSkStockTransferRpt")
    @PostMapping("/ureportZxSkStockTransferRpt")
    public List<ZxSkStockTransferRpt> ureportZxSkStockTransferRpt(@RequestBody(required = false) ZxSkStockTransferRpt zxSkStockTransferRpt) {
        return zxSkStockTransferRptService.ureportZxSkStockTransferRpt(zxSkStockTransferRpt);
    }
    
    @ApiOperation(value="报表导出物资收发存季度报表", notes="报表导出物资收发存季度报表")
    @ApiImplicitParam(name = "zxSkStockTransferRpt", value = "设备台账entity", dataType = "ZxSkStockTransferRpt")
    @RequireToken
    @PostMapping("/ureportZxSkStockTransferRptIdle")
    public ResponseEntity ureportZxSkStockTransferRptIdle(@RequestBody(required = false) ZxSkStockTransferRpt zxSkStockTransferRpt) {
        return zxSkStockTransferRptService.ureportZxSkStockTransferRptIdle(zxSkStockTransferRpt);
    }
}
