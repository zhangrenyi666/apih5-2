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
import com.apih5.mybatis.pojo.ZxSaEquipSettleAuditItem;
import com.apih5.service.ZxSaEquipSettleAuditItemService;

@RestController
public class ZxSaEquipSettleAuditItemController {

    @Autowired(required = true)
    private ZxSaEquipSettleAuditItemService zxSaEquipSettleAuditItemService;

    @ApiOperation(value="查询结算单明细表", notes="查询结算单明细表")
    @ApiImplicitParam(name = "zxSaEquipSettleAuditItem", value = "结算单明细表entity", dataType = "ZxSaEquipSettleAuditItem")
    @RequireToken
    @PostMapping("/getZxSaEquipSettleAuditItemList")
    public ResponseEntity getZxSaEquipSettleAuditItemList(@RequestBody(required = false) ZxSaEquipSettleAuditItem zxSaEquipSettleAuditItem) {
        return zxSaEquipSettleAuditItemService.getZxSaEquipSettleAuditItemListByCondition(zxSaEquipSettleAuditItem);
    }

    @ApiOperation(value="查询详情结算单明细表", notes="查询详情结算单明细表")
    @ApiImplicitParam(name = "zxSaEquipSettleAuditItem", value = "结算单明细表entity", dataType = "ZxSaEquipSettleAuditItem")
    @RequireToken
    @PostMapping("/getZxSaEquipSettleAuditItemDetail")
    public ResponseEntity getZxSaEquipSettleAuditItemDetail(@RequestBody(required = false) ZxSaEquipSettleAuditItem zxSaEquipSettleAuditItem) {
        return zxSaEquipSettleAuditItemService.getZxSaEquipSettleAuditItemDetail(zxSaEquipSettleAuditItem);
    }

    @ApiOperation(value="新增结算单明细表", notes="新增结算单明细表")
    @ApiImplicitParam(name = "zxSaEquipSettleAuditItem", value = "结算单明细表entity", dataType = "ZxSaEquipSettleAuditItem")
    @RequireToken
    @PostMapping("/addZxSaEquipSettleAuditItem")
    public ResponseEntity addZxSaEquipSettleAuditItem(@RequestBody(required = false) ZxSaEquipSettleAuditItem zxSaEquipSettleAuditItem) {
        return zxSaEquipSettleAuditItemService.saveZxSaEquipSettleAuditItem(zxSaEquipSettleAuditItem);
    }

    @ApiOperation(value="更新结算单明细表", notes="更新结算单明细表")
    @ApiImplicitParam(name = "zxSaEquipSettleAuditItem", value = "结算单明细表entity", dataType = "ZxSaEquipSettleAuditItem")
    @RequireToken
    @PostMapping("/updateZxSaEquipSettleAuditItem")
    public ResponseEntity updateZxSaEquipSettleAuditItem(@RequestBody(required = false) ZxSaEquipSettleAuditItem zxSaEquipSettleAuditItem) {
        return zxSaEquipSettleAuditItemService.updateZxSaEquipSettleAuditItem(zxSaEquipSettleAuditItem);
    }

    @ApiOperation(value="删除结算单明细表", notes="删除结算单明细表")
    @ApiImplicitParam(name = "zxSaEquipSettleAuditItemList", value = "结算单明细表List", dataType = "List<ZxSaEquipSettleAuditItem>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxSaEquipSettleAuditItem")
    public ResponseEntity batchDeleteUpdateZxSaEquipSettleAuditItem(@RequestBody(required = false) List<ZxSaEquipSettleAuditItem> zxSaEquipSettleAuditItemList) {
        return zxSaEquipSettleAuditItemService.batchDeleteUpdateZxSaEquipSettleAuditItem(zxSaEquipSettleAuditItemList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
