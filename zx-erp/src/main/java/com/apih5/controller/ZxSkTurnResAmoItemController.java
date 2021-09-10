package com.apih5.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.apih5.framework.annotation.RequireToken;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxSkTurnResAmoItem;
import com.apih5.service.ZxSkTurnResAmoItemService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@RestController
public class ZxSkTurnResAmoItemController {

    @Autowired(required = true)
    private ZxSkTurnResAmoItemService zxSkTurnResAmoItemService;

    @ApiOperation(value="查询周转材料摊销明细帐", notes="查询周转材料摊销明细帐")
    @ApiImplicitParam(name = "zxSkTurnResAmoItem", value = "周转材料摊销明细帐entity", dataType = "ZxSkTurnResAmoItem")
    @RequireToken
    @PostMapping("/getZxSkTurnResAmoItemList")
    public ResponseEntity getZxSkTurnResAmoItemList(@RequestBody(required = false) ZxSkTurnResAmoItem zxSkTurnResAmoItem) {
        return zxSkTurnResAmoItemService.getZxSkTurnResAmoItemListByCondition(zxSkTurnResAmoItem);
    }

    @ApiOperation(value="查询详情周转材料摊销明细帐", notes="查询详情周转材料摊销明细帐")
    @ApiImplicitParam(name = "zxSkTurnResAmoItem", value = "周转材料摊销明细帐entity", dataType = "ZxSkTurnResAmoItem")
    @RequireToken
    @PostMapping("/getZxSkTurnResAmoItemDetail")
    public ResponseEntity getZxSkTurnResAmoItemDetail(@RequestBody(required = false) ZxSkTurnResAmoItem zxSkTurnResAmoItem) {
        return zxSkTurnResAmoItemService.getZxSkTurnResAmoItemDetail(zxSkTurnResAmoItem);
    }

    @ApiOperation(value="新增周转材料摊销明细帐", notes="新增周转材料摊销明细帐")
    @ApiImplicitParam(name = "zxSkTurnResAmoItem", value = "周转材料摊销明细帐entity", dataType = "ZxSkTurnResAmoItem")
    @RequireToken
    @PostMapping("/addZxSkTurnResAmoItem")
    public ResponseEntity addZxSkTurnResAmoItem(@RequestBody(required = false) ZxSkTurnResAmoItem zxSkTurnResAmoItem) {
        return zxSkTurnResAmoItemService.saveZxSkTurnResAmoItem(zxSkTurnResAmoItem);
    }

    @ApiOperation(value="更新周转材料摊销明细帐", notes="更新周转材料摊销明细帐")
    @ApiImplicitParam(name = "zxSkTurnResAmoItem", value = "周转材料摊销明细帐entity", dataType = "ZxSkTurnResAmoItem")
    @RequireToken
    @PostMapping("/updateZxSkTurnResAmoItem")
    public ResponseEntity updateZxSkTurnResAmoItem(@RequestBody(required = false) ZxSkTurnResAmoItem zxSkTurnResAmoItem) {
        return zxSkTurnResAmoItemService.updateZxSkTurnResAmoItem(zxSkTurnResAmoItem);
    }

    @ApiOperation(value="删除周转材料摊销明细帐", notes="删除周转材料摊销明细帐")
    @ApiImplicitParam(name = "zxSkTurnResAmoItemList", value = "周转材料摊销明细帐List", dataType = "List<ZxSkTurnResAmoItem>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxSkTurnResAmoItem")
    public ResponseEntity batchDeleteUpdateZxSkTurnResAmoItem(@RequestBody(required = false) List<ZxSkTurnResAmoItem> zxSkTurnResAmoItemList) {
        return zxSkTurnResAmoItemService.batchDeleteUpdateZxSkTurnResAmoItem(zxSkTurnResAmoItemList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    
    @ApiOperation(value="报表导出周转材料摊销明细帐", notes="报表导出周转材料摊销明细帐")
    @ApiImplicitParam(name = "zxSkTurnResAmoItem", value = "设备台账entity", dataType = "ZxSkTurnResAmoItem")
    @PostMapping("/ureportZxSkTurnResAmoItem")
    public List<ZxSkTurnResAmoItem> ureportZxSkTurnResAmoItem(@RequestBody(required = false) ZxSkTurnResAmoItem zxSkTurnResAmoItem) {
        return zxSkTurnResAmoItemService.ureportZxSkTurnResAmoItem(zxSkTurnResAmoItem);
    }

    @ApiOperation(value="报表导出周转材料摊销明细帐", notes="报表导出周转材料摊销明细帐")
    @ApiImplicitParam(name = "zxSkTurnResAmoItem", value = "设备台账entity", dataType = "ZxSkTurnResAmoItem")
    @RequireToken
    @PostMapping("/ureportZxSkTurnResAmo")
    public ResponseEntity ureportZxSkTurnResAmo(@RequestBody(required = false) ZxSkTurnResAmoItem zxSkTurnResAmoItem) {
        return zxSkTurnResAmoItemService.ureportZxSkTurnResAmo(zxSkTurnResAmoItem);
    }
}
