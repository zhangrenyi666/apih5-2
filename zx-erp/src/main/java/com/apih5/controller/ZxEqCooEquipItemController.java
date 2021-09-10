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
import com.apih5.mybatis.pojo.ZxEqCooEquipItem;
import com.apih5.service.ZxEqCooEquipItemService;

@RestController
public class ZxEqCooEquipItemController {

    @Autowired(required = true)
    private ZxEqCooEquipItemService zxEqCooEquipItemService;

    @ApiOperation(value="查询项目设备管理-协作单位自带设备入场登记明细", notes="查询项目设备管理-协作单位自带设备入场登记明细")
    @ApiImplicitParam(name = "zxEqCooEquipItem", value = "项目设备管理-协作单位自带设备入场登记明细entity", dataType = "ZxEqCooEquipItem")
    @RequireToken
    @PostMapping("/getZxEqCooEquipItemList")
    public ResponseEntity getZxEqCooEquipItemList(@RequestBody(required = false) ZxEqCooEquipItem zxEqCooEquipItem) {
        return zxEqCooEquipItemService.getZxEqCooEquipItemListByCondition(zxEqCooEquipItem);
    }

    @ApiOperation(value="查询详情项目设备管理-协作单位自带设备入场登记明细", notes="查询详情项目设备管理-协作单位自带设备入场登记明细")
    @ApiImplicitParam(name = "zxEqCooEquipItem", value = "项目设备管理-协作单位自带设备入场登记明细entity", dataType = "ZxEqCooEquipItem")
    @RequireToken
    @PostMapping("/getZxEqCooEquipItemDetails")
    public ResponseEntity getZxEqCooEquipItemDetails(@RequestBody(required = false) ZxEqCooEquipItem zxEqCooEquipItem) {
        return zxEqCooEquipItemService.getZxEqCooEquipItemDetails(zxEqCooEquipItem);
    }

    @ApiOperation(value="新增项目设备管理-协作单位自带设备入场登记明细", notes="新增项目设备管理-协作单位自带设备入场登记明细")
    @ApiImplicitParam(name = "zxEqCooEquipItem", value = "项目设备管理-协作单位自带设备入场登记明细entity", dataType = "ZxEqCooEquipItem")
    @RequireToken
    @PostMapping("/addZxEqCooEquipItem")
    public ResponseEntity addZxEqCooEquipItem(@RequestBody(required = false) ZxEqCooEquipItem zxEqCooEquipItem) {
        return zxEqCooEquipItemService.saveZxEqCooEquipItem(zxEqCooEquipItem);
    }

    @ApiOperation(value="更新项目设备管理-协作单位自带设备入场登记明细", notes="更新项目设备管理-协作单位自带设备入场登记明细")
    @ApiImplicitParam(name = "zxEqCooEquipItem", value = "项目设备管理-协作单位自带设备入场登记明细entity", dataType = "ZxEqCooEquipItem")
    @RequireToken
    @PostMapping("/updateZxEqCooEquipItem")
    public ResponseEntity updateZxEqCooEquipItem(@RequestBody(required = false) ZxEqCooEquipItem zxEqCooEquipItem) {
        return zxEqCooEquipItemService.updateZxEqCooEquipItem(zxEqCooEquipItem);
    }

    @ApiOperation(value="删除项目设备管理-协作单位自带设备入场登记明细", notes="删除项目设备管理-协作单位自带设备入场登记明细")
    @ApiImplicitParam(name = "zxEqCooEquipItemList", value = "项目设备管理-协作单位自带设备入场登记明细List", dataType = "List<ZxEqCooEquipItem>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxEqCooEquipItem")
    public ResponseEntity batchDeleteUpdateZxEqCooEquipItem(@RequestBody(required = false) List<ZxEqCooEquipItem> zxEqCooEquipItemList) {
        return zxEqCooEquipItemService.batchDeleteUpdateZxEqCooEquipItem(zxEqCooEquipItemList);
    }

}
