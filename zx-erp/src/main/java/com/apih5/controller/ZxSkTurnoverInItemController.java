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
import com.apih5.mybatis.pojo.ZxSkTurnoverInItem;
import com.apih5.service.ZxSkTurnoverInItemService;

@RestController
public class ZxSkTurnoverInItemController {

    @Autowired(required = true)
    private ZxSkTurnoverInItemService zxSkTurnoverInItemService;

    @ApiOperation(value="查询周转材料入库明细", notes="查询周转材料入库明细")
    @ApiImplicitParam(name = "zxSkTurnoverInItem", value = "周转材料入库明细entity", dataType = "ZxSkTurnoverInItem")
    @RequireToken
    @PostMapping("/getZxSkTurnoverInItemList")
    public ResponseEntity getZxSkTurnoverInItemList(@RequestBody(required = false) ZxSkTurnoverInItem zxSkTurnoverInItem) {
        return zxSkTurnoverInItemService.getZxSkTurnoverInItemListByCondition(zxSkTurnoverInItem);
    }

    @ApiOperation(value="查询详情周转材料入库明细", notes="查询详情周转材料入库明细")
    @ApiImplicitParam(name = "zxSkTurnoverInItem", value = "周转材料入库明细entity", dataType = "ZxSkTurnoverInItem")
    @RequireToken
    @PostMapping("/getZxSkTurnoverInItemDetail")
    public ResponseEntity getZxSkTurnoverInItemDetail(@RequestBody(required = false) ZxSkTurnoverInItem zxSkTurnoverInItem) {
        return zxSkTurnoverInItemService.getZxSkTurnoverInItemDetail(zxSkTurnoverInItem);
    }

    @ApiOperation(value="新增周转材料入库明细", notes="新增周转材料入库明细")
    @ApiImplicitParam(name = "zxSkTurnoverInItem", value = "周转材料入库明细entity", dataType = "ZxSkTurnoverInItem")
    @RequireToken
    @PostMapping("/addZxSkTurnoverInItem")
    public ResponseEntity addZxSkTurnoverInItem(@RequestBody(required = false) ZxSkTurnoverInItem zxSkTurnoverInItem) {
        return zxSkTurnoverInItemService.saveZxSkTurnoverInItem(zxSkTurnoverInItem);
    }

    @ApiOperation(value="更新周转材料入库明细", notes="更新周转材料入库明细")
    @ApiImplicitParam(name = "zxSkTurnoverInItem", value = "周转材料入库明细entity", dataType = "ZxSkTurnoverInItem")
    @RequireToken
    @PostMapping("/updateZxSkTurnoverInItem")
    public ResponseEntity updateZxSkTurnoverInItem(@RequestBody(required = false) ZxSkTurnoverInItem zxSkTurnoverInItem) {
        return zxSkTurnoverInItemService.updateZxSkTurnoverInItem(zxSkTurnoverInItem);
    }

    @ApiOperation(value="删除周转材料入库明细", notes="删除周转材料入库明细")
    @ApiImplicitParam(name = "zxSkTurnoverInItemList", value = "周转材料入库明细List", dataType = "List<ZxSkTurnoverInItem>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxSkTurnoverInItem")
    public ResponseEntity batchDeleteUpdateZxSkTurnoverInItem(@RequestBody(required = false) List<ZxSkTurnoverInItem> zxSkTurnoverInItemList) {
        return zxSkTurnoverInItemService.batchDeleteUpdateZxSkTurnoverInItem(zxSkTurnoverInItemList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
