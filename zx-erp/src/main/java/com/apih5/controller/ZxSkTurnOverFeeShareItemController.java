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
import com.apih5.mybatis.pojo.ZxSkTurnOverFeeShareItem;
import com.apih5.service.ZxSkTurnOverFeeShareItemService;

@RestController
public class ZxSkTurnOverFeeShareItemController {

    @Autowired(required = true)
    private ZxSkTurnOverFeeShareItemService zxSkTurnOverFeeShareItemService;

    @ApiOperation(value="查询周转材料摊销明细", notes="查询周转材料摊销明细")
    @ApiImplicitParam(name = "zxSkTurnOverFeeShareItem", value = "周转材料摊销明细entity", dataType = "ZxSkTurnOverFeeShareItem")
    @RequireToken
    @PostMapping("/getZxSkTurnOverFeeShareItemList")
    public ResponseEntity getZxSkTurnOverFeeShareItemList(@RequestBody(required = false) ZxSkTurnOverFeeShareItem zxSkTurnOverFeeShareItem) {
        return zxSkTurnOverFeeShareItemService.getZxSkTurnOverFeeShareItemListByCondition(zxSkTurnOverFeeShareItem);
    }

    @ApiOperation(value="查询详情周转材料摊销明细", notes="查询详情周转材料摊销明细")
    @ApiImplicitParam(name = "zxSkTurnOverFeeShareItem", value = "周转材料摊销明细entity", dataType = "ZxSkTurnOverFeeShareItem")
    @RequireToken
    @PostMapping("/getZxSkTurnOverFeeShareItemDetail")
    public ResponseEntity getZxSkTurnOverFeeShareItemDetail(@RequestBody(required = false) ZxSkTurnOverFeeShareItem zxSkTurnOverFeeShareItem) {
        return zxSkTurnOverFeeShareItemService.getZxSkTurnOverFeeShareItemDetail(zxSkTurnOverFeeShareItem);
    }

    @ApiOperation(value="新增周转材料摊销明细", notes="新增周转材料摊销明细")
    @ApiImplicitParam(name = "zxSkTurnOverFeeShareItem", value = "周转材料摊销明细entity", dataType = "ZxSkTurnOverFeeShareItem")
    @RequireToken
    @PostMapping("/addZxSkTurnOverFeeShareItem")
    public ResponseEntity addZxSkTurnOverFeeShareItem(@RequestBody(required = false) ZxSkTurnOverFeeShareItem zxSkTurnOverFeeShareItem) {
        return zxSkTurnOverFeeShareItemService.saveZxSkTurnOverFeeShareItem(zxSkTurnOverFeeShareItem);
    }

    @ApiOperation(value="更新周转材料摊销明细", notes="更新周转材料摊销明细")
    @ApiImplicitParam(name = "zxSkTurnOverFeeShareItem", value = "周转材料摊销明细entity", dataType = "ZxSkTurnOverFeeShareItem")
    @RequireToken
    @PostMapping("/updateZxSkTurnOverFeeShareItem")
    public ResponseEntity updateZxSkTurnOverFeeShareItem(@RequestBody(required = false) ZxSkTurnOverFeeShareItem zxSkTurnOverFeeShareItem) {
        return zxSkTurnOverFeeShareItemService.updateZxSkTurnOverFeeShareItem(zxSkTurnOverFeeShareItem);
    }

    @ApiOperation(value="删除周转材料摊销明细", notes="删除周转材料摊销明细")
    @ApiImplicitParam(name = "zxSkTurnOverFeeShareItemList", value = "周转材料摊销明细List", dataType = "List<ZxSkTurnOverFeeShareItem>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxSkTurnOverFeeShareItem")
    public ResponseEntity batchDeleteUpdateZxSkTurnOverFeeShareItem(@RequestBody(required = false) List<ZxSkTurnOverFeeShareItem> zxSkTurnOverFeeShareItemList) {
        return zxSkTurnOverFeeShareItemService.batchDeleteUpdateZxSkTurnOverFeeShareItem(zxSkTurnOverFeeShareItemList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
