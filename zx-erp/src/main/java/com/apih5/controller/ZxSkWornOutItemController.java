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
import com.apih5.mybatis.pojo.ZxSkWornOutItem;
import com.apih5.service.ZxSkWornOutItemService;

@RestController
public class ZxSkWornOutItemController {

    @Autowired(required = true)
    private ZxSkWornOutItemService zxSkWornOutItemService;

    @ApiOperation(value="查询废旧物资管理明细", notes="查询废旧物资管理明细")
    @ApiImplicitParam(name = "zxSkWornOutItem", value = "废旧物资管理明细entity", dataType = "ZxSkWornOutItem")
    @RequireToken
    @PostMapping("/getZxSkWornOutItemList")
    public ResponseEntity getZxSkWornOutItemList(@RequestBody(required = false) ZxSkWornOutItem zxSkWornOutItem) {
        return zxSkWornOutItemService.getZxSkWornOutItemListByCondition(zxSkWornOutItem);
    }

    @ApiOperation(value="查询详情废旧物资管理明细", notes="查询详情废旧物资管理明细")
    @ApiImplicitParam(name = "zxSkWornOutItem", value = "废旧物资管理明细entity", dataType = "ZxSkWornOutItem")
    @RequireToken
    @PostMapping("/getZxSkWornOutItemDetail")
    public ResponseEntity getZxSkWornOutItemDetail(@RequestBody(required = false) ZxSkWornOutItem zxSkWornOutItem) {
        return zxSkWornOutItemService.getZxSkWornOutItemDetail(zxSkWornOutItem);
    }

    @ApiOperation(value="新增废旧物资管理明细", notes="新增废旧物资管理明细")
    @ApiImplicitParam(name = "zxSkWornOutItem", value = "废旧物资管理明细entity", dataType = "ZxSkWornOutItem")
    @RequireToken
    @PostMapping("/addZxSkWornOutItem")
    public ResponseEntity addZxSkWornOutItem(@RequestBody(required = false) ZxSkWornOutItem zxSkWornOutItem) {
        return zxSkWornOutItemService.saveZxSkWornOutItem(zxSkWornOutItem);
    }

    @ApiOperation(value="更新废旧物资管理明细", notes="更新废旧物资管理明细")
    @ApiImplicitParam(name = "zxSkWornOutItem", value = "废旧物资管理明细entity", dataType = "ZxSkWornOutItem")
    @RequireToken
    @PostMapping("/updateZxSkWornOutItem")
    public ResponseEntity updateZxSkWornOutItem(@RequestBody(required = false) ZxSkWornOutItem zxSkWornOutItem) {
        return zxSkWornOutItemService.updateZxSkWornOutItem(zxSkWornOutItem);
    }

    @ApiOperation(value="删除废旧物资管理明细", notes="删除废旧物资管理明细")
    @ApiImplicitParam(name = "zxSkWornOutItemList", value = "废旧物资管理明细List", dataType = "List<ZxSkWornOutItem>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxSkWornOutItem")
    public ResponseEntity batchDeleteUpdateZxSkWornOutItem(@RequestBody(required = false) List<ZxSkWornOutItem> zxSkWornOutItemList) {
        return zxSkWornOutItemService.batchDeleteUpdateZxSkWornOutItem(zxSkWornOutItemList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
