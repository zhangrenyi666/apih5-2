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
import com.apih5.mybatis.pojo.ZxSkShopGoodsItem;
import com.apih5.service.ZxSkShopGoodsItemService;

@RestController
public class ZxSkShopGoodsItemController {

    @Autowired(required = true)
    private ZxSkShopGoodsItemService zxSkShopGoodsItemService;

    @ApiOperation(value="查询进货台账明细", notes="查询进货台账明细")
    @ApiImplicitParam(name = "zxSkShopGoodsItem", value = "进货台账明细entity", dataType = "ZxSkShopGoodsItem")
    @RequireToken
    @PostMapping("/getZxSkShopGoodsItemList")
    public ResponseEntity getZxSkShopGoodsItemList(@RequestBody(required = false) ZxSkShopGoodsItem zxSkShopGoodsItem) {
        return zxSkShopGoodsItemService.getZxSkShopGoodsItemListByCondition(zxSkShopGoodsItem);
    }

    @ApiOperation(value="查询详情进货台账明细", notes="查询详情进货台账明细")
    @ApiImplicitParam(name = "zxSkShopGoodsItem", value = "进货台账明细entity", dataType = "ZxSkShopGoodsItem")
    @RequireToken
    @PostMapping("/getZxSkShopGoodsItemDetail")
    public ResponseEntity getZxSkShopGoodsItemDetail(@RequestBody(required = false) ZxSkShopGoodsItem zxSkShopGoodsItem) {
        return zxSkShopGoodsItemService.getZxSkShopGoodsItemDetail(zxSkShopGoodsItem);
    }

    @ApiOperation(value="新增进货台账明细", notes="新增进货台账明细")
    @ApiImplicitParam(name = "zxSkShopGoodsItem", value = "进货台账明细entity", dataType = "ZxSkShopGoodsItem")
    @RequireToken
    @PostMapping("/addZxSkShopGoodsItem")
    public ResponseEntity addZxSkShopGoodsItem(@RequestBody(required = false) ZxSkShopGoodsItem zxSkShopGoodsItem) {
        return zxSkShopGoodsItemService.saveZxSkShopGoodsItem(zxSkShopGoodsItem);
    }

    @ApiOperation(value="更新进货台账明细", notes="更新进货台账明细")
    @ApiImplicitParam(name = "zxSkShopGoodsItem", value = "进货台账明细entity", dataType = "ZxSkShopGoodsItem")
    @RequireToken
    @PostMapping("/updateZxSkShopGoodsItem")
    public ResponseEntity updateZxSkShopGoodsItem(@RequestBody(required = false) ZxSkShopGoodsItem zxSkShopGoodsItem) {
        return zxSkShopGoodsItemService.updateZxSkShopGoodsItem(zxSkShopGoodsItem);
    }

    @ApiOperation(value="删除进货台账明细", notes="删除进货台账明细")
    @ApiImplicitParam(name = "zxSkShopGoodsItemList", value = "进货台账明细List", dataType = "List<ZxSkShopGoodsItem>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxSkShopGoodsItem")
    public ResponseEntity batchDeleteUpdateZxSkShopGoodsItem(@RequestBody(required = false) List<ZxSkShopGoodsItem> zxSkShopGoodsItemList) {
        return zxSkShopGoodsItemService.batchDeleteUpdateZxSkShopGoodsItem(zxSkShopGoodsItemList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
