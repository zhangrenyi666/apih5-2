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
import com.apih5.mybatis.pojo.ZxGcsgCtPriceSysItem;
import com.apih5.service.ZxGcsgCtPriceSysItemService;

@RestController
public class ZxGcsgCtPriceSysItemController {

    @Autowired(required = true)
    private ZxGcsgCtPriceSysItemService zxGcsgCtPriceSysItemService;

    @ApiOperation(value="查询合同单价分析明细表", notes="查询合同单价分析明细表")
    @ApiImplicitParam(name = "zxGcsgCtPriceSysItem", value = "合同单价分析明细表entity", dataType = "ZxGcsgCtPriceSysItem")
    @RequireToken
    @PostMapping("/getZxGcsgCtPriceSysItemList")
    public ResponseEntity getZxGcsgCtPriceSysItemList(@RequestBody(required = false) ZxGcsgCtPriceSysItem zxGcsgCtPriceSysItem) {
        return zxGcsgCtPriceSysItemService.getZxGcsgCtPriceSysItemListByCondition(zxGcsgCtPriceSysItem);
    }

    @ApiOperation(value="查询详情合同单价分析明细表", notes="查询详情合同单价分析明细表")
    @ApiImplicitParam(name = "zxGcsgCtPriceSysItem", value = "合同单价分析明细表entity", dataType = "ZxGcsgCtPriceSysItem")
    @RequireToken
    @PostMapping("/getZxGcsgCtPriceSysItemDetail")
    public ResponseEntity getZxGcsgCtPriceSysItemDetail(@RequestBody(required = false) ZxGcsgCtPriceSysItem zxGcsgCtPriceSysItem) {
        return zxGcsgCtPriceSysItemService.getZxGcsgCtPriceSysItemDetail(zxGcsgCtPriceSysItem);
    }

    @ApiOperation(value="新增合同单价分析明细表", notes="新增合同单价分析明细表")
    @ApiImplicitParam(name = "zxGcsgCtPriceSysItem", value = "合同单价分析明细表entity", dataType = "ZxGcsgCtPriceSysItem")
    @RequireToken
    @PostMapping("/addZxGcsgCtPriceSysItem")
    public ResponseEntity addZxGcsgCtPriceSysItem(@RequestBody(required = false) ZxGcsgCtPriceSysItem zxGcsgCtPriceSysItem) {
        return zxGcsgCtPriceSysItemService.saveZxGcsgCtPriceSysItem(zxGcsgCtPriceSysItem);
    }

    @ApiOperation(value="更新合同单价分析明细表", notes="更新合同单价分析明细表")
    @ApiImplicitParam(name = "zxGcsgCtPriceSysItem", value = "合同单价分析明细表entity", dataType = "ZxGcsgCtPriceSysItem")
    @RequireToken
    @PostMapping("/updateZxGcsgCtPriceSysItem")
    public ResponseEntity updateZxGcsgCtPriceSysItem(@RequestBody(required = false) ZxGcsgCtPriceSysItem zxGcsgCtPriceSysItem) {
        return zxGcsgCtPriceSysItemService.updateZxGcsgCtPriceSysItem(zxGcsgCtPriceSysItem);
    }

    @ApiOperation(value="删除合同单价分析明细表", notes="删除合同单价分析明细表")
    @ApiImplicitParam(name = "zxGcsgCtPriceSysItemList", value = "合同单价分析明细表List", dataType = "List<ZxGcsgCtPriceSysItem>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxGcsgCtPriceSysItem")
    public ResponseEntity batchDeleteUpdateZxGcsgCtPriceSysItem(@RequestBody(required = false) List<ZxGcsgCtPriceSysItem> zxGcsgCtPriceSysItemList) {
        return zxGcsgCtPriceSysItemService.batchDeleteUpdateZxGcsgCtPriceSysItem(zxGcsgCtPriceSysItemList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
