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
import com.apih5.mybatis.pojo.ZxSkTurnoverOutItem;
import com.apih5.service.ZxSkTurnoverOutItemService;

@RestController
public class ZxSkTurnoverOutItemController {

    @Autowired(required = true)
    private ZxSkTurnoverOutItemService zxSkTurnoverOutItemService;

    @ApiOperation(value="查询周转材料出库明细", notes="查询周转材料出库明细")
    @ApiImplicitParam(name = "zxSkTurnoverOutItem", value = "周转材料出库明细entity", dataType = "ZxSkTurnoverOutItem")
    @RequireToken
    @PostMapping("/getZxSkTurnoverOutItemList")
    public ResponseEntity getZxSkTurnoverOutItemList(@RequestBody(required = false) ZxSkTurnoverOutItem zxSkTurnoverOutItem) {
        return zxSkTurnoverOutItemService.getZxSkTurnoverOutItemListByCondition(zxSkTurnoverOutItem);
    }

    @ApiOperation(value="查询详情周转材料出库明细", notes="查询详情周转材料出库明细")
    @ApiImplicitParam(name = "zxSkTurnoverOutItem", value = "周转材料出库明细entity", dataType = "ZxSkTurnoverOutItem")
    @RequireToken
    @PostMapping("/getZxSkTurnoverOutItemDetail")
    public ResponseEntity getZxSkTurnoverOutItemDetail(@RequestBody(required = false) ZxSkTurnoverOutItem zxSkTurnoverOutItem) {
        return zxSkTurnoverOutItemService.getZxSkTurnoverOutItemDetail(zxSkTurnoverOutItem);
    }

    @ApiOperation(value="新增周转材料出库明细", notes="新增周转材料出库明细")
    @ApiImplicitParam(name = "zxSkTurnoverOutItem", value = "周转材料出库明细entity", dataType = "ZxSkTurnoverOutItem")
    @RequireToken
    @PostMapping("/addZxSkTurnoverOutItem")
    public ResponseEntity addZxSkTurnoverOutItem(@RequestBody(required = false) ZxSkTurnoverOutItem zxSkTurnoverOutItem) {
        return zxSkTurnoverOutItemService.saveZxSkTurnoverOutItem(zxSkTurnoverOutItem);
    }

    @ApiOperation(value="更新周转材料出库明细", notes="更新周转材料出库明细")
    @ApiImplicitParam(name = "zxSkTurnoverOutItem", value = "周转材料出库明细entity", dataType = "ZxSkTurnoverOutItem")
    @RequireToken
    @PostMapping("/updateZxSkTurnoverOutItem")
    public ResponseEntity updateZxSkTurnoverOutItem(@RequestBody(required = false) ZxSkTurnoverOutItem zxSkTurnoverOutItem) {
        return zxSkTurnoverOutItemService.updateZxSkTurnoverOutItem(zxSkTurnoverOutItem);
    }

    @ApiOperation(value="删除周转材料出库明细", notes="删除周转材料出库明细")
    @ApiImplicitParam(name = "zxSkTurnoverOutItemList", value = "周转材料出库明细List", dataType = "List<ZxSkTurnoverOutItem>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxSkTurnoverOutItem")
    public ResponseEntity batchDeleteUpdateZxSkTurnoverOutItem(@RequestBody(required = false) List<ZxSkTurnoverOutItem> zxSkTurnoverOutItemList) {
        return zxSkTurnoverOutItemService.batchDeleteUpdateZxSkTurnoverOutItem(zxSkTurnoverOutItemList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
