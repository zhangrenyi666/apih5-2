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
import com.apih5.mybatis.pojo.ZxEqMoveUseOrgItem;
import com.apih5.service.ZxEqMoveUseOrgItemService;

@RestController
public class ZxEqMoveUseOrgItemController {

    @Autowired(required = true)
    private ZxEqMoveUseOrgItemService zxEqMoveUseOrgItemService;

    @ApiOperation(value="查询设备异动-公司内调拨明细", notes="查询设备异动-公司内调拨明细")
    @ApiImplicitParam(name = "zxEqMoveUseOrgItem", value = "设备异动-公司内调拨明细entity", dataType = "ZxEqMoveUseOrgItem")
    @RequireToken
    @PostMapping("/getZxEqMoveUseOrgItemList")
    public ResponseEntity getZxEqMoveUseOrgItemList(@RequestBody(required = false) ZxEqMoveUseOrgItem zxEqMoveUseOrgItem) {
        return zxEqMoveUseOrgItemService.getZxEqMoveUseOrgItemListByCondition(zxEqMoveUseOrgItem);
    }

    @ApiOperation(value="查询详情设备异动-公司内调拨明细", notes="查询详情设备异动-公司内调拨明细")
    @ApiImplicitParam(name = "zxEqMoveUseOrgItem", value = "设备异动-公司内调拨明细entity", dataType = "ZxEqMoveUseOrgItem")
    @RequireToken
    @PostMapping("/getZxEqMoveUseOrgItemDetails")
    public ResponseEntity getZxEqMoveUseOrgItemDetails(@RequestBody(required = false) ZxEqMoveUseOrgItem zxEqMoveUseOrgItem) {
        return zxEqMoveUseOrgItemService.getZxEqMoveUseOrgItemDetails(zxEqMoveUseOrgItem);
    }

    @ApiOperation(value="新增设备异动-公司内调拨明细", notes="新增设备异动-公司内调拨明细")
    @ApiImplicitParam(name = "zxEqMoveUseOrgItem", value = "设备异动-公司内调拨明细entity", dataType = "ZxEqMoveUseOrgItem")
    @RequireToken
    @PostMapping("/addZxEqMoveUseOrgItem")
    public ResponseEntity addZxEqMoveUseOrgItem(@RequestBody(required = false) ZxEqMoveUseOrgItem zxEqMoveUseOrgItem) {
        return zxEqMoveUseOrgItemService.saveZxEqMoveUseOrgItem(zxEqMoveUseOrgItem);
    }

    @ApiOperation(value="更新设备异动-公司内调拨明细", notes="更新设备异动-公司内调拨明细")
    @ApiImplicitParam(name = "zxEqMoveUseOrgItem", value = "设备异动-公司内调拨明细entity", dataType = "ZxEqMoveUseOrgItem")
    @RequireToken
    @PostMapping("/updateZxEqMoveUseOrgItem")
    public ResponseEntity updateZxEqMoveUseOrgItem(@RequestBody(required = false) ZxEqMoveUseOrgItem zxEqMoveUseOrgItem) {
        return zxEqMoveUseOrgItemService.updateZxEqMoveUseOrgItem(zxEqMoveUseOrgItem);
    }

    @ApiOperation(value="删除设备异动-公司内调拨明细", notes="删除设备异动-公司内调拨明细")
    @ApiImplicitParam(name = "zxEqMoveUseOrgItemList", value = "设备异动-公司内调拨明细List", dataType = "List<ZxEqMoveUseOrgItem>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxEqMoveUseOrgItem")
    public ResponseEntity batchDeleteUpdateZxEqMoveUseOrgItem(@RequestBody(required = false) List<ZxEqMoveUseOrgItem> zxEqMoveUseOrgItemList) {
        return zxEqMoveUseOrgItemService.batchDeleteUpdateZxEqMoveUseOrgItem(zxEqMoveUseOrgItemList);
    }
    
    @ApiOperation(value="设备台账tab查询设备异动-公司内调拨明细", notes="查询设备异动-公司内调拨明细")
    @ApiImplicitParam(name = "zxEqMoveUseOrgItem", value = "设备异动-公司内调拨明细entity", dataType = "ZxEqMoveUseOrgItem")
    @RequireToken
    @PostMapping("/getZxEqMoveUseOrgItemListForTab")
    public ResponseEntity getZxEqMoveUseOrgItemListForTab(@RequestBody(required = false) ZxEqMoveUseOrgItem zxEqMoveUseOrgItem) {
        return zxEqMoveUseOrgItemService.getZxEqMoveUseOrgItemListForTab(zxEqMoveUseOrgItem);
    }
    
    @ApiOperation(value="报表导出设备异动-公司内调拨明细", notes="报表导出设备异动-公司内调拨明细")
    @ApiImplicitParam(name = "zxEqMoveUseOrgItem", value = "设备异动-公司内调拨明细entity", dataType = "ZxEqMoveUseOrgItem")
    @PostMapping("/ureportZxEqMoveUseOrgItemList")
    public List<ZxEqMoveUseOrgItem> ureportZxEqMoveUseOrgItemList(@RequestBody(required = false) ZxEqMoveUseOrgItem zxEqMoveUseOrgItem) {
        return zxEqMoveUseOrgItemService.ureportZxEqMoveUseOrgItemList(zxEqMoveUseOrgItem);
    }
    
}