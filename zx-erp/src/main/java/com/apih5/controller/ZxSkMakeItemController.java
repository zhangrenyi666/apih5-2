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
import com.apih5.mybatis.pojo.ZxSkMakeItem;
import com.apih5.service.ZxSkMakeItemService;

@RestController
public class ZxSkMakeItemController {

    @Autowired(required = true)
    private ZxSkMakeItemService zxSkMakeItemService;

    @ApiOperation(value="查询仓库盘点明细", notes="查询仓库盘点明细")
    @ApiImplicitParam(name = "zxSkMakeItem", value = "仓库盘点明细entity", dataType = "ZxSkMakeItem")
    @RequireToken
    @PostMapping("/getZxSkMakeItemList")
    public ResponseEntity getZxSkMakeItemList(@RequestBody(required = false) ZxSkMakeItem zxSkMakeItem) {
        return zxSkMakeItemService.getZxSkMakeItemListByCondition(zxSkMakeItem);
    }

    @ApiOperation(value="查询详情仓库盘点明细", notes="查询详情仓库盘点明细")
    @ApiImplicitParam(name = "zxSkMakeItem", value = "仓库盘点明细entity", dataType = "ZxSkMakeItem")
    @RequireToken
    @PostMapping("/getZxSkMakeItemDetail")
    public ResponseEntity getZxSkMakeItemDetail(@RequestBody(required = false) ZxSkMakeItem zxSkMakeItem) {
        return zxSkMakeItemService.getZxSkMakeItemDetail(zxSkMakeItem);
    }

    @ApiOperation(value="新增仓库盘点明细", notes="新增仓库盘点明细")
    @ApiImplicitParam(name = "zxSkMakeItem", value = "仓库盘点明细entity", dataType = "ZxSkMakeItem")
    @RequireToken
    @PostMapping("/addZxSkMakeItem")
    public ResponseEntity addZxSkMakeItem(@RequestBody(required = false) ZxSkMakeItem zxSkMakeItem) {
        return zxSkMakeItemService.saveZxSkMakeItem(zxSkMakeItem);
    }

    @ApiOperation(value="更新仓库盘点明细", notes="更新仓库盘点明细")
    @ApiImplicitParam(name = "zxSkMakeItem", value = "仓库盘点明细entity", dataType = "ZxSkMakeItem")
    @RequireToken
    @PostMapping("/updateZxSkMakeItem")
    public ResponseEntity updateZxSkMakeItem(@RequestBody(required = false) ZxSkMakeItem zxSkMakeItem) {
        return zxSkMakeItemService.updateZxSkMakeItem(zxSkMakeItem);
    }

    @ApiOperation(value="删除仓库盘点明细", notes="删除仓库盘点明细")
    @ApiImplicitParam(name = "zxSkMakeItemList", value = "仓库盘点明细List", dataType = "List<ZxSkMakeItem>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateZxSkMakeItem")
    public ResponseEntity batchDeleteUpdateZxSkMakeItem(@RequestBody(required = false) List<ZxSkMakeItem> zxSkMakeItemList) {
        return zxSkMakeItemService.batchDeleteUpdateZxSkMakeItem(zxSkMakeItemList);
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
