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
import com.apih5.mybatis.pojo.ZxEqUseEquip;
import com.apih5.service.ZxEqUseEquipService;

@RestController
public class ZxEqUseEquipController {

    @Autowired(required = true)
    private ZxEqUseEquipService zxEqUseEquipService;

    @ApiOperation(value="查询项目设备管理-自有设备", notes="查询项目设备管理-自有设备")
    @ApiImplicitParam(name = "zxEqUseEquip", value = "项目设备管理-自有设备entity", dataType = "ZxEqUseEquip")
    @RequireToken
    @PostMapping("/getZxEqUseEquipList")
    public ResponseEntity getZxEqUseEquipList(@RequestBody(required = false) ZxEqUseEquip zxEqUseEquip) {
        return zxEqUseEquipService.getZxEqUseEquipListByCondition(zxEqUseEquip);
    }

    @ApiOperation(value="查询详情项目设备管理-自有设备", notes="查询详情项目设备管理-自有设备")
    @ApiImplicitParam(name = "zxEqUseEquip", value = "项目设备管理-自有设备entity", dataType = "ZxEqUseEquip")
    @RequireToken
    @PostMapping("/getZxEqUseEquipDetails")
    public ResponseEntity getZxEqUseEquipDetails(@RequestBody(required = false) ZxEqUseEquip zxEqUseEquip) {
        return zxEqUseEquipService.getZxEqUseEquipDetails(zxEqUseEquip);
    }

    @ApiOperation(value="新增项目设备管理-自有设备", notes="新增项目设备管理-自有设备")
    @ApiImplicitParam(name = "zxEqUseEquip", value = "项目设备管理-自有设备entity", dataType = "ZxEqUseEquip")
    @RequireToken
    @PostMapping("/addZxEqUseEquip")
    public ResponseEntity addZxEqUseEquip(@RequestBody(required = false) ZxEqUseEquip zxEqUseEquip) {
        return zxEqUseEquipService.saveZxEqUseEquip(zxEqUseEquip);
    }

    @ApiOperation(value="更新项目设备管理-自有设备", notes="更新项目设备管理-自有设备")
    @ApiImplicitParam(name = "zxEqUseEquip", value = "项目设备管理-自有设备entity", dataType = "ZxEqUseEquip")
    @RequireToken
    @PostMapping("/updateZxEqUseEquip")
    public ResponseEntity updateZxEqUseEquip(@RequestBody(required = false) ZxEqUseEquip zxEqUseEquip) {
        return zxEqUseEquipService.updateZxEqUseEquip(zxEqUseEquip);
    }

    @ApiOperation(value="删除项目设备管理-自有设备", notes="删除项目设备管理-自有设备")
    @ApiImplicitParam(name = "zxEqUseEquipList", value = "项目设备管理-自有设备List", dataType = "List<ZxEqUseEquip>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxEqUseEquip")
    public ResponseEntity batchDeleteUpdateZxEqUseEquip(@RequestBody(required = false) List<ZxEqUseEquip> zxEqUseEquipList) {
        return zxEqUseEquipService.batchDeleteUpdateZxEqUseEquip(zxEqUseEquipList);
    }

}
