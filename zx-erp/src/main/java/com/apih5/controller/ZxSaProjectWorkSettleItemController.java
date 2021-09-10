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
import com.apih5.mybatis.pojo.ZxSaProjectWorkSettleItem;
import com.apih5.service.ZxSaProjectWorkSettleItemService;

@RestController
public class ZxSaProjectWorkSettleItemController {

    @Autowired(required = true)
    private ZxSaProjectWorkSettleItemService zxSaProjectWorkSettleItemService;

    @ApiOperation(value="查询结算管理-工程类结算-工程类结算单结算清单子表(原表iesa_ProjectWorkSettleItem)", notes="查询结算管理-工程类结算-工程类结算单结算清单子表(原表iesa_ProjectWorkSettleItem)")
    @ApiImplicitParam(name = "zxSaProjectWorkSettleItem", value = "结算管理-工程类结算-工程类结算单结算清单子表(原表iesa_ProjectWorkSettleItem)entity", dataType = "ZxSaProjectWorkSettleItem")
    @RequireToken
    @PostMapping("/getZxSaProjectWorkSettleItemList")
    public ResponseEntity getZxSaProjectWorkSettleItemList(@RequestBody(required = false) ZxSaProjectWorkSettleItem zxSaProjectWorkSettleItem) {
        return zxSaProjectWorkSettleItemService.getZxSaProjectWorkSettleItemListByCondition(zxSaProjectWorkSettleItem);
    }

    @ApiOperation(value="查询详情结算管理-工程类结算-工程类结算单结算清单子表(原表iesa_ProjectWorkSettleItem)", notes="查询详情结算管理-工程类结算-工程类结算单结算清单子表(原表iesa_ProjectWorkSettleItem)")
    @ApiImplicitParam(name = "zxSaProjectWorkSettleItem", value = "结算管理-工程类结算-工程类结算单结算清单子表(原表iesa_ProjectWorkSettleItem)entity", dataType = "ZxSaProjectWorkSettleItem")
    @RequireToken
    @PostMapping("/getZxSaProjectWorkSettleItemDetail")
    public ResponseEntity getZxSaProjectWorkSettleItemDetail(@RequestBody(required = false) ZxSaProjectWorkSettleItem zxSaProjectWorkSettleItem) {
        return zxSaProjectWorkSettleItemService.getZxSaProjectWorkSettleItemDetail(zxSaProjectWorkSettleItem);
    }

    @ApiOperation(value="新增结算管理-工程类结算-工程类结算单结算清单子表(原表iesa_ProjectWorkSettleItem)", notes="新增结算管理-工程类结算-工程类结算单结算清单子表(原表iesa_ProjectWorkSettleItem)")
    @ApiImplicitParam(name = "zxSaProjectWorkSettleItem", value = "结算管理-工程类结算-工程类结算单结算清单子表(原表iesa_ProjectWorkSettleItem)entity", dataType = "ZxSaProjectWorkSettleItem")
    @RequireToken
    @PostMapping("/addZxSaProjectWorkSettleItem")
    public ResponseEntity addZxSaProjectWorkSettleItem(@RequestBody(required = false) ZxSaProjectWorkSettleItem zxSaProjectWorkSettleItem) {
        return zxSaProjectWorkSettleItemService.saveZxSaProjectWorkSettleItem(zxSaProjectWorkSettleItem);
    }

    @ApiOperation(value="更新结算管理-工程类结算-工程类结算单结算清单子表(原表iesa_ProjectWorkSettleItem)", notes="更新结算管理-工程类结算-工程类结算单结算清单子表(原表iesa_ProjectWorkSettleItem)")
    @ApiImplicitParam(name = "zxSaProjectWorkSettleItem", value = "结算管理-工程类结算-工程类结算单结算清单子表(原表iesa_ProjectWorkSettleItem)entity", dataType = "ZxSaProjectWorkSettleItem")
    @RequireToken
    @PostMapping("/updateZxSaProjectWorkSettleItem")
    public ResponseEntity updateZxSaProjectWorkSettleItem(@RequestBody(required = false) ZxSaProjectWorkSettleItem zxSaProjectWorkSettleItem) {
        return zxSaProjectWorkSettleItemService.updateZxSaProjectWorkSettleItem(zxSaProjectWorkSettleItem);
    }

    @ApiOperation(value="删除结算管理-工程类结算-工程类结算单结算清单子表(原表iesa_ProjectWorkSettleItem)", notes="删除结算管理-工程类结算-工程类结算单结算清单子表(原表iesa_ProjectWorkSettleItem)")
    @ApiImplicitParam(name = "zxSaProjectWorkSettleItemList", value = "结算管理-工程类结算-工程类结算单结算清单子表(原表iesa_ProjectWorkSettleItem)List", dataType = "List<ZxSaProjectWorkSettleItem>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxSaProjectWorkSettleItem")
    public ResponseEntity batchDeleteUpdateZxSaProjectWorkSettleItem(@RequestBody(required = false) List<ZxSaProjectWorkSettleItem> zxSaProjectWorkSettleItemList) {
        return zxSaProjectWorkSettleItemService.batchDeleteUpdateZxSaProjectWorkSettleItem(zxSaProjectWorkSettleItemList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    @ApiOperation(value="删除结算清单子表", notes="删除结算清单子表")
    @ApiImplicitParam(name = "zxSaProjectWorkSettleItemList", value = "结算管理-工程类结算-工程类结算单结算清单子表(原表iesa_ProjectWorkSettleItem)List", dataType = "List<ZxSaProjectWorkSettleItem>")
    @RequireToken
    @PostMapping("/deleteAllZxSaProjectWorkSettleItem")
    public ResponseEntity deleteAllZxSaProjectWorkSettleItem(@RequestBody(required = false) ZxSaProjectWorkSettleItem zxSaProjectWorkSettleItem) {
        return zxSaProjectWorkSettleItemService.deleteAllZxSaProjectWorkSettleItem(zxSaProjectWorkSettleItem);
    }
    
    @ApiOperation(value="工程类结算单营改增一览", notes="工程类结算单营改增一览")
    @ApiImplicitParam(name = "zxSaProjectWorkSettleItem", value = "工程类结算单结算清单子表", dataType = "ZxSaProjectWorkSettleItem")
    @RequireToken
    @PostMapping("/getZxSaProjectSettleAuditYgzInfo")
    public ResponseEntity getZxSaProjectSettleAuditYgzInfo(@RequestBody(required = false) ZxSaProjectWorkSettleItem zxSaProjectWorkSettleItem) {
    	return zxSaProjectWorkSettleItemService.getZxSaProjectSettleAuditYgzInfo(zxSaProjectWorkSettleItem);
    }
    
    @ApiOperation(value="获取工程类结算单营改增一览清单、支付项信息", notes="获取工程类结算单营改增一览清单、支付项信息")
    @ApiImplicitParam(name = "zxSaProjectWorkSettleItem", value = "工程类结算单结算清单子表", dataType = "ZxSaProjectWorkSettleItem")
    @PostMapping("/getZxSaProjectSettleAuditYgzList")
    public List<ZxSaProjectWorkSettleItem> getZxSaProjectSettleAuditYgzList(@RequestBody(required = false) ZxSaProjectWorkSettleItem zxSaProjectWorkSettleItem) {
    	return zxSaProjectWorkSettleItemService.getZxSaProjectSettleAuditYgzList(zxSaProjectWorkSettleItem);
    }
    
    @ApiOperation(value="导出工程类结算单营改增一览", notes="导出工程类结算单营改增一览")
    @ApiImplicitParam(name = "zxSaProjectWorkSettleItem", value = "工程类结算单结算清单子表", dataType = "ZxSaProjectWorkSettleItem")
    @RequireToken
    @PostMapping("/exportZxSaProjectSettleAuditYgzInfo")
    public ResponseEntity exportZxSaProjectSettleAuditYgzInfo(@RequestBody(required = false) ZxSaProjectWorkSettleItem zxSaProjectWorkSettleItem) {
    	return zxSaProjectWorkSettleItemService.exportZxSaProjectSettleAuditYgzInfo(zxSaProjectWorkSettleItem);
    }
    
    @ApiOperation(value="导出工程类结算单结算表一览", notes="导出工程类结算单营改增一览")
    @ApiImplicitParam(name = "zxSaProjectWorkSettleItem", value = "工程类结算单结算清单子表", dataType = "ZxSaProjectWorkSettleItem")
    @RequireToken
    @PostMapping("/exportZxSaProjectSettleAuditGcjsjsb")
    public ResponseEntity exportZxSaProjectSettleAuditGcjsjsb(@RequestBody(required = false) ZxSaProjectWorkSettleItem zxSaProjectWorkSettleItem) {
    	return zxSaProjectWorkSettleItemService.exportZxSaProjectSettleAuditGcjsjsb(zxSaProjectWorkSettleItem);
    }
    
    @ApiOperation(value="导出工程类结算单结算表打印版", notes="导出工程类结算单结算表打印版")
    @ApiImplicitParam(name = "zxSaProjectWorkSettleItem", value = "工程类结算单结算清单子表", dataType = "ZxSaProjectWorkSettleItem")
    @RequireToken
    @PostMapping("/exportZxSaProjectSettleAuditGcjsjsbDyb")
    public ResponseEntity exportZxSaProjectSettleAuditGcjsjsbDyb(@RequestBody(required = false) ZxSaProjectWorkSettleItem zxSaProjectWorkSettleItem) {
    	return zxSaProjectWorkSettleItemService.exportZxSaProjectSettleAuditGcjsjsbDyb(zxSaProjectWorkSettleItem);
    }
    
    @ApiOperation(value="导出工程类结算单结算单营改增一览", notes="导出工程类结算单结算单营改增一览")
    @ApiImplicitParam(name = "zxSaProjectWorkSettleItem", value = "工程类结算单结算清单子表", dataType = "ZxSaProjectWorkSettleItem")
    @RequireToken
    @PostMapping("/exportZxSaProjectSettleAuditGcjsjsdYgz")
    public ResponseEntity exportZxSaProjectSettleAuditGcjsjsdYgz(@RequestBody(required = false) ZxSaProjectWorkSettleItem zxSaProjectWorkSettleItem) {
    	return zxSaProjectWorkSettleItemService.exportZxSaProjectSettleAuditGcjsjsdYgz(zxSaProjectWorkSettleItem);
    }
}
