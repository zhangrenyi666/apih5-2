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
import com.apih5.mybatis.pojo.ZxSaProjectSetItem;
import com.apih5.service.ZxSaProjectSetItemService;

@RestController
public class ZxSaProjectSetItemController {

    @Autowired(required = true)
    private ZxSaProjectSetItemService zxSaProjectSetItemService;

    @ApiOperation(value="查询结算支付基础明细", notes="查询结算支付基础明细")
    @ApiImplicitParam(name = "zxSaProjectSetItem", value = "结算支付基础明细entity", dataType = "ZxSaProjectSetItem")
    @RequireToken
    @PostMapping("/getZxSaProjectSetItemList")
    public ResponseEntity getZxSaProjectSetItemList(@RequestBody(required = false) ZxSaProjectSetItem zxSaProjectSetItem) {
        return zxSaProjectSetItemService.getZxSaProjectSetItemListByCondition(zxSaProjectSetItem);
    }

    @ApiOperation(value="查询详情结算支付基础明细", notes="查询详情结算支付基础明细")
    @ApiImplicitParam(name = "zxSaProjectSetItem", value = "结算支付基础明细entity", dataType = "ZxSaProjectSetItem")
    @RequireToken
    @PostMapping("/getZxSaProjectSetItemDetails")
    public ResponseEntity getZxSaProjectSetItemDetails(@RequestBody(required = false) ZxSaProjectSetItem zxSaProjectSetItem) {
        return zxSaProjectSetItemService.getZxSaProjectSetItemDetails(zxSaProjectSetItem);
    }

    @ApiOperation(value="新增结算支付基础明细", notes="新增结算支付基础明细")
    @ApiImplicitParam(name = "zxSaProjectSetItem", value = "结算支付基础明细entity", dataType = "ZxSaProjectSetItem")
    @RequireToken
    @PostMapping("/addZxSaProjectSetItem")
    public ResponseEntity addZxSaProjectSetItem(@RequestBody(required = false) ZxSaProjectSetItem zxSaProjectSetItem) {
        return zxSaProjectSetItemService.saveZxSaProjectSetItem(zxSaProjectSetItem);
    }

    @ApiOperation(value="更新结算支付基础明细", notes="更新结算支付基础明细")
    @ApiImplicitParam(name = "zxSaProjectSetItem", value = "结算支付基础明细entity", dataType = "ZxSaProjectSetItem")
    @RequireToken
    @PostMapping("/updateZxSaProjectSetItem")
    public ResponseEntity updateZxSaProjectSetItem(@RequestBody(required = false) ZxSaProjectSetItem zxSaProjectSetItem) {
        return zxSaProjectSetItemService.updateZxSaProjectSetItem(zxSaProjectSetItem);
    }

    @ApiOperation(value="删除结算支付基础明细", notes="删除结算支付基础明细")
    @ApiImplicitParam(name = "zxSaProjectSetItemList", value = "结算支付基础明细List", dataType = "List<ZxSaProjectSetItem>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxSaProjectSetItem")
    public ResponseEntity batchDeleteUpdateZxSaProjectSetItem(@RequestBody(required = false) List<ZxSaProjectSetItem> zxSaProjectSetItemList) {
        return zxSaProjectSetItemService.batchDeleteUpdateZxSaProjectSetItem(zxSaProjectSetItemList);
    }

}
