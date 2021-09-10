package com.apih5.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.apih5.framework.annotation.RequireToken;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxSkTurnoverStatTotalRpt;
import com.apih5.service.ZxSkTurnoverStatTotalRptService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@RestController
public class ZxSkTurnoverStatTotalRptController {

    @Autowired(required = true)
    private ZxSkTurnoverStatTotalRptService zxSkTurnoverStatTotalRptService;

    @ApiOperation(value="查询周转材统计汇总报表", notes="查询周转材统计汇总报表")
    @ApiImplicitParam(name = "zxSkTurnoverStatTotalRpt", value = "周转材统计汇总报表entity", dataType = "ZxSkTurnoverStatTotalRpt")
    @RequireToken
    @PostMapping("/getZxSkTurnoverStatTotalRptList")
    public ResponseEntity getZxSkTurnoverStatTotalRptList(@RequestBody(required = false) ZxSkTurnoverStatTotalRpt zxSkTurnoverStatTotalRpt) {
        return zxSkTurnoverStatTotalRptService.getZxSkTurnoverStatTotalRptListByCondition(zxSkTurnoverStatTotalRpt);
    }

    @ApiOperation(value="查询详情周转材统计汇总报表", notes="查询详情周转材统计汇总报表")
    @ApiImplicitParam(name = "zxSkTurnoverStatTotalRpt", value = "周转材统计汇总报表entity", dataType = "ZxSkTurnoverStatTotalRpt")
    @RequireToken
    @PostMapping("/getZxSkTurnoverStatTotalRptDetail")
    public ResponseEntity getZxSkTurnoverStatTotalRptDetail(@RequestBody(required = false) ZxSkTurnoverStatTotalRpt zxSkTurnoverStatTotalRpt) {
        return zxSkTurnoverStatTotalRptService.getZxSkTurnoverStatTotalRptDetail(zxSkTurnoverStatTotalRpt);
    }

    @ApiOperation(value="新增周转材统计汇总报表", notes="新增周转材统计汇总报表")
    @ApiImplicitParam(name = "zxSkTurnoverStatTotalRpt", value = "周转材统计汇总报表entity", dataType = "ZxSkTurnoverStatTotalRpt")
    @RequireToken
    @PostMapping("/addZxSkTurnoverStatTotalRpt")
    public ResponseEntity addZxSkTurnoverStatTotalRpt(@RequestBody(required = false) ZxSkTurnoverStatTotalRpt zxSkTurnoverStatTotalRpt) {
        return zxSkTurnoverStatTotalRptService.saveZxSkTurnoverStatTotalRpt(zxSkTurnoverStatTotalRpt);
    }

    @ApiOperation(value="更新周转材统计汇总报表", notes="更新周转材统计汇总报表")
    @ApiImplicitParam(name = "zxSkTurnoverStatTotalRpt", value = "周转材统计汇总报表entity", dataType = "ZxSkTurnoverStatTotalRpt")
    @RequireToken
    @PostMapping("/updateZxSkTurnoverStatTotalRpt")
    public ResponseEntity updateZxSkTurnoverStatTotalRpt(@RequestBody(required = false) ZxSkTurnoverStatTotalRpt zxSkTurnoverStatTotalRpt) {
        return zxSkTurnoverStatTotalRptService.updateZxSkTurnoverStatTotalRpt(zxSkTurnoverStatTotalRpt);
    }

    @ApiOperation(value="删除周转材统计汇总报表", notes="删除周转材统计汇总报表")
    @ApiImplicitParam(name = "zxSkTurnoverStatTotalRptList", value = "周转材统计汇总报表List", dataType = "List<ZxSkTurnoverStatTotalRpt>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxSkTurnoverStatTotalRpt")
    public ResponseEntity batchDeleteUpdateZxSkTurnoverStatTotalRpt(@RequestBody(required = false) List<ZxSkTurnoverStatTotalRpt> zxSkTurnoverStatTotalRptList) {
        return zxSkTurnoverStatTotalRptService.batchDeleteUpdateZxSkTurnoverStatTotalRpt(zxSkTurnoverStatTotalRptList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    
    @ApiOperation(value="报表导出周转材统计汇总报表", notes="报表导出周转材统计汇总报表")
    @ApiImplicitParam(name = "zxSkTurnoverStatTotalRpt", value = "设备台账entity", dataType = "ZxSkTurnoverStatTotalRpt")
    @PostMapping("/ureportZxSkTurnoverStatTotalRpt")
    public List<ZxSkTurnoverStatTotalRpt> ureportZxSkTurnoverStatTotalRpt(@RequestBody(required = false) ZxSkTurnoverStatTotalRpt zxSkTurnoverStatTotalRpt) {
        return zxSkTurnoverStatTotalRptService.ureportZxSkTurnoverStatTotalRpt(zxSkTurnoverStatTotalRpt);
    }
    
    @ApiOperation(value="报表周转材统计汇总报表", notes="报表导出周转材统计汇总报表")
    @ApiImplicitParam(name = "zxSkTurnoverStatTotalRpt", value = "设备台账entity", dataType = "ZxSkTurnoverStatTotalRpt")
    @RequireToken
    @PostMapping("/ureportZxSkTurnoverStatTotalRptIdle")
    public ResponseEntity ureportZxSkTurnoverStatTotalRptIdle(@RequestBody(required = false) ZxSkTurnoverStatTotalRpt zxSkTurnoverStatTotalRpt) {
        return zxSkTurnoverStatTotalRptService.ureportZxSkTurnoverStatTotalRptIdle(zxSkTurnoverStatTotalRpt);
    }
    
    
}
