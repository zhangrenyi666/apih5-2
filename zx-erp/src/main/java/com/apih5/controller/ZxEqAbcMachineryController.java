package com.apih5.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.apih5.framework.annotation.RequireToken;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxEqAbcMachinery;
import com.apih5.service.ZxEqAbcMachineryService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@RestController
public class ZxEqAbcMachineryController {

    @Autowired(required = true)
    private ZxEqAbcMachineryService zxEqAbcMachineryService;

    @ApiOperation(value="查询主要机械设备ABC情况表", notes="查询主要机械设备ABC情况表")
    @ApiImplicitParam(name = "zxEqAbcMachinery", value = "主要机械设备ABC情况表entity", dataType = "ZxEqAbcMachinery")
    @RequireToken
    @PostMapping("/getZxEqAbcMachineryList")
    public ResponseEntity getZxEqAbcMachineryList(@RequestBody(required = false) ZxEqAbcMachinery zxEqAbcMachinery) {
        return zxEqAbcMachineryService.getZxEqAbcMachineryListByCondition(zxEqAbcMachinery);
    }

    @ApiOperation(value="查询详情主要机械设备ABC情况表", notes="查询详情主要机械设备ABC情况表")
    @ApiImplicitParam(name = "zxEqAbcMachinery", value = "主要机械设备ABC情况表entity", dataType = "ZxEqAbcMachinery")
    @RequireToken
    @PostMapping("/getZxEqAbcMachineryDetail")
    public ResponseEntity getZxEqAbcMachineryDetail(@RequestBody(required = false) ZxEqAbcMachinery zxEqAbcMachinery) {
        return zxEqAbcMachineryService.getZxEqAbcMachineryDetail(zxEqAbcMachinery);
    }

    @ApiOperation(value="新增主要机械设备ABC情况表", notes="新增主要机械设备ABC情况表")
    @ApiImplicitParam(name = "zxEqAbcMachinery", value = "主要机械设备ABC情况表entity", dataType = "ZxEqAbcMachinery")
    @RequireToken
    @PostMapping("/addZxEqAbcMachinery")
    public ResponseEntity addZxEqAbcMachinery(@RequestBody(required = false) ZxEqAbcMachinery zxEqAbcMachinery) {
        return zxEqAbcMachineryService.saveZxEqAbcMachinery(zxEqAbcMachinery);
    }

    @ApiOperation(value="更新主要机械设备ABC情况表", notes="更新主要机械设备ABC情况表")
    @ApiImplicitParam(name = "zxEqAbcMachinery", value = "主要机械设备ABC情况表entity", dataType = "ZxEqAbcMachinery")
    @RequireToken
    @PostMapping("/updateZxEqAbcMachinery")
    public ResponseEntity updateZxEqAbcMachinery(@RequestBody(required = false) ZxEqAbcMachinery zxEqAbcMachinery) {
        return zxEqAbcMachineryService.updateZxEqAbcMachinery(zxEqAbcMachinery);
    }

    @ApiOperation(value="删除主要机械设备ABC情况表", notes="删除主要机械设备ABC情况表")
    @ApiImplicitParam(name = "zxEqAbcMachineryList", value = "主要机械设备ABC情况表List", dataType = "List<ZxEqAbcMachinery>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxEqAbcMachinery")
    public ResponseEntity batchDeleteUpdateZxEqAbcMachinery(@RequestBody(required = false) List<ZxEqAbcMachinery> zxEqAbcMachineryList) {
        return zxEqAbcMachineryService.batchDeleteUpdateZxEqAbcMachinery(zxEqAbcMachineryList);
    } 

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    
    @ApiOperation(value="报表导出租用外部设备使用情况", notes="报表导出租用外部设备使用情况")
    @ApiImplicitParam(name = "zxEqAbcMachinery", value = "设备台账entity", dataType = "ZxEqAbcMachinery")
    @PostMapping("/ureportZxEqAbcMachinery")
    public List<ZxEqAbcMachinery> ureportZxEqAbcMachinery(@RequestBody(required = false) ZxEqAbcMachinery zxEqAbcMachinery) {
        return zxEqAbcMachineryService.ureportZxEqAbcMachinery(zxEqAbcMachinery);
    }
    
    @ApiOperation(value="报表导出租用外部设备使用情况", notes="报表导出租用外部设备使用情况")
    @ApiImplicitParam(name = "zxEqAbcMachinery", value = "设备台账entity", dataType = "ZxEqAbcMachinery")
    @RequireToken
    @PostMapping("/ureportZxEqAbcMachineryIdle")
    public ResponseEntity ureportZxEqAbcMachineryIdle(@RequestBody(required = false) ZxEqAbcMachinery zxEqAbcMachinery) {
        return zxEqAbcMachineryService.ureportZxEqAbcMachineryIdle(zxEqAbcMachinery);
    }
}
