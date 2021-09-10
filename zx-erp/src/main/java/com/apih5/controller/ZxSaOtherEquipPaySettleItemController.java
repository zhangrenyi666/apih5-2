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
import com.apih5.mybatis.pojo.ZxSaOtherEquipPaySettleItem;
import com.apih5.service.ZxSaOtherEquipPaySettleItemService;

@RestController
public class ZxSaOtherEquipPaySettleItemController {

    @Autowired(required = true)
    private ZxSaOtherEquipPaySettleItemService zxSaOtherEquipPaySettleItemService;

    @ApiOperation(value="查询其他类支付项结算清单明细", notes="查询其他类支付项结算清单明细")
    @ApiImplicitParam(name = "zxSaOtherEquipPaySettleItem", value = "其他类支付项结算清单明细entity", dataType = "ZxSaOtherEquipPaySettleItem")
    @RequireToken
    @PostMapping("/getZxSaOtherEquipPaySettleItemList")
    public ResponseEntity getZxSaOtherEquipPaySettleItemList(@RequestBody(required = false) ZxSaOtherEquipPaySettleItem zxSaOtherEquipPaySettleItem) {
        return zxSaOtherEquipPaySettleItemService.getZxSaOtherEquipPaySettleItemListByCondition(zxSaOtherEquipPaySettleItem);
    }

    @ApiOperation(value="查询详情其他类支付项结算清单明细", notes="查询详情其他类支付项结算清单明细")
    @ApiImplicitParam(name = "zxSaOtherEquipPaySettleItem", value = "其他类支付项结算清单明细entity", dataType = "ZxSaOtherEquipPaySettleItem")
    @RequireToken
    @PostMapping("/getZxSaOtherEquipPaySettleItemDetail")
    public ResponseEntity getZxSaOtherEquipPaySettleItemDetail(@RequestBody(required = false) ZxSaOtherEquipPaySettleItem zxSaOtherEquipPaySettleItem) {
        return zxSaOtherEquipPaySettleItemService.getZxSaOtherEquipPaySettleItemDetail(zxSaOtherEquipPaySettleItem);
    }

    @ApiOperation(value="新增其他类支付项结算清单明细", notes="新增其他类支付项结算清单明细")
    @ApiImplicitParam(name = "zxSaOtherEquipPaySettleItem", value = "其他类支付项结算清单明细entity", dataType = "ZxSaOtherEquipPaySettleItem")
    @RequireToken
    @PostMapping("/addZxSaOtherEquipPaySettleItem")
    public ResponseEntity addZxSaOtherEquipPaySettleItem(@RequestBody(required = false) ZxSaOtherEquipPaySettleItem zxSaOtherEquipPaySettleItem) {
        return zxSaOtherEquipPaySettleItemService.saveZxSaOtherEquipPaySettleItem(zxSaOtherEquipPaySettleItem);
    }

    @ApiOperation(value="更新其他类支付项结算清单明细", notes="更新其他类支付项结算清单明细")
    @ApiImplicitParam(name = "zxSaOtherEquipPaySettleItem", value = "其他类支付项结算清单明细entity", dataType = "ZxSaOtherEquipPaySettleItem")
    @RequireToken
    @PostMapping("/updateZxSaOtherEquipPaySettleItem")
    public ResponseEntity updateZxSaOtherEquipPaySettleItem(@RequestBody(required = false) ZxSaOtherEquipPaySettleItem zxSaOtherEquipPaySettleItem) {
        return zxSaOtherEquipPaySettleItemService.updateZxSaOtherEquipPaySettleItem(zxSaOtherEquipPaySettleItem);
    }

    @ApiOperation(value="删除其他类支付项结算清单明细", notes="删除其他类支付项结算清单明细")
    @ApiImplicitParam(name = "zxSaOtherEquipPaySettleItemList", value = "其他类支付项结算清单明细List", dataType = "List<ZxSaOtherEquipPaySettleItem>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxSaOtherEquipPaySettleItem")
    public ResponseEntity batchDeleteUpdateZxSaOtherEquipPaySettleItem(@RequestBody(required = false) List<ZxSaOtherEquipPaySettleItem> zxSaOtherEquipPaySettleItemList) {
        return zxSaOtherEquipPaySettleItemService.batchDeleteUpdateZxSaOtherEquipPaySettleItem(zxSaOtherEquipPaySettleItemList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
