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
import com.apih5.mybatis.pojo.ZxEqMoveSupervisorItem;
import com.apih5.service.ZxEqMoveSupervisorItemService;

@RestController
public class ZxEqMoveSupervisorItemController {

    @Autowired(required = true)
    private ZxEqMoveSupervisorItemService zxEqMoveSupervisorItemService;

    @ApiOperation(value="查询设备异动-局内调明细", notes="查询设备异动-局内调明细")
    @ApiImplicitParam(name = "zxEqMoveSupervisorItem", value = "设备异动-局内调明细entity", dataType = "ZxEqMoveSupervisorItem")
    @RequireToken
    @PostMapping("/getZxEqMoveSupervisorItemList")
    public ResponseEntity getZxEqMoveSupervisorItemList(@RequestBody(required = false) ZxEqMoveSupervisorItem zxEqMoveSupervisorItem) {
        return zxEqMoveSupervisorItemService.getZxEqMoveSupervisorItemListByCondition(zxEqMoveSupervisorItem);
    }

    @ApiOperation(value="查询详情设备异动-局内调明细", notes="查询详情设备异动-局内调明细")
    @ApiImplicitParam(name = "zxEqMoveSupervisorItem", value = "设备异动-局内调明细entity", dataType = "ZxEqMoveSupervisorItem")
    @RequireToken
    @PostMapping("/getZxEqMoveSupervisorItemDetails")
    public ResponseEntity getZxEqMoveSupervisorItemDetails(@RequestBody(required = false) ZxEqMoveSupervisorItem zxEqMoveSupervisorItem) {
        return zxEqMoveSupervisorItemService.getZxEqMoveSupervisorItemDetails(zxEqMoveSupervisorItem);
    }

    @ApiOperation(value="新增设备异动-局内调明细", notes="新增设备异动-局内调明细")
    @ApiImplicitParam(name = "zxEqMoveSupervisorItem", value = "设备异动-局内调明细entity", dataType = "ZxEqMoveSupervisorItem")
    @RequireToken
    @PostMapping("/addZxEqMoveSupervisorItem")
    public ResponseEntity addZxEqMoveSupervisorItem(@RequestBody(required = false) ZxEqMoveSupervisorItem zxEqMoveSupervisorItem) {
        return zxEqMoveSupervisorItemService.saveZxEqMoveSupervisorItem(zxEqMoveSupervisorItem);
    }

    @ApiOperation(value="更新设备异动-局内调明细", notes="更新设备异动-局内调明细")
    @ApiImplicitParam(name = "zxEqMoveSupervisorItem", value = "设备异动-局内调明细entity", dataType = "ZxEqMoveSupervisorItem")
    @RequireToken
    @PostMapping("/updateZxEqMoveSupervisorItem")
    public ResponseEntity updateZxEqMoveSupervisorItem(@RequestBody(required = false) ZxEqMoveSupervisorItem zxEqMoveSupervisorItem) {
        return zxEqMoveSupervisorItemService.updateZxEqMoveSupervisorItem(zxEqMoveSupervisorItem);
    }

    @ApiOperation(value="删除设备异动-局内调明细", notes="删除设备异动-局内调明细")
    @ApiImplicitParam(name = "zxEqMoveSupervisorItemList", value = "设备异动-局内调明细List", dataType = "List<ZxEqMoveSupervisorItem>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxEqMoveSupervisorItem")
    public ResponseEntity batchDeleteUpdateZxEqMoveSupervisorItem(@RequestBody(required = false) List<ZxEqMoveSupervisorItem> zxEqMoveSupervisorItemList) {
        return zxEqMoveSupervisorItemService.batchDeleteUpdateZxEqMoveSupervisorItem(zxEqMoveSupervisorItemList);
    }
    
    @ApiOperation(value="设备台账tab查询设备异动-局内调明细", notes="设备台账tab查询设备异动-局内调明细")
    @ApiImplicitParam(name = "zxEqMoveSupervisorItem", value = "设备异动-局内调明细entity", dataType = "ZxEqMoveSupervisorItem")
    @RequireToken
    @PostMapping("/getZxEqMoveSupervisorItemListForTab")
    public ResponseEntity getZxEqMoveSupervisorItemListForTab(@RequestBody(required = false) ZxEqMoveSupervisorItem zxEqMoveSupervisorItem) {
        return zxEqMoveSupervisorItemService.getZxEqMoveSupervisorItemListForTab(zxEqMoveSupervisorItem);
    }

}
