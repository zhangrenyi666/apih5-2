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
import com.apih5.mybatis.pojo.ZxSaProjectPaySettleItem;
import com.apih5.service.ZxSaProjectPaySettleItemService;

@RestController
public class ZxSaProjectPaySettleItemController {

    @Autowired(required = true)
    private ZxSaProjectPaySettleItemService zxSaProjectPaySettleItemService;

    @ApiOperation(value="查询结算管理-工程类结算-工程类结算单支付项子表(原表iesa_ProjectPaySettleItem)", notes="查询结算管理-工程类结算-工程类结算单支付项子表(原表iesa_ProjectPaySettleItem)")
    @ApiImplicitParam(name = "zxSaProjectPaySettleItem", value = "结算管理-工程类结算-工程类结算单支付项子表(原表iesa_ProjectPaySettleItem)entity", dataType = "ZxSaProjectPaySettleItem")
    @RequireToken
    @PostMapping("/getZxSaProjectPaySettleItemList")
    public ResponseEntity getZxSaProjectPaySettleItemList(@RequestBody(required = false) ZxSaProjectPaySettleItem zxSaProjectPaySettleItem) {
        return zxSaProjectPaySettleItemService.getZxSaProjectPaySettleItemListByCondition(zxSaProjectPaySettleItem);
    }

    @ApiOperation(value="查询详情结算管理-工程类结算-工程类结算单支付项子表(原表iesa_ProjectPaySettleItem)", notes="查询详情结算管理-工程类结算-工程类结算单支付项子表(原表iesa_ProjectPaySettleItem)")
    @ApiImplicitParam(name = "zxSaProjectPaySettleItem", value = "结算管理-工程类结算-工程类结算单支付项子表(原表iesa_ProjectPaySettleItem)entity", dataType = "ZxSaProjectPaySettleItem")
    @RequireToken
    @PostMapping("/getZxSaProjectPaySettleItemDetail")
    public ResponseEntity getZxSaProjectPaySettleItemDetail(@RequestBody(required = false) ZxSaProjectPaySettleItem zxSaProjectPaySettleItem) {
        return zxSaProjectPaySettleItemService.getZxSaProjectPaySettleItemDetail(zxSaProjectPaySettleItem);
    }

    @ApiOperation(value="新增结算管理-工程类结算-工程类结算单支付项子表(原表iesa_ProjectPaySettleItem)", notes="新增结算管理-工程类结算-工程类结算单支付项子表(原表iesa_ProjectPaySettleItem)")
    @ApiImplicitParam(name = "zxSaProjectPaySettleItem", value = "结算管理-工程类结算-工程类结算单支付项子表(原表iesa_ProjectPaySettleItem)entity", dataType = "ZxSaProjectPaySettleItem")
    @RequireToken
    @PostMapping("/addZxSaProjectPaySettleItem")
    public ResponseEntity addZxSaProjectPaySettleItem(@RequestBody(required = false) ZxSaProjectPaySettleItem zxSaProjectPaySettleItem) {
        return zxSaProjectPaySettleItemService.saveZxSaProjectPaySettleItem(zxSaProjectPaySettleItem);
    }

    @ApiOperation(value="更新结算管理-工程类结算-工程类结算单支付项子表(原表iesa_ProjectPaySettleItem)", notes="更新结算管理-工程类结算-工程类结算单支付项子表(原表iesa_ProjectPaySettleItem)")
    @ApiImplicitParam(name = "zxSaProjectPaySettleItem", value = "结算管理-工程类结算-工程类结算单支付项子表(原表iesa_ProjectPaySettleItem)entity", dataType = "ZxSaProjectPaySettleItem")
    @RequireToken
    @PostMapping("/updateZxSaProjectPaySettleItem")
    public ResponseEntity updateZxSaProjectPaySettleItem(@RequestBody(required = false) ZxSaProjectPaySettleItem zxSaProjectPaySettleItem) {
        return zxSaProjectPaySettleItemService.updateZxSaProjectPaySettleItem(zxSaProjectPaySettleItem);
    }

    @ApiOperation(value="删除结算管理-工程类结算-工程类结算单支付项子表(原表iesa_ProjectPaySettleItem)", notes="删除结算管理-工程类结算-工程类结算单支付项子表(原表iesa_ProjectPaySettleItem)")
    @ApiImplicitParam(name = "zxSaProjectPaySettleItemList", value = "结算管理-工程类结算-工程类结算单支付项子表(原表iesa_ProjectPaySettleItem)List", dataType = "List<ZxSaProjectPaySettleItem>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxSaProjectPaySettleItem")
    public ResponseEntity batchDeleteUpdateZxSaProjectPaySettleItem(@RequestBody(required = false) List<ZxSaProjectPaySettleItem> zxSaProjectPaySettleItemList) {
        return zxSaProjectPaySettleItemService.batchDeleteUpdateZxSaProjectPaySettleItem(zxSaProjectPaySettleItemList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    @ApiOperation(value="删除支付项子表", notes="删除支付项子表")
    @ApiImplicitParam(name = "zxSaProjectPaySettleItemList", value = "结算管理-工程类结算-工程类结算单支付项子表entity", dataType = "ZxSaProjectPaySettleItem")
    @RequireToken
    @PostMapping("/deleteAllZxSaProjectPaySettleItem")
    public ResponseEntity deleteAllZxSaProjectPaySettleItem(@RequestBody(required = false) ZxSaProjectPaySettleItem zxSaProjectPaySettleItem) {
        return zxSaProjectPaySettleItemService.deleteAllZxSaProjectPaySettleItem(zxSaProjectPaySettleItem);
    }
}
