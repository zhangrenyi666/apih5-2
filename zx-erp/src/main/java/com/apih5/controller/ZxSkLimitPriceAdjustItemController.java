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
import com.apih5.mybatis.pojo.ZxSkLimitPriceAdjustItem;
import com.apih5.service.ZxSkLimitPriceAdjustItemService;

@RestController
public class ZxSkLimitPriceAdjustItemController {

    @Autowired(required = true)
    private ZxSkLimitPriceAdjustItemService zxSkLimitPriceAdjustItemService;

    @ApiOperation(value="查询物资限价调整明细", notes="查询物资限价调整明细")
    @ApiImplicitParam(name = "zxSkLimitPriceAdjustItem", value = "物资限价调整明细entity", dataType = "ZxSkLimitPriceAdjustItem")
    @RequireToken
    @PostMapping("/getZxSkLimitPriceAdjustItemList")
    public ResponseEntity getZxSkLimitPriceAdjustItemList(@RequestBody(required = false) ZxSkLimitPriceAdjustItem zxSkLimitPriceAdjustItem) {
        return zxSkLimitPriceAdjustItemService.getZxSkLimitPriceAdjustItemListByCondition(zxSkLimitPriceAdjustItem);
    }

    @ApiOperation(value="查询详情物资限价调整明细", notes="查询详情物资限价调整明细")
    @ApiImplicitParam(name = "zxSkLimitPriceAdjustItem", value = "物资限价调整明细entity", dataType = "ZxSkLimitPriceAdjustItem")
    @RequireToken
    @PostMapping("/getZxSkLimitPriceAdjustItemDetails")
    public ResponseEntity getZxSkLimitPriceAdjustItemDetails(@RequestBody(required = false) ZxSkLimitPriceAdjustItem zxSkLimitPriceAdjustItem) {
        return zxSkLimitPriceAdjustItemService.getZxSkLimitPriceAdjustItemDetails(zxSkLimitPriceAdjustItem);
    }

    @ApiOperation(value="新增物资限价调整明细", notes="新增物资限价调整明细")
    @ApiImplicitParam(name = "zxSkLimitPriceAdjustItem", value = "物资限价调整明细entity", dataType = "ZxSkLimitPriceAdjustItem")
    @RequireToken
    @PostMapping("/addZxSkLimitPriceAdjustItem")
    public ResponseEntity addZxSkLimitPriceAdjustItem(@RequestBody(required = false) ZxSkLimitPriceAdjustItem zxSkLimitPriceAdjustItem) {
        return zxSkLimitPriceAdjustItemService.saveZxSkLimitPriceAdjustItem(zxSkLimitPriceAdjustItem);
    }

    @ApiOperation(value="更新物资限价调整明细", notes="更新物资限价调整明细")
    @ApiImplicitParam(name = "zxSkLimitPriceAdjustItem", value = "物资限价调整明细entity", dataType = "ZxSkLimitPriceAdjustItem")
    @RequireToken
    @PostMapping("/updateZxSkLimitPriceAdjustItem")
    public ResponseEntity updateZxSkLimitPriceAdjustItem(@RequestBody(required = false) ZxSkLimitPriceAdjustItem zxSkLimitPriceAdjustItem) {
        return zxSkLimitPriceAdjustItemService.updateZxSkLimitPriceAdjustItem(zxSkLimitPriceAdjustItem);
    }

    @ApiOperation(value="删除物资限价调整明细", notes="删除物资限价调整明细")
    @ApiImplicitParam(name = "zxSkLimitPriceAdjustItemList", value = "物资限价调整明细List", dataType = "List<ZxSkLimitPriceAdjustItem>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxSkLimitPriceAdjustItem")
    public ResponseEntity batchDeleteUpdateZxSkLimitPriceAdjustItem(@RequestBody(required = false) List<ZxSkLimitPriceAdjustItem> zxSkLimitPriceAdjustItemList) {
        return zxSkLimitPriceAdjustItemService.batchDeleteUpdateZxSkLimitPriceAdjustItem(zxSkLimitPriceAdjustItemList);
    }

}
