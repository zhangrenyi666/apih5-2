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
import com.apih5.mybatis.pojo.ZxSkInvoiceItem;
import com.apih5.service.ZxSkInvoiceItemService;

@RestController
public class ZxSkInvoiceItemController {

    @Autowired(required = true)
    private ZxSkInvoiceItemService zxSkInvoiceItemService;

    @ApiOperation(value="查询冲账单明细", notes="查询冲账单明细")
    @ApiImplicitParam(name = "zxSkInvoiceItem", value = "冲账单明细entity", dataType = "ZxSkInvoiceItem")
    @RequireToken
    @PostMapping("/getZxSkInvoiceItemList")
    public ResponseEntity getZxSkInvoiceItemList(@RequestBody(required = false) ZxSkInvoiceItem zxSkInvoiceItem) {
        return zxSkInvoiceItemService.getZxSkInvoiceItemListByCondition(zxSkInvoiceItem);
    }

    @ApiOperation(value="查询详情冲账单明细", notes="查询详情冲账单明细")
    @ApiImplicitParam(name = "zxSkInvoiceItem", value = "冲账单明细entity", dataType = "ZxSkInvoiceItem")
    @RequireToken
    @PostMapping("/getZxSkInvoiceItemDetail")
    public ResponseEntity getZxSkInvoiceItemDetail(@RequestBody(required = false) ZxSkInvoiceItem zxSkInvoiceItem) {
        return zxSkInvoiceItemService.getZxSkInvoiceItemDetail(zxSkInvoiceItem);
    }

    @ApiOperation(value="新增冲账单明细", notes="新增冲账单明细")
    @ApiImplicitParam(name = "zxSkInvoiceItem", value = "冲账单明细entity", dataType = "ZxSkInvoiceItem")
    @RequireToken
    @PostMapping("/addZxSkInvoiceItem")
    public ResponseEntity addZxSkInvoiceItem(@RequestBody(required = false) ZxSkInvoiceItem zxSkInvoiceItem) {
        return zxSkInvoiceItemService.saveZxSkInvoiceItem(zxSkInvoiceItem);
    }

    @ApiOperation(value="更新冲账单明细", notes="更新冲账单明细")
    @ApiImplicitParam(name = "zxSkInvoiceItem", value = "冲账单明细entity", dataType = "ZxSkInvoiceItem")
    @RequireToken
    @PostMapping("/updateZxSkInvoiceItem")
    public ResponseEntity updateZxSkInvoiceItem(@RequestBody(required = false) ZxSkInvoiceItem zxSkInvoiceItem) {
        return zxSkInvoiceItemService.updateZxSkInvoiceItem(zxSkInvoiceItem);
    }

    @ApiOperation(value="删除冲账单明细", notes="删除冲账单明细")
    @ApiImplicitParam(name = "zxSkInvoiceItemList", value = "冲账单明细List", dataType = "List<ZxSkInvoiceItem>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxSkInvoiceItem")
    public ResponseEntity batchDeleteUpdateZxSkInvoiceItem(@RequestBody(required = false) List<ZxSkInvoiceItem> zxSkInvoiceItemList) {
        return zxSkInvoiceItemService.batchDeleteUpdateZxSkInvoiceItem(zxSkInvoiceItemList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
