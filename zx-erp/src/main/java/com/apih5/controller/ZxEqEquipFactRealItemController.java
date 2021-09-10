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
import com.apih5.mybatis.pojo.ZxEqEquipFactRealItem;
import com.apih5.service.ZxEqEquipFactRealItemService;

@RestController
public class ZxEqEquipFactRealItemController {

    @Autowired(required = true)
    private ZxEqEquipFactRealItemService zxEqEquipFactRealItemService;

    @ApiOperation(value="查询项目设备管理-设备实际验收台账(月)明细", notes="查询项目设备管理-设备实际验收台账(月)明细")
    @ApiImplicitParam(name = "zxEqEquipFactRealItem", value = "项目设备管理-设备实际验收台账(月)明细entity", dataType = "ZxEqEquipFactRealItem")
    @RequireToken
    @PostMapping("/getZxEqEquipFactRealItemList")
    public ResponseEntity getZxEqEquipFactRealItemList(@RequestBody(required = false) ZxEqEquipFactRealItem zxEqEquipFactRealItem) {
        return zxEqEquipFactRealItemService.getZxEqEquipFactRealItemListByCondition(zxEqEquipFactRealItem);
    }

    @ApiOperation(value="查询详情项目设备管理-设备实际验收台账(月)明细", notes="查询详情项目设备管理-设备实际验收台账(月)明细")
    @ApiImplicitParam(name = "zxEqEquipFactRealItem", value = "项目设备管理-设备实际验收台账(月)明细entity", dataType = "ZxEqEquipFactRealItem")
    @RequireToken
    @PostMapping("/getZxEqEquipFactRealItemDetails")
    public ResponseEntity getZxEqEquipFactRealItemDetails(@RequestBody(required = false) ZxEqEquipFactRealItem zxEqEquipFactRealItem) {
        return zxEqEquipFactRealItemService.getZxEqEquipFactRealItemDetails(zxEqEquipFactRealItem);
    }

    @ApiOperation(value="新增项目设备管理-设备实际验收台账(月)明细", notes="新增项目设备管理-设备实际验收台账(月)明细")
    @ApiImplicitParam(name = "zxEqEquipFactRealItem", value = "项目设备管理-设备实际验收台账(月)明细entity", dataType = "ZxEqEquipFactRealItem")
    @RequireToken
    @PostMapping("/addZxEqEquipFactRealItem")
    public ResponseEntity addZxEqEquipFactRealItem(@RequestBody(required = false) ZxEqEquipFactRealItem zxEqEquipFactRealItem) {
        return zxEqEquipFactRealItemService.saveZxEqEquipFactRealItem(zxEqEquipFactRealItem);
    }

    @ApiOperation(value="更新项目设备管理-设备实际验收台账(月)明细", notes="更新项目设备管理-设备实际验收台账(月)明细")
    @ApiImplicitParam(name = "zxEqEquipFactRealItem", value = "项目设备管理-设备实际验收台账(月)明细entity", dataType = "ZxEqEquipFactRealItem")
    @RequireToken
    @PostMapping("/updateZxEqEquipFactRealItem")
    public ResponseEntity updateZxEqEquipFactRealItem(@RequestBody(required = false) ZxEqEquipFactRealItem zxEqEquipFactRealItem) {
        return zxEqEquipFactRealItemService.updateZxEqEquipFactRealItem(zxEqEquipFactRealItem);
    }

    @ApiOperation(value="删除项目设备管理-设备实际验收台账(月)明细", notes="删除项目设备管理-设备实际验收台账(月)明细")
    @ApiImplicitParam(name = "zxEqEquipFactRealItemList", value = "项目设备管理-设备实际验收台账(月)明细List", dataType = "List<ZxEqEquipFactRealItem>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxEqEquipFactRealItem")
    public ResponseEntity batchDeleteUpdateZxEqEquipFactRealItem(@RequestBody(required = false) List<ZxEqEquipFactRealItem> zxEqEquipFactRealItemList) {
        return zxEqEquipFactRealItemService.batchDeleteUpdateZxEqEquipFactRealItem(zxEqEquipFactRealItemList);
    }

}
