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
import com.apih5.mybatis.pojo.ZxEqEWorkItem;
import com.apih5.service.ZxEqEWorkItemService;

@RestController
public class ZxEqEWorkItemController {

    @Autowired(required = true)
    private ZxEqEWorkItemService zxEqEWorkItemService;

    @ApiOperation(value="查询项目设备管理-机械设备运转记录明细", notes="查询项目设备管理-机械设备运转记录明细")
    @ApiImplicitParam(name = "zxEqEWorkItem", value = "项目设备管理-机械设备运转记录明细entity", dataType = "ZxEqEWorkItem")
    @RequireToken
    @PostMapping("/getZxEqEWorkItemList")
    public ResponseEntity getZxEqEWorkItemList(@RequestBody(required = false) ZxEqEWorkItem zxEqEWorkItem) {
        return zxEqEWorkItemService.getZxEqEWorkItemListByCondition(zxEqEWorkItem);
    }

    @ApiOperation(value="查询详情项目设备管理-机械设备运转记录明细", notes="查询详情项目设备管理-机械设备运转记录明细")
    @ApiImplicitParam(name = "zxEqEWorkItem", value = "项目设备管理-机械设备运转记录明细entity", dataType = "ZxEqEWorkItem")
    @RequireToken
    @PostMapping("/getZxEqEWorkItemDetails")
    public ResponseEntity getZxEqEWorkItemDetails(@RequestBody(required = false) ZxEqEWorkItem zxEqEWorkItem) {
        return zxEqEWorkItemService.getZxEqEWorkItemDetails(zxEqEWorkItem);
    }

    @ApiOperation(value="新增项目设备管理-机械设备运转记录明细", notes="新增项目设备管理-机械设备运转记录明细")
    @ApiImplicitParam(name = "zxEqEWorkItem", value = "项目设备管理-机械设备运转记录明细entity", dataType = "ZxEqEWorkItem")
    @RequireToken
    @PostMapping("/addZxEqEWorkItem")
    public ResponseEntity addZxEqEWorkItem(@RequestBody(required = false) ZxEqEWorkItem zxEqEWorkItem) {
        return zxEqEWorkItemService.saveZxEqEWorkItem(zxEqEWorkItem);
    }

    @ApiOperation(value="更新项目设备管理-机械设备运转记录明细", notes="更新项目设备管理-机械设备运转记录明细")
    @ApiImplicitParam(name = "zxEqEWorkItem", value = "项目设备管理-机械设备运转记录明细entity", dataType = "ZxEqEWorkItem")
    @RequireToken
    @PostMapping("/updateZxEqEWorkItem")
    public ResponseEntity updateZxEqEWorkItem(@RequestBody(required = false) ZxEqEWorkItem zxEqEWorkItem) {
        return zxEqEWorkItemService.updateZxEqEWorkItem(zxEqEWorkItem);
    }

    @ApiOperation(value="删除项目设备管理-机械设备运转记录明细", notes="删除项目设备管理-机械设备运转记录明细")
    @ApiImplicitParam(name = "zxEqEWorkItemList", value = "项目设备管理-机械设备运转记录明细List", dataType = "List<ZxEqEWorkItem>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxEqEWorkItem")
    public ResponseEntity batchDeleteUpdateZxEqEWorkItem(@RequestBody(required = false) List<ZxEqEWorkItem> zxEqEWorkItemList) {
        return zxEqEWorkItemService.batchDeleteUpdateZxEqEWorkItem(zxEqEWorkItemList);
    }
    
    //报表
    @ApiOperation(value="查询项目设备管理-机械设备运转记录明细", notes="查询项目设备管理-机械设备运转记录明细")
    @ApiImplicitParam(name = "zxEqEWorkItem", value = "项目设备管理-机械设备运转记录明细entity", dataType = "ZxEqEWorkItem")
    @PostMapping("/ureportZxEqEWorkItemListForCar")
    public List<ZxEqEWorkItem> ureportZxEqEWorkItemListForCar(@RequestBody(required = false) ZxEqEWorkItem zxEqEWorkItem) {
        return zxEqEWorkItemService.ureportZxEqEWorkItemListForCar(zxEqEWorkItem);
    }
    


}
