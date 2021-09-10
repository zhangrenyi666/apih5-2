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
import com.apih5.mybatis.pojo.ZxSkMonthPurItem;
import com.apih5.service.ZxSkMonthPurItemService;

@RestController
public class ZxSkMonthPurItemController {

    @Autowired(required = true)
    private ZxSkMonthPurItemService zxSkMonthPurItemService;

    @ApiOperation(value="查询月采购计划明细", notes="查询月采购计划明细")
    @ApiImplicitParam(name = "zxSkMonthPurItem", value = "月采购计划明细entity", dataType = "ZxSkMonthPurItem")
    @RequireToken
    @PostMapping("/getZxSkMonthPurItemList")
    public ResponseEntity getZxSkMonthPurItemList(@RequestBody(required = false) ZxSkMonthPurItem zxSkMonthPurItem) {
        return zxSkMonthPurItemService.getZxSkMonthPurItemListByCondition(zxSkMonthPurItem);
    }

    @ApiOperation(value="查询详情月采购计划明细", notes="查询详情月采购计划明细")
    @ApiImplicitParam(name = "zxSkMonthPurItem", value = "月采购计划明细entity", dataType = "ZxSkMonthPurItem")
    @RequireToken
    @PostMapping("/getZxSkMonthPurItemDetails")
    public ResponseEntity getZxSkMonthPurItemDetails(@RequestBody(required = false) ZxSkMonthPurItem zxSkMonthPurItem) {
        return zxSkMonthPurItemService.getZxSkMonthPurItemDetails(zxSkMonthPurItem);
    }

    @ApiOperation(value="新增月采购计划明细", notes="新增月采购计划明细")
    @ApiImplicitParam(name = "zxSkMonthPurItem", value = "月采购计划明细entity", dataType = "ZxSkMonthPurItem")
    @RequireToken
    @PostMapping("/addZxSkMonthPurItem")
    public ResponseEntity addZxSkMonthPurItem(@RequestBody(required = false) ZxSkMonthPurItem zxSkMonthPurItem) {
        return zxSkMonthPurItemService.saveZxSkMonthPurItem(zxSkMonthPurItem);
    }

    @ApiOperation(value="更新月采购计划明细", notes="更新月采购计划明细")
    @ApiImplicitParam(name = "zxSkMonthPurItem", value = "月采购计划明细entity", dataType = "ZxSkMonthPurItem")
    @RequireToken
    @PostMapping("/updateZxSkMonthPurItem")
    public ResponseEntity updateZxSkMonthPurItem(@RequestBody(required = false) ZxSkMonthPurItem zxSkMonthPurItem) {
        return zxSkMonthPurItemService.updateZxSkMonthPurItem(zxSkMonthPurItem);
    }

    @ApiOperation(value="删除月采购计划明细", notes="删除月采购计划明细")
    @ApiImplicitParam(name = "zxSkMonthPurItemList", value = "月采购计划明细List", dataType = "List<ZxSkMonthPurItem>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxSkMonthPurItem")
    public ResponseEntity batchDeleteUpdateZxSkMonthPurItem(@RequestBody(required = false) List<ZxSkMonthPurItem> zxSkMonthPurItemList) {
        return zxSkMonthPurItemService.batchDeleteUpdateZxSkMonthPurItem(zxSkMonthPurItemList);
    }

}
