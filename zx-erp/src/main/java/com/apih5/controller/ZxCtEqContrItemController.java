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
import com.apih5.mybatis.pojo.ZxCtEqContrItem;
import com.apih5.service.ZxCtEqContrItemService;

@RestController
public class ZxCtEqContrItemController {

    @Autowired(required = true)
    private ZxCtEqContrItemService zxCtEqContrItemService;

    @ApiOperation(value="查询设备管理合同清单明细表", notes="查询设备管理合同清单明细表")
    @ApiImplicitParam(name = "zxCtEqContrItem", value = "设备管理合同清单明细表entity", dataType = "ZxCtEqContrItem")
    @RequireToken
    @PostMapping("/getZxCtEqContrItemList")
    public ResponseEntity getZxCtEqContrItemList(@RequestBody(required = false) ZxCtEqContrItem zxCtEqContrItem) {
        return zxCtEqContrItemService.getZxCtEqContrItemListByCondition(zxCtEqContrItem);
    }

    @ApiOperation(value="查询详情设备管理合同清单明细表", notes="查询详情设备管理合同清单明细表")
    @ApiImplicitParam(name = "zxCtEqContrItem", value = "设备管理合同清单明细表entity", dataType = "ZxCtEqContrItem")
    @RequireToken
    @PostMapping("/getZxCtEqContrItemDetail")
    public ResponseEntity getZxCtEqContrItemDetail(@RequestBody(required = false) ZxCtEqContrItem zxCtEqContrItem) {
        return zxCtEqContrItemService.getZxCtEqContrItemDetail(zxCtEqContrItem);
    }

    @ApiOperation(value="新增设备管理合同清单明细表", notes="新增设备管理合同清单明细表")
    @ApiImplicitParam(name = "zxCtEqContrItem", value = "设备管理合同清单明细表entity", dataType = "ZxCtEqContrItem")
    @RequireToken
    @PostMapping("/addZxCtEqContrItem")
    public ResponseEntity addZxCtEqContrItem(@RequestBody(required = false) ZxCtEqContrItem zxCtEqContrItem) {
        return zxCtEqContrItemService.saveZxCtEqContrItem(zxCtEqContrItem);
    }

    @ApiOperation(value="更新设备管理合同清单明细表", notes="更新设备管理合同清单明细表")
    @ApiImplicitParam(name = "zxCtEqContrItem", value = "设备管理合同清单明细表entity", dataType = "ZxCtEqContrItem")
    @RequireToken
    @PostMapping("/updateZxCtEqContrItem")
    public ResponseEntity updateZxCtEqContrItem(@RequestBody(required = false) ZxCtEqContrItem zxCtEqContrItem) {
        return zxCtEqContrItemService.updateZxCtEqContrItem(zxCtEqContrItem);
    }

    @ApiOperation(value="删除设备管理合同清单明细表", notes="删除设备管理合同清单明细表")
    @ApiImplicitParam(name = "zxCtEqContrItemList", value = "设备管理合同清单明细表List", dataType = "List<ZxCtEqContrItem>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxCtEqContrItem")
    public ResponseEntity batchDeleteUpdateZxCtEqContrItem(@RequestBody(required = false) List<ZxCtEqContrItem> zxCtEqContrItemList) {
        return zxCtEqContrItemService.batchDeleteUpdateZxCtEqContrItem(zxCtEqContrItemList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    
    @ApiOperation(value="查询设备管理合同清单明细-限价列表", notes="查询设备管理合同清单明细-限价列表")
    @ApiImplicitParam(name = "zxCtEqContrItem", value = "设备管理合同清单明细表entity", dataType = "ZxCtEqContrItem")
    @RequireToken
    @PostMapping("/getZxCtEqContrItemForLimitPriceList")
    public ResponseEntity getZxCtEqContrItemForLimitPriceList(@RequestBody(required = false) ZxCtEqContrItem zxCtEqContrItem) {
        return zxCtEqContrItemService.getZxCtEqContrItemForLimitPriceList(zxCtEqContrItem);
    }
    
    
    @ApiOperation(value="导入设备管理合同清单明细表", notes="导入设备管理合同清单明细表")
    @ApiImplicitParam(name = "zxCtEqContrItem", value = "设备管理合同清单明细表entity", dataType = "ZxCtEqContrItem")
    @RequireToken
    @PostMapping("/importZxCtEqContrItem")
    public ResponseEntity importZxCtEqContrItem(@RequestBody(required = false) ZxCtEqContrItem zxCtEqContrItem) {
        return zxCtEqContrItemService.importZxCtEqContrItem(zxCtEqContrItem);
    }
    
    @ApiOperation(value="设备合同台账时查询设备管理合同清单明细表", notes="设备合同台账时查询设备管理合同清单明细表")
    @ApiImplicitParam(name = "zxCtEqContrItem", value = "设备管理合同清单明细表entity", dataType = "ZxCtEqContrItem")
    @RequireToken
    @PostMapping("/getZxCtEqContrItemListForContract")
    public ResponseEntity getZxCtEqContrItemListForContract(@RequestBody(required = false) ZxCtEqContrItem zxCtEqContrItem) {
        return zxCtEqContrItemService.getZxCtEqContrItemListForContract(zxCtEqContrItem);
    }
    
}