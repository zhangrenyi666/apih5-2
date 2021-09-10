package com.apih5.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.apih5.framework.annotation.RequireToken;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxSkTurnoverTotalRpt;
import com.apih5.service.ZxSkTurnoverTotalRptService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@RestController
public class ZxSkTurnoverTotalRptController {

    @Autowired(required = true)
    private ZxSkTurnoverTotalRptService zxSkTurnoverTotalRptService;

    @ApiOperation(value="查询周转材料统计报表", notes="查询周转材料统计报表")
    @ApiImplicitParam(name = "zxSkTurnoverTotalRpt", value = "周转材料统计报表entity", dataType = "ZxSkTurnoverTotalRpt")
    @RequireToken
    @PostMapping("/getZxSkTurnoverTotalRptList")
    public ResponseEntity getZxSkTurnoverTotalRptList(@RequestBody(required = false) ZxSkTurnoverTotalRpt zxSkTurnoverTotalRpt) {
        return zxSkTurnoverTotalRptService.getZxSkTurnoverTotalRptListByCondition(zxSkTurnoverTotalRpt);
    }

    @ApiOperation(value="查询详情周转材料统计报表", notes="查询详情周转材料统计报表")
    @ApiImplicitParam(name = "zxSkTurnoverTotalRpt", value = "周转材料统计报表entity", dataType = "ZxSkTurnoverTotalRpt")
    @RequireToken
    @PostMapping("/getZxSkTurnoverTotalRptDetail")
    public ResponseEntity getZxSkTurnoverTotalRptDetail(@RequestBody(required = false) ZxSkTurnoverTotalRpt zxSkTurnoverTotalRpt) {
        return zxSkTurnoverTotalRptService.getZxSkTurnoverTotalRptDetail(zxSkTurnoverTotalRpt);
    }

    @ApiOperation(value="新增周转材料统计报表", notes="新增周转材料统计报表")
    @ApiImplicitParam(name = "zxSkTurnoverTotalRpt", value = "周转材料统计报表entity", dataType = "ZxSkTurnoverTotalRpt")
    @RequireToken
    @PostMapping("/addZxSkTurnoverTotalRpt")
    public ResponseEntity addZxSkTurnoverTotalRpt(@RequestBody(required = false) ZxSkTurnoverTotalRpt zxSkTurnoverTotalRpt) {
        return zxSkTurnoverTotalRptService.saveZxSkTurnoverTotalRpt(zxSkTurnoverTotalRpt);
    }

    @ApiOperation(value="更新周转材料统计报表", notes="更新周转材料统计报表")
    @ApiImplicitParam(name = "zxSkTurnoverTotalRpt", value = "周转材料统计报表entity", dataType = "ZxSkTurnoverTotalRpt")
    @RequireToken
    @PostMapping("/updateZxSkTurnoverTotalRpt")
    public ResponseEntity updateZxSkTurnoverTotalRpt(@RequestBody(required = false) ZxSkTurnoverTotalRpt zxSkTurnoverTotalRpt) {
        return zxSkTurnoverTotalRptService.updateZxSkTurnoverTotalRpt(zxSkTurnoverTotalRpt);
    }

    @ApiOperation(value="删除周转材料统计报表", notes="删除周转材料统计报表")
    @ApiImplicitParam(name = "zxSkTurnoverTotalRptList", value = "周转材料统计报表List", dataType = "List<ZxSkTurnoverTotalRpt>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxSkTurnoverTotalRpt")
    public ResponseEntity batchDeleteUpdateZxSkTurnoverTotalRpt(@RequestBody(required = false) List<ZxSkTurnoverTotalRpt> zxSkTurnoverTotalRptList) {
        return zxSkTurnoverTotalRptService.batchDeleteUpdateZxSkTurnoverTotalRpt(zxSkTurnoverTotalRptList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    
    @ApiOperation(value="报表导出周转材料统计报表", notes="报表导出周转材料统计报表")
    @ApiImplicitParam(name = "zxSkTurnoverTotalRpt", value = "设备台账entity", dataType = "ZxSkTurnoverTotalRpt")
    @PostMapping("/ureportZxSkTurnoverTotalRpt")
    public List<ZxSkTurnoverTotalRpt> ureportZxSkTurnoverTotalRpt(@RequestBody(required = false) ZxSkTurnoverTotalRpt zxSkTurnoverTotalRpt) {
        return zxSkTurnoverTotalRptService.ureportZxSkTurnoverTotalRpt(zxSkTurnoverTotalRpt);
    }
    
    @ApiOperation(value="报表导出周转材料统计报表", notes="报表导出周转材料统计报表")
    @ApiImplicitParam(name = "zxSkTurnoverTotalRpt", value = "设备台账entity", dataType = "ZxSkTurnoverTotalRpt")
    @RequireToken
    @PostMapping("/ureportZxSkTurnoverTotalRptIdle")
    public ResponseEntity ureportZxSkTurnoverTotalRptIdle(@RequestBody(required = false) ZxSkTurnoverTotalRpt zxSkTurnoverTotalRpt) {
        return zxSkTurnoverTotalRptService.ureportZxSkTurnoverTotalRptIdle(zxSkTurnoverTotalRpt);
    }
}
