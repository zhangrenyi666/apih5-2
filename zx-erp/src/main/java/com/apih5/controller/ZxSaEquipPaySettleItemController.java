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
import com.apih5.mybatis.pojo.ZxSaEquipPaySettleItem;
import com.apih5.service.ZxSaEquipPaySettleItemService;

@RestController
public class ZxSaEquipPaySettleItemController {

    @Autowired(required = true)
    private ZxSaEquipPaySettleItemService zxSaEquipPaySettleItemService;

    @ApiOperation(value="查询支付项结算明细表", notes="查询支付项结算明细表")
    @ApiImplicitParam(name = "zxSaEquipPaySettleItem", value = "支付项结算明细表entity", dataType = "ZxSaEquipPaySettleItem")
    @RequireToken
    @PostMapping("/getZxSaEquipPaySettleItemList")
    public ResponseEntity getZxSaEquipPaySettleItemList(@RequestBody(required = false) ZxSaEquipPaySettleItem zxSaEquipPaySettleItem) {
        return zxSaEquipPaySettleItemService.getZxSaEquipPaySettleItemListByCondition(zxSaEquipPaySettleItem);
    }

    @ApiOperation(value="查询详情支付项结算明细表", notes="查询详情支付项结算明细表")
    @ApiImplicitParam(name = "zxSaEquipPaySettleItem", value = "支付项结算明细表entity", dataType = "ZxSaEquipPaySettleItem")
    @RequireToken
    @PostMapping("/getZxSaEquipPaySettleItemDetail")
    public ResponseEntity getZxSaEquipPaySettleItemDetail(@RequestBody(required = false) ZxSaEquipPaySettleItem zxSaEquipPaySettleItem) {
        return zxSaEquipPaySettleItemService.getZxSaEquipPaySettleItemDetail(zxSaEquipPaySettleItem);
    }

    @ApiOperation(value="新增支付项结算明细表", notes="新增支付项结算明细表")
    @ApiImplicitParam(name = "zxSaEquipPaySettleItem", value = "支付项结算明细表entity", dataType = "ZxSaEquipPaySettleItem")
    @RequireToken
    @PostMapping("/addZxSaEquipPaySettleItem")
    public ResponseEntity addZxSaEquipPaySettleItem(@RequestBody(required = false) ZxSaEquipPaySettleItem zxSaEquipPaySettleItem) {
        return zxSaEquipPaySettleItemService.saveZxSaEquipPaySettleItem(zxSaEquipPaySettleItem);
    }

    @ApiOperation(value="更新支付项结算明细表", notes="更新支付项结算明细表")
    @ApiImplicitParam(name = "zxSaEquipPaySettleItem", value = "支付项结算明细表entity", dataType = "ZxSaEquipPaySettleItem")
    @RequireToken
    @PostMapping("/updateZxSaEquipPaySettleItem")
    public ResponseEntity updateZxSaEquipPaySettleItem(@RequestBody(required = false) ZxSaEquipPaySettleItem zxSaEquipPaySettleItem) {
        return zxSaEquipPaySettleItemService.updateZxSaEquipPaySettleItem(zxSaEquipPaySettleItem);
    }

    @ApiOperation(value="删除支付项结算明细表", notes="删除支付项结算明细表")
    @ApiImplicitParam(name = "zxSaEquipPaySettleItemList", value = "支付项结算明细表List", dataType = "List<ZxSaEquipPaySettleItem>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxSaEquipPaySettleItem")
    public ResponseEntity batchDeleteUpdateZxSaEquipPaySettleItem(@RequestBody(required = false) List<ZxSaEquipPaySettleItem> zxSaEquipPaySettleItemList) {
        return zxSaEquipPaySettleItemService.batchDeleteUpdateZxSaEquipPaySettleItem(zxSaEquipPaySettleItemList);
    }

    @ApiOperation(value="报表导出支付项结算明细表", notes="报表导出支付项结算明细表")
    @ApiImplicitParam(name = "zxSaEquipPaySettleItem", value = "支付项结算明细表entity", dataType = "ZxSaEquipPaySettleItem")
    @PostMapping("/ureportZxSaEquipPaySettleItemList")
    public List<ZxSaEquipPaySettleItem> ureportZxSaEquipPaySettleItemList(@RequestBody(required = false) ZxSaEquipPaySettleItem zxSaEquipPaySettleItem) {
        return zxSaEquipPaySettleItemService.ureportZxSaEquipPaySettleItemList(zxSaEquipPaySettleItem);
    }
    
    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
