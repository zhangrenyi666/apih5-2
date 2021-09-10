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
import com.apih5.mybatis.pojo.ZxSkTurnOverTransferItem;
import com.apih5.service.ZxSkTurnOverTransferItemService;

@RestController
public class ZxSkTurnOverTransferItemController {

    @Autowired(required = true)
    private ZxSkTurnOverTransferItemService zxSkTurnOverTransferItemService;

    @ApiOperation(value="查询周转材料调拨明细", notes="查询周转材料调拨明细")
    @ApiImplicitParam(name = "zxSkTurnOverTransferItem", value = "周转材料调拨明细entity", dataType = "ZxSkTurnOverTransferItem")
    @RequireToken
    @PostMapping("/getZxSkTurnOverTransferItemList")
    public ResponseEntity getZxSkTurnOverTransferItemList(@RequestBody(required = false) ZxSkTurnOverTransferItem zxSkTurnOverTransferItem) {
        return zxSkTurnOverTransferItemService.getZxSkTurnOverTransferItemListByCondition(zxSkTurnOverTransferItem);
    }

    @ApiOperation(value="查询详情周转材料调拨明细", notes="查询详情周转材料调拨明细")
    @ApiImplicitParam(name = "zxSkTurnOverTransferItem", value = "周转材料调拨明细entity", dataType = "ZxSkTurnOverTransferItem")
    @RequireToken
    @PostMapping("/getZxSkTurnOverTransferItemDetail")
    public ResponseEntity getZxSkTurnOverTransferItemDetail(@RequestBody(required = false) ZxSkTurnOverTransferItem zxSkTurnOverTransferItem) {
        return zxSkTurnOverTransferItemService.getZxSkTurnOverTransferItemDetail(zxSkTurnOverTransferItem);
    }

    @ApiOperation(value="新增周转材料调拨明细", notes="新增周转材料调拨明细")
    @ApiImplicitParam(name = "zxSkTurnOverTransferItem", value = "周转材料调拨明细entity", dataType = "ZxSkTurnOverTransferItem")
    @RequireToken
    @PostMapping("/addZxSkTurnOverTransferItem")
    public ResponseEntity addZxSkTurnOverTransferItem(@RequestBody(required = false) ZxSkTurnOverTransferItem zxSkTurnOverTransferItem) {
        return zxSkTurnOverTransferItemService.saveZxSkTurnOverTransferItem(zxSkTurnOverTransferItem);
    }

    @ApiOperation(value="更新周转材料调拨明细", notes="更新周转材料调拨明细")
    @ApiImplicitParam(name = "zxSkTurnOverTransferItem", value = "周转材料调拨明细entity", dataType = "ZxSkTurnOverTransferItem")
    @RequireToken
    @PostMapping("/updateZxSkTurnOverTransferItem")
    public ResponseEntity updateZxSkTurnOverTransferItem(@RequestBody(required = false) ZxSkTurnOverTransferItem zxSkTurnOverTransferItem) {
        return zxSkTurnOverTransferItemService.updateZxSkTurnOverTransferItem(zxSkTurnOverTransferItem);
    }

    @ApiOperation(value="删除周转材料调拨明细", notes="删除周转材料调拨明细")
    @ApiImplicitParam(name = "zxSkTurnOverTransferItemList", value = "周转材料调拨明细List", dataType = "List<ZxSkTurnOverTransferItem>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxSkTurnOverTransferItem")
    public ResponseEntity batchDeleteUpdateZxSkTurnOverTransferItem(@RequestBody(required = false) List<ZxSkTurnOverTransferItem> zxSkTurnOverTransferItemList) {
        return zxSkTurnOverTransferItemService.batchDeleteUpdateZxSkTurnOverTransferItem(zxSkTurnOverTransferItemList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
