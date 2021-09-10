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
import com.apih5.mybatis.pojo.ZxSaProjectSettleAuditItem;
import com.apih5.service.ZxSaProjectSettleAuditItemService;

@RestController
public class ZxSaProjectSettleAuditItemController {

    @Autowired(required = true)
    private ZxSaProjectSettleAuditItemService zxSaProjectSettleAuditItemService;

    @ApiOperation(value="查询结算管理-工程类结算-工程类结算单明细(原表iesa_ProjectSettleAuditItem)", notes="查询结算管理-工程类结算-工程类结算单明细(原表iesa_ProjectSettleAuditItem)")
    @ApiImplicitParam(name = "zxSaProjectSettleAuditItem", value = "结算管理-工程类结算-工程类结算单明细(原表iesa_ProjectSettleAuditItem)entity", dataType = "ZxSaProjectSettleAuditItem")
    @RequireToken
    @PostMapping("/getZxSaProjectSettleAuditItemList")
    public ResponseEntity getZxSaProjectSettleAuditItemList(@RequestBody(required = false) ZxSaProjectSettleAuditItem zxSaProjectSettleAuditItem) {
        return zxSaProjectSettleAuditItemService.getZxSaProjectSettleAuditItemListByCondition(zxSaProjectSettleAuditItem);
    }

    @ApiOperation(value="查询详情结算管理-工程类结算-工程类结算单明细(原表iesa_ProjectSettleAuditItem)", notes="查询详情结算管理-工程类结算-工程类结算单明细(原表iesa_ProjectSettleAuditItem)")
    @ApiImplicitParam(name = "zxSaProjectSettleAuditItem", value = "结算管理-工程类结算-工程类结算单明细(原表iesa_ProjectSettleAuditItem)entity", dataType = "ZxSaProjectSettleAuditItem")
    @RequireToken
    @PostMapping("/getZxSaProjectSettleAuditItemDetail")
    public ResponseEntity getZxSaProjectSettleAuditItemDetail(@RequestBody(required = false) ZxSaProjectSettleAuditItem zxSaProjectSettleAuditItem) {
        return zxSaProjectSettleAuditItemService.getZxSaProjectSettleAuditItemDetail(zxSaProjectSettleAuditItem);
    }

    @ApiOperation(value="新增结算管理-工程类结算-工程类结算单明细(原表iesa_ProjectSettleAuditItem)", notes="新增结算管理-工程类结算-工程类结算单明细(原表iesa_ProjectSettleAuditItem)")
    @ApiImplicitParam(name = "zxSaProjectSettleAuditItem", value = "结算管理-工程类结算-工程类结算单明细(原表iesa_ProjectSettleAuditItem)entity", dataType = "ZxSaProjectSettleAuditItem")
    @RequireToken
    @PostMapping("/addZxSaProjectSettleAuditItem")
    public ResponseEntity addZxSaProjectSettleAuditItem(@RequestBody(required = false) ZxSaProjectSettleAuditItem zxSaProjectSettleAuditItem) {
        return zxSaProjectSettleAuditItemService.saveZxSaProjectSettleAuditItem(zxSaProjectSettleAuditItem);
    }

    @ApiOperation(value="更新结算管理-工程类结算-工程类结算单明细(原表iesa_ProjectSettleAuditItem)", notes="更新结算管理-工程类结算-工程类结算单明细(原表iesa_ProjectSettleAuditItem)")
    @ApiImplicitParam(name = "zxSaProjectSettleAuditItem", value = "结算管理-工程类结算-工程类结算单明细(原表iesa_ProjectSettleAuditItem)entity", dataType = "ZxSaProjectSettleAuditItem")
    @RequireToken
    @PostMapping("/updateZxSaProjectSettleAuditItem")
    public ResponseEntity updateZxSaProjectSettleAuditItem(@RequestBody(required = false) ZxSaProjectSettleAuditItem zxSaProjectSettleAuditItem) {
        return zxSaProjectSettleAuditItemService.updateZxSaProjectSettleAuditItem(zxSaProjectSettleAuditItem);
    }

    @ApiOperation(value="删除结算管理-工程类结算-工程类结算单明细(原表iesa_ProjectSettleAuditItem)", notes="删除结算管理-工程类结算-工程类结算单明细(原表iesa_ProjectSettleAuditItem)")
    @ApiImplicitParam(name = "zxSaProjectSettleAuditItemList", value = "结算管理-工程类结算-工程类结算单明细(原表iesa_ProjectSettleAuditItem)List", dataType = "List<ZxSaProjectSettleAuditItem>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxSaProjectSettleAuditItem")
    public ResponseEntity batchDeleteUpdateZxSaProjectSettleAuditItem(@RequestBody(required = false) List<ZxSaProjectSettleAuditItem> zxSaProjectSettleAuditItemList) {
        return zxSaProjectSettleAuditItemService.batchDeleteUpdateZxSaProjectSettleAuditItem(zxSaProjectSettleAuditItemList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    @ApiOperation(value="删除结算单明细", notes="删除结算单明细")
    @ApiImplicitParam(name = "zxSaProjectSettleAuditItemList", value = "结算管理-工程类结算-工程类结算单明细(原表iesa_ProjectSettleAuditItem)List", dataType = "List<ZxSaProjectSettleAuditItem>")
    @RequireToken
    @PostMapping("/deleteAllZxSaProjectSettleAuditItem")
    public ResponseEntity deleteAllZxSaProjectSettleAuditItem(@RequestBody(required = false) ZxSaProjectSettleAuditItem zxSaProjectSettleAuditItem) {
        return zxSaProjectSettleAuditItemService.deleteAllZxSaProjectSettleAuditItem(zxSaProjectSettleAuditItem);
    }
    
    @ApiOperation(value="获取指定期次结算单明细", notes="获取指定期次结算单明细")
    @ApiImplicitParam(name = "zxSaProjectSettleAuditItemList", value = "结算管理-工程类结算-工程类结算单明细(原表iesa_ProjectSettleAuditItem)List", dataType = "List<ZxSaProjectSettleAuditItem>")
    @RequireToken
    @PostMapping("/getZxSaProjectSettleAuditItemByContractId")
    public ResponseEntity getZxSaProjectSettleAuditItemByContractId(@RequestBody(required = false) ZxSaProjectSettleAuditItem zxSaProjectSettleAuditItem) {
    	return zxSaProjectSettleAuditItemService.getZxSaProjectSettleAuditItemByContractId(zxSaProjectSettleAuditItem);
    }
    
    @ApiOperation(value="查询工程类结算单明细", notes="查询工程类结算单明细")
    @ApiImplicitParam(name = "zxSaProjectSettleAuditItem", value = "结算管理-工程类结算-工程类结算单明细(原表iesa_ProjectSettleAuditItem)entity", dataType = "ZxSaProjectSettleAuditItem")
    @PostMapping("/getZxSaProjectSettleAuditItemListNoToken")
    public List<ZxSaProjectSettleAuditItem> getZxSaProjectSettleAuditItemListNoToken(@RequestBody(required = false) ZxSaProjectSettleAuditItem zxSaProjectSettleAuditItem) {
        return zxSaProjectSettleAuditItemService.getZxSaProjectSettleAuditItemListNoToken(zxSaProjectSettleAuditItem);
    }
    
    @ApiOperation(value="查询工程类结算单明细(增加其他保证金)", notes="查询工程类结算单明细(增加其他保证金)")
    @ApiImplicitParam(name = "zxSaProjectSettleAuditItem", value = "结算管理-工程类结算-工程类结算单明细(原表iesa_ProjectSettleAuditItem)entity", dataType = "ZxSaProjectSettleAuditItem")
    @PostMapping("/getZxSaProjectSettleAuditAllItemList")
    public List<ZxSaProjectSettleAuditItem> getZxSaProjectSettleAuditAllItemList(@RequestBody(required = false) ZxSaProjectSettleAuditItem zxSaProjectSettleAuditItem) {
    	return zxSaProjectSettleAuditItemService.getZxSaProjectSettleAuditAllItemList(zxSaProjectSettleAuditItem);
    }
    
    @ApiOperation(value="导出工程类结算单明细", notes="导出工程类结算单明细")
    @ApiImplicitParam(name = "zxSaProjectSettleAuditItem", value = "结算管理-工程类结算-工程类结算单明细(原表iesa_ProjectSettleAuditItem)entity", dataType = "ZxSaProjectSettleAuditItem")
    @RequireToken
    @PostMapping("/exportZxSaProjectSettleAuditItemList")
    public ResponseEntity exportZxSaProjectSettleAuditItemList(@RequestBody(required = false) ZxSaProjectSettleAuditItem zxSaProjectSettleAuditItem) {
    	return zxSaProjectSettleAuditItemService.exportZxSaProjectSettleAuditItemList(zxSaProjectSettleAuditItem);
    }
    
    @ApiOperation(value="导出工程类结算会签单", notes="导出工程类结算会签单")
    @ApiImplicitParam(name = "zxSaProjectSettleAuditItem", value = "结算管理-工程类结算-工程类结算单明细(原表iesa_ProjectSettleAuditItem)entity", dataType = "ZxSaProjectSettleAuditItem")
    @RequireToken
    @PostMapping("/exportZxSaProjectSettleAuditCountersignature")
    public ResponseEntity exportZxSaProjectSettleAuditCountersignature(@RequestBody(required = false) ZxSaProjectSettleAuditItem zxSaProjectSettleAuditItem) {
    	return zxSaProjectSettleAuditItemService.exportZxSaProjectSettleAuditCountersignature(zxSaProjectSettleAuditItem);
    }
}
