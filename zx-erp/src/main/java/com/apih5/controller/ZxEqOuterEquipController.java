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
import com.apih5.mybatis.pojo.ZxEqOuterEquip;
import com.apih5.service.ZxEqOuterEquipService;

@RestController
public class ZxEqOuterEquipController {

    @Autowired(required = true)
    private ZxEqOuterEquipService zxEqOuterEquipService;

    @ApiOperation(value="查询租赁设备管理-外部租用机械设备入场验收", notes="查询租赁设备管理-外部租用机械设备入场验收")
    @ApiImplicitParam(name = "zxEqOuterEquip", value = "租赁设备管理-外部租用机械设备入场验收entity", dataType = "ZxEqOuterEquip")
    @RequireToken
    @PostMapping("/getZxEqOuterEquipList")
    public ResponseEntity getZxEqOuterEquipList(@RequestBody(required = false) ZxEqOuterEquip zxEqOuterEquip) {
        return zxEqOuterEquipService.getZxEqOuterEquipListByCondition(zxEqOuterEquip);
    }

    @ApiOperation(value="查询详情租赁设备管理-外部租用机械设备入场验收", notes="查询详情租赁设备管理-外部租用机械设备入场验收")
    @ApiImplicitParam(name = "zxEqOuterEquip", value = "租赁设备管理-外部租用机械设备入场验收entity", dataType = "ZxEqOuterEquip")
    @RequireToken
    @PostMapping("/getZxEqOuterEquipDetails")
    public ResponseEntity getZxEqOuterEquipDetails(@RequestBody(required = false) ZxEqOuterEquip zxEqOuterEquip) {
        return zxEqOuterEquipService.getZxEqOuterEquipDetails(zxEqOuterEquip);
    }

    @ApiOperation(value="新增租赁设备管理-外部租用机械设备入场验收", notes="新增租赁设备管理-外部租用机械设备入场验收")
    @ApiImplicitParam(name = "zxEqOuterEquip", value = "租赁设备管理-外部租用机械设备入场验收entity", dataType = "ZxEqOuterEquip")
    @RequireToken
    @PostMapping("/addZxEqOuterEquip")
    public ResponseEntity addZxEqOuterEquip(@RequestBody(required = false) ZxEqOuterEquip zxEqOuterEquip) {
        return zxEqOuterEquipService.saveZxEqOuterEquip(zxEqOuterEquip);
    }

    @ApiOperation(value="更新租赁设备管理-外部租用机械设备入场验收", notes="更新租赁设备管理-外部租用机械设备入场验收")
    @ApiImplicitParam(name = "zxEqOuterEquip", value = "租赁设备管理-外部租用机械设备入场验收entity", dataType = "ZxEqOuterEquip")
    @RequireToken
    @PostMapping("/updateZxEqOuterEquip")
    public ResponseEntity updateZxEqOuterEquip(@RequestBody(required = false) ZxEqOuterEquip zxEqOuterEquip) {
        return zxEqOuterEquipService.updateZxEqOuterEquip(zxEqOuterEquip);
    }

    @ApiOperation(value="删除租赁设备管理-外部租用机械设备入场验收", notes="删除租赁设备管理-外部租用机械设备入场验收")
    @ApiImplicitParam(name = "zxEqOuterEquipList", value = "租赁设备管理-外部租用机械设备入场验收List", dataType = "List<ZxEqOuterEquip>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxEqOuterEquip")
    public ResponseEntity batchDeleteUpdateZxEqOuterEquip(@RequestBody(required = false) List<ZxEqOuterEquip> zxEqOuterEquipList) {
        return zxEqOuterEquipService.batchDeleteUpdateZxEqOuterEquip(zxEqOuterEquipList);
    }

    @ApiOperation(value="运转记录=查询租赁设备管理-外部租用机械设备入场验收", notes="运转记录=查询租赁设备管理-外部租用机械设备入场验收")
    @ApiImplicitParam(name = "zxEqOuterEquip", value = "租赁设备管理-外部租用机械设备入场验收entity", dataType = "ZxEqOuterEquip")
    @RequireToken
    @PostMapping("/getZxEqOuterEquipListForRecord")
    public ResponseEntity getZxEqOuterEquipListForRecord(@RequestBody(required = false) ZxEqOuterEquip zxEqOuterEquip) {
        return zxEqOuterEquipService.getZxEqOuterEquipListForRecord(zxEqOuterEquip);
    }
    
    //报表
    @ApiOperation(value="查询租赁车辆情况统计表", notes="查询租赁车辆情况统计表")
    @ApiImplicitParam(name = "zxEqOuterEquip", value = "租赁设备管理-外部租用机械设备入场验收entity", dataType = "ZxEqOuterEquip")
    @RequireToken
    @PostMapping("/getZxEqOuterEquipListForCar")
    public ResponseEntity getZxEqOuterEquipListForCar(@RequestBody(required = false) ZxEqOuterEquip zxEqOuterEquip) {
        return zxEqOuterEquipService.getZxEqOuterEquipListForCar(zxEqOuterEquip);
    }
    @ApiOperation(value="导出租赁车辆情况统计表", notes="导出租赁车辆情况统计表")
    @ApiImplicitParam(name = "zxEqOuterEquip", value = "租赁设备管理-外部租用机械设备入场验收entity", dataType = "ZxEqOuterEquip")
    @PostMapping("/ureportZxEqOuterEquipListForCar")
    public List<ZxEqOuterEquip> ureportZxEqOuterEquipListForCar(@RequestBody(required = false) ZxEqOuterEquip zxEqOuterEquip) {
        return zxEqOuterEquipService.reportZuLinCLForm(zxEqOuterEquip);
    }

}