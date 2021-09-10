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
import com.apih5.mybatis.pojo.ZxEqEquipFactItem;
import com.apih5.service.ZxEqEquipFactItemService;

@RestController
public class ZxEqEquipFactItemController {

    @Autowired(required = true)
    private ZxEqEquipFactItemService zxEqEquipFactItemService;

    @ApiOperation(value="查询项目设备管理-设备核实台账(月)明细", notes="查询项目设备管理-设备核实台账(月)明细")
    @ApiImplicitParam(name = "zxEqEquipFactItem", value = "项目设备管理-设备核实台账(月)明细entity", dataType = "ZxEqEquipFactItem")
    @RequireToken
    @PostMapping("/getZxEqEquipFactItemList")
    public ResponseEntity getZxEqEquipFactItemList(@RequestBody(required = false) ZxEqEquipFactItem zxEqEquipFactItem) {
        return zxEqEquipFactItemService.getZxEqEquipFactItemListByCondition(zxEqEquipFactItem);
    }

    @ApiOperation(value="查询详情项目设备管理-设备核实台账(月)明细", notes="查询详情项目设备管理-设备核实台账(月)明细")
    @ApiImplicitParam(name = "zxEqEquipFactItem", value = "项目设备管理-设备核实台账(月)明细entity", dataType = "ZxEqEquipFactItem")
    @RequireToken
    @PostMapping("/getZxEqEquipFactItemDetails")
    public ResponseEntity getZxEqEquipFactItemDetails(@RequestBody(required = false) ZxEqEquipFactItem zxEqEquipFactItem) {
        return zxEqEquipFactItemService.getZxEqEquipFactItemDetails(zxEqEquipFactItem);
    }

    @ApiOperation(value="新增项目设备管理-设备核实台账(月)明细", notes="新增项目设备管理-设备核实台账(月)明细")
    @ApiImplicitParam(name = "zxEqEquipFactItem", value = "项目设备管理-设备核实台账(月)明细entity", dataType = "ZxEqEquipFactItem")
    @RequireToken
    @PostMapping("/addZxEqEquipFactItem")
    public ResponseEntity addZxEqEquipFactItem(@RequestBody(required = false) ZxEqEquipFactItem zxEqEquipFactItem) {
        return zxEqEquipFactItemService.saveZxEqEquipFactItem(zxEqEquipFactItem);
    }

    @ApiOperation(value="更新项目设备管理-设备核实台账(月)明细", notes="更新项目设备管理-设备核实台账(月)明细")
    @ApiImplicitParam(name = "zxEqEquipFactItem", value = "项目设备管理-设备核实台账(月)明细entity", dataType = "ZxEqEquipFactItem")
    @RequireToken
    @PostMapping("/updateZxEqEquipFactItem")
    public ResponseEntity updateZxEqEquipFactItem(@RequestBody(required = false) ZxEqEquipFactItem zxEqEquipFactItem) {
        return zxEqEquipFactItemService.updateZxEqEquipFactItem(zxEqEquipFactItem);
    }

    @ApiOperation(value="删除项目设备管理-设备核实台账(月)明细", notes="删除项目设备管理-设备核实台账(月)明细")
    @ApiImplicitParam(name = "zxEqEquipFactItemList", value = "项目设备管理-设备核实台账(月)明细List", dataType = "List<ZxEqEquipFactItem>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxEqEquipFactItem")
    public ResponseEntity batchDeleteUpdateZxEqEquipFactItem(@RequestBody(required = false) List<ZxEqEquipFactItem> zxEqEquipFactItemList) {
        return zxEqEquipFactItemService.batchDeleteUpdateZxEqEquipFactItem(zxEqEquipFactItemList);
    }

}
