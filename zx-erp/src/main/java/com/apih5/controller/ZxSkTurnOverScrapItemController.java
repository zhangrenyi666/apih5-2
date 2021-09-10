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
import com.apih5.mybatis.pojo.ZxSkTurnOverScrapItem;
import com.apih5.service.ZxSkTurnOverScrapItemService;

@RestController
public class ZxSkTurnOverScrapItemController {

    @Autowired(required = true)
    private ZxSkTurnOverScrapItemService zxSkTurnOverScrapItemService;

    @ApiOperation(value="查询周转材料报废明细", notes="查询周转材料报废明细")
    @ApiImplicitParam(name = "zxSkTurnOverScrapItem", value = "周转材料报废明细entity", dataType = "ZxSkTurnOverScrapItem")
    @RequireToken
    @PostMapping("/getZxSkTurnOverScrapItemList")
    public ResponseEntity getZxSkTurnOverScrapItemList(@RequestBody(required = false) ZxSkTurnOverScrapItem zxSkTurnOverScrapItem) {
        return zxSkTurnOverScrapItemService.getZxSkTurnOverScrapItemListByCondition(zxSkTurnOverScrapItem);
    }

    @ApiOperation(value="查询详情周转材料报废明细", notes="查询详情周转材料报废明细")
    @ApiImplicitParam(name = "zxSkTurnOverScrapItem", value = "周转材料报废明细entity", dataType = "ZxSkTurnOverScrapItem")
    @RequireToken
    @PostMapping("/getZxSkTurnOverScrapItemDetail")
    public ResponseEntity getZxSkTurnOverScrapItemDetail(@RequestBody(required = false) ZxSkTurnOverScrapItem zxSkTurnOverScrapItem) {
        return zxSkTurnOverScrapItemService.getZxSkTurnOverScrapItemDetail(zxSkTurnOverScrapItem);
    }

    @ApiOperation(value="新增周转材料报废明细", notes="新增周转材料报废明细")
    @ApiImplicitParam(name = "zxSkTurnOverScrapItem", value = "周转材料报废明细entity", dataType = "ZxSkTurnOverScrapItem")
    @RequireToken
    @PostMapping("/addZxSkTurnOverScrapItem")
    public ResponseEntity addZxSkTurnOverScrapItem(@RequestBody(required = false) ZxSkTurnOverScrapItem zxSkTurnOverScrapItem) {
        return zxSkTurnOverScrapItemService.saveZxSkTurnOverScrapItem(zxSkTurnOverScrapItem);
    }

    @ApiOperation(value="更新周转材料报废明细", notes="更新周转材料报废明细")
    @ApiImplicitParam(name = "zxSkTurnOverScrapItem", value = "周转材料报废明细entity", dataType = "ZxSkTurnOverScrapItem")
    @RequireToken
    @PostMapping("/updateZxSkTurnOverScrapItem")
    public ResponseEntity updateZxSkTurnOverScrapItem(@RequestBody(required = false) ZxSkTurnOverScrapItem zxSkTurnOverScrapItem) {
        return zxSkTurnOverScrapItemService.updateZxSkTurnOverScrapItem(zxSkTurnOverScrapItem);
    }

    @ApiOperation(value="删除周转材料报废明细", notes="删除周转材料报废明细")
    @ApiImplicitParam(name = "zxSkTurnOverScrapItemList", value = "周转材料报废明细List", dataType = "List<ZxSkTurnOverScrapItem>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxSkTurnOverScrapItem")
    public ResponseEntity batchDeleteUpdateZxSkTurnOverScrapItem(@RequestBody(required = false) List<ZxSkTurnOverScrapItem> zxSkTurnOverScrapItemList) {
        return zxSkTurnOverScrapItemService.batchDeleteUpdateZxSkTurnOverScrapItem(zxSkTurnOverScrapItemList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
