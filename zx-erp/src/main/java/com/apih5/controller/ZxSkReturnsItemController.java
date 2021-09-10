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
import com.apih5.mybatis.pojo.ZxSkReturnsItem;
import com.apih5.service.ZxSkReturnsItemService;

@RestController
public class ZxSkReturnsItemController {

    @Autowired(required = true)
    private ZxSkReturnsItemService zxSkReturnsItemService;

    @ApiOperation(value="查询周转材料退货明细", notes="查询周转材料退货明细")
    @ApiImplicitParam(name = "zxSkReturnsItem", value = "周转材料退货明细entity", dataType = "ZxSkReturnsItem")
    @RequireToken
    @PostMapping("/getZxSkReturnsItemList")
    public ResponseEntity getZxSkReturnsItemList(@RequestBody(required = false) ZxSkReturnsItem zxSkReturnsItem) {
        return zxSkReturnsItemService.getZxSkReturnsItemListByCondition(zxSkReturnsItem);
    }

    @ApiOperation(value="查询详情周转材料退货明细", notes="查询详情周转材料退货明细")
    @ApiImplicitParam(name = "zxSkReturnsItem", value = "周转材料退货明细entity", dataType = "ZxSkReturnsItem")
    @RequireToken
    @PostMapping("/getZxSkReturnsItemDetail")
    public ResponseEntity getZxSkReturnsItemDetail(@RequestBody(required = false) ZxSkReturnsItem zxSkReturnsItem) {
        return zxSkReturnsItemService.getZxSkReturnsItemDetail(zxSkReturnsItem);
    }

    @ApiOperation(value="新增周转材料退货明细", notes="新增周转材料退货明细")
    @ApiImplicitParam(name = "zxSkReturnsItem", value = "周转材料退货明细entity", dataType = "ZxSkReturnsItem")
    @RequireToken
    @PostMapping("/addZxSkReturnsItem")
    public ResponseEntity addZxSkReturnsItem(@RequestBody(required = false) ZxSkReturnsItem zxSkReturnsItem) {
        return zxSkReturnsItemService.saveZxSkReturnsItem(zxSkReturnsItem);
    }

    @ApiOperation(value="更新周转材料退货明细", notes="更新周转材料退货明细")
    @ApiImplicitParam(name = "zxSkReturnsItem", value = "周转材料退货明细entity", dataType = "ZxSkReturnsItem")
    @RequireToken
    @PostMapping("/updateZxSkReturnsItem")
    public ResponseEntity updateZxSkReturnsItem(@RequestBody(required = false) ZxSkReturnsItem zxSkReturnsItem) {
        return zxSkReturnsItemService.updateZxSkReturnsItem(zxSkReturnsItem);
    }

    @ApiOperation(value="删除周转材料退货明细", notes="删除周转材料退货明细")
    @ApiImplicitParam(name = "zxSkReturnsItemList", value = "周转材料退货明细List", dataType = "List<ZxSkReturnsItem>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxSkReturnsItem")
    public ResponseEntity batchDeleteUpdateZxSkReturnsItem(@RequestBody(required = false) List<ZxSkReturnsItem> zxSkReturnsItemList) {
        return zxSkReturnsItemService.batchDeleteUpdateZxSkReturnsItem(zxSkReturnsItemList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
