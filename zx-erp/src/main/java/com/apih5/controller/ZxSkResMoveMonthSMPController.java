package com.apih5.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.apih5.framework.annotation.RequireToken;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxSkResMoveMonthSMP;
import com.apih5.service.ZxSkResMoveMonthSMPService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@RestController
public class ZxSkResMoveMonthSMPController {

    @Autowired(required = true)
    private ZxSkResMoveMonthSMPService zxSkResMoveMonthSMPService;

    @ApiOperation(value="查询物资收发存汇总月报表", notes="查询物资收发存汇总月报表")
    @ApiImplicitParam(name = "zxSkResMoveMonthSMP", value = "物资收发存汇总月报表entity", dataType = "ZxSkResMoveMonthSMP")
    @RequireToken
    @PostMapping("/getZxSkResMoveMonthSMPList")
    public ResponseEntity getZxSkResMoveMonthSMPList(@RequestBody(required = false) ZxSkResMoveMonthSMP zxSkResMoveMonthSMP) {
        return zxSkResMoveMonthSMPService.getZxSkResMoveMonthSMPListByCondition(zxSkResMoveMonthSMP);
    }

    @ApiOperation(value="查询详情物资收发存汇总月报表", notes="查询详情物资收发存汇总月报表")
    @ApiImplicitParam(name = "zxSkResMoveMonthSMP", value = "物资收发存汇总月报表entity", dataType = "ZxSkResMoveMonthSMP")
    @RequireToken
    @PostMapping("/getZxSkResMoveMonthSMPDetail")
    public ResponseEntity getZxSkResMoveMonthSMPDetail(@RequestBody(required = false) ZxSkResMoveMonthSMP zxSkResMoveMonthSMP) {
        return zxSkResMoveMonthSMPService.getZxSkResMoveMonthSMPDetail(zxSkResMoveMonthSMP);
    }

    @ApiOperation(value="新增物资收发存汇总月报表", notes="新增物资收发存汇总月报表")
    @ApiImplicitParam(name = "zxSkResMoveMonthSMP", value = "物资收发存汇总月报表entity", dataType = "ZxSkResMoveMonthSMP")
    @RequireToken
    @PostMapping("/addZxSkResMoveMonthSMP")
    public ResponseEntity addZxSkResMoveMonthSMP(@RequestBody(required = false) ZxSkResMoveMonthSMP zxSkResMoveMonthSMP) {
        return zxSkResMoveMonthSMPService.saveZxSkResMoveMonthSMP(zxSkResMoveMonthSMP);
    }

    @ApiOperation(value="更新物资收发存汇总月报表", notes="更新物资收发存汇总月报表")
    @ApiImplicitParam(name = "zxSkResMoveMonthSMP", value = "物资收发存汇总月报表entity", dataType = "ZxSkResMoveMonthSMP")
    @RequireToken
    @PostMapping("/updateZxSkResMoveMonthSMP")
    public ResponseEntity updateZxSkResMoveMonthSMP(@RequestBody(required = false) ZxSkResMoveMonthSMP zxSkResMoveMonthSMP) {
        return zxSkResMoveMonthSMPService.updateZxSkResMoveMonthSMP(zxSkResMoveMonthSMP);
    }

    @ApiOperation(value="删除物资收发存汇总月报表", notes="删除物资收发存汇总月报表")
    @ApiImplicitParam(name = "zxSkResMoveMonthSMPList", value = "物资收发存汇总月报表List", dataType = "List<ZxSkResMoveMonthSMP>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxSkResMoveMonthSMP")
    public ResponseEntity batchDeleteUpdateZxSkResMoveMonthSMP(@RequestBody(required = false) List<ZxSkResMoveMonthSMP> zxSkResMoveMonthSMPList) {
        return zxSkResMoveMonthSMPService.batchDeleteUpdateZxSkResMoveMonthSMP(zxSkResMoveMonthSMPList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    
    @ApiOperation(value="报表导出物资收发存汇总月报表", notes="报表导出物资收发存汇总月报表")
    @ApiImplicitParam(name = "zxSkResMoveMonthSMP", value = "物资收发存汇总月报表entity", dataType = "ZxSkResMoveMonthSMP")
    @PostMapping("/ureportZxSkResMoveMonthSMP")
    public List<ZxSkResMoveMonthSMP> ureportZxSkResMoveMonthSMP(@RequestBody(required = false) ZxSkResMoveMonthSMP zxSkResMoveMonthSMP) {
        return zxSkResMoveMonthSMPService.ureportZxSkResMoveMonthSMP(zxSkResMoveMonthSMP);
    }
    
    @ApiOperation(value="报表物资收发存汇总月报表", notes="报表物资收发存汇总月报表")
    @ApiImplicitParam(name = "zxSkResMoveMonthSMP", value = "物资收发存汇总月报表entity", dataType = "ZxSkResMoveMonthSMP")
    @RequireToken
    @PostMapping("/ureportZxSkResMoveMonthSMPIdle")
    public ResponseEntity ureportZxSkResMoveMonthSMPIdle(@RequestBody(required = false) ZxSkResMoveMonthSMP zxSkResMoveMonthSMP) {
        return zxSkResMoveMonthSMPService.ureportZxSkResMoveMonthSMPIdle(zxSkResMoveMonthSMP);
    }
}
